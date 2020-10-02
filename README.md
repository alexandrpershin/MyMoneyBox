# The Brief:

Create a mini version of the Moneybox app that will allow existing users to login, check their account and add money to their moneybox.

The project currently contains a LoginActivity.kt with 3 EditTexts, a Button and a lovely animation of an owl that plays on the press of the button.  We want you to implement two additional screens.

## The screens

We want to give some useful functionality to our users. To allow them to log into the app, view and edit their account using our sandbox API (See API usage).

### Screen 1 - User accounts screen
This screen should be shown after the user has successfully logged in and should show have the following functionality:
- Display "Hello {name} **only** if they provided it on the previous screen"
- Show the **'TotalPlanValue'** of a user.
- Show the accounts the user holds, e.g. ISA, GIA, LISA, Pension.
- Show all of those account's **'PlanValue'**.
- Show all of those account's **'Moneybox'** total.

### Screen 2 - Individual account screen
If a user selects one of those accounts, they should then be taken to this screen.  This screen should have the following functionality:
- Show the **'Name'** of the account.
- Show the account's **'PlanValue'**.
- Show the accounts **'Moneybox'** total.
- Allow a user to add to a fixed value (e.g. £10) to their moneybox total.

A prototype wireframe of all 3 screens is provided as a guideline. You are free to change any elements of the screen and provide additional information if you wish.

![](/images/wireframe.png)

## What we are looking for:
 - An android application written in either Java or Kotlin.
 - Demonstration of coding style and design patterns.
 - Knowledge of common android libraries and any others that you find useful.
 - Storage of data between screens.
 - Consistency of data between screens.
 - Error handling.
 - Any form of unit or integration testing you see fit.
 - The application must run on Android 5.0 and above.
 - The application must compile and run in Android Studio.

Please feel free to modify/refactor the LoginActivity and use any libraries/helper methods to make your life easier.

## How to Submit your solution:
 - Zip up your solution, excluding any build artifacts to reduce the size, and email it back to our recruitment team.
 - Provide a readme in markdown with a brief summary of your application.

## API Usage
This is a brief summary of the api endpoints in the moneybox sandbox environment. There are a lot of other additional properties from the json responses that are not relevant, but you must use these endpoints to retrieve the information needed for this application.

#### Base URL & Test User
The base URL for the moneybox sandbox environment is `https://api-test01.moneyboxapp.com/`.
You can log into test your app using the following user:

|  Username          | Password         |
| ------------- | ------------- |
| jaeren+androidtest@moneyboxapp.com  | P455word12  |

#### Headers

In order to make requests https must be used and the following headers must be included in each request.

|  Key | Value |
| ------------- | ------------- |
| AppId  | 3a97b932a9d449c981b595  |
| Content-Type  | application/json  |
| appVersion | 7.15.0 |
| apiVersion | 3.0.0 |

#### Authentication
To login with this user and retrieve a bearer token you need to call `POST /users/login`.
```
POST /users/login
{
  "Email": "jaeren+androidtest@moneyboxapp.com",
  "Password": "P455word12",
  "Idfa": "ANYTHING"
}
```
Sample json response
```
"Session": {
        "BearerToken": "TsMWRkbrcu3NGrpf84gi2+pg0iOMVymyKklmkY0oI84=",
        "ExternalSessionId": "4ff0eab7-7d3f-40c5-b87b-68d4a4961983", -- not used
        "SessionExternalId": "4ff0eab7-7d3f-40c5-b87b-68d4a4961983", -- not used
        "ExpiryInSeconds": 0 -- not used
    }
```
After obtaining a bearer token an Authorization header must be provided for all other endpoints along with the headers listed above (Note: The BearerToken has a sliding expiration of 5 mins).

|  Key          | Value         |
| ------------- | ------------- |
| Authorization  | Bearer TsMWRkbrcu3NGrpf84gi2+pg0iOMVymyKklmkY0oI84=  |

#### Investor Products
Provides product and account information for a user that will be needed for the two additional screens.
```
GET /investorproducts
```
### One off payments
Adds a one off amount to the users moneybox.
```
POST /oneoffpayments
{
  "Amount": 20,
  "InvestorProductId": 3230 ------> The InvestorProductId from /investorproducts endpoint
}
```
Good luck!

# Solution summary

## The screens

Hi there! Thank you very much for your time to create this test assessment and for checking my solution.

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

1. Splash screen 
Entry point is SplashScreenFragment which shows fun animation and app logo. Here it's ViewModel 
checks if user was logged previously. 
If logged app navigates user to screen with all investment products otherwise to login screen.

2. Log in screen 
On login screen user is able to enter email and password to log in in the app.
Every time when user clicks to "Log in" button app checks if input is valid.
If user passed the checks then user go to screen with all investment products.

3. User accounts screen (products)
Once user navigated to screen with all investment products, app gets data from database and 
display to the user, at the same time app 
send request to server for latest data and refresh the database. Here I used SSOT 
(Single source of truth), so data will always come to screen from one source. 
User is able to Swipe to refresh the screen to get fresh data force. User is able to click on one 
of items and app will navigate the user
to product details.

4. Product details screen
Once user navigated to product details, the app displays product details and gives possibility to
pop up moneybox with fixed £10 amount.

The token may become expired after 5 minutes, so app checks backend response with every request.
If there is error about token expiration the app will force log out user and navigate to Log in 
screen. All local data will be removed.

5. Unit tests
I implemented some unit tests for LoginViewModel.kt and UserAccountsViewModel.kt

That's all! Thank you!!!!!! 
  




