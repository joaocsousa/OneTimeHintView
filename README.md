
# OneTimeHintView

[![Build Status](https://travis-ci.org/joaocsousa/OneTimeHintView.svg?branch=master)](https://travis-ci.org/joaocsousa/OneTimeHintView)

This is an Android library that allows you to easily add a onetime (or disposable) hint view to any layout with a high level of customization. Once the user dismisses the view, it'll never be displayed again.

##Preview
[Video](https://www.youtube.com/watch?v=y_98Phqq1LQ)

##Usage
First you need to import this library using gradle (coming soon).


### XML

You can add a view to your XML:

    <com.tinycoolthings.onetimehintview.OneTimeHintView
       android:layout_width="match_parent"
       android:layout_height="wrap_content"
       app:oneTimeHintView_title="Did you know..."
       app:oneTimeHintView_description="You can use this card to display useful information."
       app:oneTimeHintView_key="unique_key"/>

### Code
Or you can add it through code if you wish:

    oneTimeHintView(this)
		.withKey("got_it_test_3")
		.withTitle("Did you also know")
		.withDescription("You can also load a OneTimeHintView using code?")
		.withCardBackgroundColor(Color.parseColor("#3F51B5"))
		.withDebugEnabled(false)
		.withTextColor(getResources().getColor(android.R.color.white))
		.loadInto(findViewById(R.id.hintViewContainer))

###All properties

There are setters for every property.

    backgroundColor - Set the background color.
    cardColor - Set the card's color.
    title - Set the title.
    description - Set the description.
    buttonLabel - Set the button label.
    buttonLabelTextColor - Set the text color.
    key - Set the key.
    textColor - Set the text color.
    debug - If the app is in debug mode, force the view to be shown.
    contentLayout - Sets a custom content layout.
    animateDismiss - Should animate dismiss or not.

## License

Copyright 2015 Joao Sousa

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
