package carno.lab3.view;

import carno.lab3.controller.Controller;
import carno.lab3.controller.IController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUI extends JFrame implements ControllerObserver {
    private JPanel mainPanel;
    private JComboBox rozmirnistBox;
    private JTable carnoTable;
    private JPanel carnoPanel;
    private JScrollPane scroll;
    private IController controller;


    public GUI() {
        super("Carno");
        controller = new Controller(this);
        setSize(new Dimension(680, 320));
        setResizable(false);
        setResizable(true);
        initRozmirnistBox();
        add(mainPanel);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initRozmirnistBox() {
        rozmirnistBox.setToolTipText("Choose matrix rozmirnist");
        for (int i = 6; i <= 10; i++) {
            rozmirnistBox.addItem(i);
        }
        rozmirnistBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notifyAmountChanged(controller, ((Integer) rozmirnistBox.getSelectedItem()));
            }
        });
        rozmirnistBox.setEditable(false);
        notifyAmountChanged(controller, ((Integer) rozmirnistBox.getSelectedItem()));
    }


    @Override
    public void notifyAmountChanged(IController controller, int newAmount) {
        controller.amountOfVariablesChanged(newAmount);
    }

    public JTable getCarnoTable() {
        return carnoTable;
    }

    public void setController(IController controller) {
        this.controller = controller;
    }
}
