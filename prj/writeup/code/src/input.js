import React, { Component } from "react";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import "./App.css";
import { inputNote } from "./utils";

export default class Input extends Component {
  constructor(props) {
    super(props);
    this.state = { key: "", note: "", title: "" };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  // Controlled Component for text inputs
  handleChange(event) {
    var name = event.target.name;
    this.setState({
      [name]: event.target.value,
    });
  }

  //Button Clicked
  handleSubmit(event) {
    try {
      if (
        this.state.key === "" ||
        this.state.note === "" ||
        this.state.title === "" ||
        this.state.key.length !== 16
      ) {
        alert("Must have proper note, key and title format.");
      } else {
        inputNote(
          this.state.note,
          this.state.key.toLowerCase(),
          this.state.title
        );
        this.setState({ key: "", note: "", title: "" });
        alert("The note is was submitted");
      }
    } catch (error) {
      alert("ERROR SUBMITTING NOTE");
    }
    event.preventDefault();
  }

  render() {
    var { key, note, title } = this.state;

    return (
      <div>
        <h3 className="descript title">
          Input the note, key and title below. The key is 16 HEXIDEXIMAL
          CHARCTERS ONLY.
        </h3>
        <form
          onSubmit={this.handleSubmit}
          className="note-form"
          noValidate
          autoComplete="off"
        >
          <div className="note-form">
            <label className="descript-small title">
              Input a title for your Note.
            </label>
            <div className="note-form">
              <TextField
                id="note-title"
                label="Title"
                placeholder="Input your title here"
                name="title"
                value={title}
                onChange={this.handleChange}
                variant="filled"
              />
            </div>
            <label className="descript-small title">
              Input the plaintext Note here.
            </label>
            <TextField
              id="note-text"
              label="Note"
              className="note-input"
              placeholder="Write your note here"
              multiline
              rows={8}
              rowsMax={10}
              name="note"
              value={note}
              onChange={this.handleChange}
              variant="filled"
            />
          </div>
          <label className="descript-small title">
            Input the Hex String of 16 characters here.
          </label>
          <div className="note-form">
            <TextField
              id="note-key"
              label="Key"
              placeholder="Input your key"
              name="key"
              value={key}
              onChange={this.handleChange}
              variant="filled"
            />
          </div>
          <Button color="primary" onClick={this.handleSubmit}>
            Encrypt Note
          </Button>
        </form>
      </div>
    );
  }
}
