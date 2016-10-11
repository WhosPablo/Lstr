package com.whospablo.lstr;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity
        extends AppCompatActivity
        implements TaskAdapter.OnTaskSelectedListener {

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        RecyclerView recList = (RecyclerView) findViewById(R.id.task_card_list);
        recList.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        TaskAdapter ta = new TaskAdapter(createList(30), this);
        recList.setAdapter(ta);
    }

    private List<Task> createList(int size) {
        List<Task> result = new ArrayList<>();
        for (int i=1; i <= size; i++) {
            Task t = new Task();
            t.title = "Task"+ i;
            t.summary = "This is some really long summary text that should lead to an ellipsis";
            t.finished = false;
            result.add(t);

        }

        return result;
    }

    @Override
    public void onTaskSelectedListener(View v, Task t) {
        Snackbar.make(v, t.title + " selected", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }
}
