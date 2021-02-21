/**
 * Driver_lab1.java
 * Alex Fuoco
 * MSCS 630
 * Lab 1
 * February 21, 2021
 * version: 1.0
 *
 * This file contains the Driver for the Lab1.
 * It converts a string input to an int output.
 */


/**
 * Driver_lab1 class
 * Coverts a string into a array of integers.
 * Each character is converted to an in from 'a-z' is 0-25,
 * ' ' is 26.
 */
public class Driver_lab1 {
  public static void main(String[] args) {
  }

  /**
   * Converting a string input to a corresponding number from 0-26 include ' '.
   * Converted using the unicode of the char.
   * @param plainText a string with characters to be translated into int
   * @return int arr converted from the string input
   */
  public static int[] str2int(String plainText){
    char charInput[] = plainText.toLowerCase().toCharArray();
    int NUM_OF_CHARS = charInput.length;
    int intOutput[] = new int[NUM_OF_CHARS];

    for (int i = 0; i < NUM_OF_CHARS; i++) {
      char nextChar = charInput[i];
      if( nextChar == ' ') {
        intOutput[i] = 26;
      } else {
        intOutput[i] = nextChar - 'a';
      }
    }

    return intOutput;
  }
}
