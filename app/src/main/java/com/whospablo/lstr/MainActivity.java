package com.whospablo.lstr;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity
        extends AppCompatActivity
        implements TaskAdapter.OnTaskSelectedListener {

    private TaskDAO mTaskDAO;
    private TaskAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task t = mTaskDAO.createTask("","",false);
                t.setTitle("Task "+t.getId());
                t.setSummary("This is some really long summary text that should lead to an ellipsis");
                Snackbar.make(view, t.getTitle(), Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                mTaskDAO.saveTask(t);
                mAdapter.addItem(t);
            }
        });

        RecyclerView recList = (RecyclerView) findViewById(R.id.task_card_list);
        recList.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        mTaskDAO = new TaskDAO(this);
        mTaskDAO.open();

        mAdapter = new TaskAdapter(mTaskDAO.getAllTasks(),  this);
        recList.setAdapter(mAdapter);


    }

    @Override
    public void onTaskSelectedListener(View v, Task t) {
        Snackbar.make(v, t.getTitle() + " removed", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
        mTaskDAO.deleteTask(t);
        mAdapter.removeItem(t);

    }

    @Override
    protected void onResume() {
        mTaskDAO.open();
        super.onPostResume();
    }

    @Override
    protected void onPause() {
        mTaskDAO.close();
        super.onPause();
    }
}
