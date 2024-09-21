package view;

import javax.swing.*;
import java.awt.*;

public class WindowView extends JFrame implements View {

    private JButton[][] buttons;
    private JTextField statusField;
    private final int WINDOW_SIZE = 400;
    private int width = WINDOW_SIZE;
    private int height = WINDOW_SIZE;

    public WindowView(String title, int size) {
        buttons = new JButton[size][size];
        statusField = new JTextField();
        statusField.setEditable(false);
        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(size, size));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j] = new JButton();
                buttonPanel.add(buttons[i][j]);
            }
        }
        add(buttonPanel, BorderLayout.CENTER);
        add(statusField, BorderLayout.SOUTH);
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public int inputNumber() {
        return 0;
    }

    @Override
    public void outputMessage(String message) {
        statusField.setText(message);
    }

    @Override
    public void drawPlayingField(char[][] array) {

    }
}
