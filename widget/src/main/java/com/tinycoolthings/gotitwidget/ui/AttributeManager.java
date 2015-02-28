package com.tinycoolthings.gotitwidget.ui;

/**
 * Created by joaosousa on 28/02/15.
 */

import com.tinycoolthings.gotitwidget.OneTimeHintView;

public class AttributeManager implements AttributeApplier {

	private Attributes mAttributes = new Attributes();

	public String getPreferencesKey() {
		return mAttributes.mPreferencesKey;
	}

	public void setPreferencesKey(String preferencesKey) {
		mAttributes.mPreferencesKey = preferencesKey;
	}

	public Boolean isInDebug() {
		return mAttributes.mDebug;
	}

	public void setBackgroundColor(int backgroundColor) {
		mAttributes.mBackgroundColor = backgroundColor;
	}

	public void setCardBackgroundColor(int cardBackgroundColor) {
		mAttributes.mCardBackgroundColor = cardBackgroundColor;
	}

	public int getGlobalTextColor() {
		return mAttributes.mGlobalTextColor;
	}

	public void setGlobalTextColor(int globalTextColor) {
		mAttributes.mGlobalTextColor = globalTextColor;
	}

	public void setTitleTextColor(int titleTextColor) {
		mAttributes.mTitleTextColor = titleTextColor;
	}

	public void setDescriptionTextColor(int descriptionTextColor) {
		mAttributes.mDescriptionTextColor = descriptionTextColor;
	}

	public void setTitle(CharSequence title) {
		mAttributes.mTitle = title;
	}

	public void setDescription(CharSequence description) {
		mAttributes.mDescription = description;
	}

	public void setButtonLabel(CharSequence buttonLabel) {
		mAttributes.mButtonLabel = buttonLabel;
	}

	public void setButtonLabelTextColor(int buttonLabelTextColor) {
		mAttributes.mButtonLabelTextColor = buttonLabelTextColor;
	}

	@Override
	public void applyTo(OneTimeHintView target) {
		target.setBackgroundColor(mAttributes.mBackgroundColor);
		target.setCardBackgroundColor(mAttributes.mCardBackgroundColor);
		target.setTitleTextColor(mAttributes.mTitleTextColor);
		target.setDescriptionTextColor(mAttributes.mDescriptionTextColor);
		target.setTitle(mAttributes.mTitle);
		target.setDescription(mAttributes.mDescription);
		target.setButtonLabel(mAttributes.mButtonLabel);
		target.setButtonLabelTextColor(mAttributes.mButtonLabelTextColor);
	}

	public void setDebug(boolean debug) {
		mAttributes.mDebug = debug;
	}

	class Attributes {
		private String mPreferencesKey;
		private int mBackgroundColor;
		private int mCardBackgroundColor;
		private int mGlobalTextColor;
		private int mTitleTextColor;
		private int mDescriptionTextColor;
		private CharSequence mTitle;
		private CharSequence mDescription;
		private CharSequence mButtonLabel;
		private int mButtonLabelTextColor;
		private boolean mDebug;
	}
}