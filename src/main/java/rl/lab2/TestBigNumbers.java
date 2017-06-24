package rl.lab2;


import org.apache.log4j.Logger;
import rl.NumberIsNotSimpleException;
import rl.RLOperations;
import rl.ResultFrame;
import rl.lab1.RLChislo;

import javax.swing.*;
import java.math.BigDecimal;
import java.math.BigInteger;

public class TestBigNumbers {

    private static final Logger logger = Logger.getLogger(TestBigNumbers.class);


    public static void main(String[] args) {
        final String[] inputNumber = {null};
        ResultFrame resultFrame = new ResultFrame();
        resultFrame.getStartButton().addActionListener(l -> {
            inputNumber[0] = JOptionPane.showInputDialog("Введіть просте число для опрацювання (наприклад: 29 547 678 871)", "29547678871");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    doCountPQ(inputNumber[0], resultFrame);
                }
            }).start();
        });


    }

    private static void doCountPQ(String val, ResultFrame resultFrame) {
        BigInteger bi = new BigInteger(val, 10);
        String bi_binary = bi.toString(2);

        String message = "Число N у 10й системі числення має вигляд " + val + "\n" + "тоді як у двійквій " + bi_binary;
        logger.info(message);
        resultFrame.appendToOutput(message);

        RLChislo nRL = RLOperations.toRLFromBinary(bi_binary);

        BigDecimal bigDecimal = RLOperations.to10FromRL(nRL);

        String message1 = "Тоді у РЛ формі має вигляд " + nRL;
        logger.info(message1);
        resultFrame.appendToOutput(message1);
        String message2 = "Для перевірки правильності переводу, переведемо знову у десяткову систему та зрівняємо результати\n" +
                "Вхідне N = " + val + "\n" +
                message1 + "\n" +
                "Із  РЛ   = " + bigDecimal.toString();
        logger.info(message2);
        resultFrame.appendToOutput(message2);

        String message3 = "Починаэмо пошук ключів ";
        logger.info(message3);
        resultFrame.appendToOutput(message3);

        RLChislo sqrtN;
        try {
            sqrtN = RLOperations.sqrt(nRL, Integer.MAX_VALUE, true);
        } catch (NumberIsNotSimpleException e) {
            sqrtN = e.getSimpleRL();
        }

        if (RLOperations.encodePQ(nRL, true,
//                тут змінюєш роміжок на якому шукаємо (можливо від 2 до корня) бо на 0 не ділиться,
// якшо ділимо на 1, то це буде результатом,
// а це нас не влащтовує
                new BigInteger("2", 10),
                RLOperations.to10FromRL(sqrtN).toBigInteger(), resultFrame) == null)
            resultFrame.appendToOutput("Невірний добуток, немає результатів");
    }


}
