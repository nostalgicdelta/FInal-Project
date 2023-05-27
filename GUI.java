import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class GUI extends JFrame implements ActionListener {
    // These are my 3 different GUI elements which include JButton, JLabel, JComboBox etc...
    private JLabel promptLabel;
    private JButton startButton;
    private JButton submitButton;
    private JTextField guessTextField;
    private JTextArea displayArea;
    private JComboBox<String> choiceComboBox;
    private JCheckBox timerCheckBox;
    private Choice choice;
    private Thread timerThread;
    private int timeRemaining;
    private JButton hintButton;
    private JTextArea hintArea;
    private Hint hint;
    private int score = 0;
    private JLabel scoreLabel;


    public GUI() {
        setupGUI();
    }

    // This is my overloaded constructor that can setup the GUI but can easily change the size of the game window if needed to be changed.
    public GUI(int width, int height) {
        this();
        setSize(width, height);
    }

    public void setupGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Jumbled Game");
        setSize(400, 375);
        setLocationRelativeTo(null);

        promptLabel = new JLabel("Choose an option:");
        String[] options = {"Phrases", "Words"};
        choiceComboBox = new JComboBox<>(options);
        startButton = new JButton("Start Game");
        submitButton = new JButton("Submit");
        guessTextField = new JTextField(22);
        displayArea = new JTextArea(10, 20);
        displayArea.setEditable(false);
        timerCheckBox = new JCheckBox("Enable Timer");
        scoreLabel = new JLabel("Score: " + score);

        hintButton = new JButton("Hint");
        hintArea = new JTextArea(1, 24);
        hintArea.setEditable(false);

        startButton.addActionListener(this);
        submitButton.addActionListener(this);
        hintButton.addActionListener(this);

        // This is my layout manager in the form of FlowLayout.
        setLayout(new FlowLayout());
        add(promptLabel);
        add(choiceComboBox);
        add(timerCheckBox);
        add(scoreLabel);
        add(new JScrollPane(displayArea));
        add(startButton);
        add(new JScrollPane(hintArea));
        add(hintButton);
        add(guessTextField);
        add(submitButton);

        // This is my Image that shows the user a picture of money (What they could win playing the game).
        try {
            BufferedImage myPicture = ImageIO.read(new File("multimedia\\money.jpg"));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            add(picLabel);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(rootPane, e, getTitle(), ABORT, null);
        }

        setVisible(true);
    }

    private void startGame() {
        String selectedChoice = (String) choiceComboBox.getSelectedItem();
        boolean enableTimer = timerCheckBox.isSelected();
        if (selectedChoice.equals("Phrases")) {
            choice = new Phrases();
            choice.setOriginalText();
            hint = new PhrasesHint(choice.getOriginalText());
        } else if (selectedChoice.equals("Words")) {
            choice = new Words();
            choice.setOriginalText();
            hint = new WordsHint(choice.getOriginalText());
        }

        if (enableTimer) {
            startTimer();
        }

        setupGame();
    }

    private void setupGame() {
        String jumbledText = choice.getJumbledText();
        displayArea.setText(jumbledText);
        guessTextField.setText("");
        hintArea.setText("");
        guessTextField.requestFocus();
    }

    private void submitGuess() {
        String guessField = guessTextField.getText().trim();
        if (guessField.isEmpty()) {
            try {
                throw new EmptyTextAreaException("Empty TextArea Exception");
            } catch (EmptyTextAreaException e) {
                e.displayErrorMessage();
            }
            return;
        }
        String guess = guessTextField.getText();
        String originalText = choice.getOriginalText();
        if (guess.equalsIgnoreCase(originalText)) {
            score++;
            scoreLabel.setText("Score: " + score);
            JOptionPane.showMessageDialog(this, "Congratulations! Your guess is correct.");
            resetTimer();
            startGame();
        } else {
            JOptionPane.showMessageDialog(this, "Oops! Your guess is incorrect. Try again.");
        }
        guessTextField.setText("");
        guessTextField.requestFocus();
    }

    // This is my Thread. It is used in this case as a timer for the user.
    private void startTimer() {
        timeRemaining = 60; 
        timerThread = new Thread(() -> {
            while (timeRemaining > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                timeRemaining--;
            }
            showTimeUpMessage();
            setupGame();
        });
        timerThread.start();
    }

    private void resetTimer() {
        if (timerThread != null && timerThread.isAlive()) {
            timerThread.interrupt();
        }
    }

    private void showTimeUpMessage() {
        String originalText = choice.getOriginalText();
        JOptionPane.showMessageDialog(this, "Time's up!\nThe word or phrase was: " + originalText);
    }

    private void showHint() {
        String jumbledText = displayArea.getText().trim();
        if (jumbledText.isEmpty()) {
            try {
                throw new EmptyTextAreaException("Empty TextArea Exception");
            } catch (EmptyTextAreaException e) {
                e.displayErrorMessage();
            }
            return;
        }
        hint.updateHint();
        hintArea.setText(hint.getHint());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            startGame();
        } else if (e.getSource() == submitButton) {
            submitGuess();
        } else if (e.getSource() == hintButton) {
            showHint();
        }
    }
}

