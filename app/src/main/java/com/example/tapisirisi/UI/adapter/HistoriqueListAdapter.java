package com.example.tapisirisi.UI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tapisirisi.R;
import com.example.tapisirisi.model.Motif;

import java.util.List;

public class HistoriqueListAdapter extends BaseAdapter {

    private List<Motif> motifs;
    private Context context;

    public HistoriqueListAdapter(Context context, List<Motif> motifs) {
        this.context = context;
        this.motifs = motifs;
    }

    @Override
    public int getCount() {
        return motifs.size();
    }

    @Override
    public Object getItem(int position) {
        return motifs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.activity_historique_item, parent, false);
        }

        // get current item to be displayed
        Motif currentMotif = (Motif) getItem(position);

        // get the TextView for item name and item description
        ImageView motifLibelleImageView = (ImageView)
                convertView.findViewById(R.id.historiqueItemImage);
        TextView motifTitleTextView = (TextView)
                convertView.findViewById(R.id.historiqueItemTitle);

        //sets the text for item name and item description from the current item object
//        motifLibelleImageView.setBackgroundResource(currentMotif.getDrawable());
        motifTitleTextView.setText(currentMotif.getLibelle());

        // returns the view for the current row
        return convertView;
    }

    public void setMotifs(List<Motif> motifs) {
        this.motifs = motifs;
    }
}

class ViewHolder extends RecyclerView.ViewHolder {
    public ImageView motifLibelleImageView;
    public TextView motifTitleTextView;

    public ViewHolder(View view) {
        super(view);
        this.motifLibelleImageView = (ImageView) view.findViewById(R.id.historiqueItemImage);
        this.motifTitleTextView = (TextView) view.findViewById(R.id.historiqueItemTitle);
    }
}
