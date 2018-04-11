package com.n9mtq4.eventsystem.networking

import com.n9mtq4.eventsystem.core.EventSystem
import com.n9mtq4.eventsystem.core.event.BaseEvent
import java.io.Serializable

/**
 * Created by will on 4/10/18 at 7:10 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
open class NetworkedEvent(@Transient override val initiatingEventSystem: EventSystem, var originatingNetworkClientConnection: NetworkClientConnection? = null, override var isCanceled: Boolean = false) : BaseEvent, Serializable
