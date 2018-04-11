package com.n9mtq4.eventsystem.networking

import com.n9mtq4.eventsystem.core.EventSystem
import com.n9mtq4.eventsystem.usertext.println
import java.net.ServerSocket
import kotlin.concurrent.thread

/**
 * Created by will on 4/10/18 at 8:31 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
class NetworkServer(private val eventSystem: EventSystem, private val port: Int) {
	
	private lateinit var serverSocket: ServerSocket
	
	fun startServer() {
		
		serverSocket = ServerSocket(port)
		listenForClients()
		
	}
	
	fun listenForClients() {
		
		thread(start = true) {
			while (true) {
				
				val clientSocket = serverSocket.accept()
				
				thread(start = true) {
					eventSystem.println("Client Connecting...")
					val clientConnection = NetworkClientConnection(clientSocket)
					clientConnection.setup()
					eventSystem.addListenerAttribute(clientConnection)
					eventSystem.println("Client Connected")
				}
				
			}
		}
		
	}
	
	fun close() {
		
		eventSystem.dispose()
		serverSocket.close()
		
	}
	
}
