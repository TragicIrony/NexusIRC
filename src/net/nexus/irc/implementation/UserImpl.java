package net.nexus.irc.implementation;

import net.nexus.irc.Client;
import net.nexus.irc.model.User;
import net.nexus.irc.util.Sanity;

import java.util.Objects;

/**
 * @author Kevin
 */
final class UserImpl extends ConversableImpl implements User {

	private volatile String name;

	UserImpl(Client client, String name) {
		super(client);

		setName(name);
	}

	void setName(String name) {
		Sanity.nonEmpty(name, "UserImpl#setName(null)");

		// TODO Validate name with ISUPPORT reply
		this.name = name;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof User) {
			UserImpl user = (UserImpl) object;
			return user.name.equalsIgnoreCase(name) && user.client.equals(client);
		}
		return false;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getRealName() {
		return "";
	}

	@Override
	public String getUsername() {
		return "";
	}

	@Override
	public int hashCode() {
		return Objects.hash(name.toLowerCase(), client);
	}

	@Override
	public boolean isLocal() {
		return client.isLocal(this);
	}

}
