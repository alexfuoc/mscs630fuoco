package labs;
/*
  AESCipher.java
  Alex Fuoco
  MSCS 630
  Lab 5
  March 30, 2021
  version: 1.0

  This file contains the AESCipher class.
 */

/**
 * This class contains the methods for creating the KeyRoundsHex from the Lab 4.
 * It also contains the full AES Encryption algorithm for lab 5.
 */
public class AESCipher {
    private static final int[] S_BOX = {
            0x63 ,0x7c ,0x77 ,0x7b ,0xf2 ,0x6b ,0x6f ,0xc5 ,0x30 ,0x01 ,0x67 ,0x2b ,0xfe ,0xd7 ,0xab ,0x76
            ,0xca ,0x82 ,0xc9 ,0x7d ,0xfa ,0x59 ,0x47 ,0xf0 ,0xad ,0xd4 ,0xa2 ,0xaf ,0x9c ,0xa4 ,0x72 ,0xc0
            ,0xb7 ,0xfd ,0x93 ,0x26 ,0x36 ,0x3f ,0xf7 ,0xcc ,0x34 ,0xa5 ,0xe5 ,0xf1 ,0x71 ,0xd8 ,0x31 ,0x15
            ,0x04 ,0xc7 ,0x23 ,0xc3 ,0x18 ,0x96 ,0x05 ,0x9a ,0x07 ,0x12 ,0x80 ,0xe2 ,0xeb ,0x27 ,0xb2 ,0x75
            ,0x09 ,0x83 ,0x2c ,0x1a ,0x1b ,0x6e ,0x5a ,0xa0 ,0x52 ,0x3b ,0xd6 ,0xb3 ,0x29 ,0xe3 ,0x2f ,0x84
            ,0x53 ,0xd1 ,0x00 ,0xed ,0x20 ,0xfc ,0xb1 ,0x5b ,0x6a ,0xcb ,0xbe ,0x39 ,0x4a ,0x4c ,0x58 ,0xcf
            ,0xd0 ,0xef ,0xaa ,0xfb ,0x43 ,0x4d ,0x33 ,0x85 ,0x45 ,0xf9 ,0x02 ,0x7f ,0x50 ,0x3c ,0x9f ,0xa8
            ,0x51 ,0xa3 ,0x40 ,0x8f ,0x92 ,0x9d ,0x38 ,0xf5 ,0xbc ,0xb6 ,0xda ,0x21 ,0x10 ,0xff ,0xf3 ,0xd2
            ,0xcd ,0x0c ,0x13 ,0xec ,0x5f ,0x97 ,0x44 ,0x17 ,0xc4 ,0xa7 ,0x7e ,0x3d ,0x64 ,0x5d ,0x19 ,0x73
            ,0x60 ,0x81 ,0x4f ,0xdc ,0x22 ,0x2a ,0x90 ,0x88 ,0x46 ,0xee ,0xb8 ,0x14 ,0xde ,0x5e ,0x0b ,0xdb
            ,0xe0 ,0x32 ,0x3a ,0x0a ,0x49 ,0x06 ,0x24 ,0x5c ,0xc2 ,0xd3 ,0xac ,0x62 ,0x91 ,0x95 ,0xe4 ,0x79
            ,0xe7 ,0xc8 ,0x37 ,0x6d ,0x8d ,0xd5 ,0x4e ,0xa9 ,0x6c ,0x56 ,0xf4 ,0xea ,0x65 ,0x7a ,0xae ,0x08
            ,0xba ,0x78 ,0x25 ,0x2e ,0x1c ,0xa6 ,0xb4 ,0xc6 ,0xe8 ,0xdd ,0x74 ,0x1f ,0x4b ,0xbd ,0x8b ,0x8a
            ,0x70 ,0x3e ,0xb5 ,0x66 ,0x48 ,0x03 ,0xf6 ,0x0e ,0x61 ,0x35 ,0x57 ,0xb9 ,0x86 ,0xc1 ,0x1d ,0x9e
            ,0xe1 ,0xf8 ,0x98 ,0x11 ,0x69 ,0xd9 ,0x8e ,0x94 ,0x9b ,0x1e ,0x87 ,0xe9 ,0xce ,0x55 ,0x28 ,0xdf
            ,0x8c ,0xa1 ,0x89 ,0x0d ,0xbf ,0xe6 ,0x42 ,0x68 ,0x41 ,0x99 ,0x2d ,0x0f ,0xb0 ,0x54 ,0xbb ,0x16};

