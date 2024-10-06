package com.example.foodplannerapplication.features.favorites.view;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodplannerapplication.R;
import com.example.foodplannerapplication.database.ConcreteLocalSource;
import com.example.foodplannerapplication.features.favorites.presenter.FavoritesPresenter;
import com.example.foodplannerapplication.models.Meal;
import com.example.foodplannerapplication.models.repository.Repo;
import com.example.foodplannerapplication.network.ApiClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public class FavoritesFragment extends Fragment implements FavoritesView, FavoritesAdapter.OnFavClick {
    private static final String TAG = "FavoritesFragment";

    private TextView psback;
    private Button btnback;
    private RecyclerView recyclerView;
    private FavoritesAdapter adapter;
    private List<Meal> favourites;

    private FavoritesPresenter presenter;
    private ItemTouchHelper.SimpleCallback simpleItemTouchCallback;
    private ItemTouchHelper itemTouchHelper;

    private int position;

    private MediaPlayer mediaPlayer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favourites = new ArrayList<>();
        adapter = new FavoritesAdapter(favourites, this);
        presenter = new FavoritesPresenter(this, Repo.getInstance(
                ApiClient.getInstance(),
                ConcreteLocalSource.getInstance(getContext())
        ));
        presenter.getFavorites();

        simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT /*| ItemTouchHelper.DOWN | ItemTouchHelper.UP*/) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                position = viewHolder.getAdapterPosition();
                presenter.deleteFavorite(adapter.getFavorites().get(position));
            }
        };
        itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.sfavorite);
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        psback = view.findViewById(R.id.ps_back);
        btnback = view.findViewById(R.id.btn_back);
        recyclerView = view.findViewById(R.id.rv_favorites);
        recyclerView.setAdapter(adapter);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        presenter.isDoneWork();

    }

    @Override
    public void onGetFavorites(Observable<List<Meal>> observable) {
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> adapter.setList(meals), throwable -> Log.i(TAG, throwable.getMessage()));
    }

    @Override
    public void onDeleteFavorites(Completable completable) {
        completable.observeOn(AndroidSchedulers.mainThread()).subscribe(() -> {
            favourites.remove(position);
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onGetDoneWorkFlag(Observable<Boolean> observable) {
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(isDoneWork -> {
                    if (isDoneWork) {
                        psback.setVisibility(View.INVISIBLE);
                        btnback.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                    } else {
                        psback.setVisibility(View.VISIBLE);
                        btnback.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.INVISIBLE);
                    }
                }, throwable -> {
                    psback.setVisibility(View.VISIBLE);
                    btnback.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                    Log.i(TAG, throwable.getMessage());
                });
    }

    @Override
    public void onClick(Meal meal) {
        FavoritesFragmentDirections.ActionFavoritesFragmentToDetailsFragment action = FavoritesFragmentDirections.actionFavoritesFragmentToDetailsFragment(meal);
        Navigation.findNavController(requireView()).navigate(action);
    }
}