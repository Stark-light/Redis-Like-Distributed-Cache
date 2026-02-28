# Redis-Like-Distributed-Cache (Java)
Fault-tolerant distributed cache in Java using consistent hashing, replication, eviction (LRU/LFU), persistence (WAL + snapshot), and Netty-based TCP protocol.

A production-style **distributed in-memory cache** built in **Java** with **consistent hashing**, **replication**, **eviction policies (LRU/LFU)**, and **persistence (WAL + snapshots)**.
Designed to simulate real-world cache systems like **Redis / Memcached** and distributed routing models like **Dynamo-style sharding**.

---

## 🚀 Features

### ✅ Core
- **Distributed sharding** using **Consistent Hashing**
- **Virtual nodes (vnodes)** for balanced key distribution
- **Replication (RF=2/3)** for high availability
- **Automatic failover reads** (fallback to replica if primary fails)
- **Eviction policy**: LRU (LFU optional)
- **TTL / Expiry support**
- **Persistence**
  - **WAL (Write Ahead Log)** for durable writes
  - **Periodic snapshots** for fast recovery
- **Custom TCP protocol** (Redis-inspired) built using **Netty**
- Concurrent request handling (multi-client)

---

## 🧠 System Design Overview

### Architecture
Client → Router (optional) → Cache Node Cluster

Each Cache Node contains:
- In-memory store (ConcurrentHashMap)
- Eviction engine (LRU/LFU)
- Replication manager
- Persistence manager (WAL + Snapshot)
- TCP server (Netty)

---

## 📡 Protocol (Commands)

Supported commands:

```txt
PING
SET <key> <value>
GET <key>
DEL <key>
EXPIRE <key> <ttlSeconds>
STATS
