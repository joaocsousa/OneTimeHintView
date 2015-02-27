package com.tinycoolthings.gotitwidget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by joaosousa on 25/02/15.
 */
public class OneTimeHintView extends LinearLayout {

	private CardView mCardView;
	private Button mDismissButton;
	private ArrayList<OnDismissListener> mOnDismissListeners = new ArrayList<>();

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
		LayoutInflater.from(getContext()).inflate(R.layout.view_one_time_hint_view_card, this, true);
		processAttributes(attrs, defStyleAttr, defStyleRes);
		addOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				animate().alpha(0).start();
			}
		});
	}

	private void processAttributes(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.OneTimeHintView, defStyleAttr, defStyleRes);
		try {
			// background color
			int backgroundColor = typedArray.getColor(R.styleable.OneTimeHintView_one_time_hint_view_background_color, R.color.one_time_hint_view_default_background_color);
			setBackgroundColor(getResources().getColor(backgroundColor));
			// card background color
			int cardBackgroundColor = typedArray.getColor(R.styleable.OneTimeHintView_one_time_hint_view_card_color, R.color.one_time_hint_view_default_card_color);
			getCardView().setCardBackgroundColor(getResources().getColor(cardBackgroundColor));
			// text color
			int defaultTextColor = typedArray.getColor(R.styleable.OneTimeHintView_one_time_hint_view_card_color, R.color.one_time_hint_view_default_text_color);
			getDismissButton().setTextColor(getResources().getColor(defaultTextColor));
			// title
			initTitle(getResources().getColor(defaultTextColor), typedArray.getString(R.styleable.OneTimeHintView_one_time_hint_view_title));
			// description
			initDescription(getResources().getColor(defaultTextColor), typedArray.getString(R.styleable.OneTimeHintView_one_time_hint_view_description));
		} finally {
			typedArray.recycle();
		}
	}

	private CardView getCardView() {
		if (mCardView == null) {
			mCardView = (CardView) findViewById(R.id.one_time_hint_view_cardview);
		}
		return mCardView;
	}

	private Button getDismissButton() {
		if (mDismissButton == null) {
			mDismissButton = (Button) findViewById(R.id.one_time_hint_view_cardview_button);
			mDismissButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					for (OnDismissListener onDismissListener : mOnDismissListeners) {
						onDismissListener.onDismiss();
					}
				}
			});
		}
		return mDismissButton;
	}

	public void addOnDismissListener(OnDismissListener onDismissListener) {
		if (onDismissListener == null) {
			throw new IllegalArgumentException("onDismissListener can't be null!");
		}
		mOnDismissListeners.add(onDismissListener);
	}

	private void initTitle(@ColorRes int color, String text) {
		TextView title = (TextView) findViewById(R.id.one_time_hint_view_cardview_title);
		title.setTextColor(color);
		title.setText(text);
	}

	private void initDescription(@ColorRes int color, String text) {
		TextView description = (TextView) findViewById(R.id.one_time_hint_view_cardview_description);
		description.setTextColor(color);
		description.setText(text);
	}

	interface OnDismissListener {
		void onDismiss();
	}

}
