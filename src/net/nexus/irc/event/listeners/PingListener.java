package net.nexus.irc.event.listeners;

import net.nexus.irc.event.IRCEventListener;
import net.nexus.irc.event.events.PingEvent;

/**
 * @author Kevin
 */
public interface PingListener extends IRCEventListener {

	void onPing(PingEvent event);

}
