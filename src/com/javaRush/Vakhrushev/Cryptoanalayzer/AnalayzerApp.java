package com.javaRush.Vakhrushev.Cryptoanalayzer;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class AnalayzerApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InputValidator validator = new InputValidator();
        Set<String> dictionary = new HashSet<>();
        dictionary.add("и");
        dictionary.add("в");
        dictionary.add("не");
        dictionary.add("на");
        dictionary.add("что");
        dictionary.add("он");
        dictionary.add("сказал");
        dictionary.add("как");
        dictionary.add("это");

        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Зашифровать файл");
            System.out.println("2. Расшифровать файл");
            System.out.println("3. Brute Force расшифровка");
            System.out.println("4. Проверка на палиндром");
            System.out.println("5. Проверка возраста персоны");
            System.out.println("0. Выход");
            System.out.print("Ваш выбор: ");

            int choice;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Считываем перевод строки после числа
            } else {
                System.out.println("Введите корректное число!");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Введите путь к файлу: ");
                    String encryptPath = scanner.nextLine();
                    if (!validator.isFileExist(encryptPath)) break;
                    System.out.print("Введите ключ для шифрования: ");
                    int encryptKey = scanner.nextInt();
                    scanner.nextLine();
                    String encryptedFilePath = CaesarCipher.encrypt(encryptPath, encryptKey);
                    System.out.println("Файл зашифрован. Результат сохранён в: " + encryptedFilePath);
                    break;
                case 2:
                    System.out.print("Введите путь к файлу: ");
                    String decryptPath = scanner.nextLine();
                    if (!validator.isFileExist(decryptPath)) break;
                    System.out.print("Введите ключ для расшифровки: ");
                    int decryptKey = scanner.nextInt();
                    scanner.nextLine();
                    String decryptedFilePath = CaesarCipher.decrypt(decryptPath, decryptKey);
                    System.out.println("Файл расшифрован. Результат сохранён в: " + decryptedFilePath);
                    break;
                case 3:
                    System.out.print("Введите путь к файлу: ");
                    String brutePath = scanner.nextLine();
                    if (!validator.isFileExist(brutePath)) break;
                    String bruteFilePath = CaesarCipher.decryptBruteForce(brutePath, dictionary);
                    System.out.println("Brute Force завершён. Результат сохранён в: " + bruteFilePath);
                    break;
                case 4:
                    System.out.print("Введите слово для проверки на палиндром: ");
                    String word = scanner.nextLine();
                    SecretMessage message = new SecretMessage(word);
                    System.out.println("Палиндром: " + message.isPalindrome());
                    break;
                case 5:
                    System.out.print("Введите имя: ");
                    String name = scanner.nextLine();
                    System.out.print("Введите возраст: ");
                    int age = scanner.nextInt();
                    scanner.nextLine();
                    Person person = new Person(name, age);
                    System.out.println(person);
                    System.out.println("Совершеннолетний: " + person.isAdult());
                    break;
                case 0:
                    System.out.println("Выход из программы.");
                    return;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, введите число от 0 до 5.");
            }
        }
    }
}
