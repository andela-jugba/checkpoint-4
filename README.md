# checkpoint-4
The second Android based check point

This README contains steps necessary to get your application up and running.

### The Challenge ###
To implement an app that tracks the user’s movement and records any 
position where the user stands for more than 5 minutes. 
This will allow the user to know where most time is spent and to optimize their productivity.

### Implemented Solution ###

The app packages and functons are broken down as follows;

Controllers- contains splash screen, main and List activities.

Database- all data persistence classes to save and retrieve location data

Fragements- contain fragments mactich the theme of host activites

Models- the location model and helpers

Services- contains background services and helpers that track and save the user's location

ui_helpers-contains adapter classes used by in the fragment display.


### How do I get set up? ###

Summary of set up-clone repo and import into Android Studio and build gradle
How to run tests-change build variants in Andriod Studio to Unit test and select test to run
Deployment instructions-generate a sign apk or run in debug mode from android studio


## Credits ##
This app uses the following great libraries
* [Ranger](https://github.com/asantibanez/Ranger)
* [AwesomeSplash](https://github.com/ViksaaSkool/AwesomeSplash)
* [WheelIndicator](https://github.com/dlazaro66/WheelIndicatorView)
* [ExpandableRecycler](https://github.com/bignerdranch/expandable-recycler-view)


