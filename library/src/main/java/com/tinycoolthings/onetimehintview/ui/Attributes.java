package com.tinycoolthings.onetimehintview.ui;

import java.util.ArrayList;

import static com.tinycoolthings.onetimehintview.ui.Attribute.AttributeTarget;

/**
 * Created by joaosousa on 28/02/15.
 * <p/>
 * Helper class that holds multiple attributes and applies them all to a target view.
 */

public class Attributes {

	private AttributeTarget mTarget;
	private Attribute<String> mPreferencesKey = new Attribute<>(new Attribute.AttributeApplier<String>() {
		@Override
		public void apply(AttributeTarget target, String value) {
			target.setPreferencesKey(value);
		}
	});
	private Attribute<Integer> mBackgroundColor = new Attribute<>(new Attribute.AttributeApplier<Integer>() {
		@Override
		public void apply(AttributeTarget target, Integer value) {
			target.setViewBackgroundColor(value);
		}
	});
	private Attribute<Integer> mCardBackgroundColor = new Attribute<>(new Attribute.AttributeApplier<Integer>() {
		@Override
		public void apply(AttributeTarget target, Integer value) {
			target.setCardBackgroundColor(value);
		}
	});
	private Attribute<Integer> mGlobalTextColor = new Attribute<>(new Attribute.AttributeApplier<Integer>() {
		@Override
		public void apply(AttributeTarget target, Integer value) {
			target.setGlobalTextColor(value);
		}
	});
	private Attribute<Integer> mTitleTextColor = new Attribute<>(new Attribute.AttributeApplier<Integer>() {
		@Override
		public void apply(AttributeTarget target, Integer value) {
			target.setTitleTextColor(value);
		}
	});
	private Attribute<Integer> mDescriptionTextColor = new Attribute<>(new Attribute.AttributeApplier<Integer>() {
		@Override
		public void apply(AttributeTarget target, Integer value) {
			target.setDescriptionTextColor(value);
		}
	});
	private Attribute<CharSequence> mTitle = new Attribute<>(new Attribute.AttributeApplier<CharSequence>() {
		@Override
		public void apply(AttributeTarget target, CharSequence value) {
			target.setTitle(value);
		}
	});
	private Attribute<CharSequence> mDescription = new Attribute<>(new Attribute.AttributeApplier<CharSequence>() {
		@Override
		public void apply(AttributeTarget target, CharSequence value) {
			target.setDescription(value);
		}
	});
	private Attribute<CharSequence> mButtonLabel = new Attribute<>(new Attribute.AttributeApplier<CharSequence>() {
		@Override
		public void apply(AttributeTarget target, CharSequence value) {
			target.setButtonLabel(value);
		}
	});
	private Attribute<Integer> mButtonLabelTextColor = new Attribute<>(new Attribute.AttributeApplier<Integer>() {
		@Override
		public void apply(AttributeTarget target, Integer value) {
			target.setButtonLabelTextColor(value);
		}
	});
	private Attribute<Boolean> mDebug = new Attribute<>(new Attribute.AttributeApplier<Boolean>() {
		@Override
		public void apply(AttributeTarget target, Boolean value) {
			target.setDebug(value);
		}
	});
	private Attribute<Integer> mLayoutResource = new Attribute<>(new Attribute.AttributeApplier<Integer>() {
		@Override
		public void apply(AttributeTarget target, Integer value) {
			target.setContentLayout(value);
		}
	});

	private ArrayList<Attribute<?>> mAttributes = new ArrayList<>();

	{
		mAttributes.add(mPreferencesKey);
		mAttributes.add(mBackgroundColor);
		mAttributes.add(mCardBackgroundColor);
		mAttributes.add(mGlobalTextColor);
		mAttributes.add(mTitleTextColor);
		mAttributes.add(mDescriptionTextColor);
		mAttributes.add(mTitle);
		mAttributes.add(mDescription);
		mAttributes.add(mButtonLabel);
		mAttributes.add(mButtonLabelTextColor);
		mAttributes.add(mDebug);
		mAttributes.add(mLayoutResource);
	}

	private Attributes(AttributeTarget target) {
		mTarget = target;
	}

	public static Attributes attributes(AttributeTarget target) {
		return new Attributes(target);
	}

	public Attribute<String> getPreferencesKey() {
		return mPreferencesKey;
	}

	public void setPreferencesKey(String preferencesKey) {
		mPreferencesKey.setValue(preferencesKey);
	}

	public Attribute<Integer> getBackgroundColor() {
		return mBackgroundColor;
	}

	public void setBackgroundColor(int backgroundColor) {
		mBackgroundColor.setValue(backgroundColor);
	}

	public Attribute<Integer> getCardBackgroundColor() {
		return mCardBackgroundColor;
	}

	public void setCardBackgroundColor(int cardBackgroundColor) {
		mCardBackgroundColor.setValue(cardBackgroundColor);
	}

	public Attribute<Integer> getGlobalTextColor() {
		return mGlobalTextColor;
	}

	public void setGlobalTextColor(int globalTextColor) {
		mGlobalTextColor.setValue(globalTextColor);
	}

	public Attribute<Integer> getTitleTextColor() {
		return mTitleTextColor;
	}

	public void setTitleTextColor(int titleTextColor) {
		mTitleTextColor.setValue(titleTextColor);
	}

	public Attribute<Integer> getDescriptionTextColor() {
		return mDescriptionTextColor;
	}

	public void setDescriptionTextColor(int descriptionTextColor) {
		mDescriptionTextColor.setValue(descriptionTextColor);
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

	public void setButtonLabelTextColor(int buttonLabelTextColor) {
		mButtonLabelTextColor.setValue(buttonLabelTextColor);
	}

	public Attribute<Boolean> isDebug() {
		return mDebug;
	}

	public void setDebug(boolean debug) {
		mDebug.setValue(debug);
	}

	public Attribute<Integer> getContentLayout() {
		return mLayoutResource;
	}

	public void setContentLayout(int layoutResource) {
		mLayoutResource.setValue(layoutResource);
	}

	public void applyAll() {
		for (Attribute<?> mAttribute : mAttributes) {
			mAttribute.apply(mTarget);
		}
	}
}