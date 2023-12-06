# Project 10 - Gesture Playground and Sensors
## Description
The Gesture Playground is an Android application that allows users to interact with a canvas using gestures. Users can draw on the canvas, move a ball around, and view logs of their gesture activities.  Additionally, on the home page, the app displays the ambient temperature and relative humidity from the device's sensors, as well as the user's location with location services. 

## Features
- Location: Gets the users current location and displays it's City and State
- Sensors: Displays ambient temperature and relative humidity
- Canvas Interaction: Users can draw on the canvas and move a ball with their gestures.
- Gesture Logging: Logs of performed gestures are displayed in a scrollable list.
- Portrait and Landscape Modes: The app supports both portrait and landscape modes, adjusting the layout accordingly.

  ## Discussion
  I struggled with uploading my app to Google Play, as I was unfamiliar with building APKs.  Additionally, I struggled with live updating sensors, but learned I could use a SharedState object to communicate changes throughout the app without a View Model.

## Video Example

<img src= 'https://github.com/kenna-edwards55/project10/blob/master/Project10Demo.gif' width='70%'>

## Google Play Testing Track
- Currently still in review 

<img src = 'https://github.com/kenna-edwards55/project10/blob/master/Google%20Play.png' width = '50%'>
