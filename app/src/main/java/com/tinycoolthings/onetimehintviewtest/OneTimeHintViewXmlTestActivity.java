package com.tinycoolthings.onetimehintviewtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tinycoolthings.onetimehintview.OneTimeHintView;

public class OneTimeHintViewXmlTestActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_one_time_hint_view_xml_test);
		setSupportActionBar((Toolbar) findViewById(R.id.activity_one_time_hint_view_xml_test_toolbar));
		findViewById(R.id.activity_one_time_hint_view_test_code_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(
					new Intent(
						OneTimeHintViewXmlTestActivity.this,
						OneTimeHintViewCodeTestActivity.class));
			}
		});
		OneTimeHintView hintView = (OneTimeHintView) findViewById(R.id.activity_one_time_hint_view_xml_test_hint_view_1);
		hintView.addOnDismissListener(new OneTimeHintView.OnDismissListener() {
			@Override
			public void onDismiss() {
				findViewById(R.id.activity_one_time_hint_view_xml_test_separator).setVisibility(View.GONE);
			}
		});
	}

}
