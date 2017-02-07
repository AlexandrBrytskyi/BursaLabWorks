package carno.lab2.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by alexandr on 02.10.16.
 */
public class TableIstinnostiFrame extends JFrame {
    private JPanel mainPanel;
    private JScrollPane scrollPane;
    private JTable tableIstinnosti;

    public TableIstinnostiFrame() throws HeadlessException {
        super("Table Istinnosti");
        add(mainPanel);
        setSize(500, 800);
        setVisible(true);
    }

    public JTable getTableIstinnosti() {
        return tableIstinnosti;
    }
}
