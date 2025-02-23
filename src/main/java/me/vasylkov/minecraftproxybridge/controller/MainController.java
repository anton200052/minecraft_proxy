package me.vasylkov.minecraftproxybridge.controller;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.proxy.ProxyConfiguration;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyInfo;
import me.vasylkov.minecraftproxybridge.service.ProxyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/proxy")
public class MainController {
    private final ProxyService proxyService;
    private final ProxyConfiguration proxyConfiguration;

    @GetMapping("/control")
    public String control(Model model) {
        model.addAttribute("isProxyEnabled", proxyConfiguration.isEnabled());
        return "control";
    }

    @GetMapping("/showProxyForm")
    public String showProxyForm(Model model) {
        ProxyInfo proxyInfo = new ProxyInfo();
        model.addAttribute("proxyInfo", proxyInfo);
        return "enable_proxy_form";
    }

    @PostMapping("/enable")
    public String enable(@ModelAttribute("proxyInfo") ProxyInfo proxyInfo, Model model) throws IOException {
        proxyService.enableProxy(proxyInfo);
        return "redirect:/proxy/control";
    }

    @GetMapping("/disable")
    public String disable(Model model) throws IOException {
        proxyService.disableProxy();
        return "redirect:/proxy/control";
    }
}
