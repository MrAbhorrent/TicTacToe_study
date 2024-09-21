package view;

import javax.swing.*;
import java.awt.*;

public class SettingView extends JFrame {

    private JTextField textField;
    private JButton[] buttons;

    public SettingView(String text, char[] symbols) {

        buttons = new JButton[symbols.length];
        textField = new JTextField();
        textField.setEditable(false);
        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout());
        for (int i = 0; i < symbols.length; i++) {
            buttons[i] = new JButton();
            buttonPanel.add(buttons[i]);
        }
        add(textField, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        setTitle("Настройка игры");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


}
