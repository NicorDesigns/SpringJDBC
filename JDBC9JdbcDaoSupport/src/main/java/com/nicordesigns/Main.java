package com.nicordesigns;

import com.nicordesigns.dao.CharityDao;
import com.nicordesigns.dao.JdbcTemplateCharityDaoImpl;
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

    CharityDao charityDao = context.getBean(JdbcTemplateCharityDaoImpl.class);
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

    int charityId = charityDao.insert(charityZisize);
    System.out.println(charityZisize + " inserted : DB Generated charityId: " + charityId);

    Charity charity1 = charityDao.findByCharityTaxId("4760176232");
    System.out.println(charity1);

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
    //
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

    //    var charityList = Arrays.asList(charityZisize, charityYMCA, charityUMCA);
    //    int[] insertResults = charityDao.insertBatch(charityList);
    //    int i = 0;
    //    for (Charity charity : charityList) {
    //      System.out.println(charity);
    //      System.out.println("int result : " + insertResults[i++]);
    //    }
  }
}
