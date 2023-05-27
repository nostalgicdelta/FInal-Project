import javax.swing.*;

// This is my custom exception class. It prevents the game from crashing when the user selects the hint or submit button without starting the game.
public class EmptyTextAreaException extends Exception {
    // This exception has 2 methods which will let the user know that they have to select start before they can select hint or submit.
    // This exception is used in the showHint() and submitGuess() function in the GUI class.
    public EmptyTextAreaException(String message) {
        super(message);
    }

    public void displayErrorMessage() {
        JOptionPane.showMessageDialog(null, "Please select Words or Phrases\nand then Start the game!", "Empty TextArea", JOptionPane.ERROR_MESSAGE);
    }
}
