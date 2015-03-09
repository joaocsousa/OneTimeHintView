package com.tinycoolthings.onetimehintviewtest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import static com.tinycoolthings.onetimehintview.OneTimeHintView.oneTimeHintView;


public class OneTimeHintViewCodeTestActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_one_time_hint_view_code_test);
		Toolbar toolbar = (Toolbar) findViewById(R.id.activity_one_time_hint_view_code_test_toolbar);
		toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				NavUtils.navigateUpFromSameTask(OneTimeHintViewCodeTestActivity.this);
			}
		});
		setSupportActionBar(toolbar);
		oneTimeHintView(this)
			.withKey("got_it_test_3")
			.withTitle("Did you also know")
			.withDescription("You can also load a OneTimeHintView using code?")
			.withCardBackgroundColor(Color.parseColor("#3F51B5"))
			.withDebugEnabled(false)
			.withTextColor(getResources().getColor(android.R.color.white))
			.loadInto(findViewById(R.id.activity_one_time_hint_view_code_test_container));
	}

}