    private static final int[] R_CON = {
            0x8D,0x01,0x02,0x04,0x08,0x10,0x20,0x40,0x80,0x1B,0x36,0x6C,0xD8,0xAB,0x4D,0x9A,
            0x2F,0x5E,0xBC,0x63,0xC6,0x97,0x35,0x6A,0xD4,0xB3,0x7D,0xFA,0xEF,0xC5,0x91,0x39,
            0x72,0xE4,0xD3,0xBD,0x61,0xC2,0x9F,0x25,0x4A,0x94,0x33,0x66,0xCC,0x83,0x1D,0x3A,
            0x74,0xE8,0xCB,0x8D,0x01,0x02,0x04,0x08,0x10,0x20,0x40,0x80,0x1B,0x36,0x6C,0xD8,
            0xAB,0x4D,0x9A,0x2F,0x5E,0xBC,0x63,0xC6,0x97,0x35,0x6A,0xD4,0xB3,0x7D,0xFA,0xEF,
            0xC5,0x91,0x39,0x72,0xE4,0xD3,0xBD,0x61,0xC2,0x9F,0x25,0x4A,0x94,0x33,0x66,0xCC,
            0x83,0x1D,0x3A,0x74,0xE8,0xCB,0x8D,0x01,0x02,0x04,0x08,0x10,0x20,0x40,0x80,0x1B,
            0x36,0x6C,0xD8,0xAB,0x4D,0x9A,0x2F,0x5E,0xBC,0x63,0xC6,0x97,0x35,0x6A,0xD4,0xB3,
            0x7D,0xFA,0xEF,0xC5,0x91,0x39,0x72,0xE4,0xD3,0xBD,0x61,0xC2,0x9F,0x25,0x4A,0x94,
            0x33,0x66,0xCC,0x83,0x1D,0x3A,0x74,0xE8,0xCB,0x8D,0x01,0x02,0x04,0x08,0x10,0x20,
            0x40,0x80,0x1B,0x36,0x6C,0xD8,0xAB,0x4D,0x9A,0x2F,0x5E,0xBC,0x63,0xC6,0x97,0x35,
            0x6A,0xD4,0xB3,0x7D,0xFA,0xEF,0xC5,0x91,0x39,0x72,0xE4,0xD3,0xBD,0x61,0xC2,0x9F,
            0x25,0x4A,0x94,0x33,0x66,0xCC,0x83,0x1D,0x3A,0x74,0xE8,0xCB,0x8D,0x01,0x02,0x04,
            0x08,0x10,0x20,0x40,0x80,0x1B,0x36,0x6C,0xD8,0xAB,0x4D,0x9A,0x2F,0x5E,0xBC,0x63,
            0xC6,0x97,0x35,0x6A,0xD4,0xB3,0x7D,0xFA,0xEF,0xC5,0x91,0x39,0x72,0xE4,0xD3,0xBD,
            0x61,0xC2,0x9F,0x25,0x4A,0x94,0x33,0x66,0xCC,0x83,0x1D,0x3A,0x74,0xE8,0xCB,0x8D};

