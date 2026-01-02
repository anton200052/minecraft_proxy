Minecraft Packet Sniffer & Shadow Proxy
A Man-in-the-Middle (MitM) proxy server designed to analyze, intercept, and manipulate Minecraft protocol traffic.

This project is a Proof-of-Concept (PoC) demonstrating a vulnerability in non-licensed ("cracked" / offline-mode) Minecraft servers where packet encryption is often disabled. By implementing the Minecraft protocol from scratch, this tool allows for seamless session hijacking ("Shadow Swapping") and packet-level manipulation without modifying the game client.

🔬 The Vulnerability: Unencrypted Traffic
The core concept relies on the fact that many unofficial Minecraft servers disable standard packet encryption to allow players without Mojang accounts to join.

Using the Minecraft Protocol Documentation, I implemented a custom Packet Sniffer & Deserializer.

From Scratch Implementation: No external protocol libraries were used. Every byte is read and parsed into Java Objects manually based on the protocol specification.

Deep Inspection: The proxy sits between the client and the server, intercepting raw TCP traffic, converting it to readable objects, and re-serializing it back to the stream.

🕵️ Key Mechanics: "Shadow Session" & Hot-Swapping
The most advanced feature of this proxy is the Session Hot-Swap mechanism, designed to bypass "Screen Share" (SS) checks commonly used by server administrators to detect cheats.

How the "Shadow Swap" Works
The system utilizes two distinct proxy ports to manage two simultaneous client connections for a single server session.

The "Dirty" Client (Active):

Connects to Proxy Port A (e.g., 25565).

Status: Full Control. Sends packets to the server and receives updates.

Usage: The player uses this client with cheats/mods injected on their main machine.

The "Shadow" Client (Passive):

Connects to Proxy Port B (e.g., 25566) from a clean machine or VM.

Status: Read-Only (Phantom). The proxy forwards all packets from the Server to this client, but blocks all outgoing packets from this client to the server.

Result: The Shadow Client mirrors the exact movement and state of the Dirty Client in real-time, effectively spectating the session from a first-person perspective.

The Handover (The Exploit):

When an admin requests a screen share check, the player disconnects/closes the Dirty Client.

The Proxy detects the socket closure but keeps the connection to the Minecraft Server alive.

The Proxy immediately promotes the Shadow Client to "Active" status, allowing it to send packets.

Outcome: The player continues the session seamlessly on the clean machine (Shadow Client) without ever logging out or triggering a "player left" message on the server.

Architecture Diagram
Фрагмент кода

sequenceDiagram
    participant DirtyClient as Client A (Cheats)
    participant ShadowClient as Client B (Clean)
    participant Proxy as MitM Proxy
    participant Server as Minecraft Server

    Note over DirtyClient, Server: Phase 1: Gameplay
    DirtyClient->>Proxy: Send Movement/Action Packets
    Proxy->>Server: Forward Packets
    Server->>Proxy: Send World Updates
    Proxy->>DirtyClient: Forward Updates
    Proxy->>ShadowClient: Mirror Updates (Sync State)
    ShadowClient->>Proxy: Send Packets (BLOCKED ❌)

    Note over DirtyClient, Server: Phase 2: The Swap
    DirtyClient->>Proxy: Disconnect (Close Socket)
    Proxy->>Proxy: Detect Disconnect -> Promote Client B
    
    Note over ShadowClient, Server: Phase 3: Clean Session
    ShadowClient->>Proxy: Send Movement/Action Packets
    Proxy->>Server: Forward Packets (Allowed ✅)
    Server->>Proxy: Send World Updates
    Proxy->>ShadowClient: Forward Updates
⚡ Additional Capabilities
🛠 Client-less Packet Manipulation (External Cheats)
Traditional cheats require modifying the game code (internal cheats) or injecting DLLs. This proxy allows for External Packet Manipulation.

Since the proxy parses packets into Java Objects, logic can be written on the proxy side to modify player behavior (e.g., AutoClicker, Aura, Anti-Knockback) by altering outgoing packets.

Benefit: The game client remains 100% vanilla (unmodified), making client-side anti-cheats ineffective against these modifications.

🧩 Modular Architecture
The project is built on Spring Boot, allowing for easy extension:

Packet Handler Interface: Easily add new logic for specific packet IDs.

Version Agnostic Design: While currently supporting specific versions, the deserializer pattern is adaptable to protocol updates.

⚠️ Disclaimer
This software is developed for educational and research purposes only, to demonstrate vulnerabilities in unencrypted network protocols.

Do not use this tool to violate the Terms of Service of any Minecraft server.

The author does not condone cheating or malicious behavior in online games.
