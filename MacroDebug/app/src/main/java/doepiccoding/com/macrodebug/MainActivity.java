package doepiccoding.com.macrodebug;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

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
            </#DEBUG_OFF>*/
                Log.e("LogginUserInfo", "Id: " + user.getId());
                Log.e("LogginUserInfo", "Id: " + user.getDistance());

        }

        return names;
    }

    //MOCK Objects just to simulate a debug scenario...
    private A mBusinessLogic;
    private class A{
        public List<User> getUsersByZipcode(String zipcode){
            return null;
        }
    }
    private class User{
        public String getName(){
            return "";
        }
        public String getId(){
            return "";
        }
        public String getDistance(){
            return null;
        }
    }
}
