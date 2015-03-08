package com.tinycoolthings.onetimehintview.ui;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by joaosousa on 08/03/15.
 */
public class AttributesTest {

	private static final Boolean ANIMATE_DISMISS = true;
	private static final Integer BACKGROUND_COLOR = 100;
	private static final String BUTTON_LABEL = "buttonLabel";
	private static final Integer BUTTON_LABEL_TEXT_COLOR = 200;
	private static final Integer BUTTON_CARD_BACKGROUND_COLOR = 300;
	private static final Integer CONTENT_LAYOUT = 400;
	private static final Boolean DEBUG = true;
	private static final String DESCRIPTION = "description";
	private static final String KEY = "key";
	private static final Integer TEXT_COLOR = 500;
	private static final String TITLE = "title";

	Attributes mAttributes = new Attributes();

	@Before
	public void setUp() throws Exception {
		mAttributes.setAnimateDismiss(ANIMATE_DISMISS);
		mAttributes.setBackgroundColor(BACKGROUND_COLOR);
		mAttributes.setButtonLabel(BUTTON_LABEL);
		mAttributes.setButtonLabelTextColor(BUTTON_LABEL_TEXT_COLOR);
		mAttributes.setCardBackgroundColor(BUTTON_CARD_BACKGROUND_COLOR);
		mAttributes.setContentLayout(CONTENT_LAYOUT);
		mAttributes.setDebug(DEBUG);
		mAttributes.setDescription(DESCRIPTION);
		mAttributes.setKey(KEY);
		mAttributes.setTextColor(TEXT_COLOR);
		mAttributes.setTitle(TITLE);
	}

	@Test
	public void testGetters() throws Exception {
		assertEquals(ANIMATE_DISMISS, mAttributes.shouldAnimateDismiss().getValue());
		assertEquals(BACKGROUND_COLOR, mAttributes.getBackgroundColor().getValue());
		assertEquals(BUTTON_LABEL, mAttributes.getButtonLabel().getValue());
		assertEquals(BUTTON_LABEL_TEXT_COLOR, mAttributes.getButtonLabelTextColor().getValue());
		assertEquals(BUTTON_CARD_BACKGROUND_COLOR, mAttributes.getCardBackgroundColor().getValue());
		assertEquals(CONTENT_LAYOUT, mAttributes.getContentLayout().getValue());
		assertEquals(DEBUG, mAttributes.isInDebug().getValue());
		assertEquals(DESCRIPTION, mAttributes.getDescription().getValue());
		assertEquals(KEY, mAttributes.getKey().getValue());
		assertEquals(TEXT_COLOR, mAttributes.getTextColor().getValue());
		assertEquals(TITLE, mAttributes.getTitle().getValue());
	}
}
