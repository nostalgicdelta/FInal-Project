// This is my 1st child class that extends the Hint class.
class WordsHint extends Hint {
    private String originalWord;
    private int hintIndex;

    public WordsHint(String originalWord) {
        this.originalWord = originalWord;
        hintIndex = 0;
        updateHint();
    }

    @Override
    public void updateHint() {
        if (hintIndex <= originalWord.length()) {
            hint = originalWord.substring(0, hintIndex);
            hintIndex++;
        }
    }
}