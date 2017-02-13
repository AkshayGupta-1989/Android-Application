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

import com.example.arpit.congressapplication.CommitteeAdapter;
import com.example.arpit.congressapplication.CompareArray;
import com.example.arpit.congressapplication.JSONParser;
import com.example.arpit.congressapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Committee.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Committee#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Committee extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<HashMap<String, String>> committeeHouseDetails = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> committeeSenateDetails = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> committeeJointDetails = new ArrayList<HashMap<String, String>>();


    //URL
    static final String URLCommittee = "http://webtechasg8.us-west-2.elasticbeanstalk.com/Cong.php?variable=committee";
    public static final String RESULTS = "results"; // parent node
    public static final String COMMITTEE_ID = "committee_id";
    public static final String COMMITTEE_NAME = "name";
    public static final String CHAMBER = "chamber";
    public static final String PARENT = "parent_committee_id";
    public static final String CONTACT = "phone";
    public static final String OFFICE = "office";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TabLayout tabLayout;
    CommitteeAdapter adapterHouse,adapterSenate,adapterJoint;
    ArrayList<HashMap<String, String>> finalDetails = new ArrayList<HashMap<String, String>>();
    View view=null;
    ListView list;
    private OnFragmentInteractionListener mListener;
    private int tabValue=0;
    private Toolbar toolbar;
    private DrawerLayout drawer;
     ArrayList<HashMap<String, String>> committeeHouseData=new ArrayList<HashMap<String, String>>();
     ArrayList<HashMap<String, String>> committeeSenateData=new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> committeeJointData=new ArrayList<HashMap<String, String>>();
    public Committee() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Committee.
     */
    // TODO: Rename and change types and number of parameters
    public static Committee newInstance(String param1, String param2) {
        Committee fragment = new Committee();
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
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_committee, container, false);


        toolbar = (Toolbar )getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Committees");

        //Unlocking the Drawer
        drawer = (DrawerLayout ) getActivity().findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        //Setting navigation functionality(Hamburger)
        ((AppCompatActivity ) getActivity()).getDelegate().getSupportActionBar().setDisplayHomeAsUpEnabled(false);
         ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawer, toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //Creating Tabs
        tabLayout = (TabLayout)view.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("House"));
        tabLayout.addTab(tabLayout.newTab().setText("Senate"));
        tabLayout.addTab(tabLayout.newTab().setText("Joint"));

        list=(ListView)view.findViewById(R.id.list);

        if(committeeHouseData.size()==0) {
            committeeHouseData = getCommitteeHouseData(URLCommittee);
            Collections.sort(committeeHouseData, new CompareArray("name"));
            adapterHouse=new CommitteeAdapter(this, committeeHouseData);
        }

       // System.out.print("HouseList   "+ committeeHouseData.size());
        //Fetching Legislature House Data
        if(committeeSenateData.size()==0) {
            committeeSenateData = getCommitteeSenateData();
            Collections.sort(committeeSenateData, new CompareArray("name"));
            adapterSenate=new CommitteeAdapter(this, committeeSenateData);
        }
       // System.out.print("SenateList   "+ committeeSenateData.size());
        //Fetching Legislature House Data
        if(committeeJointData.size()==0) {
            committeeJointData = getCommitteeJointData();
            Collections.sort(committeeJointData, new CompareArray("name"));
            adapterJoint=new CommitteeAdapter(this, committeeJointData);
        }
        //System.out.print("JointList   "+ committeeJointData);
        // Getting adapter by passing xml data ArrayList

        //Settting Default Tab
        TabLayout.Tab tab = tabLayout.getTabAt(tabValue);

        tab.select();
        if(tabValue==0) {
            finalDetails = committeeHouseData;
            list.setAdapter(adapterHouse);
        }
        if(tabValue==1) {
            finalDetails = committeeSenateData;
            list.setAdapter(adapterSenate);
        }
        if(tabValue==2) {
            finalDetails = committeeJointData;
            list.setAdapter(adapterJoint);
        }
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    list.setAdapter(adapterHouse);
                    finalDetails=committeeHouseData;
                    tabValue=tab.getPosition();
                } else if (tab.getPosition() == 1) {
                    list.setAdapter(adapterSenate);
                    finalDetails=committeeSenateData;
                    tabValue=tab.getPosition();
                } else {
                    list.setAdapter(adapterJoint);
                    finalDetails=committeeJointData;
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

                android.support.v4.app.Fragment detail = new CommitteeDetails();
                Bundle args = new Bundle();
                args.putSerializable("hashmap", finalDetails.get(position));
                detail.setArguments(args);
                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame, detail).addToBackStack("back").commit();

            }
        });

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

    public ArrayList getCommitteeHouseData(String URL){

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        JSONParser parser = new JSONParser();
        JSONObject json = parser.getJSONFromUrl(URL); // getting XML from URL


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
                map.put(COMMITTEE_ID, detail.getString(COMMITTEE_ID));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                map.put(COMMITTEE_NAME,detail.getString( COMMITTEE_NAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                String off=detail.getString( CHAMBER);
                map.put(CHAMBER,detail.getString( CHAMBER));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                String off=detail.getString( OFFICE);
                map.put(OFFICE,detail.getString( OFFICE));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                map.put(CONTACT,detail.getString( CONTACT));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                map.put(PARENT,detail.getString( PARENT));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if((detail.getString(CHAMBER)).equalsIgnoreCase("senate"))
                {
                    committeeSenateDetails.add(map);
                }else if((detail.getString(CHAMBER)).equalsIgnoreCase("joint")){
                    committeeJointDetails.add(map);
                }
                else if((detail.getString(CHAMBER)).equalsIgnoreCase("house")){
                    committeeHouseDetails.add(map);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return committeeHouseDetails;
    }
    public ArrayList getCommitteeSenateData(){
        if(committeeSenateDetails!=null){
            return committeeSenateDetails;
        }
        return null;
    }
    public ArrayList getCommitteeJointData(){
        if(committeeJointDetails!=null){
            return committeeJointDetails;
        }
        return null;
    }

}
