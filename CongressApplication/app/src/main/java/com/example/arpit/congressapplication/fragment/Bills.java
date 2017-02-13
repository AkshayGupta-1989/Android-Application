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
import android.widget.ListView;

import com.example.arpit.congressapplication.BillAdapter;
import com.example.arpit.congressapplication.DateFormatter;
import com.example.arpit.congressapplication.JSONParser;
import com.example.arpit.congressapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Bills.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Bills#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Bills extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    static final String URLActiveBill = "http://webtechasg8.us-west-2.elasticbeanstalk.com/Cong.php?variable=bill";
    static final String URLNewBill = "http://webtechasg8.us-west-2.elasticbeanstalk.com/Cong.php?variable=bill1";

    public static final String RESULTS = "results"; // parent node
    public static final String BILL_ID = "bill_id";
    public static final String SHORT_TITLE = "short_title";
    public static final String OFFICIAL_TITLE = "official_title";
    public static final String INTRODUCED_ON = "introduced_on";
    public static final String BILL_TYPE = "bill_type";
    public static final String SPONSOR = "sponsor";
    public static final String TITLE = "title";
    public static final String LAST_NAME = "last_name";
    public static final String FIRST_NAME = "first_name";
    public static final String CHAMBER = "chamber";
    public static final String CONG_URL = "congress";
    public static final String URLS = "urls";
    public static final String VERSION_STATUS = "version_name";
    public static final String LAST_VERSION = "last_version";
    public static final String BIIL_URL = "pdf";
    public static final String STATUS = "active";
    public static final String HISTORY = "history";


    ArrayList<HashMap<String, String>> finalDetails = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> activeBillsList=new  ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> newBillsList=new  ArrayList<HashMap<String, String>>();


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TabLayout tabLayout;
    BillAdapter adapterActiveBills;
    BillAdapter adapterNewBills;
    View view=null;
    ListView list;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private int tabValue=0;

    private OnFragmentInteractionListener mListener;

    public Bills() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Bills.
     */
    // TODO: Rename and change types and number of parameters
    public static Bills newInstance(String param1, String param2) {
        Bills fragment = new Bills();
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
        view=inflater.inflate(R.layout.fragment_bills, container, false);

        //Setting navigation functionality(Hamburger)
        toolbar = (Toolbar )getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Bills");
        drawer = (DrawerLayout ) getActivity().findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

           ((AppCompatActivity ) getActivity()).getDelegate().getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            //((AppCompatActivity ) getActivity()).getDelegate().getSupportActionBar().setDisplayShowHomeEnabled(false);

            ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawer, toolbar, R.string.openDrawer, R.string.closeDrawer);
            drawer.setDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();

        //Creating Tabs
        tabLayout = (TabLayout)view.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Active Bills"));
        tabLayout.addTab(tabLayout.newTab().setText("New Bills"));

        list=(ListView)view.findViewById(R.id.list);

        // Fetching Legislature State Data
        if(activeBillsList.size()==0) {
            activeBillsList = getBills(URLActiveBill);
            adapterActiveBills=new BillAdapter(this, activeBillsList);
        }
        // Need to sort of Basis of bill id(Discuss)
        if(newBillsList.size()==0) {
            newBillsList = getBills(URLNewBill);
            adapterNewBills=new BillAdapter(this, newBillsList);
        }

        //Setting default Tab
        TabLayout.Tab tab = tabLayout.getTabAt(tabValue);
        tab.select();

        if(tabValue==0) {
            finalDetails=activeBillsList;
            list.setAdapter(adapterActiveBills);
        }
        if(tabValue==1) {
            finalDetails = newBillsList;
            list.setAdapter(adapterNewBills);
        }

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                   list.setAdapter(adapterActiveBills);
                    finalDetails=activeBillsList;
                    tabValue=tab.getPosition();

                } else  {
                   list.setAdapter(adapterNewBills);
                    finalDetails=newBillsList;
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

        //View details
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                android.support.v4.app.Fragment detail = new BillDetails();
                Bundle args = new Bundle();
                args.putSerializable("hashmap", finalDetails.get(position));
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

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

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

    ArrayList<HashMap<String, String>> billsList;
    public ArrayList getBills(String URL){

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        billsList=new ArrayList<HashMap<String, String>>();
        DateFormatter dateFormatter=new DateFormatter();
        JSONParser parser = new JSONParser();
        JSONObject json = parser.getJSONFromUrl(URL); // getting XML from URL
        //Document doc = parser.getDomElement(xml); // getting DOM element

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
            JSONObject detail=null;
            try {
                detail = result.getJSONObject(I);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // adding each child node to HashMap key =&gt; value
            try {
                map.put(BILL_ID, detail.getString(BILL_ID).toUpperCase());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                map.put(SHORT_TITLE,detail.getString( SHORT_TITLE));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                map.put(OFFICIAL_TITLE,detail.getString( OFFICIAL_TITLE));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                map.put(INTRODUCED_ON, dateFormatter.dateFormat(detail.getString( INTRODUCED_ON)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                map.put(BILL_TYPE,detail.getString( BILL_TYPE).toUpperCase());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONObject sponsor=detail.getJSONObject( SPONSOR);
                map.put(TITLE,sponsor.getString( TITLE));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONObject sponsor=detail.getJSONObject( SPONSOR);
                map.put(FIRST_NAME,sponsor.getString( FIRST_NAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONObject sponsor=detail.getJSONObject( SPONSOR);
                map.put(LAST_NAME,sponsor.getString( LAST_NAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                map.put(CHAMBER,detail.getString( CHAMBER));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONObject lastVersion=detail.getJSONObject( LAST_VERSION);
                map.put(VERSION_STATUS,lastVersion.getString(VERSION_STATUS));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                JSONObject urls=detail.getJSONObject(URLS);
                map.put(CONG_URL,urls.getString( CONG_URL));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONObject lastVersion=detail.getJSONObject( LAST_VERSION);
                JSONObject billUrl=lastVersion.getJSONObject(URLS);
                map.put(BIIL_URL,billUrl.getString( BIIL_URL));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                JSONObject history=detail.getJSONObject( HISTORY);
                map.put(STATUS,history.getString( STATUS));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // adding HashList to ArrayList
            billsList.add(map);
        }

        return billsList;
    }


    }
