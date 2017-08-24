package cz.demoday.app.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.demoday.app.entity.Transaction;

/**
 * Created by Speedy on 22. 8. 2017.
 */

/*
    Class for parsing JSON object to class Transaction
 */
public class TransactionParser {

    public TransactionParser() {
    }

    //return list of transactions parsed from JSON input
    public static ArrayList<Transaction> getTransactions(String data) throws JSONException {
        ArrayList<Transaction> result = new ArrayList<>();
        JSONObject obj = new JSONObject(data);
        JSONArray arr = obj.getJSONArray("transactions");
        for (int i = 0; i < arr.length(); i++)
        {
            String name = arr.getJSONObject(i).getString("description");
            String type = arr.getJSONObject(i).getString("type");
            String date= arr.getJSONObject(i).getString("date");
            String amountStr = arr.getJSONObject(i).getString("amount");
            Float amount = Float.parseFloat(amountStr);
            result.add(new Transaction(name, type, amount, date));
        }
        return result;
    }
}
