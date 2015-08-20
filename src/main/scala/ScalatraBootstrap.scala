import javax.servlet.ServletContext

import com.labs2160.slacker.ui.{Settings, SystemServlet}
import com.typesafe.scalalogging.LazyLogging
import net.sf.ehcache.CacheManager
import org.scalatra._

class ScalatraBootstrap extends LifeCycle with LazyLogging {

    val ContextPath = s"/slacker"

    override def init(context: ServletContext) = {

        logger.info(s"Application Name    : ${Settings.appName}")
        logger.info(s"Application Version : ${Settings.appVersion}")
        logger.info(s"Build Timestamp     : ${Settings.buildTimestamp}")
        logger.info(s"Server Port         : ${Settings.serverPort}")

        val cacheManager = CacheManager.create(classOf[ScalatraBootstrap].getResource("/ehcache.xml"))
        context.mount(new SystemServlet, s"$ContextPath/system", "System")
    }
}
