package com.example.rest.api.integration.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.rest.api.integration.R;
import com.example.rest.api.integration.util.Constant;

import org.json.JSONObject;

public class MemberDetailFragment extends Fragment implements View.OnClickListener {

    Button editButton = null;
    Button deleteButton = null;
    EditText firstNameTextView = null;
    EditText lastNameTextView = null;
    EditText emailTextView = null;
    View rootView = null;

    String memberID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_member_detail, container, false);

        firstNameTextView = (EditText)rootView.findViewById(R.id.first_name_text_view);
        lastNameTextView = (EditText)rootView.findViewById(R.id.last_name_text_view);
        emailTextView = (EditText)rootView.findViewById(R.id.email_text_view);

        editButton = (Button) rootView.findViewById(R.id.editButton);
        deleteButton = (Button) rootView.findViewById(R.id.deleteButton);

        editButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);

        Bundle dataBundle = this.getArguments();

        memberID = dataBundle.get("id").toString();
        firstNameTextView.setText(dataBundle.get("firstName").toString());
        lastNameTextView.setText(dataBundle.get("lastName").toString());
        emailTextView.setText(dataBundle.get("email").toString());

        return rootView;
    }

    @Override
    public void onClick(View view) {
        if (view == deleteButton) {
            String url = Constant.API_URL + memberID + ".json";
            AndroidNetworking.delete(url)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Fragment memberListFragment = new MemberListFragment();
                            getFragmentManager().beginTransaction().replace(R.id.frame_layout, memberListFragment, "Member List").commit();
                        }
                        @Override
                        public void onError(ANError error) {
                            Fragment memberListFragment = new MemberListFragment();
                            getFragmentManager().beginTransaction().replace(R.id.frame_layout, memberListFragment, "Member List").commit();
                        }
                    });

        }
    }

}
