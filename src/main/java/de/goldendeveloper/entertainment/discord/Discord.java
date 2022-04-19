package de.goldendeveloper.entertainment.discord;

import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import de.goldendeveloper.entertainment.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Discord {

    private JDA bot;
    public static String cmdEntertainment = "entertainment";
    public static String cmdPlay = "play";
    public static String cmdPlayOptionTrack = "trackurl";
    public static String cmdSkip = "skip";
    public static String cmdPause = "pause";
    public static String cmdResume = "resume";
    public static String cmdStop = "stop";
    public static String cmdScissorsStonePapered = "scissors-stone-papered";
    public static String cmdScissorsStonePaperedOptionObjekt = "objekt";
    public static String cmdEmojiStart = "emojistart";
    public static String cmdGalgenStart = "galgenstart";
    public static String getCmdShutdown = "shutdown";
    public static String getCmdRestart = "restart";
    public static String cmdHelp = "help";

    public Discord(String Token) {
        try {
            bot = JDABuilder.createDefault(Token)
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .enableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.ROLE_TAGS, CacheFlag.EMOTE, CacheFlag.ACTIVITY, CacheFlag.CLIENT_STATUS, CacheFlag.ONLINE_STATUS)
                    .enableIntents(GatewayIntent.GUILD_MESSAGE_REACTIONS,
                            GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_EMOJIS,
                            GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_PRESENCES,
                            GatewayIntent.GUILD_BANS, GatewayIntent.DIRECT_MESSAGE_REACTIONS,
                            GatewayIntent.GUILD_INVITES, GatewayIntent.DIRECT_MESSAGE_TYPING,
                            GatewayIntent.GUILD_MESSAGE_TYPING, GatewayIntent.GUILD_VOICE_STATES,
                            GatewayIntent.GUILD_WEBHOOKS, GatewayIntent.GUILD_MEMBERS,
                            GatewayIntent.GUILD_MESSAGE_TYPING)
                    .addEventListeners(new Events())
                    .setAutoReconnect(true)
                    .build().awaitReady();
            bot.upsertCommand(cmdEntertainment, "Wähle aus wie du Entertaint werden möchtest!").queue();
            bot.upsertCommand(getCmdShutdown, "Fährt den Discord Bot herunter!").queue();
            bot.upsertCommand(getCmdRestart, "Startet den Discord Bot neu!").queue();
            bot.upsertCommand(cmdPlay, "Spielt YouTube-Musik!").addOption(OptionType.STRING, cmdPlayOptionTrack, "YouTube Video Link!", true).queue();
            bot.upsertCommand(cmdSkip, "Überspringt das momentane Lied!").queue();
            bot.upsertCommand(cmdStop, "Spielt keine Musik mehr ab!").queue();
            bot.upsertCommand(cmdScissorsStonePapered, "Schere Stein Papier!").addOption(OptionType.STRING, cmdScissorsStonePaperedOptionObjekt, "Wähle Schere Stein oder Papier aus!", true, true).queue();
            bot.upsertCommand(cmdPause, "Pausiert die Musik!").queue();
            bot.upsertCommand(cmdResume, "Spielt die Musik weiter ab!").queue();
//            bot.upsertCommand(cmdEmojiStart, "Erstellt einen Game Channel und Startet das Game!").queue();
            bot.upsertCommand(cmdHelp, "Zeigt dir eine Liste möglicher Befehle an!").queue();
            Online();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendErrorMessage(String Error) {
        WebhookEmbedBuilder embed = new WebhookEmbedBuilder();
        embed.setAuthor(new WebhookEmbed.EmbedAuthor(getBot().getSelfUser().getName(), getBot().getSelfUser().getAvatarUrl(), "https://Golden-Developer.de"));
        embed.addField(new WebhookEmbed.EmbedField(false, "[ERROR]", Error));
        embed.setColor(0xFF0000);
        embed.setFooter(new WebhookEmbed.EmbedFooter("@Golden-Developer", getBot().getSelfUser().getAvatarUrl()));
        new WebhookClientBuilder(Main.getConfig().getDiscordWebhook()).build().send(embed.build());
    }

    public JDA getBot() {
        return bot;
    }

    private void Online() {
        WebhookEmbedBuilder embed = new WebhookEmbedBuilder();
        embed.setAuthor(new WebhookEmbed.EmbedAuthor(getBot().getSelfUser().getName(), getBot().getSelfUser().getAvatarUrl(), "https://Golden-Developer.de"));
        embed.addField(new WebhookEmbed.EmbedField(false, "[Status]", "ONLINE"));
        embed.setColor(0x00FF00);
        embed.setFooter(new WebhookEmbed.EmbedFooter("@Golden-Developer", getBot().getSelfUser().getAvatarUrl()));
        new WebhookClientBuilder(Main.getConfig().getDiscordWebhook()).build().send(embed.build());
    }
}