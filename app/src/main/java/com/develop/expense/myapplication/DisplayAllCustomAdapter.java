package com.develop.expense.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import Database.DataHandler;
import Model.MoneyModel;

/**
 * Created by Shailendra on 12/2/2016.
 */
public class DisplayAllCustomAdapter extends ArrayAdapter {
    private TextView categoryText, dateText, descriptionText, amountText;
    private ImageView imageView;
    private ImageView delItem;
    private List<MoneyModel> mList;
    final static String[] dayName = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    public DisplayAllCustomAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        mList = objects;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        LayoutInflater li = LayoutInflater.from(getContext());
        View customView = li.inflate(R.layout.displayall, parent, false);

        categoryText = (TextView) customView.findViewById(R.id.categoryText);
        dateText = (TextView) customView.findViewById(R.id.dateText);
        descriptionText = (TextView) customView.findViewById(R.id.descriptionText);
        amountText = (TextView) customView.findViewById(R.id.amountText);
        imageView = (ImageView) customView.findViewById(R.id.imageView);
        delItem = (ImageView) customView.findViewById(R.id.delItem);

        String dateCame = mList.get(position).getDate();

        String[] splitDate = dateCame.split("-");


        Calendar calendar = new GregorianCalendar(Integer.parseInt(splitDate[0]), (Integer.parseInt(splitDate[1]) - 1), Integer.parseInt(splitDate[2]));


        int dayResult = calendar.get(Calendar.DAY_OF_WEEK) - 1;


        String mdayName = dayName[dayResult];


        categoryText.setText(mList.get(position).getCategory());
        dateText.setText(mList.get(position).getDate() + ", " + mdayName);
        descriptionText.setText(mList.get(position).getDescription());
        amountText.setText(mList.get(position).getAmount() + "");
        String IEtype = mList.get(position).getIEType();

        if (IEtype.matches("income")) {
            amountText.setTextColor(Color.parseColor("#FF0E6B33"));

        } else {
            amountText.setTextColor(Color.parseColor("#FFCE1F1F"));
        }

        switch (mList.get(position).getCategory()) {

            case "Medical":
                imageView.setImageResource(R.drawable.medical);
                break;
            case "Clothing":
                imageView.setImageResource(R.drawable.clothing);
                break;
            case "Gadget":
                imageView.setImageResource(R.drawable.gadget);
                break;
            case "Food":
                imageView.setImageResource(R.drawable.pizza);
                break;
            case "Education":
                imageView.setImageResource(R.drawable.education);
                break;
            case "Personal":
                imageView.setImageResource(R.drawable.personal);
                break;
            case "Social":
                imageView.setImageResource(R.drawable.social);
                break;
            case "Fun":
                imageView.setImageResource(R.drawable.fun);
                break;
            case "Household":
                imageView.setImageResource(R.drawable.household);
                break;
            case "Bonus":
                imageView.setImageResource(R.drawable.bonus);
                break;
            case "Donation":
                imageView.setImageResource(R.drawable.donation);
                break;
            case "Profit":
                imageView.setImageResource(R.drawable.profit);
                break;
            case "Salary":
                imageView.setImageResource(R.drawable.salary);
                break;

            default:
                imageView.setImageResource(R.drawable.pizza);
        }

        delItem.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        AlertDialog.Builder delBuilder = new AlertDialog.Builder(getContext());
                        delBuilder.setIcon(R.mipmap.ic_launcher).setTitle("Delete Item").setCancelable(true).
                                setMessage("Are you sure you want to delete this item?").setPositiveButton(
                                "Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        DataHandler dataHandler = new DataHandler(getContext());   //here get position to delete item
                                        dataHandler.deleteItem(getItem(position));
                                        mList.remove(position);

                                        notifyDataSetChanged();

                                        Toast.makeText(getContext(), "Deleted Successfully", Toast.LENGTH_LONG).show();


//                                        Intent intent = ((Activity) getContext()).getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION );
//                                       getContext().startActivity(intent);

                                    }
                                }
                        );
                        delBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        delBuilder.show();


                    }
                }
        );


        return customView;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getPosition(Object item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
