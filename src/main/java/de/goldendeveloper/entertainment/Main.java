package de.goldendeveloper.entertainment;

import de.goldendeveloper.dcbcore.DCBot;
import de.goldendeveloper.dcbcore.DCBotBuilder;
import de.goldendeveloper.dcbcore.interfaces.CommandInterface;
import de.goldendeveloper.entertainment.discord.CustomEvents;
import de.goldendeveloper.entertainment.discord.commands.*;
import de.goldendeveloper.entertainment.util.AudioPlayerHelper;

import java.util.LinkedList;

public class Main {

    private static MysqlConnection mysqlConnection;
    private static  CustomConfig customConfig;
    private static DCBot dcBot;
    private static AudioPlayerHelper audioPlayerHelper;


    public static void main(String[] args) {
        customConfig = new CustomConfig();
        DCBotBuilder dcBotBuilder = new DCBotBuilder(args, true);
        dcBotBuilder.registerCommands(registerCommands());
        dcBotBuilder.registerEvents(new CustomEvents());
        dcBot = dcBotBuilder.build();
        audioPlayerHelper = new AudioPlayerHelper();
        mysqlConnection = new MysqlConnection(customConfig.getMysqlHostname(), customConfig.getMysqlUsername(), customConfig.getMysqlPassword(), customConfig.getMysqlPort());
    }

    private static LinkedList<CommandInterface> registerCommands() {
        LinkedList<CommandInterface>  commands = new LinkedList<>();
        commands.add(new CountingGame());
        commands.add(new DeleteCountingGame());
        commands.add(new Entertainment());
        commands.add(new Pause());
        commands.add(new Play());
        commands.add(new Resume());
        commands.add(new ScissorsRockPaper());
        commands.add(new Skip());
        commands.add(new Stop());
        commands.add(new Volume());
        commands.add(new YtSearch());
        return commands;
    }

    public static MysqlConnection getMysqlConnection() {
        return mysqlConnection;
    }

    public static CustomConfig getCustomConfig() {
        return customConfig;
    }

    public static DCBot getDcBot() {
        return dcBot;
    }

    public static AudioPlayerHelper getAudioPlayerHelper() {
        return audioPlayerHelper;
    }
}
