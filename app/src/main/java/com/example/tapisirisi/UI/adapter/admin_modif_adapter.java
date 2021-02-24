package com.example.tapisirisi.UI.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tapisirisi.ServiceImpl.propriete.deleteProprieteServiceImpl;
import com.example.tapisirisi.R;
import com.example.tapisirisi.model.Propriete;

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
        Button btn  = convertView.findViewById(R.id.enrgModifs);
        btn.setText("supprimer");
        btn.setOnClickListener(v -> {
            Log.i("info",String.valueOf(motifs.get(position).getId()));
            Intent intent = new Intent(context, deleteProprieteServiceImpl.class);
            Bundle bundle = new Bundle();
            bundle.putLong("idPropriete",motifs.get(position).getId() );
            intent.putExtras(bundle);
            context.startService(intent);
        });
        // get the TextView for item name and item description
        EditText lib = (EditText)
                convertView.findViewById(R.id.libellePropMotif);
        EditText desc = (EditText)
                convertView.findViewById(R.id.descPropMotif);
        ImageView imageView = convertView.findViewById(R.id.imageModif);
       // Picasso.get().load(currentMotif.getFileUrl()).into(imageView);
        //sets the text for item name and item description from the current item object
        desc.setText(currentMotif.getDescription());
        desc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                motifs.get(position).setDescription(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        lib.setText(currentMotif.getLibelle());
        lib.addTextChangedListener(new TextWatcher() {
            public  void onTextChanged(CharSequence cs, int s, int b, int c) {
                Log.i("Key:", cs.toString());
                motifs.get(position).setLibelle(cs.toString());
            }
            public synchronized void afterTextChanged(Editable editable) { }
            public void beforeTextChanged(CharSequence cs, int i, int j, int
                    k) { }
        });
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
        this.modifDescription = (EditText) view.findViewById(R.id.descPropMotif);
        this.modifLibelle = (EditText) view.findViewById(R.id.libellePropMotif);
    }
}

