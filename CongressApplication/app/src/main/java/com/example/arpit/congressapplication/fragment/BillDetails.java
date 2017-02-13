package com.example.arpit.congressapplication.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arpit.congressapplication.R;
import com.example.arpit.congressapplication.activity.MainActivity;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BillDetails.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BillDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BillDetails extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Toolbar toolbar;
    private DrawerLayout drawer;
    public MainActivity mainActivity;
    HashMap<String, String> mMap = new HashMap<String,String>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BillDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BillDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static BillDetails newInstance(String param1, String param2) {
        BillDetails fragment = new BillDetails();
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

        final View view=inflater.inflate(R.layout.fragment_bill_details, container, false);
        mainActivity=(MainActivity ) getActivity();
        Bundle b = this.getArguments();
        if(b.getSerializable("hashmap") != null)
            mMap = (HashMap<String,String>)b.getSerializable("hashmap");
        toolbar=(Toolbar )getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Bill Info");
        drawer = (DrawerLayout ) getActivity().findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        if (((AppCompatActivity ) getActivity()).getDelegate().getSupportActionBar() != null) {
            (( AppCompatActivity ) getActivity()).getDelegate().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
           // (( AppCompatActivity ) getActivity()).getDelegate().getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = getFragmentManager().getBackStackEntryCount();

                if (count == 0) {
                    BillDetails.super.getActivity().onBackPressed();

                } else {
                     getFragmentManager().popBackStack();

                }
            }
        });
        //Setting values

        ImageView star = (ImageView) view.findViewById(R.id.star);
        if(mainActivity.billFavDetails.contains(mMap)){
            star.setImageResource(R.drawable.starfilled);
        }
        else {
            star.setImageResource(R.drawable.star);
        }// legis imag

        TextView sponsor=(TextView ) view.findViewById(R.id.sponsor) ;
        sponsor.setText(mMap.get("title") + "." + mMap.get("last_name")+"," + mMap.get("first_name"));
        TextView billId=(TextView ) view.findViewById(R.id.billId) ;
        billId.setText(mMap.get("bill_id"));
        TextView title=(TextView ) view.findViewById(R.id.officialTitle) ;
        if (mMap.get(Bills.SHORT_TITLE) != "null")
            title.setText(mMap.get("short_title"));
        else
            title.setText(mMap.get("official_title"));
        TextView billType=(TextView ) view.findViewById(R.id.type) ;
        if(mMap.get("bill_type")!="null" && mMap.get("bill_type")!=null)
           billType.setText(mMap.get("bill_type"));
        else
            billType.setText("N.A");
        TextView chamber=(TextView ) view.findViewById(R.id.chamber) ;
        if(mMap.get("chamber").equalsIgnoreCase("house"))
            chamber.setText("House");
        if(mMap.get("chamber").equalsIgnoreCase("senate"))
            chamber.setText("Senate");
        TextView status=(TextView ) view.findViewById(R.id.status) ;
        if(mMap.get("active").equalsIgnoreCase("true"))
            status.setText("Active");
        if(mMap.get("active").equalsIgnoreCase("false"))
            status.setText("New");
        TextView introduced=(TextView ) view.findViewById(R.id.introduced) ;
        introduced.setText(mMap.get("introduced_on"));
        TextView congUrl=(TextView ) view.findViewById(R.id.congUrl) ;
        if(mMap.get("congress")!="null" && mMap.get("congress")!=null)
           congUrl.setText(mMap.get("congress"));
        else
           congUrl.setText("N.A");
        TextView version=(TextView ) view.findViewById(R.id.version) ;
        if(mMap.get("version_name")!="null" && mMap.get("version_name")!=null)
           version.setText(mMap.get("version_name"));
        else
           version.setText("N.A");
        TextView billUrl=(TextView ) view.findViewById(R.id.billUrl) ;
        if(mMap.get("pdf")!="null")
            billUrl.setText(mMap.get("pdf"));
        else
            billUrl.setText("None");

        //Favorites Functionality
        star.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                ImageView star = (ImageView) view.findViewById(R.id.star);
                if(mainActivity.billFavDetails.contains(mMap)){
                    mainActivity.billFavDetails.remove(mMap);
                    star.setImageResource(R.drawable.star);
                }
                else {
                    mainActivity.billFavDetails.add(mMap);
                    star.setImageResource(R.drawable.starfilled);
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

  /*  @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = ( OnFragmentInteractionListener ) context;
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
}
