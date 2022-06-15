package edu.fbansept.demoandroidcda2022.controller;

import static android.provider.Settings.System.getString;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.fbansept.demoandroidcda2022.R;
import edu.fbansept.demoandroidcda2022.RequestManager;
import edu.fbansept.demoandroidcda2022.model.Utilisateur;
import edu.fbansept.demoandroidcda2022.vue.ListeMaterielActivity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.impl.DefaultJwtParser;

public class UtilisateurController {

    private static UtilisateurController instance = null;

    private UtilisateurController() {

    }

    public static UtilisateurController getInstance() {

        if(instance == null ) {
            instance = new UtilisateurController();
        }

        return instance;

    }

    public interface ConnexionSuccessListener {
        void onConnexionSuccessListener(String login);
    }

    public interface ConnexionErrorListener {
        void onConnexionErrorListener(String messageErreur);
    }

    public void connexion(
            Context context,
            String email,
            String password,
            ConnexionSuccessListener successListener,
            ConnexionErrorListener errorListener) {

        Map<String, String> body= new HashMap<>();
        body.put("email", email);
        body.put("motDePasse", password);

        JsonObjectRequest requete = new JsonObjectRequest(
                Request.Method.POST,
                "http://46.105.124.25:8080/demo-0.0.20-SNAPSHOT/connexion",
                new JSONObject(body),
                reponse -> {

                    try {
                        if(reponse.has("erreur")){
                            errorListener.onConnexionErrorListener( reponse.getString("erreur"));
                            //Toast.makeText(this, reponse.getString("erreur"), Toast.LENGTH_LONG).show();
                        } else {

                            String[] splitToken = reponse.getString("token").split("\\.");
                            String unsignedToken = splitToken[0] + "." + splitToken[1] + ".";

                            DefaultJwtParser parser = new DefaultJwtParser();
                            Jwt<?, ?> jwt = parser.parse(unsignedToken);
                            Claims donneesToken = (Claims) jwt.getBody();

                            SharedPreferences stockageIdentifiants = context.getSharedPreferences(
                                    "identifiants", Context.MODE_PRIVATE);

                            SharedPreferences.Editor editeur = stockageIdentifiants.edit();
                            editeur.putString("token", reponse.getString("token"));
                            editeur.apply();

                            successListener.onConnexionSuccessListener(donneesToken.getSubject());
                        }

                    } catch (JSONException e) {
                        errorListener.onConnexionErrorListener( "Réponse inattendue");
                        e.printStackTrace();
                    }
                },
                erreur -> {
                    errorListener.onConnexionErrorListener("Vérifiez votre connexion");
                    erreur.printStackTrace();
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("Access-Control-Allow-Origin", "*");
                return params;
            }
        };

        RequestManager.getInstance(context).addToRequestQueue(requete);
    }

    public interface ChargementListeUtilisateurSuccessListener {
        void onChargementListeUtilisateurSuccessListener(List<Utilisateur> listeUtilisateur);
    }

    public interface ChargementListeUtilisateurErrorListener {
        void onChargementListeUtilisateurErrorListener(String messageErreur);
    }

    public void chargementListeUtilisateur(
            Context context,
            ChargementListeUtilisateurSuccessListener successListener,
            ChargementListeUtilisateurErrorListener errorListener) {

        JsonArrayRequest requete = new JsonArrayRequest(
                "http://46.105.124.25:8080/demo-0.0.20-SNAPSHOT/liste-utilisateur",
                reponse -> {

                    try {
                        ArrayList<Utilisateur> listeUtilisateur= new ArrayList<>();

                        for (int i = 0; i < reponse.length(); i++){
                            JSONObject object = reponse.getJSONObject(i);
                            Utilisateur utilisateur = new Utilisateur(
                                    object.getInt("id"),
                                    object.getString("email"),
                                    object.getString("prenom"),
                                    object.getString("nom")
                            );

                            listeUtilisateur.add(utilisateur);
                        }

                        successListener
                                .onChargementListeUtilisateurSuccessListener(listeUtilisateur);

                    } catch (JSONException e) {
                        errorListener
                                .onChargementListeUtilisateurErrorListener("Réponse inattendue");
                    }
                },
                erreur -> {
                    errorListener
                            .onChargementListeUtilisateurErrorListener("Erreur");
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                SharedPreferences stockageIdentifiants = context.getSharedPreferences(
                        "identifiants", Context.MODE_PRIVATE);

                String token = stockageIdentifiants.getString("token",null);

                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("Access-Control-Allow-Origin", "*");
                params.put("Authorization", "Bearer " + token);
                return params;
            }
        };

        RequestManager.getInstance(context).addToRequestQueue(requete);

    }

    public interface EditionUtilisateurSuccessListener {
        void onModificationUtilisateurSuccessListener(String message);
    }

    public interface EditionUtilisateurErrorListener {
        void onModificationUtilisateurErrorListener(String messageErreur);
    }

    public void modifierUtilisateur(
            Context context,
            Utilisateur utilisateur,
            EditionUtilisateurSuccessListener successListener,
            EditionUtilisateurErrorListener errorListener
            ){

        JsonObjectRequest requete = new JsonObjectRequest(
                Request.Method.POST,
                "http://46.105.124.25:8080/demo-0.0.20-SNAPSHOT/utilisateur",
                utilisateur.toJsonObject(),
                reponse -> {

                    try {
                        if(reponse.has("erreur")){
                            errorListener.onModificationUtilisateurErrorListener( reponse.getString("erreur"));
                            //Toast.makeText(this, reponse.getString("erreur"), Toast.LENGTH_LONG).show();
                        } else {

                            successListener.onModificationUtilisateurSuccessListener("Utilisateur modifié");
                        }

                    } catch (JSONException e) {
                        errorListener.onModificationUtilisateurErrorListener( "Réponse inattendue");
                        e.printStackTrace();
                    }
                },
                erreur -> {
                    errorListener.onModificationUtilisateurErrorListener("Vérifiez votre connexion");
                    erreur.printStackTrace();
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("Access-Control-Allow-Origin", "*");
                return params;
            }
        };

        RequestManager.getInstance(context).addToRequestQueue(requete);

    }

}
