package carno.lab2.view;

import carno.lab2.controller.Controller;
import carno.lab2.controller.IController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by alexandr on 02.10.16.
 */
public class GUI extends JFrame implements ControllerObserver {
    private JPanel mainPanel;
    private JComboBox rozmirnistBox;
    private JTable carnoTable;
    private JPanel carnoPanel;
    private JScrollPane scroll;
    private IController controller;


    public GUI() {
        super("Carno");
        setSize(new Dimension(600, 700));
        setResizable(true);
        initRozmirnistBox();
        add(mainPanel);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        controller = new Controller(this);
    }

    private void initRozmirnistBox() {
        rozmirnistBox.setToolTipText("Choose matrix rozmirnist");
        for (int i = 3; i <= 10; i++) {
            rozmirnistBox.addItem(i);
        }
        rozmirnistBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notifyAmountChanged(controller, ((Integer) rozmirnistBox.getSelectedItem()));
            }
        });
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
