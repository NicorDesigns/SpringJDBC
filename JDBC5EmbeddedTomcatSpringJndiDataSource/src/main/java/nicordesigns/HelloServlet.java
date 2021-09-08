package nicordesigns;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "hello", value = "/hello")
public class HelloServlet extends HttpServlet {
  private String message;

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    String catalogName = DBUtil.getCatalogName();

    ServletOutputStream out = response.getOutputStream();
    out.write("servlet says hello - ".getBytes());
    out.write(catalogName.getBytes());
    out.write("/n".getBytes());
    out.flush();
    out.close();
  }

  public void destroy() {}
}