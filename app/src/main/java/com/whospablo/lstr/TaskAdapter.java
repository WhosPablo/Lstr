package com.whospablo.lstr;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pablo_arango on 10/10/16.
 */

class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>{

    private List<Task> mTaskList;
    private TaskCardListener mCallback;

    interface TaskCardListener {
        void onTaskSelectedListener(View v, Task t);
        void onTaskCheckBoxClicked(Task t, boolean isChecked );
    }

    TaskAdapter(List<Task> taskList, TaskCardListener callback) {
        this.mTaskList = taskList;
        this.mCallback = callback;

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
        final Task t = mTaskList.get(position);
        holder.vCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCallback.onTaskSelectedListener( v, t);
            }
        });
        holder.vTitle.setText(t.getTitle());
        holder.vSummary.setText(t.getSummary());
        holder.vFinished.setChecked(t.getFinished());
        holder.vFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = ((CheckBox) v).isChecked();
                mCallback.onTaskCheckBoxClicked(t, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    void addItem(int position, Task t){
        mTaskList.add(position, t);
    }

    void editItem(Task t){
        int position = mTaskList.indexOf(t);
        mTaskList.set(position, t);
    }

    void removeItem(Task t){
        int position = mTaskList.indexOf(t);
        mTaskList.remove(position);
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
