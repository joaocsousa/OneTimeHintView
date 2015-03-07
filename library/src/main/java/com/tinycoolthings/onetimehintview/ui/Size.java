package com.tinycoolthings.onetimehintview.ui;

/**
 * Created by joaosousa on 27/02/15.
 * <p/>
 * Helper class to manage a size (with and height).
 */
public class Size {

	private final int mWidth;
	private final int mHeight;

	public Size(int width, int height) {
		mWidth = width;
		mHeight = height;
	}

	/** Gets the width of this size (in pixels). */
	@SuppressWarnings("unused")
	public int getWidth() {
		return mWidth;
	}

	/** Gets the height of this size (in pixels). */
	@SuppressWarnings("unused")
	public int getHeight() {
		return mHeight;
	}

}
