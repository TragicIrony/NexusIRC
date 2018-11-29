package net.nexus.irc.event.events;

import net.nexus.irc.Client;
import net.nexus.irc.event.IRCEvent;
import net.nexus.irc.model.Conversable;

import java.util.Optional;

/**
 * @author Kevin
 */
public class ModeEvent extends IRCEvent {

	public ModeEvent(Client client, String[] args) {
		super(client, args);
	}

	public char[] getFlags() {
		String flags = parameter.substring(parameter.indexOf(' ') + 1).replaceAll("[+-]", "");

		if (flags.indexOf(' ') > -1)
			return flags.substring(0, flags.indexOf(' ')).toCharArray();

		return flags.replace(":", "").toCharArray();
	}

	public String[] getParameters() {
		String parameters = parameter.substring(parameter.indexOf(new String(getFlags())));

		if (parameters.indexOf(' ') > -1)
			return parameters.substring(parameters.indexOf(' ') + 1).replaceFirst(":", "").split(" ");

		return new String[0];
	}

	@Override
	public Optional<Conversable> getRecipient() {
		return client.getConversable(getRecipientValue());
	}

	@Override
	public String getRecipientValue() {
		return parameter.substring(0, parameter.indexOf(' '));
	}

}
