# Application Title: Water Tracker App

## Overview

This project is my final project for the course CSE 340 at the University of Washington. The water intake tracking app is designed to help users,
particularly those with disabilities that impact memory or attention, track their daily water intake and stay hydrated throughout the day by
using motion sensors to automatically log drinks. The intended audience could be anyone who wants to improve their water intake, but the app would
be particularly beneficial for those with memory or attention impairments. The goal of the app is to provide an easy-to-use and accessible tool for
tracking water intake and improving hydration habits.

## Design Document
Please find the project's design document linked [here](https://docs.google.com/document/d/1SXewYQ2fBW8axCmrANewo-UD6axlJ8AC_N7mVocQ_Ck/edit?usp=sharing)

## Configuration Settings

Enable data binding in build.gradle: 
```
android {
    ...
    
    buildFeatures {
        viewBinding true
    }
}
```

Default Config:

- CompileSDK: 33
- TargetSDK: 29
- MinSDK: 24

Add this library to dependencies:
```
dependencies {
    implementation 'com.google.code.gson:gson:2.8.5'
}
```

## API Key

No API Key required.

## Reading the Code: 

Please read through WaterEntry.java, WaterCardView.java, and WaterEntryAdapter.java first. You can also reference the 
 water_card_view.xml file while reading these 3.  These files
are the backbone behind the list view. Next, you should read HomeFragment.java. The remaining files can be 
read in any order.

## Other information
I used virtual sensors to test the accelerometer because I do not have an Android phone. 
