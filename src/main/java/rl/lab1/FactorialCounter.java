package rl.lab1;


import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import rl.RLOperations;
import rl.ResultFrame;

import javax.swing.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;

public class FactorialCounter {

    private static final Logger logger = Logger.getLogger(FactorialCounter.class);

    public BigDecimal countFact10(BigDecimal num) {
        if (num.equals(BigDecimal.ONE)) return BigDecimal.ONE;
        return num.multiply(countFact10(num.subtract(BigDecimal.ONE)), MathContext.DECIMAL64);
    }

    public Double countFact10(Integer num) {
        if (num.equals(1)) return 1d;
        return num * countFact10(--num);
    }

    public RLChislo countFactorial(RLChislo num) {
        RLChislo one = RLOperations.rlFrom10(BigDecimal.ONE);
        if (num.equals(one)) return one;
        return RLOperations.multiply(num, countFactorial(RLOperations.decrement(num)));
    }

    public static void main(String[] args) {
        ResultFrame resultFrame = new ResultFrame();
        resultFrame.getStartButton().addActionListener(e -> {
            int start = Integer.parseInt(JOptionPane.showInputDialog("Введіть число з якого будемо починати рахувати фактораіл (15)"));
            int end = Integer.parseInt(JOptionPane.showInputDialog("Введіть число до якого будемо рахувати (>21)"));

            countFactorialDouble(start,end,resultFrame);
        });
    }

    private static void countFactorialDouble(int start, int end,ResultFrame resultFrame) {

        Map<Integer, Double> differense = new HashMap<>();

        FactorialCounter fc = new FactorialCounter();

        for (int i = start; i < end+1; i++) {
            Double res_10 = fc.countFact10(i);
            String message = "Факторіал звичайним методом " + i + " = " + res_10;
            logger.info(message);
            resultFrame.appendToOutput(message);

            RLChislo factorial10 = fc.countFactorial(RLOperations.rlFrom10(BigDecimal.valueOf(i)));
            BigDecimal rlFloat = RLOperations.to10FromRL(factorial10);
            String message1 = "Факторіал РЛ числа " + i + " = " + rlFloat.toEngineeringString() + " , " + " \nРЛ запис: " + factorial10;
            logger.info(message1);
            resultFrame.appendToOutput(message1);

            BigDecimal subtract = rlFloat.subtract(BigDecimal.valueOf(res_10));
            String message2 = "Різниця " + subtract + "\n\n";
            logger.info(message2);
            resultFrame.appendToOutput(message2);


            differense.put(i, subtract.doubleValue());
        }
        XYSeries series = new XYSeries("Похибка");
        for (Map.Entry<Integer, Double> integerDoubleEntry : differense.entrySet()) {
            series.add(integerDoubleEntry.getKey(), integerDoubleEntry.getValue());
        }

        XYDataset dataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart("Похибка обчислень факторіалу за стандартом IEE754 та РЛ арифметикою"
                , "ITEРАЦІЯ", "похибка",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, true);

        JFrame frame =
                new JFrame("Графік");

        frame.getContentPane()
                .add(new ChartPanel(chart));
        frame.setSize(900, 700);
        frame.setVisible(true);
    }


}


//2.092279e+13
//-4.056482E31