import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Logger {
    public static void main(String[] args) throws IOException {
         // set up scanner + input
         Scanner input = new Scanner(System.in);
         String filename = input.next();

         // file declaration
         File file = new File(filename);
         // if file exists execute the following
         if(file.exists()) {
             // write to existing file (in append mode)
             FileWriter fWriter = new FileWriter(file, true);
             BufferedWriter writer = new BufferedWriter(fWriter);

             // format the current time
             SimpleDateFormat dateFormatter = new SimpleDateFormat("YYYY-MM-dd HH:mm");
             Date d1 = new Date();
             String currentTime = dateFormatter.format(d1);
             // logging start

             writer.write(currentTime + " [START] Logging Started.\n");
             writer.flush();
             // Set up new scanner
             //Scanner comInput = new Scanner(System.in);
             //String command = comInput.next(); //encrypt, password, decrypt, etc

             while(true) {
                 // WAITING FOR COMMAND FROM DRIVER
                 String command = input.next();
                 SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm");
                 Date c = new Date();
                 // format current command time
                 String current = format.format(c);

                 // write command and time
                 switch(command) {
                     case "ENCRYPT":
                         Scanner encrScan = new Scanner(System.in);
                         String encr = encrScan.nextLine();
                         //encrScan.close();
                         String newEncr = encrScan.nextLine();
                         int i = newEncr.indexOf(' ');
                         String first = newEncr.substring(0, i);
                         String rest = newEncr.substring(i + 1);
                         writer.write(current + " [" + command + "] " + "String to be encrypted: " + encr + ". Encrypted string: " + rest + ".\n");
                         writer.flush();
                         break;

                     case "DECRYPT" :
                         Scanner decrScan = new Scanner(System.in);
                         String decr = decrScan.nextLine();
                         writer.write(current + " [" + command + "] " + "Decrypted string: " + decr + ".\n");
                         writer.flush();
                         break;

                     case "PASSWORD" :
                         Scanner passScan = new Scanner(System.in);
                         String password = passScan.nextLine();
                         writer.write(current + " [SET_PASSWORD] Passkey is now " + password + ".\n");
                         writer.flush();
                         //passScan.close();
                         break;

                     case "HISTORY" :
                         writer.write(current + " [HISTORY] History Checked.\n");
                         writer.flush();
                         break;
                     case "QUIT":
                         // logging stop time
                         Date stop = new Date();
                         SimpleDateFormat formatEnd = new SimpleDateFormat("YYYY-MM-dd HH:mm");
                         String stopTime = formatEnd.format(stop);

                         // write stop time and logging stopped
                         writer.write(stopTime + " [STOP] Logging Stopped.\n");
                         writer.close();
                         input.close();
                         break;
                 }
                 //writer.write(current + " [ ] " + command + "\n");
             }
         } else {
             System.out.println("File does not exist!");
         }
    }
}
