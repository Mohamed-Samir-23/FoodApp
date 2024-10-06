package com.example.foodplannerapplication.features.Scan.presenter;

import com.example.foodplannerapplication.features.Scan.view.ScanView;
import com.example.foodplannerapplication.models.repository.Repo;

public class SettingsPresenter {
    ScanView view;
    Repo repo;

    public SettingsPresenter(ScanView view, Repo repo) {
        this.view = view;
        this.repo = repo;
    }

    public void isDoneWork() {
        //view.onGetDoneWorkFlag(repo.isDoneWork());
    }


}
