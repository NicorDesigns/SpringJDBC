package com.nicordesigns;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "hello", value = "/hello")
public class HelloServlet extends HttpServlet {

  private String message;

  public void init() {}

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    WebApplicationContext webApplicationContext =
        WebApplicationContextUtils.findWebApplicationContext(getServletContext());

    message =
        "Hello : the MariaDB Catalog name is: " + DBUtil.getCatalogName(webApplicationContext);

    PrintWriter out = response.getWriter();
    out.println("<h1>" + message + "</h1>");
  }

  public void destroy() {}
}
