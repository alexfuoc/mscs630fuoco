/**
 * Driver_lab2b.java
 * Alex Fuoco
 * MSCS 630
 * Lab 2
 * March 14, 2021
 * version: 1.0
 *
 * This file contains the Driver for the Lab3b.
 */

package com.labs;

import java.util.Arrays;

/**
 * Driver_lab3b class
 */
public class Driver_lab3b {
  public static void main(String[] args) {
  }

  /**
   * getHexMatP method
   * @param s character to pad the array with
   * @param p string to be translated into the array by character
   * @return array containing input string translated to matrix with padding
   */
  public static int[][] getHexMatP(char s, String p) {
    int[][] hex = new int[4][4]; //16 characters
    int stringPosition = 0;

    //Filling the array from columns, padding with the s
    for(int i = 0; i < hex.length; i++){
      for( int j = 0; j < hex.length; j++){
        if ( p.length() > stringPosition ) {
          hex[j][i] = p.charAt(stringPosition);
          stringPosition++;
        } else { //pad the array with the character
          hex[j][i] = s;
        }
      }
    }
//    System.out.println("In Driver");
//    System.out.println(Arrays.deepToString(hex));
    return hex;
  }
}
