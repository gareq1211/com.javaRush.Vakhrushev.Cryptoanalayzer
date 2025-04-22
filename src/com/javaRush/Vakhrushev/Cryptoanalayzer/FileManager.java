package com.javaRush.Vakhrushev.Cryptoanalayzer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class FileManager {

    // Чтение текста из файла (полный текст, строка за строкой)
    public static String readTextFromFile(String filePath) {
        StringBuilder text = new StringBuilder();
        Path path = Paths.get(filePath);

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }

        return text.toString().trim();
    }

    // Запись текста в указанный файл
    public static void writeTextToFile(String filePath, String text) {
        Path path = Paths.get(filePath);

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(text);
            System.out.println("Текст успешно записан в файл: " + filePath);
        } catch (IOException e) {
            System.err.println("Ошибка при записи файла: " + e.getMessage());
        }
    }

    // Чтение слов из файла для словаря Brute Force
    public static Set<String> readWordsFromFile(String filePath) {
        Set<String> wordSet = new HashSet<>();
        Path path = Paths.get(filePath);

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("[\\s\\p{Punct}]+");
                for (String word : words) {
                    if (!word.isEmpty()) {
                        wordSet.add(word.toLowerCase());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении словаря: " + e.getMessage());
        }

        return wordSet;
    }
}
