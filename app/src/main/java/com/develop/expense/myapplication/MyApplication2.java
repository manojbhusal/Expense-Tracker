package com.develop.expense.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by Shailendra on 5/19/2017.
 */

public class MyApplication2 extends Application {


    public static AdView adView;
    public AdView bannerAdView;
    public static String showAd;
    public static InterstitialAd interstitialAd;
    public static AdRequest adRequest;
    @Override
        public void onCreate() {
            super.onCreate();



            showAd="NO";
//look for shared pref
            interstitialAd= new InterstitialAd(this);
            interstitialAd.setAdUnitId(getString(R.string.admob_interstitial_id));

            adView = new AdView(this);
            adView.setAdSize(AdSize.SMART_BANNER);
            adView.setAdUnitId(getString(R.string.banner_ad_unit_id));
            // Request for Ads
            AdRequest adRequest = new AdRequest.Builder().build();

            // Load ads into Banner Ads
            adView.loadAd(adRequest);



        }

        public static void loadAd(Context context, LinearLayout layAd) {


                if (adView.getParent() != null) {
                    ViewGroup tempVg = (ViewGroup) adView.getParent();
                    tempVg.removeView(adView);
                }

                layAd.addView(adView);




        }

}
