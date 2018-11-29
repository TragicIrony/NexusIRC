package net.nexus.irc.event.listeners;

import net.nexus.irc.event.IRCEventListener;
import net.nexus.irc.event.events.InviteEvent;

/**
 * @author Kevin
 */
public interface InviteListener extends IRCEventListener {

	void onInvite(InviteEvent event);

}
