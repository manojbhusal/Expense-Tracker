package com.develop.expense.myapplication;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Shailendra on 11/30/2016.
 */
public class CustomAdapter extends ArrayAdapter {

    private TextView itemName;
    private ImageView itemImage;
    public List mlist;
    public LinearLayout cataLayout;


    public CustomAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        mlist = objects;

    }


    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        LayoutInflater li = LayoutInflater.from(getContext());
        final View customView = li.inflate(R.layout.customitem, parent, false);
        cataLayout = (LinearLayout) customView.findViewById(R.id.cataLayout);

        itemName = (TextView) customView.findViewById(R.id.itemText);
        itemImage = (ImageView) customView.findViewById(R.id.itemImage);
        itemName.setText(mlist.get(position).toString());
        Log.v("binaya", mlist.get(position).toString());
        switch (mlist.get(position).toString()) {

            case "Medical":
                itemImage.setImageResource(R.drawable.medical);
                break;
            case "Clothing":
                itemImage.setImageResource(R.drawable.clothing);
                break;
            case "Gadget":
                itemImage.setImageResource(R.drawable.gadget);
                break;
            case "Food":
                itemImage.setImageResource(R.drawable.pizza);
                break;
            case "Education":
                itemImage.setImageResource(R.drawable.education);
                break;
            case "Personal":
                itemImage.setImageResource(R.drawable.personal);
                break;
            case "Social":
                itemImage.setImageResource(R.drawable.social);
                break;
            case "Fun":
                itemImage.setImageResource(R.drawable.fun);
                break;
            case "Household":
                itemImage.setImageResource(R.drawable.household);
                break;
            case "Bonus":
                itemImage.setImageResource(R.drawable.bonus);
                break;
            case "Donation":
                itemImage.setImageResource(R.drawable.donation);
                break;
            case "Profit":
                itemImage.setImageResource(R.drawable.profit);
                break;
            case "Salary":
                itemImage.setImageResource(R.drawable.salary);
                break;
            case "Business":
                itemImage.setImageResource(R.drawable.business);
                break;
            case "Extras":
                itemImage.setImageResource(R.drawable.extras);
                break;


            default:
                itemImage.setImageResource(R.drawable.pizza);
break;

        }

//        cataLayout.setOnLongClickListener(
//                new View.OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View v) {
//
//                        AlertDialog.Builder delBuilder= new AlertDialog.Builder(getContext());//put proper context
//                        delBuilder.setCancelable(true);
//                        delBuilder.setMessage("Delete");
//                        delBuilder.show();
//                        return false;
//                    }
//                }
//        );

        return customView;
    }
}
