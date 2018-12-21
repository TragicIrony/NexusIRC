package net.nexus.irc.implementation;

import net.nexus.irc.Client;
import net.nexus.irc.model.Channel;
import net.nexus.irc.model.Conversable;
import net.nexus.irc.model.User;
import net.nexus.irc.util.Sanity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Kevin
 */
final class ClientImpl implements Client {

	private final User localUser;

	private final ConcurrentHashMap.KeySetView<Channel, Boolean> channels;

	ClientImpl() {
		this.localUser = null;
		this.channels = ConcurrentHashMap.newKeySet();
	}

	void add(Channel channel) {
		channels.add(Sanity.nonNull(channel, "ClientImpl#add(null)"));
	}

	void remove(Channel channel) {
		channels.remove(Sanity.nonNull(channel, "ClientImpl#remove(null)"));
	}

	@Override
	public void connect() {
		if (isConnected())
			return;
	}

	@Override
	public void disconnect() {

	}

	@Override
	public Optional<Channel> getChannel(String name) {
		return channels.stream().filter(channel -> channel.getName().equalsIgnoreCase(name)).findFirst();
	}

	@Override
	public Set<Channel> getChannels() {
		return Collections.unmodifiableSet(channels);
	}

	@Override
	public Optional<Conversable> getConversable(String name) {
		return getConversables().stream().filter(conversable -> conversable.getName().equalsIgnoreCase(name)).findFirst();
	}

	@Override
	public Set<Conversable> getConversables() {
		Set<Conversable> conversables = new HashSet<>();

		channels.forEach(channel -> {
			conversables.add(channel);
			conversables.addAll(channel.getUsers());
		});

		conversables.add(localUser);
		return conversables;
	}

	@Override
	public User getLocalUser() {
		return localUser;
	}

	@Override
	public Optional<User> getUser(String name) {
		return getUsers().stream().filter(user -> user.getName().equalsIgnoreCase(name)).findFirst();
	}

	@Override
	public Set<User> getUsers() {
		Set<User> users = new HashSet<>();
		channels.forEach(channel -> users.addAll(channel.getUsers()));
		users.add(localUser);
		return users;
	}

	@Override
	public boolean isConnected() {
		return false;
	}

	@Override
	public boolean isLocal(User user) {
		return localUser.equals(user);
	}

	@Override
	public void writeLine(String line) {

	}

}
