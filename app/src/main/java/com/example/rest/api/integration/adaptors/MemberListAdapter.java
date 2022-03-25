package com.example.rest.api.integration.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rest.api.integration.R;
import com.example.rest.api.integration.stub.MemberDTO;
import com.google.gson.JsonObject;

import java.util.List;


public class MemberListAdapter extends BaseAdapter {

    List<MemberDTO> members;
    Context context;
    private static LayoutInflater inflater = null;

    public MemberListAdapter(Context context, List<MemberDTO> members) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.members = members;
    }

    @Override
    public int getCount() {
        return members.size();
    }

    @Override
    public Object getItem(int i) {
        return members.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final MemberDetailItemHolder holder;
        if (view == null){
            view = inflater.inflate(R.layout.member_list_item, null);
            holder = new MemberDetailItemHolder();
            holder.firstName = (TextView) view.findViewById(R.id.first_name_text_view);
            holder.lastName = (TextView) view.findViewById(R.id.last_name_text_view);
            holder.email = (TextView) view.findViewById(R.id.email_text_view);
            view.setTag(holder);
        } else {
            holder = (MemberDetailItemHolder) view.getTag();
        }
        if (holder != null){
            MemberDTO member = members.get(i);
            holder.firstName.setText(member.getFirstName());
            holder.lastName.setText(member.getLastName());
            holder.email.setText(member.getEmail());
        }

        return view;
    }

    private class MemberDetailItemHolder {
        private TextView firstName;
        private TextView lastName;
        private TextView email;
    }
}
