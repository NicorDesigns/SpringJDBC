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

    System.out.println("Inserting new Created Charity: " + charity);
    Charity insertedCharity = charityDao.insert(charity);

    System.out.println(
        "DB Generated charityId: " + insertedCharity.getCharityId() + " inserted :  " + charity);
    System.out.println("Inserted Charity Category: " + insertedCharity.getCharityCategory());
    System.out.println(
        "Inserted Charity CategoryId: " + insertedCharity.getCharityCategory().getCategoryId());

    //    Charity foundCharity = charityDao.findByCharityTaxId(charity.getCharityTaxId());
    //    System.out.println(foundCharity);
    //
    //    foundCharity.setCharityName("Update_" + charity.getCharityName());
    //    foundCharity.setCharityMission("Update_" + charity.getCharityMission());
    //    int rowsAffected = charityDao.update(foundCharity);
    //    System.out.println("Rows Affected : " + rowsAffected);
    //
    //    Charity updatedCharity = charityDao.findByCharityTaxId(foundCharity.getCharityTaxId());
    //    System.out.println(updatedCharity);
    //
    //    Charity foundUpdatedCharity =
    // charityDao.findByCharityTaxId(updatedCharity.getCharityTaxId());
    //    System.out.println(foundUpdatedCharity);
    //
    //    int rowsDeleted = charityDao.delete(foundCharity);
    //    System.out.println("Rows Affected : " + rowsDeleted);
  }
}
