package com.whospablo.lstr;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity
        extends AppCompatActivity
        implements TaskAdapter.TaskCardListener {

    private TaskDAO mTaskDAO;
    private TaskAdapter mAdapter;

    public static final int EDIT_TASK_REQUEST_CODE = 20;
    public static final int DELETE_TASK_RESULT_CODE = 444;

    public static final String TASK = "task";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task t = mTaskDAO.createTask("","",false);
                mAdapter.addItem(0, t);
                mAdapter.notifyDataSetChanged();
                fireEditTaskActivity(view, t);
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
        fireEditTaskActivity(v, t);
    }

    @Override
    public void onTaskCheckBoxClicked(Task t, boolean isChecked) {
        if( t.getFinished() != isChecked ){
            t.setFinished(isChecked);
            mTaskDAO.saveTask(t);
            mAdapter.editItem(t);
        }

    }

    @Override
    protected void onResume() {
        mTaskDAO.open();
        super.onPostResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTaskDAO.close();
    }

    public void fireEditTaskActivity(View v, Task t ){
        Intent intent = new Intent(this, EditTaskActivity.class);
        intent.putExtra("task", t);

        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this,
                        v,
                        getString(R.string.transition_card) );

        ActivityCompat.startActivityForResult(this, intent, EDIT_TASK_REQUEST_CODE, options.toBundle());
    }

    public void deleteTask(Task t){
        mTaskDAO.deleteTask(t);
        mAdapter.removeItem(t);
        mAdapter.notifyDataSetChanged();
        Snackbar.make(findViewById(R.id.fab), t.getTitle() + " removed", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mTaskDAO.open();
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == EDIT_TASK_REQUEST_CODE) {
            Task t = (Task) data.getSerializableExtra(TASK);
            mAdapter.editItem(t);
            mAdapter.notifyDataSetChanged();
            mTaskDAO.saveTask(t);

        } else if( resultCode == DELETE_TASK_RESULT_CODE && requestCode == EDIT_TASK_REQUEST_CODE) {
            Task t = (Task) data.getSerializableExtra(TASK);
            deleteTask(t);
        }
    }
}
