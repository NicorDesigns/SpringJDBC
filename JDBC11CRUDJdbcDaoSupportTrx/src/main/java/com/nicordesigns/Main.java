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

    // TODO Demo Step 1 : Update and retrieve a Charity from the DB with one to one Category
    // relationship
    Charity charityZisize = createZisizeCharity();

    int charityId = charityService.createCharity(charityZisize);
    System.out.println("charityZisizeDB inserted : DB Generated charityId: " + charityId);

    Charity charityZisizeDB = charityService.findByCharityTaxId(charityZisize.getCharityTaxId());
    System.out.println("charityZisizeDB retrieved : " + charityZisizeDB);

    // 1. (a) Update Charity Category with a new Category Type
    Category disabilitiesUpdate = new Category("DISABILITIES UPDATE");
    charityZisize.setCharityCategory(disabilitiesUpdate);

    var charityRowUpdated = charityService.update(charityZisize);
    System.out.println("Charity Rows Updated : " + charityRowUpdated);

    Charity charityZisizeDB2 = charityService.findByCharityTaxId(charityZisize.getCharityTaxId());
    System.out.println("Updated charityZisize in DB retrieved : " + charityZisizeDB2);

    // 1. (b) Update the Charity Category with an existing Category Type
    Category disabilitiesUpdateRegular = new Category("DISABILITIES");
    charityZisize.setCharityCategory(disabilitiesUpdateRegular);

    var charityRowUpdatedRegular = charityService.update(charityZisize);
    System.out.println("Charity Rows Updated : " + charityRowUpdatedRegular);
    Charity charityZisizeDB3 = charityService.findByCharityTaxId(charityZisize.getCharityTaxId());
    System.out.println("Updated charityZisize in DB retrieved : " + charityZisizeDB3);

    // TODO Demo Step 2. Update the Charity with a different list of Programs
    charityZisizeDB.setCharityPrograms(addPrograms());
    var charityRowUpdatedRegular2 = charityService.update(charityZisizeDB);
    System.out.println("Charity Rows Updated : " + charityRowUpdatedRegular2);
    Charity charityZisizeDB4 = charityService.findByCharityTaxId(charityZisize.getCharityTaxId());
    System.out.println("Updated charityZisize in DB retrieved : " + charityZisizeDB4);

    // TODO Demo Step 3. Add a new Charity with a single Category and Multiple Programs to the DB
    // and return it
    Category categoryYMCA = new Category("COMMUNITY DEVELOPMENT");
    Charity charityYMCA =
        new Charity(
            "XXXXXXXXXX1",
            "YMCA South Africa ",
            "YMCA is the oldest Christian Youth Development organisation in the world"
                + "...reaching 65 million young people worldwide.  ",
            "https://www.saymca.org.za/",
            "https://www.facebook.com/YMCASouthAfrica/",
            "https://twitter.com/ymca_sa");
    charityYMCA.setCharityCategory(categoryYMCA);
    charityYMCA.setCharityPrograms(addYMCAPrograms());
    int ymcaCharityId = charityService.createCharity(charityYMCA);
    System.out.println(charityYMCA + " inserted : DB Generated charityId: " + ymcaCharityId);

    var charityYMCADB1 = charityService.findByCharityTaxId("XXXXXXXXXX1");
    System.out.println(charityYMCADB1);

    // TODO Step 4. Delete the Charity in the DB
    //    var deleteCharityRowCount = charityService.delete(charityYMCADB1);
    //    System.out.println("Delete Charity Row-Count " + deleteCharityRowCount);
    //    var charityYMCADB2 = charityService.findByCharityTaxId("XXXXXXXXXX1");
    //    if (Objects.isNull(charityYMCADB2)) {
    //      System.out.println("Charity has been deleted from DB " + charityYMCADB1);
    //    } else {
    //      System.out.println("Charity has not been deleted from DB " + charityYMCADB2);
    //    }

    // TODO Step 5. Add another charity  to the DB

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

    context.close();
  }

  private static List<Program> addYMCAPrograms() {
    List<Program> programList = new ArrayList<>();
    programList.add(new Program("Y Fit"));
    programList.add(new Program("Y Health"));
    return programList;
  }

  private static List<Program> addPrograms() {
    List<Program> programList = new ArrayList<>();
    programList.add(new Program("Residential facility"));
    programList.add(new Program("Social Work offices for psycho-social services"));
    programList.add(new Program("Overprotective workshops"));
    programList.add(new Program("Office based care"));
    return programList;
  }

  private static Charity createZisizeCharity() {

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
