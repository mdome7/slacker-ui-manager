package com.labs2160.slacker.ui

import com.typesafe.scalalogging.Logger
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.{InternalServerError, ScalatraServlet}
import org.scalatra.json.JacksonJsonSupport
import org.slf4j.LoggerFactory

/**
 * Base servlet for this application
 */
abstract class BaseServlet extends ScalatraServlet with JacksonJsonSupport {

    protected val logger = Logger(LoggerFactory.getLogger(this.getClass))

    // Sets up automatic case class to JSON output serialization
    protected implicit val jsonFormats: Formats = DefaultFormats

    before() {
        // Before every action runs, set the content type to be in JSON format.
        contentType = formats("json")
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
