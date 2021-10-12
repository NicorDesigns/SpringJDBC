package com.nicordesigns;

import com.nicordesigns.dao.CharityDao;
import com.nicordesigns.dao.CharityDaoImpl;
import com.nicordesigns.model.Charity;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;

public class Main {

  public static void main(String[] args) {

    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

    DataSource dataSource = context.getBean(DataSource.class);
    System.out.println(dataSource);

    CharityDao charityDao = new CharityDaoImpl(dataSource);

    Charity charity =
        new Charity(
            "4760176232",
            "Zisize Care Centre ",
            "Zisize is a care centre for the disabled situated at "
                + "Dingaanstat mission at Makhosini Valley near Ulundi.",
            "https://www.facebook.com/ZISIZE/?ref=page_internal",
            "https://www.facebook.com/ZISIZE/?ref=page_internal",
            "N/A");

    // int charityId = charityDao.insert(charity);
    // System.out.println(charityId);

    Charity charity1 = charityDao.findByCharityTaxId("4760176232");
    System.out.println(charity1);
  }
}
