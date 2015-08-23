package com.labs2160.slacker.core.dao

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}
import java.util.Properties

import com.labs2160.slacker.api.{Action, SlackerContext}
import com.typesafe.scalalogging.LazyLogging
import org.scalatest.{BeforeAndAfter, FunSpec, GivenWhenThen}
import org.scalatest.Matchers._

import scala.collection.JavaConversions._

/**
 * Created by mdometita on 8/21/15.
 */
class ActionDAOFSSpec extends FunSpec with BeforeAndAfter with GivenWhenThen with LazyLogging {

    class TempAction1 extends Action {
        override def execute(slackerContext: SlackerContext): Boolean = true
        override def setConfiguration(properties: Properties): Unit = {}
    }

    class TempAction2 extends Action {
        override def execute(slackerContext: SlackerContext): Boolean = true
        override def setConfiguration(properties: Properties): Unit = {}
    }

    var dao: ActionDAOFS = _

    val tmpDir = System.getProperty("java.io.tmpdir")
    val actionListFile = Paths.get(tmpDir, "actions.csv")

    before {
        val classes = new StringBuffer
        List(classOf[TempAction1], classOf[TempAction2]).foreach(clazz => {
            classes.append(clazz.getName).append("\n")
        })
        Files.write(actionListFile, classes.toString.getBytes(StandardCharsets.UTF_8))

        dao = new ActionDAOFS(Paths.get(tmpDir))
    }

    after {
        logger.info("Deleting file: {}", actionListFile.toString)
        Files.delete(actionListFile)
    }

    describe("ActionDAOFS") {
        describe("#listActionClasses") {
            it("loads it corrrectly") {
                val actionClasses = dao.listActionClasses()
                actionClasses should not be ('empty)
                actionClasses.foreach(c => {
                    logger.info("Action class: {}", c.getName)
                })
            }
        }
    }
}
