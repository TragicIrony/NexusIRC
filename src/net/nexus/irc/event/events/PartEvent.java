package net.nexus.irc.event.events;

import net.nexus.irc.Client;
import net.nexus.irc.event.IRCEvent;
import net.nexus.irc.model.Conversable;

import java.util.Optional;

/**
 * @author Kevin
 */
public class PartEvent extends IRCEvent {

	private static final String EMPTY_MESSAGE = "";

	public PartEvent(Client client, String[] args) {
		super(client, args);
	}

	public String getMessage() {
		return parameter.indexOf(':') > -1 ? parameter.substring(parameter.indexOf(':') + 1) : EMPTY_MESSAGE;
	}

	@Override
	public Optional<Conversable> getRecipient() {
		return client.getChannel(getRecipientValue()).map(channel -> channel);
	}

	@Override
	public String getRecipientValue() {
		return getMessage().isEmpty() ? parameter.substring(0, parameter.indexOf(':') - 1) : parameter;
	}

}
