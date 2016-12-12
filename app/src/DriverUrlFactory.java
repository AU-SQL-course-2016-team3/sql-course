public final class DriverUrlFactory {

    private static final String POSTGRES_DRIVER = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=foobar";
    private static final String MYSQL_DRIVER = "jdbc:mysql://localhost:3306/mysql?user=root&password=foobar";

    public static String getDriverUrl(SupportedDrivers driver) {
        if (driver == SupportedDrivers.MYSQL) {
            return MYSQL_DRIVER;
        } else if (driver == SupportedDrivers.POSTGRESQL) {
            return POSTGRES_DRIVER;
        } else {
            throw new IllegalArgumentException("???");
        }
    }

    public enum SupportedDrivers {
        POSTGRESQL,
        MYSQL;
    }

}
