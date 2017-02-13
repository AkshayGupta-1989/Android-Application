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
 * {@link CommitteeDetails.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CommitteeDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommitteeDetails extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Toolbar toolbar;
    private DrawerLayout drawer;
    HashMap<String, String> mMap = new HashMap<String,String>();
    public MainActivity mainActivity;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CommitteeDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommitteeDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static CommitteeDetails newInstance(String param1, String param2) {
        CommitteeDetails fragment = new CommitteeDetails();
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
        final View view=inflater.inflate(R.layout.fragment_committee_details, container, false);

        mainActivity=(MainActivity ) getActivity();

        Bundle b = this.getArguments();
        if(b.getSerializable("hashmap") != null)
            mMap = (HashMap<String,String>)b.getSerializable("hashmap");
        toolbar=(Toolbar )getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Committee Info");
        //Locking Drawer
        drawer = (DrawerLayout ) getActivity().findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        //Dislaying Back Arrow
        if (((AppCompatActivity ) getActivity()).getDelegate().getSupportActionBar() != null) {
            (( AppCompatActivity ) getActivity()).getDelegate().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         //   (( AppCompatActivity ) getActivity()).getDelegate().getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = getFragmentManager().getBackStackEntryCount();

                if (count == 0) {
                    CommitteeDetails.super.getActivity().onBackPressed();

                } else {
                    getFragmentManager().popBackStack();

                }
            }
        });
        //Setting values

        ImageView star = (ImageView) view.findViewById(R.id.star); // legis imag
        if(mainActivity.committeeFavDetails.contains(mMap)){
            star.setImageResource(R.drawable.starfilled);
        }
        else {
            star.setImageResource(R.drawable.star);
        }

        TextView committeeId=(TextView ) view.findViewById(R.id.committeeId) ;
        committeeId.setText(mMap.get("committee_id"));
        TextView name=(TextView ) view.findViewById(R.id.name) ;
        name.setText(mMap.get("name"));
        ImageView chamberPic = (ImageView) view.findViewById(R.id.chamberPic);

       /* String imgURL = "http://cs-server.usc.edu:45678/hw/hw8/images/s.svg";
        Picasso.with(view.getContext()).load(imgURL).into(chamberPic);
*/
        TextView chamber=(TextView ) view.findViewById(R.id.chamber) ;
        if(mMap.get("chamber").equalsIgnoreCase("house")) {
            chamber.setText("House");
            chamberPic.setImageResource(R.drawable.house);
        }else if(mMap.get("chamber").equalsIgnoreCase("senate")) {
            chamber.setText("Senate");
            chamberPic.setImageResource(R.drawable.senate);
        }else if(mMap.get("chamber").equalsIgnoreCase("joint")) {
            chamber.setText("Joint");
            chamberPic.setImageResource(R.drawable.senate);
        }
        TextView parent=(TextView ) view.findViewById(R.id.parent) ;
        if(mMap.get("parent_committee_id")!="null" && mMap.get("parent_committee_id")!=null)
            parent.setText(mMap.get("parent_committee_id"));
        else
            parent.setText("N.A");
        TextView contact=(TextView ) view.findViewById(R.id.contact) ;
        if(mMap.get("phone")!="null" && mMap.get("phone")!=null)
        contact.setText(mMap.get("phone"));
        else
            contact.setText("N.A");
        TextView office=(TextView ) view.findViewById(R.id.office) ;
        //System.out.println("office"+(mMap.get("office")));
        if(mMap.get("office")!="null" && mMap.get("office")!=null)
        office.setText(mMap.get("office"));
        else
            office.setText("N.A");


        //Favorites Functionality
        star.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                ImageView star = (ImageView) view.findViewById(R.id.star);
                if(mainActivity.committeeFavDetails.contains(mMap)){
                    mainActivity.committeeFavDetails.remove(mMap);
                    star.setImageResource(R.drawable.star);
                }
                else {
                    mainActivity.committeeFavDetails.add(mMap);
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
