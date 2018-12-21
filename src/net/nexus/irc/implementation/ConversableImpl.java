package net.nexus.irc.implementation;

import net.nexus.irc.Client;
import net.nexus.irc.model.Conversable;
import net.nexus.irc.model.Mode;
import net.nexus.irc.util.Sanity;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Kevin
 */
abstract class ConversableImpl implements Conversable {

	protected final Client client;

	private final Map<String, String> attributes;
	private final ConcurrentHashMap.KeySetView<Mode, Boolean> modes;

	ConversableImpl(Client client) {
		this.client = Sanity.nonNull(client, "ConversableImpl#(null)");
		this.attributes = new ConcurrentHashMap<>();
		this.modes = ConcurrentHashMap.newKeySet();
	}

	void add(Mode mode) {
		modes.add(Sanity.nonNull(mode, "ConversableImpl#add(null)"));
	}

	void remove(Mode mode) {
		modes.remove(Sanity.nonNull(mode, "ConversableImpl#remove(null)"));
	}

	void setAttribute(String key, String value) {
		Sanity.nonEmpty(key, "ConversableImpl#setAttribute(null, String)");

		if (value == null || value.isEmpty())
			attributes.remove(key);
		else
			attributes.put(key, value);
	}

	@Override
	public final String getAttribute(String key) {
		return attributes.getOrDefault(key, "");
	}

	@Override
	public final Set<Mode> getModes() {
		return Collections.unmodifiableSet(modes);
	}

	@Override
	public final void message(String message) {
		if (message == null || message.isEmpty())
			return;

		client.writeLine("PRIVMSG " + getName() + " :" + message);
	}

	@Override
	public final void notice(String notice) {
		if (notice == null || notice.isEmpty())
			return;

		client.writeLine("NOTICE " + getName() + " :" + notice);
	}

}