    private static final int[] MULT_TWO = {
            0x00,0x02,0x04,0x06,0x08,0x0a,0x0c,0x0e,0x10,0x12,0x14,0x16,0x18,0x1a,0x1c,0x1e,
            0x20,0x22,0x24,0x26,0x28,0x2a,0x2c,0x2e,0x30,0x32,0x34,0x36,0x38,0x3a,0x3c,0x3e,
            0x40,0x42,0x44,0x46,0x48,0x4a,0x4c,0x4e,0x50,0x52,0x54,0x56,0x58,0x5a,0x5c,0x5e,
            0x60,0x62,0x64,0x66,0x68,0x6a,0x6c,0x6e,0x70,0x72,0x74,0x76,0x78,0x7a,0x7c,0x7e,
            0x80,0x82,0x84,0x86,0x88,0x8a,0x8c,0x8e,0x90,0x92,0x94,0x96,0x98,0x9a,0x9c,0x9e,
            0xa0,0xa2,0xa4,0xa6,0xa8,0xaa,0xac,0xae,0xb0,0xb2,0xb4,0xb6,0xb8,0xba,0xbc,0xbe,
            0xc0,0xc2,0xc4,0xc6,0xc8,0xca,0xcc,0xce,0xd0,0xd2,0xd4,0xd6,0xd8,0xda,0xdc,0xde,
            0xe0,0xe2,0xe4,0xe6,0xe8,0xea,0xec,0xee,0xf0,0xf2,0xf4,0xf6,0xf8,0xfa,0xfc,0xfe,
            0x1b,0x19,0x1f,0x1d,0x13,0x11,0x17,0x15,0x0b,0x09,0x0f,0x0d,0x03,0x01,0x07,0x05,
            0x3b,0x39,0x3f,0x3d,0x33,0x31,0x37,0x35,0x2b,0x29,0x2f,0x2d,0x23,0x21,0x27,0x25,
            0x5b,0x59,0x5f,0x5d,0x53,0x51,0x57,0x55,0x4b,0x49,0x4f,0x4d,0x43,0x41,0x47,0x45,
            0x7b,0x79,0x7f,0x7d,0x73,0x71,0x77,0x75,0x6b,0x69,0x6f,0x6d,0x63,0x61,0x67,0x65,
            0x9b,0x99,0x9f,0x9d,0x93,0x91,0x97,0x95,0x8b,0x89,0x8f,0x8d,0x83,0x81,0x87,0x85,
            0xbb,0xb9,0xbf,0xbd,0xb3,0xb1,0xb7,0xb5,0xab,0xa9,0xaf,0xad,0xa3,0xa1,0xa7,0xa5,
            0xdb,0xd9,0xdf,0xdd,0xd3,0xd1,0xd7,0xd5,0xcb,0xc9,0xcf,0xcd,0xc3,0xc1,0xc7,0xc5,
            0xfb,0xf9,0xff,0xfd,0xf3,0xf1,0xf7,0xf5,0xeb,0xe9,0xef,0xed,0xe3,0xe1,0xe7,0xe5};

    private static final int[] MULT_THREE = {
            0x00,0x03,0x06,0x05,0x0c,0x0f,0x0a,0x09,0x18,0x1b,0x1e,0x1d,0x14,0x17,0x12,0x11,
            0x30,0x33,0x36,0x35,0x3c,0x3f,0x3a,0x39,0x28,0x2b,0x2e,0x2d,0x24,0x27,0x22,0x21,
            0x60,0x63,0x66,0x65,0x6c,0x6f,0x6a,0x69,0x78,0x7b,0x7e,0x7d,0x74,0x77,0x72,0x71,
            0x50,0x53,0x56,0x55,0x5c,0x5f,0x5a,0x59,0x48,0x4b,0x4e,0x4d,0x44,0x47,0x42,0x41,
            0xc0,0xc3,0xc6,0xc5,0xcc,0xcf,0xca,0xc9,0xd8,0xdb,0xde,0xdd,0xd4,0xd7,0xd2,0xd1,
            0xf0,0xf3,0xf6,0xf5,0xfc,0xff,0xfa,0xf9,0xe8,0xeb,0xee,0xed,0xe4,0xe7,0xe2,0xe1,
            0xa0,0xa3,0xa6,0xa5,0xac,0xaf,0xaa,0xa9,0xb8,0xbb,0xbe,0xbd,0xb4,0xb7,0xb2,0xb1,
            0x90,0x93,0x96,0x95,0x9c,0x9f,0x9a,0x99,0x88,0x8b,0x8e,0x8d,0x84,0x87,0x82,0x81,
            0x9b,0x98,0x9d,0x9e,0x97,0x94,0x91,0x92,0x83,0x80,0x85,0x86,0x8f,0x8c,0x89,0x8a,
            0xab,0xa8,0xad,0xae,0xa7,0xa4,0xa1,0xa2,0xb3,0xb0,0xb5,0xb6,0xbf,0xbc,0xb9,0xba,
            0xfb,0xf8,0xfd,0xfe,0xf7,0xf4,0xf1,0xf2,0xe3,0xe0,0xe5,0xe6,0xef,0xec,0xe9,0xea,
            0xcb,0xc8,0xcd,0xce,0xc7,0xc4,0xc1,0xc2,0xd3,0xd0,0xd5,0xd6,0xdf,0xdc,0xd9,0xda,
            0x5b,0x58,0x5d,0x5e,0x57,0x54,0x51,0x52,0x43,0x40,0x45,0x46,0x4f,0x4c,0x49,0x4a,
            0x6b,0x68,0x6d,0x6e,0x67,0x64,0x61,0x62,0x73,0x70,0x75,0x76,0x7f,0x7c,0x79,0x7a,
            0x3b,0x38,0x3d,0x3e,0x37,0x34,0x31,0x32,0x23,0x20,0x25,0x26,0x2f,0x2c,0x29,0x2a,
            0x0b,0x08,0x0d,0x0e,0x07,0x04,0x01,0x02,0x13,0x10,0x15,0x16,0x1f,0x1c,0x19,0x1a};


