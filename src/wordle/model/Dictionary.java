package wordle.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Dictionary {

    Set<String> words;
    List<String> wordles;
    ThreadLocalRandom random = ThreadLocalRandom.current();

    public Dictionary(Path pathToWords, Path pathToWordles) throws IOException {
        words = Files.lines(pathToWords)
                .collect(Collectors.toSet());
        wordles = Files.lines(pathToWordles)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public String getRandomWord() {
        return wordles.get(random.nextInt(wordles.size()));
    }

    public boolean isValidWord(String target) {
        return words.contains(target);
    }

    public List<String> getWordles() {
        return new ArrayList<>(words);
    }
}
