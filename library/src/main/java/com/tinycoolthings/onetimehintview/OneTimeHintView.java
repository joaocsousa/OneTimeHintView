package com.tinycoolthings.onetimehintview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tinycoolthings.onetimehintview.ui.Attributes;
import com.tinycoolthings.onetimehintview.ui.Size;
import com.tinycoolthings.onetimehintview.util.SimpleAnimatorListener;

import java.util.ArrayList;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.tinycoolthings.onetimehintview.util.Utils.isDebugEnabled;

/**
 * Disposable view that will only be shown until the user acknowledges that they've read it, after
 * which point it will no longer be displayed.
 * <p/>
 * Created by joaosousa on 25/02/15.
 */
public class OneTimeHintView extends LinearLayout {

	/** The default duration of the dismiss animation. */
	private static final int DEFAULT_ANIMATION_DURATION = 250;
	/** The default content layout. */
	private static final int DEFAULT_CONTENT_LAYOUT = R.layout.view_one_time_hint_view_default_content;

	/** The list of dismiss listeners to be notified when this view is dismissed. */
	private ArrayList<OnDismissListener> mOnDismissListeners = new ArrayList<>();
	/** Boolean that controls if this view is supposed to be shown or not. */
	private boolean mShow = true;
	/** The size of this view. */
	private Size mSize;
	/** The list of attributes that this view will use to be costumized. */
	private Attributes mAttributes = new Attributes();

	private OnDismissListener mOnDismissedListener = new OnDismissListener() {

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
	};

	/**
	 * Constructor. For arguments meaning, check {@link #OneTimeHintView(android.content.Context)}.
	 *
	 * @param context
	 */
	public OneTimeHintView(Context context) {
		this(context, null);
	}

