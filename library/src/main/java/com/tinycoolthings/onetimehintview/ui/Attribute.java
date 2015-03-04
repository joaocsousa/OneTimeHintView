package com.tinycoolthings.onetimehintview.ui;

import android.view.View;

/**
 * Created by joaosousa on 28/02/15.
 * <p/>
 * Helper class that holds a value and easily allows to check if that value has changed or not.
 */
public class Attribute<T> {

	private T mValue;

	private Boolean mChanged = false;

	private final AttributeApplier<T> mApplier;

	public Attribute(AttributeApplier<T> applier) {
		mApplier = applier;
	}

	public T getValue() {
		return mValue;
	}

	public void setValue(T currentValue) {
		mChanged = mValue.equals(currentValue);
		mValue = currentValue;
	}

	public void used() {
		mChanged = false;
	}

	public boolean changed() {
		return mChanged;
	}

	public void apply(AttributeTarget target) {
		if (changed()) {
			mApplier.apply(target, getValue());
			used();
		}
	}

	public interface AttributeApplier<T> {
		void apply(AttributeTarget target, T value);
	}

	public interface AttributeTarget {
		public AttributeTarget setPreferencesKey(String preferencesKey);
		public AttributeTarget setViewBackgroundColor(int backgroundColor);
		public AttributeTarget setCardBackgroundColor(int cardBackgroundColor);
		public AttributeTarget setGlobalTextColor(int globalTextColor);
		public AttributeTarget setTitleTextColor(int titleTextColor);
		public AttributeTarget setDescriptionTextColor(int descriptionTextColor);
		public AttributeTarget setTitle(CharSequence title);
		public AttributeTarget setDescription(CharSequence description);
		public AttributeTarget setButtonLabel(CharSequence buttonLabel);
		public AttributeTarget setButtonLabelTextColor(int buttonLabelTextColor);
		public AttributeTarget setDebug(boolean debug);
		public AttributeTarget setContentLayout(int layoutResource);
	}

}