package com.niwarthana.smartchef.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.niwarthana.smartchef.R;

import java.text.NumberFormat;
import java.util.Locale;

public class Utils {

    private static Utils utils;
    private static AlertDialog dialog;

    public static Utils getInstance() {

        if (utils == null) {
            utils = new Utils();
        }
        return utils;
    }

    public Button showSuccessPopup(final Activity activity, String title, String msg) {
        Button okButton = null;
        try {
            if (!activity.isFinishing()) {
                LayoutInflater inflater = activity.getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.popup_notification_succes, null);
                okButton = dialoglayout.findViewById(R.id.btn_close);
                TextView txtTitle = dialoglayout.findViewById(R.id.txt_title);
                TextView txtMsg = dialoglayout.findViewById(R.id.txt_msg);
                txtTitle.setText(title);
                txtMsg.setText(msg);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!activity.isFinishing() && dialog != null) {
                            dialog.dismiss();
                        }
                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setView(dialoglayout);
                dialog = builder.show();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return okButton;
    }

    public Button showErrorPopup(final Activity activity, String title, String msg) {
        Button okButton = null;
        try {
            if (!activity.isFinishing()) {
                LayoutInflater inflater = activity.getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.popup_notification_error, null);
                okButton = dialoglayout.findViewById(R.id.btn_close);
                TextView txtTitle = dialoglayout.findViewById(R.id.txt_title);
                TextView txtMsg = dialoglayout.findViewById(R.id.txt_msg);
                txtTitle.setText(title);
                txtMsg.setText(msg);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!activity.isFinishing() && dialog != null) {
                            dialog.dismiss();
                        }
                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setView(dialoglayout);

                dialog = builder.show();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return okButton;
    }
    public void hidePopup(Activity activity) {
        if (!activity.isFinishing() && dialog != null) {
            dialog.dismiss();
        }
    }
    public String formatPrice(double price) {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.UK);
        return format.format(price);

    }
}
