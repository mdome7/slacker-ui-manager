package com.labs2160.slacker.service

import java.nio.file.Paths

import com.labs2160.slacker.ui.Settings
import com.typesafe.scalalogging.LazyLogging
import org.scalamock.scalatest.MockFactory
import org.scalatest.{GivenWhenThen, BeforeAndAfter, FunSpec}
import org.scalatest.Matchers._

import scala.io.Source

/**
 * Created by mdometita on 8/21/15.
 */
class ExecutionsServiceSpec extends FunSpec with BeforeAndAfter with GivenWhenThen with MockFactory with LazyLogging {

    val service = new ExecutionsService(Settings.dirExecutionsLogs)
    describe("ExecutionsService") {
        describe("#getFinishedExecutions") {
            it("loads it corrrectly") {
                val finishedExecutions = service.getFinishedExecutions()
                finishedExecutions should not be ('empty)
                finishedExecutions.foreach(e => {
                    logger.info("Workflow {}", e.workflowId)
                })
            }
        }
    }
}
