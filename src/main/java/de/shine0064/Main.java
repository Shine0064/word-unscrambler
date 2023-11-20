package de.shine0064;

import org.apache.commons.cli.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.shine0064.FileSystem.readFileIntoList;

public class Main {
    public static CommandLineParser parser = new DefaultParser();
    public static HelpFormatter formatter = new HelpFormatter();
    public static CommandLine cmd;

    public static Option helpOption = new Option("h", "help", false, "Show this help message");
    public static Option dictOption = new Option("d", "dictionary", true, "The dictionary to use, defaults to words_alpha.txt within the same folder.");
    public static Option wordOption = new Option("w", "word", true, "The scrambled word to solve, if not provided, will read from stdin. Meant for use in scripts!");
    public static Option minOption = new Option("m", "minimum-length", true, "The minimum length of the word, default is 2.");
    public static Option maxOption = new Option("M", "maximum-length", true, "The maximum length of the word, default is 10.");

    public static void main(String[] args) {
        Options options = new Options();

        helpOption.setRequired(false);
        options.addOption(helpOption);

        dictOption.setRequired(false);
        options.addOption(dictOption);

        wordOption.setRequired(false);
        options.addOption(wordOption);

        minOption.setRequired(false);
        options.addOption(minOption);

        maxOption.setRequired(false);
        options.addOption(maxOption);

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("java -jar word_unscrambler-1.0-all.jar", options);
            System.exit(1);
            return;
        }

        if (cmd.hasOption(helpOption)) {
            formatter.printHelp("java -jar word_unscrambler-1.0-all.jar", options);
            System.exit(0);
            return;
        }

        run();
    }

    public static void run() {
        int minLength;
        int maxLength;

        try {
            minLength = Integer.parseInt(cmd.getOptionValue(minOption, "2"));
            maxLength = Integer.parseInt(cmd.getOptionValue(maxOption, "10"));
        } catch (NumberFormatException e) {
            Matcher m = Pattern.compile("\".+\"").matcher(e.getMessage());
            if (m.find())
                System.out.printf("%s is not a valid number!", m.group(0));

            System.exit(1);
            return;
        }

        Unscrambler unscrambler = new Unscrambler(minLength, maxLength);

        Path dictPath = Path.of(cmd.getOptionValue(dictOption, "words_alpha.txt"));
        File dictFile = new File(String.valueOf(dictPath));

        try {
            readFileIntoList(dictFile, unscrambler.words);
        } catch (FileNotFoundException e) {
            try {
                System.out.printf("File %s not found!", dictFile.getCanonicalFile());
            } catch (IOException ignored) {}
            System.exit(1);
            return;
        }

        String scrambledFlag = cmd.getOptionValue(wordOption);
        if (scrambledFlag != null) {
            List<String> result = unscrambler.unscramble(scrambledFlag, true);
            StringJoiner joiner = new StringJoiner(", ");
            for (String s : result)
                joiner.add(s);
            String resString = joiner.toString();

            System.out.println(resString);
            System.exit(0);
        }

        System.out.printf("Loaded %d words from %s\n", unscrambler.words.size(), dictPath);

        Scanner input = new Scanner(System.in);
        //noinspection InfiniteLoopStatement
        while (true) {
            System.out.print("Input: ");
            String scrambled = input.nextLine();
            List<String> result = unscrambler.unscramble(scrambled, true);
            StringJoiner joiner = new StringJoiner(", ");
            for (String s : result)
                joiner.add(s);
            String resString = joiner.toString();

            System.out.println(resString);
        }
    }
}