/**
 * Driver_lab2b.java
 * Alex Fuoco
 * MSCS 630
 * Lab 2
 * March 2, 2021
 * version: 1.0
 *
 * This file contains the Driver for the Lab2b.
 */

package com.labs;

/**
 * Driver_lab2b class
 */
public class Driver_lab2b {
  public static void main(String[] args) {
  }

  /**
   * euclidAlgExt method
   * @param b
   * @param a
   * @return array containing d, x and y of the euclidAlgExtended,
   * computes the values so that d = ax + by, where d = gcd(a,b)
   */
  public static long[] euclidAlgExt(long b, long a) {
    if (a == 0)
      return new long[] { b, 1, 0 };

    long[] values = euclidAlgExt(a, b % a);
    long d = values[0];
    long y = values[2];
    long x = values[1] - (b / a) * values[2];
    return new long[] { d, y, x };
  }
}
