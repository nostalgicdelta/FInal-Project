// This is my parent class that also has 2 child classes that extend it.
abstract class Hint {
    protected String hint;

    public abstract void updateHint();

    public String getHint() {
        return hint;
    }
}

