package edu.fbansept.demoandroidcda2022.model;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Utilisateur implements Serializable {

    private Integer id;
    private String email;
    private String prenom;
    private String nom;

    public Utilisateur(Integer id, String email, String prenom, String nom) {
        this.id = id;
        this.email = email;
        this.prenom = prenom;
        this.nom = nom;
    }

    public JSONObject toJsonObject(){
        Map<String, String> body= new HashMap<>();
        body.put("id",getId().toString());
        body.put("prenom", getPrenom());
        body.put("nom", getNom());
        body.put("email", getEmail());

        return new JSONObject(body);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
