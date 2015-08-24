import java.nio.file.{Path, Files, Paths}
import java.util.function.Consumer
import javax.servlet.ServletContext

import com.labs2160.slacker.core.config.{YAMLWorkflowEngineProvider, SlackerConfig}
import com.labs2160.slacker.core.dao.{ActionDAOFS, ActionDAO}
import com.labs2160.slacker.core.lib.ClassLoaderUtil
import com.labs2160.slacker.service.{WorkflowService, ExecutionsService, ConfigurationService, WorkflowExecutionLogger}
import com.labs2160.slacker.ui._
import com.typesafe.scalalogging.LazyLogging
import net.sf.ehcache.CacheManager
import org.scalatra._
import scala.collection.JavaConversions._
//import org.eclipse.jetty.webapp.WebAppClassLoader

class ScalatraBootstrap extends LifeCycle with LazyLogging {

    val ContextPath = s""

    override def init(context: ServletContext) = {

        logger.info(s"Application Name    : ${Settings.appName}")
        logger.info(s"Application Version : ${Settings.appVersion}")
        logger.info(s"Build Timestamp     : ${Settings.buildTimestamp}")
        logger.info(s"Server Port         : ${Settings.serverPort}")

        logger.info(s"Home Dir            : ${Settings.dirHome}")
        logger.info(s"Executions Logs Dir : ${Settings.dirExecutionsLogs}")

        val cacheManager = CacheManager.create(classOf[ScalatraBootstrap].getResource("/ehcache.xml"))


        for (dir <- List(Settings.dirHome, Settings.dirConf, Settings.dirExecutionsLogs)) {
            if (! Files.exists(dir)) Files.createDirectories(dir)
        }

        loadLibraries(Settings.dirLib)


        val executionLogger = new WorkflowExecutionLogger(Settings.dirExecutionsLogs)
        val config = new SlackerConfig
        config.setConfigFile(Paths.get(Settings.dirHome.toString, "config.yaml"))

        val engineProvider = new YAMLWorkflowEngineProvider(config)
        val engine = engineProvider.provide

        engine.addWorkflowExecutionListener(executionLogger)

        engine.start

        val actionDAO = new ActionDAOFS(Settings.dirConf)
        val confService = new ConfigurationService(engine, actionDAO)
        val executionsService = new ExecutionsService(Settings.dirExecutionsLogs)
        val workflowService = new WorkflowService(engine)

        context.mount(new SystemServlet, s"$ContextPath/system", "System")
        context.mount(new HomeServlet(confService), s"$ContextPath/home", "Home")
        context.mount(new ActionsServlet(confService), s"$ContextPath/actions", "Actions")
        context.mount(new TriggersServlet(confService), s"$ContextPath/triggers", "Triggers")
        context.mount(new CollectorsServlet(confService), s"$ContextPath/collectors", "Collectors")
        context.mount(new WorkflowsServlet(confService, workflowService), s"$ContextPath/workflows", "Workflows")
        context.mount(new ExecutionsServlet(confService, executionsService), s"$ContextPath/admin/executions", "Executions")
        context.mount(new ExecutionServlet(engine), s"$ContextPath/execute", "Execute")
    }

    def loadLibraries(dir: Path): Unit = {
        var stream = Files.newDirectoryStream(dir, "*.jar")

        val cl = getClass.getClassLoader
        stream.foreach(p => {
            logger.info("Adding to classpath: {}", p.toString)
            callMethod(cl, "addURL", p.toUri.toURL)
        })
    }

    def callMethod(obj: AnyRef, methodName: String, _args: Any*): Any = {
        val args = _args.map(_.asInstanceOf[AnyRef])
        def _parents: Stream[Class[_]] = Stream(obj.getClass) #::: _parents.map(_.getSuperclass)
        val parents = _parents.takeWhile(_ != null).toList
        val methods = parents.flatMap(_.getDeclaredMethods)
        val method = methods.find(_.getName == methodName).getOrElse(throw new IllegalArgumentException("Method " + methodName + " not found"))
        method.setAccessible(true)
        method.invoke(obj, args : _*)
    }
}
