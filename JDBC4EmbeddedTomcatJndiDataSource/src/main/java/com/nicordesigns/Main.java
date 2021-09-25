package com.nicordesigns;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.WebResourceSet;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.EmptyResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.tomcat.util.descriptor.web.ContextResource;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

  public static void main(String[] args) throws Exception {

    // 1. Set up property values that we will use later
    System.setProperty("org.apache.catalina.startup.EXIT_ON_INIT_FAILURE", "true");
    System.setProperty("PORT", "8080");
    System.setProperty("temp-path", "tomcat-base-dir");
    System.setProperty("additional-web-inf-path", "target/classes");

    // 2. Get the root folder of where we are running Main
    File mainRootFolder = getMainRootFolder();
    System.out.println("Application resolved root folder: " + mainRootFolder.getAbsolutePath());

    // 3. Instantiate Tomcat and set its base directory to a temporary created path
    Tomcat tomcat = new Tomcat();
    Path tempPath = getTempPath(System.getenv("temp-path"));
    tomcat.setBaseDir(tempPath.toString());

    // 4. Set the value of the web port 8080 from a previously defined environment variable
    String webPort = getWebPort(System.getenv("PORT"));
    tomcat.setPort(Integer.parseInt(webPort));

    // 5. Get the location of our Web Application Content Folder from our root folder
    File webContentFolder = getWebContentFolder(mainRootFolder);
    System.out.println("Configuring web app with basedir: " + webContentFolder.getAbsolutePath());

    // 6. Set the Standard Context for our instance of Tomcat
    StandardContext standardContext = getStandardContext(tomcat, webContentFolder);
    System.out.println("Standard Context Path: " + standardContext.getPath());

    // 7. Get the Web Resource Root for our Tomcat Application and add it to the Standard Context
    WebResourceRoot webResourceRoot = updateWebResourceRoot(mainRootFolder, standardContext);
    standardContext.setResources(webResourceRoot);

    // 8. Enable Naming for JNDI
    tomcat.enableNaming();

    // 9. Add MariaDB as a JNDI resource
    addJNDIMariaDBResource(standardContext);

    // 10. Start Tomcat
    tomcat.start();
    tomcat.getServer().await();
  }

  private static void addJNDIMariaDBResource(StandardContext standardContext) {

    ContextResource mariaDBContextReSource = new ContextResource();
    mariaDBContextReSource.setName("jdbc/charityDB");
    mariaDBContextReSource.setAuth("Container");
    mariaDBContextReSource.setType("javax.sql.DataSource");
    mariaDBContextReSource.setProperty("driverClassName", "org.mariadb.jdbc.Driver");
    mariaDBContextReSource.setProperty(
        "url", "jdbc:mariadb://localhost:3306/charityDB?user=root&password=secret");
    mariaDBContextReSource.setProperty("factory", "org.apache.tomcat.jdbc.pool.DataSourceFactory");
    mariaDBContextReSource.setProperty("user", "root");
    mariaDBContextReSource.setProperty("password", "secret");

    standardContext.getNamingResources().addResource(mariaDBContextReSource);
  }

  private static WebResourceRoot updateWebResourceRoot(
      File mainRootFolder, StandardContext standardContext) {

    // Declare an alternative location for your "WEB-INF/classes" dir Servlet 3.0 annotation will
    // work
    File additionWebInfClassesFolder = new File(mainRootFolder.getAbsolutePath(), "target/classes");

    return updateWebResourceRoot(standardContext, additionWebInfClassesFolder);
  }

  private static WebResourceRoot updateWebResourceRoot(
      StandardContext ctx, File additionWebInfClassesFolder) {
    WebResourceRoot resources = new StandardRoot(ctx);

    WebResourceSet webResourceSet;

    webResourceSet = addWebResourceSetToWebResourceRoot(additionWebInfClassesFolder, resources);

    resources.addPreResources(webResourceSet);
    return resources;
  }

  private static WebResourceSet addWebResourceSetToWebResourceRoot(
      File additionWebInfClassesFolder, WebResourceRoot webResourceRoot) {
    WebResourceSet webResourceSet;
    if (additionWebInfClassesFolder.exists()) {
      webResourceSet =
          new DirResourceSet(
              webResourceRoot,
              "/WEB-INF/classes",
              additionWebInfClassesFolder.getAbsolutePath(),
              "/");
      System.out.println(
          "loading WEB-INF webResourceRoot from as '"
              + additionWebInfClassesFolder.getAbsolutePath()
              + "'");
    } else {
      webResourceSet = new EmptyResourceSet(webResourceRoot);
    }
    return webResourceSet;
  }

  private static StandardContext getStandardContext(Tomcat tomcat, File webContentFolder)
      throws ServletException {

    StandardContext ctx =
        (StandardContext) tomcat.addWebapp("", webContentFolder.getAbsolutePath());
    ctx.setParentClassLoader(Main.class.getClassLoader());
    return ctx;
  }

  private static File getWebContentFolder(File mainRootFolder) throws IOException {

    File webContentFolder = new File(mainRootFolder.getAbsolutePath(), "src/main/webapp/");
    if (!webContentFolder.exists()) {
      webContentFolder = Files.createTempDirectory("default-doc-base").toFile();
    }
    return webContentFolder;
  }

  private static String getWebPort(String webPort) {

    if (webPort == null || webPort.isEmpty()) {
      webPort = "8080";
    }
    return webPort;
  }

  private static Path getTempPath(String tempSysPath) throws IOException {
    if (tempSysPath == null || !tempSysPath.isEmpty()) {
      tempSysPath = "tomcat-base-dir";
    }

    return Files.createTempDirectory(tempSysPath);
  }

  private static File getMainRootFolder() {

    try {
      File root;
      String runningJarPath =
          Main.class
              .getProtectionDomain()
              .getCodeSource()
              .getLocation()
              .toURI()
              .getPath()
              .replaceAll("\\\\", "/");

      int lastIndexOf = runningJarPath.lastIndexOf("/target/");

      if (lastIndexOf < 0) {
        root = new File("");
      } else {
        root = new File(runningJarPath.substring(0, lastIndexOf));
      }
      return root;

    } catch (URISyntaxException ex) {
      throw new RuntimeException(ex);
    }
  }
}
