package de.goldendeveloper.entertainment.discord;

import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import de.goldendeveloper.entertainment.Main;
import de.goldendeveloper.entertainment.MysqlConnection;
import de.goldendeveloper.entertainment.Youtube;
import de.goldendeveloper.entertainment.discord.music.GuildMusicManager;
import de.goldendeveloper.mysql.entities.Column;
import de.goldendeveloper.mysql.entities.Database;
import de.goldendeveloper.mysql.entities.Row;
import de.goldendeveloper.mysql.entities.Table;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.ShutdownEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;
import java.util.*;

public class Events extends ListenerAdapter {

    public static final String movie = "movie";
    public static final String serien = "serien";
    public static final String jokes = "jokes";
    public static final String games = "games";
    public static final String eightBall = "eight-ball";
    public static final String fact = "facts";
    public static final String skip = "skip";
    public static final String firstLetter = "firstLetter";

    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;

    public Events() {
        this.musicManagers = new HashMap<>();
        this.playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    @Override
    public void onShutdown(@NotNull ShutdownEvent e) {
        WebhookEmbedBuilder embed = new WebhookEmbedBuilder();
        embed.setAuthor(new WebhookEmbed.EmbedAuthor(Main.getDiscord().getBot().getSelfUser().getName(), Main.getDiscord().getBot().getSelfUser().getAvatarUrl(), "https://Golden-Developer.de"));
        embed.addField(new WebhookEmbed.EmbedField(false, "[Status]", "OFFLINE"));
        embed.setColor(0xFF0000);
        embed.setFooter(new WebhookEmbed.EmbedFooter("@Golden-Developer", Main.getDiscord().getBot().getSelfUser().getAvatarUrl()));
        new WebhookClientBuilder(Main.getConfig().getDiscordWebhook()).build().send(embed.build());

        System.exit(0);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        String cmd = e.getName();
        User _Coho04_ = e.getJDA().getUserById("513306244371447828");
        User zRazzer = e.getJDA().getUserById("428811057700536331");
        if (cmd.equalsIgnoreCase(Discord.cmdEntertainment)) {
            MessageEmbed embed = new EmbedBuilder().setTitle("Wähle aus welches Entertainment Programm du haben möchtest!").build();
            e.getInteraction().replyEmbeds(embed).addActionRow(
                    Button.danger(movie, "Filme"),
                    Button.primary(serien, "Serien"),
                    Button.secondary(jokes, "Jokes"),
                    Button.success(games, "Games"),
                    Button.primary(fact, "Fakten")
            ).addActionRow(
                    Button.secondary(eightBall, "Eight-Ball"),
                    Button.link("https://www.youtube.com/watch?v=dQw4w9WgXcQ", "P*rno")
            ).queue();
        } else if (cmd.equalsIgnoreCase(Discord.cmdEmojiStart)) {
            if (e.isFromGuild()) {
                if (e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                    if (Main.getMysqlConnection().getMysql().existsDatabase(MysqlConnection.dbName)) {
                        Database db = Main.getMysqlConnection().getMysql().getDatabase(MysqlConnection.dbName);
                        if (db.existsTable(MysqlConnection.DiscordTable)) {
                            Table table = db.getTable(MysqlConnection.DiscordTable);
                            if (table.existsColumn(MysqlConnection.DiscordID)) {
                                Column column = table.getColumn(MysqlConnection.DiscordID);
                                if (column.getAll().contains(e.getGuild().getId())) {
                                    HashMap<String, Object> map = table.getRow(table.getColumn(MysqlConnection.DiscordID), e.getGuild().getId());
                                    if (map.containsKey(MysqlConnection.emojiGameChannelID)) {
                                        String Channel = map.get(MysqlConnection.emojiGameChannelID).toString();
                                        if (!Channel.isEmpty() && !Channel.isBlank()) {
                                            TextChannel channel = e.getGuild().getTextChannelById(Channel);
                                            if (channel != null) {
                                                channel.sendMessageEmbeds(Objects.requireNonNull(EmojiEmbed())).setActionRows(
                                                        ActionRow.of(
                                                                Button.danger(skip, "Überspringen"),
                                                                Button.primary(firstLetter, "Erster Buchstabe")
                                                        )
                                                ).queue();
                                            }
                                        }
                                    }
                                } else {
                                    Guild guild = e.getGuild();
                                    guild.createTextChannel("emoji-quiz").queue(channel -> {
                                        table.insert(new Row(table, table.getDatabase())
                                                .with(MysqlConnection.DiscordID, e.getGuild().getId())
                                                .with(MysqlConnection.emojiGameChannelID, channel.getId())
                                        );
                                    });
                                }
                            }
                        }
                    }
                }
            }
        } else if (cmd.equalsIgnoreCase(Discord.cmdGalgenStart)) {
            if (e.isFromGuild()) {
                if (e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                    if (Main.getMysqlConnection().getMysql().existsDatabase(MysqlConnection.dbName)) {
                        Database db = Main.getMysqlConnection().getMysql().getDatabase(MysqlConnection.dbName);
                        if (db.existsTable(MysqlConnection.DiscordTable)) {
                            Table table = db.getTable(MysqlConnection.DiscordTable);
                            if (table.existsColumn(MysqlConnection.DiscordID)) {
                                Column column = table.getColumn(MysqlConnection.DiscordID);
                                if (column.getAll().contains(e.getGuild().getId())) {
                                    HashMap<String, Object> map = table.getRow(table.getColumn(MysqlConnection.DiscordID), e.getGuild().getId());
                                    if (map.containsKey(MysqlConnection.emojiGameChannelID)) {
                                        String Channel = map.get(MysqlConnection.emojiGameChannelID).toString();
                                        if (!Channel.isEmpty() && !Channel.isBlank()) {
                                            TextChannel channel = e.getGuild().getTextChannelById(Channel);
                                            if (channel != null) {
                                                channel.sendMessageEmbeds(GalgenEmbed()).setActionRows(
                                                        ActionRow.of(
                                                                Button.danger(skip, "Überspringen"),
                                                                Button.primary(firstLetter, "Erster Buchstabe")
                                                        )
                                                ).queue();
                                            }
                                        }
                                    }
                                } else {
                                    Guild guild = e.getGuild();
                                    guild.createTextChannel("galgen-quiz").queue(channel -> {
                                        table.insert(new Row(table, table.getDatabase())
                                                .with(MysqlConnection.DiscordID, e.getGuild().getId())
                                                .with(MysqlConnection.galgenGameChannelID, channel.getId())
                                        );
                                    });
                                }
                            }
                        }
                    }
                }
            }
        } else if (cmd.equalsIgnoreCase(Discord.cmdHelp)) {
            List<Command> commands = Main.getDiscord().getBot().retrieveCommands().complete();
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("**Help Commands**");
            embed.setColor(Color.MAGENTA);
            embed.setFooter("@Golden-Developer", e.getJDA().getSelfUser().getAvatarUrl());
            for (Command cm : commands) {
                embed.addField("/" + cm.getName(), cm.getDescription(), true);
            }
            e.getInteraction().replyEmbeds(embed.build()).addActionRow(
                    Button.link("https://wiki.Golden-Developer.de/", "Online Übersicht"),
                    Button.link("https://support.Golden-Developer.de", "Support Anfragen")
            ).queue();
        } else if (e.getName().equalsIgnoreCase(Discord.getCmdShutdown)) {
            if (e.getUser() == zRazzer || e.getUser() == _Coho04_) {
                e.getInteraction().reply("Der Bot wird nun heruntergefahren").queue();
                e.getJDA().shutdown();
            } else {
                e.getInteraction().reply("Dazu hast du keine Rechte du musst für diesen Befehl der Bot inhaber sein!").queue();
            }
        } else if (e.getName().equalsIgnoreCase(Discord.getCmdRestart)) {
            if (e.getUser() == zRazzer || e.getUser() == _Coho04_) {
                try {
                    e.getInteraction().reply("Der Discord Bot wird nun neugestartet!").queue();
                    Process p = Runtime.getRuntime().exec("screen -AmdS GD-Entertainment java -Xms1096M -Xmx1096M -jar GD-Entertainment-1.0.jar");
                    p.waitFor();
                    e.getJDA().shutdown();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                e.getInteraction().reply("Dazu hast du keine Rechte du musst für diesen Befehl der Bot inhaber sein!").queue();
            }
        } else if (cmd.equalsIgnoreCase(Discord.cmdPlay)) {
            String TrackUrl = e.getOption(Discord.cmdPlayOptionTrack).getAsString();
            if (e.isFromGuild()) {
                loadAndPlay(e, TrackUrl);
            } else {
                e.reply("Dieser Command ist nur auf einem Server möglich!").queue();
            }
        } else if (cmd.equalsIgnoreCase(Discord.cmdYtsearch)) {
            String keyWord = e.getOption(Discord.cmdPlayOptionTrack).getAsString();
            Youtube youtube = new Youtube();
            youtube.setKeyWord(keyWord);
            String TrackUrl = youtube.execute();
            if (e.isFromGuild()) {
                loadAndPlay(e, "https://www.youtube.com/watch?v=" + TrackUrl);
            } else {
                e.reply("Dieser Command ist nur auf einem Server möglich!").queue();
            }
        } else if (cmd.equalsIgnoreCase(Discord.cmdSkip)) {
            if (e.isFromGuild()) {
                skipTrack(e);
            } else {
                e.reply("Dieser Command ist nur auf einem Server möglich!").queue();
            }
        } else if (cmd.equalsIgnoreCase(Discord.cmdStop)) {
            if (e.isFromGuild()) {
                stopTrack(e);
            } else {
                e.reply("Dieser Command ist nur auf einem Server möglich!").queue();
            }
        } else if (cmd.equalsIgnoreCase(Discord.cmdResume)) {
            if (e.isFromGuild()) {
                resumeTrack(e);
            } else {
                e.reply("Dieser Command ist nur auf einem Server möglich!").queue();
            }
        } else if (cmd.equalsIgnoreCase(Discord.cmdPause)) {
            if (e.isFromGuild()) {
                pauseTrack(e);
            } else {
                e.reply("Dieser Command ist nur auf einem Server möglich!").queue();
            }
        } else if (cmd.equalsIgnoreCase(Discord.cmdScissorsStonePapered)) {
            String wahl = e.getOption(Discord.cmdScissorsStonePaperedOptionObjekt).getAsString();
            List<String> options = new ArrayList<>();
            options.add("Schere");
            options.add("Stein");
            options.add("Papier");
            if (options.contains(wahl)) {
                String botWahl = options.get(new Random().nextInt(options.size()));
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle("**Schere Stein Papier**");
                embedBuilder.addField("Deine Auswahl:", wahl, false);
                embedBuilder.addField("Mein Auswahl:", botWahl, false);
                if (wahl.equalsIgnoreCase(botWahl)) {
                    embedBuilder.addField("Resultat", "Mhh, Sieht so aus als steht es Unentschieden!!", false);
                } else if (wahl.equalsIgnoreCase("Stein") && botWahl.equalsIgnoreCase("Schere")) {
                    embedBuilder.addField("Resultat", "Gratulation du hast Gewonnen!!", false);
                } else if (wahl.equalsIgnoreCase("Stein") && botWahl.equalsIgnoreCase("Papier")) {
                    embedBuilder.addField("Resultat", "Leider hast du Verloren, Versuch es am besten noch einmal!!", false);
                } else if (wahl.equalsIgnoreCase("Schere") && botWahl.equalsIgnoreCase("Stein")) {
                    embedBuilder.addField("Resultat", "Leider hast du Verloren, Versuch es am besten noch einmal!!", false);
                } else if (wahl.equalsIgnoreCase("Schere") && botWahl.equalsIgnoreCase("Papier")) {
                    embedBuilder.addField("Resultat", "Gratulation du hast Gewonnen!!", false);
                } else if (wahl.equalsIgnoreCase("Papier") && botWahl.equalsIgnoreCase("Stein")) {
                    embedBuilder.addField("Resultat", "Gratulation du hast Gewonnen!!", false);
                } else if (wahl.equalsIgnoreCase("Papier") && botWahl.equalsIgnoreCase("Schere")) {
                    embedBuilder.addField("Resultat", "Leider hast du Verloren, Versuch es am besten noch einmal!!", false);
                }
                embedBuilder.setFooter("@Golden-Developer", e.getJDA().getSelfUser().getAvatarUrl());
                e.replyEmbeds(embedBuilder.build()).queue();
            } else {
                e.reply("ERROR: Deine Antwort muss eins der Folgenden Beispiele sein: Schere, Stein, Papier").queue();
            }
        } else if (cmd.equalsIgnoreCase(Discord.cmdVolume)) {
            if (e.isFromGuild()) {
                setVolume(e);
            } else {
                e.reply("Dieser Command ist nur auf einem Server möglich!").queue();
            }
        }
    }


    @Override
    public void onButtonInteraction(ButtonInteractionEvent e) {
        String button = e.getButton().getId();
        if (button != null) {
            switch (button) {
                case serien ->
                        e.getInteraction().reply("Wir empfehlen dir die Serie [" + getItem(serien) + "]!").queue();
                case movie -> e.getInteraction().reply("Wir empfehlen dir den Film [" + getItem(movie) + "]!").queue();
                case games -> e.getInteraction().reply("Wir empfehlen dir das Game [" + getItem(games) + "]!").queue();
                case fact -> e.getInteraction().reply(getItem(fact)).queue();
                case jokes -> e.getInteraction().reply(getItem(jokes)).queue();
                case eightBall -> e.getInteraction().reply(getItem(eightBall)).queue();
                case skip -> {
                    Message msg = e.getChannel().getHistory().getMessageById(e.getChannel().getLatestMessageId());
                    if (msg != null) {
                        msg.editMessageEmbeds(EmojiEmbed()).queue();
                    }
                }
            }
        }
    }

    @Override
    public void onCommandAutoCompleteInteraction(@NotNull CommandAutoCompleteInteractionEvent e) {
        String cmd = e.getName();
        if (cmd.equalsIgnoreCase(Discord.cmdScissorsStonePapered)) {
            if (e.getFocusedOption().getName().equalsIgnoreCase(Discord.cmdScissorsStonePaperedOptionObjekt)) {
                e.replyChoices(
                        new Command.Choice("Schere", "Schere"),
                        new Command.Choice("Stein", "Stein"),
                        new Command.Choice("Papier", "Papier")
                ).queue();
            }
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.isFromGuild()) {
            Table table = Main.getMysqlConnection().getMysql().getDatabase(MysqlConnection.dbName).getTable(MysqlConnection.DiscordTable);
            if (table.getColumn(MysqlConnection.emojiGameChannelID).getAll().contains(e.getChannel().getId())) {
                //Emoji Game
            } else if (table.getColumn(MysqlConnection.galgenGameChannelID).getAll().contains(e.getChannel().getId())) {
                if (e.getMessage().getContentRaw().split(" ").length == 1) {
                    HashMap<String, Object> row = table.getRow(table.getColumn(MysqlConnection.GalgenGameActive), "1");
                    String keyWort = row.get(MysqlConnection.columnGameBegriff).toString();
                    if (e.getMessage().getContentRaw().toCharArray().length == 1) {
                        for (Character c : keyWort.toCharArray()) {
                            if (c.equals(e.getMessage().getContentRaw())) {
                                Message m = getGameMessage(e.getChannel().getHistory().getRetrievedHistory());
                                System.out.println("Next");
                            }
                        }
                    } else {
                        if (e.getMessage().getContentRaw().equalsIgnoreCase(keyWort)) {
                            //Win
                            // Setzte Active und Errors 0 DB
                        } else {
//                            table.getColumn().set("", "");
                            // Next ERROR +1
                        }
                    }
                }
            }
        }
    }

    public Message getGameMessage(List<Message> history) {
        for (Message m : history) {
            if (m.getEmbeds().size() == 1) {
                MessageEmbed embed = m.getEmbeds().get(0);
                if (embed.getTitle().equalsIgnoreCase("Galgenmännchen") || embed.getTitle().equalsIgnoreCase("Emoji Quiz")) {
                    return m;
                }
            }
        }
        return null;
    }


    private void loadAndPlay(final SlashCommandInteractionEvent e, final String trackUrl) {
        GuildMusicManager musicManager = getGuildAudioPlayer(e.getGuild());

        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                e.reply(track.getInfo().title + " wurde der Warteschlange hinzugefügt!").queue();
                play(e.getGuild(), e.getMember(), musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();
                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }
                for (AudioTrack track : playlist.getTracks()) {
                    musicManager.scheduler.queue(track);
                }
                e.reply(firstTrack.getInfo().title + " wurde der Warteschlange hinzugefügt! (Erster Song der Playlist: " + playlist.getName() + ")").queue();
                play(e.getGuild(), e.getMember(), musicManager, firstTrack);
            }

            @Override
            public void noMatches() {
                e.reply("Es konnte nichts gefunden werden mit dem Link: " + trackUrl).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                e.reply(exception.getMessage() + " konnte nicht abgespielt werden!").queue();
            }
        });
    }

    private void play(Guild guild, Member member, GuildMusicManager musicManager, AudioTrack track) {
        connectToVoiceChannel(member, guild.getAudioManager());
        musicManager.scheduler.queue(track);
    }

    private static void connectToVoiceChannel(Member member, AudioManager audioManager) {
        if (!audioManager.isConnected()) {
            if (member.getVoiceState().inAudioChannel()) {
                audioManager.openAudioConnection(member.getVoiceState().getChannel());
            }
        }
    }

    private void skipTrack(SlashCommandInteractionEvent e) {
        GuildMusicManager musicManager = getGuildAudioPlayer(e.getGuild());
        musicManager.scheduler.nextTrack();
        e.reply("Der nächste Song wird abgespielt!").queue();
    }

    private void stopTrack(SlashCommandInteractionEvent e) {
        GuildMusicManager musicManager = getGuildAudioPlayer(e.getGuild());
        if (musicManager.getPlayer().getPlayingTrack() != null) {
            musicManager.getPlayer().stopTrack();
            if (e.getGuild().getSelfMember().getVoiceState().inAudioChannel()) {
                e.getGuild().getAudioManager().closeAudioConnection();
                e.reply("Ich beende die Vorstellung!").queue();
            }
        } else {
            e.reply("Es wird momentan nichts abgespielt!").queue();
        }
    }

    private void resumeTrack(SlashCommandInteractionEvent e) {
        GuildMusicManager musicManager = getGuildAudioPlayer(e.getGuild());
        if (musicManager.getPlayer().isPaused()) {
            musicManager.getPlayer().setPaused(false);
            e.reply("Die Musik wird weiter gespielt!").queue();
        } else {
            e.reply("Es konnte nichts Abgespielt werden!").queue();
        }
    }


    private void setVolume(SlashCommandInteractionEvent e) {
        int volume = e.getOption(Discord.cmdVolumeOptionVolume).getAsInt();
        GuildMusicManager musicManager = getGuildAudioPlayer(e.getGuild());
        if (!musicManager.getPlayer().isPaused()) {
            musicManager.getPlayer().setVolume(volume);
            e.reply("Die Musik wird nun mit Lautstärke " + volume + "!").queue();
        } else {
            e.reply("Es konnte nichts Abgespielt werden!").queue();
        }
    }

    private void pauseTrack(SlashCommandInteractionEvent e) {
        GuildMusicManager musicManager = getGuildAudioPlayer(e.getGuild());
        if (!musicManager.getPlayer().isPaused()) {
            musicManager.getPlayer().setPaused(true);
            e.reply("Die Musik wurde pausiert!").queue();
        } else {
            e.reply("Es wird momentan nichts abgespielt!").queue();
        }
    }

    private synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
        GuildMusicManager musicManager = musicManagers.get(guild.getIdLong());
        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guild.getIdLong(), musicManager);
        }
        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());
        return musicManager;
    }

    public static String getItem(String ID) {
        Table table = null;
        switch (ID) {
            case movie -> table = Main.getMysqlConnection().getMysql().getDatabase(MysqlConnection.dbName).getTable(MysqlConnection.movieTName);
            case serien -> table = Main.getMysqlConnection().getMysql().getDatabase(MysqlConnection.dbName).getTable(MysqlConnection.serienTName);
            case games -> table = Main.getMysqlConnection().getMysql().getDatabase(MysqlConnection.dbName).getTable(MysqlConnection.gameTName);
            case jokes -> table = Main.getMysqlConnection().getMysql().getDatabase(MysqlConnection.dbName).getTable(MysqlConnection.jokeTName);
            case fact -> table = Main.getMysqlConnection().getMysql().getDatabase(MysqlConnection.dbName).getTable(MysqlConnection.factTName);
            case eightBall -> table = Main.getMysqlConnection().getMysql().getDatabase(MysqlConnection.dbName).getTable(MysqlConnection.eightBallTName);
        }
        if (table != null) {
            return table.getRandomFromColumn(MysqlConnection.columnName).toString();
        }
        return "";
    }

    private MessageEmbed GalgenEmbed() {
        if (Main.getMysqlConnection().getMysql().existsDatabase(MysqlConnection.dbName)) {
            Database db = Main.getMysqlConnection().getMysql().getDatabase(MysqlConnection.dbName);
            if (db.existsTable(MysqlConnection.GalgenGameTable)) {
                Table table = db.getTable(MysqlConnection.GalgenGameTable);
                if (table.existsColumn("id")) {
                    Column id = table.getColumn("id");
                    HashMap<String, Object> row = table.getRow(id, Integer.toString(new Random().nextInt(id.getAll().size())));
                    String keyWort = row.get(MysqlConnection.columnGameBegriff).toString();
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("Galgenmännchen");
                    builder.setDescription(
                            ".....--------........................\n" +
                                    ".....|.........|..........................\n" +
                                    ".....|........○..........................\n" +
                                    ".....|....\\ ..|../.....................\n" +
                                    ".....|.........|..........................\n" +
                                    ".....|.........|...........................\n" +
                                    ".....|......./\\ ........................\n" +
                                    ".....|..../......\\ .....................\n" +
                                    ".....|......................................"
                    );
                    String underline = "";
                    for (Character c : keyWort.toCharArray()) {
                        underline = underline + "_";
                    }
                    builder.addField("Lösungswort:", underline, false);
                    builder.addField("Fehler: ", row.get(MysqlConnection.GameErrors).toString() + "/8", true);
                    builder.addField("Buchstaben: ", "~~" + row.get(MysqlConnection.GalgenBuchstaben).toString().toUpperCase() + "~~", true);
                    builder.setFooter("» Dir fällt der Begriff nicht ein? Nutze den Überspringen-Button, um das Quiz zu überspringen.");
                    return builder.build();
                }
            }
        }
        return null;
    }

    public static MessageEmbed EmojiEmbed() {
        if (Main.getMysqlConnection().getMysql().existsDatabase(MysqlConnection.dbName)) {
            Database db = Main.getMysqlConnection().getMysql().getDatabase(MysqlConnection.dbName);
            if (db.existsTable(MysqlConnection.GameTable)) {
                Table table = db.getTable(MysqlConnection.GameTable);
                if (table.existsColumn("id")) {
                    Column id = table.getColumn("id");
                    HashMap<String, Object> row = table.getRow(id, Integer.toString(new Random().nextInt(id.getAll().size())));
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("Emoji Quiz");
                    builder.addField("", "Gesuchter Begriff: " + row.get(MysqlConnection.GameEmojiOne).toString() + " " + row.get(MysqlConnection.GameEmojiTwo).toString(), false);
                    builder.addField("Schwierigkeit: ", row.get(MysqlConnection.GameDifficulty).toString(), false);
                    builder.addField("Tipp: ", row.get(MysqlConnection.GameHint).toString(), false);
                    builder.setFooter("» Dir fällt der Begriff nicht ein? Nutze den Überspringen-Button, um das Quiz zu überspringen.");
                    return builder.build();
                }
            }
        }
        return null;
    }
}
