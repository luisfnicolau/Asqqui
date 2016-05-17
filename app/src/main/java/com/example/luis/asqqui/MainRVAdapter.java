package com.example.luis.asqqui;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luis.asqqui.data.DatabaseContract;

/**
 * Created by Luis-DELL on 3/31/2016.
 */
public class MainRVAdapter extends RecyclerView.Adapter<MainRVAdapter.PoliticianViewHolder> {

    Cursor cursor;
    Context context;
    int party;
    private int COLUMN_INDEX_NAME;
    private int COLUMN_INDEX_PARTY;
    private int COLUMN_INDEX_NUMBER;
    private int COLUMN_INDEX_PHOTO;

    public MainRVAdapter(Context context, int position) {
        this.context = context;
        party = position;
        ContentResolver resolver = context.getContentResolver();
        cursor = resolver.query(DatabaseContract.PoliticianEntry.CONTENT_URI,
                null,
                DatabaseContract.PoliticianEntry.COLUMN_POSITION + " = " + position,
                null,
                null);
        if (cursor != null) {
            COLUMN_INDEX_NAME = cursor.getColumnIndex(DatabaseContract.PoliticianEntry.COLUMN_NAME);
            COLUMN_INDEX_PARTY = cursor.getColumnIndex(DatabaseContract.PoliticianEntry.COLUMN_PARTY);
            COLUMN_INDEX_NUMBER = cursor.getColumnIndex(DatabaseContract.PoliticianEntry.COLUMN_VOTE_NUMBER);
            COLUMN_INDEX_PHOTO = cursor.getColumnIndex(DatabaseContract.PoliticianEntry.COLUMN_PHOTO);
        }
    }

    @Override
    public PoliticianViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_2, parent, false);
        PoliticianViewHolder vh = new PoliticianViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PoliticianViewHolder holder, int position) {
        if (cursor != null) {
            if (cursor.moveToPosition(position)) {
                holder.textCandidateName.setText(cursor.getString(COLUMN_INDEX_NAME));
                holder.candidateParty.setText(Utility.getPartyNamebyId(context, cursor.getInt(COLUMN_INDEX_PARTY)));
                holder.candidateNumber.setText(cursor.getString(COLUMN_INDEX_NUMBER));
                holder.candidateImage.setImageBitmap(Utility.convertBytesToImage(cursor.getBlob(COLUMN_INDEX_PHOTO)));
            }
        } else {
            holder.candidateParty.setText(Utility.getPartyNamebyId(context, party));
            holder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (cursor != null) {
            return cursor.getCount();
        } else {
            return 5;
        }
    }

    public static class PoliticianViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView textCandidateName;
        TextView textCandidateShortDescription;
        ImageView candidateImage;
        TextView candidateNumber;
        TextView candidateParty;

        PoliticianViewHolder(View itemView) {
            super(itemView);
            textCandidateName = (TextView) itemView.findViewById(R.id.text_candidate_name);
//            textCandidateShortDescription = (TextView)itemView.findViewById(R.id.text_candidate_short_description);
            candidateImage = (ImageView) itemView.findViewById(R.id.candidate_image);
            candidateNumber = (TextView) itemView.findViewById(R.id.candidate_number);
            candidateParty = (TextView) itemView.findViewById(R.id.candidate_party);
            cv = (CardView) itemView.findViewById(R.id.item_card_view);
        }

    }
}
