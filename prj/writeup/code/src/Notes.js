import React, { Component } from "react";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Paper from "@material-ui/core/Paper";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import "./App.css";
import { getNotes, decryptNote, hexToByte } from "./utils";

export default class Notes extends Component {
  constructor(props) {
    super(props);
    this.state = {
      keyInput: [],
      data: [],
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.refreshNotes = this.refreshNotes.bind(this);
  }

  //Load notes for db on mount
  componentDidMount() {
    this.refreshNotes();
  }

  // Controlled Component for text inputs
  handleChange(event) {
    var keys = this.state.keyInput;
    var inputIndex = event.target.name;
    keys[inputIndex] = event.target.value;
    this.setState({
      keyInput: keys,
    });
  }

  //Button Clicked
  handleSubmit(event) {
    try {
      //decrypt
      var keyIndex = event.currentTarget.value;
      var updateData = this.state.data;
      var keyInput = this.state.keyInput;

      //check for sam key input
      if (keyInput[keyIndex].length !== 16) {
        alert("WRONG KEY INPUT");
      } else {
        var keyByte = hexToByte(keyInput[keyIndex]);
        var decryptedText = decryptNote(
          updateData[keyIndex].NoteCipher,
          keyByte
        );
        if (decryptedText instanceof Error) {
          console.log("Error Returned to Component");
        } else {
          updateData[keyIndex].NoteCipher = decryptedText;
          this.setState({
            data: updateData,
          });
        }
      }
    } catch (error) {
      //error
      console.log("Error Decrypting");
    }
  }

  refreshNotes() {
    var notes;
    getNotes()
      .then((data) => {
        notes = data;
        console.log("Returned Promise In Notes Component Did Mount");
        console.log(notes);
        this.setState({
          data: notes.Items,
          keyInput: new Array(notes.length),
        });
      })
      .catch((err) => console.log(err));
  }

  render() {
    var { data } = this.state;

    return (
      <div className="table">
        <h3 className="descript title">
          Ciphered Notes. Input the KeyHex to decrypt
        </h3>
        <TableContainer component={Paper}>
          <Table aria-label="simple table">
            <TableHead>
              <TableRow key="Header" style={{ maxHeight: "100px" }}>
                <TableCell>Title</TableCell>
                <TableCell align="right">Note</TableCell>
                <TableCell align="right">Key</TableCell>
                <TableCell align="right">Decipher</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {data.map((row, index) => (
                <TableRow key={row.Title.concat(row.NoteCipher)}>
                  <TableCell component="th" scope="row">
                    {row.Title}
                  </TableCell>
                  <TableCell
                    style={{
                      whiteSpace: "normal",
                      wordBreak: "break-word",
                      width: "500px",
                    }}
                    align="right"
                  >
                    {row.NoteCipher}
                  </TableCell>
                  <TableCell align="right">
                    <TextField
                      id={row.Title}
                      label="Key"
                      placeholder="Input your key"
                      name={index}
                      onChange={this.handleChange}
                      variant="filled"
                    />
                  </TableCell>
                  <TableCell align="right">
                    <Button
                      color="primary"
                      value={index}
                      onClick={this.handleSubmit}
                    >
                      Decrypt Note
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
        <Button color="primary" onClick={this.refreshNotes}>
          Refresh Notes
        </Button>
      </div>
    );
  }
}
