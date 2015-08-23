package com.labs2160.slacker.ui

import com.labs2160.slacker.service.ConfigurationService
import com.labs2160.slacker.model.User

/**
 * Created by mdometita on 8/20/15.
 */
class HomeServlet(val confService: ConfigurationService) extends BaseServlet {

    get ("/") {
        contentType = "text/html"

        val user = new User("test")

        scaml("home")
    }
}
