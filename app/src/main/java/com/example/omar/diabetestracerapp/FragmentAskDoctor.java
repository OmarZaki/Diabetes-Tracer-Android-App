package com.example.omar.diabetestracerapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.omar.diabetestracerapp.data_model.Messages;
import com.example.omar.diabetestracerapp.data_model.User;
import com.example.omar.diabetestracerapp.database.DataSource;
import com.example.omar.diabetestracerapp.rest_client.RestClient;

import java.util.Date;

/**
 *
 */
public class FragmentAskDoctor extends Fragment {
    private OnFragmentInteractionListener mListener;
    /**---------> Back-end Instances<------- */
    private DataSource dataSource;
    private RestClient restClient;
    /**-------- end --------------- */
    Button btnSendMessage;
    EditText etMessageText;
    EditText etMessageDate;


    public FragmentAskDoctor() {
        // Required empty public constructor
    }


    public static FragmentAskDoctor newInstance() {
        FragmentAskDoctor fragment = new FragmentAskDoctor();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.dataSource = new DataSource(getActivity());
        this.restClient = new RestClient(getActivity());


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ask_doctor, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        btnSendMessage = (Button) getActivity().findViewById(R.id.btnSendMessageToDoctor);

        etMessageDate = (EditText) getActivity().findViewById(R.id.etMessageDate);
        etMessageDate.setText(User.ConvertDateToString(new Date()));
        etMessageDate.setEnabled(false);

        etMessageText = (EditText)getActivity().findViewById(R.id.etMessage);
        etMessageText.setText("");
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User currentUser = dataSource.retrieveUserFromDataBase();
                Messages message = new Messages();
                message.setDate_time(new Date());
                message.setText(etMessageText.getText().toString().trim());
                message.setUsers_id(currentUser.getId());
                if(message.validate()) {

                    // 1. send the message to server - > insert it in database ;
                    restClient.SendMessage(message);
                }else{
                    Toast.makeText(getActivity().getBaseContext(),"Write someting In the Text", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

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
        // TODO: Update argument Type and name
        void onFragmentInteraction(Uri uri);
    }
}
