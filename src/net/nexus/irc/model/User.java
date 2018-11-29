package net.nexus.irc.model;

/**
 * @author Kevin
 */
public interface User extends Conversable {

	default String getNickname() {
		return getName();
	}

	String getRealName();

	String getUsername();

	boolean isLocal();

	default boolean isOnChannel(Channel channel) {
		return channel != null && channel.contains(this);
	}

}
