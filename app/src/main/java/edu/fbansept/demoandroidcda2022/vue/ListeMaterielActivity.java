package edu.fbansept.demoandroidcda2022.vue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import edu.fbansept.demoandroidcda2022.R;
import edu.fbansept.demoandroidcda2022.controller.UtilisateurController;
import edu.fbansept.demoandroidcda2022.vue.adapter.ListeUtilisateurAdapteur;

public class ListeMaterielActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_materiel);

        RecyclerView recyclerViewUtilisateur = findViewById(R.id.listeUtilisateur);

        UtilisateurController
                .getInstance()
                .chargementListeUtilisateur(
                        this,
                        listeUtilisateur -> {

                            ListeUtilisateurAdapteur adapteur =
                                    new ListeUtilisateurAdapteur(listeUtilisateur);

                            recyclerViewUtilisateur.setLayoutManager(
                                    new LinearLayoutManager(this));

                            recyclerViewUtilisateur.setAdapter(adapteur);

                        },
                        erreur -> Toast.makeText(
                                this,
                                "erreur",
                                Toast.LENGTH_LONG).show()
                );
    }
}