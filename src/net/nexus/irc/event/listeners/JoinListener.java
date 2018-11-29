package net.nexus.irc.event.listeners;

import net.nexus.irc.event.IRCEventListener;
import net.nexus.irc.event.events.JoinEvent;

/**
 * @author Kevin
 */
public interface JoinListener extends IRCEventListener {

	void onJoin(JoinEvent event);

}
