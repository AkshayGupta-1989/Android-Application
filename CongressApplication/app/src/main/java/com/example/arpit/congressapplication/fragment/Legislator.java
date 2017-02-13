package com.example.arpit.congressapplication.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.arpit.congressapplication.CompareArray;
import com.example.arpit.congressapplication.CompareArrayLegis;
import com.example.arpit.congressapplication.DateFormatter;
import com.example.arpit.congressapplication.JSONParser;
import com.example.arpit.congressapplication.R;
import com.example.arpit.congressapplication.XMLAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Legislator.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Legislator#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Legislator extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //URL
    static final String URL = "http://webtechasg8.us-west-2.elasticbeanstalk.com/Cong.php?variable=legislature";

    public static final String RESULTS = "results"; // parent node
    public static final String LAST_NAME = "last_name";
    public static final String FIRST_NAME = "first_name";
    public static final String STATE_NAME = "state_name";
    public static final String DISTRICT = "district";
    public static final String BIOGUIDE_ID = "bioguide_id";
    public static final String CHAMBER = "chamber";
    public static final String PARTY = "party";
    public static final String TITLE = "title";
    public static final String EMAIL = "oc_email";
    public static final String CONTACT = "phone";
    public static final String START = "term_start";
    public static final String END = "term_end";
    public static final String OFFICE = "office";
    public static final String FAX = "fax";
    public static final String BIRTHDAY = "birthday";
    public static final String FACEBOOK = "facebook_id";
    public static final String TWITTER = "twitter_id";
    public static final String WEBSITE = "website";



    ListView list;
    XMLAdapter adapterHouse;
    XMLAdapter adapterState;
    XMLAdapter adapterSenate;
    private TabLayout tabLayout;
    Map<String, Integer> mapIndex;
    View view=null;
    //Declaring ArrayLists
    ArrayList<HashMap<String, String>> legislatureDetails = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> legislatureStateDetails = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> legislatureHouseDetails = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> legislatureSenateDetails = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> legislatureStateData = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> legislatureHouseData = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> legislatureSenateData = new ArrayList<HashMap<String, String>>();

    // XML node keys

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private int tabValue=0;
    private OnFragmentInteractionListener mListener;

    public Legislator() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Legislator.
     */
    // TODO: Rename and change types and number of parameters
    public static Legislator newInstance(String param1, String param2) {
        Legislator fragment = new Legislator();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_legislator, container, false);

        //Setting navigation functionality(Hamburger)
        toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Legislators");
        drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            ((AppCompatActivity ) getActivity()).getDelegate().getSupportActionBar().setDisplayHomeAsUpEnabled(false);
           // ((AppCompatActivity ) getActivity()).getDelegate().getSupportActionBar().setDisplayShowHomeEnabled(false);
            ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawer, toolbar, R.string.openDrawer, R.string.closeDrawer);
            drawer.setDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();
           //Creating Tabs
        tabLayout = (TabLayout)view.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("By States"));
        tabLayout.addTab(tabLayout.newTab().setText("House"));
        tabLayout.addTab(tabLayout.newTab().setText("Senate"));

        list=(ListView)view.findViewById(R.id.list);

       if(legislatureStateData.size()==0) {
           legislatureStateData = getLegislatureStateData();
           //Sort By StateName
           Collections.sort(legislatureStateData, new CompareArrayLegis("state_name","last_name"));
           adapterState=new XMLAdapter(this, legislatureStateData);
         /*  getIndexList(legislatureStateData, "state_name");
          displayIndex();*/
       }

       if(legislatureHouseData.size()==0) {
           legislatureHouseData = getLegislatureHouseData();
           Collections.sort(legislatureHouseData,new CompareArray("last_name"));
           adapterHouse=new XMLAdapter(this, legislatureHouseData);
          // getIndexList(legislatureHouseData, "state_name");
         //  displayIndex();
       }
        if(legislatureSenateData.size()==0) {
            legislatureSenateData = getLegislatureSenateData();
            Collections.sort(legislatureSenateData, new CompareArray("last_name"));
            adapterSenate=new XMLAdapter(this, legislatureSenateData);
          //  getIndexList(legislatureSenateData, "state_name");
         //   displayIndex();
        }

        //Settting Default Tab
        final TabLayout.Tab tab = tabLayout.getTabAt(tabValue);
            tab.select();
        if(tabValue==0) {
            list.setAdapter(adapterState);
            legislatureDetails = legislatureStateData;
            getIndexList(legislatureStateData, "state_name");
            displayIndex();
        }
        if(tabValue==1) {
            list.setAdapter(adapterHouse);
            legislatureDetails = legislatureHouseData;
           getIndexList(legislatureHouseData, "last_name");
            displayIndex();
        }
        if(tabValue==2) {
            list.setAdapter(adapterSenate);
            legislatureDetails = legislatureSenateData;
            getIndexList(legislatureSenateData, "last_name");
            displayIndex();
        }


        //On Tab selection
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                   list.setAdapter(adapterState);
                    getIndexList(legislatureStateData,"state_name");
                    displayIndex();
                    legislatureDetails=legislatureStateData;
                    tabValue=tab.getPosition();

                } else if (tab.getPosition() == 1) {
                    list.setAdapter(adapterHouse);
                    getIndexList(legislatureHouseData,"last_name");
                    displayIndex();
                    legislatureDetails=legislatureHouseData;
                    tabValue=tab.getPosition();
                } else {
                    list.setAdapter(adapterSenate);
                    getIndexList(legislatureSenateData,"last_name");
                    displayIndex();
                    legislatureDetails=legislatureSenateData;
                    tabValue=tab.getPosition();
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        // Click event for single list row
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                android.support.v4.app.Fragment detail = new Details();
                Bundle args = new Bundle();
                args.putSerializable("hashmap", legislatureDetails.get(position));
                detail.setArguments(args);
                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame, detail).addToBackStack("back").commit();


            }
        });
        // Inflate the layout for this fragment
        return view;
    }



       // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public ArrayList getLegislatureStateData(){

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
if(legislatureDetails.size()==0) {
    JSONParser parser = new JSONParser();
    JSONObject json = parser.getJSONFromUrl(URL); // getting XML from URL
    //Document doc = parser.getDomElement(xml); // getting DOM element
    DateFormatter dateFormatter=new DateFormatter();
    JSONArray result = null;
    try {
        result = json.getJSONArray(RESULTS);
    } catch (JSONException e) {
        e.printStackTrace();
    }
    // looping through all legislatures nodes &lt;song&gt;
    for (int I = 0; I < result.length(); I++) {
        // creating new HashMap
        HashMap<String, String> map = new HashMap<String, String>();
        JSONObject detail = null;
        try {
            detail = result.getJSONObject(I);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // adding each child node to HashMap key =&gt; value
        try {
            map.put(LAST_NAME, detail.getString(LAST_NAME));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            map.put(FIRST_NAME, detail.getString(FIRST_NAME));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            map.put(STATE_NAME, detail.getString(STATE_NAME));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            map.put(DISTRICT, detail.getString(DISTRICT));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            map.put(BIOGUIDE_ID, detail.getString(BIOGUIDE_ID));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            map.put(PARTY, detail.getString(PARTY));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            map.put(TITLE, detail.getString(TITLE));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            map.put(EMAIL, detail.getString(EMAIL));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            map.put(CONTACT, detail.getString(CONTACT));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            map.put(START, dateFormatter.dateFormat(detail.getString(START)));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            map.put(END, dateFormatter.dateFormat(detail.getString(END)));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            map.put(OFFICE, detail.getString(OFFICE));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            map.put(FAX, detail.getString(FAX));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            map.put(BIRTHDAY, dateFormatter.dateFormat(detail.getString(BIRTHDAY)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            map.put(FACEBOOK, detail.getString(FACEBOOK));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            map.put(TWITTER, detail.getString(TWITTER));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            map.put(WEBSITE, detail.getString(WEBSITE));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            if ((detail.getString(CHAMBER)).equalsIgnoreCase("house")) {
                map.put(CHAMBER, "House");
                legislatureHouseDetails.add(map);
            } else if ((detail.getString(CHAMBER)).equalsIgnoreCase("senate")) {
                map.put(CHAMBER, "Senate");
                legislatureSenateDetails.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // adding HashList to ArrayList
        legislatureStateDetails.add(map);
    }
}
        return legislatureStateDetails;
    }
    public ArrayList getLegislatureHouseData(){
        if(legislatureHouseDetails!=null){
            return legislatureHouseDetails;
        }
        return null;
    }
    public ArrayList getLegislatureSenateData(){
        if(legislatureSenateDetails!=null){
            return legislatureSenateDetails;
        }
        return null;
    }
    private void getIndexList( ArrayList<HashMap<String, String>> frameList,String indexName) {
        mapIndex = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < frameList.size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
             map = frameList.get(i);
            String state=map.get(indexName);
            String index = state.substring(0, 1);

            if (mapIndex.get(index) == null)
                mapIndex.put(index, i);
        }
    }
    private void displayIndex() {
        LinearLayout indexLayout = (LinearLayout) view.findViewById(R.id.side_index);
        indexLayout.removeAllViews();
        TextView textView;
            List<String> indexList = new ArrayList<String>(mapIndex.keySet());
            for (String index : indexList) {
                textView = (TextView) getActivity().getLayoutInflater().inflate(
                        R.layout.side_index_item, null);
                textView.setText(index);
                textView.setOnClickListener(this);

                indexLayout.addView(textView);
            }

    }
    public void onClick(View view) {

        TextView selectedIndex = (TextView) view;
        list.setSelection(mapIndex.get(selectedIndex.getText()));
    }
}
