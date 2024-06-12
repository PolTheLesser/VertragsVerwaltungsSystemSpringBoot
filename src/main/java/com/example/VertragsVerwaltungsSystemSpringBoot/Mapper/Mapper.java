package com.example.VertragsVerwaltungsSystemSpringBoot.Mapper;

import com.example.VertragsVerwaltungsSystemSpringBoot.Model.Vertrag;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public Vertrag jsonObjectToVertrag(JSONObject jsonObject) {

        Vertrag vertrag = new Vertrag();

        if (jsonObject.get("methode") == null && jsonObject.get("vertrag") == null){
            vertrag.setWagniskennziffer((int) (long) jsonObject.get("wagniskennziffer"));
            vertrag.setPreis(new Double(jsonObject.get("preis").toString()));
            vertrag.setVsnr((int) (long) jsonObject.get("vsnr"));
            vertrag.setVorname((String) jsonObject.get("vorname"));
            vertrag.setNachname((String) jsonObject.get("nachname"));
            vertrag.setGeburtsdatum((String) jsonObject.get("geburtsdatum"));
            vertrag.setAddresse((String) jsonObject.get("addresse"));
            vertrag.setVersicherungsbeginn((String) jsonObject.get("versicherungsbeginn"));
            vertrag.setFahrzeug_hersteller((String) jsonObject.get("fahrzeug_hersteller"));
            vertrag.setFahrzeug_typ((String) jsonObject.get("fahrzeug_typ"));
            vertrag.setFahrzeug_hoechstgeschwindigkeit((int) (long) jsonObject.get("fahrzeug_hoechstgeschwindigkeit"));
            vertrag.setAmtliches_kennzeichen((String) jsonObject.get("amtliches_kennzeichen"));
            vertrag.setAntrags_datum(jsonObject.get("antragsdatum"));
        } else if (jsonObject.get("methode").equals("GET") && jsonObject.get("aktion").equals("/vertrag/vsnr")) {
            vertrag.setVsnr((int) (long) jsonObject.get("vsnr"));

        } else if (jsonObject.get("methode").equals("POST") && jsonObject.get("aktion").equals("/preis")) {
            vertrag.setFahrzeug_hersteller((String) jsonObject.get("fahrzeug_hersteller"));
            vertrag.setGeburtsdatum((String) jsonObject.get("geburtsdatum"));
            vertrag.setFahrzeug_hoechstgeschwindigkeit((int) (long) jsonObject.get("fahrzeug_hoechstgeschwindigkeit"));

        } else if (jsonObject.get("methode").equals("POST") && jsonObject.get("aktion").equals("/aenderung")) {
            vertrag.setWagniskennziffer((int) (long) jsonObject.get("wagniskennziffer"));
            vertrag.setPreis(new Double(jsonObject.get("preis").toString()));
            vertrag.setVsnr((int) (long) jsonObject.get("vsnr"));
            vertrag.setVorname((String) jsonObject.get("vorname"));
            vertrag.setNachname((String) jsonObject.get("nachname"));
            vertrag.setGeburtsdatum((String) jsonObject.get("geburtsdatum"));
            vertrag.setAddresse((String) jsonObject.get("addresse"));
            vertrag.setVersicherungsbeginn((String) jsonObject.get("versicherungsbeginn"));
            vertrag.setFahrzeug_hersteller((String) jsonObject.get("fahrzeug_hersteller"));
            vertrag.setFahrzeug_typ((String) jsonObject.get("fahrzeug_typ"));
            vertrag.setFahrzeug_hoechstgeschwindigkeit((int) (long) jsonObject.get("fahrzeug_hoechstgeschwindigkeit"));
            vertrag.setAmtliches_kennzeichen((String) jsonObject.get("amtliches_kennzeichen"));

         } else if (jsonObject.get("methode").equals("POST") && jsonObject.get("aktion").equals("/neu")) {
             vertrag.setWagniskennziffer((int) (long) jsonObject.get("wagniskennziffer"));
             vertrag.setVsnr((int) (long) jsonObject.get("vsnr"));
             vertrag.setVorname((String) jsonObject.get("vorname"));
             vertrag.setNachname((String) jsonObject.get("nachname"));
             vertrag.setGeburtsdatum((String) jsonObject.get("geburtsdatum"));
             vertrag.setAddresse((String) jsonObject.get("addresse"));
             vertrag.setVersicherungsbeginn((String) jsonObject.get("versicherungsbeginn"));
             vertrag.setFahrzeug_hersteller((String) jsonObject.get("fahrzeug_hersteller"));
             vertrag.setFahrzeug_typ((String) jsonObject.get("fahrzeug_typ"));
             vertrag.setFahrzeug_hoechstgeschwindigkeit((int) (long) jsonObject.get("fahrzeug_hoechstgeschwindigkeit"));
             vertrag.setAmtliches_kennzeichen((String) jsonObject.get("amtliches_kennzeichen"));

        } else if (jsonObject.get("methode").equals("DELETE") && jsonObject.get("aktion").equals("/vertrag/vsnr")) {
            vertrag.setVsnr((int) (long) jsonObject.get("vsnr"));

        }
        return  vertrag;
    }
}
