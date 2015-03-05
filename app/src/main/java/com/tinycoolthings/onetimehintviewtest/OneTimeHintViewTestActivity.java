package com.tinycoolthings.onetimehintviewtest;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.tinycoolthings.onetimehintview.OneTimeHintView;

public class OneTimeHintViewTestActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_one_time_hint_view_test);
//		new OneTimeHintView(this)
//			.setTitle("Did you know...")
//			.setDescription("That you can add hint views progamatically?")
//			.setCardBackgroundColor(android.R.color.holo_blue_bright)
//			.loadInto((android.view.ViewGroup) findViewById(R.id.hint_view_test_container));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_one_time_hint_view_test, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
