# Minecraft Proxy

A powerful Spring Boot-based Minecraft proxy server that hides the player's real IP address from the Minecraft server, logs user activity, and supports mirror sessions to bypass cheat checks.

## Features

- 🌐 **IP Protection**: Hide the real IP address of Minecraft clients by routing connections through this proxy.
- 📋 **User Data Logging**: All user connection activity is logged and stored in a database.
- 🔁 **Mirror Sessions**: Create a mirrored session between two clients connected under the same username, bypassing moderator cheat inspections.
- 🎮 **Version Support**: Supports Minecraft versions **1.16.5** and **1.19.2**, with an extensible architecture to support more versions.
- 🤝 **Multi-Client Support**: Multiple users can connect and create mirrored clients without installing the program locally. Just run it on a remote server and share access with friends.

## How It Works

The proxy sits between Minecraft clients and the actual Minecraft server. It intercepts and forwards all traffic, rewriting necessary data to maintain a valid session.

### Mirror Session Explained

When a player joins a Minecraft server through the proxy using a modded (cheat) client:

1. **First Connection**: The player connects to the proxy using a client with cheats. The proxy logs and forwards the connection to the real server.
2. **Second Connection**: The same player opens a second Minecraft client (clean version, no cheats) and connects again using the same nickname and version.
3. **Mirroring**: The proxy creates a **mirror session** between both clients. From the Minecraft server's point of view, it's still the same single user.
4. **Moderator Check**: If a moderator attempts a demo inspection (e.g., to check for cheats), the user can **close the first (cheat) client**. The clean client will then continue the session without any traces of cheating.
5. This allows seamless bypass of most client-side mod inspections.

> ⚠️ This method works because Minecraft servers only expect one connection per nickname. The proxy manages two actual client connections, forwarding data to make them appear as one to the server.

## Release Package

The release `.zip` contains:

```
minecraft-proxy.zip
├── start.bat               # For Windows
├── start.sh                # For Linux/macOS
├── minecraft-proxy.jar     # Executable JAR
└── config/
    └── config.properties   # Configuration file
```

### Run Instructions

Make the script executable (Linux/macOS):

```bash
chmod +x start.sh
./start.sh
```

Or just double-click `start.bat` on Windows.

## Configuration

Database settings are defined in `config/config.properties`.

---

## Build Instructions

You can build the project using Maven. Follow the steps below:

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/minecraft-proxy.git
cd minecraft-proxy
```

### 2. Add Configuration File

Move template config file in the correct location:

```bash
mkdir -p config
cp config_template/config.properties config/config.properties
```

### 3. Build the Project

Make sure you have Maven and Java installed. Then run:

```bash
mvn clean package
```

The resulting JAR file will be located in:

```
target/minecraft-proxy-<version>.jar
```

## Disclaimer

This software is provided for educational purposes only. Bypassing cheat detection may violate the terms of service of Minecraft servers. Use responsibly.

---
