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

            // kein Filename angegeben -> erzeugen auf Basis des encodedFiles
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
            // Charset US_ASCII sonst wird nicht die ASCII Tabelle für die Position der Zeichen herangezogen!! (UTF-8 anders)
            String fullText = new String(fullFile, StandardCharsets.US_ASCII);
            //System.out.print(fullText);

            // initiale Stelle im Schlüssel-Array key[]
            int keyPos = 0;

            // String Zeichen für Zeichen durchgehen
            for (int i = 0; i < fullText.length(); i++) {
                //Zeichen decodieren anzeigen und in decodedFile schreiben

                char c = decode(fullText.charAt(i), key[keyPos]);
                System.out.print(c);
                decodedFile.write(c);

                // zur nächsten Stelle im Schlüssel Array key[]
                keyPos++;
                keyPos %= KEYLENGTH;
            }
            // File zu
            decodedFile.close();

        }
        catch  (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param c character zum Dekodieren
     * @param offset Wert an der aktuellen Position im key[] Array zur Verschiebung nach Vigenere
     * @return
     */
    private char decode (char c, int offset)
    {
        //Leerzeichen? -> bekommt Code 'A'-1 == ein Buchstaben vor A in der ASCII Tabelle
        if (c == ' ')
        {
            c = 'A' - 1;
        }

        if (c >= 'A' - 1 && c <= 'Z')
        {
            //eigentlicher Codiervorgang
            c -= offset;

            //codiertes Zeichen nach 'Z' ?
            if (c > 'Z')
            {

                c=(char)(c-27);
            }

            //codiertes Zeichen hinter dem Leerzeichen?
            if (c < 'A' -1)
            {
                c=(char) (c+27);
            }

        }
        // Leerzeichen, nicht @ aus der ASCII Tabelle!
        if (c == 'A' - 1)
        {
            return ' ';
        }
        else
        {
            return c;
        }

    }



}
