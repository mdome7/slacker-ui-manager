package com.labs2160.slacker.service

import com.labs2160.slacker.api.{Action, Endpoint}
import com.labs2160.slacker.core.engine.{Workflow, WorkflowEngine}
import com.labs2160.slacker.model.wf.conf.{EndpointDefinition, ActionDefinition, WorkflowDefinition}

import scala.collection.JavaConversions._
/**
 * Created by mdometita on 8/22/15.
 */
class WorkflowService(val engine: WorkflowEngine) {

    def findWorkflow(path: String*): Workflow = {
        engine.getRegistry.findWorkflow(path: _*)
    }

    def extractDefinition(wf: Workflow, path: String*): WorkflowDefinition = {
        val actions = wf.getActions.map(extractDefinition(_))
        WorkflowDefinition(path, wf.getName, wf.getDescription, true, actions, Seq.empty[EndpointDefinition])
    }

    def extractDefinition(action: Action): ActionDefinition = {
        ActionDefinition(action.getClass.getName)
    }
}
