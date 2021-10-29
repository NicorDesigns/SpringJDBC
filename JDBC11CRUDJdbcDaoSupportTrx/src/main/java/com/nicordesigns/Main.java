package com.nicordesigns;

import com.nicordesigns.model.Category;
import com.nicordesigns.model.Charity;
import com.nicordesigns.model.Program;
import com.nicordesigns.service.CharityService;
import com.nicordesigns.service.CharityServiceImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

  public static void main(String[] args) throws SQLException {

    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

    CharityService charityService = context.getBean("charityService", CharityServiceImpl.class);

    Charity charityZisize = createCharity();

    int charityId = charityService.createCharity(charityZisize);
    System.out.println(charityZisize + " inserted : DB Generated charityId: " + charityId);

    Charity charityZisizeDB = charityService.findByCharityTaxId(charityZisize.getCharityTaxId());
    System.out.println(charityZisizeDB);

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

    //    Category categoryUSCA = new Category("RELIGION");
    //    Charity charityUMCA =
    //        new Charity(
    //            "XXXXXXXXXX2",
    //            "UMCA ",
    //            "UCSA is a voluntary, non-racial, multicultural, interdenominational Christian
    // organization.",
    //            "http://vcsv.co.za/",
    //            "https://www.facebook.com/UCSA.VCSV/",
    //            "https://twitter.com/VCSV_UCSA");
    //    charityUMCA.setCharityCategory(categoryUSCA);
    //
    //
    //    int charityId = charityService.createCharity(charityUMCA);
    //    System.out.println(charityUMCA + " inserted : DB Generated charityId: " + charityId);
    //
    //    Charity charity1 = charityService.findByCharityTaxId("XXXXXXXXXX2");
    //    System.out.println(charity1);

    //    var charityList = Arrays.asList(charityZisize, charityYMCA, charityUMCA);
    //    int[] insertResults = charityDao.insertBatch(charityList);
    //    int i = 0;
    //    for (Charity charity : charityList) {
    //      System.out.println(charity);
    //      System.out.println("int result : " + insertResults[i++]);
    //    }

    context.close();
  }

  private static Charity createCharity() {

    Charity charityZisize =
        new Charity(
            "4760176232",
            "Zisize Care Centre ",
            "Zisize is a care centre for the disabled situated at "
                + "Dingaanstat mission at Makhosini Valley near Ulundi.",
            "https://www.facebook.com/ZISIZE/?ref=page_internal",
            "https://www.facebook.com/ZISIZE/?ref=page_internal",
            "N/A");

    Category categoryZisize = new Category("DISABILITIES");
    System.out.println("Created new Category: " + categoryZisize);

    charityZisize.setCharityCategory(categoryZisize);

    List<Program> programList = new ArrayList<>();
    programList.add(new Program("Residential facility"));
    programList.add(new Program("Social Work offices for psycho-social services"));
    programList.add(new Program("Protective workshops"));
    programList.add(new Program("Home based care"));

    charityZisize.setCharityPrograms(programList);
    System.out.println("Created new Charity: " + charityZisize);

    return charityZisize;
  }
}
