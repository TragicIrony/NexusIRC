package net.nexus.irc.event.events;

import net.nexus.irc.Client;
import net.nexus.irc.event.IRCEvent;
import net.nexus.irc.model.Conversable;

import java.util.Optional;

/**
 * @author Kevin
 */
public class MessageEvent extends IRCEvent {

	private static final int START_OF_HEADING = 0x01;

	public MessageEvent(Client client, String[] args) {
		super(client, args);
	}

	public String getMessage() {
		return parameter.substring(parameter.indexOf(':') + 1);
	}

	public boolean isCtcp() {
		String message = getMessage();

		int length = message.length();
		return length > 1 && message.charAt(0) == START_OF_HEADING && message.charAt(length - 1) == START_OF_HEADING;
	}

	@Override
	public Optional<Conversable> getRecipient() {
		return client.getConversable(getRecipientValue());
	}

	@Override
	public String getRecipientValue() {
		return parameter.substring(0, parameter.indexOf(':') - 1);
	}

}
