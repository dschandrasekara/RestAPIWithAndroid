package com.example.rest.api.integration.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.rest.api.integration.R;
import com.example.rest.api.integration.adaptors.MemberListAdapter;
import com.example.rest.api.integration.stub.MemberDTO;
import com.example.rest.api.integration.util.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MemberListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private ProgressDialog progressDialog;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getListView().setOnItemClickListener(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        getMembers();
    }

    private void getMembers() {
        showingProgress();
        AndroidNetworking.get(Constant.API_URL_WITH_JSON)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray memberIds = response.names();
                        List<MemberDTO> memberDTOList = new ArrayList<>();
                        for (int i=0; i<memberIds.length(); i++) {
                            try {
                                String id = (String) memberIds.get(i);
                                JSONObject member = (JSONObject) response.get(id);
                                MemberDTO dto = new MemberDTO();
                                dto.setFirstName(member.getString("firstName"));
                                dto.setLastName(member.getString("lastName"));
                                dto.setEmail(member.getString("email"));
                                dto.setId(id);
                                memberDTOList.add(dto);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        dismissProgress();
                        MemberListAdapter memberListAdapter = new MemberListAdapter(getContext(), memberDTOList);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setListAdapter(memberListAdapter);
                            }
                        });

                    }
                    @Override
                    public void onError(ANError error) {
                        dismissProgress();
                    }});
    }

    private void dismissProgress() {
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }

    private void showingProgress(){
        if(progressDialog == null || !progressDialog.isShowing()){
            Activity a = getActivity();
            if(a != null){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog = ProgressDialog.show(
                                getActivity(),
                                "",
                                "Loading Data...",
                                true,
                                true,
                                new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        if(progressDialog != null){
                                            progressDialog.dismiss();
                                        }
                                    }
                                }
                        );
                    }
                });
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        MemberDTO selectedMember = (MemberDTO) adapterView.getItemAtPosition(i);
        Bundle transferData = new Bundle();
        transferData.putString("id", selectedMember.getId());
        transferData.putString("firstName", selectedMember.getFirstName());
        transferData.putString("lastName", selectedMember.getLastName());
        transferData.putString("email", selectedMember.getEmail());
        Fragment memberDetailFragment = new MemberDetailFragment();
        memberDetailFragment.setArguments(transferData);
        getFragmentManager().beginTransaction().replace(R.id.frame_layout, memberDetailFragment, "Member Detail").commit();
    }
}
