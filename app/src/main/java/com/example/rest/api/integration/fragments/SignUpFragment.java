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
import com.example.rest.api.integration.stub.MemberDTO;
import com.example.rest.api.integration.util.Constant;

import org.json.JSONObject;

public class SignUpFragment extends Fragment implements View.OnClickListener {

   Button signUpButton = null;
   EditText firstNameTextView = null;
   EditText lastNameTextView = null;
   EditText emailTextView = null;
   View rootView = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_signup, container, false);

        firstNameTextView = (EditText)rootView.findViewById(R.id.first_name_text_view);
        lastNameTextView = (EditText)rootView.findViewById(R.id.last_name_text_view);
        emailTextView = (EditText)rootView.findViewById(R.id.email_text_view);

        signUpButton = (Button) rootView.findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        if (view == signUpButton) {
            MemberDTO member = new MemberDTO();
            member.setFirstName(firstNameTextView.getText().toString());
            member.setLastName(lastNameTextView.getText().toString());
            member.setEmail(emailTextView.getText().toString());
            AndroidNetworking.post(Constant.API_URL_WITH_JSON)
                    .addJSONObjectBody(member.getJsonObject())
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Fragment memberListFragment = new MemberListFragment();
                            getFragmentManager().beginTransaction().replace(R.id.frame_layout, memberListFragment, "Member List").commit();
                        }
                        @Override
                        public void onError(ANError error) {
                            System.out.println(error.toString());
                        }
                    });

        }
    }
}
