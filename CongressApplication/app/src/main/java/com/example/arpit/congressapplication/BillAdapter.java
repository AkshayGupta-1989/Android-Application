package com.example.arpit.congressapplication;

/**
 * Created by Arpit on 18-11-2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.arpit.congressapplication.fragment.Bills;
import com.example.arpit.congressapplication.fragment.Favorites;

import java.util.ArrayList;
import java.util.HashMap;




public class BillAdapter extends BaseAdapter{

    private Bills fragment;
    private Favorites fragmentFav;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;

    public BillAdapter(Bills a, ArrayList<HashMap<String, String>> d) {
        fragment = a;
        data=d;
        inflater = (LayoutInflater)fragment.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }
    public BillAdapter(Favorites a, ArrayList<HashMap<String, String>> d) {
        fragmentFav = a;
        data=d;
        inflater = (LayoutInflater)fragmentFav.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row_bill, null);

            TextView title = ( TextView ) vi.findViewById(R.id.title);
            TextView desc = ( TextView ) vi.findViewById(R.id.desc);
            TextView date = ( TextView ) vi.findViewById(R.id.date);


            HashMap<String, String> result = new HashMap<String, String>();
            result = data.get(position);
            // Setting all values in listview
            title.setText((result.get(Bills.BILL_ID)).toUpperCase());
            date.setText(result.get(Bills.INTRODUCED_ON));
            if (result.get(Bills.SHORT_TITLE) != "null") {
                desc.setText(result.get(Bills.SHORT_TITLE));
            } else {
                desc.setText(result.get(Bills.OFFICIAL_TITLE));
            }
            return vi;
        }
}



