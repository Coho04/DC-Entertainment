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
import de.goldendeveloper.entertainment.util.CountingGame;
import de.goldendeveloper.mysql.entities.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ShutdownEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
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

    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;

    private final List<String> CountingChannels = new ArrayList<>();

    public Events() {
        this.musicManagers = new HashMap<>();
        this.playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    @Override
    public void onShutdown(@NotNull ShutdownEvent e) {
        if (Main.getDeployment()) {
            WebhookEmbedBuilder embed = new WebhookEmbedBuilder();
            embed.setAuthor(new WebhookEmbed.EmbedAuthor(Main.getDiscord().getBot().getSelfUser().getName(), Main.getDiscord().getBot().getSelfUser().getAvatarUrl(), "https://Golden-Developer.de"));
            embed.addField(new WebhookEmbed.EmbedField(false, "[Status]", "Offline"));
            embed.addField(new WebhookEmbed.EmbedField(false, "Gestoppt als", Main.getDiscord().getBot().getSelfUser().getName()));
            embed.addField(new WebhookEmbed.EmbedField(false, "Server", Integer.toString(Main.getDiscord().getBot().getGuilds().size())));
            embed.addField(new WebhookEmbed.EmbedField(false, "Status", "\uD83D\uDD34 Offline"));
            embed.addField(new WebhookEmbed.EmbedField(false, "Version", Main.getConfig().getProjektVersion()));
            embed.setFooter(new WebhookEmbed.EmbedFooter("@Golden-Developer", Main.getDiscord().getBot().getSelfUser().getAvatarUrl()));
            embed.setTimestamp(new Date().toInstant());
            embed.setColor(0xFF0000);
            new WebhookClientBuilder(Main.getConfig().getDiscordWebhook()).build().send(embed.build()).thenRun(() -> System.exit(0));
        }
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
                    e.getInteraction().reply("Der Discord Bot [" + e.getJDA().getSelfUser().getName() + "] wird nun neugestartet!").queue();
                    Process p = Runtime.getRuntime().exec("screen -AmdS " + Main.getConfig().getProjektName() + " java -Xms1096M -Xmx1096M -jar " + Main.getConfig().getProjektName() + "-" + Main.getConfig().getProjektVersion() + ".jar restart");
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
        } else if (cmd.equalsIgnoreCase(Discord.cmdYtSearch)) {
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

        if (cmd.equalsIgnoreCase(Discord.cmdCountingGame)) {
            if (e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                if (!CountingGame.hasCountingGame(e.getGuild())) {
                    String channelName = e.getOption(Discord.cmdCountingGameChannelName).getAsString();
                    CountingGame countingGame = CountingGame.create(channelName, e.getGuild());
                    e.reply("Der Counting Channel wurde erstellt!").queue();
                } else {
                    e.reply("Dieser Server hat bereits einen ZählChannel bitte lösche diesen zuerst mit dem Befehl: /" + Discord.cmdDeleteCountingGame + "!").queue();
                }
            } else {
                e.reply("Dazu hast du keine Rechte!").queue();
            }
        } else if (cmd.equalsIgnoreCase(Discord.cmdDeleteCountingGame)) {
            if (e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                if (CountingGame.hasCountingGame(e.getGuild())) {
                    CountingGame countingGame = CountingGame.findGame(Objects.requireNonNull(e.getGuild()));
                    if (countingGame != null) {
                        countingGame.delete();
                    }
                    e.reply("Der Counting Channel wurde gelöscht!").queue();
                } else {
                    e.reply("Dieser Server keinen  Zähl Channel bitte erstelle zuerst einen mit dem Befehl: /" + Discord.cmdCountingGame + "!").queue();
                }
            } else {
                e.reply("Dazu hast du keine Rechte!").queue();
            }
        }
    }


    @Override
    public void onButtonInteraction(ButtonInteractionEvent e) {
        String button = e.getButton().getId();
        if (button != null) {
            switch (button) {
                case serien -> e.getInteraction().reply("Wir empfehlen dir die Serie [" + getItem(serien) + "]!").queue();
                case movie -> e.getInteraction().reply("Wir empfehlen dir den Film [" + getItem(movie) + "]!").queue();
                case games -> e.getInteraction().reply("Wir empfehlen dir das Game [" + getItem(games) + "]!").queue();
                case fact -> e.getInteraction().reply(getItem(fact)).queue();
                case jokes -> e.getInteraction().reply(getItem(jokes)).queue();
                case eightBall -> e.getInteraction().reply(getItem(eightBall)).queue();
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
        if (e.isFromGuild() && !e.getAuthor().isBot()) {
            Table table = Main.getMysqlConnection().getMysql().getDatabase(MysqlConnection.dbName).getTable(CountingGame.TableName);
            if (CountingChannels.contains(e.getGuild().getId()) || table.getColumn(CountingGame.ChannelColumn).getAll().getAsString().contains(e.getChannel().getId())) {
               if (!CountingChannels.contains(e.getGuild().getId())) {
                   CountingChannels.add(e.getGuild().getId());
               }
                String message = e.getMessage().getContentRaw();
                int num = table.getRow(table.getColumn(CountingGame.ChannelColumn), e.getChannel().getId()).getData().get(CountingGame.NumberColumn).getAsInt();
                CountingGame countingGame = CountingGame.findGame(e.getGuild());
                if (isNumeric(message)) {
                    num = num+1;
                    if (Integer.parseInt(message) == num) {
                        e.getMessage().addReaction(Emoji.fromUnicode("✅")).queue();
                        if (countingGame != null) {
                            countingGame.setCurrentNumber(num);
                        }
                    } else {
                        assert countingGame != null;
                        countingGame.setCurrentNumber(0);
                        e.getMessage().addReaction(Emoji.fromUnicode("❌")).queue();
                        e.getChannel().sendMessage(e.getAuthor().getAsMention() + " STOPPT DAS SPIEL BEI " + num + "!! Die nächste Zahl ist ´1´.").queue();
                    }
                } else {
                    assert countingGame != null;
                    countingGame.setCurrentNumber(0);
                    e.getMessage().addReaction(Emoji.fromUnicode("❌")).queue();
                    e.getChannel().sendMessage(e.getAuthor().getAsMention() + " STOPPT DAS SPIEL BEI " + num + "!! Die nächste Zahl ist ´1´. Dieser Zählkanal ist im reinen Zahlenmodus.").queue();
                }
            }
        }
    }

    @Override
    public void onGuildJoin(GuildJoinEvent e) {
        Main.getServerCommunicator().addServer(e.getGuild().getId());
        e.getJDA().getPresence().setActivity(Activity.playing("/help | " + e.getJDA().getGuilds().size() + " Servern"));
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent e) {
        Main.getServerCommunicator().removeServer(e.getGuild().getId());
        e.getJDA().getPresence().setActivity(Activity.playing("/help | " + e.getJDA().getGuilds().size() + " Servern"));
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

    private void play(Guild guild, Member member, GuildMusicManager
            musicManager, AudioTrack track) {
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
            return table.getColumn(MysqlConnection.columnName).getRandom().toString();
        }
        return "";
    }

    public boolean isNumeric(String strNum) {
        try {
            Integer.parseInt(strNum);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
