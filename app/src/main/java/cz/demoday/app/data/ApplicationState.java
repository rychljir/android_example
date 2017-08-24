package cz.demoday.app.data;

import java.util.ArrayList;

import cz.demoday.app.entity.Transaction;

/**
 * Created by Speedy on 22. 8. 2017.
 */


/*
    Class with static variable for saving current state of app

 */
public class ApplicationState {
    //list of transactions showed in app
    public static ArrayList<Transaction> transactions = new ArrayList<>();
    //list of flags of favourite transactions
    public static boolean[] favourites;
}
