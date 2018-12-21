package net.nexus.irc;

import net.nexus.irc.model.Channel;
import net.nexus.irc.model.Conversable;
import net.nexus.irc.model.User;

import java.util.Optional;
import java.util.Set;

/**
 * @author Kevin
 */
public interface Client {

	interface Builder {

		Builder nickname(String nickname);

		Builder realName(String realName);

		Builder username(String username);

		default Builder server(String hostname) {
			return server(hostname, 6667, "");
		}

		default Builder server(String hostname, int port) {
			return server(hostname, port, "");
		}

		Builder server(String hostname, int port, String password);

	}

	void connect();

	void disconnect();

	default void disconnect(String message) {
		if (message != null && !message.isEmpty())
			writeLine("QUIT :" + message);

		disconnect();
	}

	Optional<Channel> getChannel(String name);

	Set<Channel> getChannels();

	Optional<Conversable> getConversable(String name);

	Set<Conversable> getConversables();

	User getLocalUser();

	Optional<User> getUser(String name);

	Set<User> getUsers();

	boolean isConnected();

	boolean isLocal(User user);

	void writeLine(String line);

}
