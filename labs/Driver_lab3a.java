/*
  Driver_lab2a.java
  Alex Fuoco
  MSCS 630
  Lab 2
  March 14, 2021
  version: 1.0

  This file contains the Driver for the Lab2a.
  Finds the greatest common divisor between 2 longs.
 */

package com.labs;

/**
 * Driver_lab3a class
 */
public class Driver_lab3a {
  public static void main(String[] args) {
    int array[][] = {{-520989692}};
    int modulo = 27972;
    System.out.println(cofModDet(modulo,array));
  }

  /**
   * Receives an integer m > 0, a two-
   * dimensional array (matrix) A, and returns the corresponding determinant in modulo m as an integer.
   * For n ≥ 3, det(A)= a11det(A11)−a12det(A12)+a13det(A13)− · · · + (−1)n+1a1ndet(A1n), where
   * Aij is the (n − 1) × (n − 1) submatrix that results when the i-th row and the j-th column are removed
   * from the original matrix A.
   * @param m
   * @param A two-dimensional array
   * @return the determinant in modulo m
   */
  public static int cofModDet(int m, int[][] A) {
    int len = A.length;
    int temp[][];
    int determinant = 0;

    if( len == 1 ){
      System.out.print("Determinant Modulo M: ");
      int cof = Math.floorMod(A[0][0],m);
      System.out.print(cof);
      System.out.println();
      determinant = Math.floorMod(cof,m);
      return determinant;
    } else if ( len == 2 ){
      int a = Math.floorMod(A[0][0], m);
      int b = Math.floorMod(A[0][1], m);
      int c = Math.floorMod(A[1][0], m);
      int d = Math.floorMod(A[1][1], m);

      int ad = Math.floorMod((a * d), m);
      int bc = Math.floorMod((b * c), m);
      determinant = Math.floorMod((ad - bc),m);
      return determinant;
    }

    //For square matrix length 3 and above, recursive call for finding the determinant
    //Loops through and finds the submatrix that results when the i-th row and the j-th column are removed
    //from the original matrix
    for (int i = 0; i < A[0].length; i++) {
      temp = new int[A.length - 1][A[0].length - 1];

      for (int j = 1; j < A.length; j++) {
        for (int k = 0; k < A[0].length; k++) {
          if (k < i) {
            temp[j - 1][k] = A[j][k];
          } else if (k > i) {
            temp[j - 1][k - 1] = A[j][k];
          }
        }
      }

      determinant += Math.floorMod(A[0][i],m) * Math.pow (-1, i) * cofModDet(m, temp);
    }
    return Math.floorMod(determinant,m);
  }
}