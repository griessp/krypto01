
package com.company;

/**
 * Philipp Griess, 4ACIF, 10.60.2021
 * Main Methode zum Aufruf von Ver- und Entschlüsselung nach Vigenere / Heinbach ;-)
 */

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        // Zum Verschlüsseln Textfile angeben
        //KryptEncoder crypt = new KryptEncoder("C:\\Temp\\POS\\enc\\Testkrypt.txt", "1485130982", "");
        //crypt.krypt();

        KryptDecoder encrypt = new KryptDecoder("C:\\Temp\\POS\\enc\\Griess.kry", "", "1485130982");
        encrypt.unKrypt();
}
}
