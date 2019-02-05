package com.limamauricio.popularmovies.ui.trailers;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.limamauricio.popularmovies.R;
import com.limamauricio.popularmovies.model.Trailer;
import com.limamauricio.popularmovies.utils.OnClickListenerEvent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailerViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.img_trailer)
    ImageView trailerImage;

    @BindView(R.id.trailerName)
    TextView trailerName;

    @BindView(R.id.trailer_item)
    CardView trailerItem;

    private final String TRAILER_IMG = "http://img.youtube.com/vi/";
    private final String IMG_TRAILER_FORMAT = "/0.jpg";

    public TrailerViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bind(final Trailer trailer, final OnClickListenerEvent onClickListenerEvent){

        Glide.with(itemView)
                .load(TRAILER_IMG + trailer.getKey() + IMG_TRAILER_FORMAT)
                .into(trailerImage);

        trailerName.setText(trailer.getName());

        trailerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListenerEvent.onTrailerClick(trailer);
            }

    });
}
}