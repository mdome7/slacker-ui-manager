package com.labs2160.slacker.ui

import com.typesafe.scalalogging.Logger
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.{ServletContextHandler, DefaultServlet}
import org.scalatra.servlet.ScalatraListener
import org.slf4j.LoggerFactory

/**
 * Main entry point for the application.
 * Launches the Jetty server.
 */
object JettyLauncher {

    def main(args: Array[String]) {
        val Log = Logger(LoggerFactory.getLogger(JettyLauncher.getClass))

        val server = new Server(Settings.serverPort)
        val context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
        context.addServlet(classOf[DefaultServlet].getName(), "/");
        context.addEventListener(new ScalatraListener())

        server.setHandler(context);

        Log.info("Starting Jetty server...")
        server.start

        Log.info(s"Server started, listening on port ${Settings.serverPort}")
        server.join
    }
}
