package com.example.tapisirisi.logic.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tapisirisi.R;
import com.example.tapisirisi.activities.Admin.ModifierMotif;
import com.example.tapisirisi.logic.model.Motif;
import com.example.tapisirisi.logic.model.UserMotif;

import java.io.Serializable;
import java.util.List;

public class admin_list_adapter extends BaseAdapter {

    private List<UserMotif> userMotifs;
    private Context context;

    LayoutInflater inflter;

    public admin_list_adapter(Context context, List<UserMotif> userMotifs) {
        this.context = context;
        this.userMotifs = userMotifs;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return userMotifs.size();
    }

    @Override
    public Object getItem(int position) {
        return userMotifs.get(position);
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
        UserMotif currentMotif = (UserMotif) getItem(position);

        // get the TextView for item name and item description

        ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
        imageView.setImageResource(R.drawable.ic_baseline_account_circle_24);
        Button btnModifier = (Button) convertView.findViewById(R.id.modifierUserMotif);
        Button btnSupprimer = (Button) convertView.findViewById(R.id.supprimerUserMotif);

//        btnModifier.setId((int) userMotifs.get(position).getId());
//        btnSupprimer.setId((int) userMotifs.get(position).getId());

        LinearLayout l = (LinearLayout) convertView.findViewById(R.id.lbtn);
        l.setId((int)userMotifs.get(position).getId());
btnModifier.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, ModifierMotif.class);
        Bundle b = new Bundle();
        b.putInt("idUserMotif", l.getId());
        for (UserMotif um:userMotifs) {
            if (um.getId() == l.getId()){
                Bundle bundle = new Bundle();
                bundle.putSerializable("userMotif", (Serializable) userMotifs.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        }


    }
});
Log.i("id", String.valueOf(l.getId()));

        return convertView;
    }

    public void setMotifs(List<Motif> motifs)
    {
        this.userMotifs = userMotifs;
    }

}

class adminViewHolder extends RecyclerView.ViewHolder {
    public ImageView motifImageView;


    public adminViewHolder(View view) {
        super(view);
        this.motifImageView = (ImageView) view.findViewById(R.id.image);

    }
}

