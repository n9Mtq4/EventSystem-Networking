package com.n9mtq4.eventsystem.networking

import com.n9mtq4.eventsystem.core.EventSystem
import com.n9mtq4.eventsystem.usertext.ui.impl.UISysOut

/**
 * Created by will on 4/10/18 at 8:50 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
fun main(args: Array<String>) {
	
	val eventSystem = EventSystem()
	eventSystem.addListenerAttribute(UISysOut())
	eventSystem.addListenerAttribute(PrintNetworkEvents())
	
	val server = NetworkServer(eventSystem, 9999)
	server.startServer()
	
}
