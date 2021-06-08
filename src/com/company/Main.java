package com.company;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        //test:
        //KryptEncoder crypt = new KryptEncoder("C:\\Temp\\POS\\enc\\Testkrypt.txt", "1485130982", "");
        //crypt.krypt();

        KryptDecoder encrypt = new KryptDecoder("C:\\Temp\\POS\\enc\\Griess.kry", "", "1485130982");
        encrypt.unKrypt();
}
}
