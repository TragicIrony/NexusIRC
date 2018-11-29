package net.nexus.irc.model;

import java.util.Optional;
import java.util.Set;

/**
 * @author Kevin
 */
public interface Channel extends Conversable {

	boolean contains(User user);

	Optional<User> getUser(String name);

	Set<User> getUsers();

	default void part() {
		part("");
	}

	void part(String message);

}
