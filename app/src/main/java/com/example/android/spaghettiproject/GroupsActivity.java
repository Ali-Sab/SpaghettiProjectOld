package com.example.android.spaghettiproject;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import java.util.LinkedList;

public class GroupsActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.android.spaghettiproject.extra.MESSAGE";
    private static Button button;

    private final LinkedList<String> mGroupList = new LinkedList<>();

    private RecyclerView mRecyclerView;
    private NextButtonListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        button = (Button) findViewById(R.id.button_group);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fabAdd = findViewById(R.id.fab);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this,
//                        OrderActivity.class);
//                intent.putExtra(EXTRA_MESSAGE, mOrderMessage);
//                startActivity(intent);
            }
        });

        mGroupList.addLast("Sample 1");

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mAdapter = new NextButtonListAdapter(this, mGroupList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    public void goToLists(View view) {
        Intent intent = new Intent(GroupsActivity.this, ListsActivity.class);
        intent.putExtra(EXTRA_MESSAGE, button.getText().toString());
        startActivity(intent);
    }
}