	/**
	 * Constructor. For arguments meaning, check {@link #LinearLayout(android.content.Context, android.util.AttributeSet)}.
	 *
	 * @param context
	 * @param attrs
	 */
	public OneTimeHintView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * Constructor. For arguments meaning, check {@link #LinearLayout(android.content.Context, android.util.AttributeSet, int)}.
	 *
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public OneTimeHintView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, R.style.OneTimeHintView);
	}

	/**
	 * Constructor. For arguments meaning, check {@link #LinearLayout(android.content.Context, android.util.AttributeSet, int)}.
	 *
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 * @param defStyleRes
	 */
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public OneTimeHintView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	/**
	 * Initialization method that will process the attributes from the xml and applied them
	 *
	 * @param attrs        Attribute set.
	 * @param defStyleAttr Attribute of the current theme.
	 * @param defStyleRes  Attribute of the current theme.
	 */
	private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		processAttributes(attrs, defStyleAttr, defStyleRes);
		addOnDismissListener(mOnDismissedListener);
		if (wasDismissedBefore()) {
			hide();
		} else {
			LayoutInflater.from(getContext()).inflate(R.layout.view_one_time_hint_view_card, this, true);
			applyAttributes();
			prepareDismissListener();
		}
	}

	/**
	 * Prepares the dismiss listener.
	 */
	private void prepareDismissListener() {
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

	/**
	 * Apply every attribute available from the list of attributes.
	 */
	private void applyAttributes() {
		// preferences key
		if (mAttributes.getPreferencesKey().changed()) {
			if (wasDismissedBefore()) {
				hide();
			} else {
				show();
			}
			mAttributes.getPreferencesKey().used();
		}
		// content layout
		if (mAttributes.getContentLayout().changed()) {
			ViewGroup root = (ViewGroup) findViewById(R.id.one_time_hint_view_content_container);
			if (root != null) {
				root.removeAllViews();
				View content = LayoutInflater.from(getContext())
					.inflate(mAttributes.getContentLayout().getValue(), root, false);
				if (content != null) {
					root.addView(content, 0);
				}
			}
			mAttributes.getContentLayout().used();
		}
		// background color
		if (mAttributes.getBackgroundColor().changed()) {
			setBackgroundColor(mAttributes.getBackgroundColor().getValue());
			mAttributes.getBackgroundColor().used();
		}
		// card background color
		if (mAttributes.getCardBackgroundColor().changed()) {
			CardView cardView = (CardView) findViewById(R.id.one_time_hint_view_cardview);
			if (cardView != null) {
				cardView.setCardBackgroundColor(mAttributes.getCardBackgroundColor().getValue());
			}
			mAttributes.getCardBackgroundColor().used();
		}
		// text color
		if (mAttributes.getTextColor().changed()) {
			// apply to title
			TextView titleView = (TextView) findViewById(R.id.one_time_hint_view_cardview_title);
			if (titleView != null) {
				titleView.setTextColor(mAttributes.getTextColor().getValue());
			}
			// apply to description
			TextView descriptionView = (TextView) findViewById(R.id.one_time_hint_view_cardview_description);
			if (descriptionView != null) {
				descriptionView.setTextColor(mAttributes.getTextColor().getValue());
			}
			// apply to button Label
			Button buttonView = (Button) findViewById(R.id.one_time_hint_view_cardview_button);
			if (buttonView != null) {
				buttonView.setTextColor(mAttributes.getTextColor().getValue());
			}
			mAttributes.getTextColor().used();
		}
		// title
		if (mAttributes.getTitle().changed()) {
			TextView titleView = (TextView) findViewById(R.id.one_time_hint_view_cardview_title);
			if (titleView != null) {
				titleView.setText(mAttributes.getTitle().getValue());
			}
			mAttributes.getTitle().used();
		}
		// description
		if (mAttributes.getDescription().changed()) {
			TextView descriptionView = (TextView) findViewById(R.id.one_time_hint_view_cardview_description);
			if (descriptionView != null) {
				descriptionView.setText(mAttributes.getDescription().getValue());
			}
			mAttributes.getDescription().used();
		}
		// button label text color
		if (mAttributes.getButtonLabelTextColor().changed()) {
			TextView buttonView = (TextView) findViewById(R.id.one_time_hint_view_cardview_button);
			if (buttonView != null) {
				buttonView.setTextColor(mAttributes.getButtonLabelTextColor().getValue());
			}
			mAttributes.getButtonLabelTextColor().used();
		}
		// button label
		if (mAttributes.getButtonLabel().changed()) {
			Button buttonView = (Button) findViewById(R.id.one_time_hint_view_cardview_button);
			if (buttonView != null) {
				buttonView.setText(mAttributes.getButtonLabel().getValue());
			}
		}
		// debug
		if (mAttributes.isInDebug().changed()) {
			if (wasDismissedBefore()) {
				hide();
			} else {
				show();
			}
			mAttributes.isInDebug().used();
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (mSize == null && getMeasuredWidth() > 0 && getMeasuredHeight() > 0) {
			mSize = new Size(getMeasuredWidth(), getMeasuredHeight());
		}
	}

	/**
	 * Marks this hitn view as dismissed, so that it won't be displayed again.
	 */
	private void markAsDismissed() {
		getDefaultSharedPreferences(getContext()).edit().putBoolean(mAttributes.getPreferencesKey().getValue(), true).commit();
	}

	/**
	 * Checks if this view has been dismissed before.
	 * @return True if this view has been dismissed before, false otherwise.
	 */
	private boolean wasDismissedBefore() {
		return !mAttributes.isInDebug().getValue() &&
			getDefaultSharedPreferences(getContext()).getBoolean(mAttributes.getPreferencesKey().getValue(), false);
	}

	/**
	 * Marks this view to be shown.
	 */
	private void show() {
		mShow = true;
		updateVisibility();
	}

	/**
	 * Marks this view to be hidden.
	 */
	private void hide() {
		mShow = false;
		updateVisibility();
	}

	/**
	 * Updates the visibility of this view based.
	 */
	private void updateVisibility() {
		setVisibility(mShow ? VISIBLE : GONE);
		ViewGroup parent = (ViewGroup) getParent();
		if (parent != null) {
			parent.removeView(this);
		}
	}

	/**
	 * Processes the attributes provided and stores them in a
	 * {@link com.tinycoolthings.onetimehintview.ui.Attributes} instance.
	 * For arguments meaning see {@link #OneTimeHintView(android.content.Context, android.util.AttributeSet, int, int)}
	 *
	 * @param attrs
	 * @param defStyleAttr
	 * @param defStyleRes
	 */
	private void processAttributes(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.OneTimeHintView, defStyleAttr, defStyleRes);
		try {
			// preferences key
			String key = typedArray.getString(R.styleable.OneTimeHintView_oneTimeHintView_key);
			if (key == null || key.isEmpty()) {
				throw new IllegalStateException("You definitely need to provide a preferences key to the " + getClass().getSimpleName() +
					". Use the attribute \'one_time_hint_view_key\' to provide a unique key.");
			}
			mAttributes.setKey(key);
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
			// global text color
			int textColor = typedArray.getColor(
				R.styleable.OneTimeHintView_oneTimeHintView_cardColor,
				R.color.one_time_hint_view_default_text_color);
			mAttributes.setTextColor(getResources().getColor(textColor));
			// title
			mAttributes.setTitle(typedArray.getString(R.styleable.OneTimeHintView_oneTimeHintView_title));
			// description
			mAttributes.setDescription(typedArray.getString(R.styleable.OneTimeHintView_oneTimeHintView_description));
			// button label text color
			mAttributes.setButtonLabelTextColor(getResources().getColor(textColor));
			// button label
			CharSequence defaultButtonLabel = getResources().getString(R.string.one_time_hint_view_button_default_label);
			String buttonLabel = typedArray.getString(R.styleable.OneTimeHintView_oneTimeHintView_buttonLabel);
			mAttributes.setButtonLabel(buttonLabel != null && !buttonLabel.isEmpty() ? buttonLabel : defaultButtonLabel);
			// debug
			boolean isInDebugMode = isDebugEnabled(getContext()) &&
				typedArray.getBoolean(R.styleable.OneTimeHintView_oneTimeHintView_debug, false);
			mAttributes.setDebug(isInDebugMode);
			// dismiss animation
			boolean dismissAnimation = typedArray.getBoolean(R.styleable.OneTimeHintView_oneTimeHintView_dismissAnimation, true);
			mAttributes.setDismissAnimation(dismissAnimation);
		} finally {
			typedArray.recycle();
		}
	}

	/**
	 * Adds a listener to the list of listeners to be notified when the card is dismissed.
	 *
	 * @param onDismissListener The listener to be called when the card is dismissed.
	 * @return
	 */
	public OneTimeHintView addOnDismissListener(OnDismissListener onDismissListener) {
		if (onDismissListener == null) {
			throw new IllegalArgumentException("onDismissListener can't be null!");
		}
		mOnDismissListeners.add(onDismissListener);
		return this;
	}

	/**
	 * Sets the text color of the dismiss button label.
	 *
	 * @param buttonLabelTextColor The text color to be applied to the button's text.
	 * @return
	 */
	@SuppressWarnings("unused")
	public OneTimeHintView setButtonLabelTextColor(@ColorRes int buttonLabelTextColor) {
		mAttributes.setButtonLabelTextColor(buttonLabelTextColor);
		applyAttributes();
		return this;
	}

	/**
	 * Sets the card dismiss button label to be displayed.
	 *
	 * @param buttonLabel The text to used as button label in the card.
	 * @return
	 */
	@SuppressWarnings("unused")
	public OneTimeHintView setButtonLabel(CharSequence buttonLabel) {
		mAttributes.setButtonLabel(buttonLabel);
		applyAttributes();
		return this;
	}

	/**
	 * Sets the button label by delegating to {@link #setButtonLabel(CharSequence)},
	 * but providing the resolved button label resource.
	 *
	 * @param buttonLabel A string resource to be used as button label.
	 * @return
	 */
	@SuppressWarnings("unused")
	public OneTimeHintView setButtonLabel(@StringRes int buttonLabel) {
		return setButtonLabel(getResources().getString(buttonLabel));
	}

	/**
	 * Sets the card description to be displayed (if applicable).
	 *
	 * @param description The description to used in the card.
	 * @return
	 */
	@SuppressWarnings("unused")
	public OneTimeHintView setDescription(CharSequence description) {
		mAttributes.setDescription(description);
		applyAttributes();
		return this;
	}

	/**
	 * Sets the card description (if applicable) by delegating to {@link #setDescription(CharSequence)},
	 * but providing the resolved description resource.
	 *
	 * @param description A string resource to be used as description.
	 * @return
	 */
	@SuppressWarnings("unused")
	public OneTimeHintView setDescription(@StringRes int description) {
		return setDescription(getResources().getString(description));
	}

	/**
	 * Sets the card title to be displayed (if applicable).
	 *
	 * @param title The title to used in the card.
	 * @return
	 */
	@SuppressWarnings("unused")
	public OneTimeHintView setTitle(CharSequence title) {
		mAttributes.setTitle(title);
		applyAttributes();
		return this;
	}

	/**
	 * Sets the card title (if applicable) by delegating to {@link #setTitle(CharSequence)},
	 * but providing the resolved title resource.
	 *
	 * @param title A string resource to be used as title.
	 * @return
	 */
	@SuppressWarnings("unused")
	public OneTimeHintView setTitle(@StringRes int title) {
		return setTitle(getResources().getString(title));
	}

	/**
	 * Unique key that will determine if this view is supposed to be displayed or not.
	 *
	 * @param key Should be a unique key.
	 * @return
	 */
	@SuppressWarnings("unused")
	public OneTimeHintView setKey(String key) {
		mAttributes.setKey(key);
		applyAttributes();
		return this;
	}

	/**
	 * Sets the text color to be used in both the title (if available), description (if available)
	 * and button.
	 *
	 * @param textColor The color to be used as text color.
	 * @return
	 */
	@SuppressWarnings("unused")
	public OneTimeHintView setTextColor(@ColorRes int textColor) {
		mAttributes.setTextColor(textColor);
		applyAttributes();
		return this;
	}

	/**
	 * Sets the color background of the main view (not the card itself).
	 *
	 * @param color Color resource to apply as background color.
	 * @return
	 */
	@SuppressWarnings("unused")
	public OneTimeHintView setBackground(@ColorRes int color) {
		mAttributes.setBackgroundColor(color);
		applyAttributes();
		return this;
	}

	/**
	 * Sets a custom content layout to be used in the card hint view.
	 *
	 * @param contentLayout The layout to use.
	 */
	@SuppressWarnings("unused")
	public OneTimeHintView setContentLayout(int contentLayout) {
		mAttributes.setContentLayout(contentLayout);
		applyAttributes();
		return this;
	}

	/**
	 * Interface to be implemented by every class that wants
	 * to be notified when this hint view is dismissed.
	 */
	public interface OnDismissListener {
		/**
		 * Method that will be called when this hint view is dismissed.
		 */
		void onDismiss();
	}

}
