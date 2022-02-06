package wordle.exceptions;


import wordle.controller.Controller;

public abstract class WordleException extends Exception{

    public abstract void resolve(Controller controller);
}
