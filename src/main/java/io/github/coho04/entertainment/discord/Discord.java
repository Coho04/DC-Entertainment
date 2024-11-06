package io.github.coho04.entertainment.discord;

import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import dev.arbjerg.lavalink.client.LavalinkClient;
import dev.arbjerg.lavalink.libraries.jda.JDAVoiceUpdateListener;
import io.github.coho04.dcbcore.DCBot;
import io.github.coho04.dcbcore.discord.commands.*;
import io.github.coho04.dcbcore.discord.events.CoreEvents;
import io.github.coho04.dcbcore.interfaces.CommandInterface;
import io.sentry.Sentry;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.*;
import java.util.stream.Collectors;

public class Discord {

    private JDA bot;
    private final DCBot dcBot;
    private final LinkedList<CommandInterface> commands;

    public Discord(String botToken, DCBot dcBot, LavalinkClient client) {
        this.commands = dcBot.getCommandDataList();
        this.dcBot = dcBot;
        try {
            JDABuilder botBuilder = JDABuilder.createDefault(botToken)
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .setVoiceDispatchInterceptor(new JDAVoiceUpdateListener(client))
                    .enableCache(
                            CacheFlag.MEMBER_OVERRIDES, CacheFlag.ROLE_TAGS,
                            CacheFlag.STICKER, CacheFlag.ACTIVITY,
                            CacheFlag.CLIENT_STATUS, CacheFlag.ONLINE_STATUS
                    )
                    .enableIntents(
                            GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_MESSAGES,
                            GatewayIntent.GUILD_EMOJIS_AND_STICKERS, GatewayIntent.DIRECT_MESSAGES,
                            GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_MODERATION,
                            GatewayIntent.DIRECT_MESSAGE_REACTIONS, GatewayIntent.GUILD_INVITES,
                            GatewayIntent.DIRECT_MESSAGE_TYPING, GatewayIntent.GUILD_MESSAGE_TYPING,
                            GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_WEBHOOKS,
                            GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGE_TYPING,
                            GatewayIntent.MESSAGE_CONTENT
                    )
                    .addEventListeners(new CoreEvents(dcBot))
                    .setAutoReconnect(true)
                    .setContextEnabled(true);
            if (!dcBot.getEvents().isEmpty()) {
                dcBot.getEvents().forEach(botBuilder::addEventListeners);
            }
            if (!dcBot.getGatewayIntentList().isEmpty()) {
                botBuilder.enableIntents(dcBot.getGatewayIntentList());
            }
            bot = botBuilder.build().awaitReady();
            this.registerDefaultCommand();
            if (dcBot.getDeployment()) {
                sendDiscordOnlineMessage();
            }
            bot.getPresence().setActivity(Activity.playing("/help | " + bot.getGuilds().size() + " Servern"));
            commands.stream().filter(Objects::nonNull).forEach(commandInterface -> bot.upsertCommand(commandInterface.commandData()).queue());
        } catch (InterruptedException e) {
            Sentry.captureException(e);
            System.out.println(e.getMessage());
        }
    }

    void registerDefaultCommand() {
        LinkedList<CommandInterface> defaultCommands = new LinkedList<>();
        Collections.addAll(defaultCommands, new BotStats(), new BotOwner(), new Donate(), new Help(), new Invite(), new Join(), new Ping(), new Restart(), new Shutdown());
        this.commands.addAll(defaultCommands
                .stream()
                .filter(commandInterface -> !this.dcBot.getRemovedCommandDataList().contains(commandInterface))
                .collect(Collectors.toCollection(LinkedList::new)));
    }

    public LinkedList<CommandInterface> getCommands() {
        return commands;
    }

    public JDA getBot() {
        return bot;
    }

    private void sendDiscordOnlineMessage() {
        WebhookEmbedBuilder embed = new WebhookEmbedBuilder();
        if (dcBot.getRestart()) {
            embed.setColor(0x33FFFF);
            embed.addField(new WebhookEmbed.EmbedField(false, "[Status]", "Neustart erfolgreich"));
        } else {
            embed.setColor(0x00FF00);
            embed.addField(new WebhookEmbed.EmbedField(false, "[Status]", "ONLINE"));
        }
        embed.setAuthor(new WebhookEmbed.EmbedAuthor(getBot().getSelfUser().getName(), getBot().getSelfUser().getAvatarUrl(), "https://Golden-Developer.de"));
        embed.addField(new WebhookEmbed.EmbedField(false, "Gestartet als", bot.getSelfUser().getName()));
        embed.addField(new WebhookEmbed.EmbedField(false, "Server", Integer.toString(bot.getGuilds().size())));
        embed.addField(new WebhookEmbed.EmbedField(false, "Status", "\uD83D\uDFE2 Gestartet"));
        embed.addField(new WebhookEmbed.EmbedField(false, "Version", dcBot.getConfig().getProjektVersion()));
        embed.setFooter(new WebhookEmbed.EmbedFooter("@Golden-Developer", getBot().getSelfUser().getAvatarUrl()));
        embed.setTimestamp(new Date().toInstant());
        new WebhookClientBuilder(dcBot.getConfig().getDiscordWebhook()).build().send(embed.build());
    }

    public boolean hasPermissions(SlashCommandInteractionEvent e) {
        return e.getUser() == e.getJDA().getUserById(428811057700536331L) || e.getUser() == e.getJDA().getUserById(513306244371447828L);
    }
}