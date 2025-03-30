package me.vasylkov.minecraftproxybridge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String redirectToEndpoint() {
        return "redirect:/proxy/control";
    }
}
