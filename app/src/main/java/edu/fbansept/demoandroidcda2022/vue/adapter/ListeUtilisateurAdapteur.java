package edu.fbansept.demoandroidcda2022.vue.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import edu.fbansept.demoandroidcda2022.model.Utilisateur;
import edu.fbansept.demoandroidcda2022.R;
import edu.fbansept.demoandroidcda2022.vue.EditionUtilisateurActivity;

public class ListeUtilisateurAdapteur extends RecyclerView.Adapter<ListeUtilisateurAdapteur.UtilisateurViewHolder> {

    static class UtilisateurViewHolder extends RecyclerView.ViewHolder {

        TextView textViewPrenom;
        TextView textViewNom;
        Button boutonEditer;
        Button boutonSupprimer;

        public UtilisateurViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNom = itemView.findViewById(R.id.nomTextView);
            textViewPrenom = itemView.findViewById(R.id.prenomTextView);
            boutonEditer = itemView.findViewById(R.id.buttonEditerUtilisateur);
            boutonSupprimer = itemView.findViewById(R.id.buttonSupprimerUtilisateur);
        }
    }

    private List<Utilisateur> listeUtilisateur;

    public ListeUtilisateurAdapteur(List<Utilisateur> listeUtilisateur) {

        this.listeUtilisateur = listeUtilisateur;

    }

    @NonNull
    @Override
    public UtilisateurViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vue = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.element_liste_utilisateur,parent,false);

        return new UtilisateurViewHolder(vue);
    }

    @Override
    public void onBindViewHolder(@NonNull UtilisateurViewHolder holder, int position) {
        holder.textViewPrenom
                .setText(this.listeUtilisateur.get(position).getPrenom());

        holder.textViewNom
                .setText(this.listeUtilisateur.get(position).getNom());

        holder.boutonEditer.setOnClickListener(e -> {

            Intent intent = new Intent(
                    holder.itemView.getContext(),
                    EditionUtilisateurActivity.class);

            intent.putExtra("utilisateur", listeUtilisateur.get(position));

            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return this.listeUtilisateur.size();
    }
}
