package net.nexus.irc.event.events;

import net.nexus.irc.Client;
import net.nexus.irc.event.IRCEvent;
import net.nexus.irc.model.Conversable;

import java.util.Optional;

/**
 * @author Kevin
 */
public class JoinEvent extends IRCEvent {

	public JoinEvent(Client client, String[] args) {
		super(client, args);
	}

	@Override
	public Optional<Conversable> getRecipient() {
		return client.getChannel(getRecipientValue()).map(channel -> channel);
	}

	@Override
	public String getRecipientValue() {
		return parameter.replace(":", "");
	}

}
