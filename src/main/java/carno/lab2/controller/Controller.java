package carno.lab2.controller;

import carno.lab2.model.*;
import carno.lab2.view.GUI;
import carno.lab2.view.TableIstinnostiFrame;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

/**
 * Created by alexandr on 02.10.16.
 */
public class Controller implements IController, ControllerObservable {


    private GUI gui;
    private Function function;
    private int romirnist;
    private char[] varNames;
    private List<Minterm> leftPartCombinations;
    private List<Minterm> topPartCombinations;
    private List<Minterm> tableIstinnosti;
    private List<Map.Entry<Minterm, List<Minterm>>> valuesInTheTable;
    private JTable tableCarno;
    MyRowTableModel myRowTableModel;
    private TableIstinnostiFrame tableIstinnostiFrame;
    private TabliciaIstinnostiTableModel tabliciaIstinnostiTableModel;
    private Map<Integer, List<Minterm>> whoCanBeScleyani = new HashMap<>();


    public Controller(GUI gui) {
        this.gui = gui;
        tableIstinnostiFrame = new TableIstinnostiFrame();
    }

    @Override
    public void amountOfVariablesChanged(int newRozmirnist) {
        this.romirnist = newRozmirnist;
        buildKarno();
    }

    @Override
    public void createTableIstinnosti() {
        generateTableIstinnosti();
    }

    @Override
    public void buildKarno() {
        generateVarNames();
        generateLeftPartCombinations();
        generateTopPartCombinations();
        createTableIstinnosti();
//        System.out.println(tableIstinnosti);
        generateValuesOfTable();
        setTableWithModel();
        showTableIstinnosti();
//        System.out.println(valuesInTheTable);
    }

    private void showTableIstinnosti() {
        String[] colNames = new String[varNames.length + 1];
        for (int i = 0; i < colNames.length - 1; i++) {
            colNames[i] = "" + varNames[i];
        }
        colNames[colNames.length - 1] = "Function";

        tabliciaIstinnostiTableModel = new TabliciaIstinnostiTableModel(tableIstinnosti, colNames);
        tableIstinnostiFrame.getTableIstinnosti().setModel(tabliciaIstinnostiTableModel);
        tableIstinnostiFrame.getTableIstinnosti().updateUI();
    }


    private void generateValuesOfTable() {
        int k = 0;
        valuesInTheTable = new LinkedList<>();
        for (Minterm leftPartCombination : leftPartCombinations) {
            List<Minterm> mintermsInRow = new LinkedList<>();
            for (int i = 0; i < topPartCombinations.size(); i++) {
                mintermsInRow.add(tableIstinnosti.get(i + k));
            }
            k += topPartCombinations.size();
            Map.Entry<Minterm, List<Minterm>> row = new MyRow(leftPartCombination, mintermsInRow);
            valuesInTheTable.add(row);
        }
    }

    private void setTableWithModel() {
        String[] colNames = new String[topPartCombinations.size() + 1];
        for (int i = 1; i < topPartCombinations.size() + 1; i++) {
            colNames[i] = topPartCombinations.get(i - 1).toString();
        }
        colNames[0] = "/";
//        System.out.println(Arrays.toString(colNames));

        tableCarno = gui.getCarnoTable();
        myRowTableModel = new MyRowTableModel(valuesInTheTable, colNames);
        tableCarno.setModel(myRowTableModel);
        tableCarno.setRowSelectionAllowed(false);
        setCellButtonRenderer();
    }

    private void setCellButtonRenderer() {
        JTableButtonRenderer renderer = new JTableButtonRenderer();
        for (Minterm topPartCombination : topPartCombinations) {
            tableCarno.getColumn(topPartCombination.toString()).setCellRenderer(renderer);
        }
        if (tableCarno.getMouseListeners().length < 3)
            tableCarno.addMouseListener(new JTableButtonMouseListener(tableCarno));
    }


