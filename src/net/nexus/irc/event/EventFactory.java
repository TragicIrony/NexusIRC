package net.nexus.irc.event;

import net.nexus.irc.Client;
import net.nexus.irc.event.events.*;
import net.nexus.irc.util.Sanity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Kevin
 */
public final class EventFactory {

	private static final Map<String, Class<? extends IRCEvent>> EVENT_CLASSES;
	private static final Pattern NUMERIC_PATTERN = Pattern.compile("^[0-9]{3}$");

	static {
		Map<String, Class<? extends IRCEvent>> eventClasses = new HashMap<>();

		eventClasses.put("ERROR", ErrorEvent.class);
		eventClasses.put("INVITE", InviteEvent.class);
		eventClasses.put("JOIN", JoinEvent.class);
		eventClasses.put("KICK", KickEvent.class);
		eventClasses.put("MODE", ModeEvent.class);
		eventClasses.put("NICK", NickEvent.class);
		eventClasses.put("NOTICE", NoticeEvent.class);
		eventClasses.put("PART", PartEvent.class);
		eventClasses.put("PING", PingEvent.class);
		eventClasses.put("PRIVMSG", PrivMsgEvent.class);
		eventClasses.put("QUIT", QuitEvent.class);
		eventClasses.put("TOPIC", TopicEvent.class);

		EVENT_CLASSES = Collections.unmodifiableMap(eventClasses);
	}

	private EventFactory() throws InstantiationException {
		throw new InstantiationException("EventFactory must not be instantiated");
	}

	public static IRCEvent createEventFromLine(Client client, String line) {
		Sanity.nonNull(client, "EventFactory#createEventFromLine(null, String)");
		Sanity.nonEmpty(line, "EventFactory#createEventFromLine(Client, null)");

		String[] split = disassemble(line);

		Class<? extends IRCEvent> eventClass;
		if (NUMERIC_PATTERN.matcher(split[1]).matches())
			eventClass = NumericEvent.class;
		else
			eventClass = EVENT_CLASSES.getOrDefault(split[1], IRCEvent.class);

		try {
			Constructor<? extends IRCEvent> constructor = eventClass.getConstructor(Client.class, String[].class);
			if (constructor != null)
				return constructor.newInstance(client, split);
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String[] disassemble(String line) {
		int limit = line.startsWith(":") ? 3 : 2;

		String[] event = new String[]{"", "", ""};
		String[] split = line.split(" ", limit);

		int index = 0;
		if (limit == 3)
			event[0] = split[index++];

		event[1] = split[index++];
		event[2] = split[index];

		return event;
	}

}
