package io.github.coho04.entertainment.discord.commands.music;

import dev.arbjerg.lavalink.client.player.Track;
import io.github.coho04.dcbcore.DCBot;
import io.github.coho04.dcbcore.interfaces.CommandInterface;
import io.github.coho04.entertainment.Main;
import io.github.coho04.entertainment.discord.ButtonClickListener;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Command to display the music queue in a Discord server.
 */
public class QueueList implements CommandInterface {

    private static final int ITEMS_PER_PAGE = 5;

    /**
     * Provides the command data for the "queue" command.
     *
     * @return CommandData object containing the command name and description.
     */
    @Override
    public CommandData commandData() {
        return Commands.slash("queue", "Zeigt die Queue an!");
    }

    /**
     * Executes the "queue" command.
     *
     * @param e     The SlashCommandInteractionEvent triggered by the command.
     * @param dcBot The DCBot instance.
     */
    @Override
    public void runSlashCommand(SlashCommandInteractionEvent e, DCBot dcBot) {
        Queue<Track> queue = Main.getAudioPlayerHelper().getMusicManager(e.getGuild().getIdLong()).getScheduler().getQueue();

        if (queue.isEmpty()) {
            e.reply("Die Queue ist aktuell leer!").queue();
            return;
        }

        List<Track> trackList = new ArrayList<>(queue);
        int totalPages = (int) Math.ceil((double) trackList.size() / ITEMS_PER_PAGE);
        AtomicInteger currentPage = new AtomicInteger(0);

        List<MessageEmbed> pages = IntStream.range(0, totalPages).mapToObj(page -> {
            List<Track> paginatedTracks = trackList.subList(page * ITEMS_PER_PAGE, Math.min((page + 1) * ITEMS_PER_PAGE, trackList.size()));
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Queue - Seite " + (page + 1))
                    .setColor(Color.CYAN)
                    .setFooter(e.getJDA().getSelfUser().getName(), e.getJDA().getSelfUser().getAvatarUrl());

            for (Track track : paginatedTracks) {
                embed.addField(track.getInfo().getTitle(), "by " + track.getInfo().getAuthor(), false);
            }
            return embed.build();
        }).collect(Collectors.toList());

        Button prev = Button.primary("prev", "Vorherige Seite").withDisabled(true);
        Button next = Button.primary("next", "Nächste Seite").withDisabled(pages.size() <= 1);

        e.replyEmbeds(pages.get(currentPage.get()))
                .addActionRow(prev, next)
                .queue(interactionHook -> interactionHook.retrieveOriginal().queue(message ->
                        e.getJDA().addEventListener(new ButtonClickListener(message, pages, currentPage, prev, next))));
    }
}
