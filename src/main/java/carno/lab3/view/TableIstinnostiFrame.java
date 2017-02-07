package carno.lab3.view;

import javax.swing.*;
import java.awt.*;


public class TableIstinnostiFrame extends JFrame {
    private JPanel mainPanel;
    private JScrollPane scrollPane;
    private JTable tableIstinnosti;

    public TableIstinnostiFrame() throws HeadlessException {
        super("Table Istinnosti");
        add(mainPanel);
        setSize(1400, 200);
        setVisible(true);
    }

    public JTable getTableIstinnosti() {
        return tableIstinnosti;
    }
}
