package com.labs2160.slacker.service

import java.nio.file.{Files, Path}
import java.time.LocalDateTime

import com.labs2160.slacker.model.Execution
import com.typesafe.scalalogging.LazyLogging

import scala.collection.mutable.ListBuffer
import scala.collection.JavaConversions._
import scala.io.Source

/**
 * Created by mdometita on 8/21/15.
 */
class ExecutionsService(val dir: Path) extends LazyLogging {

    def getFinishedExecutions(from: Option[LocalDateTime] = None): List[Execution] = {
        var stream = Files.newDirectoryStream(dir)
        val executions = ListBuffer.empty[Execution]
        stream.foreach(p => {
            logger.info("Execution: {}", p.toString)
            executions += new Execution(p.getFileName.toString, Source.fromFile(p.toFile).getLines().toList)
        })
        executions.toList
    }
}
