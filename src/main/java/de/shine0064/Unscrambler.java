package de.shine0064;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Unscrambler {
    public List<String> words = new ArrayList<>();
    public int minLength;
    public int maxLength;

    public Unscrambler(int minLength, int maxLength) {
        this.minLength = minLength;
        this.maxLength = maxLength;
    }


    public List<String> unscramble(String scrambled, boolean sort) {
        List<String> result = new ArrayList<>();

        words.forEach((word) -> {
            if (word.length() > maxLength || word.length() < minLength) return;

            boolean addWord = true;
            for (char c : word.toCharArray()) {
                int scrambled_count = StringUtils.countMatches(scrambled, c);
                if (scrambled_count <= 0)
                    break;

                int word_count = StringUtils.countMatches(word, c);
                if ((word_count < 1)
                    || (word_count > scrambled_count)) {
                    addWord = false;
                    break;
                }
            }
            if (addWord)
                result.add(word);
        });

        if (sort) {
            result.sort((x, y) -> y.length() - x.length());
        }

        return result;
    }
}
