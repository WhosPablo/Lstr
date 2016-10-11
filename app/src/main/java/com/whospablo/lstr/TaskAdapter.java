package com.whospablo.lstr;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pablo_arango on 10/10/16.
 */

class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>{

    private List<Task> taskList;
    private OnTaskSelectedListener mCallback;

    public interface OnTaskSelectedListener {
        void onTaskSelectedListener(Task t);
    }



    TaskAdapter(List<Task> taskList, OnTaskSelectedListener callback) {
        this.taskList = taskList;
        this.mCallback = ( OnTaskSelectedListener ) callback;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.layout_task_card, parent, false);

        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        final Task t = taskList.get(position);
        holder.vCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCallback.onTaskSelectedListener( t );
            }
        });
        holder.vTitle.setText(t.title);
        holder.vSummary.setText(t.summary);
        holder.vFinished.setChecked(t.finished);
        holder.vFinished.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }


    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView vTitle;
        TextView vSummary;
        CheckBox vFinished;
        CardView vCard;

        TaskViewHolder(View v) {
            super(v);
            try{
                vCard = (CardView)  v;
            } catch (ClassCastException e) {
                throw new ClassCastException(v.toString()
                        + " must extend CardView");
            }
            vTitle = (TextView) v.findViewById(R.id.task_title_text);
            vSummary = (TextView) v.findViewById(R.id.task_summary_text);
            vFinished = (CheckBox) v.findViewById(R.id.task_checkBox);

        }
    }
}
