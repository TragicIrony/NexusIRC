package net.nexus.irc.event;


import net.nexus.irc.Client;
import net.nexus.irc.model.Conversable;
import net.nexus.irc.util.Sanity;

import java.util.Optional;

/**
 * @author Kevin
 */
public class IRCEvent {

	private static final String[] EMPTY_ARGUMENTS = { "", "", "" };

	protected final Client client;

	protected final String command;
	protected final String source;
	protected final String parameter;

	protected final long timestamp;

	public IRCEvent(Client client, String[] args) {
		this.client = Sanity.nonNull(client, "IRCEvent(null, args)");

		String[] arguments = EMPTY_ARGUMENTS;
		for (int i = 0; i < 3; i++) {
			if (args.length < i)
				break;

			arguments[i] = args[i];
		}

		this.source = arguments[0];
		this.command = arguments[1];
		this.parameter = arguments[2];
		this.timestamp = System.currentTimeMillis();
	}

	public final Client getClient() {
		return client;
	}

	public final String getCommand() {
		return command;
	}

	public final String getInitiator() {
		//  :<name>!~<username>@<hostmask>
		String initiator = source;

		if (initiator.startsWith(":"))
			initiator = initiator.substring(1);
		if (initiator.contains(" "))
			initiator = initiator.substring(0, initiator.indexOf(' '));

		if (initiator.contains("!"))
			return initiator.substring(0, initiator.indexOf('!'));

		return initiator;
	}

	public final String getParameter() {
		return parameter;
	}

	public Optional<Conversable> getRecipient() {
		return Optional.of(client.getLocalUser());
	}

	public String getRecipientValue() {
		return client.getLocalUser().getName();
	}

	public final String getSource() {
		return source;
	}

	public final long getTimestamp() {
		return timestamp;
	}

	@Override
	public String toString() {
		return String.format("IRCEvent[source=%s, command=%s, parameter=%s, timestamp=%d]", source, command, parameter, timestamp);
	}

}
