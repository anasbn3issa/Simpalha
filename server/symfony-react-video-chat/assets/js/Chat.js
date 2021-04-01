import React, { useState } from "react";
import { useParams } from "react-router-dom";

import axios from "axios";
import Video from "twilio-video";

const Chat = () => {
  const [roomName, setRoomName] = useState("");
  const [hasJoinedRoom, setHasJoinedRoom] = useState(false);
  const [hasRendered, sethasRendered] = useState(false);

  const joinChat = () => {
    sethasRendered(true);
    const id = window.location.pathname.substring(1);
    console.log(hasRendered);
    axios
      .post("/access_token/" + id)
      .then((response) => {
        console.log("resp");
        connectToRoom(response.data.token);
        setHasJoinedRoom(true);
        setRoomName("");
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const connectToRoom = (token) => {
    const { connect, createLocalVideoTrack } = Video;

    let connectOption = { name: roomName };

    connect(token, connectOption).then(
      (room) => {
        console.log(`Successfully joined a Room: ${room}`);

        const videoChatWindow = document.getElementById("video-chat-window");

        createLocalVideoTrack().then((track) => {
          videoChatWindow.appendChild(track.attach());
        });

        room.on("participantConnected", (participant) => {
          console.log(`Participant "${participant.identity}" connected`);

          participant.tracks.forEach((publication) => {
            if (publication.isSubscribed) {
              const track = publication.track;
              videoChatWindow.appendChild(track.attach());
            }
          });

          participant.on("trackSubscribed", (track) => {
            videoChatWindow.appendChild(track.attach());
          });
        });
      },
      (error) => {
        console.error(`Unable to connect to Room: ${error.message}`);
      }
    );
  };
  if (hasRendered == false) joinChat();
  return (
    <div className="container">
      <div className={"col-md-12"}>
        <h1 className="text-title">Simpalha Chat Room</h1>
      </div>

      <div className="col-md-6">
        <div id="video-chat-window"></div>
      </div>
    </div>
  );
};

export default Chat;
