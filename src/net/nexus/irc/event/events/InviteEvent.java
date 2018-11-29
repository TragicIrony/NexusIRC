package net.nexus.irc.event.events;

import net.nexus.irc.Client;
import net.nexus.irc.event.IRCEvent;

/**
 * @author Kevin
 */
public class InviteEvent extends IRCEvent {

	public InviteEvent(Client client, String[] args) {
		super(client, args);
	}

	public String getChannelName() {
		return parameter.substring(parameter.lastIndexOf(' ') + 1).replace(":", "");
	}

}
