package com.labs2160.slacker.ui

import com.labs2160.slacker.api.{SlackerRequest, RequestHandler}
import com.labs2160.slacker.core.engine.WorkflowEngine
import com.labs2160.slacker.service.{ConfigurationService, ExecutionsService}
import org.scalatra.BadRequest

/**
 * Created by mdometita on 8/20/15.
 */
class ExecutionServlet(val handler: RequestHandler) extends BaseServlet {

    get ("/") {
        contentType = formats("txt")

        val req = params.getOrElse("request", halt(BadRequest("request parameter is required")))
        logger.info("Got request: {}", request)

        val slackerRequest = new SlackerRequest(this.getClass.getName, req.split(" "): _*)
        val res = handler.handle(slackerRequest)

        logger.info("Submitted request...")
        res.get.getMessage
    }
}