    public static void main(String[] args) {
        String plainText = "54776F204F6E65204E696E652054776F";
        String keyHex = "5468617473206D79204B756E67204675";
        String cipherText = AES(plainText, keyHex);

        System.out.println("Plain text input:\t" + plainText);
        System.out.println("Key Hex input:\t\t" + keyHex);
        System.out.println("Cipher text output:\t" + cipherText);

    }

    /**
     * Takes in a plain text string and a key string and encrypts using AES Encryption for 128 bits
     * @param pTextHex the plaintext string in hexstring formated to be encrypted, 32 chars
     * @param keyHex the initial keyhex to used for encryption in hexstring format, 32 chars
     * @return the encrypted plaintext returned in an array length of 1
     */
    public static String AES(String pTextHex, String keyHex){
        int[][] outStateHex;
        String[] cipherText;
        String[] roundKeysHex = aesRoundKeys(keyHex);
        int[][] plainTextMatrix = parseHexString(pTextHex);

        outStateHex = AESStateXOR(plainTextMatrix, parseHexString(roundKeysHex[0])); //First Round
        for (int round = 0; round < 9; round++) { //Rounds 1 through 10 inclusive
            outStateHex = AESNibbleSub(outStateHex);
            outStateHex = AESShiftRow(outStateHex);
            outStateHex = AESMixColumn(outStateHex);
            outStateHex = AESStateXOR(outStateHex, parseHexString(roundKeysHex[round+1]));
        }
        outStateHex = AESNibbleSub(outStateHex); //Round 11
        outStateHex = AESShiftRow(outStateHex);  //Round 11
        outStateHex = AESStateXOR(outStateHex, parseHexString(roundKeysHex[10])); //Round 11
        cipherText[0] = aesCipherFormat(outStateHex);

        System.out.println("Plain text input:\t" + pTextHex);
        System.out.println("Key Hex input:\t\t" + keyHex);
        System.out.println("Cipher text output:\t" + cipherText[0]);

        return cipherText;
    }

    /**
     * Takes in the starting string KeyHex, and returns the keyRoundsHex, giving the 11 rounds of keys
     * @param KeyHex the key as a HexString
     * @return returns the 11 row HexString containing each round of keys produced
     */
    public static String[] aesRoundKeys(String KeyHex) {
        String[] roundKeyHex;
        int[][] keys = new int[48][4];


        int len = KeyHex.length();
        int[] data = new int[len / 2];

        //Parsing KeyHex and inputting first ket into keys matrix
        for (int i = 0; i < len; i += 2) {
            String temp1 = String.valueOf(KeyHex.charAt(i));
            String temp2 = String.valueOf(KeyHex.charAt(i+1));
            String temp = temp1.concat(temp2);

            data[i / 2] = Integer.parseInt(temp, 16);
        }



        // Adding the initial string as int in the keys array
        for (int i = 0; i < 4; i++){
            keys[i] = new int[]{data[4 * i], data[4 * i + 1], data[4 * i + 2], data[4 * i + 3]};
        }

        //adding 4-48
        for (int i = 4; i < keys.length; i++) {
            int[] temp = keys[i - 1];

            //Every 4th word, do this
            if (i % 4 == 0) {
                temp = rotWord(temp); //First, shift bytes left one
                temp = subWord(temp); //Second, do a subword with the S_BOX
                temp[0] = aesRCON(temp[0], i); //Third, XOR the first element with the Round Constant
            }

            // XOR the word[i-4] with the temp word we created
            for (int j = 0; j < temp.length; j++) {
                keys[i][j] = keys[i - 4][j] ^ temp[j];
            }

        }

        roundKeyHex = formatKeyMatrix(keys);

        return roundKeyHex;
    }

