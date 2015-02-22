# debugger_macro_AS(Linux/MasOSX)
Author: Martin Cazares

#Description
The idea of this project is to provide a cleaner way to add debugging code into Android applications by having
special "tags" specifying what code must be commented out when doing a release build or uncommenting this code when switching back to debugging the app(Go to the examples section for more details).

#How to configure:
To have this feature running in your Android Studio project you need to follow these steps:
- Download the Project Example
- From the Project Example copy the file “debugger_script.sh” that is in the “app” folder and paste it into your project’s “app” folder.
- Go to your project’s “app/build.gradle” file and add the following code at the end of the file right after the dependenies:

    ```javascript
     project.afterEvaluate{
        checkReleaseManifest.doLast {
            System.out.println("DO RELEASE THINGS")
            exec{
                commandLine './debugger_script.sh', 'release'
            }
        }
        checkDebugManifest.doLast {
            System.out.println("DO DEBUG THINGS")
            exec{
                commandLine './debugger_script.sh', 'debug'
            }
        }
    }```
    


-That is it, you can start coding and don’t forget to add the tag “//<#DEBUG>” to start a “Debugging Area” and “//</#DEBUG>” to end the “Debugging Area”. Note that anything between these tags will be commented when in “release” build. Look at the examples below to understand how it works…


#Examples:
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
            Log.e("LogginUserInfo", "Name: " + name);
            Log.e("LogginUserInfo", "Id: " + user.getId());
            Log.e("LogginUserInfo", "Id: " + user.getDistance());
            //====================================================================
        }

        return names;
     }

However, so far, Android Tools don't provide a clean way to handle it, what we end up doing would be something like this:

Example1:

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

Even tho we are avoiding that code to be executed by using flags, the code is still being evaluated and it could be somehow misleading at times, so, the whole idea for this project is to add special “tags” as follows to debug your code…

Example2:

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
            //<#DEBUG>
            Log.e("LogginUserInfo", "Name: " + name);
            Log.e("LogginUserInfo", "Id: " + user.getId());
            Log.e("LogginUserInfo", "Id: " + user.getDistance());
            //</#DEBUG>
        }

        return names;
    }

Everything within the tags:
//<#DEBUG>
…
//</#DEBUG>

Will be commented out when changing your Build Variants to “release” as follows:

Example3:

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

If you change your “Build Variants” back to “debug” you would have the code uncommented again exactly the same way it shows in the “Example2”.


So, Hope is as helpful for you as it has been for me…

Regards!