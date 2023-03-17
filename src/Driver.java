import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Stores history of strings
        ArrayList<String> history = new ArrayList<>();

        // Create process for logger program
        Process logger = Runtime.getRuntime().exec("java -classpath /Users/abdurrehmanzulfiqar/IdeaProjects/Logger/out/production/Logger Logger");
        InputStream loggerInput = logger.getInputStream();
        OutputStream loggerOutput = logger.getOutputStream();
        // Read from input stream
        Scanner fromLog = new Scanner(loggerInput);
        // Write to output stream
        PrintStream toLog = new PrintStream(loggerOutput);

        // Create process for encryption program
        Process encryption = Runtime.getRuntime().exec("java -classpath /Users/abdurrehmanzulfiqar/IdeaProjects/Logger/out/production/Logger Encryption");
        InputStream encryptInput = encryption.getInputStream();
        OutputStream encryptOutput = encryption.getOutputStream();

        // Read from input stream
        Scanner fromEncrypt = new Scanner(encryptInput);
        // Write to output stream
        PrintStream toEncrypt = new PrintStream(encryptOutput);

        // Accept name of the log file
        System.out.println("Enter the name of the file: ");
        Scanner input = new Scanner(System.in);
        String filename = input.next();

        toLog.println(filename);
        //System.out.println(fromLog.nextLine());

        // Accept input
        Scanner comInput = new Scanner(System.in);

        // Variables for setup to send to other processes
        //-----------------------------------------------
        String key;

        while (true) {
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println("                                     Menu                                                ");
            System.out.println("----------------------------------------------------------------------------------------\n");
            System.out.println("    password: set the password needed for encryption and decryption");
            System.out.println("    encrypt: encrypt a string");
            System.out.println("    decrypt: decrypt a string");
            System.out.println("    history: show history");
            System.out.println("    quit: quit program\n");
            System.out.println("----------------------------------------------------------------------------------------\n");

            System.out.println("Enter a command: ");
            String command = comInput.nextLine();
            //String[] paramCheck = command.split(" ", 2);
            //if(paramCheck.length > 1) {
             //   System.out.println("Invalid command!");
            //}

            if (command.equals("quit")) {
                toLog.println("QUIT");
                toLog.flush();
                break;
            }

            switch (command) {
                case "password" :
                case "password ":
                    System.out.println("Do you want to 1) pick a string from history or 2) make a new password? (Enter 1 or 2)");
                    Scanner read = new Scanner(System.in);
                    int choice = read.nextInt();
                    if (choice == 1) {
                        if (history.size() == 0) {
                            System.out.println("History is empty.");
                        } else {
                            for (int i = 0; i < history.size(); i++) {
                                System.out.println((i+1) + ": " + history.get(i));
                            }
                            System.out.println("Enter the number of the word you would like to use, or enter -1 to exit: ");
                            choice = read.nextInt();

                            if(choice == -1) {
                                break;
                            }

                            toLog.println("PASSWORD");
                            toLog.flush();

                            key = history.get(choice - 1);
                            key = key.toUpperCase();
                            String com = "PASSKEY " + key;
                            toEncrypt.println(com);
                            toEncrypt.flush();

                            String result = fromEncrypt.nextLine();
                            if (result.equals("RESULT")) {
                                System.out.println("Your new password has been set.");
                                toLog.println(key);
                                toLog.flush();
                            } else {
                                System.out.println("Error creating password. Try again.");
                            }
                        }
                    }
                    else if (choice == 2){
                        System.out.println("Enter a password: ");
                        toLog.println("PASSWORD");
                        toLog.flush();

                        Scanner passScan = new Scanner(System.in);
                        key = passScan.next();
                        key = key.toUpperCase();
                        //toLog.println(key);
                        //toLog.flush();
                        //com += " " + key;
                        String com = "PASSKEY " + key;
                        toEncrypt.println(com);
                        toEncrypt.flush();

                        String result = fromEncrypt.nextLine();
                        if (result.equals("RESULT")) {
                            System.out.println("Your new password has been set.");
                            toLog.println(key);
                            toLog.flush();
                        } else {
                            System.out.println("Error creating password. Try again.");
                        }
                    } else {
                        System.out.println("Please enter either 1 or 2.");
                    }
                    break;
                case "encrypt" :
                    // give the ENCRYPT word to the logger program
                    toLog.println("ENCRYPT");
                    toLog.flush();

                    Scanner encryptScan = new Scanner(System.in);
                    System.out.println("Would you like to 1) select a string from history or 2) enter a new string? (Enter either 1 or 2)");
                    int choiceEncrypt = encryptScan.nextInt();
                    if (choiceEncrypt == 1) {
                        if (history.size() == 0) {
                            System.out.println("History is empty.");
                        } else {
                            for (int i = 0; i < history.size(); i++) {
                                System.out.println((i + 1) + ": " + history.get(i));
                            }
                            System.out.println("Enter the number of the word you would like to use, or enter -1 to exit: ");
                            choiceEncrypt = encryptScan.nextInt();

                            if(choiceEncrypt == -1) {
                                break;
                            }

                            String word = history.get(choiceEncrypt - 1);
                            word = word.toUpperCase();

                            toLog.println(word);
                            toLog.flush();

                            String com = "ENCRYPT " + word;
                            toEncrypt.println(com);
                            toEncrypt.flush();

                            String response = fromEncrypt.nextLine();
                            System.out.println(response);
                            toLog.println(response);
                            toLog.flush();

                        }
                    }
                    else if (choiceEncrypt == 2) {
                        System.out.println("Enter a string to encrypt: ");

                        Scanner scan = new Scanner(System.in);
                        String encryptThis = scan.nextLine();
                        encryptThis = encryptThis.toUpperCase();

                        // give the string to the logger program
                        toLog.println(encryptThis);
                        toLog.flush();

                        history.add(encryptThis);
                        toEncrypt.println("ENCRYPT " + encryptThis);
                        toEncrypt.flush();
                        String encrypted = fromEncrypt.nextLine();
                        System.out.println(encrypted);
                        // give the ENCRYPTED string to the logger
                        toLog.println(encrypted);
                        toLog.flush();
                    } else {
                        System.out.println("Please enter either 1 or 2.");
                    }
                    break;
                case "decrypt" :
                    toLog.println("DECRYPT");
                    toLog.flush();

                    Scanner decryptScan = new Scanner(System.in);
                    System.out.println("Would you like to 1) select a string from history or 2) enter a new string? (Enter either 1 or 2)");
                    int choiceDecrypt = decryptScan.nextInt();
                    if (choiceDecrypt == 1) {
                        if (history.size() == 0) {
                            System.out.println("History is empty.");
                        } else {
                            for (int i = 0; i < history.size(); i++) {
                                System.out.println((i + 1) + ": " + history.get(i));
                            }
                            System.out.println("Enter the number of the word you would like to use, or enter -1 to exit: ");
                            choiceDecrypt = decryptScan.nextInt();

                            if(choiceDecrypt == -1) {
                                break;
                            }

                            String word = history.get(choiceDecrypt - 1);
                            word = word.toUpperCase();

                            toLog.println(word);
                            toLog.flush();

                            String com = "DECRYPT " + word;
                            toEncrypt.println(com);
                            toEncrypt.flush();

                            String response = fromEncrypt.nextLine();
                            System.out.println(response);
                            toLog.println(response);
                            toLog.flush();

                        }
                    }
                    else if (choiceDecrypt == 2) {
                        System.out.println("Enter a string to decrypt: ");
                        Scanner scan2 = new Scanner(System.in);

                        String decryptThis = scan2.nextLine();
                        decryptThis = decryptThis.toUpperCase();
                        history.add(decryptThis);

                        toEncrypt.println("DECRYPT " + decryptThis);
                        toEncrypt.flush();

                        String decrypted = fromEncrypt.nextLine();
                        System.out.println(decrypted);
                        toLog.println(decrypted);
                        toLog.flush();
                    } else {
                        System.out.println("Please enter either 1 or 2.");
                    }
                    break;
                case "history" :
                    toLog.println("HISTORY");
                    toLog.flush();
                    if (history.size() == 0) {
                        System.out.println("History is empty.");
                    }
                    int count = 1;
                    for(int i = 0; i < history.size(); i++) {
                        System.out.println(count + ": " + history.get(i));
                        count++;
                    }
                    break;
                default:
                    System.out.println("Please try again.");
                    break;
            }

        }
        fromEncrypt.close();

    }
}



