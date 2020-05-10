package us.testfor.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class FrontResource {
    @GetMapping("/")
    fun get(model: Model): String {
        model["title"] = "Hello world"
        return "main"
    }
}
