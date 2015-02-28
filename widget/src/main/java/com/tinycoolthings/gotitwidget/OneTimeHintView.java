package com.tinycoolthings.gotitwidget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tinycoolthings.gotitwidget.util.SimpleAnimatorListener;
import com.tinycoolthings.gotitwidget.util.Size;

import java.util.ArrayList;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * Created by joaosousa on 25/02/15.
 */
public class OneTimeHintView extends LinearLayout {

	private ArrayList<OnDismissListener> mOnDismissListeners = new ArrayList<>();
	private boolean mShow = true;
	private Size mSize;
	private AttributeManager mAttributeManager = new AttributeManager();

	public OneTimeHintView(Context context) {
		this(context, null);
	}

	public OneTimeHintView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public OneTimeHintView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, R.style.OneTimeHintView);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public OneTimeHintView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		processAttributes(attrs, defStyleAttr, defStyleRes);
		if (wasDismissedBefore()) {
			hide();
		} else {
			LayoutInflater.from(getContext()).inflate(R.layout.view_one_time_hint_view_card, this, true);
			applyAttributes();
			setup();
		}
	}

	private void setup() {
		Button dismissButton = (Button) findViewById(R.id.one_time_hint_view_cardview_button);
		if (dismissButton != null) {
			dismissButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					for (OnDismissListener onDismissListener : mOnDismissListeners) {
						onDismissListener.onDismiss();
					}
				}
			});
		}
	}

	private void applyAttributes() {
		mAttributeManager.applyTo(this);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (mSize == null && getMeasuredWidth() > 0 && getMeasuredHeight() > 0) {
			mSize = new Size(getMeasuredWidth(), getMeasuredHeight());
			attachDismissListener();
		}
	}

	private void markAsDismissed() {
		getDefaultSharedPreferences(getContext()).edit().putBoolean(mAttributeManager.getPreferencesKey(), true).commit();
	}

	private boolean wasDismissedBefore() {
		return !mAttributeManager.isInDebug() &&
			getDefaultSharedPreferences(getContext()).getBoolean(mAttributeManager.getPreferencesKey(), false);
	}

	private void attachDismissListener() {
		if (mSize != null) {
			addOnDismissListener(new OnDismissListener() {
				@Override
				public void onDismiss() {
					ValueAnimator animator = ValueAnimator.ofFloat(mSize.getHeight(), 0);
					animator.setDuration(200);
					animator.setInterpolator(new AccelerateInterpolator());
					animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
						public void onAnimationUpdate(ValueAnimator animation) {
							getLayoutParams().height = ((Float) animation.getAnimatedValue()).intValue();
							requestLayout();
						}
					});
					animator.addListener(new SimpleAnimatorListener() {
						@Override
						public void onAnimationEnd(Animator animation) {
							super.onAnimationEnd(animation);
							markAsDismissed();
							hide();
						}
					});
					animator.start();
				}
			});
		}
	}

	private void hide() {
		mShow = false;
		updateVisibility();
	}

	private void updateVisibility() {
		setVisibility(mShow ? VISIBLE : GONE);
		ViewGroup parent = (ViewGroup) getParent();
		if (parent != null) {
			parent.removeView(this);
		}
	}

	private void processAttributes(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.OneTimeHintView, defStyleAttr, defStyleRes);
		try {
			// preferences key
			mAttributeManager.setPreferencesKey(typedArray.getString(R.styleable.OneTimeHintView_one_time_hint_view_key));
			if (mAttributeManager.getPreferencesKey() == null || mAttributeManager.getPreferencesKey().isEmpty()) {
				throw new IllegalStateException("You definitely need to provide a preferences key to the " + getClass().getSimpleName() +
					". Use the attribute \'one_time_hint_view_key\' to provide a unique key.");
			}
			// background color
			int backgroundColor = typedArray.getColor(
				R.styleable.OneTimeHintView_one_time_hint_view_background_color,
				R.color.one_time_hint_view_default_background_color);
			mAttributeManager.setBackgroundColor(getResources().getColor(backgroundColor));
			// card background color
			int cardBackgroundColor = typedArray.getColor(
				R.styleable.OneTimeHintView_one_time_hint_view_card_color,
				R.color.one_time_hint_view_default_card_color);
			mAttributeManager.setCardBackgroundColor(getResources().getColor(cardBackgroundColor));
			// text color
			int textColor = typedArray.getColor(
				R.styleable.OneTimeHintView_one_time_hint_view_card_color,
				R.color.one_time_hint_view_default_text_color);
			mAttributeManager.setGlobalTextColor(getResources().getColor(textColor));
			// title
			mAttributeManager.setTitleTextColor(mAttributeManager.getGlobalTextColor());
			mAttributeManager.setTitle(typedArray.getString(R.styleable.OneTimeHintView_one_time_hint_view_title));
			// description
			mAttributeManager.setDescriptionTextColor(mAttributeManager.getGlobalTextColor());
			mAttributeManager.setDescription(typedArray.getString(R.styleable.OneTimeHintView_one_time_hint_view_description));
			// button label
			mAttributeManager.setButtonLabelTextColor(mAttributeManager.getGlobalTextColor());
			CharSequence defaultButtonLabel = getResources().getString(R.string.one_time_hint_view_button_default_label);
			String buttonLabel = typedArray.getString(R.styleable.OneTimeHintView_one_time_hint_view_button_label);
			mAttributeManager.setButtonLabel(buttonLabel != null && !buttonLabel.isEmpty() ? buttonLabel : defaultButtonLabel);
			// debug
			mAttributeManager.setDebug(typedArray.getBoolean(R.styleable.OneTimeHintView_one_time_hint_debug, false));
		} finally {
			typedArray.recycle();
		}
	}

	public void addOnDismissListener(OnDismissListener onDismissListener) {
		if (onDismissListener == null) {
			throw new IllegalArgumentException("onDismissListener can't be null!");
		}
		mOnDismissListeners.add(onDismissListener);
	}

	private void setButtonLabelTextColor(int buttonLabelTextColor) {
		((Button) findViewById(R.id.one_time_hint_view_cardview_button)).setTextColor(buttonLabelTextColor);
	}

	private void setButtonLabel(CharSequence buttonLabel) {
		((Button) findViewById(R.id.one_time_hint_view_cardview_button)).setText(buttonLabel);
	}

	private void setDescription(CharSequence description) {
		((TextView) findViewById(R.id.one_time_hint_view_cardview_description)).setText(description);
	}

	private void setTitle(CharSequence title) {
		((TextView) findViewById(R.id.one_time_hint_view_cardview_title)).setText(title);
	}

	private void setDescriptionTextColor(int titleTextColor) {
		((TextView) findViewById(R.id.one_time_hint_view_cardview_description)).setTextColor(titleTextColor);
	}

	private void setTitleTextColor(int titleTextColor) {
		((TextView) findViewById(R.id.one_time_hint_view_cardview_title)).setTextColor(titleTextColor);
	}

	private void setCardBackgroundColor(int cardBackgroundColor) {
		((CardView) findViewById(R.id.one_time_hint_view_cardview)).setCardBackgroundColor(cardBackgroundColor);
	}

	public interface OnDismissListener {
		void onDismiss();
	}

	interface AttributeApplier {
		void applyTo(OneTimeHintView target);
	}

	private static class AttributeManager implements AttributeApplier {

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


}
