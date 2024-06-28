package com.example.VertragsVerwaltungsSystemSpringBoot.Mapper;

import com.example.VertragsVerwaltungsSystemSpringBoot.Model.Vertrag;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

/**
 * Die Mapper-Klasse bietet Methoden zur Konvertierung von JSON-Objekten in Vertrag-Objekte.
 */
@Component
public class Mapper {

    /**
     * Konvertiert ein JSONObject in ein Vertrag-Objekt.
     *
     * @param jsonObject das JSONObject, das in ein Vertrag-Objekt konvertiert werden soll
     * @return das Vertrag-Objekt, das aus dem JSONObject erzeugt wurde
     */
    public Vertrag jsonObjectToVertrag(JSONObject jsonObject) {

        Vertrag vertrag = new Vertrag();

        // Prüfen, ob die Methode und Vertrag im JSONObject nicht null sind
        if (jsonObject.get("methode") == null && jsonObject.get("vertrag") == null) {
            // Setzen der Werte im Vertrag-Objekt aus dem JSONObject
            vertrag.setWagniskennziffer(Integer.parseInt(jsonObject.get("wagniskennziffer").toString()));
            vertrag.setPreis(Double.parseDouble(jsonObject.get("preis").toString()));
            vertrag.setVsnr(Integer.parseInt(jsonObject.get("vsnr").toString()));
            vertrag.setVorname((String) jsonObject.get("vorname"));
            vertrag.setNachname((String) jsonObject.get("nachname"));
            vertrag.setGeburtsdatum((String) jsonObject.get("geburtsdatum"));
            vertrag.setAddresse((String) jsonObject.get("addresse"));
            vertrag.setVersicherungsbeginn((String) jsonObject.get("versicherungsbeginn"));
            vertrag.setFahrzeug_hersteller((String) jsonObject.get("fahrzeug_hersteller"));
            vertrag.setFahrzeug_typ((String) jsonObject.get("fahrzeug_typ"));
            vertrag.setFahrzeug_hoechstgeschwindigkeit(Integer.parseInt(jsonObject.get("fahrzeug_hoechstgeschwindigkeit").toString()));
            vertrag.setAmtliches_kennzeichen((String) jsonObject.get("amtliches_kennzeichen").toString());
            vertrag.setAntrags_datum(jsonObject.get("antragsdatum"));
        }

        return vertrag;
    }
}