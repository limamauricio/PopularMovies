package com.limamauricio.popularmovies.ui.review;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.limamauricio.popularmovies.R;
import com.limamauricio.popularmovies.model.Review;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.authorsReview)
    TextView review;

    @BindView(R.id.reviewAuthor)
    TextView author;

    public ReviewViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bind(final Review r){

        author.setText(r.getAuthor());
        review.setText(r.getReview());

    }
}
