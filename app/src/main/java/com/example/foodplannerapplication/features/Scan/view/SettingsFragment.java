package com.example.foodplannerapplication.features.Scan.view;

import android.animation.Animator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplannerapplication.R;
import com.example.foodplannerapplication.database.ConcreteLocalSource;
import com.example.foodplannerapplication.features.Scan.presenter.SettingsPresenter;
import com.example.foodplannerapplication.models.repository.Repo;
import com.example.foodplannerapplication.network.ApiClient;
import com.example.foodplannerapplication.util.NetworkConnection;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public class SettingsFragment extends Fragment implements SettingsView {
    public static final String TAG = "SettingsFragment";
    private Button btnforward, btnBackup;
    private SettingsPresenter presenter;

    private LottieAnimationView lvDone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SettingsPresenter(this,
                Repo.getInstance(
                        ApiClient.getInstance(),
                        ConcreteLocalSource.getInstance(getContext())
                ));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        //initClicks();

        presenter.isDoneWork();
    }

    @Override
    public void initViews(View view) {
        lvDone = view.findViewById(R.id.lv_done);
        btnforward = view.findViewById(R.id.btn_forward);
        btnBackup = view.findViewById(R.id.btn_backup);

    }


    private boolean isConnected() {
        if (NetworkConnection.isConnected(requireContext())) {
            return true;
        } else {
            Toast.makeText(getContext(), R.string.check_your_internet_connection_and_try_again, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}