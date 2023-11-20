package de.shine0064;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class FileSystem {
    public static void readFileIntoList(File file, List<String> list) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        while(scanner.hasNextLine())
            list.add(scanner.nextLine());
        scanner.close();
    }
}
