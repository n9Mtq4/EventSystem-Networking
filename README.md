# EventSystem Networking Module
This handles server and client connections.
Allows for event systems to send events to each other through a network socket.

## Examples

### Server Example
```kotlin
val eventSystem = EventSystem()
eventSystem.addListenerAttribute(UISysOut())
eventSystem.addListenerAttribute(PrintNetworkEvents())

val server = NetworkServer(eventSystem, 9999)
server.startServer()
```

### Client Example
```kotlin
val eventSystem = EventSystem()

val mySocket = Socket("127.0.0.1", 9999)
val clientConnection = NetworkClientConnection(mySocket)
eventSystem.addListenerAttribute(clientConnection)
clientConnection.setup()
```
