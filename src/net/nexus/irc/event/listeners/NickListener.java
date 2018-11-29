package net.nexus.irc.event.listeners;

import net.nexus.irc.event.IRCEventListener;
import net.nexus.irc.event.events.NickEvent;

/**
 * @author Kevin
 */
public interface NickListener extends IRCEventListener {

	void onNick(NickEvent event);

}