    /**
     * RotWord performs a one-byte circular left shift on a word.
     * Takes an input word [B0, B1, B2, B3] is transformed into [B1, B2, B3, B0].
     * @param word an array of ints, representing hex bytes
     * @return the array shifted one byte to the left
     */
    public static int[] rotWord(int[] word){
        int swap = word[0];
        int[] rotWord = new int[word.length];

        for(int i = 0; i < word.length; i++){
            if(i == word.length - 1){
                rotWord[i] = swap;
            }else {
                rotWord[i] = word[i+1];
            }
        }

        return rotWord;
    }

    /**
     * SubWord performs a byte substitution on each byte using the S_BOX.
     * @param word an array of ints, representing hex bytes
     * @return the word substitued with each byte using the S_BOX
     */
    public static int[] subWord(int[] word){
        int[] subWord = new int[word.length];


        for(int i = 0; i < word.length; i++){
            String hexByte = Integer.toHexString(word[i]);
            //System.out.println("OUTSIDE THE SBOX SWAP, HEX BYTE IN SUBWORD: " + hexByte);
            int sboxSwap = aesSBOX(hexByte);
            subWord[i] = sboxSwap;
        }

        return subWord;
    }

    /**
     * Translates a byte by into the S_BOX equivalent
     * @param inHex HEX string to be transformed using S_BOX
     * @return outHex the substituted byte as a integer represented hex
     */
    public static int aesSBOX(String inHex){
//        System.out.println("INSIDE THE SBOX SWAP");
        int hex1;
        int hex2;
        if(inHex.length() == 1) { // pad the byte with a zero
            hex1 = 0;
            hex2 = Integer.parseInt(String.valueOf(inHex.charAt(0)), 16);
        } else {
            hex1 = Integer.parseInt(String.valueOf(inHex.charAt(0)), 16);
            hex2 = Integer.parseInt(String.valueOf(inHex.charAt(1)), 16);
        }

        int outHex = S_BOX[(hex1 * 16) + hex2];

        return outHex;
    }

    /**
     * XORed with a round constant, Rcon[j], where j is the round.
     * @param inHex the byte to be XORed with the round constant
     * @param round the round of the key
     * @return the resulting byte
     */
    private static int aesRCON(int inHex, int round){
        int RCON = R_CON[round/4];

        return inHex ^ RCON;
    }

    /**
     * Takes in the the 4x4 matrix of integer in hex and XORs it with the keyHex 4x4 matrix
     * @param sHex the 4x4 matrix of the ciphertext in Hex
     * @param keyHex the 4x4 matrix of the key in Hex
     * @return returns the product of the XOR matrix
     */
    private static int[][] AESStateXOR(int[][]sHex, int [][]keyHex){
        int len = sHex.length;
        int[][] outStateHex = new int[len][len];

        for(int i = 0; i < len; i++){
            for(int j = 0; j < len; j++){
                outStateHex[j][i] = sHex[j][i] ^ keyHex[j][i];
            }
        }

        return outStateHex;
    }

    /**
     * Performs the nibble substitution on the 4x4 matrix
     * @param inStateHex input to be substituted
     * @return substituted matrix
     */
    private static int[][] AESNibbleSub(int[][] inStateHex){
        int[][] outStateHex = new int[4][4];

        int i = 0;
        for(int[] word: inStateHex){
            outStateHex[i] = subWord(word);
            i++;
        }

        return outStateHex;
    }

