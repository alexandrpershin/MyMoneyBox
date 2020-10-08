# Solution summary

## About app architecture

I used Single Activity Application approach and Model-View-ViewModel architectural pattern.

## Tech stack
 
 1. Kotlin Coroutines for network requests and background operations.
 2. Room database for data persistence.
 3. Navigation controller for navigation between fragments.
 4. Koin library for dependency injection.
 5. Chucker interceptor library to visualise network requests (is disabled for release version).
 6. Ktx library for some cool extensions
 7. Retrofit library for networking
 8. Encryption library for encryption data in SharedPreferences   

## How app works?

1. Splash screen <br />
Entry point is SplashScreenFragment which shows fun animation and app logo. Here it's ViewModel 
checks if user was logged previously. 
If logged app navigates user to screen with all investment products otherwise to login screen.

2. Log in screen <br /> 
On login screen user is able to enter email and password to log in in the app.
Every time when user clicks to "Log in" button app checks if input is valid.
If user passed the checks then user go to screen with all investment products.

3. User accounts screen (products) <br />
Once user navigated to screen with all investment products, app gets data from database and 
display to the user, at the same time app 
send request to server for latest data and refresh the database. Here I used SSOT 
(Single source of truth), so data will always come to screen from one source. 
User is able to Swipe to refresh the screen to get fresh data force. User is able to click on one 
of items and app will navigate the user
to product details.

4. Product details screen  <br />
Once user navigated to product details, the app displays product details and gives possibility to
pop up moneybox with fixed £10 amount.

The token may become expired after 5 minutes, so app checks backend response with every request.
If there is error about token expiration the app will force log out user and navigate to Log in 
screen. All local data will be removed.

5. Unit tests  <br />
I implemented some unit tests for LoginViewModel.kt and UserAccountsViewModel.kt
 



