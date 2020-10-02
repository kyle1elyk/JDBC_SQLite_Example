import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Scanner;


public class JdbcSqliteRunner {

    // Create EL Diagram

    public static void main(String[] args) throws ClassNotFoundException {

        // Ensure we have the sqlite driver. Maven should handle it, otherwise this will throw ClassNotFound
        Class.forName("org.sqlite.JDBC");

        createTableIfNotExist();

        /*LiteLog.log("This is not an alert");
        LiteLog.log("Warning", LiteLog.Level.WARNING);
        LiteLog.log("Error!", LiteLog.Level.ERROR);*/

        Scanner stdin = new Scanner(System.in);
        System.out.println("Enter text into the log. (EOF to end)\r\n");
        System.out.print("Log > ");
        while (stdin.hasNextLine()) {
            LiteLog.log(stdin.nextLine());
            System.out.println(LiteLog.getLastMessage());
            System.out.println();


            System.out.print("Log > ");
        }
        stdin.close();
        System.out.println();

        // Prints all the messages from the log
        Arrays.stream(LiteLog.getMessages()).forEach(System.out::println);
    }

    /**
     * Initiates a sqlite connection to the `log.db` file and, if it does not exist, sets the database up with the
     * id, message, posted, and message_level columns.
     */
    private static void createTableIfNotExist() { createTableIfNotExist(false); }

    /**
     * Initiates a sqlite connection to the `log.db` file and, if it does not exist, sets the database up with the
     * id, message, posted, and message_level columns.
     * @param reset will remove the existing database if present
     */
    private static void createTableIfNotExist(final boolean reset) {
        // initiate connection. This fails if the sqlite driver is not found!
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:log.db")) {

            Statement statement = conn.createStatement();

            if (reset) statement.executeUpdate("DROP TABLE IF EXISTS `log`");

            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS `log`" +
                            "(id INTEGER PRIMARY KEY," +
                            "message TEXT," +
                            "posted INTEGER," +
                            "message_level INTEGER)");

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
