package com.n9mtq4.eventsystem.networking

import com.n9mtq4.eventsystem.core.EventSystem
import com.n9mtq4.eventsystem.usertext.events.UserTextEvent
import com.n9mtq4.eventsystem.usertext.listener.UserTextListener
import com.n9mtq4.eventsystem.usertext.modules.ModuleExit
import com.n9mtq4.eventsystem.usertext.modules.ModuleListenerList
import com.n9mtq4.eventsystem.usertext.println
import com.n9mtq4.eventsystem.usertext.ui.impl.UIScanner
import java.net.Socket

/**
 * Created by will on 4/10/18 at 8:52 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
fun main(args: Array<String>) {
	
	val eventSystem = EventSystem()
	
	eventSystem.addListenerAttribute(UIScanner())
	eventSystem.addListenerAttribute(ModuleExit())
	eventSystem.addListenerAttribute(ModuleListenerList())
	
	eventSystem.addListenerAttribute(PrintNetworkEvents())
	eventSystem.addListenerAttribute(SendNetEvent())
	
	val mySocket = Socket("127.0.0.1", 9999)
	val clientConnection = NetworkClientConnection(mySocket)
	eventSystem.addListenerAttribute(clientConnection)
	clientConnection.setup()
	
}

class SendNetEvent : UserTextListener {
	
	override fun receiveUserText(event: UserTextEvent, eventSystem: EventSystem) {
		if (event.msg.trim().equals("net ping", ignoreCase = true)) {
			eventSystem.println("Sending Network event")
			eventSystem.pushEvent(NetworkedEvent(eventSystem))
		}
	}
	
}
