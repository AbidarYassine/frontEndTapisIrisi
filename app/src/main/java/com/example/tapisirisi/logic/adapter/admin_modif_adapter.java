package com.example.tapisirisi.logic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tapisirisi.R;
import com.example.tapisirisi.logic.model.Propriete;

import java.util.List;

public class admin_modif_adapter extends BaseAdapter {

    private List<Propriete> motifs;
    private Context context;

    public admin_modif_adapter(Context context, List<Propriete> motifs) {
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
                    inflate(R.layout.admin_row_prp_modif, parent, false);
        }

        // get current item to be displayed
        Propriete currentMotif = (Propriete) getItem(position);

        // get the TextView for item name and item description
        EditText lib = (EditText)
                convertView.findViewById(R.id.libelle);
        EditText desc = (EditText)
                convertView.findViewById(R.id.desc);

        //sets the text for item name and item description from the current item object
        desc.setText(currentMotif.getDescription());
        lib.setText(currentMotif.getLibelle());
       // desc.setText(proprties[position]);

        // returns the view for the current row
        return convertView;
    }

    public void setMotifs(List<Propriete> motifs)
    {
        this.motifs = motifs;
    }

}

class adminModifViewHolder extends RecyclerView.ViewHolder {

    public EditText modifLibelle;
    public EditText modifDescription;

    public adminModifViewHolder(View view) {
        super(view);
        this.modifDescription = (EditText) view.findViewById(R.id.desc);
        this.modifLibelle = (EditText) view.findViewById(R.id.libelle);
    }
}

