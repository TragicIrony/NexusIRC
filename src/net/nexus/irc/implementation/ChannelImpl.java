package net.nexus.irc.implementation;

import net.nexus.irc.Client;
import net.nexus.irc.model.Channel;
import net.nexus.irc.model.Mode;
import net.nexus.irc.model.User;
import net.nexus.irc.util.Sanity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Kevin
 */
final class ChannelImpl extends ConversableImpl implements Channel {

	private final String name;
	private final ConcurrentHashMap.KeySetView<User, Boolean> users;

	ChannelImpl(Client client, String name) {
		super(client);

		// TODO Validate channel name with ISUPPORT reply
		this.name = Sanity.nonEmpty(name, "ChannelImpl#(Client, \"\"");
		this.users = ConcurrentHashMap.newKeySet();
	}

	void add(User user) {
		users.add(Sanity.nonNull(user, "ChannelImpl#add(null)"));
	}

	void remove(User user) {
		Sanity.nonNull(user, "ChannelImpl#remove(null)");

		if (!users.remove(user))
			return; // User is unknown to the channel

		List<Mode> cleanup = new ArrayList<>();

		getModes()
				.stream()
				.filter(mode -> mode.getParameter().equalsIgnoreCase(user.getName()))
				.filter(mode -> "qaohv".indexOf(mode.getFlag()) != -1)
				.forEach(cleanup::add);

		cleanup.forEach(this::remove);
	}

	@Override
	public boolean contains(User user) {
		return users.contains(user);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Channel) {
			ChannelImpl channel = (ChannelImpl) object;
			return channel.name.equalsIgnoreCase(name) && channel.users.equals(users) && channel.client.equals(client);
		}
		return false;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Optional<User> getUser(String name) {
		return users.stream().filter(user -> user.getName().equalsIgnoreCase(name)).findFirst();
	}

	@Override
	public Set<User> getUsers() {
		return Collections.unmodifiableSet(users);
	}

	@Override
	public void part(String message) {
		if (message == null || message.isEmpty())
			message = name;

		client.writeLine("PART " + name + " :" + message);
	}

}