    private void generateTableIstinnosti() {
        tableIstinnosti = new LinkedList<>();
        int counter = 1;
        for (Minterm leftPartCombination : leftPartCombinations) {
            for (Minterm topPartCombination : topPartCombinations) {
                List<Variable> vars = new LinkedList<>();
                for (Variable variable : leftPartCombination.getVariables()) {
                    vars.add(variable);
                }
                for (Variable variable : topPartCombination.getVariables()) {
                    vars.add(variable);
                }
                Minterm minterm = new Minterm(counter++, false, vars);
                tableIstinnosti.add(minterm);
            }
        }
    }

    private void generateTopPartCombinations() {
        int mintermRozmirnist = varNames.length - varNames.length / 2;
        int stringLength = Integer.toBinaryString((int) Math.pow(2, mintermRozmirnist) - 1).length();
        topPartCombinations = new LinkedList<>();
        generateMinterms(mintermRozmirnist, stringLength, topPartCombinations, varNames.length / 2);
    }

    private void generateLeftPartCombinations() {
        int mintermRozmirnist = varNames.length / 2;
        int stringLength = Integer.toBinaryString((int) Math.pow(2, mintermRozmirnist) - 1).length();
        leftPartCombinations = new LinkedList<Minterm>();
        generateMinterms(mintermRozmirnist, stringLength, leftPartCombinations, 0);
    }

    private void generateMinterms(int mintermRozmirnist, int stringLength, List<Minterm> listToWrite, int startVarName) {
        /*for (int i = 0; i < Math.pow(2, mintermRozmirnist); i++) {
            String func = myFrom10ToBinary(stringLength, i);
            List<Variable> variables = new LinkedList<>();
            for (int j = 0; j < stringLength; j++) {
                variables.add(new Variable(varNames[j + startVarName], func.charAt(j) == '0' ? true : false));
            }
            listToWrite.add(new Minterm(i, false, variables));
        }*/

        for (int i = 0; i < Math.pow(2, mintermRozmirnist); i++) {
            listToWrite.add(new Minterm(i, false, new LinkedList<Variable>()));
        }

        int amountOfVarsToBeSimple = listToWrite.size() / 2;

        for (int v = 0; v < mintermRozmirnist; v++) {
            if (v == 0) {
                for (int i = 0; i < listToWrite.size(); i++) {
                    listToWrite.get(i).getVariables().add(new Variable(varNames[v + startVarName], i < amountOfVarsToBeSimple ? false : true));
                }
            } else {
                int counter = 0;
                boolean insertingTrue = true;
                boolean insertingStart = true;
                for (int i = 0; i < listToWrite.size() / amountOfVarsToBeSimple + 1; i++) {
                    if (insertingStart) {
                        for (int j = 0; j < amountOfVarsToBeSimple / 2; j++) {
                            listToWrite.get(counter).getVariables().add(new Variable(varNames[v + startVarName], true));
                            counter++;
                        }
                        insertingStart = false;
                        insertingTrue = false;
                    } else {
                        for (int j = 0; j < amountOfVarsToBeSimple; j++) {
                            if (counter == listToWrite.size()) break;
                            listToWrite.get(counter).getVariables().add(new Variable(varNames[v + startVarName], insertingTrue));
                            counter++;
                        }
                        if (insertingTrue == true) {
                            insertingTrue = false;
                        } else {
                            insertingTrue = true;
                        }
                    }

                }
                amountOfVarsToBeSimple /= 2;
            }
        }
    }
/*
    private boolean defineIsInverted(int variableNum, int currentMintern, int allMinternAmount) {
        if (variableNum == 0) {
            if (currentMintern < allMinternAmount / 2) {
                return false;
            } else {
                return true;
            }
        } else {
            if (variableNum == 1) {
                if (currentMintern > allMinternAmount / 4 && currentMintern < allMinternAmount - allMinternAmount / 4) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }*/

    private void generateVarNames() {
        varNames = new char[romirnist];
        for (int i = 0; i < varNames.length; i++) {
            varNames[i] = (char) (65 + i);
        }
    }

