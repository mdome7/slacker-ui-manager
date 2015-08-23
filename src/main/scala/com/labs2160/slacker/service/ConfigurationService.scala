package com.labs2160.slacker.service

import com.labs2160.slacker.api.Action
import com.labs2160.slacker.core.dao.ActionDAO
import com.labs2160.slacker.core.engine._

import scala.collection.JavaConversions._

/**
 * Created by mdometita on 8/21/15.
 */
class ConfigurationService(val engine: WorkflowEngine, val actionDAO: ActionDAO) {

    def listActionClasses(): List[Class[_ <: Action]] = actionDAO.listActionClasses

    def listActionMetadata(): List[ActionMetadata] = listActionClasses.map(ActionMetadataExtractor.extract(_))

    def listWorkflowMetadata(): List[WorkflowMetadata] = engine.getRegistry.getWorkflowMetadata.toList

    def findWorkflow(path: String*): Workflow = {
        engine.getRegistry.findWorkflow(path: _*)
    }
}
