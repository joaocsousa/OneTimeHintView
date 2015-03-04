package com.tinycoolthings.onetimehintview;

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

import com.tinycoolthings.onetimehintview.ui.Attribute;
import com.tinycoolthings.onetimehintview.ui.Attributes;
import com.tinycoolthings.onetimehintview.ui.Size;
import com.tinycoolthings.onetimehintview.util.SimpleAnimatorListener;

import java.util.ArrayList;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.tinycoolthings.onetimehintview.ui.Attributes.attributes;
import static com.tinycoolthings.onetimehintview.util.Utils.isInDebugMode;

/**
 * Created by joaosousa on 25/02/15.
 */
public class OneTimeHintView extends LinearLayout implements Attribute.AttributeTarget {

	private static final int DEFAULT_ANIMATION_DURATION = 250;
	private static final int DEFAULT_CONTENT_LAYOUT = R.layout.view_one_time_hint_view_default_content;

	private ArrayList<OnDismissListener> mOnDismissListeners = new ArrayList<>();
	private boolean mShow = true;
	private Size mSize;
	private Attributes mAttributes = attributes(this);
	private boolean mDefaultContentLayout = false;

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
		getDefaultSharedPreferences(getContext()).edit().putBoolean(mAttributes.getPreferencesKey().getValue(), true).commit();
	}

	private boolean wasDismissedBefore() {
		return !mAttributes.isDebug().getValue() &&
			getDefaultSharedPreferences(getContext()).getBoolean(mAttributes.getPreferencesKey().getValue(), false);
	}

	private void attachDismissListener() {
		if (mSize != null) {
			addOnDismissListener(new OnDismissListener() {
				@Override
				public void onDismiss() {
					ValueAnimator animator = ValueAnimator.ofFloat(mSize.getHeight(), 0);
					animator.setDuration(DEFAULT_ANIMATION_DURATION);
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
			String key = typedArray.getString(R.styleable.OneTimeHintView_oneTimeHintView_key);
			if (key == null || key.isEmpty()) {
				throw new IllegalStateException("You definitely need to provide a preferences key to the " + getClass().getSimpleName() +
					". Use the attribute \'one_time_hint_view_key\' to provide a unique key.");
			}
			mAttributes.setPreferencesKey(key);
			// content layout
			int layoutResource = typedArray.getResourceId(
				R.styleable.OneTimeHintView_oneTimeHintView_contentLayout,
				DEFAULT_CONTENT_LAYOUT);
			mAttributes.setContentLayout(layoutResource);
			// background color
			int backgroundColor = typedArray.getColor(
				R.styleable.OneTimeHintView_oneTimeHintView_backgroundColor,
				R.color.one_time_hint_view_default_background_color);
			mAttributes.setBackgroundColor(getResources().getColor(backgroundColor));
			// card background color
			int cardBackgroundColor = typedArray.getColor(
				R.styleable.OneTimeHintView_oneTimeHintView_cardColor,
				R.color.one_time_hint_view_default_card_color);
			mAttributes.setCardBackgroundColor(getResources().getColor(cardBackgroundColor));
			// text color
			int textColor = typedArray.getColor(
				R.styleable.OneTimeHintView_oneTimeHintView_cardColor,
				R.color.one_time_hint_view_default_text_color);
			mAttributes.setGlobalTextColor(getResources().getColor(textColor));
			// title
			mAttributes.setTitleTextColor(textColor);
			mAttributes.setTitle(typedArray.getString(R.styleable.OneTimeHintView_oneTimeHintView_title));
			// description
			mAttributes.setDescriptionTextColor(textColor);
			mAttributes.setDescription(typedArray.getString(R.styleable.OneTimeHintView_oneTimeHintView_description));
			// button label
			mAttributes.setButtonLabelTextColor(textColor);
			CharSequence defaultButtonLabel = getResources().getString(R.string.one_time_hint_view_button_default_label);
			String buttonLabel = typedArray.getString(R.styleable.OneTimeHintView_oneTimeHintView_buttonLabel);
			mAttributes.setButtonLabel(buttonLabel != null && !buttonLabel.isEmpty() ? buttonLabel : defaultButtonLabel);
			// debug
			boolean isInDebugMode = isInDebugMode(getContext()) &&
				typedArray.getBoolean(R.styleable.OneTimeHintView_oneTimeHintView_debug, false);
			mAttributes.setDebug(isInDebugMode);
		} finally {
			typedArray.recycle();
		}
	}

	public OneTimeHintView addOnDismissListener(OnDismissListener onDismissListener) {
		if (onDismissListener == null) {
			throw new IllegalArgumentException("onDismissListener can't be null!");
		}
		mOnDismissListeners.add(onDismissListener);
		return this;
	}

	public OneTimeHintView setButtonLabelTextColor(int buttonLabelTextColor) {
		mAttributes.setButtonLabelTextColor(buttonLabelTextColor);
		((Button) findViewById(R.id.one_time_hint_view_cardview_button)).setTextColor(buttonLabelTextColor);
		return this;
	}

	@Override
	public OneTimeHintView setDebug(boolean debug) {
		return this;
	}

	public OneTimeHintView setButtonLabel(CharSequence buttonLabel) {
		mAttributes.setButtonLabel(buttonLabel);
		((Button) findViewById(R.id.one_time_hint_view_cardview_button)).setText(buttonLabel);
		return this;
	}

	public OneTimeHintView setButtonLabel(int buttonLabel) {
		return setButtonLabel(getResources().getString(buttonLabel));
	}

	public OneTimeHintView setDescription(CharSequence description) {
		mAttributes.setDescription(description);
		if (mDefaultContentLayout) {
			((TextView) findViewById(R.id.one_time_hint_view_cardview_description)).setText(description);
		}
		return this;
	}

	public OneTimeHintView setDescription(int description) {
		return setDescription(getResources().getString(description));
	}

	public OneTimeHintView setTitle(CharSequence title) {
		mAttributes.setTitle(title);
		if (mDefaultContentLayout) {
			((TextView) findViewById(R.id.one_time_hint_view_cardview_title)).setText(title);
		}
		return this;
	}

	public OneTimeHintView setTitle(int title) {
		return setTitle(getResources().getString(title));
	}

	public OneTimeHintView setDescriptionTextColor(int titleTextColor) {
		mAttributes.setDescriptionTextColor(titleTextColor);
		if (mDefaultContentLayout) {
			((TextView) findViewById(R.id.one_time_hint_view_cardview_description)).setTextColor(titleTextColor);
		}
		return this;
	}

	public OneTimeHintView setTitleTextColor(int titleTextColor) {
		mAttributes.setTitleTextColor(titleTextColor);
		if (mDefaultContentLayout) {
			((TextView) findViewById(R.id.one_time_hint_view_cardview_title)).setTextColor(titleTextColor);
		}
		return this;
	}

	@Override
	public OneTimeHintView setPreferencesKey(String preferencesKey) {
		return this;
	}

	@Override
	public OneTimeHintView setCardBackgroundColor(int cardBackgroundColor) {
		mAttributes.setCardBackgroundColor(cardBackgroundColor);
		((CardView) findViewById(R.id.one_time_hint_view_cardview)).setCardBackgroundColor(cardBackgroundColor);
		return this;
	}

	@Override
	public OneTimeHintView setGlobalTextColor(int globalTextColor) {
		return this;
	}

	@Override
	public OneTimeHintView setViewBackgroundColor(int color) {

		mAttributes.setBackgroundColor(color);
		return this;
	}

	public OneTimeHintView setUniqueKey(String key) {
		return this;
	}

	public OneTimeHintView setContentLayout(int contentLayout) {
		mDefaultContentLayout = contentLayout == DEFAULT_CONTENT_LAYOUT;
		ViewGroup root = (ViewGroup) findViewById(R.id.one_time_hint_view_content_container);
		root.removeAllViews();
		View content = LayoutInflater.from(getContext())
			.inflate(contentLayout, root, false);
		root.addView(content, 0);
		return this;
	}

	public interface OnDismissListener {
		void onDismiss();
	}

}