    /**
     * Shifts the rows of the incoming 4x4 Hex matrix,
     * Shifts each row the amount of bits of the rows index
     * ie. inStateHex[3] will be shifted three bits to the left
     * @param inStateHex the matrix to shift the rows
     * @return the 4x4 matrix with the shifted bits
     */
    private static int[][] AESShiftRow(int[][] inStateHex) {
        int[][] outStateHex = new int[4][4];

        for(int i = 0; i < outStateHex.length; i++){
            int[] rotateHex = inStateHex[i];
            for(int j = 0; j < i; j++){ // Call rotate hex as many times as the place of the array
                rotateHex = rotWord(rotateHex);
            }
            outStateHex[i] = rotateHex;
        }

        return outStateHex;
    }

    /**
     * Each byte in a column is replaced by two times
     * that byte, plus three times the the next byte, plus the byte that
     * comes next, plus the byte that follows
     * @param inStateHex The 4x4 hex matrix to mix the columns of
     * @return the mixed column 4x4 matrix
     */
    private static int[][] AESMixColumn(int[][] inStateHex){
        int[][] outStateHex = new int[4][4];

        //Perform the mix function over every column
        for(int i = 0; i < inStateHex.length; i++){
            outStateHex[0][i] = galoisMultTwo(inStateHex[0][i]) ^ galoisMultThree(inStateHex[1][i])
                    ^ inStateHex[2][i] ^ inStateHex[3][i];
            outStateHex[1][i] = inStateHex[0][i] ^ galoisMultTwo(inStateHex[1][i])
                    ^ galoisMultThree(inStateHex[2][i]) ^ inStateHex[3][i];
            outStateHex[2][i] = inStateHex[0][i] ^ inStateHex[1][i]
                    ^ galoisMultTwo(inStateHex[2][i]) ^ galoisMultThree(inStateHex[3][i]);
            outStateHex[3][i] = galoisMultThree(inStateHex[0][i]) ^ inStateHex[1][i]
                    ^ inStateHex[2][i] ^ galoisMultTwo(inStateHex[3][i]);
        }
        return outStateHex;
    }

    /**
     * Multiplication of an hex by 2, using the Galois multiplication lookup tables
     * @param inHexInt integer to be multiplied by three
     * @return the returning integer
     */
    private static int galoisMultTwo(int inHexInt){
        int hex1;
        int hex2;
        String inHex = Integer.toHexString(inHexInt);
        if(inHex.length() == 1) { // pad the byte with a zero
            hex1 = 0;
            hex2 = Integer.parseInt(String.valueOf(inHex.charAt(0)), 16);
        } else {
            hex1 = Integer.parseInt(String.valueOf(inHex.charAt(0)), 16);
            hex2 = Integer.parseInt(String.valueOf(inHex.charAt(1)), 16);
        }

        int outHex = MULT_TWO[(hex1 * 16) + hex2]; //get the inHex * 2 equivalent from lookup table

        return outHex;
    }

    /**
     * Lookup function for the multiplcation table for goalis multuplicaiton
     * @param inHexInt the integer to be multiplied by three
     * @return the int from the lookup table
     */
    private static int galoisMultThree(int inHexInt){
        int hex1;
        int hex2;
        String inHex = Integer.toHexString(inHexInt);
        if(inHex.length() == 1) { // pad the byte with a zero
            hex1 = 0;
            hex2 = Integer.parseInt(String.valueOf(inHex.charAt(0)), 16);
        } else {
            hex1 = Integer.parseInt(String.valueOf(inHex.charAt(0)), 16);
            hex2 = Integer.parseInt(String.valueOf(inHex.charAt(1)), 16);
        }

        int outHex = MULT_THREE[(hex1 * 16) + hex2]; //get the inHex * 3 equivalent from lookup table

        return outHex;
    }