    @Override
    public void mintermValueChanged(int mintermNumber) {
        Minterm minterm = tableIstinnosti.get(mintermNumber - 1);
        if (minterm.isFunctionValue()) {
            minterm.setFunctionValue(false);
        } else {
            minterm.setFunctionValue(true);
        }
        updateTableIstinnostoUI();
    }


    @Override
    public void showVariable(Minterm minterm) {
        System.out.println("Выбран минтерм " + minterm);
        showVariablesCouldBeScleyani(minterm);
    }

    @Override
    public void showVariablesCouldBeScleyani(Minterm minterm) {
        System.out.println("С ним могут склеиватся следующие минтермы: ");
        List<Minterm> whoCanBeWithMe = new LinkedList<>();
        findSosedi(minterm, whoCanBeWithMe);
        findAnother(minterm, whoCanBeWithMe);
        System.out.println(whoCanBeWithMe);
        whoCanBeScleyani.put(minterm.getNum(), whoCanBeWithMe);
        makeAndMinimizeFunction();
    }

    private void findAnother(Minterm minterm, List<Minterm> whoCanBeWithMe) {
        for (Minterm concrMint : tableIstinnosti) {
            if (minterm.getNum() != concrMint.getNum()) {
                if (watchEqual(concrMint, minterm, new LinkedList<Variable>())) whoCanBeWithMe.add(minterm);
            }
        }
    }

    private void makeAndMinimizeFunction() {
        Function simpleDNF = makeFunctionDNF();
        System.out.println(simpleDNF);
        Function minimDNF = minimizeDNF(simpleDNF);
        System.out.println("Minimised DNF " + minimDNF);
        Function simpleKNF = makeFunctionKNF();
        System.out.println(simpleKNF);
    }

    private Function minimizeDNF(Function simpleDNF) {
        boolean madeChanges = true;
        while (madeChanges) {
            Collections.sort(simpleDNF.getMinterms());
//            System.out.println(simpleDNF.getMinterms());
            int size = simpleDNF.getMinterms().size();
            for (int i = 0; i < simpleDNF.getMinterms().size() - 1; i++) {
                List<Variable> equalVars = new LinkedList<>();
                if (watchEqual(simpleDNF.getMinterms().get(i), simpleDNF.getMinterms().get(i + 1), equalVars)
                        && !equalVars.isEmpty()) {
                    simpleDNF.getMinterms().remove(i);
                    simpleDNF.getMinterms().get(i).setVariables(equalVars);
                }
            }
            if (size - simpleDNF.getMinterms().size() == 0) madeChanges = false;
        }
        return simpleDNF;
    }

    private boolean watchEqual(Minterm minterm, Minterm minterm1, List<Variable> equalVars) {
        boolean res = false;
        int counter = 0;
        for (int i = 0; i < minterm.getVariables().size(); i++) {
            Variable var1 = minterm.getVariables().get(i);
            if (minterm1.getVariables().size() - minterm.getVariables().size() != 0) return false;
            if (var1.equals(minterm1.getVariables().get(i))) {
                equalVars.add(new Variable(var1.getName(), var1.isInverted()));
                res = true;
            } else {
                if (var1.getName() != minterm1.getVariables().get(i).getName()) {
                    res = false;
//                    System.out.println(var1.getName() + " ws "+minterm1.getVariables().get(i).getName() + "  " + "Res = false");
                } else {
                    counter++;
                    if (counter == 2) return false;
                }
            }
        }
        return res;
    }
    private Function makeFunctionKNF() {
        List<Minterm> notNullMin = new LinkedList<>();
        for (Minterm minterm : tableIstinnosti) {
            if (!minterm.isFunctionValue()) {
                notNullMin.add(copyInverseMinterm(minterm));
            }
        }
        return new KNFFunction(notNullMin);
    }

