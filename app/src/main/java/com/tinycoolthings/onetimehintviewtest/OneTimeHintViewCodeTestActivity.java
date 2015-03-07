package com.tinycoolthings.onetimehintviewtest;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;


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
	}

}
