# DeveloperBee Task: Accessibility Service App

This Android app demonstrates the use of an accessibility service to detect a specific search word in the Chrome browser and show a notification to close the tab containing that word.

## Features

1. **Accessibility Service Integration**: The app utilizes an accessibility service to monitor text changes in the Chrome browser.

2. **Target Word Detection**: When the user performs a search in Chrome, the app detects if the search query contains a specific target word.

3. **Notification on Detection**: If the target word is detected in the search query, a notification is shown, prompting the user to close the tab.

4. **Settings Activity**: The app includes a settings activity where the user can set their preferred target word. If not set, a default target word "hi" is used.

5. **Shared Preferences**: The user's preferred target word is saved using shared preferences, allowing it to persist across app launches.

6. **Notification Permission Check**: On app startup, the app checks if notification permissions are granted. If not, the user is prompted to grant the permission.

7. **App Settings Navigation**: The app provides a button to navigate to the app settings to grant notification permission if needed.

## How to Use

1. **App Installation**: Install the app on an Android device or emulator.

2. **Open Settings**: Launch the app and open the settings activity.

3. **Set Target Word**: In the settings activity, set your preferred target word for detection. If left empty, the default word "hi" will be used.

4. **Notification Permission**: The app will check if notification permissions are granted. If not, a dialog will prompt you to grant the permission.

5. **Chrome Search**: Open the Chrome browser and perform a search using the Chrome's search bar.

6. **Notification**: If your search query contains the target word, a notification will be shown, asking you to close the tab.

## Note

- This app uses an accessibility service for monitoring text changes in the Chrome browser. Make sure to grant the necessary permissions when prompted.

- The notification permission is required for showing notifications. Grant the permission through the app settings if prompted.


## Acknowledgements

This app was developed as part of a task for DeveloperBee.
