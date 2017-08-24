package cz.demoday.app.src;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import cz.demoday.R;
import cz.demoday.app.data.ApplicationState;
import cz.demoday.app.data.HttpAsyncTask;
import cz.demoday.app.entity.Transaction;
import cz.demoday.app.utils.ItemDividerDecoration;
import cz.demoday.app.utils.TransactionsAdapter;

/*
    Launching activity with a list of transaction
 */
public class MainActivity extends AppCompatActivity {
    private final String API_URL = "example.com/transactions/1";

    public RecyclerView mRecyclerView;
    public RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<Transaction> transactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.main_list_recycler);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new ItemDividerDecoration(this));

        //get transactions from REST API
        new HttpAsyncTask(this).execute(API_URL);

        mAdapter = new TransactionsAdapter(this, ApplicationState.transactions, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }


}
