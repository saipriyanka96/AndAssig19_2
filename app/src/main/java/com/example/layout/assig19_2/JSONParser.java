package com.example.layout.assig19_2;
//Package objects contain version information about the implementation and specification of a Java package
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Pri on 10/25/2017.
 */

public class JSONParser {
    private static final String TAG = JSONParser.class.getSimpleName();

    public JSONParser() {
    }
//is called to get the json from url
    public String makeServiceCall(String reqUrl){
        String response = null;//response is equal to null
        try {
            URL url = new URL(reqUrl);
            //url object is created
            //Returns a URLConnection instance that represents a connection to the remote object referred to by the URL.
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
//Set the method for the URL reques
            httpURLConnection.setRequestMethod("GET");
            // read the response
            InputStream inputStream=new BufferedInputStream(httpURLConnection.getInputStream());

            response = convertStreamToString(inputStream);
        } catch (MalformedURLException e) {
            //Thrown to indicate that a malformed URL has occurred. Either no legal protocol could be found in a specification string or the string could not be parsed.
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            //Thrown to indicate that there is an error in the underlying protocol, such as a TCP error.
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            //Signals that an I/O exception of some sort has occurred.
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            //
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    private String convertStreamToString(InputStream inputS) {
        //inputstream reads the file
        //used to read the text from a character-based input stream.
        BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputS));
        StringBuilder stringBuilder = new StringBuilder();
//A mutable sequence of characters.
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                //Reads a line of text which is not equal to null

                stringBuilder.append(line).append('\n');
                //Appends the string representation of the char argument to this sequence.
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputS.close();
                //closes the stream
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return stringBuilder.toString();
        //returns to the string
    }


}
