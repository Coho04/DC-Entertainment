package io.github.coho04.entertainment.enums;

import io.github.coho04.entertainment.Main;
import io.sentry.Sentry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * This enum represents different types of entertainment.
 * Each enum value has a name, a value, and an article.
 */
public enum EntertainmentType {

    MOVIE("Film", "movie", "den"),
    SERIES("Serie", "series", "die"),
    GAMES("Game", "games", "das"),
    JOKES("Jokes", "jokes", "den"),
    FACTS("Fakt", "facts", "der"),
    EIGHTBALL("EightBall", "eightball", "das");

    private final String name;
    private final String value;
    private final String article;

    /**
     * Constructor for the EntertainmentType enum.
     * It initializes the name, value, and article for the enum value.
     * @param name The name of the entertainment type.
     * @param value The value of the entertainment type.
     * @param article The article of the entertainment type.
     */
    EntertainmentType(String name, String value, String article) {
        this.name = name;
        this.value = value;
        this.article = article;
    }

    /**
     * This method returns the name of the entertainment type.
     * @return The name of the entertainment type.
     */
    public String getName() {
        return name;
    }

    /**
     * This method returns the name of the entertainment type with the article.
     * @return The name of the entertainment type with the article.
     */
    public String getNameWithArticle() {
        return article + " " + name;
    }

    /**
     * This method returns the value of the entertainment type.
     * @return The value of the entertainment type.
     */
    public String getValue() {
        return value;
    }

    /**
     * This method returns a list of all entertainment types.
     * @return A list of all entertainment types.
     */
    public static List<EntertainmentType> getAllEntertainmentTypes() {
        return List.of(EntertainmentType.values());
    }

    /**
     * This method retrieves an item from the database for the entertainment type.
     * It connects to the database, executes a select query, and returns the name of the item.
     * If an SQLException occurs, it prints the exception message and sends the exception to Sentry.
     * @return The name of the item, or null if an SQLException occurs.
     */
    public String getItem() {
        try (Connection connection = Main.getMysql().getSource().getConnection()) {
            String selectQuery = "SELECT name FROM ? GROUP BY name ORDER BY RAND() LIMIT 1;";
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            statement.execute("USE `" + Main.getCustomConfig().getMysqlDatabase() + "`;");
            statement.setString(1, value);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            Sentry.captureException(exception);
        }
        return null;
    }

    /**
     * This method returns the entertainment type that matches the given value.
     * It filters the list of all entertainment types by the value and returns the first match.
     * @param value The value to match.
     * @return The matching entertainment type, or null if no match is found.
     */
    public static EntertainmentType getEntertainmentType(String value) {
        return getAllEntertainmentTypes().stream().filter(type -> type.getValue().equalsIgnoreCase(value)).findFirst().orElse(null);
    }
}
