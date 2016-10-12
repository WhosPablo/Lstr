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

    private List<Task> mTaskList;
    private OnTaskSelectedListener mCallback;

    interface OnTaskSelectedListener {
        void onTaskSelectedListener(View v, Task t);
    }

    TaskAdapter(List<Task> taskList, OnTaskSelectedListener callback) {
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
    public void onBindViewHolder(TaskViewHolder holder, final int position) {
        final Task t = mTaskList.get(position);
        holder.vCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCallback.onTaskSelectedListener( v, t );
            }
        });
        holder.vTitle.setText(t.getTitle());
        holder.vSummary.setText(t.getSummary());
        holder.vFinished.setChecked(t.getFinished());
        holder.vFinished.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    public void addItem(Task t){
        mTaskList.add(t);
        notifyItemInserted(mTaskList.indexOf(t));
    }

    public void removeItem(Task t){
        int position = mTaskList.indexOf(t);
        mTaskList.remove(position);
        notifyItemRemoved(position);
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
