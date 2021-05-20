import React from "react";
import "./App.css";
import VideoChat from "./VideoChat";

const App = () => {
  return (
    <div className="app">
      <header>
        <h1>Simpalha Chat Room</h1>
      </header>
      <main>
        <VideoChat />
      </main>
      <footer>
        <p>
          Made with{" "}
          <span role="img" aria-label="React">
            ⚛️
          </span>{" "}
          by <a href="#">Wajdi</a>
        </p>
      </footer>
    </div>
  );
};

export default App;
