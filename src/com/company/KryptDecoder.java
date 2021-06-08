package com.company;

/**
 * Philipp Griess, 4ACIF
 * Entschlüsselt vereinfachte Textfeiles nach dem KryptEncoder mit einem mindestens 10-stelligen Schlüssel nach Vigenere
 */

import com.sun.xml.internal.bind.api.impl.NameConverter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class KryptDecoder {

    // input File
    private File encodedFile;
    //output file
    private BufferedWriter decodedFile;


    //10stelliger KEY aus Ziffern
    private final int KEYLENGTH = 10;
    private int[] key = new int[KEYLENGTH];

    public KryptDecoder(String encodedFileName, String decodedFileName, String keyString) {

        try {
            encodedFile = new File(encodedFileName);

            // kein decodedFileName -> erzeugen auf Basis des encodedFileNames
            if(decodedFileName.isEmpty())
            {
                if (encodedFileName.lastIndexOf('.') != -1) //Punkt vorhanden?
                {
                    decodedFileName =
                            encodedFileName.substring(0, encodedFileName.lastIndexOf('.'))
                                    + ".unkry";
                } else { //kein Punkt
                    decodedFileName = encodedFileName + ".unkry";
                }
            }

            // File für Ausgabe öffnen
            decodedFile = (new BufferedWriter(new FileWriter(decodedFileName)));

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Schlüssel Array aus Eingabe erzeugen
        for (int i = 0; i < key.length; i++) {
            key[i] = Character.getNumericValue(keyString.charAt(i % keyString.length()));
        }
    }

    /**
     * liest das verschlüsselte File und entschlüsselt die Daten in das decodedFile
     */
    public void unKrypt ()
    {
        try {
            // gesamtes File in byteArray lesen
            byte[] fullFile = Files.readAllBytes(encodedFile.toPath());

            // Umwandeln in String
            String fullText = new String(fullFile, StandardCharsets.UTF_8);
            System.out.print(fullText);
        }
        catch  (IOException e) {
            e.printStackTrace();
        }
    }

    private char decode (char c, int offset)
    {

    }



}
