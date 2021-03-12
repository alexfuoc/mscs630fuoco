/**
 * Driver_lab2a.java
 * Alex Fuoco
 * MSCS 630
 * Lab 2
 * March 2, 2021
 * version: 1.0
 *
 * This file contains the Driver for the Lab2a.
 * Finds the greatest common divisor between 2 longs.
 */

package com.labs;

/**
 * Driver_lab2a class
 * Finds the greatest common divisor of 2 longs using the Euclidean Algorithm.
 * Takes in 2 longs assuming a,b > 0 and a >= b.
 */
public class Driver_lab2a {
  public static void main(String[] args) {
  }

  /**
   * Recursive approach to Euclidean algorithm. The base case is when a is 0,
   * reaching the greatest common divisor being b. This assumes that a > b, once b modulo a is equal to zero,
   * the gcd is a.
   * @param a long
   * @param b long
   * @return greatest common divisor between a and b.
   */
  public static long euclidAlg(long a, long b) {
    if(a == 0) {
      return b;
    }

    return euclidAlg(b % a, a);
  }
}