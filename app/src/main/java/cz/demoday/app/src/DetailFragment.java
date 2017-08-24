package cz.demoday.app.src;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cz.demoday.R;
import cz.demoday.app.data.ApplicationState;
import cz.demoday.app.entity.Transaction;

;



/**
 * Created by Speedy on 22. 8. 2017.
 */
/*

    Fragment which displays the detail of a transaction

 */
public class DetailFragment extends Fragment {
    protected View view;
    private ToggleButton toggleButton;
    //index of transaction in list
    private int index;
    private AlertDialog alert;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail, container, false);

        index = getArguments().getInt("index", 0);

        //get transaction based on its index
        Transaction t = ApplicationState.transactions.get(index);
        try {
            //parse Transaction to views
            initData(t);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        view.findViewById(R.id.backpressed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        //handle Favourite button
        toggleButton = (ToggleButton) view.findViewById(R.id.detail_favourite);
        if (ApplicationState.favourites[index]) {
            toggleButton.setChecked(true);
            toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), android.R.drawable.btn_star_big_on));
        } else {
            toggleButton.setChecked(false);
            toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), android.R.drawable.btn_star_big_off));
        }


        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), android.R.drawable.btn_star_big_on));
                    ApplicationState.favourites[index] = true;
                    sendNotification();
                    showPopUp();
                } else {
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), android.R.drawable.btn_star_big_off));
                    ApplicationState.favourites[index] = false;
                }
            }
        });

        return view;
    }

    private void sendNotification(){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getActivity())
                        .setSmallIcon(R.drawable.bank_icon)
                        .setContentTitle("Fkin narcist")
                        .setContentText("You narcistic piece of shit.!");

        NotificationManager mNotificationManager =
                (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(001, mBuilder.build());
    }

    private void showPopUp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("You narcistic piece of shit!")
                .setCancelable(false)
                .setPositiveButton("Im sorry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        alert.dismiss();
                    }
                });
        alert = builder.create();
        alert.show();
    }

    //parse data from object Transaction to views
    private void initData(Transaction t) throws ParseException {
        ((TextView) view.findViewById(R.id.detail_name)).setText(t.getName());
        ((TextView) view.findViewById(R.id.detail_type)).setText(t.getType());
        ((TextView) view.findViewById(R.id.detail_amount)).setText(Float.toString(t.getAmount()) + "$");

        //prase ISO8061 date
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String date = t.getDate();
        Date parsedDate = new Date();
        try {
            parsedDate = df1.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        date = parsedDate.getDay() + "." + parsedDate.getMonth() + "." + parsedDate.getYear();

        ((TextView) view.findViewById(R.id.detail_date)).setText(date);
        ((ImageView) view.findViewById(R.id.detail_img)).setImageDrawable(getImage(t.getType()));
    }

    //get image based on type of transaction
    private Drawable getImage(String type) {
        switch (type) {
            case "house":
                return getActivity().getResources().getDrawable(R.drawable.home_icon);
            case "bank":
                return getActivity().getResources().getDrawable(R.drawable.bank_icon);
            case "gas":
                return getActivity().getResources().getDrawable(R.drawable.gas_icon);
            case "kids":
                return getActivity().getResources().getDrawable(R.drawable.kids_icon);
            default:
                return getActivity().getResources().getDrawable(R.drawable.bank_icon);
        }
    }
}

