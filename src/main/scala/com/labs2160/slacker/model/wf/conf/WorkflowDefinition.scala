package com.labs2160.slacker.model.wf.conf

/**
 * Created by mdometita on 8/22/15.
 */
case class WorkflowDefinition (val path: Seq[String],
                          val name: String,
                          val description: String,
                          val active: Boolean,
                          val actions: Seq[ActionDefinition],
                          val endpoints: Seq[EndpointDefinition]
                          )
