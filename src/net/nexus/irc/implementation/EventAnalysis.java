package net.nexus.irc.implementation;

import net.nexus.irc.event.IRCEvent;
import net.nexus.irc.event.events.*;
import net.nexus.irc.event.listeners.*;
import net.nexus.irc.model.Channel;
import net.nexus.irc.model.Conversable;
import net.nexus.irc.model.User;
import net.nexus.irc.util.Sanity;

import java.util.Arrays;

/**
 * @author Kevin
 */
final class EventAnalysis implements ErrorListener, JoinListener, KickListener, ModeListener, NickListener, NumericListener, PartListener, PingListener, QuitListener {

	private static final int RPL_AWAY = 301;

	private static final int RPL_CHANNEL_MODE_IS = 324;

	private static final int RPL_TOPIC = 332;
	private static final int RPL_TOPIC_WHO_TIME = 333;

	private static final int[] TOPIC_RELATED_NUMERICS = { RPL_TOPIC, RPL_TOPIC_WHO_TIME };

	@Override
	public void onError(ErrorEvent event) {
		event.getClient().disconnect();
	}

	@Override
	public void onEvent(IRCEvent event) {
		if (event instanceof NumericEvent)
			return;

		User user = event.getClient().getUser(event.getInitiator()).orElse(null);
		if (user == null)
			return; // API does not track users that are not on a mutual channel with the local user

		String source = event.getSource();
		if (source.startsWith(":"))
			source = source.substring(1);

		if (source.equalsIgnoreCase(user.getName()))
			return; // User mode event
		else if (source.indexOf('@') == -1)
			return; // Source does not hold hostmask
		else
			source = source.substring(source.indexOf('!') + 1); // Nickname is already known

		int index = source.indexOf('@');
		((UserImpl) user).setAttribute("USERNAME", source.substring(0, index));
		((UserImpl) user).setAttribute("HOSTNAME", source.substring(++index));
	}

	@Override
	public void onJoin(JoinEvent event) {
		ClientImpl client = (ClientImpl) event.getClient();

		((ChannelImpl) event.getRecipient().orElseGet(() -> {
			Channel channel = new ChannelImpl(client, event.getRecipientValue());
			client.add(channel);

			client.writeLine("WHO " + channel.getName());
			client.writeLine("MODE " + channel.getName());

			return channel;
		})).add(client.getUser(event.getInitiator()).orElse(new UserImpl(client, event.getInitiator())));
	}

	@Override
	public void onMode(ModeEvent event) {
		Conversable conversable = event.getRecipient().orElse(null);
		Sanity.nonNull(conversable, "EventAnalysis#onMode(ModeEvent)->Conversable");

		// Parse modes and either add or remove them to the conversable
	}

	// TODO Avoid code duplication
	@Override
	public void onKick(KickEvent event) {
		ClientImpl client = (ClientImpl) event.getClient();

		Conversable conversable = event.getRecipient().orElse(null);
		Sanity.nonNull(conversable, "EventAnalysis#onKick(KickEvent)->Channel");

		ChannelImpl channel = (ChannelImpl) conversable;
		User user = channel.getUser(event.getTarget()).orElse(null);
		Sanity.nonNull(user, "EventAnalysis#onKick(KickEvent)->User");

		if (user.isLocal())
			client.remove(channel);
		else
			channel.remove(user);
	}

	@Override
	public void onNick(NickEvent event) {
		User user = event.getClient().getUser(event.getInitiator()).orElse(null);
		Sanity.nonNull(user, "EventAnalysis#onNick(NickEvent)");

		((UserImpl) user).setName(event.getNewNickname());
	}

	@Override
	public void onNumeric(NumericEvent event) {
		if (Arrays.binarySearch(TOPIC_RELATED_NUMERICS, event.getNumeric()) < 0) {
			String parameter = event.getParameter();
			String[] split = parameter.split(" ");

			Channel channel = event.getClient().getChannel(split[1]).orElse(null);
			Sanity.nonNull(channel, "EventAnalysis#onNumeric(NumericEvent)->Topic->Channel");

			if (event.getNumeric() == RPL_TOPIC)
				((ChannelImpl) channel).setAttribute("TOPIC", parameter.substring(parameter.indexOf(':') + 1));
			else {
				((ChannelImpl) channel).setAttribute("TOPIC_SETTER", split[2]);
				((ChannelImpl) channel).setAttribute("TOPIC_TIMESTAMP", split[3]);
			}
		}

		if (event.getNumeric() == RPL_AWAY) {
			String[] split = event.getParameter().split(" ", 3);

			User user = event.getClient().getUser(split[1]).orElse(null);
			if (user == null)
				return; // API does not track users that are not on a mutual channel with the local user

			((UserImpl) user).setAttribute("AWAY", Boolean.toString(true));
			((UserImpl) user).setAttribute("AWAY_MESSAGE", split[2].substring(1));
		}

		if (event.getNumeric() == RPL_CHANNEL_MODE_IS) {
			String parameter = event.getParameter();

			String channelName = parameter.substring(parameter.indexOf(' ') + 1, parameter.indexOf(' ', parameter.indexOf(' ') + 1));
			String modes = parameter.substring(parameter.indexOf(' ', parameter.indexOf(channelName)) + 1);

			onMode(new ModeEvent(event.getClient(), new String[] { event.getSource(), "MODE", channelName + " " + modes }));
		}
	}

	@Override
	public void onPart(PartEvent event) {
		ClientImpl client = (ClientImpl) event.getClient();

		Conversable conversable = event.getRecipient().orElse(null);
		Sanity.nonNull(conversable, "EventAnalysis#onPart(PartEvent)->Channel");

		ChannelImpl channel = (ChannelImpl) conversable;
		User user = channel.getUser(event.getInitiator()).orElse(null);
		Sanity.nonNull(user, "EventAnalysis#onKick(KickEvent)->User");

		if (user.isLocal())
			client.remove(channel);
		else
			channel.remove(user);
	}

	@Override
	public void onPing(PingEvent event) {
		event.getClient().writeLine("PONG " + event.getParameter());
	}

	@Override
	public void onQuit(QuitEvent event) {
		User user = event.getClient().getUser(event.getInitiator()).orElse(null);
		Sanity.nonNull(user, "EventAnalysis#onQuit(QuitEvent)");

		event.getClient().getChannels().forEach(channel -> ((ChannelImpl) channel).remove(user));
	}

}
