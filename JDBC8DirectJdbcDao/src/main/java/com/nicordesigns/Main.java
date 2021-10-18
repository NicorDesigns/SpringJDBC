package com.nicordesigns;

import com.nicordesigns.dao.CharityDao;
import com.nicordesigns.dao.CharityDaoImpl;
import com.nicordesigns.model.Category;
import com.nicordesigns.model.Charity;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;

public class Main {

  public static void main(String[] args) throws SQLException {

    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

    CharityDao charityDao = context.getBean(CharityDaoImpl.class);

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
            "N/A",
            category);

    System.out.println("Created new Charity: " + charity);

    //        Charity insertedCharity = charityDao.insert(charity);
    //
    //        System.out.println(
    //            "DB Generated charityId: " + insertedCharity.getCharityId() + " inserted :  " +
    //     charity);
    //        System.out.println("Inserted Charity Category: " +
    // insertedCharity.getCharityCategory());
    //        System.out.println(
    //            "Inserted Charity CategoryId: " +
    //     insertedCharity.getCharityCategory().getCategoryId());

    System.out.println("Finding Charity in MariaDB: " + charity);
    Charity findCharityInDB = charityDao.findByCharityTaxId(charity.getCharityTaxId());
    System.out.println("Charity retrieved from MariaDB: " + findCharityInDB);

    //    findCharityInDB.setCharityName("Update_" + charity.getCharityName());
    //    findCharityInDB.setCharityMission("Update_" + charity.getCharityMission());
    //    int rowsAffected = charityDao.update(findCharityInDB);
    //    System.out.println("Rows Affected : " + rowsAffected);

    //    Charity updatedCharity = charityDao.findByCharityTaxId(findCharityInDB.getCharityTaxId());
    //    System.out.println("Updated Charity in DB: " + updatedCharity);
    //
    //    Charity foundUpdatedCharity =
    // charityDao.findByCharityTaxId(updatedCharity.getCharityTaxId());
    //    System.out.println(foundUpdatedCharity);
    //
    //    int rowsDeleted = charityDao.delete(findCharityInDB);
    //    System.out.println("Rows Affected : " + rowsDeleted);
  }
}
