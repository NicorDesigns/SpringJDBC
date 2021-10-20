package com.nicordesigns;

import com.nicordesigns.dao.CharityDao;
import com.nicordesigns.dao.JdbcCharityDao;
import com.nicordesigns.model.Category;
import com.nicordesigns.model.Charity;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;

public class Main {

  public static void main(String[] args) throws SQLException {

    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

    DataSource dataSource = context.getBean(DataSource.class);
    System.out.println(dataSource);

    CharityDao charityDao = context.getBean(JdbcCharityDao.class);

    Category category = new Category("DISABILITIES");
    System.out.println("Created new Category: " + category);
    Charity charity =
        new Charity(
            "4760176232",
            "Zisize Care Centre ",
            "Zisize is a care centre for the disabled situated at "
                + "Dingaanstat mission at Makhosini Valley near Ulundi.",
            "https://www.facebook.com/ZISIZE/?ref=page_internal",
            "https://www.facebook.com/ZISIZE/?ref=page_internal",
            "N/A");

    int charityId = charityDao.insert(charity);
    System.out.println(charity + " inserted : DB Generated charityId: " + charityId);

    Charity charity1 = charityDao.findByCharityTaxId("4760176232");
    System.out.println(charity1);
  }
}
