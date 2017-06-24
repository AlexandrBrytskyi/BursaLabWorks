package rl;

import javax.swing.*;
import java.awt.*;

public class ResultFrame extends JFrame {

        private JPanel mainPanel;
        private JTextArea output;
        private JButton startButton;

        public ResultFrame() throws HeadlessException {
            super("Факторіал");
            setSize(new Dimension(900, 600));
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            initView();
            showDialog("Вітаю, давайте перевіряти точність обчислень", "Вітання!");
            add(mainPanel);
            setVisible(true);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }


        private void initView() {
            mainPanel = new JPanel(new BorderLayout());
            output = new JTextArea("Натискайте на кнопку для початку обрахунку\n");
            JScrollPane pane = new JScrollPane(output, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            output.setEditable(false);


            startButton = new JButton("Рахувати");

            
            mainPanel.add(pane, BorderLayout.CENTER);
            mainPanel.add(startButton, BorderLayout.SOUTH);

        }


        public void appendToOutput(String message) {
            output.append(message + "\n");
        }


        public void showDialog(String s, String title) {
            JOptionPane.showMessageDialog(this,
                    s,
                    title,
                    JOptionPane.INFORMATION_MESSAGE);
        }

        public JPanel getMainPanel() {
            return mainPanel;
        }

        public JTextArea getOutput() {
            return output;
        }

        public JButton getStartButton() {
            return startButton;
        }
    }