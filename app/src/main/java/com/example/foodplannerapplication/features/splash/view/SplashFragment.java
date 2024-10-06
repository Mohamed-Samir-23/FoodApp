package com.example.foodplannerapplication.features.splash.view;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foodplannerapplication.R;
import com.example.foodplannerapplication.database.ConcreteLocalSource;
import com.example.foodplannerapplication.features.splash.presenter.SplashPresenter;
import com.example.foodplannerapplication.models.repository.Repo;
import com.example.foodplannerapplication.network.ApiClient;
import com.example.foodplannerapplication.util.NetworkConnection;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;

public class SplashFragment extends Fragment implements SplashView {

    public final static String TAG = "SplashFragment";
    private SplashPresenter presenter;
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SplashPresenter(this,
                Repo.getInstance(
                        ApiClient.getInstance(),
                        ConcreteLocalSource.getInstance(getContext())
                ));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.welcome);
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        ImageView logo = view.findViewById(R.id.mpslogo);
        logo.startAnimation(fadeIn);
        presenter.getCategories();
        presenter.getCountries();
        presenter.getIngredients();


        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (isConnected()) {
                presenter.isDoneWork();
            }
            else
            {
                while (true)
                {
                    if (isConnected()) {
                        presenter.getCategories();
                        presenter.getCountries();
                        presenter.getIngredients();
                        break;
                    }
                }

                presenter.isDoneWork();
            }

        }, 8000);
    }

    @Override
    public void onGetDoneWorkFlag(Observable<Boolean> observable) {
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        isDoneWork -> {
                            if (isDoneWork) {
                                Navigation.findNavController(requireView()).setGraph(R.navigation.main_graph);
                            } else {
                            }
                        }, throwable -> Log.i(TAG, throwable.getMessage())
                );

    }

    @Override
    public void onGetFirstTimeFlag(Observable<Boolean> observable) {
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(isFirstTime -> {
                    if (isFirstTime) {
                        Log.i(TAG, isFirstTime.toString());
                    } else {
                        presenter.isDoneWork();
                    }
                }, throwable -> Log.i(TAG, throwable.getMessage())
        );
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