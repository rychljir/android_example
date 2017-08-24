package cz.demoday.app.data;

import android.app.Activity;
import android.os.AsyncTask;

import cz.demoday.app.src.MainActivity;
import cz.demoday.app.utils.TransactionsAdapter;

/**
 * Created by Speedy on 22. 8. 2017.
 */


/*
    Class for creating task forHTTP request to REST API
 */
public class HttpAsyncTask extends AsyncTask<String, Void, String> {
    MainActivity parent;

    public HttpAsyncTask(Activity parent){
        this.parent = (MainActivity)parent;
    }

    //create request in background
    @Override
    protected String doInBackground(String... urls) {
        ApplicationState.transactions = WebserviceClient.GET(urls[0]);
        return "";
    }

    //do after response is recieved
    @Override
    protected void onPostExecute(String result) {
        ((TransactionsAdapter)parent.mAdapter).setTransactions(ApplicationState.transactions);
        if(ApplicationState.favourites == null || ApplicationState.favourites.length!=ApplicationState.transactions.size()){
            ApplicationState.favourites = new boolean[ApplicationState.transactions.size()];
        }
        parent.mAdapter.notifyDataSetChanged();
    }
}