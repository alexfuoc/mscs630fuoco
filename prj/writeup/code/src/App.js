import "./App.css";

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <h1 className="title">Note Encrypter</h1>
        <img src={"memo512.png"} className="App-logo" alt="logo" />
      </header>
        <p className="body">
          Using AES 128 bit your notes will be encrypted. 
        </p>
        <p className="body" >
        Just write your note and provide the key to start protecting yourself!
        </p>
    </div>
  );
}

export default App;
