package com.labs2160.slacker.model.wf.conf

/**
 * Created by mdometita on 8/22/15.
 */
case class ActionDefinition(val className: String, val configuration: Map[String, String] = Map.empty[String, String])
