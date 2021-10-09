package com.nicordesigns.server;

import com.nicordesigns.Main;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.WebResourceSet;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.EmptyResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

@Component
public class EmbeddedTomcat {

  private static final Logger log = org.apache.log4j.Logger.getLogger(EmbeddedTomcat.class);

  private final Tomcat tomcat = new Tomcat();

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
      log.info(
          "loading WEB-INF webResourceRoot from as '"
              + additionWebInfClassesFolder.getAbsolutePath()
              + "'");
    } else {
      webResourceSet = new EmptyResourceSet(webResourceRoot);
    }
    return webResourceSet;
  }

  private static WebResourceRoot updateWebResourceRoot(
      StandardContext ctx, File additionWebInfClassesFolder) {
    WebResourceRoot resources = new StandardRoot(ctx);

    WebResourceSet webResourceSet;

    webResourceSet = addWebResourceSetToWebResourceRoot(additionWebInfClassesFolder, resources);

    resources.addPreResources(webResourceSet);
    return resources;
  }

  private static WebResourceRoot updateWebResourceRoot(File root, StandardContext ctx) {
    // Declare an alternative location for your "WEB-INF/classes" dir Servlet 3.0 annotation will
    // work
    File additionWebInfClassesFolder = new File(root.getAbsolutePath(), "target/classes");

    return updateWebResourceRoot(ctx, additionWebInfClassesFolder);
  }

  private static StandardContext getStandardContext(Tomcat tomcat, File webContentFolder)
      throws ServletException {
    StandardContext ctx =
        (StandardContext) tomcat.addWebapp("", webContentFolder.getAbsolutePath());
    ctx.setParentClassLoader(Main.class.getClassLoader());
    return ctx;
  }

  private static File getWebContentFolder(File root) throws IOException {
    File webContentFolder = new File(root.getAbsolutePath(), "src/main/webapp/");
    if (!webContentFolder.exists()) {
      webContentFolder = Files.createTempDirectory("default-doc-base").toFile();
    }
    return webContentFolder;
  }

  private static Path getTempPath(String tempSysPath) throws IOException {

    if (tempSysPath == null || !tempSysPath.isEmpty()) {
      tempSysPath = "tomcat-base-dir";
    }

    return Files.createTempDirectory(tempSysPath);
  }

  private static String getWebPort(String webPort) {

    if (webPort == null || webPort.isEmpty()) {
      webPort = "8080";
    }
    return webPort;
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

  @PostConstruct
  public void start() throws Exception {

    System.setProperty("org.apache.catalina.startup.EXIT_ON_INIT_FAILURE", "true");
    String webPort = getWebPort(System.getenv("PORT"));
    tomcat.setPort(Integer.parseInt(webPort));

    File mainRootFolder = getMainRootFolder();
    log.info("Application resolved root folder: " + mainRootFolder.getAbsolutePath());
    Path tempPath = getTempPath(System.getenv("temp-path"));
    tomcat.setBaseDir(tempPath.toString());

    File webContentFolder = getWebContentFolder(mainRootFolder);
    log.info("Configuring web app with basedir: " + webContentFolder.getAbsolutePath());

    StandardContext standardContext = getStandardContext(tomcat, webContentFolder);
    log.info("Standard Context Path: " + standardContext.getPath());

    WebResourceRoot webResourceRoot = updateWebResourceRoot(mainRootFolder, standardContext);
    standardContext.setResources(webResourceRoot);

    tomcat.start();
    log.info("Tomcat Server Started at " + new Date());
    tomcat.getServer().await();
  }

  @PreDestroy
  public void stop() throws LifecycleException {
    tomcat.stop();
  }
}
