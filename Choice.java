// This is my interface that also has 2 child classes that implement it.
// The child classes are Phrases and Words.
public interface Choice {
    public String getChoiceType();
    public String getJumbledText();
    public String getOriginalText();
    public void setOriginalText();
}