    private Minterm copyInverseMinterm(Minterm minterm) {
        List<Variable> vars = new LinkedList<>();
        for (Variable variable : minterm.getVariables()) {
            Variable variable1 = new Variable(variable.getName(), !variable.isInverted());
            vars.add(variable1);
        }
        return new Minterm(minterm.getNum(), minterm.isFunctionValue(), vars);
    }

    private Function makeFunctionDNF() {
        List<Minterm> notNullMin = new LinkedList<>();
        for (Minterm minterm : tableIstinnosti) {
            if (minterm.isFunctionValue()) {
                notNullMin.add(copyMinterm(minterm));
            }
        }
        return new DNFFunction(notNullMin);
    }

    private Minterm copyMinterm(Minterm minterm) {
        List<Variable> vars = new LinkedList<>();
        for (Variable variable : minterm.getVariables()) {
            vars.add(variable);
        }
        return new Minterm(minterm.getNum(), minterm.isFunctionValue(), vars);
    }


    private void findSosedi(Minterm minterm, List<Minterm> whoCanBeWithMe) {
        int minternNum = minterm.getNum();
        whoCanBeWithMe.add(minterm);
        if (minternNum <= topPartCombinations.size()) {
            if (minternNum - topPartCombinations.size() == 0) {
//rightmost top element
                whoCanBeWithMe.add(findMintermBuNum(1));
                whoCanBeWithMe.add(findMintermBuNum(topPartCombinations.size() * leftPartCombinations.size()));
                whoCanBeWithMe.add(findMintermBuNum(topPartCombinations.size() * leftPartCombinations.size() - topPartCombinations.size() + 1));
                whoCanBeWithMe.add(findMintermBuNum(minternNum - 1));
                whoCanBeWithMe.add(findMintermBuNum(minternNum + topPartCombinations.size()));
            } else {
                if (minterm.getNum() == 1) {
                    whoCanBeWithMe.add(findMintermBuNum(topPartCombinations.size()));
                    whoCanBeWithMe.add(findMintermBuNum(topPartCombinations.size() * leftPartCombinations.size()));
                    whoCanBeWithMe.add(findMintermBuNum(topPartCombinations.size() * leftPartCombinations.size() - topPartCombinations.size() + 1));
                    whoCanBeWithMe.add(findMintermBuNum(minternNum + 1));
                    whoCanBeWithMe.add(findMintermBuNum(minternNum + topPartCombinations.size()));
                } else {
                    whoCanBeWithMe.add(findMintermBuNum(minternNum - 1));
                    whoCanBeWithMe.add(findMintermBuNum(minternNum + 1));
                    whoCanBeWithMe.add(findMintermBuNum(minternNum + topPartCombinations.size()));
                    whoCanBeWithMe.add(findMintermBuNum(minternNum + topPartCombinations.size() * (leftPartCombinations.size() - 1)));
                }
            }
        } else {
            if (minternNum % topPartCombinations.size() == 0) {
                if (minternNum < topPartCombinations.size() * leftPartCombinations.size()) {
//                rigt elem
                    whoCanBeWithMe.add(findMintermBuNum(minternNum - topPartCombinations.size()));
                    whoCanBeWithMe.add(findMintermBuNum(minternNum + topPartCombinations.size()));
                    whoCanBeWithMe.add(findMintermBuNum(minternNum - 1));
                    whoCanBeWithMe.add(findMintermBuNum(minternNum - topPartCombinations.size() + 1));
                } else {
//                    last elem
                    whoCanBeWithMe.add(findMintermBuNum(1));
                    whoCanBeWithMe.add(findMintermBuNum(topPartCombinations.size()));
                    whoCanBeWithMe.add(findMintermBuNum(topPartCombinations.size() * leftPartCombinations.size() - topPartCombinations.size() + 1));
                    whoCanBeWithMe.add(findMintermBuNum(minternNum - 1));
                    whoCanBeWithMe.add(findMintermBuNum(minternNum - topPartCombinations.size()));
                }
            } else {
                if (minternNum % topPartCombinations.size() == 1 && minternNum != 1) {
                    if (minternNum == topPartCombinations.size() * (leftPartCombinations.size() - 1) + 1) {
//                        last row left element
                        whoCanBeWithMe.add(findMintermBuNum(1));
                        whoCanBeWithMe.add(findMintermBuNum(topPartCombinations.size()));
                        whoCanBeWithMe.add(findMintermBuNum(minternNum + 1));
                        whoCanBeWithMe.add(findMintermBuNum(minternNum - topPartCombinations.size()));
                        whoCanBeWithMe.add(findMintermBuNum(topPartCombinations.size() * leftPartCombinations.size()));
                    } else {
//                       left elements
                        whoCanBeWithMe.add(findMintermBuNum(minternNum + topPartCombinations.size()));
                        whoCanBeWithMe.add(findMintermBuNum(minternNum + 1));
                        whoCanBeWithMe.add(findMintermBuNum(minternNum - topPartCombinations.size()));
                        whoCanBeWithMe.add(findMintermBuNum(minternNum + topPartCombinations.size() - 1));
                    }
                } else {
                    if (minternNum < topPartCombinations.size() * leftPartCombinations.size() &&
                            minternNum > topPartCombinations.size() * (leftPartCombinations.size() - 1) + 1) {
//                        down elements
                        whoCanBeWithMe.add(findMintermBuNum(minternNum - 1));
                        whoCanBeWithMe.add(findMintermBuNum(minternNum + 1));
                        whoCanBeWithMe.add(findMintermBuNum(minternNum - topPartCombinations.size()));
                        whoCanBeWithMe.add(findMintermBuNum(minternNum + minternNum % topPartCombinations.size()));
                    } else {
//                        center elements
                        whoCanBeWithMe.add(findMintermBuNum(minternNum - 1));
                        whoCanBeWithMe.add(findMintermBuNum(minternNum + 1));
                        whoCanBeWithMe.add(findMintermBuNum(minternNum - topPartCombinations.size()));
                        whoCanBeWithMe.add(findMintermBuNum(minternNum + topPartCombinations.size()));
                    }
                }
            }
        }
    }

