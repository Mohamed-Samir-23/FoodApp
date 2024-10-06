package com.example.foodplannerapplication.features.plan.view;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodplannerapplication.R;
import com.example.foodplannerapplication.database.ConcreteLocalSource;
import com.example.foodplannerapplication.features.plan.presenter.PlanPresenter;
import com.example.foodplannerapplication.models.repository.Repo;
import com.example.foodplannerapplication.network.ApiClient;
import com.example.foodplannerapplication.util.Constants;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import kotlin.Pair;

public class PlanFragment extends Fragment implements PlanView {
    private final static String TAG = "PlanFragment";

    private PlanPresenter presenter;


    private TextView psback;
    private Button btnback;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private PlanPagerAdapter adapter;

    private ArrayList<Pair<String, Integer>> days;
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PlanPresenter(this,
                Repo.getInstance(
                        ApiClient.getInstance(),
                        ConcreteLocalSource.getInstance(getContext())
                ));
        days = new ArrayList<>();
        days.add(new Pair<>("Saturday", Constants.DAYS.SATURDAY));
        days.add(new Pair<>("Sunday", Constants.DAYS.SUNDAY));
        days.add(new Pair<>("Monday", Constants.DAYS.MONDAY));
        days.add(new Pair<>("Tuesday", Constants.DAYS.TUESDAY));
        days.add(new Pair<>("Wednesday", Constants.DAYS.WEDNESDAY));
        days.add(new Pair<>("Thursday", Constants.DAYS.THURSDAY));
        days.add(new Pair<>("Friday", Constants.DAYS.FRIDAY));
        adapter = new PlanPagerAdapter(getChildFragmentManager(), getLifecycle(), days);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.splan);
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        psback = view.findViewById(R.id.ps_back);
        btnback = view.findViewById(R.id.btn_back);
        tabLayout = view.findViewById(R.id.tl_days);
        viewPager = view.findViewById(R.id.vp_days);
        //btnback.setOnClickListener(v -> Navigation.findNavController(v).setGraph(R.navigation.auth_graph));
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(days.get(position).getFirst())
        ).attach();
        presenter.isDoneWork();

    }

    @Override
    public void onGetDoneWorkFlag(Observable<Boolean> observable) {
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(isDoneWork -> {
                    if (isDoneWork) {
                        psback.setVisibility(View.INVISIBLE);
                        btnback.setVisibility(View.INVISIBLE);
                        tabLayout.setVisibility(View.VISIBLE);
                        viewPager.setVisibility(View.VISIBLE);
                    } else {
                        psback.setVisibility(View.VISIBLE);
                        btnback.setVisibility(View.VISIBLE);
                        tabLayout.setVisibility(View.INVISIBLE);
                        viewPager.setVisibility(View.INVISIBLE);
                    }
                }, throwable -> {
                    psback.setVisibility(View.VISIBLE);
                    btnback.setVisibility(View.VISIBLE);
                    tabLayout.setVisibility(View.INVISIBLE);
                    viewPager.setVisibility(View.INVISIBLE);
                    Log.i(TAG, throwable.getMessage());
                });
    }
}