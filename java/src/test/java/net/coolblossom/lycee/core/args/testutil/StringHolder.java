package net.coolblossom.lycee.core.args.testutil;

import javax.annotation.Nonnull;

public class StringHolder {
	@Nonnull String value;
	public StringHolder(@Nonnull final String arg) {
		value = arg;
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public boolean equals(final Object obj) {
		if(this==obj) {
			return true;
		}
		if( !(obj instanceof StringHolder) ) {
			return false;
		}
		final StringHolder holder = (StringHolder)obj;
		return value.equals(holder.value);
	}
}