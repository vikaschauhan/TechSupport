package in.infocruise.techsupport;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.infocruise.techsupport.Model.TicketDetail;
import in.infocruise.techsupport.helper.MySingleton;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OpenTicketFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OpenTicketFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OpenTicketFragment extends Fragment {
    private static final String TAG = DashboardActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private TicketAdapter mTicketAdapter = null;
    private ArrayList<TicketDetail> mExampleList;
    private RecyclerView.LayoutManager mLayoutManager;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public OpenTicketFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OpenTicketFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OpenTicketFragment newInstance(String param1, String param2) {
        OpenTicketFragment fragment = new OpenTicketFragment();
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
        //return inflater.inflate(R.layout.fragment_open_ticket, container, false);
        View rootView = inflater.inflate(R.layout.fragment_open_ticket, container, false);

        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mTicketAdapter);
        mRecyclerView.setHasFixedSize(true);

        mExampleList = new ArrayList<>();

        getData();

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

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

    private void getData() {


        String url = "http://202.83.19.113:84/service.asmx/ticketMaster?";
        Log.d(TAG, "url now is: " + url);
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject tickeDetail = (JSONObject) response.get(i);

                                String ticketNote = tickeDetail.getString("ticket_note");
                                String contact = tickeDetail.getString("contact");
                                String dealerCode = tickeDetail.getString("dealer_code");


                                //ManufacturerMaster.add(manufacturerName);
                                mExampleList.add(new TicketDetail(ticketNote, contact, dealerCode));

                            }
                            mTicketAdapter = new TicketAdapter(getContext(), mExampleList);
                            mRecyclerView.setAdapter(mTicketAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Get a RequestQueue
        //RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).
        // getRequestQueue();

        // Add a request (in this example, called stringRequest) to your RequestQueue.
        MySingleton.getInstance(getContext()).addToRequestQueue(request);
    }
}
