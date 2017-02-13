package com.example.arpit.congressapplication;

/**
 * Created by Arpit on 18-11-2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arpit.congressapplication.fragment.Favorites;
import com.example.arpit.congressapplication.fragment.Legislator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;




public class XMLAdapter extends BaseAdapter{

    private Legislator activity;
    private Favorites activityFav;

    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;

    public XMLAdapter(Legislator a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //System.out.print("Data"+data);
    }

    public XMLAdapter(Favorites a, ArrayList<HashMap<String, String>> d) {
        activityFav = a;
        data=d;
        inflater = (LayoutInflater)activityFav.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
        //System.out.print("Data"+data);
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, parent, false);

            TextView title = (TextView) vi.findViewById(R.id.title); // title
            TextView state = (TextView) vi.findViewById(R.id.state); // artist name
            ImageView legis_image = (ImageView) vi.findViewById(R.id.list_image); //  image

            HashMap<String, String> result = new HashMap<String, String>();
            result = data.get(position);
            // Setting all values in listview
            title.setText(result.get(Legislator.LAST_NAME) + "," + result.get(Legislator.FIRST_NAME));
        if(result.get(Legislator.DISTRICT).equalsIgnoreCase("null")){
            state.setText("(" + result.get(Legislator.PARTY) + ")" + (result.get(Legislator.STATE_NAME)+ " " + "- District" + " " + "N.A"));

        }else {
            state.setText("(" + result.get(Legislator.PARTY) + ")" + (result.get(Legislator.STATE_NAME) + " " + "- District" + " " + result.get(Legislator.DISTRICT)));
        }// preparing image URL
        String imgURL;
       /* if(result.get(Legislator.BIOGUIDE_ID).equalsIgnoreCase("null")) {
            legis_image.setImageResource(R.drawable.house);
        }   else {
         */    imgURL = "https://theunitedstates.io/images/congress/original/" + result.get(Legislator.BIOGUIDE_ID) + ".jpg";
        if(activity!=null)
            Picasso.with(activity.getContext()).load(imgURL).resize(50,50).into(legis_image);
          else
            Picasso.with(activityFav.getContext()).load(imgURL).resize(50,50).into(legis_image);

        // Loading image using Picasso


        return vi;
    }


}

