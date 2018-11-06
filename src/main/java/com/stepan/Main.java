package com.stepan;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

public class Main
{
    private static final String CHOOSE = "Choose: ", EXIT = "0) exit\n", HMAC_SHA_256 = "HmacSHA256", UTF_8 = "UTF-8", HASH = "Hash: ", YOUR_CHOICE = "Your choice: ", KEY_MESSAGE_COMP_CHOICE = "Key: %1$d%nMessage: %2$d%nComputer's choice: %2$d) %3$s%n", DRAW = "Draw!\n", USER_WON = "You won!\n", COMPUTER_WON = "Computer won!\n";
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        String[] things = {"rock", "scissors", "paper"};

        Scanner scanner = new Scanner(System.in);
        int thingsQuantity = things.length;
        int userChoice = -1;

        while(true) {
            System.out.println(CHOOSE);
            for(int i = 0; i < thingsQuantity; i++) {
                System.out.println(i + 1 + ") " + things[i]);
            }
            System.out.println(EXIT);

            int randNum = new SecureRandom().nextInt();
            int compChoice = Math.abs(randNum % thingsQuantity);
            Mac hmacSHA256 = Mac.getInstance(HMAC_SHA_256);
            SecretKeySpec secretKey = new SecretKeySpec(Integer.toString(randNum).getBytes(UTF_8), HMAC_SHA_256);
            hmacSHA256.init(secretKey);
            String hash = Hex.encodeHexString(hmacSHA256.doFinal(Integer.toString(compChoice).getBytes(UTF_8)));

            System.out.println(HASH + hash);
            System.out.print(YOUR_CHOICE);
            userChoice = scanner.nextInt();
            if(userChoice == 0) {
                break;
            }
            System.out.format(KEY_MESSAGE_COMP_CHOICE, randNum, (compChoice + 1) , things[compChoice]);

            int difference = (userChoice - 1) - compChoice;
            int winningCombinationNum = (thingsQuantity - 1)/2;

            if(difference == 0) {
                System.out.println(DRAW);
                continue;
            }
            if(difference < -winningCombinationNum || difference > winningCombinationNum) {
                if(difference > 0) {
                    System.out.println(USER_WON);
                } else {
                    System.out.println(COMPUTER_WON);
                }
            } else {
                if(difference > 0) {
                    System.out.println(COMPUTER_WON);
                } else {
                    System.out.println(USER_WON);
                }
            }
        }
    }
}