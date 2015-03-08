package com.tinycoolthings.onetimehintview.ui;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AttributeTest {

	Attribute<String> mStringAttribute;
	Attribute<Integer> mIntegerAttribute;
	Attribute<Boolean> mBooleanAttribute;

	private static final String STRING_VALUE = "New Value";
	private static final Integer INTEGER_VALUE = 1000;
	private static final Boolean BOOLEAN_VALUE = true;

	@Before
	public void setUp() throws Exception {
		mStringAttribute = new Attribute<>();
		mIntegerAttribute = new Attribute<>();
		mBooleanAttribute = new Attribute<>();
	}

	@Test
	public void testAttributeCreation() throws Exception {
		assertNotNull(mStringAttribute);
		assertNotNull(mIntegerAttribute);
		assertNotNull(mBooleanAttribute);
	}

	@Test
	public void testAttributeSetter() throws Exception {

		assertFalse("String attribute was not changed yet.", mStringAttribute.changed());
		assertFalse("Integer attribute was not changed yet.", mIntegerAttribute.changed());
		assertFalse("Boolean attribute was not changed yet.", mBooleanAttribute.changed());

		assertFalse("String attribute was not set yet, so it should be invalid.", mStringAttribute.isValid());
		assertFalse("Integer attribute was not set yet, so it should be invalid.", mIntegerAttribute.isValid());
		assertFalse("Boolean attribute was not set yet, so it should be invalid.", mBooleanAttribute.isValid());

		mStringAttribute.setValue(STRING_VALUE);
		assertTrue("String attribute should be " + STRING_VALUE, mStringAttribute.getValue().equals(STRING_VALUE));
		mIntegerAttribute.setValue(INTEGER_VALUE);
		assertTrue("Integer attribute should be " + INTEGER_VALUE, mIntegerAttribute.getValue().equals(INTEGER_VALUE));
		mBooleanAttribute.setValue(BOOLEAN_VALUE);
		assertTrue("Boolean attribute should be " + BOOLEAN_VALUE, mBooleanAttribute.getValue().equals(BOOLEAN_VALUE));

		assertTrue("String attribute should have changed", mStringAttribute.changed());
		assertTrue("Integer attribute should have changed", mIntegerAttribute.changed());
		assertTrue("Boolean attribute should have changed", mBooleanAttribute.changed());

		assertTrue("String attribute should be valid. Current value -> " + mStringAttribute.getValue(), mStringAttribute.isValid());
		assertTrue("Integer attribute should be valid Current value -> " + mIntegerAttribute.getValue(), mIntegerAttribute.isValid());
		assertTrue("Boolean attribute should be valid. Current value -> " + mBooleanAttribute.getValue(), mBooleanAttribute.isValid());

		// mark all as used
		mStringAttribute.used();
		mBooleanAttribute.used();
		mIntegerAttribute.used();

		assertFalse("String attribute should not have changed", mStringAttribute.changed());
		assertFalse("Integer attribute should not have changed", mIntegerAttribute.changed());
		assertFalse("Boolean attribute should not have changed", mBooleanAttribute.changed());

		assertTrue("String attribute should be valid. Current value -> " + mStringAttribute.getValue(), mStringAttribute.isValid());
		assertTrue("Integer attribute should be valid Current value -> " + mIntegerAttribute.getValue(), mIntegerAttribute.isValid());
		assertTrue("Boolean attribute should be valid. Current value -> " + mBooleanAttribute.getValue(), mBooleanAttribute.isValid());

	}

}