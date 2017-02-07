package carno.lab3.controller;

import carno.lab3.model.Minterm;

/**
 * Created by alexandr on 02.10.16.
 */
public interface IController {

    void amountOfVariablesChanged(int newRozmirnist);

    void createTableIstinnosti();

    void buildKarno();

    void mintermValueChanged(int mintermNumber);

    void showVariable(Minterm minterm);

    void showVariablesCouldBeScleyani(Minterm minterm);
}
