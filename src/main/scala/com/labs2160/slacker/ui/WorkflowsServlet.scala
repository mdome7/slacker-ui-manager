package com.labs2160.slacker.ui

import com.labs2160.slacker.core.engine.{Workflow, WorkflowMetadata}
import com.labs2160.slacker.service.{WorkflowService, ConfigurationService}
import com.labs2160.slacker.model.User
import com.typesafe.scalalogging.LazyLogging
import org.json4s.{FieldSerializer, DefaultFormats, Formats}
import org.scalatra.NotFound
import org.scalatra.json._

/**
 * Created by mdometita on 8/20/15.
 */
class WorkflowsServlet(val confService: ConfigurationService, val workflowService: WorkflowService) extends BaseServlet with LazyLogging {

    protected override implicit val jsonFormats: Formats = DefaultFormats + FieldSerializer[Workflow]()

    get("/") {
        contentType = formats("html")
        val workflowMetadataList = confService.listWorkflowMetadata

        def comparator(wmd1: WorkflowMetadata, wmd2: WorkflowMetadata) =
            wmd1.getPath.reduceLeft(_ + _).toLowerCase < wmd2.getPath.reduceLeft(_ + _).toLowerCase

        scaml("workflows", "workflowMetadataList" -> workflowMetadataList.sortWith(comparator))
    }

    get("/:path") {
        contentType = formats("json")
        val path = params("path").split("_")
        val wf = workflowService.findWorkflow(path: _*)
        if (wf == null) {
            halt(NotFound(s"Workflow not found for path: ${path.reduceLeft(_ + " " + _)}"))
        } else {
            logger.debug(s"Found workflow ${wf.getName} - ${wf.getDescription}")
            workflowService.extractDefinition(wf, path: _*)
        }
    }
}
