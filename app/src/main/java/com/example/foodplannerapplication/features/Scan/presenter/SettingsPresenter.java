package com.example.foodplannerapplication.features.Scan.presenter;

import com.example.foodplannerapplication.features.Scan.view.SettingsView;
import com.example.foodplannerapplication.models.repository.Repo;

public class SettingsPresenter {
    SettingsView view;
    Repo repo;

    public SettingsPresenter(SettingsView view, Repo repo) {
        this.view = view;
        this.repo = repo;
    }

    public void isDoneWork() {
        //view.onGetDoneWorkFlag(repo.isDoneWork());
    }


}
