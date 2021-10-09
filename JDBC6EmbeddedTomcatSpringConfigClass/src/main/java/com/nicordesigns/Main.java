package com.nicordesigns;

import com.nicordesigns.server.ServerConfig;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

  private static final Logger log = Logger.getLogger(Main.class);

  public static void main(String[] args) {

    System.setProperty("org.apache.catalina.startup.EXIT_ON_INIT_FAILURE", "true");
    System.setProperty("PORT", "8080");
    System.setProperty("temp-path", "tomcat-base-dir");
    System.setProperty("additional-web-inf-path", "target/classes");

    AnnotationConfigApplicationContext ctx;
    try {

      ctx = new AnnotationConfigApplicationContext();
      ctx.register(ServerConfig.class);

      ctx.refresh();
      ctx.start();

    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }
}
