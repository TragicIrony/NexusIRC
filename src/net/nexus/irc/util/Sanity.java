package net.nexus.irc.util;

/**
 * @author Kevin
 */
public final class Sanity {

	private Sanity() {}

	public static String nonEmpty(String string, String error) {
		if (nonNull(string).isEmpty())
			throw new EmptyStringException(error);

		return string;
	}

	public static <O> O nonNull(O o) {
		return nonNull(o, "object cannot be null");
	}

	public static <O> O nonNull(O o, String error) {
		if (o == null)
			throw new NullPointerException(error);

		return o;
	}

	public static class EmptyStringException extends RuntimeException {

		public EmptyStringException(String error) {
			super(error);
		}

	}

}
