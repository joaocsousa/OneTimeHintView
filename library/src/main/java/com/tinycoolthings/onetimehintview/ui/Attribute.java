package com.tinycoolthings.onetimehintview.ui;

import android.support.annotation.NonNull;

/**
 * Created by joaosousa on 28/02/15.
 * <p/>
 * Helper class that holds a value and easily allows to check if that value has changed or not.
 */
public class Attribute<T> {

	private T mValue;

	private Boolean mChanged = false;

	public T getValue() {
		return mValue;
	}

	public void setValue(T currentValue) {
		mChanged = currentValue != null && !currentValue.equals(mValue);
		mValue = currentValue;
	}

	public void used() {
		mChanged = false;
	}

	public boolean changed() {
		return mChanged;
	}

}