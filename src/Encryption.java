import java.io.*;
import java.util.Locale;
import java.util.Scanner;

public class Encryption {
    public static void main(String[] args) {
        String command;
        //System.out.println("Enter a command: ");
        Scanner input = new Scanner(System.in);
        command = input.nextLine();

        VigenereCipher vcipher = new VigenereCipher();
        String passkey = null;

        while (!command.equals("QUIT")) {

            //if (command.equals("QUIT")) {
              // System.out.println("Exiting program.");
               //break;
            //}
            int i = command.indexOf(' ');
            String first = command.substring(0, i);
            String rest = command.substring(i+1);


            if(first.equals("PASSKEY")) {
                passkey = rest;
                System.out.println("RESULT");
            }

            if(first.equals("ENCRYPT")) {
                if (passkey == null) {
                    System.out.println("ERROR Password not set!!");
                } else {
                    String circularKey = vcipher.circularKey(rest, passkey);
                    String encrypted = vcipher.encrypt(rest, circularKey);
                    System.out.println("RESULT: " + encrypted);
                }
            }

            if(first.equals("DECRYPT")) {
                if (passkey == null) {
                    System.out.println("ERROR Password not set");
                } else {
                    String circularKey = vcipher.circularKey(rest, passkey);
                    String decrypted = vcipher.decrypt(rest, circularKey);
                    System.out.println("RESULT: " + decrypted);
                }
            }
            command = input.nextLine();
        }
        //System.out.println("Quitting.");
        //System.out.println("Exiting program.");
    }

    public static class VigenereCipher {
        public String circularKey(String text, String key) {
            int length = text.length();
            int flag = 0;
            int i = 0;
            int j = key.length() - 1;

            while(true) {
                if (key.length() < text.length()) {
                    if (length == i) {
                        i = 0;
                    }
                    if (key.length() == text.length()) {
                        break;
                    }
                    key = key + key.charAt(i);
                    i++;
                    flag = 1;
                }
                else if (key.length() > text.length()) {
                    if (length == j) {
                        j = key.length() - 1;
                    }
                    if (key.length() == text.length()) {
                        break;
                    }
                    key = key.substring(0, j);
                    //String rest = key.substring(j);
                    j--;
                    flag = 2;
                } else {
                    if (flag == 0) {
                        if (length == i) {
                            i = 0;
                        }
                        if (key.length() == text.length()) {
                            break;
                        }
                        key = key + key.charAt(i);
                        i++;
                    }
                    if (flag == 1) {
                        if (length == i) {
                            i = 0;
                        }
                        if (key.length() == text.length()) {
                            break;
                        }
                        key = key + key.charAt(i);
                        i++;
                    }
                    if (flag == 2) {
                        if (length == j) {
                            j = key.length() - 1;
                        }
                        if (key.length() == text.length()) {
                            break;
                        }
                        key = key.substring(0, j);
                        //String rest = key.substring(j);
                        j--;
                    }
                }
            }
            return key;
        }

        public String encrypt(String text, String key) {
            int length = text.length();
            String temp = "";
            for(int i = 0; i < length; i++) {
                int p = ((text.charAt(i) + key.charAt(i)) % 26) + 'A';
                temp = temp + (char) p;
            }
            return temp;
        }

        public String decrypt(String text, String key) {
            int original_length = text.length();
            int key_length = key.length();
            String temp = "";
            for (int i = 0; i < original_length && i < key_length; i++) {
                int p = (text.charAt(i) - key.charAt(i) + 26) % 26;
                p = p + 'A';
                temp = temp + (char) p;
            }
            return temp;
        }
    }
}
