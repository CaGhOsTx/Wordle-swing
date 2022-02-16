package wordle.view;

public class Stats {
    String word;
    Float avg;
    int score;

    public Stats(String word, Float avg, int score) {
        this.word = word;
        this.avg = avg;
        this.score = score;
    }

    public String getWord() {
        return word;
    }

    public Float getAvg() {
        return avg;
    }

    public int getScore() {
        return score;
    }
}
