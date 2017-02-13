package com.example.arpit.congressapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arpit.congressapplication.DateFormatter;
import com.example.arpit.congressapplication.R;
import com.example.arpit.congressapplication.activity.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Details.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Details#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Details extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private WebView webView;
    private OnFragmentInteractionListener mListener;
    HashMap<String, String> mMap = new HashMap<String,String>();
    public MainActivity mainActivity;

   // ArrayList<HashMap<String, String>> legislatureFavDetails = new ArrayList<HashMap<String, String>>();
    Intent intent;
    public Details() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Details.
     */
    // TODO: Rename and change types and number of parameters
    public static Details newInstance(String param1, String param2) {
        Details fragment = new Details();
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     final View view=inflater.inflate(R.layout.fragment_details, container, false);
        mainActivity=(MainActivity ) getActivity();
        Bundle b = this.getArguments();
        if(b.getSerializable("hashmap") != null)
            mMap = (HashMap<String,String>)b.getSerializable("hashmap");
        toolbar=(Toolbar)getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Legislator Info");

        //Locking the Drawer
        drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //Displaying Back Arrow
        if (((AppCompatActivity ) getActivity()).getDelegate().getSupportActionBar() != null){
            ((AppCompatActivity ) getActivity()).getDelegate().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = getFragmentManager().getBackStackEntryCount();

                if (count == 0) {
                    Details.super.getActivity().onBackPressed();

                } else {
                    getFragmentManager().popBackStack();

                }
            }
        });
        //Set Values in View
        final String fb=mMap.get("facebook_id");
        final String twitter1=mMap.get("twitter_id");
        final String website1=mMap.get("website");

        ImageView star = (ImageView) view.findViewById(R.id.star);
        if(mainActivity.legislatureFavDetails.contains(mMap)){
            star.setImageResource(R.drawable.starfilled);
        }
        else {
            star.setImageResource(R.drawable.star);
        }// legis imag
        ImageView facebook = (ImageView) view.findViewById(R.id.facebook); // legis imag
            facebook.setImageResource(R.drawable.facebook);
        ImageView twitter = (ImageView) view.findViewById(R.id.twitter); // legis image
            twitter.setImageResource(R.drawable.twitter);
        ImageView website = (ImageView) view.findViewById(R.id.website); // legis image
            website.setImageResource(R.drawable.world);

        // Loading image using Picasso
        final ImageView legis_image = (ImageView) view.findViewById(R.id.imagelegis); // legis image
        String imgURL = "https://theunitedstates.io/images/congress/original/" + mMap.get("bioguide_id")+ ".jpg";
        Picasso.with(view.getContext()).load(imgURL).into(legis_image);


        //Setting Party Image

        TextView party=(TextView ) view.findViewById(R.id.party) ;
        ImageView party_image = (ImageView) view.findViewById(R.id.imageparty);
        if(mMap.get("party").equalsIgnoreCase("R")){
            party_image.setImageResource(R.drawable.republican);
             party.setText("Republican");
        }else
        if(mMap.get("party").equalsIgnoreCase("D")){
           party_image.setImageResource(R.drawable.picture1);
              party.setText("Democratic");
        }else
        if(mMap.get("party").equalsIgnoreCase("I")){
            party_image.setImageResource(R.drawable.independent);
            party.setText("Independent");
        }
        DateFormatter dateFormatter=new DateFormatter();
        TextView name=(TextView ) view.findViewById(R.id.name) ;
        name.setText(mMap.get("title") + "." + mMap.get("last_name")+"," + mMap.get("first_name"));
        TextView email=(TextView ) view.findViewById(R.id.email) ;
        if(mMap.get("oc_email")!="null" && mMap.get("oc_email")!=null)
            email.setText(mMap.get("oc_email"));
        else
            email.setText("N.A");
        TextView chamber=(TextView ) view.findViewById(R.id.chamber) ;
        if(mMap.get("chamber")!="null" && mMap.get("chamber")!=null)
            chamber.setText(mMap.get("chamber"));
        else
            chamber.setText("N.A");
        TextView contact=(TextView ) view.findViewById(R.id.contact) ;
        if(mMap.get("phone")!="null" && mMap.get("phone")!=null)
            contact.setText(mMap.get("phone"));
        else
            contact.setText("N.A");
        TextView start=(TextView ) view.findViewById(R.id.start) ;
        if(mMap.get("term_start")!="null" && mMap.get("term_start")!=null)
           start.setText(mMap.get("term_start"));
        else
            start.setText("N.A");
        TextView end=(TextView ) view.findViewById(R.id.end) ;
        if(mMap.get("term_end")!="null" && mMap.get("term_end")!=null)
            end.setText(mMap.get("term_end"));
        else
            end.setText("N.A");
        ProgressBar term = (ProgressBar) view.findViewById(R.id.pb_horizontal);
        TextView tvProgressHorizontal= ( TextView ) view.findViewById(R.id.tv_progress_horizontal);
        Date dateStart = new Date(mMap.get("term_start"));
       // System.out.println("dateStart in date"+dateStart);
        double startTime=dateStart.getTime();
        //System.out.println("dateStart milli "+startTime);
        Date dateEnd = new Date(mMap.get("term_end"));
        //System.out.println("dateEnd in date"+dateEnd);
        double endTime=dateEnd.getTime();
       // System.out.println("dateEnd in milli"+endTime);
        double current=System.currentTimeMillis();
        //System.out.println("current in milli"+current);
        double calcTerm=((current-startTime)/(endTime-startTime))*100;
        //System.out.println("calc in perct "+calcTerm);
        term.setProgress(( int ) calcTerm);
        tvProgressHorizontal.setText((int)calcTerm+"%");

        //System.out.println("calc in perct "+(int) calcTerm);
        TextView office=(TextView ) view.findViewById(R.id.office) ;
        if(mMap.get("office")!="null" && mMap.get("office")!=null)
            office.setText(mMap.get("office"));
        else
            office.setText("N.A");
        TextView state=(TextView ) view.findViewById(R.id.state) ;
        if(mMap.get("state_name")!="null" && mMap.get("state_name")!=null)
            state.setText(mMap.get("state_name"));
        else
            state.setText("N.A");
        TextView fax=(TextView ) view.findViewById(R.id.fax) ;
        if(mMap.get("fax")!="null" && mMap.get("fax")!=null)
            fax.setText(mMap.get("fax"));
        else
            fax.setText("N.A");
        TextView birthDay=(TextView ) view.findViewById(R.id.birthday) ;
        if(mMap.get("birthday")!="null" && mMap.get("birthday")!=null)
            birthDay.setText(mMap.get("birthday"));
        else
            birthDay.setText("N.A");

        /*View web=inflater.inflate(R.layout.webview, container, false);
        webView=(WebView)web.findViewById(R.id.webView1);
*/
        facebook.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(fb=="null" || fb==null) {
                    Context context = getActivity().getApplicationContext();
                    CharSequence text = "Facebook account not available";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else {
                    String urlFb = "https://www.facebook.com/" + fb;
                   /* webView.getSettings().setJavaScriptEnabled(true);
                    webView.loadUrl(url);*/
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(urlFb));
                    startActivity(intent);
                }
            }

        });



        twitter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(twitter1=="null" || twitter1==null) {
                    Context context = getActivity().getApplicationContext();
                    CharSequence text = "Twitter account not available";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }else{
                String urlTwitter = "https://www.twitter.com/"+ twitter1;
                   /* webView.getSettings().setJavaScriptEnabled(true);
                    webView.loadUrl(url);*/
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(urlTwitter));
                    startActivity(intent);

                }

            }

        });
        website.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View arg0) {
                if(website1=="null" || website1==null) {
                    Context context = getActivity().getApplicationContext();
                    CharSequence text = "Website not available";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }else {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(website1));
                    startActivity(intent);
                   // webView.setWebChromeClient(new WebChromeClient());
                   /* webView.getSettings().setJavaScriptEnabled(true);
                    webView.getSettings().setDomStorageEnabled(true);
                    webView.setWebViewClient(new WebViewClient());
                    webView.getSettings().setJavaScriptEnabled(true);
                    if (Build.VERSION.SDK_INT >= 21) {
                        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                    }*/
                  /*  String url = website1;
                    if (!url.startsWith("http://") && !url.startsWith("https://"))
                        url = "http://" + url;
                  */ /* WebSettings settings = webView.getSettings();
                    settings.setAllowFileAccessFromFileURLs(true);
                    settings.setAllowUniversalAccessFromFileURLs(true);
                  */ /* webView.getSettings().setDomStorageEnabled(true);

                    webView.setWebViewClient(new WebViewClient() {
                        public boolean shouldOverrideUrlLoading(WebView view,String url) {
                            return false;
                        }
                    });*/

                   // webView.loadUrl(url);

                }
            }

        });


        //Favorites Functionality
        star.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                ImageView star = (ImageView) view.findViewById(R.id.star);
                if(mainActivity.legislatureFavDetails.contains(mMap)){
                    mainActivity.legislatureFavDetails.remove(mMap);
                    star.setImageResource(R.drawable.star);
                }
                else {
                    mainActivity.legislatureFavDetails.add(mMap);
                    star.setImageResource(R.drawable.starfilled);
                }
            }

        });
       /* @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed(); // Ignore SSL certificate errors
        }
*/
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

}