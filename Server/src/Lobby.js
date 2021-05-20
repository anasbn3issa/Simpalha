import React, { useState, useEffect } from "react";

const Lobby = ({
  username,
  roomName,
  handleSubmit,
  handleUsernameChange,
  connecting,
}) => {
  return (
    <form onSubmit={handleSubmit}>
      <h2>Enter a room</h2>
      <div>
        <label htmlFor="name">Name:</label>
        <input
          type="text"
          id="field"
          value={username}
          readOnly={connecting}
          onChange={handleUsernameChange}
          required
        />
      </div>

      <div>
        <label htmlFor="room">Room name:</label>
        <input
          type="text"
          id="room"
          value={roomName}
          readOnly={connecting}
          required
        />
      </div>

      <button type="submit" disabled={connecting}>
        {connecting ? "Connecting" : "Join"}
      </button>
    </form>
  );
};

export default Lobby;
