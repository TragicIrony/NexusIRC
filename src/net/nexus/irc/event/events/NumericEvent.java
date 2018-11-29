package net.nexus.irc.event.events;

import net.nexus.irc.Client;
import net.nexus.irc.event.IRCEvent;

/**
 * @author Kevin
 */
public class NumericEvent extends IRCEvent {

	private final int numeric;

	public NumericEvent(Client client, String[] args) {
		super(client, args);

		int value = -1;
		try {
			value = Integer.parseInt(command);
		} catch (NumberFormatException ignored) {}

		this.numeric = value;
	}

	public int getNumeric() {
		return numeric;
	}

}
