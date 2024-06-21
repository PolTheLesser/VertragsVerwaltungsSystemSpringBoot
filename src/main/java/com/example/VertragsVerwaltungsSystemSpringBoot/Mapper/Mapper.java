package com.example.VertragsVerwaltungsSystemSpringBoot.Mapper;

import com.example.VertragsVerwaltungsSystemSpringBoot.Model.Vertrag;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public Vertrag jsonObjectToVertrag(JSONObject jsonObject) {

        Vertrag vertrag = new Vertrag();

        if (jsonObject.get("methode") == null && jsonObject.get("vertrag") == null){
            vertrag.setWagniskennziffer(new Integer(jsonObject.get("wagniskennziffer").toString()));
            vertrag.setPreis(new Double(jsonObject.get("preis").toString()));
            vertrag.setVsnr(new Integer(jsonObject.get("vsnr").toString()));
            vertrag.setVorname((String) jsonObject.get("vorname"));
            vertrag.setNachname((String) jsonObject.get("nachname"));
            vertrag.setGeburtsdatum((String) jsonObject.get("geburtsdatum"));
            vertrag.setAddresse((String) jsonObject.get("addresse"));
            vertrag.setVersicherungsbeginn((String) jsonObject.get("versicherungsbeginn"));
            vertrag.setFahrzeug_hersteller((String) jsonObject.get("fahrzeug_hersteller"));
            vertrag.setFahrzeug_typ((String) jsonObject.get("fahrzeug_typ"));
            vertrag.setFahrzeug_hoechstgeschwindigkeit(new Integer(jsonObject.get("fahrzeug_hoechstgeschwindigkeit").toString()));
            vertrag.setAmtliches_kennzeichen((String) jsonObject.get("amtliches_kennzeichen").toString());
            vertrag.setAntrags_datum(jsonObject.get("antragsdatum"));
        }
        return vertrag;
    }
}
