package net.nexus.irc.event.events;

import net.nexus.irc.Client;

/**
 * @author Kevin
 */
public class PrivMsgEvent extends MessageEvent {

	public PrivMsgEvent(Client client, String[] args) {
		super(client, args);
	}

}
