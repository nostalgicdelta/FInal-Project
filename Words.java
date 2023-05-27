import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

// This is my 2nd child class that implements the interface Choice.
public class Words implements Choice {
    private List<String> originalWords;
    public String originalWord;
    private int currentWordIndex;

    public Words() {
        originalWords = readTextFile();
    }

    // This is one of my overridden methods that will pick a random Word from my txt file Words. I also have numerous other overridden methods as well.
    @Override
    public void setOriginalText () {
        Random random = new Random();
        currentWordIndex = random.nextInt(originalWords.size());
        originalWord = originalWords.get(currentWordIndex);
    }

    @Override
    public String getChoiceType() {
        return "word";
    }


    @Override
    public String getJumbledText() {
        return jumbleText(originalWord);
    }

    private String jumbleText(String text) {
        char[] chars = text.toCharArray();
        Random random = new Random();
        for (int i = 0; i < chars.length; i++) {
            int randomIndex = random.nextInt(chars.length);
            char temp = chars[i];
            chars[i] = chars[randomIndex];
            chars[randomIndex] = temp;
        }
        return new String(chars);
    }

    @Override
    public String getOriginalText() {
        return originalWord;
    }

    private List<String> readTextFile() {
        List<String> textLines = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("words.txt"))) {
            while ((line = br.readLine()) != null) {
                textLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return textLines;
    }
}