# Instant News

<img src="doc%2Flist_light.png" width="200"  alt=""/> | <img src="doc%2Flist_dark.png" width="200"  alt=""/>
:-------------------------:|:-------------------------:
<img src="doc%2Fdetails_light.png" width="200"  alt=""/> | <img src="doc%2Fdetails_dark.png" width="200" alt=""/>

## Description

This application was developed as part of my hire process
for [Instant System](https://instant-system.com/). It uses
the [News API](https://newsapi.org/) to fetch the latest news from various sources and display them
in a list-detail
layout. The user can click on a news item to view more details about it. This application does not
include an API key
for security reasons, so you will need to provide your own API key in order to run it. You only need
to provide the API
key once at first launch, and the application will store it in the
device's [SharedPreferences](https://developer.android.com/training/data-storage/shared-preferences)
for the next launches.

## How to use

After installing the application, you will be redirected to the onboarding flow at first launch. You
will need to get an API key from the [News API](https://newsapi.org/) website. You can then choose
between two authentication methods: "Scan QR Code" or "Enter API Key". If you want to use the "Scan
QR Code" method, you will need to generate a QR code using the API key from the News API website.
You can do it on this [website](https://qr-code-generator.com/). Just copy your API key from the
"Authentication" section of the News API website and paste it in the QR code generator page. You
will then be able to scan the QR code with the application to authenticate. You can also choose the
"Enter API Key" method and paste or type your API key in the text field.

## Structure

## Packages

The application is divided into three main packages:

- `api`: Contains the Retrofit API interface and the `NewsResponse` data class.
- `model`: Contains the `News` data class.
- `ui`: Contains all the composables of the application, the theme, and the `NewsViewModel`.

## Dependency Injection

The application does not use a dependency injection framework, but it uses the `ViewModel` and
the `Application` classes to globally provide the `NewsViewModel` and the `NewsClient` instances.
I have chosen to have only one ViewModel for the entire application given the small number of
features and screens.

## Navigation

The application uses androidx navigation with a single `NavHost` but a nested navigation graph to
separate the onboarding flow from the main flow.

### Onboarding

The onboarding flow consists of several screens sharing the same layout. The user will be able to
choose an authentication method between "Scan QR Code" and "Enter API Key". If the authentication
fails, the user will be redirected to an onboarding screen to try again.

This part of the application has been added because of the security issues related to storing the
API key in the application's code. With this authentication flow, only users with a valid API key
will be able to access the application. Another choice would have been to create a Firebase project
and have the API key stored in the Firebase project. Then fetching the news from a function in
Firebase
and forwarding it to the application. This would have been more secure, but it would have opened the
access to the news API to anyone using the application.

### Main Flow

The main navigation flow is a simple list-detail layout. The user will see a list of news items
fetched from the News API. When the user clicks on an item, they will be redirected to a detail
screen with more information about the news item. The list screen only displays the title, the
source and
an image if available. The detail screen displays in addition to this, the description (if
available)
and the content (if available). On the detail screen, the user can click on a button at the bottom
to open the news in their browser. I have chosen to open the news in the browser instead of
having an in-app browser for the sake of simplicity and user experience. As the user will likely
expect
to be able to open the news article in their default browser.

## To be improved

* The application suffers from some code duplication, especially for the transitions in
  the `AppNavHost`
  composable.
* There are also some magic strings for the routes that are a little bit dangerous. Creating a
  sealed class
  for the routes would be a better solution. The Android team also released a new solution for this
  use case a few months ago (see [this](https://youtu.be/AIC_OFQ1r3k?si=MirFPOIoP9_-d3cI) video from
  Philipp Lackner).
* A "pull-to-refresh" feature could be added to the list screen to allow the user to refresh the
  news list.
* The `QrCodeScanScreen` layout is not finished and does not look that great I think.
* A dependency injection framework could be used to simplify sharing the different instances
  of the application, especially the ViewModel.