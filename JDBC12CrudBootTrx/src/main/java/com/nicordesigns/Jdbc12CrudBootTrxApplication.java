package com.nicordesigns;

import com.nicordesigns.model.Category;
import com.nicordesigns.model.Charity;
import com.nicordesigns.model.Program;
import com.nicordesigns.service.CharityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
public class Jdbc12CrudBootTrxApplication implements CommandLineRunner {

  private static final Logger logger = LoggerFactory.getLogger(Jdbc12CrudBootTrxApplication.class);

  @Autowired private CharityService charityService;

  public static void main(String[] args) {
    SpringApplication.run(Jdbc12CrudBootTrxApplication.class, args);
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
    logger.info("Created new Category: " + categoryZisize);

    charityZisize.setCharityCategory(categoryZisize);

    List<Program> programList = new ArrayList<>();
    programList.add(new Program("Residential facility"));
    programList.add(new Program("Social Work offices for psycho-social services"));
    programList.add(new Program("Protective workshops"));
    programList.add(new Program("Home based care"));

    charityZisize.setCharityPrograms(programList);
    logger.info("Created new Charity: " + charityZisize);

    return charityZisize;
  }

  @Override
  public void run(String... args) throws Exception {
    Charity charityZisize = createCharity();

    int charityId = charityService.createCharity(charityZisize);
    logger.info("charityZisizeDB inserted : DB Generated charityId: " + charityId);

    Charity charityZisizeDB = charityService.findByCharityTaxId(charityZisize.getCharityTaxId());
    logger.info("charityZisizeDB retrieved : " + charityZisizeDB);

    // Update Charity Category with a new Category Type
    Category disabilitiesUpdate = new Category("DISABILITIES UPDATE");
    charityZisize.setCharityCategory(disabilitiesUpdate);

    var charityRowUpdated = charityService.update(charityZisize);
    logger.info("Charity Rows Updated : " + charityRowUpdated);

    Charity charityZisizeDB2 = charityService.findByCharityTaxId(charityZisize.getCharityTaxId());
    logger.info("Updated charityZisize in DB retrieved : " + charityZisizeDB2);

    // Update the Charity Category with an existing Category Type
    Category disabilitiesUpdateRegular = new Category("DISABILITIES");
    charityZisize.setCharityCategory(disabilitiesUpdateRegular);

    var charityRowUpdatedRegular = charityService.update(charityZisize);
    logger.info("Charity Rows Updated : " + charityRowUpdatedRegular);
    Charity charityZisizeDB3 = charityService.findByCharityTaxId(charityZisize.getCharityTaxId());
    logger.info("Updated charityZisize in DB retrieved : " + charityZisizeDB3);

    // Update the Charity with a different list of Programs
    charityZisizeDB.setCharityPrograms(addPrograms());
    var charityRowUpdatedRegular2 = charityService.update(charityZisizeDB);
    logger.info("Charity Rows Updated : " + charityRowUpdatedRegular2);
    Charity charityZisizeDB4 = charityService.findByCharityTaxId(charityZisize.getCharityTaxId());
    logger.info("Updated charityZisize in DB retrieved : " + charityZisizeDB4);

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
    logger.info(charityYMCA + " inserted : DB Generated charityId: " + ymcaCharityId);

    var charityYMCADB1 = charityService.findByCharityTaxId("XXXXXXXXXX1");
    logger.info(charityYMCADB1.toString());

    // Delete the Charity in the DB
    var deleteCharityRowCount = charityService.delete(charityYMCADB1);
    logger.info("Delete Charity Row-Count " + deleteCharityRowCount);
    var charityYMCADB2 = charityService.findByCharityTaxId("XXXXXXXXXX1");
    if (Objects.isNull(charityYMCADB2)) {
      logger.info("Charity has been deleted from DB " + charityYMCADB1);
    } else {
      logger.info("Charity has not been deleted from DB " + charityYMCADB2);
    }
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

    int charityUMCAId = charityService.createCharity(charityUMCA);
    logger.info(charityUMCA + " inserted : DB Generated charityId: " + charityUMCAId);

    Charity charityUMCADB = charityService.findByCharityTaxId("XXXXXXXXXX2");
    logger.info(charityUMCADB.toString());

    var charityList = Arrays.asList(charityZisize, charityYMCA, charityUMCADB);
    int[] insertResults = charityService.insertBatch(charityList);
    int i = 0;
    for (Charity charity : charityList) {
      logger.info(charity.toString());
      logger.info("int result : " + insertResults[i++]);
    }

    // TODO Fix insertbatch and findall
    Assert.isTrue(
        charityService.findAllCharities().size() == 3, "First booking should work with no problem");
    logger.info("Alice, Bob and Carol have been booked");

    for (Charity charity : charityService.findAllCharities()) {
      logger.info("Charity Retrieved from DB : " + charity + "/n");
    }
    //        logger.info("You shouldn't see Chris or Samuel. Samuel violated DB constraints, " +
    //            "and Chris was rolled back in the same TX");
    //        Assert.isTrue(CharityService.findAllBookings().size() == 3,
    //            "'Samuel' should have triggered a rollback");
    //
    //        try {
    //          CharityService.book("Buddy", null);
    //        } catch (RuntimeException e) {
    //          logger.info("v--- The following exception is expect because null is not " +
    //              "valid for the DB ---v");
    //          logger.error(e.getMessage());
    //        }
    //
    //        for (String person : CharityService.findAllBookings()) {
    //          logger.info("So far, " + person + " is booked.");
    //        }
    //        logger.info("You shouldn't see Buddy or null. null violated DB constraints, and " +
    //            "Buddy was rolled back in the same TX");
    //        Assert.isTrue(CharityService.findAllBookings().size() == 3,
    //            "'null' should have triggered a rollback");

  }
}
