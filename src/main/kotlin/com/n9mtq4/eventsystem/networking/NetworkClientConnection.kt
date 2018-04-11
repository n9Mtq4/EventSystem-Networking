package com.n9mtq4.eventsystem.networking

import com.n9mtq4.eventsystem.core.EventSystem
import com.n9mtq4.eventsystem.core.annotation.Async
import com.n9mtq4.eventsystem.core.annotation.ListensFor
import com.n9mtq4.eventsystem.core.event.DisableEvent
import com.n9mtq4.eventsystem.core.event.EnableEvent
import com.n9mtq4.eventsystem.core.listener.BaseListener
import com.n9mtq4.eventsystem.core.listener.impl.DisableListener
import com.n9mtq4.eventsystem.core.listener.impl.EnableListener
import kotlinx.coroutines.experimental.launch
import java.io.*
import java.net.Socket

/**
 * Created by will on 4/10/18 at 8:14 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
class NetworkClientConnection(@Transient val socket: Socket) : EnableListener, DisableListener, BaseListener, Serializable {
	
	@Transient private val linkedEventSystems = mutableListOf<EventSystem>()
	
	@Transient private lateinit var inputStream: InputStream
	@Transient private lateinit var outputStream: OutputStream
	@Transient private lateinit var objectInputStream: ObjectInputStream
	@Transient private lateinit var objectOutputStream: ObjectOutputStream
	
	fun setup() {
		
		inputStream = socket.getInputStream()
		outputStream = socket.getOutputStream()
		objectOutputStream = ObjectOutputStream(outputStream)
		objectOutputStream.flush()
		objectInputStream = ObjectInputStream(inputStream)
		
		launch { receiveInputObjects() }
		
	}
	
	private fun receiveInputObjects() {
		while (true) {
			val netEvent = objectInputStream.readObject() as NetworkedEvent
			receivedIncomingEventFromNet(netEvent)
		}
	}
	
	override fun onEnable(enableEvent: EnableEvent, eventSystem: EventSystem) {
		linkedEventSystems += eventSystem
	}
	
	override fun onDisable(disableEvent: DisableEvent, eventSystem: EventSystem) {
		linkedEventSystems -= eventSystem
		if (linkedEventSystems.isEmpty()) close() // close if there is nothing else to do
	}
	
	private fun close() {
		objectOutputStream.close()
		objectInputStream.close()
		outputStream.close()
		inputStream.close()
		socket.close()
	}
	
	@Async
	@ListensFor
	fun listenForNetworkEvents(networkedEvent: NetworkedEvent, eventSystem: EventSystem) {
		
		if (this == networkedEvent.originatingNetworkClientConnection) return
		
		// send it out the client socket
		objectOutputStream.writeObject(networkedEvent)
		objectOutputStream.flush()
		
	}
	
	private fun receivedIncomingEventFromNet(event: NetworkedEvent) {
		
		event.originatingNetworkClientConnection = this // mark this event as coming from us, so we don't rebroadcast it
		linkedEventSystems.forEach { it.pushEvent(event) }
		
	}
	
}
