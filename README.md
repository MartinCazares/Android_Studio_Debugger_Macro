# debugger_macro_AS(Linux/MasOSX)
Author: Martin Cazares

# Description
The idea of this project is to provide a cleaner way to add debugging code into your Android applications by having
special "tags" specifying what code must be commented out when doing a release build or uncommenting this code when switching back to debugging the app(Go to the examples section for more details).

# How to configure:
To have this feature running in your Android Studio project you need to follow these steps:
* Download the Project Example
* From the Project Example copy the file “debugger_script.sh” that is in the “app” folder and paste it into your project’s “app” folder.
* Go to your project’s “app/build.gradle” file and add the following code at the end of the file right after the dependenies:

        project.afterEvaluate{
            checkReleaseManifest.doLast {
                System.out.println("DEBUGGER MACRO MOVED TO RELEASE MODE")
                exec{
                    commandLine './debugger_script.sh', 'release'
                }
            }
            checkDebugManifest.doLast {
                System.out.println("DEBUGGER MACRO MOVED TO DEBUG MODE")
                exec{
                    commandLine './debugger_script.sh', 'debug'
                }
            }
        }


*That is it, you can start coding and don’t forget to add the tag “//<#DEBUG_AREA>” to start and “//</#DEBUG_AREA>” to end you Debugging Areas. Note the XML kind of syntax to start and end the areas, also notice that anything between these tags will be commented when in “release” build. Look at the examples below to understand how it works…


# Debug Area Examples:
A very common scenario is where you need to log pieces of information when processing some data as follows:

    private List<String> getNamesOfUsersNearMe(String zipCode){
        List<User> users = mBusinessLogic.getUsersByZipcode(zipCode);
        if(users == null || users.size() < 1){
            return null;
        }

        List<String> names = new ArrayList<String>();
        int totalUsers = users.size();
        for(int i = 0; i < totalUsers; i++){
            User user = users.get(i);
            String name = user.getName();
            names.add(name);
            //=========This piece of code is only for logging purposes...=========
            Log.e("LogUserInfo", "Name: " + name);
            Log.e("LogUserInfo", "Id: " + user.getId());
            Log.e("LogUserInfo", "Id: " + user.getDistance());
            //====================================================================
        }

        return names;
     }

However, so far, Android Tools don't provide a clean way to handle it and what we end up doing would be something like this:

    private List<String> getNamesOfUsersNearMe(String zipCode){
        List<User> users = mBusinessLogic.getUsersByZipcode(zipCode);
        if(users == null || users.size() < 1){
            return null;
        }

        List<String> names = new ArrayList<String>();
        int totalUsers = users.size();
        for(int i = 0; i < totalUsers; i++){
            User user = users.get(i);
            String name = user.getName();
            names.add(name);
            //This piece of code is only for logging purposes...
            if(DEBUG){
                Log.e("LogginUserInfo", "Name: " + name);
                Log.e("LogginUserInfo", "Id: " + user.getId());
                Log.e("LogginUserInfo", "Id: " + user.getDistance());
            }
        }

        return names;
    }

Even tho we are avoiding that code to be executed by using flags, the code is still there and the "if" statement is being evaluated and it could be somehow misleading at times or even have an impact in performance when used in long for loops, so, the whole idea of this project is to add special “tags” as follows to debug your code…

Debug Area Declaration Example:

    private List<String> getNamesOfUsersNearMe(String zipCode){
        List<User> users = mBusinessLogic.getUsersByZipcode(zipCode);
        if(users == null || users.size() < 1){
            return null;
        }

        List<String> names = new ArrayList<String>();
        int totalUsers = users.size();
        for(int i = 0; i < totalUsers; i++){
            User user = users.get(i);
            String name = user.getName();
            names.add(name);
            //<#DEBUG_AREA>
            Log.e("LogginUserInfo", "Name: " + name);
            Log.e("LogginUserInfo", "Id: " + user.getId());
            Log.e("LogginUserInfo", "Id: " + user.getDistance());
            //</#DEBUG_AREA>
        }
        return names;
    }

Everything within the tags:

    //<#DEBUG_AREA>
    …
    //</#DEBUG_AREA>

Will be commented out when changing your Build Variants to “release” as follows:

Debug Area "release" version:

    private List<String> getNamesOfUsersNearMe(String zipCode){
        List<User> users = mBusinessLogic.getUsersByZipcode(zipCode);
        if(users == null || users.size() < 1){
            return null;
        }

        List<String> names = new ArrayList<String>();
        int totalUsers = users.size();
        for(int i = 0; i < totalUsers; i++){
            User user = users.get(i);
            String name = user.getName();
            names.add(name);
            /*<#DEBUG_OFF>
                Log.e("LogginUserInfo", "Name: " + name);
                Log.e("LogginUserInfo", "Id: " + user.getId());
                Log.e("LogginUserInfo", "Id: " + user.getDistance());
            </#DEBUG_OFF>*/
        }

        return names;
    }

If you change your “Build Variants” back to “debug” you would have the code uncommented again exactly the same way it shows in the “Debug Area Declaration Example".

#Single Line of Log
There are also scenarios where you might want to add one single log into your code, and for these cases we have a wrapper Log class (ASDebuggerMacroLog, get it from the example) that will be commented when you switch to release mode, so, instead of having something like this using Debug Areas:

    if(users == null || users.size() < 1){
        //<#DEBUG_AREA>
        Log.e("LogUserInfo", "There's no users available near me...");
        //</#DEBUG_AREA>
        return null;
    }

You can have something cleaner like this(Proper way to use Single Log):

    if(users == null || users.size() < 1){
        ASDebuggerMacroLog.e("LogUserInfo", "There's no users available near me...");
        return null;
    }

Where the line of code used by the class "ASDebuggerMacroLog" will be commented. Note: You MUST use this statement by it self in a single line and you have to be careful when using it because it will comment the whole line and if not used properly it might break your code in a case like the one below:

    if(validation){
        ASDebuggerMacroLog.e("LogUserInfo", "Single Log");}

Since the line "ASDebuggerMacroLog.e("LogUserInfo", "Single Log");}" will be commented, the last curly brace will be commented and it will break the syntax of your code. Remember to use it always by it self as shown in the(Proper way to use Single Log).


Don't forget to download the working example for a better understanding in a real app, I hope this tool is as helpful for you as it has been for me…

Regards!
