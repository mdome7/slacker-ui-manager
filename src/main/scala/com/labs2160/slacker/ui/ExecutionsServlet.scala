package com.labs2160.slacker.ui

import com.labs2160.slacker.service.{ExecutionsService, ConfigurationService}

/**
 * Created by mdometita on 8/20/15.
 */
class ExecutionsServlet(val confService: ConfigurationService, val executionsService: ExecutionsService) extends BaseServlet {

    get ("/") {
        contentType = "text/html"

        scaml("executions",
            "finishedExecutions" -> executionsService.getFinishedExecutions())
    }
}
