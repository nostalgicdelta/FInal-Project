import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

// This is my 1st child class that implements the interface Choice.
public class Phrases implements Choice {
    private List<String> originalPhrases;
    private String originalPhrase;
    private int currentPhraseIndex;

    public Phrases() {
        originalPhrases = readTextFile();
    }

    // This is one of my overridden methods that will pick a random phrase from my txt file Phrases. I also have numerous other overridden methods as well.
    @Override
    public void setOriginalText() {
        Random random = new Random();
        currentPhraseIndex = random.nextInt(originalPhrases.size());
        originalPhrase = originalPhrases.get(currentPhraseIndex);
    }

    @Override
    public String getChoiceType() {
        return "phrase";
    }

    @Override
    public String getJumbledText() {
        return jumbleText(originalPhrase);
    }

    private String jumbleText(String text) {
        char[] chars = text.toCharArray();
        List<Character> charList = new ArrayList<>();
        for (char c : chars) {
            charList.add(c);
        }
        Random random = new Random();
        StringBuilder jumbledText = new StringBuilder();
        while (!charList.isEmpty()) {
            int randomIndex = random.nextInt(charList.size());
            jumbledText.append(charList.remove(randomIndex));
        }
        return jumbledText.toString();
    }

    @Override
    public String getOriginalText() {
        return originalPhrase;
    }

    // This is my Read/Write something from a file
    // This method reads the text file Phrases for a list of all the possible phrases to be used in the game.
    private List<String> readTextFile() {
        List<String> textLines = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("phrases.txt"))) {
            while ((line = br.readLine()) != null) {
                textLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return textLines;
    }
}