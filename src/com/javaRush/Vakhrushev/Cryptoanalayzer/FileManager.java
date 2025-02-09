package com.javaRush.Vakhrushev.Cryptoanalayzer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class FileManager {

    public static void FileManagerReader(String filePath) throws IOException {
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
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }


    public static void writerAndReaderFile(String filePath) throws IOException {

        Path BrutalForceEncryptPath = Paths.get(filePath);
        ;
        Path BrutalForceDecryptPath = Paths.get(filePath);
        ;

        int bufferSize = 4096;

        try (InputStream inputStream = Files.newInputStream(BrutalForceEncryptPath);
             OutputStream outputStream = Files.newOutputStream(BrutalForceDecryptPath)) {

            byte[] buffer = new byte[bufferSize];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("Файл успешно скопирован!");

        } catch (IOException e) {
            System.err.println("Ошибка при обработке файла: " + e.getMessage());
        }
    }
}