    private Minterm findMintermBuNum(int i) {
        for (Minterm minterm : tableIstinnosti) {
            if (minterm.getNum() == i) return minterm;
        }
        return null;
    }

    private void updateTableIstinnostoUI() {
        tableIstinnostiFrame.getTableIstinnosti().updateUI();
    }

    private String myFrom10ToBinary(int sringLength, int nubmer) {
        String res = Integer.toBinaryString(nubmer);
        int diff = sringLength - res.length();
        if (diff != 0) {
            for (int i = 0; i < diff; i++) {
                res = "0" + res;
            }
        }
        return res;
    }

    private class MyRow implements Map.Entry<Minterm, List<Minterm>> {

        private Minterm key;
        private List<Minterm> val;

        public MyRow(Minterm key, List<Minterm> val) {
            this.key = key;
            this.val = val;
        }

        @Override
        public Minterm getKey() {
            return key;
        }

        @Override
        public List<Minterm> getValue() {
            return val;
        }

        @Override
        public List<Minterm> setValue(List<Minterm> value) {
            this.val = value;
            return val;
        }

        @Override
        public String toString() {
            return "MyRow{" +
                    "key=" + key +
                    ", val=" + val +
                    '}';
        }
    }

    private class MyRowTableModel extends AbstractTableModel {

        private List<Map.Entry<Minterm, List<Minterm>>> rows;
        private String[] colNames;
        private Map<Integer, List<MyButton>> buttons;

        public MyRowTableModel(List<Map.Entry<Minterm, List<Minterm>>> rows, String[] colNames) {
            this.rows = rows;
            this.colNames = colNames;
            initButtons();
        }

