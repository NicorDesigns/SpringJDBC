package com.nicordesigns;

import com.nicordesigns.model.Category;
import com.nicordesigns.model.Charity;
import com.nicordesigns.service.CharityService;
import com.nicordesigns.service.CharityServiceImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;

public class Main {

  public static void main(String[] args) throws SQLException {

    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

    CharityService charityService = context.getBean("charityService", CharityServiceImpl.class);

    //    Charity charityZisize = createCharity();
    //
    //    int charityId = charityService.createCharity(charityZisize);
    //    System.out.println(charityZisize + " inserted : DB Generated charityId: " + charityId);
    //
    //    Charity charity1 = charityService.findByCharityTaxId("4760176232");
    //    System.out.println(charity1);

    //    Category categoryYMCA = new Category("COMMUNITY DEVELOPMENT");
    //    Charity charityYMCA =
    //        new Charity(
    //            "XXXXXXXXXX1",
    //            "YMCA South Africa ",
    //            "YMCA is the oldest Christian Youth Development organisation in the world"
    //                + "...reaching 65 million young people worldwide.  ",
    //            "https://www.saymca.org.za/",
    //            "https://www.facebook.com/YMCASouthAfrica/",
    //            "https://twitter.com/ymca_sa");
    //    charityYMCA.setCharityCategory(categoryYMCA);
    //    int charityId = charityService.createCharity(charityYMCA);
    //    System.out.println(charityYMCA + " inserted : DB Generated charityId: " + charityId);
    //
    //    Charity charity1 = charityService.findByCharityTaxId("XXXXXXXXXX1");
    //    System.out.println(charity1);

    Category categoryUSCA = new Category("RELIGION");
    Charity charityUMCA =
        new Charity(
            "XXXXXXXXXX2",
            "UMCA ",
            "UCSA is a voluntary, non-racial, multicultural, interdenominational Christian organization.",
            "http://vcsv.co.za/",
            "https://www.facebook.com/UCSA.VCSV/",
            "https://twitter.com/VCSV_UCSA");
    charityUMCA.setCharityCategory(categoryUSCA);

    int charityIdUMCA = charityService.createCharity(charityUMCA);
    System.out.println(charityUMCA + " inserted : DB Generated charityId: " + charityIdUMCA);

    Charity charityUMCAinDB = charityService.findByCharityTaxId("XXXXXXXXXX2");
    System.out.println(charityUMCAinDB);

    context.close();
  }

  private static Charity createCharity() {
    Category categoryZisize = new Category("DISABILITIES");
    System.out.println("Created new Category: " + categoryZisize);

    Charity charityZisize =
        new Charity(
            "4760176232",
            "Zisize Care Centre ",
            "Zisize is a care centre for the disabled situated at "
                + "Dingaanstat mission at Makhosini Valley near Ulundi.",
            "https://www.facebook.com/ZISIZE/?ref=page_internal",
            "https://www.facebook.com/ZISIZE/?ref=page_internal",
            "N/A");

    charityZisize.setCharityCategory(categoryZisize);
    System.out.println("Created new Charity: " + charityZisize);
    return charityZisize;
  }
}
