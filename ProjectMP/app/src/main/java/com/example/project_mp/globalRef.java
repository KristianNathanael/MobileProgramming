package com.example.project_mp;

import android.app.Application;

import java.util.List;

public class globalRef extends Application{
    static List<Refference> listR;

    public List<Refference> getListR() {
        return listR;
    }

    public void setListR(List<Refference> listR) {
        this.listR = listR;
    }
}
