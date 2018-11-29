package net.nexus.irc.event.events;

import net.nexus.irc.Client;
import net.nexus.irc.event.IRCEvent;

/**
 * @author Kevin
 */
public class PingEvent extends IRCEvent {

	public PingEvent(Client client, String[] args) {
		super(client, args);
	}

}
