package net.nexus.irc.event.events;

import net.nexus.irc.Client;
import net.nexus.irc.event.IRCEvent;

/**
 * @author Kevin
 */
public class QuitEvent extends IRCEvent {

	public QuitEvent(Client client, String[] args) {
		super(client, args);
	}

	public String getMessage() {
		return parameter.substring(1);
	}

}
