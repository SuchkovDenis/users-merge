package ru.suchkov.merger;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Необходимо ввести путь до файла!");
        }
        else {
            Map<String, Set<String>> input = read(args[0]);
            if (input == null) {
                System.out.println("Ошибка чтения файла");
            } else {
                print(MergeUtil.merge(input));
            }
        }
    }

    /**
     * Parse input file to Map<String, Set<String>>
     *
     * @param pathToFile - path to file with users and emails
     * @return map of users and related emails if read was successful, null otherwise
     */
    private static Map<String, Set<String>> read(String pathToFile) {
        try (FileInputStream is = new FileInputStream(new File(pathToFile));
             InputStreamReader inputStreamReader = new InputStreamReader(is);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            Map<String, Set<String>> map = new HashMap<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!"".equals(line.replaceAll("\\s+", ""))) {
                    String[] tokens = line.split("\\s+");
                    if (tokens.length > 1) {
                        map.put(
                            tokens[0],
                            new HashSet<>(Arrays.asList(tokens).subList(1, tokens.length))
                        );
                    }
                }
            }
            return map;
        } catch (IOException ex) {
            return null;
        }
    }

    /**
     * Print map in pretty format
     *
     * @param map map should be printed
     */
    private static void print(Map<String, Set<String>> map) {
        for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
            System.out.print(entry.getKey() + " ");
            for (String email : entry.getValue()) {
                System.out.print(email + " ");
            }
            System.out.println();
        }
    }
}
