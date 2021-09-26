# PracticeApp

#### Helping The Newbies
This app is made with a purpose to help newbies understand core concepts of Android Development.
There are very limited and not so clear resources available out there, Hence I've decided to fix it.

##### TOPIC IMPLEMENTED
Android based : 
    1) Background service
    2) Foreground service
    3) Broadcast Receiver
    4) WorkManager
    5) ViewBinding
    6) ViewModel
    7) Fragment Transactions
    

Non Android based :
    1) Lambda functions
    2) Higher Order functions
    3) Returning functions
    4) Higher Order functions which returns a function (almost the same thing)

#### WorkFlow
As soon the onCreateMethod is triggered, following things are done : 
    1) MyService (background service) is fired which gets killed after 2secs.
    2) MyForegroundService is created (if not already running) and keeps running in never ending loop.
    3) This foreground service then listens for network_changed broadcasts and shows a Toast.
    4) handleOnClickListener() implements listener for login button. This listener fires up a broadcast to the same
        MyBroadcastReceiver. Also this listener replaces the current fragment with FragB and adds it to backstack.

When ever the app is launched for the first time, A 'Unique periodic work' is enqueued in the MyWorker.
This periodic work shows notification every 15 minutes stating that workManager has been FiredUp
