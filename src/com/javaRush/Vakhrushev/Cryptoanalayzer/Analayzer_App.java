package com.javaRush.Vakhrushev.Cryptoanalayzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Analayzer_App {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        InputValidator validator = new InputValidator();
        CaesarCipher caesarCipher = new CaesarCipher();
        FileManager fileManager = new FileManager();

        while (true) {
            System.out.println("/nВыберите пункт меню:");
            System.out.println("1.Зашифровать текст");
            System.out.println("2.Расшифровать текст");
            System.out.println("3.Расшифровать текст Brute force");
            System.out.println("4.Выход");
            System.out.print("Ваш выбор: ");

            int choice = Integer.parseInt(scanner.nextLine());


            switch (choice) {
                case 1:
                    System.out.print("Введите путь к файлу: ");
                    String fileEncryptPath = scanner.nextLine();
                    System.out.print("Ключ:");
                    int keyEncrypt = Integer.parseInt(scanner.nextLine());
                    //if (validator.isKeyValid(keyEncrypt) && validator.isFileExist(fileEncryptPath)) {
                        caesarCipher.encrypt(fileEncryptPath, keyEncrypt);
                  //  }
                    break;
                case 2:
                    System.out.print("Введите путь к файлу: ");
                    String fileDecryptPath = scanner.nextLine();
                    System.out.print("Ключ:");
                    int keyDecrypt = Integer.parseInt(scanner.nextLine());
                    if (validator.isKeyValid(keyDecrypt) && validator.isFileExist(fileDecryptPath)) {
                        caesarCipher.encrypt(fileDecryptPath, keyDecrypt);
                        break;
                    }

                case 3:
                    System.out.print("Введите путь к файлу: ");
                    String BrutalForceEncryptPath = scanner.nextLine();
                    System.out.print("Введите путь к файлу содержащему словарь либо похожий текст автора: ");
                    String dictionaryPath = scanner.nextLine();
                    System.out.print("Введите путь куда записать полученный файл: ");
                    String BrutalForceDecryptPath = scanner.nextLine();
                    Set<String> dictionary = new HashSet<>();

                    try (BufferedReader reader = new BufferedReader(new FileReader(dictionaryPath))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            String[] words = line.split("[\\s\\p{Punct}]+");
                            for (String word : words) {
                                if (!word.isEmpty()) {
                                    dictionary.add(word);
                                }
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("Ошибка при чтении файла: " + e.getMessage());
                    }

                    CaesarCipher.decryptBruteForce(BrutalForceEncryptPath, CaesarCipher.ALPHABET, dictionary);
                    FileManager.writerAndReaderFile(BrutalForceDecryptPath);
                    break;
                case 4:
                    return;

                default:
                    System.out.println("Вы сделали не верный выбор. Еще раз.");
            }

        }
    }
}

