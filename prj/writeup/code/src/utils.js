import * as AWS from "aws-sdk";
import { v4 as uuidv4 } from "uuid";
var aesjs = require("aes-js");

AWS.config.update({
  region: "us-east-2",
  endpoint: "dynamodb.us-east-2.amazonaws.com",
});

var docClient = new AWS.DynamoDB.DocumentClient();

/**
 * Encrypts the note with the 16char key hex and the note
 * @param {*} notePT
 * @param {*} byteKey
 * @returns The encrypted Note
 */
function encryptNote(notePT, byteKey) {
  try {
    var byteNote = aesjs.utils.utf8.toBytes(notePT);
    // The counter is optional, and if omitted will begin at 1
    var aesCtr = new aesjs.ModeOfOperation.ctr(byteKey, new aesjs.Counter(5));
    var encryptedBytes = aesCtr.encrypt(byteNote);

    // To print or store the binary data, you may convert it to hex
    var encryptedHex = aesjs.utils.hex.fromBytes(encryptedBytes);
    // console.log("Printing the Plaintext Note:");
    // console.log(notePT);
    // console.log("Printing the Encrypted Note in Hex:");
    // console.log(encryptedHex);

    return encryptedHex;
  } catch (error) {
    alert("ERROR ENCRYPTING PLEASE TRY AGAIN");
    return error;
  }
}

/**
 * Decrypts the Note with the hex Byte Array and the CipherText
 * @param {*} notePT
 * @param {*} byteKey
 * @returns
 */
function decryptNote(notePT, byteKey) {
  try {
    // When ready to decrypt the hex string, convert it back to bytes
    var encryptedBytes = aesjs.utils.hex.toBytes(notePT);

    // The counter mode of operation maintains internal state, so to
    // decrypt a new instance must be instantiated.
    var aesCtr = new aesjs.ModeOfOperation.ctr(byteKey, new aesjs.Counter(5));
    var decryptedBytes = aesCtr.decrypt(encryptedBytes);

    // Convert our bytes back into text
    var decryptedText = aesjs.utils.utf8.fromBytes(decryptedBytes);
    console.log("Printing the Decrypted Note:");
    console.log(decryptedText);
    // "Text may be any length you wish, no padding is required."
    return decryptedText;
  } catch (error) {
    alert("ERROR DECRYPTING THE MESSAGE");
    return error;
  }
}

/**
 * Change key from hexstring to a byte array
 * @param {*} key 16 char hex string
 * @returns the key in a byte array
 */
function hexToByte(key) {
  try {
    var hexArray = key.split("");
    var byteArray = [];

    hexArray.forEach((hex) => {
      var byteHex = aesjs.utils.hex.toBytes(hex)[0];

      if (isNaN(byteHex)) {
        throw new Error("Invalid Hex String");
      } else {
        byteArray.push(byteHex);
      }
    });

    console.log("Printing the Key BEFORE");
    console.log(key);
    console.log("Printing the Key AFTER");
    console.log(byteArray);

    return byteArray;
  } catch (error) {
    console.log("ERROR CHANGING HEX TO BYTE ARRAY");
  }
}

/**
 * Inputs the note into the database
 * @param {*} notePT
 * @param {*} key
 * @param {*} title
 */
function inputNote(notePT, key, title) {
  try {
    var byteArray = hexToByte(key);
    var encryptedHex = encryptNote(notePT, byteArray);

    var params = {
      TableName: "Notes2",
      Item: {
        UUID: uuidv4(),
        NoteCipher: encryptedHex,
        Title: title,
      },
    };

    docClient.put(params, function (err, data) {
      if (err) {
        console.log("ERROR", err);
        return err;
      } else {
        console.log("Success Note added to DB");
        return "Success: Note added to DB";
      }
    });
  } catch (error) {
    console.log(error);
  }
}

/**
 * Queries the database for all of the notes
 * @returns a promise with the db results
 */
function getNotes() {
  try {
    let params = {
      TableName: "Notes2",
    };

    return new Promise((resolve, reject) => {
      docClient.scan(params, function (err, data) {
        if (err) {
          console.log("Error");
          console.log(err);
          reject();
        } else {
          console.log("Successful Return:");
          console.log(data);
          resolve(data);
        }
      });
    });
  } catch (error) {
    console.log(error);
  }
}

export { getNotes, inputNote, hexToByte, decryptNote, encryptNote };
