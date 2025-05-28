# ChatApplication Client-Server Chat Application in Java
This repository contains a simple client-server chat application implemented using Java AWT for the GUI, SWIG for native interface integration, and Java Socket programming for network communication.
Features:

Graphical User Interface (GUI): Built using Java AWT for a simple yet functional chat interface.
Socket Communication: Utilizes Java's ServerSocket and Socket classes for real-time messaging between the client and server.
Multi-Client Support: Allows multiple clients to connect and chat with the server.
Message Broadcasting: The server relays messages to all connected clients.
SWIG Integration: Enables interoperability with C/C++ for potential performance optimizations.
How It Works:
The server starts and listens for incoming client connections.
Clients connect to the server and establish a socket-based communication channel.
Messages sent by clients are processed by the server and forwarded to other connected clients.
The GUI displays messages dynamically, allowing real-time interaction.
