
package com.javaRush.Vakhrushev.Cryptoanalayzer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class CaesarCipher {

    static final List ALPHABET = Arrays.asList('а', 'б',
            'в', 'г', 'д', 'е', 'ж', 'з', 'и', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у',
            'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'я', '.', ',', '«', '»',
            ':', '!', '?', ' ', 'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж', 'З', 'И', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У',
            'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Я', 'й', 'Й', 'ю', 'Ю', 'ё', 'Ё');

    public static String encrypt(String filePath, int key) throws IllegalArgumentException{
        Path Path = Paths.get(filePath);
        Set<String> wordSet = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(Path)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("[\\s\\p{Punct}]+");
                for (String word : words) {
                    if (!word.isEmpty()) {
                        wordSet.add(word);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
        StringBuilder encryptedText = new StringBuilder();
        int alphabetLength = ALPHABET.size();

        for (char ch : wordSet.toString().toCharArray()) {
            int index = ALPHABET.indexOf(ch);
            if (index == -1) {
                throw new IllegalArgumentException("Неизвестный символ: " + ch + "! You are under ATTACKED");
            }
            int newIndex = (index + key + alphabetLength) % alphabetLength;
            encryptedText.append(ALPHABET.indexOf(newIndex));
        }
        return encryptedText.toString();
    }


    public static String decrypt(String filePath, int key, List ALPHABET) throws IllegalArgumentException{
        StringBuilder decryptedText = new StringBuilder();
        int alphabetLength = CaesarCipher.ALPHABET.size();

        for (char ch : filePath.toCharArray()) {
            int index = CaesarCipher.ALPHABET.indexOf(ch);
            if (index == -1) {
                throw new IllegalArgumentException("Неизвестный символ: " + ch + "! You are under ATTACKED!");
            }
            int newIndex = (index - key + alphabetLength) % alphabetLength;
            decryptedText.append(CaesarCipher.ALPHABET.indexOf(newIndex));
        }
        return decryptedText.toString();

    }

    public static String decryptBruteForce(String filePath, List  ALPHABET, Set <String> dictionary) {
        int bestScore = 0;
        String bestDecryption = "";

        for (int shift = 1; shift < ALPHABET.size(); shift++) {
            String decryptedText = decrypt(filePath, shift, ALPHABET);
            int score = evaluateText(decryptedText, dictionary);

            if (score > bestScore) {
                bestScore = score;
                bestDecryption = decryptedText;
            }
        }
        return bestDecryption;
    }

    private static int evaluateText(String decryptedText, Set<String> dictionary) {
        int score = 0;
        String[] words = decryptedText.split("\\s+ ");

        for (String word : words) {
            if (dictionary.contains(word)) {
                score += word.length();
            }
        }
        return score;
    }




}

