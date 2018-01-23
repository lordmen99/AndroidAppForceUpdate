package com.studyapps.jsoup;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class ForceUpdate {

    private Context context;
    private double versionName= Double.valueOf(BuildConfig.VERSION_NAME);
    private double playStoreVersionName;
    private boolean cancelable = false;
    private boolean cancelableTouchOutSide = false;
    private CharSequence titleText = "Update Available";
    private String messageText = "An Update is Available With New Features";

    public ForceUpdate(Context context){
        this.context = context;
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    public void setCanceledOnTouchOutside(boolean cancelableTouchOutSide ) {
        this.cancelableTouchOutSide = cancelableTouchOutSide;
    }

    public void setTitle(@Nullable CharSequence title) {
        this.titleText = title;
    }

    public void setMessage(String message) {
        this.messageText = message;
    }


    public void build(){

        LoadUrl loadUrl = new LoadUrl();
        loadUrl.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadUrl extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                String url1 = "https://play.google.com/store/apps/details?id=com.studyapps.easystorysaver";
                Document doc = Jsoup.connect(url1)
                        .userAgent("Mozilla/4.0")
                        .referrer("https://www.google.com")
                        .timeout(30000)
                        .get();
                String ver = doc.select("div[itemprop=softwareVersion]").text();
                playStoreVersionName = Double.valueOf(ver);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (playStoreVersionName>versionName){
                showUpdateDialog();
            }
        }
    }

    private void showUpdateDialog() {

        final Dialog updateDialog = new Dialog(context);
        updateDialog.setContentView(R.layout.update_dialog);
        updateDialog.setCanceledOnTouchOutside(cancelableTouchOutSide);

        TextView title = updateDialog.findViewById(R.id.title);
        TextView message = updateDialog.findViewById(R.id.message);
        ImageView updateIcon  = updateDialog.findViewById(R.id.update_icon);
        updateIcon.setImageResource(android.R.drawable.ic_dialog_info);

        title.setText(titleText);
        message.setText(messageText);

        GradientDrawable updateShape = new GradientDrawable();
        updateShape.setColor(context.getResources().getColor(android.R.color.holo_green_dark));
        updateShape.setCornerRadii(new float[]{10f,10f,10f,10f,10f,10f,10f,10f});

        Button updateButton = updateDialog.findViewById(R.id.updateButton);
        updateButton.setBackground(updateShape);

        GradientDrawable ignoreShape = new GradientDrawable();
        ignoreShape.setColor(context.getResources().getColor(android.R.color.holo_red_dark));
        ignoreShape.setCornerRadii(new float[]{10f,10f,10f,10f,10f,10f,10f,10f});

        Button ignoreButton = updateDialog.findViewById(R.id.ignoreButton);
        ignoreButton.setBackground(ignoreShape);


        updateDialog.show();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id="+context.getPackageName()));
                context.startActivity(intent);
            }
        });

        ignoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDialog.dismiss();
            }
        });

    }
}
