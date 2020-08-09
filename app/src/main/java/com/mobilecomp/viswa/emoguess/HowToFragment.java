package com.mobilecomp.viswa.emoguess;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.common.base.Strings;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HowToFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HowToFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HowToFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HowToFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HowToFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HowToFragment newInstance(String param1, String param2) {
        HowToFragment fragment = new HowToFragment();
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
        View view = inflater.inflate(R.layout.fragment_how_to, container, false);
        // Toast toast=Toast. makeText(getActivity(),"howto fragment",Toast. LENGTH_SHORT);
        //toast.show();
        Button b=view.findViewById(R.id.howtochildren);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog2 = new Dialog(getContext());
                dialog2.setContentView(R.layout.how_to_children);
                dialog2.setTitle("For Children:");
                dialog2.show();
            }
        });

        Button b1=view.findViewById(R.id.howtoparent);
        b1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity(),R.style.AlertDialog);
                //builder.setTitle("For Parents: ");
                final SpannableString okString = new SpannableString("How to Play");
                okString.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 11, 0);
                okString.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                final SpannableString okString1 = new SpannableString("How to Share");
                okString1.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 12, 0);
                okString1.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                final SpannableString[] options = {okString, okString1};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // the user clicked on colors[which]
                        if (okString.equals(options[which])) {
                            final Dialog dg = new Dialog(getContext());
                            dg.setContentView(R.layout.how_to_play_parents);
                            dg.setTitle("How to Play:");
                            dg.show();
                        }
                        if (okString1.equals(options[which])) {
                            final Dialog dg = new Dialog(getContext());
                            dg.setContentView(R.layout.how_to_share_parents);
                            dg.setTitle("How to Share:");
                            dg.show();
                        }
                    }
                });
                AlertDialog dia = builder.create();
                dia.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6ab7ff")));
                dia.show();
            }
        });

//        Spinner dropdown = view.findViewById(R.id.spinner1);
//        //Button b= view.findViewById(R.id.t);
//        String[] items = new String[]{"How to Play", "How to Share"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, items) {
//
//            public View getView(int position, View convertView, ViewGroup parent) {
//                View v = super.getView(position, convertView, parent);
//                Typeface externalFont=Typeface.create("casual", Typeface.BOLD);
//                ((TextView) v).setTextColor(Color.WHITE);
//                ((TextView) v).setTypeface(externalFont);
//
//                return v;
//            }
//
//
//            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
//                View v =super.getDropDownView(position, convertView, parent);
//
//                Typeface externalFont=Typeface.create("casual", Typeface.BOLD);
//                ((TextView) v).setTypeface(externalFont);
//                v.setBackgroundColor(Color.parseColor("#FDA172"));
//                ((TextView) v).setTextColor(Color.WHITE);
//
//                return v;
//            }
//        };
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        //dropdown.setAdapter(adapter);
//        dropdown.setAdapter(
//                new NothingSelectedSpinnerAdapter(
//                        adapter,
//                        R.layout.contact_spinner_row_nothing_selected,
//                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
//                        getContext()));
//        dropdown.setOnItemSelectedListener(this);
//       // dropdown.setPrompt("For Parents: ");
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.how_to_play_parents);
                dialog.setTitle("For Parents:");
                dialog.show();
                break;
            case 2:
                final Dialog dialog2 = new Dialog(getContext());
                dialog2.setContentView(R.layout.how_to_share_parents);
                dialog2.setTitle("For Parents:");
                dialog2.show();
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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