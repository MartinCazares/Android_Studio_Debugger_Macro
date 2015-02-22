package doepiccoding.com.macrodebug;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;
import java.util.List;

import doepiccoding.com.macrodebug.util.ASDebuggerMacroLog;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Start test...
        getNamesOfUsersNearMe("xxxx");
    }

    private List<String> getNamesOfUsersNearMe(String zipCode){
        List<User> users = mBusinessLogic.getUsersByZipcode(zipCode);
        if(users == null || users.size() < 1){
            ASDebuggerMacroLog.e("SingleLineLogger", "There's no users available near me...");
            return null;
        }

        List<String> names = new ArrayList<String>();
        int totalUsers = users.size();
        for(int i = 0; i < totalUsers; i++){
            User user = users.get(i);
            String name = user.getName();
            names.add(name);
            //<#DEBUG_AREA>
                android.util.Log.e("LogUserInfo", "Name: " + name);
                android.util.Log.e("LogUserInfo", "Id: " + user.getId());
                android.util.Log.e("LogUserInfo", "Id: " + user.getDistance());
            //</#DEBUG_AREA>
        }

        ASDebuggerMacroLog.e("LogUserInfo", "Returning " + names.size() + " names.");
        return names;
    }

    //MOCK Objects just to simulate a debug scenario...
    private A mBusinessLogic = new A();
    private class A{
        public List<User> getUsersByZipcode(String zipcode){
            List<User> users = new ArrayList<User>();
            users.add(new User().setName("Mary"));
            users.add(new User().setName("Jane"));
            users.add(new User().setName("High"));
            return users;
        }
    }
    private class User{
        private String name;
        public String getName(){
            return name;
        }
        public String getId(){
            return "";
        }
        public String getDistance(){
            return null;
        }
        public User setName(String name){
            this.name = name;
            return this;
        }
    }
}
