package net.nexus.irc.event.events;

import net.nexus.irc.Client;
import net.nexus.irc.event.IRCEvent;
import net.nexus.irc.model.Conversable;

import java.util.Optional;

/**
 * @author Kevin
 */
public class KickEvent extends IRCEvent {

	public KickEvent(Client client, String[] args) {
		super(client, args);
	}

	public String getMessage() {
		return parameter.substring(parameter.indexOf(':') + 1);
	}

	@Override
	public Optional<Conversable> getRecipient() {
		return client.getUser(getRecipientValue()).map(user -> user);
	}

	@Override
	public String getRecipientValue() {
		return parameter.substring(0, parameter.indexOf(' '));
	}

	public String getTarget() {
		return parameter.substring(parameter.indexOf(" ") + 1, parameter.indexOf(':') - 1);
	}

}
