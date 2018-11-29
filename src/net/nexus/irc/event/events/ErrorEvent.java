package net.nexus.irc.event.events;

import net.nexus.irc.Client;
import net.nexus.irc.event.IRCEvent;

/**
 * @author Kevin
 */
public class ErrorEvent extends IRCEvent {

	public ErrorEvent(Client client, String[] args) {
		super(client, args);
	}

}
