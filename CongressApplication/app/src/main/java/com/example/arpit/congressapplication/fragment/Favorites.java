package com.example.arpit.congressapplication.fragment;

import android.net.Uri;
import android.os.Bundle;
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

import com.example.arpit.congressapplication.BillAdapter;
import com.example.arpit.congressapplication.CommitteeAdapter;
import com.example.arpit.congressapplication.CompareArray;
import com.example.arpit.congressapplication.CompareArrayBill;
import com.example.arpit.congressapplication.R;
import com.example.arpit.congressapplication.XMLAdapter;
import com.example.arpit.congressapplication.activity.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Favorites.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Favorites#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Favorites extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view=null;
    ListView list;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private int tabValue=0;
    private TabLayout tabLayout;
    XMLAdapter adapterLegis;
    BillAdapter adapterBill;
    CommitteeAdapter adapterCommittee;
    private OnFragmentInteractionListener mListener;
    Map<String, Integer> mapIndex;
    ArrayList<HashMap<String, String>> legislatureFinalDetails = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> billFinalDetails = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> legislatureDetails = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> billDetails = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> comitteeFinalDetails = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> committeeDetails = new ArrayList<HashMap<String, String>>();

    public MainActivity mainActivity;


    public Favorites() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Favorites.
     */
    // TODO: Rename and change types and number of parameters
    public static Favorites newInstance(String param1, String param2) {
        Favorites fragment = new Favorites();
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
        view=inflater.inflate(R.layout.fragment_favorites, container, false);


        toolbar = (Toolbar )getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Favorites");
      //Unlocking the drawer
        drawer = (DrawerLayout ) getActivity().findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        //Displaying Hamburger
        ((AppCompatActivity ) getActivity()).getDelegate().getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawer, toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //Creating Tabs
        tabLayout = (TabLayout )view.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Legislators"));
        tabLayout.addTab(tabLayout.newTab().setText("Bills"));
        tabLayout.addTab(tabLayout.newTab().setText("Committees"));
        final   LinearLayout indexLayout = (LinearLayout) view.findViewById(R.id.side_index);
        LinearLayout indexLayoutParent = (LinearLayout) view.findViewById(R.id.fav);
        int height=indexLayoutParent.getHeight();
        final  LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(0,height);

        list=(ListView )view.findViewById(R.id.list);
        mainActivity=(MainActivity ) getActivity();
        legislatureDetails=mainActivity.legislatureFavDetails;
        billDetails=mainActivity.billFavDetails;
        committeeDetails=mainActivity.committeeFavDetails;



        if(legislatureDetails.size()!=0) {
            //Sort By StateName
            Collections.sort(legislatureDetails, new CompareArray("last_name"));
            adapterLegis=new XMLAdapter(this, mainActivity.legislatureFavDetails);

        }
        if(billDetails.size()!=0) {
            //Sort By StateName
            Collections.sort(billDetails, new CompareArrayBill("introduced_on"));
            adapterBill=new BillAdapter(this, mainActivity.billFavDetails);
        }
        if(committeeDetails.size()!=0) {
            //Sort By StateName
            Collections.sort(committeeDetails, new CompareArray("name"));
            adapterCommittee=new CommitteeAdapter(this, mainActivity.committeeFavDetails);
        }
        final TabLayout.Tab tab = tabLayout.getTabAt(tabValue);

        if(tabValue==0) {

            if (legislatureDetails.size() != 0) {
                //indexLayout.setLayoutParams(parms1);

                list.setAdapter(adapterLegis);
                legislatureFinalDetails=legislatureDetails;
                getIndexList(legislatureDetails, "last_name");
                displayIndex();
            }

        }
        if(tabValue==1) {
            indexLayout.setLayoutParams(parms);
            if(billDetails.size()!=0) {
                list.setAdapter(adapterBill);
                billFinalDetails = billDetails;
            }
        }
        if(tabValue==2) {
            indexLayout.setLayoutParams(parms);
            if(committeeDetails.size()!=0) {
                list.setAdapter(adapterCommittee);
                comitteeFinalDetails = committeeDetails;
            }
        }
      /*  if(tabValue==0) {
            if (legislatureDetails.size() != 0) {
                displayIndex();
            }
        }*/
        tab.select();
        tabLayout.setSelected(true);

       // tabLayout.(tab);
        //On Tab selection
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    LinearLayout indexLayoutParent = (LinearLayout) view.findViewById(R.id.fav);
                    int height=indexLayoutParent.getHeight();
                    LinearLayout indexLayout = (LinearLayout) view.findViewById(R.id.side_index);
                    LinearLayout.LayoutParams parms1 = new LinearLayout.LayoutParams(100,height);
                    indexLayout.setLayoutParams(parms1);
                      list.setAdapter(adapterLegis);
                      legislatureFinalDetails = legislatureDetails;
                      tabValue = tab.getPosition();
                    getIndexList(legislatureFinalDetails, "last_name");
                    displayIndex();


                  } else if (tab.getPosition() == 1) {
                    indexLayout.setLayoutParams(parms);
                    list.setAdapter(adapterBill);
                    billFinalDetails=billDetails;
                    tabValue=tab.getPosition();

                  } else {
                    indexLayout.setLayoutParams(parms);
                    list.setAdapter(adapterCommittee);
                    tabValue=tab.getPosition();
                    comitteeFinalDetails=committeeDetails;
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
                if(tabValue==0) {
                    android.support.v4.app.Fragment detail = new Details();
                    Bundle args = new Bundle();
                    args.putSerializable("hashmap", legislatureFinalDetails.get(position));
                    detail.setArguments(args);
                    android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame, detail).addToBackStack("back").commit();

                }
                if(tabValue==1) {
                    android.support.v4.app.Fragment detail = new BillDetails();
                    Bundle args = new Bundle();
                    args.putSerializable("hashmap", billFinalDetails.get(position));
                    detail.setArguments(args);
                    android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame, detail).addToBackStack("back").commit();

                }
                if(tabValue==2) {
                    android.support.v4.app.Fragment detail = new CommitteeDetails();
                    Bundle args = new Bundle();
                    args.putSerializable("hashmap", comitteeFinalDetails.get(position));
                    detail.setArguments(args);
                    android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame, detail).addToBackStack("back").commit();

                }
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
            indexLayout.isShown();

        }

    }
    public void onClick(View view) {

        TextView selectedIndex = (TextView) view;
        list.setSelection(mapIndex.get(selectedIndex.getText()));
    }

}