        private void initButtons() {
            this.buttons = new HashMap<>();
            int counter = 0;
            for (Map.Entry<Minterm, List<Minterm>> row : rows) {
                List<Minterm> minterms = row.getValue();
                List<MyButton> buttons = new LinkedList<>();
                for (final Minterm val : minterms) {
                    final MyButton button = new MyButton(val.getNum());
                    button.setText(val.isFunctionValue() ? "1" : "0");
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            mintermValueChanged(button.getNumOfMinterm());
                            if (button.getText().equals("0")) {
                                button.setText("1");
                                showVariable(val);
                                button.setMustChangeColor(true);
                            } else {
                                button.setMustChangeColor(false);
                                whoCanBeScleyani.remove(val.getNum());
                                button.setText("0");
                            }
                            tableCarno.updateUI();
                        }
                    });
                    buttons.add(button);
                }
                this.buttons.put(counter, buttons);
                counter++;
            }
        }

        public int getRowCount() {
            return rows.size();
        }

        public int getColumnCount() {
            return colNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return colNames[column];
        }

        public Object getValueAt(int rowIndex, int columnIndex) {

            Map.Entry<Minterm, List<Minterm>> selected = rows.get(rowIndex);
            Minterm minterm = selected.getKey();
            List<Minterm> values = selected.getValue();

            switch (columnIndex) {
                case 0:
                    return minterm.toString();
                default:
                    return buttons.get(rowIndex).get(columnIndex - 1);
            }
        }

        public Collection<List<MyButton>> getButtons() {
            return buttons.values();
        }


    }


    private class MyButton extends JButton {
        private int numOfMinterm;

        private boolean mustChangeColor = false;

        public MyButton(int numOfMinterm) {
            super();
            this.numOfMinterm = numOfMinterm;
        }

        public int getNumOfMinterm() {
            return numOfMinterm;
        }

        public boolean isMustChangeColor() {
            return mustChangeColor;
        }

        public void setMustChangeColor(boolean mustChangeColor) {
            this.mustChangeColor = mustChangeColor;
        }

        @Override
        public String toString() {
            return "MyButton{" +
                    "numOfMinterm=" + numOfMinterm +
                    "} ";
        }
    }

    private static class JTableButtonRenderer implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            MyButton button = (MyButton) value;
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                if (button.mustChangeColor) {
                    button.setForeground(table.getForeground());
                    button.setBackground(Color.GREEN);
                } else {
                    button.setForeground(table.getForeground());
                    button.setBackground(UIManager.getColor("Button.background"));
                }
            }
            return button;
        }
    }

    private static class JTableButtonMouseListener extends MouseAdapter {
        private final JTable table;

        public JTableButtonMouseListener(JTable table) {
            this.table = table;
        }

        public void mouseClicked(MouseEvent e) {
            int column = table.getColumnModel().getColumnIndexAtX(e.getX());
            int row = e.getY() / table.getRowHeight();

            if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
                Object value = table.getValueAt(row, column);
                if (value instanceof JButton) {
                    ((JButton) value).doClick();
                }
            }
        }
    }

    private class TabliciaIstinnostiTableModel extends AbstractTableModel {

        private List<Minterm> minterms;
        private String[] colNames;

        public TabliciaIstinnostiTableModel(List<Minterm> rows, String[] colNames) {
            this.minterms = rows;
            this.colNames = colNames;
            initSorter();
        }

        private void initSorter() {
            TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(this);
            tableIstinnostiFrame.getTableIstinnosti().setRowSorter(sorter);
            List<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
            sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
            sorter.setSortKeys(sortKeys);
        }

        public int getRowCount() {
            return minterms.size();
        }

        public int getColumnCount() {
            return colNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return colNames[column];
        }

        public Object getValueAt(int rowIndex, int columnIndex) {

            Minterm selected = minterms.get(rowIndex);
            List<Variable> variables = selected.getVariables();

            if (columnIndex == getColumnCount() - 1) {
                return selected.isFunctionValue() ? "1" : "0";
            }

            return variables.get(columnIndex).isInverted() ? "0" : "1";
        }
    }


}
