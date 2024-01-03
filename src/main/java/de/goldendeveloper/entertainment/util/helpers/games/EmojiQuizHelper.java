package de.goldendeveloper.entertainment.util.helpers.games;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.Map;

public class EmojiQuizHelper {

    private final Map<String, String> emojiToWordMap;
    private String currentEmoji;
    private String currentWord;

    public EmojiQuizHelper() {
        emojiToWordMap = new HashMap<>();
        loadEmojiQuestions();
        generateNewQuestion();
    }

    private void loadEmojiQuestions() {
        emojiToWordMap.put(":alien: :zap:", "E.T.");
    }

    private void generateNewQuestion() {
        Map.Entry<String, String> entry = emojiToWordMap.entrySet().iterator().next();
        currentEmoji = entry.getKey();
        currentWord = entry.getValue();
    }

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

    private void checkAnswer(MessageReceivedEvent event, String guess) {
        if (guess.equalsIgnoreCase(currentWord)) {
            event.getChannel().sendMessage("Richtig! Die Antwort ist: " + currentWord).queue();
            generateNewQuestion();
        } else {
            event.getChannel().sendMessage("Falsch! Versuche es erneut.").queue();
        }
    }

    private void giveHint(MessageReceivedEvent event) {
        if (currentWord != null && !currentWord.isEmpty()) {
            event.getChannel().sendMessage("Der erste Buchstabe ist: " + currentWord.charAt(0)).queue();
        }
    }

    // Hier könntest du weitere Methoden hinzufügen, um die Spiellogik zu erweitern
}
