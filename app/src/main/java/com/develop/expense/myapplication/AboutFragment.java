package com.develop.expense.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Shailendra on 5/9/2017.
 */

public class AboutFragment extends android.app.DialogFragment {
LayoutInflater inflater;
    TextView icons8,licence;
    private TextView privacy;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
View v=null;
        inflater=(LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v=inflater.inflate(R.layout.fragment_about,null);
icons8=(TextView)v.findViewById(R.id.icons8);
        licence=(TextView)v.findViewById(R.id.licence);

        icons8.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      Intent intent= new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse("https://icons8.com"));
                        startActivity(intent);
                    }
                }
        );

        licence.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
Intent intent= new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setType(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse("https://creativecommons.org/licenses/by-nd/3.0/"));
                        startActivity(intent);
                    }
                }
        );


        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.setView(v);
        return builder.create();
    }
}
