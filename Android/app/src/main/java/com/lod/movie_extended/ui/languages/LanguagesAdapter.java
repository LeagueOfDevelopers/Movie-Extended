package com.lod.movie_extended.ui.languages;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lod.movie_extended.R;
import com.lod.movie_extended.data.model.Language;
import com.lod.movie_extended.events.LanguageSelected;
import com.squareup.otto.Bus;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class LanguagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Language> languages;
    private static final int TYPE_FOOTER = 1;

    LanguagesPresenter languagesPresenter;

    public LanguagesAdapter(LanguagesPresenter languagesPresenter) {
        this.languagesPresenter = languagesPresenter;
    }

    public void setLanguages(ArrayList<Language> languages) {
        this.languages = languages;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_FOOTER){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_button, parent, false);
            return new FooterAdapter(view);
        }
        else {
            View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_languages, parent, false);
            return new LanguageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder  holder, int position) {
        if (position == getBasicItemCount() && holder.getItemViewType() == TYPE_FOOTER) {
            onBindFooterView(holder, position);
        }
        else {
            ((LanguageViewHolder) holder).languageNameTextView.setText(languages.get(position).getName());
        }
    }

    private void onBindFooterView(RecyclerView.ViewHolder  holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        if(position == getBasicItemCount()){
            return TYPE_FOOTER;
        }
        return super.getItemViewType(position);
    }


    @Override
    public int getItemCount() {
        return languages.size() + 1;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public int getBasicItemCount() {
        return languages.size();
    }

    class LanguageViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.language_name_text_view)
        TextView languageNameTextView;
        @Bind(R.id.language_item_card_view)
        CardView cardView;

        public LanguageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick(R.id.language_item_card_view)
        public void onClick(CardView cardView) {
            languagesPresenter.onLanguageSelected();
        }
    }
    class FooterAdapter extends RecyclerView.ViewHolder{

        @Bind(R.id.go_to_film_button)
        Button goToFilmButton;

        public FooterAdapter(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick(R.id.go_to_film_button)
        public void onClick(View view) {
//            events.post(new LanguageSelected(null));
        }
    }
}
