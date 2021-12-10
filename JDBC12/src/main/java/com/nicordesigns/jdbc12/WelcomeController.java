package com.nicordesigns.jdbc12;

import com.nicordesigns.jdbc12.model.Charity;
import com.nicordesigns.jdbc12.service.CharityService;
import com.nicordesigns.jdbc12.service.DBHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WelcomeController {

  @Autowired DBHelloService dbHelloService;

  @Autowired CharityService charityService;

  @GetMapping("/hello")
  public String greeting(
      @RequestParam(name = "message", required = false, defaultValue = "World") String message,
      Model model) {
    model.addAttribute("message", dbHelloService.execute("Now "));
    return "welcome";
  }

  @GetMapping("/findcharity")
  public String charity(
      @RequestParam(name = "taxid", required = false, defaultValue = "XXXXXXXXXX2") String taxid,
      Model model) {
    List<Charity> charities;
    charities = new ArrayList<Charity>();
    var charity = charityService.findByCharityTaxId("XXXXXXXXXX2");
    charities.add(charity);
    model.addAttribute("charities", charities);
    return "charities";
  }
}
