package wordle.view;

import wordle.controller.Controller;

import java.awt.event.KeyEvent;

public class Player {

    Controller controller;

    public Player(Controller controller) {
        this.controller = controller;
    }

    void guess(int iterations, int speedMult) {
        sleep(1);
        String suggestion;
        String startingWord = "orate";
        float tries = 0;
        for (int j = 0; j < iterations; j++) {
            suggestion = startingWord;
            controller.MODEL.updateSuggestion();
            controller.isRunning = true;
            while (controller.isRunning) {
                for (char c : suggestion.toCharArray()) {
                    KeyEvent event = getEvent(c);
                    controller.MAIN.keyPressed(event);
                    sleep(speedMult);
                }
                suggestion = controller.MODEL.getSuggestion();
                controller.MAIN.keyPressed(getEvent('\n'));
                sleep(10 * speedMult);
            }
            tries += 6 - controller.MODEL.getTries();
            tries /= 2f;
            controller.RESTART.keyPressed(getEvent(' '));
            sleep(speedMult);
        }
        int score = iterations - controller.lostCount;
        controller.lostCount = 0;
        System.out.println("wins: " + score + " / " + iterations);
        System.out.println("avg guesses: " + tries);
    }


    private KeyEvent getEvent(char c) {
        return new KeyEvent(controller.VIEW, 0, 0, 0, KeyEvent.getExtendedKeyCodeForChar(c));
    }

    private void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
