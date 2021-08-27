package com.nicordesigns;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
    name = "MyServlet",
    urlPatterns = {"/hello"})
public class HelloServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String catalogName = DatabaseUtil.getCatalogName();
    ServletOutputStream out = resp.getOutputStream();
    out.write("servlet says hello - ".getBytes());
    out.write(catalogName.getBytes());
    out.write("/n".getBytes());
    out.flush();
    out.close();
  }
}
