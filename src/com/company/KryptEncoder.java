package com.company;
/**
 * Verschlüssselt vereinfachte Textfiles - nur Großbuchstaben, SPACE, LF
 * mit einer mindestens 10stelligen Nummernkombination nach Vigenere
 */

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class KryptEncoder {
    //input Text File
    //private BufferedReader inputFile;
    private File inputFile;
    //output Text File
    private BufferedWriter outputFile;
    //output Code File

    //10stelliger KEY aus Ziffern
    private final int KEYLENGTH = 10;
    private int[] key = new int[KEYLENGTH];


    //Konstruktor übernimmt Dateiname
    //übernimmt Schlüsseltext aus Ziffern und erzeugt daraus Schlüssel
    // erzeugt automatisch name des outputfiles mit endung: .kry
    // öffnet output file

    public KryptEncoder(String inputFileName, String keyString, String outputFileName) {
        try {
            //inputFile = new BufferedReader(new FileReader(inputFileName));
            inputFile = new File(inputFileName);
            //kein outputfilename? -> erzeuge output FileName aus Inputfilenname
            if (outputFileName.isEmpty()) {
                if (inputFileName.lastIndexOf('.') != -1) //Punkt vorhanden?
                {
                    outputFileName =
                            inputFileName.substring(0, inputFileName.lastIndexOf('.'))
                                    + ".kry";
                } else { //kein Punkt
                    outputFileName = inputFileName + ".kry";
                }
            }
            //öffne OutputFile
            outputFile = new BufferedWriter(new FileWriter(outputFileName));

        } catch (IOException e) {
            e.printStackTrace();
        }
        //erzeuge Schlüssel
        for (int i = 0; i < KEYLENGTH; i++) {

            key[i] = Character.getNumericValue(keyString.charAt(i % keyString.length()));
        }
        //test
        System.out.println(key);
    }

    /**
     * Löscht alle Sonderzeichen außer SPACE und \n
     * wandelt in Großbuchstaben um
     * @param text to be simplified
     */
    private String simplify(String text) {
        String simpleText;
        simpleText = text.toUpperCase();
        text = "";
        for (int i = 0; i < simpleText.length(); i++) {
            if (((simpleText.charAt(i) >= 'A' && simpleText.charAt(i) <= 'Z') || simpleText.charAt(i) == '\n' || simpleText.charAt(i) == ' ')) {
                //simpleText=simpleText.replace(text.charAt(i),' ');
                char[] s = new char[1];
                s[0] = simpleText.charAt(i);
                text = text.concat(new String(s));
            }
        }
        //test:
        //System.out.print(text);
        return text;
    }


    /**
     * @param c      char to encrypt SPACE is the first, LF will not be encrypted
     * @param offset moving distance
     * @return encrypted key
     */
    private char encrypt(char c, int offset) {
        //Leerzeichen? -> bekommt Code 'A'-1 == ein Buchstaben vor A in der ASCII Tabelle
        if (c == ' ') {
            c = 'A' - 1;
        }
        if (c >= 'A' - 1 && c <= 'Z') {
            //eigentlicher Codiervorgang
            c += offset;
            //codiertes Zeichen nach 'Z' ?
            if (c > 'Z') {
                c = (char) ('A' - 1 + (c - ('Z'+1)));  //==c-27
            }
        }
        //Wenn das Zeichen 'A' -1 == @ -> SPACE
        if (c == 'A' - 1) {
            return ' ';
        } else {
            return c;
        }
    }

    /**
     * encrypts whole Text of original File to Output File
     */
    public void krypt() {
        try {
            //liest alle bytes aus dem File
            byte[] ganzerInhalt = Files.readAllBytes(inputFile.toPath());
            //wandelt in Text um
            String ganzerText = new String(ganzerInhalt, StandardCharsets.UTF_8);
            //Großbuchstaben!
            ganzerText = simplify(ganzerText);
            System.out.print(ganzerText);
            System.out.println();


            int keyCounter = 0;  //zählt die aktuelle Stelle im Schlüssel
            //encoden:
            //Buchstabe für Buchstabe:
            for (int i = 0; i < ganzerText.length(); i++) {
                char c = encrypt(ganzerText.charAt(i), key[keyCounter]);
                outputFile.write(c);
                keyCounter++;
                keyCounter %= KEYLENGTH;
            }
            outputFile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
