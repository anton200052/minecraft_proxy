# Minecraft Protocol Reverse Engineering & Shadow Proxy

![Java](https://img.shields.io/badge/Java-Spring_Boot-orange)
![Protocol](https://img.shields.io/badge/Protocol-TCP%2FIP-blue)
![Architecture](https://img.shields.io/badge/Architecture-MitM-purple)
![Type](https://img.shields.io/badge/Type-Research_PoC-red)

**A custom-built Man-in-the-Middle (MitM) proxy infrastructure designed to analyze, intercept, and manipulate Minecraft protocol traffic in real-time.**

This project explores vulnerabilities in the unencrypted communication of non-licensed ("offline-mode") Minecraft servers. It features a completely custom implementation of the Minecraft Protocol (handshake, packet serialization/deserialization) written from scratch without external protocol libraries.

---

## 💡 The Novel Discovery

During the development of this research tool, I identified and implemented a unique vulnerability exploitation technique that—to the best of my knowledge—**has not been previously documented or utilized in public game security research.**

**The Discovery:**
It is possible to maintain a single active session with the Game Server while seamlessly "hot-swapping" the control between two completely different physical client instances. By manipulating the TCP stream at the proxy level, a session can be transferred from a "Dirty" client (running cheat software) to a "Clean" client (running vanilla software) **without the server detecting a disconnect or protocol violation.**

---

## 🛠️ How It Works

The core vulnerability lies in the fact that many unofficial servers disable packet encryption. This allows the proxy to act as a transparent bridge, parsing raw bytes into Java Objects and vice versa.

### 1. Packet Sniffing & Manipulation (Client-less Cheats)
Since the proxy understands the protocol structure, it can modify packets on the fly.
* **Concept:** Instead of injecting code into the game process (which Anti-Cheats scan for), the proxy modifies the network stream.
* **Example:** The proxy can artificially generate "Attack" packets or modify "Position" packets, effectively implementing cheats (like AutoClicker or Reach) while the game client remains unmodified and clean during file system inspections.

### 2. The "Shadow Swap" Mechanism
This is the flagship feature designed to bypass manual administrative checks (Screen Shares).

* **Phase 1: Mirroring.** Two clients connect to the proxy ports. Client A (Cheats) controls the player. Client B (Clean) acts as a "Shadow"—it receives all data *from* the server (rendering the game world) but its outgoing packets are **blocked** by the proxy.
* **Phase 2: The Swap.** When a check is initiated, Client A is closed. The proxy detects the socket closure but keeps the server connection alive.
* **Phase 3: Handover.** The proxy instantly unblocks Client B. Client B takes over the session seamlessly. The administrator sees a user playing on a clean client with no history of cheats in the process list.

---

## 🏗️ Architecture Flow

The following diagram illustrates the traffic flow during the "Shadow Swap" process.
<img width="5292" height="4575" alt="Image" src="https://github.com/user-attachments/assets/0ed96ba6-c784-4b71-89e1-79e2bc638753" />

---
## 🗂️ Technical Implementation

### ⚙️ Core Framework
**Stack:** `Java` / `Spring Boot`
* Manages the application lifecycle and dependency injection.
* Ensures modularity and ease of testing.

### 🔌 Networking
**Stack:** `java.net.Socket` (Native)
* Low-level TCP socket management.
* **Zero external network libraries:** No Netty or high-level wrappers were used. This ensures absolute granular control over every byte sent and received.

### 🧠 Protocol Engine
**Stack:** `Custom Implementation`
* A "From Scratch" implementation of the [Minecraft Protocol](https://minecraft.wiki/w/Minecraft_Wiki:Protocol_documentation).
* Manually handles **VarInt** encoding/decoding, buffer reading, and packet ID mapping.
* Implements the full Handshake and Login sequence logic.

### 🪞 Mirror Logic & State Manager
**Stack:** `Custom State Machine`
* Contains complex logic to sync the "Shadow" client.
* Ensures the shadow client receives the exact initialization sequence (Join Game, Chunk Data, Inventory) to match the active client's state perfectly before the swap occurs.

---

## ⚙️ Getting Started
### Installation

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/anton200052/minecraft_proxy
    cd minecraft_proxy
    ```

3.  **Build and Run:**
    ```bash
    mvn spring-boot:run
    ```
    *The application will start on `http://localhost:8080` (or your configured port).*

## ⚠️ Disclaimer

**Educational Purpose Only.**
This software was developed as a Proof of Concept (PoC) to demonstrate vulnerabilities in unencrypted network protocols and the risks of relying on client-side checks for anti-cheat enforcement.

* This tool is not intended for malicious use in online games.
* The author does not condone violating the Terms of Service of any platform.
