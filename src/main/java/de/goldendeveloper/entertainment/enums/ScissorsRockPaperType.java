package de.goldendeveloper.entertainment.enums;

import de.goldendeveloper.entertainment.discord.commands.ScissorsRockPaper;

import java.util.List;

public enum ScissorsRockPaperType {
    SCISSORS("Schere", "scissors"),
    STONE("Stein", "stone" ),
    PAPER("Papier", "paper");

    private final String name;
    private final String value;

    ScissorsRockPaperType(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static List<ScissorsRockPaperType> getAllScissorsRockPaperTypes() {
        return List.of(ScissorsRockPaperType.values());
    }

    public static ScissorsRockPaperType getScissorsRockPaperType(String value) {
        return getAllScissorsRockPaperTypes().stream().filter(type -> type.getValue().equalsIgnoreCase(value)).findFirst().orElse(null);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
