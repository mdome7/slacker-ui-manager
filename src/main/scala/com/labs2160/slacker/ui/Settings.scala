package com.labs2160.slacker.ui

import java.nio.file.Paths

import com.typesafe.config.ConfigFactory

/**
 * Application configuration settings.
 */
object Settings {

    /* looks for application.conf in classpath; can be overridden by system env "config.file",
       "config.resource" or "config.url" (see typesafe config documentation) */
    val config = ConfigFactory.load

    // non-lazy fields, we want all exceptions at construct time
    /** datetime this application software was built */
    val buildTimestamp = config.getString("build.timestamp")

    /** application version (e.g. 1.0.0-SNAPSHOT) */
    val appName = config.getString("app.name")

    /** application version (e.g. 1.0.0-SNAPSHOT) */
    val appVersion = config.getString("app.version")

    /** port the Jetty server will listen on */
    val serverPort = config.getInt("server.port")

    val dirHome = Paths.get(config.getString("directories.home"))
    val dirLib = Paths.get(config.getString("directories.lib"))
    val dirConf = Paths.get(config.getString("directories.conf"))
    val dirExecutionsLogs = Paths.get(config.getString("directories.executions.logs"))
}
