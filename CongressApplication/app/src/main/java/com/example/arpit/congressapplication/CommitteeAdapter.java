package com.example.arpit.congressapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.arpit.congressapplication.fragment.Committee;
import com.example.arpit.congressapplication.fragment.Favorites;

import java.util.ArrayList;
import java.util.HashMap;





public class CommitteeAdapter extends BaseAdapter {

    private Committee fragment;
    private Favorites fragmentCommittee;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;

    public CommitteeAdapter(Committee a, ArrayList<HashMap<String, String>> d) {
        fragment = a;
        data=d;
        inflater = (LayoutInflater)fragment.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    public CommitteeAdapter(Favorites a, ArrayList<HashMap<String, String>> d) {
        fragmentCommittee = a;
        data=d;
        inflater = (LayoutInflater)fragmentCommittee.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


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
            vi = inflater.inflate(R.layout.list_row_committee, parent, false);

        TextView title = (TextView) vi.findViewById(R.id.title); // committeeId
        TextView name = (TextView) vi.findViewById(R.id.name); // name
        TextView chamber = (TextView) vi.findViewById(R.id.chamber); // chamber

        HashMap<String, String> result = new HashMap<String, String>();
        result = data.get(position);
        // Setting all values in listview
        title.setText(result.get(Committee.COMMITTEE_ID));
        name.setText(result.get(Committee.COMMITTEE_NAME));
        if(result.get(Committee.CHAMBER).equalsIgnoreCase("house")){
            chamber.setText("House");
        }else
        if(result.get(Committee.CHAMBER).equalsIgnoreCase("senate")){
            chamber.setText("Senate");
        }else
        if(result.get(Committee.CHAMBER).equalsIgnoreCase("joint")){
            chamber.setText("Joint");
        }

        return vi;
    }


}

