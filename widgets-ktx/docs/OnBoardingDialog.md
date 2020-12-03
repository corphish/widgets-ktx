# OnBoardingDialog
This is a dialog which can show information in multiple swipeable pages within the dialog. It can be used to guide the user on how to use the section or let the user know about the capabilities of a section. It should however not be used as app intro.

### Usage
```Kotlin
OnBoardingDialog(this).apply {
    slides = listOf(
            OnBoardingDialog.Slide(
                    titleString = "Test slide 1",
                    messageString = "Description for slide 1. You can also add animation from lottiefiles here which will appear on top."
            ),
            OnBoardingDialog.Slide(
                    titleString = "Test slide 2",
                    messageString = "Description for slide 2."
            ),
            OnBoardingDialog.Slide(
                    titleString = "Test slide 3",
                    messageString = "Description for slide 3."
            ),
    )
}.show()
```