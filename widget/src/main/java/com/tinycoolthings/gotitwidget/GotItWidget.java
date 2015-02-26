package com.tinycoolthings.gotitwidget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

/**
 * Created by joaosousa on 25/02/15.
 */
public class GotItWidget extends LinearLayout {

	public GotItWidget(Context context) {
		this(context, null);
	}

	public GotItWidget(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public GotItWidget(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public GotItWidget(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init();
	}

	private void init() {
		setBackgroundColor(getResources().getColor(android.R.color.transparent));
		int padding = getResources().getDimensionPixelOffset(R.dimen.got_it_widget_default_margin);
		LayoutInflater.from(getContext()).inflate(R.layout.got_it_widget, this, true);
	}



}
