package com.example.VertragsVerwaltungsSystemSpringBoot.Services;

import com.example.VertragsVerwaltungsSystemSpringBoot.Data.FileRepository;
import com.example.VertragsVerwaltungsSystemSpringBoot.Model.Vertrag;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;

@Service
public class PreisBerechnungsService {

    @Autowired
    private FileRepository fileRepository;

    public String postPreis(Vertrag vertrag) {

        String geburtsdatum = vertrag.getGeburtsdatum();

        int geburtsjahr = Integer.parseInt(geburtsdatum.substring(6));

        int vMax = vertrag.getFahrzeug_hoechstgeschwindigkeit();

        int alter;
        int sfKlasseVereinfacht;

        double rabattInProzent = 0;

        alter = Year.now().getValue() - geburtsjahr;

        sfKlasseVereinfacht = alter - 17;

        rabattInProzent = getRabattInProzent(sfKlasseVereinfacht);

        double herstellerBedingteMehrkosten = getHerstellerBedingteMehrkosten(vertrag);

        double versicherungsSummeMonatlich = (vMax) * herstellerBedingteMehrkosten * (1 - rabattInProzent);

        return "" + Math.round(versicherungsSummeMonatlich * 100) / 100.00;
    }

    private double getRabattInProzent(int sfKlasseVereinfacht) {

        double rabatt;

        if (sfKlasseVereinfacht <= 3) {
            rabatt = rechnungBisSFKlasseDrei(sfKlasseVereinfacht);
        } else if (sfKlasseVereinfacht <= 7) {
            rabatt = rechnungBisSFKlasseSieben(sfKlasseVereinfacht);
        } else if (sfKlasseVereinfacht <= 34) {
            rabatt = rechnungBisSFKlasseVierUndDreissig(sfKlasseVereinfacht);
        } else {
            rabatt = 80;
        }
        double rabattInProzent = rabatt * 0.01;
        return rabattInProzent;
    }

    private double rechnungBisSFKlasseDrei(int x) {
        return 15 * x - 15;
    }

    private double rechnungBisSFKlasseSieben(int x) {
        double a = 0.41666666666667;
        double b = -7.5;
        double c = 47.0833333333334;
        double d = -55;

        return (a * x * x * x) + (b * x * x) + (c * x) + (d);
    }

    private double rechnungBisSFKlasseVierUndDreissig(int x) {
        double a = 0.0012809956559;
        double b = -0.1038687940241;
        double c = 3.5108100194322;
        double d = 30.4325822470923;

        return (a * x * x * x) + (b * x * x) + (c * x) + (d);
    }

    private double getHerstellerBedingteMehrkosten(Vertrag vertrag) {

        String path = fileRepository.srcPath() + "/main/resources/fahrzeugHersteller/fahrzeugHersteller.json";

        JSONObject fahrzeugJson = fileRepository.getJsonObject(path);

        String fahrzeugHersteller = vertrag.getFahrzeug_hersteller().toLowerCase();

        double herstellerBedingterPreisMultiplikator = (double) fahrzeugJson.get(fahrzeugHersteller);

        return herstellerBedingterPreisMultiplikator;
    }
}
