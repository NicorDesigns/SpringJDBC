package com.nicordesigns.controller;

import com.nicordesigns.service.DBHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {

  @Autowired private DBHelloService DBHelloService;

  @RequestMapping(value = "/hello")
  public String showDBCatalog(Model model) {
    model.addAttribute("message", DBHelloService.execute("HELLO"));
    return "welcome";
  }
}
