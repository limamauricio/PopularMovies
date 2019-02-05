package com.limamauricio.popularmovies.ui.trailers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.limamauricio.popularmovies.R;
import com.limamauricio.popularmovies.model.Trailer;
import com.limamauricio.popularmovies.utils.OnClickListenerEvent;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerViewHolder> {

    private final List<Trailer> trailerList;
    private final OnClickListenerEvent onClickListenerEvent;

    public TrailerAdapter(List<Trailer> trailers, OnClickListenerEvent onClickListenerEvent) {
        this.trailerList = trailers;
        this.onClickListenerEvent = onClickListenerEvent;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TrailerViewHolder(LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.trailer_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder trailerViewHolder, int i) {
        Trailer trailer = this.trailerList.get(i);
        trailerViewHolder.bind(trailer, onClickListenerEvent);
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }
}
