package com.example.layout.assig19_2;
//Package objects contain version information about the implementation and specification of a Java package

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    //public keyword is used in the declaration of a class,method or field;public classes,method and fields can be accessed by the members of any class.
//extends is for extending a class. implements is for implementing an interface
//AppCompatActivity is a class from e v7 appcompat library. This is a compatibility library that back ports some features of recent versions of
// Android to older devices.

    private String TAG = MainActivity.class.getSimpleName();
    //this tag returns the simple name of the underlying class as given in the source code
    private ProgressDialog pDialog;
    private ListView lv;
    ArrayList<HashMap<String, String>> movieList;
    //hashmap of string in the form of arraylist
    //JSON Node Names
    private static final String TAG_NAME = "name";
    private static final String TAG_VOTES = "vote_count";
    private static final String TAG_ID = "id";
    private static final String TAG_RESULTS = "results";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Variables, methods, and constructors, which are declared protected in a superclass can be accessed only by the subclasses
        // in other package or any class within the package of the protected members class.
        //void is a Java keyword.  Used at method declaration and definition to specify that the method does not return any type,
        // the method returns void.
        //onCreate Called when the activity is first created. This is where you should do all of your normal static set up: create views,
        // bind data to lists, etc. This method also provides you with a Bundle containing the activity's previously frozen state,
        // if there was one.Always followed by onStart().
        //Bundle is most often used for passing data through various Activities.
// This callback is called only when there is a saved instance previously saved using onSaveInstanceState().
// We restore some state in onCreate() while we can optionally restore other state here, possibly usable after onStart() has
// completed.The savedInstanceState Bundle is same as the one used in onCreate().
        // call the super class onCreate to complete the creation of activity like the view hierarchy
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //R means Resource
        //layout means design
        //  main is the xml you have created under res->layout->main.xml
        //  Whenever you want to change your current Look of an Activity or when you move from one Activity to another .
        // The other Activity must have a design to show . So we call this method in onCreate and this is the second statement to set
        // the design
        ///findViewById:A user interface element that displays text to the user.
        movieList = new ArrayList<>();
        //arraylist object which contains the movies lisr
        lv = (ListView) findViewById(R.id.list);
        new GetMovieLists().execute();
        //get the movie list and execute the list
    }

    private class GetMovieLists extends AsyncTask<Void, Void, Void> {
        //AsyncTask must be subclassed to be used. The subclass will override at least one method (doInBackground(Params...)), and most often will override a second one (onPostExecute(Result).)
        /**Params, the type of the parameters sent to the task upon execution.
         Progress, the type of the progress units published during the background computation.
         Result, the type of the result of the background computation.**/
        @Override
        protected void onPreExecute() {
            //Runs on the UI thread before doInBackground(Params...).
            //This method must be called from the main thread of your app.
            //The java super keyword is used to refer the immediate parent class object.
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            //this will send the value to current activity
            pDialog.setMessage("Please wait...");
            //sets the message
            pDialog.setCancelable(false);
            //Sets whether this dialog is cancelable with the BACK key.
            pDialog.show();
            //shows the dialog
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            //Override this method to perform a computation on a background thread.
            //Parameters
           // params	Params: The parameters of the task.Returns
           // Result	A result, defined by the subclass of this task.
            //url that we need to open
            String url = "http://api.themoviedb.org/3/tv/top_rated?api_key=8496be0b2149805afa458ab8ec27560c";
            JSONParser jsonParser = new JSONParser();
            //It is an independent data exchange format and is the best alternative for XML
            //jsonParser object created
            // Making a request to url and getting response
            String jsonStr = jsonParser.makeServiceCall(url);
/**Send an ERROR log message.

 Parameters
 tag	String: Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
 msg	String: The message you would like logged.
**/
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                //if json string is not equal to  null
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    //create a new jsonstring
                    Log.e(TAG, "object of json" + jsonObj);
                    Log.e(TAG, "object of json page" + jsonObj);
//here we get the result of json array
                    // Getting JSON Array node
                    JSONArray jsonArray_results = jsonObj.getJSONArray(TAG_RESULTS);
                    Log.e(TAG, "object of json results" + jsonArray_results);
// // looping through All Contacts
                    for (int i = 0; i <= jsonArray_results.length(); i++) {
                        //get the json aobect with index i
                        JSONObject jsonObject = jsonArray_results.getJSONObject(i);
                        Log.e(TAG, "object of json in for loop" + jsonObject);
                        // node is JSON Object
                        String movie_name = jsonObject.getString(TAG_NAME);
                        String id = jsonObject.getString(TAG_ID);
                        String vote_count = jsonObject.getString(TAG_VOTES);
//hashmap values that which contains the string values
                        HashMap<String, String> results = new HashMap<>();
                        results.put(TAG_ID, id);
                        //put(K key, V value) method is used to associate the specified value with the specified key in this map.
                        Log.e(TAG, "id" + id);//get the id
                        results.put(TAG_NAME, movie_name);
                        Log.e(TAG, "movie name" + movie_name);
                        results.put(TAG_VOTES, vote_count);
                        Log.e(TAG, "vote count" + vote_count);

                        movieList.add(results);
                        //add the results into movies
                    }
                } catch (JSONException e) {
                    //Thrown to indicate a problem with the JSON AP
                    //method prints this throwable and its backtrace to the standard error stream
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            /**Runs on the UI thread after doInBackground(Params...). The specified result is the value returned by doInBackground(Params...).

             This method won't be invoked if the task was cancelled.

             This method must be called from the main thread of your app.

             Parameters
             result	Result: The result of the operation computed by doInBackground(Params...).
             **/
            super.onPostExecute(result);

            // Here Dismiss the progress dialog
            if (pDialog.isShowing())
                //Whether the dialog is currently showing.
                pDialog.dismiss();
/**Dismiss a dialog that was previously shown via showDialog(int).

 Parameters
 id	int: The id of the managed dialog.
 **/
            //Here Updating parsed JSON data into ListView

            ListAdapter adapter = new SimpleAdapter(MainActivity.this, movieList, R.layout.item_list, new String[]{"name", "id",
                    "vote_count"}, new int[]{R.id.name,
                    R.id.vote_count, R.id.id});
            lv.setAdapter(adapter);
            //sets the adapter
        }
    }
}