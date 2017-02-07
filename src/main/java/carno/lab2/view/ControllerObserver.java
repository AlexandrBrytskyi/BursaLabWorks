package carno.lab2.view;

import carno.lab2.controller.IController;

/**
 * Created by alexandr on 02.10.16.
 */
public interface ControllerObserver {


    void notifyAmountChanged(IController controller, int newAmount);
}
