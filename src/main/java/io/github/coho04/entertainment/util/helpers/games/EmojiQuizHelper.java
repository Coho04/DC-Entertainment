package io.github.coho04.entertainment.util.helpers.games;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a helper for the Emoji Quiz game.
 * It contains a map of emoji to word pairs, the current emoji, and the current word.
 */
public class EmojiQuizHelper {

    private final Map<String, String> emojiToWordMap;
    private String currentEmoji;
    private String currentWord;

    /**
     * Constructor for the EmojiQuizHelper.
     * It initializes the emoji to word map, loads the emoji questions, and generates a new question.
     */
    public EmojiQuizHelper() {
        emojiToWordMap = new HashMap<>();
        loadEmojiQuestions();
        generateNewQuestion();
    }

    /**
     * This method loads the emoji questions into the emoji to word map.
     */
    private void loadEmojiQuestions() {
        emojiToWordMap.put(":alien: :zap:", "E.T.");
    }

    /**
     * This method generates a new question.
     * It retrieves the first entry from the emoji to word map and sets the current emoji and word to the key and value of the entry.
     */
    private void generateNewQuestion() {
        Map.Entry<String, String> entry = emojiToWordMap.entrySet().iterator().next();
        currentEmoji = entry.getKey();
        currentWord = entry.getValue();
    }

    /**
     * This method is triggered when a message is received.
     * It checks the content of the message and calls the appropriate method based on the content.
     *
     * @param event The MessageReceivedEvent object that represents the message received event.
     */
    public void onMessageReceived(MessageReceivedEvent event) {
        String msg = event.getMessage().getContentRaw();
        if (msg.equals("!emojiQuiz")) {
            sendQuestion(event);
        } else if (msg.startsWith("!guess ")) {
            checkAnswer(event, msg.substring(7).trim());
        } else if (msg.equals("!hint")) {
            giveHint(event);
        } else if (msg.equals("!skip")) {
            generateNewQuestion();
            sendQuestion(event);
        }
    }

    /**
     * This method sends a question to the channel.
     * It creates an embed with the question and sends it to the channel.
     *
     * @param event The MessageReceivedEvent object that represents the message received event.
     */
    private void sendQuestion(MessageReceivedEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Emoji-Ratespiel", null);
        eb.setDescription("Gesuchter Begriff " + currentEmoji);
        eb.addField("Schwierigkeit", "einfach", false);
        eb.addField("Tipp", "Fiktive Figur", false);
        eb.setColor(0x1F8B4C);
        eb.setFooter("Nutze den Überspringen-Button, um das Quiz zu überspringen.");
        event.getChannel().sendMessageEmbeds(eb.build()).queue();
    }

    /**
     * This method checks the answer to the question.
     * If the guess is correct, it sends a message to the channel, generates a new question, and sends the new question.
     * If the guess is incorrect, it sends a message to the channel.
     *
     * @param event The MessageReceivedEvent object that represents the message received event.
     * @param guess The guess to check.
     */
    private void checkAnswer(MessageReceivedEvent event, String guess) {
        if (guess.equalsIgnoreCase(currentWord)) {
            event.getChannel().sendMessage("Richtig! Die Antwort ist: " + currentWord).queue();
            generateNewQuestion();
        } else {
            event.getChannel().sendMessage("Falsch! Versuche es erneut.").queue();
        }
    }

    /**
     * This method gives a hint to the player.
     * It sends a message to the channel with the first letter of the current word.
     *
     * @param event The MessageReceivedEvent object that represents the message received event.
     */
    private void giveHint(MessageReceivedEvent event) {
        if (currentWord != null && !currentWord.isEmpty()) {
            event.getChannel().sendMessage("Der erste Buchstabe ist: " + currentWord.charAt(0)).queue();
        }
    }
}
