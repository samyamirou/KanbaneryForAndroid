package com.kanbandroid.view.adapter;

import android.content.Context;
import com.kanbandroid.model.Project;
import com.kanbandroid.model.Workspace;
import com.kanbandroid.view.cell.CustomCellView;
import com.kanbandroid.view.cell.ProjectCellView;

import java.util.List;

public class ProjectCellAdapter extends DefaultAdapter<Project> {
    public ProjectCellAdapter(Context context, List<Project> objectList) {
        super(context, ProjectCellView.class, objectList);
    }

    @Override
    protected void bindView(int position, CustomCellView<Project> cell) {
        Project project = (Project) getItem(position);

        //Cas ou on veut afficher le header tournee
        boolean printHeader = false;

        String workspaceName = project.getWorkspace().getName();
        if (position != 0 && workspaceName != null) {
            Project previousProject = (Project) getItem(position - 1);
            Workspace previousWorkspace = previousProject.getWorkspace();
            if (previousWorkspace != null) {
                printHeader = !workspaceName.equals(previousWorkspace.getName());
            }
        } else if (position == 0) {
            printHeader = true;
        }

        ((ProjectCellView) cell).setPrintHeader(printHeader);

        cell.setData(project);
    }
}
