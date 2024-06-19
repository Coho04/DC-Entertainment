package de.goldendeveloper.entertainment.enums;

import java.util.List;

/**
 * This enum represents the possible choices in a game of Scissors, Rock, Paper.
 * Each enum value has a name and a value.
 */
public enum ScissorsRockPaperType {

    SCISSORS("Schere", "scissors"),
    STONE("Stein", "stone"),
    PAPER("Papier", "paper");

    private final String name;
    private final String value;

    /**
     * Constructor for the ScissorsRockPaperType enum.
     * It initializes the name and value for the enum value.
     *
     * @param name  The name of the choice.
     * @param value The value of the choice.
     */
    ScissorsRockPaperType(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * This method returns a list of all possible choices.
     *
     * @return A list of all ScissorsRockPaperType values.
     */
    public static List<ScissorsRockPaperType> getAllScissorsRockPaperTypes() {
        return List.of(ScissorsRockPaperType.values());
    }

    /**
     * This method returns the choice that matches the given value.
     * It filters the list of all choices by the value and returns the first match.
     *
     * @param value The value to match.
     * @return The matching choice, or null if no match is found.
     */
    public static ScissorsRockPaperType getScissorsRockPaperType(String value) {
        return getAllScissorsRockPaperTypes().stream().filter(type -> type.getValue().equalsIgnoreCase(value)).findFirst().orElse(null);
    }

    /**
     * This method returns the name of the choice.
     *
     * @return The name of the choice.
     */
    public String getName() {
        return name;
    }

    /**
     * This method returns the value of the choice.
     *
     * @return The value of the choice.
     */
    public String getValue() {
        return value;
    }
}
