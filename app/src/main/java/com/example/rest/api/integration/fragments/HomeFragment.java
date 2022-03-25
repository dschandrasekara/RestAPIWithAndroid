package com.example.rest.api.integration.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.rest.api.integration.R;

public class HomeFragment extends Fragment implements View.OnClickListener {

    Button signUpButton = null;
    Button officerLoginButton = null;
    View rootView = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        signUpButton = (Button) rootView.findViewById(R.id.signUpButton);
        officerLoginButton = (Button) rootView.findViewById(R.id.officerLogin);

        signUpButton.setOnClickListener(this);
        officerLoginButton.setOnClickListener(this);

        return rootView;
    }


    @Override
    public void onClick(View view) {
        if (view == signUpButton) {
            Fragment signUpFragment = new SignUpFragment();
            getFragmentManager().beginTransaction().replace(R.id.frame_layout, signUpFragment, "Member SignUP").commit();
        } else if (view == officerLoginButton) {
            Fragment memberListFragment = new MemberListFragment();
            getFragmentManager().beginTransaction().replace(R.id.frame_layout, memberListFragment, "Member List").commit();

        }
    }
}
