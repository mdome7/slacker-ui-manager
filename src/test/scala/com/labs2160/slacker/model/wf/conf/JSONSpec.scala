package com.labs2160.slacker.model.wf.conf

import com.fasterxml.jackson.databind.ObjectMapper
import com.typesafe.scalalogging.LazyLogging
import org.json4s.{DefaultFormats, Formats}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FunSpec, GivenWhenThen, BeforeAndAfter}
import org.scalatest.Matchers._
import org.json4s.JsonDSL._
import org.json4s.JsonDSL.WithDouble._
import org.json4s.jackson.JsonMethods._
import org.json4s.native.Serialization.{read, write}
/**
 * Created by mdometita on 8/22/15.
 */
class JSONSpec extends FunSpec with BeforeAndAfter with GivenWhenThen with MockFactory with LazyLogging {

    protected implicit val jsonFormats: Formats = DefaultFormats

    val actions = (1 to 3).map(i => {
        ActionDefinition(s"action${i}", Map("key" -> s"val${i}"))
    })
    val endpoints = (1 to 3).map(i => {
        EndpointDefinition(s"endpoint${i}", Map("key" -> s"val${i}"))
    })

    val workflow = WorkflowDefinition(Seq("a", "b"), "workflow1", "description", actions, endpoints, true)

    val om = new ObjectMapper()

    describe("Workflow toJSON") {
        it ("yah") {
            val j = List(1, 2, 3)
            val json = write(workflow)
            logger.info(pretty(render(parse(json))))
            //json should equal ("abc")
        }
    }
}
