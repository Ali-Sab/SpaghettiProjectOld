package com.example.android.spaghettiproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.LinkedList;

public class NextButtonListAdapter extends RecyclerView.Adapter<NextButtonListAdapter.NextButtonListHolder> {

    private final LinkedList<String> mGroupList;
    private final LayoutInflater mInflater;

    public class NextButtonListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final Button nextButtonView;
        public final NextButtonListAdapter mAdapter;

        public NextButtonListHolder(@NonNull View itemView, NextButtonListAdapter adapter) {
            super(itemView);
            nextButtonView = itemView.findViewById(R.id.button_group);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int mPosition = getLayoutPosition();
            String element = mGroupList.get(mPosition);

            Intent intent;
            if (this.getClass().getSimpleName() == "GroupsActivity.class")
                intent = new Intent(v.getContext(), ListsActivity.class);
            else
                intent = new Intent(v.getContext(), ListsActivity.class);
            intent.putExtra(GroupsActivity.EXTRA_MESSAGE, element);
            v.getContext().startActivity(intent);

        }
    }

    public NextButtonListAdapter(Context context, LinkedList<String> groupList) {
        mInflater = LayoutInflater.from(context);
        this.mGroupList = groupList;
    }

    @NonNull
    @Override
    public NextButtonListAdapter.NextButtonListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.next_button_item, parent, false);
        return new NextButtonListHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull NextButtonListAdapter.NextButtonListHolder holder, int position) {
        String mCurrent = mGroupList.get(position);
        holder.nextButtonView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mGroupList.size();
    }


}