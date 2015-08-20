package com.labs2160.slacker.ui

import com.typesafe.scalalogging.Logger
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.ScalatraServlet
import org.scalatra.json.JacksonJsonSupport
import org.slf4j.LoggerFactory

/**
 * Created by mdometita on 6/11/15.
 */
class SystemServlet extends BaseServlet {

    get("/") {
        Map("Application Version" -> Settings.appVersion,
            "Application Name" -> Settings.appName,
            "Build Timestamp" -> Settings.buildTimestamp)
    }
}
