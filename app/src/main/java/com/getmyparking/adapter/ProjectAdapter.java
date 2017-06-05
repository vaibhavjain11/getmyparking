package com.getmyparking.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.getmyparking.ProjectModel;
import com.getmyparking.R;
import com.getmyparking.Utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vaibhavjain on 04/06/17.
 */

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectHolder> implements View.OnClickListener, Filterable{

    private Filter fRecords;

    @Override
    public void onClick(View v) {

    }

    @Override
    public Filter getFilter() {
        if(fRecords == null) {
            fRecords=new RecordFilter();
        }
        return fRecords;
    }

    public interface onRecyclerItemClickListener{
        void onItemClick(int position);
    }

    private Context context;
    private List<ProjectModel> list;
    private onRecyclerItemClickListener listener;
    public ProjectAdapter(Context context, List<ProjectModel> projectModelList){
        this.context = context;
        this.list = projectModelList;
        this.listener = (onRecyclerItemClickListener)context;
    }


    @Override
    public ProjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_project,parent,false);
        return new ProjectHolder(view);
    }

    @Override
    public void onBindViewHolder(ProjectHolder holder, final int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.txtAmount.setText(String.valueOf(list.get(position).getAmtPledged()));
        holder.txtBackers.setText(list.get(position).getNumBackers());
        Date date1 = Utility.convertTime(list.get(position).getEndTime(),"yyyy-mm-dd");
        Date date2 = Utility.convertDate(String.valueOf(System.currentTimeMillis()),"yyyy-mm-dd");
        long days = 0;
        if(date1.after(date2)) {
            days = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000);
        }
        holder.txtDays.setText(String.valueOf(days));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ProjectHolder extends ViewHolder{

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.amount)
        TextView txtAmount;
        @BindView(R.id.backers)
        TextView txtBackers;
        @BindView(R.id.days)
        TextView txtDays;
        @BindView(R.id.cardview)
        CardView cardView;

        public ProjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private class RecordFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();

            //Implement filter logic
            // if edittext is null return the actual list
            if (constraint == null || constraint.length() == 0) {
                //No need for filter
                results.values = list;
                results.count = list.size();

            } else {
                //Need Filter
                // it matches the text  entered in the edittext and set the data in adapter list
                ArrayList<ProjectModel> fRecords = new ArrayList<ProjectModel>();

                for (ProjectModel s : list) {
                    if (s.getTitle().toString().toLowerCase().trim().contains(constraint.toString().toLowerCase().trim())) {
                        fRecords.add(s);
                    }
                }
                results.values = fRecords;
                results.count = fRecords.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,FilterResults results) {

            //it set the data from filter to adapter list and refresh the recyclerview adapter
            list = (ArrayList<ProjectModel>) results.values;
            notifyDataSetChanged();
        }
    }

}
