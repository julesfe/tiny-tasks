import React from 'react';
import logo from './logo.svg';
import './App.css';

function LandingPageComponent() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo"/>
        <a
          className="App-link"
          href="/tasks"
          target="_blank"
          rel="noopener noreferrer"
        >
          Enter TinyTasks
        </a>
      </header>
    </div>
  );
}

export default App;
