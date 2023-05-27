// This is my 2nd child class that extends the Hint class 
class PhrasesHint extends Hint {
    private String originalPhrase;
    private int hintIndex;

    public PhrasesHint(String originalPhrase) {
        this.originalPhrase = originalPhrase;
        hintIndex = 0;
        updateHint();
    }

    @Override
    public void updateHint() {
        if (hintIndex <= originalPhrase.length()) {
            hint = originalPhrase.substring(0, hintIndex);
            hintIndex += 2;
        }
    }
}