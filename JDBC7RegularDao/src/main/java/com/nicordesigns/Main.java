package com.nicordesigns;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;

public class Main {

  private DataSource dataSource;

  public static void main(String[] args) {

    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
    Main main = new Main();

    DataSource dataSource = context.getBean(DataSource.class);
    System.out.println(dataSource);
    main.setDataSource(dataSource);

    //        transactionMain.insertCharity(
    //                "4760176232",
    //                "Zisize Care Centre ",
    //                "Zisize is a care centre for the disabled situated at Dingaanstat mission at
    // Makhosini Valley\n"
    //                        + "    // near Ulundi.",
    //                "https://www.facebook.com/ZISIZE/?ref=page_internal",
    //                "https://www.facebook.com/ZISIZE/?ref=page_internal",
    //                "N/A");

    //        VehicleDao vehicleDao = (VehicleDao) context.getBean("vehicleDao");
    //        Vehicle vehicle = new Vehicle("EX0001", "Green", 4, 4);
    //        vehicleDao.insert(vehicle);

  }

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }
}
