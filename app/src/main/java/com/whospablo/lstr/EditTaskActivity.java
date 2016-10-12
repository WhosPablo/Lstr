package com.whospablo.lstr;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;

import static com.whospablo.lstr.MainActivity.TASK;

/**
 * Created by pablo_arango on 10/11/16.
 */

public class EditTaskActivity
        extends AppCompatActivity {

    private Task mCurrentTask;

    private EditText mTitleEditText;
    private EditText mSummaryEditText;
    private CheckBox mFinishedCheckbox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

//        ActionBar bar = getSupportActionBar();
//        bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_edit_task);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mCurrentTask = (Task) getIntent().getSerializableExtra(TASK);


        mTitleEditText = (EditText) findViewById(R.id.title_edit_text);
        mSummaryEditText = (EditText) findViewById(R.id.summary_edit_text);

        mFinishedCheckbox = (CheckBox) findViewById(R.id.finished_checkBox);

        mTitleEditText.setText(mCurrentTask.getTitle());
        mSummaryEditText.setText(mCurrentTask.getSummary());

        mFinishedCheckbox.setChecked(mCurrentTask.getFinished());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_edit_task, menu);
        Drawable drawable = menu.findItem(R.id.action_delete_task).getIcon();
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_task:
                Intent deleteIntent = new Intent();
                deleteIntent.putExtra(MainActivity.TASK, mCurrentTask );
                setResult(MainActivity.DELETE_TASK_RESULT_CODE, deleteIntent);

                ActivityCompat.finishAfterTransition(this);
                return true;
            case android.R.id.home:

                Intent saveIntent = new Intent();

                mCurrentTask.setFinished(mFinishedCheckbox.isChecked());
                mCurrentTask.setTitle(mTitleEditText.getText().toString());
                mCurrentTask.setSummary(mSummaryEditText.getText().toString());

                saveIntent.putExtra(MainActivity.TASK, mCurrentTask );
                setResult(RESULT_OK, saveIntent);

                ActivityCompat.finishAfterTransition(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
