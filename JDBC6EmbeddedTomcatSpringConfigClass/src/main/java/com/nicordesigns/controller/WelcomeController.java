package com.nicordesigns.controller;

import com.nicordesigns.service.DBHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class WelcomeController {

  @Autowired private DBHelloService DBHelloService;

  @RequestMapping(value = "/hello")
  public String test(WebRequest request, Model model) {
    model.addAttribute("message", DBHelloService.execute("HELLO"));
    return "welcome";
  }
}
