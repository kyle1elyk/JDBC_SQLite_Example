import java.sql.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class LiteLog {

    /*
        Protocol:          jdbc
        Database type:     sqlite
        Database location: log.db

        variations:
        jdbc:mysql:serverx.com
        jdbc:oracle:serverz.com
    */
    protected static final String DB_CONNECTION_URL = "jdbc:sqlite:log.db";

    public enum Level {
        /** Low priority */
        INFO,
        /** Medium priority */
        WARNING,
        /** High priority */
        ERROR;

        private final int value;

        Level() {
            this.value = ordinal();
        }

        private static Level get(int level) throws IllegalArgumentException{
            try {
                return Level.values()[level];
            } catch (ArrayIndexOutOfBoundsException aioobe) {
                throw new IllegalArgumentException("Unknown level: "+ level);
            }
        }
    }

    /**
     * An object for containing the data from a row in the db
     */
    public static class Message {
        final long id;
        final String message;
        /** Stored in UTC, derived from epoch seconds in db */
        final Instant posted;
        final Level level;

        private Message (long id, String message, long posted, int level) {
            this.id = id;
            this.message = message;
            this.posted = Instant.ofEpochSecond(posted);
            this.level = Level.get(level);
        }

        /**
         * Formats the message element in the form `[yyyy-MM-dd HH:mm:ss] [{@link Level}]: message`
         * @return the formatted message element
         */
        @Override
        public String toString() {
            // Takes epoch Instance (UTC) and converts to local time, and formats to make readable
            String formattedTime = ZonedDateTime.ofInstant(this.posted, ZoneId.systemDefault())
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            return String.format("[%s] [%s]: %s", formattedTime, this.level, this.message);
        }
    }

    /**
     * Records a new message in the db as {@link Level#INFO}
     * @param message String to be recorded in the db
     * @return success of log into the db
     */
    public static boolean log(final String message) { return log(message, Level.INFO); }

    /**
     * Records a new message in the db at a specified {@link Level}
     * @param message String to be recorded in the db
     * @param level Level of the specified message
     * @return success of log into the db
     */
    public static boolean log(final String message, final Level level) {

        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL)) {

            Instant now = Instant.now();
            long epoch = now.getEpochSecond();


            PreparedStatement insertStatement = conn.prepareStatement(
                    "INSERT INTO log(message, posted, message_level) VALUES (?, ?, ?)"
            );

            insertStatement.setString(1, message);
            insertStatement.setLong(2, epoch);
            insertStatement.setInt(3, level.value);

            insertStatement.execute();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Returns all messages in the db
     * @return An array containing each message element from the db
     */
    public static Message[] getMessages() {
        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL)) {

            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT * FROM log"
            );

            ArrayList<Message> messages = new ArrayList<>();

            while (rs.next()) {
                Message m = new Message(
                        rs.getLong("id"),
                        rs.getString("message"),
                        rs.getLong("posted"),
                        rs.getInt("message_level"));

                messages.add(m);
            }
            rs.close();


            return messages.toArray(new Message[0]);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return new Message[0];
        }
    }

    /**
     * Returns the Message element for the last message in the db by id
     * @return Last message in database, null if empty
     */
    public static Message getLastMessage() {

        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL)) {
            Statement selectAll = conn.createStatement();
            ResultSet rs = selectAll.executeQuery(
                    "SELECT * FROM log ORDER BY id DESC LIMIT 1"
            );


            Message msg = null;
            while (rs.next()) {
                msg = new Message(
                        rs.getLong("id"),
                        rs.getString("message"),
                        rs.getLong("posted"),
                        rs.getInt("message_level"));
            }
            rs.close();

            return msg;

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }

    }
}
