package io.github.coho04.entertainment;

import io.github.coho04.entertainment.discord.commands.Entertainment;
import io.github.coho04.entertainment.discord.commands.games.EightBall;
import io.github.coho04.entertainment.discord.commands.games.ScissorsRockPaper;
import io.github.coho04.entertainment.discord.commands.music.*;
import io.github.coho04.entertainment.discord.CustomEvents;
import io.github.coho04.entertainment.discord.music.AudioPlayerHelper;
import io.github.coho04.dcbcore.DCBot;
import io.github.coho04.dcbcore.DCBotBuilder;

/**
 * This is the main class of the application.
 * It initializes the bot, registers the commands and events, and starts the bot.
 */
public class Main {

    private static MYSQL mysql;
    private static CustomConfig customConfig;
    private static DCBot dcBot;
    private static AudioPlayerHelper audioPlayerHelper;

    /**
     * The main method of the application.
     * It initializes the bot, registers the commands and events, and starts the bot.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        customConfig = new CustomConfig();
        DCBotBuilder dcBotBuilder = new DCBotBuilder(args, true);
        dcBotBuilder.registerCommands(new Entertainment(), new Pause(), new Play(), new Resume(), new ScissorsRockPaper(), new Skip(), new Stop(), new Volume(), new YtSearch(), new LeaveVoice(), new EightBall());
        dcBotBuilder.registerEvents(new CustomEvents());
        dcBotBuilder.setWithLavaLink(true);
        dcBot = dcBotBuilder.build();
        audioPlayerHelper = new AudioPlayerHelper();
        mysql = new MYSQL();
        System.out.println("Java application started successfully");
    }

    /**
     * This method returns the MySQL instance.
     *
     * @return The MySQL instance.
     */
    public static MYSQL getMysql() {
        return mysql;
    }

    /**
     * This method returns the custom configuration instance.
     *
     * @return The custom configuration instance.
     */
    public static CustomConfig getCustomConfig() {
        return customConfig;
    }

    /**
     * This method returns the bot instance.
     *
     * @return The bot instance.
     */
    public static DCBot getDcBot() {
        return dcBot;
    }

    /**
     * This method returns the audio player helper instance.
     *
     * @return The audio player helper instance.
     */
    public static AudioPlayerHelper getAudioPlayerHelper() {
        return audioPlayerHelper;
    }
}
