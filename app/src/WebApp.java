// This class is a part of the 4th "sprint" of the database labs
// running in Saint-Petersburg Academic University in Fall'16
//
// Author: Dmitry Barashev
// License: WTFPL

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.support.DatabaseConnection;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static spark.Spark.get;
import static spark.Spark.port;

public class WebApp {

  /*
   * change `mysql` on `postgresql` if you want to change db
   */
  private static final DriverUrlFactory.SupportedDrivers DRIVER_TO_USE = DriverUrlFactory.SupportedDrivers.MYSQL;
  private static final String DRIVER_URL = DriverUrlFactory.getDriverUrl(DRIVER_TO_USE);

  private static JdbcConnectionSource createConnectionSource(String driver) {
    try {
      JdbcConnectionSource connectionSource = new JdbcConnectionSource(driver);
      connectionSource.getReadWriteConnection(null).setAutoCommit(false);
      return connectionSource;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  private static Object runTxn(String isolationLevel, JdbcConnectionSource connectionSource, Callable<Object> code)
      throws SQLException, IOException {
    try (DatabaseConnection connection = connectionSource.getReadWriteConnection(null)) {
      Callable<Object> txnWrapper = () -> {
        connection.executeStatement(
            "set transaction isolation level " + isolationLevel, DatabaseConnection.DEFAULT_RESULT_FLAGS);
        Object result = code.call();
        connection.commit(null);
        return result;
      };
      return TransactionManager.callInTransaction(
          connection, false, new ProxyDatabaseType(connectionSource.getDatabaseType()), txnWrapper);
    }
  }

  private static Object getAllSportsmen(Request req, Response resp) throws IOException, SQLException {
    final JdbcConnectionSource connectionSource = createConnectionSource(DRIVER_URL);
    resp.type("text/plain");
    return runTxn("REPEATABLE READ", connectionSource, () -> {
      List<String> sportsmen = DaoManager.createDao(connectionSource, Sportsman.class)
          .queryForAll().stream()
          .map(Sportsman::toString).collect(Collectors.toList());
      return String.join("\n", sportsmen);
    });
  }

  private static Object newSportsman(Request req, Response resp) throws IOException, SQLException {
    final JdbcConnectionSource connectionSource = createConnectionSource(DRIVER_URL);

    Object success = runTxn("READ COMMITTED", connectionSource, () -> {
      List<Country> countries = DaoManager.createDao(connectionSource, Country.class)
          .queryForEq("name", countryCodeToCountry(req.queryMap("country").value()));

      if (countries.size() != 1) {
        return false;
      }

      Dao<Sportsman, Integer> sportsmenDao = DaoManager.createDao(connectionSource, Sportsman.class);

      Sportsman newSportsman = new Sportsman();
      newSportsman.setName(req.queryMap("name").value());
      newSportsman.setSex(req.queryMap("sex").value());
      newSportsman.setAge(req.queryMap("age").integerValue());
      newSportsman.setWeight(req.queryMap("weight").integerValue());
      newSportsman.setHeight(req.queryMap("height").integerValue());
      newSportsman.setCountryId(countries.get(0).getId());

      sportsmenDao.create(newSportsman);
      return true;
    });

    if (Boolean.TRUE.equals(success)) {
      resp.redirect("/sportsman/all");
      return null;
    }

    resp.status(500);
    return "Transaction failed";
  }

  public static void main(String[] args) throws Exception {
    port(8000);
    get("/sportsman/all", WebApp.withTry(WebApp::getAllSportsmen));
    get("/sportsman/new", WebApp.withTry(WebApp::newSportsman));
  }

  private static String countryCodeToCountry(String code) {
    final Locale l = new Locale("EN", code.toUpperCase()); // we work with english language
    return l.getDisplayCountry();
  }

  private static Route withTry(Route route) {
    return (req, resp) -> {
      try {
        return route.handle(req, resp);
      } catch (Throwable e) {
        e.printStackTrace();
        resp.status(500);
        return e.getMessage();
      }
    };
  }
}
