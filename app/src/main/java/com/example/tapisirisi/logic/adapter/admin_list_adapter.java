package com.example.tapisirisi.logic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tapisirisi.R;
import com.example.tapisirisi.logic.model.Motif;

import java.util.List;

public class admin_list_adapter extends BaseAdapter {

    private List<Motif> motifs;
    private Context context;

    public admin_list_adapter(Context context, List<Motif> motifs) {
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
                    inflate(R.layout.admin_row_list, parent, false);
        }

        // get current item to be displayed
        Motif currentMotif = (Motif) getItem(position);

        // get the TextView for item name and item description

        ImageView imageView = (ImageView) convertView.findViewById(R.id.image);


        //sets the text for item name and item description from the current item object
        imageView.setBackgroundResource(currentMotif.getDrawable());


        // returns the view for the current row
        return convertView;
    }

    public void setMotifs(List<Motif> motifs)
    {
        this.motifs = motifs;
    }

}

class adminViewHolder extends RecyclerView.ViewHolder {
    public ImageView motifLibelleImageView;

    public adminViewHolder(View view) {
        super(view);
        this.motifLibelleImageView = (ImageView) view.findViewById(R.id.image);
    }
}

