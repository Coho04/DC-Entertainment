package de.goldendeveloper.entertainment.enums;

import de.goldendeveloper.entertainment.Main;
import io.sentry.Sentry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

    EntertainmentType(String name, String value, String article) {
        this.name = name;
        this.value = value;
        this.article = article;
    }

    public String getName() {
        return name;
    }

    public String getNameWithArticle() {
        return article + " " + name;
    }

    public String getValue() {
        return value;
    }

    public static List<EntertainmentType> getAllEntertainmentTypes() {
        return List.of(EntertainmentType.values());
    }

    public String getItem() {
        try (Connection connection = Main.getMysql().getSource().getConnection()) {
            String selectQuery = "SELECT name FROM ? GROUP BY name ORDER BY RAND() LIMIT 1;";
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            statement.execute("USE `GD-Entertainment`");
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

    public static EntertainmentType getEntertainmentType(String value) {
        return getAllEntertainmentTypes().stream().filter(type -> type.getValue().equalsIgnoreCase(value)).findFirst().orElse(null);
    }
}
