package com.labs2160.slacker.ui

import com.typesafe.scalalogging.{LazyLogging, Logger}
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.scalate.{ScalateUrlGeneratorSupport, ScalateSupport}
import org.scalatra.{UrlGeneratorSupport, InternalServerError, ScalatraServlet}
import org.scalatra.json.JacksonJsonSupport
import org.slf4j.LoggerFactory
import org.scalatra.json._

/**
 * Base servlet for this application
 */
abstract class BaseServlet extends ScalatraServlet
    with JacksonJsonSupport
    with ScalateSupport
    with UrlGeneratorSupport
    with ScalateUrlGeneratorSupport
    with LazyLogging {

    val defaultRoute = get("/") { jade("index") }

    // Sets up automatic case class to JSON output serialization
    protected implicit val jsonFormats: Formats = DefaultFormats

    before() {
        // Before every action runs, set the content type to be in JSON format.
        contentType = formats("json")
    }

    notFound {
        // remove content type in case it was set through an action
        contentType = null
        // Try to render a ScalateTemplate if no route matched
        findTemplate(requestPath) map { path =>
            contentType = "text/html"
            layoutTemplate(path)
        } orElse serveStaticResource() getOrElse resourceNotFound()
    }

    /**
     * Catch-all error handler.
     * Wrap the error message in proper JSON.  Potentially add
     * error codes in the future.
     */
    error {
        case e: Throwable =>
            logger.error(s"Unhandled error - ${e.getMessage}", e)
            halt(InternalServerError(formatResponseMessageJson(e.getMessage)))
    }

    /**
     * Wrapper to return simple messages in JSON format.
     * @param msg
     * @return
     */
    protected def formatResponseMessageJson(msg: String) = s"""{"message" : "${msg}"}"""
}
