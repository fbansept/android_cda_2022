package edu.fbansept.demoandroidcda2022.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import edu.fbansept.demoandroidcda2022.R;
import edu.fbansept.demoandroidcda2022.model.Utilisateur;


public class EditionUtilisateurActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edition_utilisateur);

        Utilisateur utilisateur = (Utilisateur) getIntent()
                .getSerializableExtra("utilisateur");

        EditText editTextPrenom = findViewById(R.id.editTextEditionPrenom);
        EditText editTextNom = findViewById(R.id.editTextEditionNom);
        EditText editTextEmail = findViewById(R.id.editTextEditionEmail);

        editTextPrenom.setText(utilisateur.getPrenom());
        editTextNom.setText(utilisateur.getNom());
        editTextEmail.setText(utilisateur.getEmail());

        Button bouton = findViewById(R.id.buttonEnregistrerUtilisateur);
    }
}