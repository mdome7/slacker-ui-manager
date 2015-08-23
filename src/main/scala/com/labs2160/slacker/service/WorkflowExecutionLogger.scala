package com.labs2160.slacker.service

import java.io.{FileWriter, PrintWriter}
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path, Paths, StandardOpenOption}

import com.fasterxml.jackson.databind.ObjectMapper
import com.labs2160.slacker.core.event.{WorkflowExecutionEventType, WorkflowExecutionEvent, WorkflowExecutionListener}
import com.typesafe.scalalogging.{LazyLogging, Logger}
import org.slf4j.LoggerFactory

import scala.collection.mutable.ListBuffer


/**
 * Created by mdometita on 8/20/15.
 */
class WorkflowExecutionLogger(val logDirPath: Path) extends WorkflowExecutionListener with LazyLogging {

    val objectMapper = new ObjectMapper

    def this(logDir: String) = this(Paths.get(logDir))

    val logBuffer = new collection.mutable.HashMap[String,ListBuffer[String]]

    override def notifyEvent(event: WorkflowExecutionEvent): Unit = {
        val events = logBuffer.getOrElseUpdate(event.getWorkflowId, ListBuffer.empty[String])

        val eventLine = objectMapper.writeValueAsString(event)
        logger.debug("WF Event: {}", eventLine)
        events += eventLine
        if (event.getType == WorkflowExecutionEventType.WORKFLOW_FINISH) {
            flush(event.getWorkflowId)
        }
    }

    def flush(workflowId: String): Unit = {
        logger.info("Flushing logs for {}", workflowId)
        val logEntries = logBuffer.get(workflowId)
        if (logEntries.isDefined) {
            // TODO: make more efficient and thead safe
            val pw = new PrintWriter(new FileWriter(getLogFileForWorkflow(workflowId).toString))
            logEntries.get.foreach(pw.println(_))
            pw.close();
            //Files.write(getLogFileForWorkflow(workflowId), lines, cs = StandardCharsets.UTF_8, StandardOpenOption.APPEND, StandardOpenOption.CREATE, StandardOpenOption.WRITE)
        }
    }

    private def getLogFileForWorkflow(workflowId: String): Path = {
        Paths.get(logDirPath.toString, workflowId)
    }
}
