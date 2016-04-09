package com.lod.movie_extended.ui.fragment.languages;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lod.movie_extended.R;
import com.lod.movie_extended.data.model.Film;
import com.lod.movie_extended.data.model.Language;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class LanguagesAdapter extends RecyclerView.Adapter<LanguagesAdapter.LanguageViewHolder> {

    private Film film;
    private boolean isSound;
    private List<Language> languages;
    private LanguagesPresenter presenter;
    private Context context;
    public LanguagesAdapter(LanguagesPresenter presenter, Context context) {
        this.presenter = presenter;
        this.context = context;
    }

    public void setFilm(Film film, boolean isSound) {
        this.film = film;
        this.isSound = isSound;
        languages = isSound? film.getSoundLanguages():film.getSubtitleLanguages();
    }

    @Override
    public LanguageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_languages, parent, false);
        return new LanguageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LanguageViewHolder holder, int position) {
        holder.languageNameTextView.setText(languages.get(position).getName());
        Language currentLanguage = isSound? film.getSelectedSoundLanguage(): film.getSelectedSubtitleLanguage();

        if(currentLanguage != null && currentLanguage == languages.get(position)) {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.primary_red));
            presenter.allowNext();
        }
        else {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        }
    }

    @Override
    public int getItemCount() {
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
            Language currentLanguage = languages.get(getAdapterPosition());
            if(isSound) {
                film.setSelectedSoundLanguage(currentLanguage);
            }
            else {
                film.setSelectedSubtitleLanguage(currentLanguage);
            }
            presenter.allowNext();
            notifyDataSetChanged();
        }
    }
}
