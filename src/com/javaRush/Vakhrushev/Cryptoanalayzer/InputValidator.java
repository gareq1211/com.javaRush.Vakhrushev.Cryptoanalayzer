package com.javaRush.Vakhrushev.Cryptoanalayzer;

import java.io.File;

public class InputValidator {
    public boolean isFileExist(String filePath) {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            System.out.println("Файл не существует");
            return false;
        }
        return true;
    }

    public boolean isKeyValid(int key) {
        return key >= 0 && key < CaesarCipher.ALPHABET.size();
    }
}