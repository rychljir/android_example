package cz.demoday.app.utils;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cz.demoday.R;
import cz.demoday.app.entity.Transaction;
import cz.demoday.app.src.DetailFragment;
import cz.demoday.app.src.MainActivity;

/**
 * Created by Speedy on 22. 8. 2017.
 */


/*
    Adapter for RecycleView which represents the list of transactions

 */
public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.ViewHolder> {
    private List<Transaction> mTransactions;
    private Context mContext;
    private DetailOnClickListener mOnClickListener;
    private MainActivity parent;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, type, amount;
        public ImageView image;
        public ImageButton detail_btn;

        public ViewHolder(View itemView) {
            super(itemView);

            this.name = (TextView) itemView.findViewById(R.id.transaction_name);
            this.type = (TextView) itemView.findViewById(R.id.transaction_type);
            ;
            this.amount = (TextView) itemView.findViewById(R.id.transaction_amount);
            ;
            this.image = (ImageView) itemView.findViewById(R.id.transaction_img);
            this.detail_btn = (ImageButton) itemView.findViewById(R.id.transaction_detail_btn);
        }
    }

    public TransactionsAdapter(Context context, List<Transaction> transactions, Activity activity) {
        mContext = context;
        mTransactions = transactions;
        mOnClickListener = new DetailOnClickListener();
        parent = (MainActivity) activity;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public TransactionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);


        View contactView = inflater.inflate(R.layout.item_transaction, parent, false);
        contactView.setOnClickListener(mOnClickListener);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(TransactionsAdapter.ViewHolder viewHolder, int position) {
        Transaction transaction = mTransactions.get(position);

        TextView textView = viewHolder.name;
        textView.setText(transaction.getName());

        textView = viewHolder.type;
        textView.setText(transaction.getType());

        textView = viewHolder.amount;
        textView.setText(NumberFormat.getNumberInstance(Locale.US).format(transaction.getAmount()) + " $");

        ImageView imageView = viewHolder.image;
        imageView.setImageDrawable(getImage(transaction.getType()));

        ImageButton button = viewHolder.detail_btn;
    }

    private Drawable getImage(String type) {
        switch (type) {
            case "house":
                return getContext().getResources().getDrawable(R.drawable.home_icon);
            case "bank":
                return getContext().getResources().getDrawable(R.drawable.bank_icon);
            case "gas":
                return getContext().getResources().getDrawable(R.drawable.gas_icon);
            case "kids":
                return getContext().getResources().getDrawable(R.drawable.kids_icon);
            default:
                return getContext().getResources().getDrawable(R.drawable.bank_icon);
        }
    }


    @Override
    public int getItemCount() {
        return mTransactions.size();
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        mTransactions = transactions;
    }

    //Listener for showing a fragment with details of a transaction
    class DetailOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(final View v) {
            int itemPosition = parent.mRecyclerView.getChildLayoutPosition(v);
            Log.d("Adapter", Integer.toString(itemPosition));

            DetailFragment df = new DetailFragment();
            Bundle args = new Bundle();
            args.putInt("index", itemPosition);
            df.setArguments(args);

            FragmentManager fragmentManager = parent.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.animator.slide_in, R.animator.slide_out, R.animator.slide_in, R.animator.slide_out);
            fragmentTransaction.replace(R.id.detail_fragment,
                    df).addToBackStack(null);
            fragmentTransaction.commit();

        }
    }
}
