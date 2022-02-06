package wordle.controller;

import wordle.model.Model;
import wordle.exceptions.WrongPathException;
import wordle.view.View;

import java.awt.event.KeyListener;

public class Controller {
    private static Controller CONTROLLER;
    public  View VIEW = View.getInstance();
    public  Model MODEL;
    public final KeyListener MAIN = ControllerListeners.MAIN.linkTo(this);
    public final KeyListener RESTART = ControllerListeners.RESTART.linkTo(this);

    private Controller() {
        try {
            MODEL = Model.getInstance();
        } catch (WrongPathException e) {
            e.resolve(CONTROLLER);
        }
        VIEW.addKeyListener(MAIN);
    }

    public static Controller getInstance() {
        CONTROLLER = CONTROLLER == null ? new Controller() : CONTROLLER;
        CONTROLLER.updateTries();
        return CONTROLLER;
    }

    public void updateTries() {
        CONTROLLER.VIEW.setTries(CONTROLLER.MODEL.getTries());
    }
}
