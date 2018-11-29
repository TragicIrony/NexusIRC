package net.nexus.irc.event.listeners;

import net.nexus.irc.event.IRCEventListener;
import net.nexus.irc.event.events.PrivMsgEvent;

/**
 * @author Kevin
 */
public interface PrivMsgListener extends IRCEventListener {

	void onPrivMsg(PrivMsgEvent event);

}
