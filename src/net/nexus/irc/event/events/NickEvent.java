package net.nexus.irc.event.events;

import net.nexus.irc.Client;
import net.nexus.irc.event.IRCEvent;

/**
 * @author Kevin
 */
public class NickEvent extends IRCEvent {

	public NickEvent(Client client, String[] args) {
		super(client, args);
	}

	public String getNewNickname() {
		return parameter.replace(":", "");
	}

}
