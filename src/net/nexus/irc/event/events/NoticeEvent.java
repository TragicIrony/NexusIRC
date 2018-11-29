package net.nexus.irc.event.events;

import net.nexus.irc.Client;

/**
 * @author Kevin
 */
public class NoticeEvent extends MessageEvent {

	public NoticeEvent(Client client, String[] args) {
		super(client, args);
	}

}
