package nicordesigns;

import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Objects;

public class DBUtil {

  private static Connection createConnection(WebApplicationContext ctx) {
    try {
      DataSource ds = (DataSource) ctx.getBean("dataSource");
      return ds.getConnection();
    } catch (Exception exc) {
      exc.printStackTrace();
      return null;
    }
  }

  public static String getCatalogName(WebApplicationContext ctx) {

    Connection conn = createConnection(ctx);
    String catalogName;
    try {
      catalogName = Objects.requireNonNull(conn).getCatalog();
      conn.close();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return catalogName;
  }
}
