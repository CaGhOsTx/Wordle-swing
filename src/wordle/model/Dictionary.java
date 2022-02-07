package wordle.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Dictionary {

    List<String> words;
    ThreadLocalRandom random = ThreadLocalRandom.current();

    public Dictionary(Path pathToWords) throws IOException {
        words = Files.lines(pathToWords)
                .filter(s -> s.length() == 5)
                .sorted()
                .toList();
    }

    public String getRandomWord() {
        return words.get(random.nextInt(words.size()));
    }

    public boolean isValidWord(String target) {
        return Collections.binarySearch(words, target) >= 0;
    }
}
