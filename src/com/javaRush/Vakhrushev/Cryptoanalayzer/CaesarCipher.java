
package com.javaRush.Vakhrushev.Cryptoanalayzer;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CaesarCipher {

    // Алфавит
    public static final List<Character> ALPHABET = Arrays.asList(
            'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з', 'и', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у',
            'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', '.', ',', '«', '»',
            ':', '!', '?', ' ', 'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж', 'З', 'И', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У',
            'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я', 'Й', 'й', 'ё', 'Ё'
    );

    public static String encrypt(String filePath, int key) {
        return processFile(filePath, key, true);
    }

    public static String decrypt(String filePath, int key) {
        return processFile(filePath, key, false);
    }

    private static String processFile(String filePath, int key, boolean isEncrypt) {
        Path inputPath = Paths.get(filePath);
        Path outputPath = generateOutputPath(filePath, isEncrypt ? "_encrypted" : "_decrypted");

        try (BufferedReader reader = Files.newBufferedReader(inputPath);
             BufferedWriter writer = Files.newBufferedWriter(outputPath)) {

            String line;
            while ((line = reader.readLine()) != null) {
                StringBuilder processedLine = new StringBuilder();
                for (char ch : line.toCharArray()) {
                    int index = ALPHABET.indexOf(ch);
                    if (index != -1) {
                        int shift = isEncrypt ? key : -key;
                        int newIndex = (index + shift + ALPHABET.size()) % ALPHABET.size();
                        processedLine.append(ALPHABET.get(newIndex));
                    } else {
                        processedLine.append(ch); // неизвестный символ — оставим как есть
                    }
                }
                writer.write(processedLine.toString());
                writer.newLine();
            }

            System.out.println((isEncrypt ? "Зашифрованный" : "Расшифрованный") + " файл сохранён по пути: " + outputPath);

        } catch (IOException e) {
            System.err.println("Ошибка обработки файла: " + e.getMessage());
        }

        return outputPath.toString();
    }

    public static String decryptBruteForce(String filePath, Set<String> dictionary) {
        String bestResult = "";
        int bestScore = 0;

        for (int key = 1; key < ALPHABET.size(); key++) {
            StringBuilder result = new StringBuilder();

            try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    StringBuilder lineDecrypted = new StringBuilder();
                    for (char ch : line.toCharArray()) {
                        int index = ALPHABET.indexOf(ch);
                        if (index != -1) {
                            int newIndex = (index - key + ALPHABET.size()) % ALPHABET.size();
                            lineDecrypted.append(ALPHABET.get(newIndex));
                        } else {
                            lineDecrypted.append(ch);
                        }
                    }
                    result.append(lineDecrypted).append("\n");
                }
            } catch (IOException e) {
                System.err.println("Ошибка при brute force расшифровке: " + e.getMessage());
            }

            int score = evaluateText(result.toString(), dictionary);
            if (score > bestScore) {
                bestScore = score;
                bestResult = result.toString();
            }
        }

        // сохраняем результат в файл
        Path outputPath = generateOutputPath(filePath, "_brute_decrypted");
        try {
            Files.write(outputPath, bestResult.getBytes());
            System.out.println("Результат brute force сохранён по пути: " + outputPath);
        } catch (IOException e) {
            System.err.println("Ошибка записи файла после brute force: " + e.getMessage());
        }

        return outputPath.toString();
    }

    private static int evaluateText(String text, Set<String> dictionary) {
        int score = 0;
        String[] words = text.split("\\s+");
        for (String word : words) {
            if (dictionary.contains(word)) {
                score += word.length();
            }
        }
        return score;
    }

    private static Path generateOutputPath(String originalPath, String suffix) {
        Path input = Paths.get(originalPath);
        String fileName = input.getFileName().toString();
        String newFileName = fileName.contains(".")
                ? fileName.substring(0, fileName.lastIndexOf('.')) + suffix + fileName.substring(fileName.lastIndexOf('.'))
                : fileName + suffix;
        return input.resolveSibling(newFileName);
    }
}
