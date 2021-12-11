package com.nicordesigns.jdbc12jdbcspringboot;

import com.nicordesigns.jdbc12jdbcspringboot.model.Charity;
import com.nicordesigns.jdbc12jdbcspringboot.service.CharityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CharityController {

  @Autowired CharityService charityService;

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
