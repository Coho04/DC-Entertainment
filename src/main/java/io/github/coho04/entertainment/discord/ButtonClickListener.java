package io.github.coho04.entertainment.discord;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Listener for handling button click interactions in Discord messages.
 */
public class ButtonClickListener extends ListenerAdapter {

    private final Message message;
    private final List<MessageEmbed> pages;
    private final AtomicInteger currentPage;
    private final Button prev;
    private final Button next;

    /**
     * Constructs a ButtonClickListener.
     *
     * @param message     The message containing the buttons.
     * @param pages       The list of message embeds representing the pages.
     * @param currentPage The current page index.
     * @param prev        The button for navigating to the previous page.
     * @param next        The button for navigating to the next page.
     */
    public ButtonClickListener(Message message, List<MessageEmbed> pages, AtomicInteger currentPage, Button prev, Button next) {
        this.message = message;
        this.pages = pages;
        this.currentPage = currentPage;
        this.prev = prev;
        this.next = next;
    }

    /**
     * Handles button interaction events.
     *
     * @param event The button interaction event.
     */
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (!event.getMessageId().equals(message.getId())) return;

        int newPage = currentPage.get();
        if (event.getComponentId().equals("prev")) {
            newPage = Math.max(0, currentPage.decrementAndGet());
        } else if (event.getComponentId().equals("next")) {
            newPage = Math.min(pages.size() - 1, currentPage.incrementAndGet());
        }

        event.editMessageEmbeds(pages.get(newPage))
                .setActionRow(
                        prev.withDisabled(newPage == 0),
                        next.withDisabled(newPage == pages.size() - 1)
                ).queue();
    }
}
