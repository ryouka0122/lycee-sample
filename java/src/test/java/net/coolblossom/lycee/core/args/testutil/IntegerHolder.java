package net.coolblossom.lycee.core.args.testutil;

import javax.annotation.Nonnull;

public class IntegerHolder {
	@Nonnull Integer value;
	public IntegerHolder(@Nonnull final Integer arg) {
		value = arg;
	}

	@Override
	public String toString() {
		return value.toString();
	}

	@Override
	public boolean equals(final Object obj) {
		if(this==obj) {
			return true;
		}
		if( !(obj instanceof IntegerHolder) ) {
			return false;
		}
		final IntegerHolder holder = (IntegerHolder)obj;
		return value.equals(holder.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}
}