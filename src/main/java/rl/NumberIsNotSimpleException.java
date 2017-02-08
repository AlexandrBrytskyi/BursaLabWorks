package rl;

import rl.lab1.RLChislo;


public class NumberIsNotSimpleException extends Throwable {

    private RLChislo simpleRL;

    public NumberIsNotSimpleException(String message, RLChislo simpleRL) {
        super(message);
        this.simpleRL = simpleRL;
    }

    public RLChislo getSimpleRL() {
        return simpleRL;
    }
}