    /**
     * Parses a hex string into a 4x4 integer matrix
     * @param hexString 32 character hex string to be transformed into int matrix
     * @return the 4x4 matrix
     */
    private static int[][] parseHexString(String hexString){
        int len = hexString.length();
        int[] hexStringInt = new int[len / 2];
        int[][] hexOut = new int[4][4];

        //Parsing KeyHex and inputting first ket into keys matrix
        for (int i = 0; i < len; i += 2) {
            String temp1 = String.valueOf(hexString.charAt(i));
            String temp2 = String.valueOf(hexString.charAt(i+1));
            String temp = temp1.concat(temp2);

            hexStringInt[i / 2] = Integer.parseInt(temp, 16);
        }

        int hexStringPos = 0;
        for(int i = 0; i < hexOut.length; i++){
            for(int j = 0; j < hexOut.length; j++){
                hexOut[j][i] = hexStringInt[hexStringPos];
                hexStringPos++;
            }
        }

        return hexOut;
    }

    /**
     * Prints the W matrix, by integer representation, hexstring equivalent and the resulting word as a HexString
     * @param words the words matrix to be printed
     */
    public static void printWMatrix(int[][] words) {
        System.out.println("Printing the W Matrix:");

        for (int[] word : words) {
            String[] keyOut = new String[4];
            String keyString = "";

            System.out.print("[ ");
            for (int j = 0; j < word.length; j++) {
                int temp = word[j];
                System.out.print(temp);
                if (j != 3) {
                    System.out.print(", ");
                }
                keyOut[j] = Integer.toHexString(temp);
                if (keyOut[j].length() == 1) { // pad the byte with a zero
                    keyOut[j] = "0".concat(keyOut[j]);
                }
                keyString = keyString.concat(keyOut[j]).toUpperCase();
            }
            System.out.print("]");
            System.out.println();
            System.out.print("[ ");

            for (int j = 0; j < keyOut.length; j++) {
                System.out.print(keyOut[j]);
                if (j != 3) {
                    System.out.print(", ");
                }
            }
            System.out.print("]");
            System.out.println();
            System.out.print(keyString);
            System.out.println();
        }
    }

    /**
     * Prints the 4x4 Matrix
     * @param words the words matrix to be printed
     */
    public static void printFourMatrix(int[][] words, boolean toHexString) {
        for (int[] word : words) {
            System.out.print("{ ");
            for (int hexByte: word){
                if(toHexString){
                    String hexString = Integer.toHexString(hexByte);
                    if (hexString.length() == 1) { // pad the byte with a zero
                        hexString = "0".concat(hexString);
                    }
                    System.out.print(hexString.toUpperCase() + ", ");
                }else {
                    System.out.print(hexByte + ", ");
                }
            }
            System.out.print("}");
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Taking the int[][] words matrix and formatting it into the roundKeyHex String[]
     * @param words the integer words matrix to be formatted
     * @return the formatted string array
     */
    public static String[] formatKeyMatrix(int[][] words) {
        System.out.println("Formatting the final key strings:");
        String[] outKeyString = new String[11];
        String keyString = "";

        for (int i = 0; i < words.length; i++) {
            if( i % 4 == 0 && i != 0){
                System.out.println(keyString);
                outKeyString[(i/4)-1] = keyString;
                keyString = "";
            }

            for (int j = 0; j < words[i].length; j++) {
                String temp = Integer.toHexString(words[i][j]);
                if(temp.length() == 1) { // pad the byte with a zero
                    temp = "0".concat(temp);
                }
                keyString = keyString.concat(temp).toUpperCase();
            }

        }
        System.out.println();

        return outKeyString;
    }

    /**
     * Taking the int[][] words matrix and formatting it into the final cipher text string
     * @param inStateHex the integer 4x4 matrix to be formatted in hex
     * @return the formatted string
     */
    public static String aesCipherFormat(int[][] inStateHex) {
        String outCipher = "";

        for (int i = 0; i < inStateHex.length; i++) {
            for (int j = 0; j < inStateHex.length; j++) {
                String temp = Integer.toHexString(inStateHex[j][i]);
                if(temp.length() == 1) { // pad the byte with a zero
                    temp = "0".concat(temp);
                }
                outCipher = outCipher.concat(temp).toUpperCase();
            }
        }

        return outCipher;
    }
}
