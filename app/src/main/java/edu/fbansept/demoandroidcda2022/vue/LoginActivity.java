package edu.fbansept.demoandroidcda2022.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.fbansept.demoandroidcda2022.R;
import edu.fbansept.demoandroidcda2022.RequestManager;
import edu.fbansept.demoandroidcda2022.controller.UtilisateurController;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultJwtParser;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bouton = findViewById(R.id.button);

        bouton.setOnClickListener(view -> {

            EditText editTextEmail = findViewById(R.id.editTextEmail);
            EditText editTextPassword = findViewById(R.id.editTextTextPassword);

            UtilisateurController.getInstance().connexion(
                    this,
                    editTextEmail.getText().toString(),
                    editTextPassword.getText().toString(),
                    login -> {
                        Toast.makeText(this, "Bienvenue " + login, Toast.LENGTH_LONG).show();

                        startActivity(
                                new Intent(this, ListeMaterielActivity.class)
                        );
                    },
                    messageErreur -> {
                        Toast.makeText(this, messageErreur, Toast.LENGTH_LONG).show();
                    }
            );

        });
    }
}