package com.n9mtq4.eventsystem.networking

import com.n9mtq4.eventsystem.core.EventSystem
import com.n9mtq4.eventsystem.core.annotation.ListensFor
import com.n9mtq4.eventsystem.core.listener.BaseListener

/**
 * Created by will on 4/10/18 at 9:01 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
open class PrintNetworkEvents : BaseListener {
	
	@ListensFor
	fun listenForNetwork(networkedEvent: NetworkedEvent, initEventSystem: EventSystem) {
		println(networkedEvent)
	}
	
}
