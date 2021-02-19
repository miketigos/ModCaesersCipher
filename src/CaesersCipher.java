import java.util.ArrayList;
import java.util.Scanner;

public class CaesersCipher {


     /* This method will get the user input for message and key
     * convert the users input for key into something we can use
     * then we will call the methods and print the results, then we
     * will see if the user wants to run the program again
     */
    public void runCipher() {
        Scanner input = new Scanner(System.in);

        // this boolean var. will be used to control the loop
        // initally set to false to prevent infinite loop
        boolean runAgain;
        do {
            // this boolean var. will be used to control the game loop
            runAgain = false;
            System.out.print("Enter a string to be encoded: ");
            String message = input.nextLine();

            System.out.print("Enter the individual key values (positive or negative integers, \n"
                    + "one after another in the same line with a blank between two values: ");

            // create an arraylist to hold the key to the cipher, and split the user input at whitespace
            ArrayList<Integer> key = new ArrayList<>();
            String code = input.nextLine();
            String[] keyArray = code.split(" ");

            // parse the entries in the keyArray into integer and add to the key list
            for (String s : keyArray) {
                key.add(Integer.parseInt(s));
            }

            // run the ecrypt func. and print the resulting string
            String encrytpedMessage = (encryptMessage(message, key));
            System.out.println("(" + encrytpedMessage + ")");

            // run the decrypt func. and print the resulting string
            String decrytpedMessage = (decryptMessage(encrytpedMessage, key));
            System.out.println("(" + decrytpedMessage + ")");


             /* Prompt the user to run the program again
              if 'y' then change the runAgain boolean variable to true and we will run again
              if 'n' then maintain the false status of runAgain and close the scanner and
              terminate the program with code 0 */

            System.out.print("Do you want to run the program again? (y/n)");
            String response = input.nextLine();

            if (response.charAt(0) == 'y') {
                runAgain = true;
            }
        } while (runAgain);
        input.close();
        System.exit(0);

    }
    /*runs in O(n + n) where n is length of message
     * plus other n for the sb.toString method
     */
    public String encryptMessage(String str, ArrayList<Integer> key) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {

            // curChar is the character that we are going to shift
            int curChar = str.charAt(i);

            // curKey is the key at the proper index, we use the mod.
            // operator to make sure we repeat the key
            int curKey = (key.get(i % key.size()));

            // the shift is the net-shift that will happen within th ascii bounds 32 to 126
            // ex: key == 99, 99 % 95 = 4, we will shift 4 characters up from the cur char
            int shift = (curKey % 95);

            // if the net-shift is 0 then we are going nowhere or doing a full loop of the
            // appropriate characters
            if (shift == 0) {
                sb.append((char) curChar);
            // if the cur char plus the shift is less than 32, we will
            // subtract 32 from the character(this guarantees that it is atleast -1
            // and we will subtract that from 127 ex: 32 - 1 should go to 126
            // 127 + (-1) = 126
            } else if ((curChar + shift) < 32) {
                sb.append((char) (127 + ((curChar - 32) + shift)));
            // if the cur char plus the shift is more than 126, we will
            // subtract 126 from the character(this guarantees that it is atleast 1
            // and we will add that to 31
            } else if ((curChar + shift) > 126) {
                sb.append((char) ((31 + (curChar - 126) + shift)));
            // else, just shift and add to new string
            } else {
                sb.append((char) (curChar + shift));
            }
        }
        //return built string
        return sb.toString();
    }

    /*
     * This does the exact same thing as encrypt but it negates the key
     * negating the key is how you solve the cipher,
     * See documentation for encrypt message for logic of decrypt
     * runs in O(k + n + n) where k is len of key and n is length of message
     * the plus k is because of the negatekey method being O(n)
     * plus other n for the sb.toString method
     */
    public String decryptMessage(String str, ArrayList<Integer> key) {
        negateKey(key);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            int curChar = str.charAt(i);
            int curKey = (key.get(i % key.size()));
            int shift = (curKey % 95);

            if (shift == 0) {
                sb.append((char) curChar);
            } else if ((curChar + shift) < 32) {
                sb.append((char) (127 + ((curChar - 32) + shift)));
            } else if ((curChar + shift) > 126) {
                sb.append((char) (31 + (curChar - 126) + shift));
            } else {
                sb.append((char) (curChar + shift));
            }
        }
        return sb.toString();
    }
    // this will loop through each entry in the key and negate it in place O(n)
    private void negateKey(ArrayList<Integer> key) {
        for (int i = 0; i < key.size(); i++) {
            int curKey = key.get(i);
            key.set(i, (-curKey));
        }
    }
}


