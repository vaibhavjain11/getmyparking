package com.getmyparking.presenter;

import android.content.Context;
import android.database.Cursor;

import com.getmyparking.ProjectModel;
import com.getmyparking.interactor.ListInteractor;

import java.util.List;

/**
 * Created by vaibhavjain on 04/06/17.
 */

public class ListPresenter implements ListInteractor.ListInteractorInterface{

    public ListPresenterInterface listPresenterInterface;
    public Context context;
    public ListInteractor listInteractor;

    @Override
    public void listProject(Cursor cursor) {
            listPresenterInterface.getProjectList(cursor);
    }

    public interface ListPresenterInterface{
        void getProjectList(Cursor  cursor);
    }

    public ListPresenter(Context context, ListPresenterInterface l){
        this.context = context;
        listPresenterInterface = l;
        listInteractor = new ListInteractor(context,this);

    }

    public void getListOfProject(){
        listInteractor.getMyProjectList();
    }



}
