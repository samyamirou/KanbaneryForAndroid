package com.kanbandroid.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import com.kanbandroid.model.Project;
import com.kanbandroid.view.cell.ProjectCellView;

import java.util.List;

public class ProjectListAdapter extends BaseAdapter {

    private Context context;
    private List<Project> projectList;

    public ProjectListAdapter(Context context, List<Project> projectList) {
        this.context = context;
        this.projectList = projectList;
    }

    public int getCount() {
        return projectList.size();
    }

    public Object getItem(int position) {
        return projectList.get(position);
    }

    public long getItemId(int position) {
        return projectList.get(position).getId().longValue();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ProjectCellView view = (ProjectCellView) convertView;
        if (view == null) {
            view = new ProjectCellView(context);
        }

        Project project = (Project) getItem(position);
        view.setData(project);

        return view;
    }
}
