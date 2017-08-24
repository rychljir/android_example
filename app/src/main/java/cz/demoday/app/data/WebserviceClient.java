package cz.demoday.app.data;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import cz.demoday.app.entity.Transaction;

/**
 * Created by Speedy on 22. 8. 2017.
 */

/*
    Class for creating HTTP request to REST API

 */
public class WebserviceClient {

    public WebserviceClient() {
    }

    //returns list of Transaction parsed from recieved JSON
    public static ArrayList<Transaction> GET(String url){
        String result = "";
        try {

            HttpURLConnection urlConnection = null;
            URL restURL = new URL("https://stub.bbeight.synetech.cz/v1/customers/3");
            urlConnection = (HttpURLConnection) restURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */ );
            urlConnection.setConnectTimeout(15000 /* milliseconds */ );
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(restURL.openStream()));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            return TransactionParser.getTransactions(sb.toString());

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return new ArrayList<Transaction>();
    }
}
