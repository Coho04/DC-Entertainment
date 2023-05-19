package de.goldendeveloper.entertainment.enums;


import de.goldendeveloper.entertainment.Main;
import de.goldendeveloper.entertainment.MysqlConnection;
import de.goldendeveloper.mysql.entities.Table;

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

    public Table getMysqlTable() {
        return Main.getMysqlConnection().getMysql().getDatabase(MysqlConnection.dbName).getTable(name);
    }

    public String getItem() {
        return getMysqlTable().getColumn(MysqlConnection.columnName).getRandom().toString();
    }

    public static EntertainmentType getEntertainmentType(String value) {
        return getAllEntertainmentTypes().stream().filter(type -> type.getValue().equalsIgnoreCase(value)).findFirst().orElse(null);
    }
}
