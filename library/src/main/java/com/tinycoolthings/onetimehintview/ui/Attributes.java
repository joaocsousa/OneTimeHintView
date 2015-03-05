package com.tinycoolthings.onetimehintview.ui;

/**
 * Created by joaosousa on 28/02/15.
 * <p/>
 * Helper class that holds multiple attributes.
 */

public class Attributes {

	private Attribute<String> mPreferencesKey = new Attribute<>();
	private Attribute<Integer> mBackgroundColor = new Attribute<>();
	private Attribute<Integer> mCardBackgroundColor = new Attribute<>();
	private Attribute<Integer> mTextColor = new Attribute<>();
	private Attribute<CharSequence> mTitle = new Attribute<>();
	private Attribute<CharSequence> mDescription = new Attribute<>();
	private Attribute<CharSequence> mButtonLabel = new Attribute<>();
	private Attribute<Integer> mButtonLabelTextColor = new Attribute<>();
	private Attribute<Boolean> mDebug = new Attribute<>();
	private Attribute<Integer> mLayoutResource = new Attribute<>();
	private Attribute<Boolean> mDismissAnimation = new Attribute<>();

	public Attribute<String> getPreferencesKey() {
		return mPreferencesKey;
	}

	public void setKey(String preferencesKey) {
		mPreferencesKey.setValue(preferencesKey);
	}

	public Attribute<Integer> getBackgroundColor() {
		return mBackgroundColor;
	}

	public void setBackgroundColor(Integer backgroundColor) {
		mBackgroundColor.setValue(backgroundColor);
	}

	public Attribute<Integer> getCardBackgroundColor() {
		return mCardBackgroundColor;
	}

	public void setCardBackgroundColor(Integer cardBackgroundColor) {
		mCardBackgroundColor.setValue(cardBackgroundColor);
	}

	public Attribute<Integer> getTextColor() {
		return mTextColor;
	}

	public void setTextColor(Integer textColor) {
		mTextColor.setValue(textColor);
	}

	public Attribute<CharSequence> getTitle() {
		return mTitle;
	}

	public void setTitle(CharSequence title) {
		mTitle.setValue(title);
	}

	public Attribute<CharSequence> getDescription() {
		return mDescription;
	}

	public void setDescription(CharSequence description) {
		mDescription.setValue(description);
	}

	public Attribute<CharSequence> getButtonLabel() {
		return mButtonLabel;
	}

	public void setButtonLabel(CharSequence buttonLabel) {
		mButtonLabel.setValue(buttonLabel);
	}

	public Attribute<Integer> getButtonLabelTextColor() {
		return mButtonLabelTextColor;
	}

	public void setButtonLabelTextColor(Integer buttonLabelTextColor) {
		mButtonLabelTextColor.setValue(buttonLabelTextColor);
	}

	public Attribute<Boolean> isInDebug() {
		return mDebug;
	}

	public void setDebug(Boolean debug) {
		mDebug.setValue(debug);
	}

	public Attribute<Integer> getContentLayout() {
		return mLayoutResource;
	}

	public void setContentLayout(Integer contentLayout) {
		mLayoutResource.setValue(contentLayout);
	}

	public Attribute<Boolean> getDismissAnimation() {
		return mDismissAnimation;
	}

	public void setDismissAnimation(Boolean dismissAnimation) {
		mDismissAnimation.setValue(dismissAnimation);
	}

}