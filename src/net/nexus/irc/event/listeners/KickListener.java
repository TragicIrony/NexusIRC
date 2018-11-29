package net.nexus.irc.event.listeners;

import net.nexus.irc.event.IRCEventListener;
import net.nexus.irc.event.events.KickEvent;

/**
 * @author Kevin
 */
public interface KickListener extends IRCEventListener {

	void onKick(KickEvent event);

}
