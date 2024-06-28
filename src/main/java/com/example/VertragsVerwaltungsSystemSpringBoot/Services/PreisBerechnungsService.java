package com.example.VertragsVerwaltungsSystemSpringBoot.Services;

import com.example.VertragsVerwaltungsSystemSpringBoot.Data.FileRepository;
import com.example.VertragsVerwaltungsSystemSpringBoot.Model.Vertrag;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;

/**
 * Der PreisBerechnungsService berechnet die monatlichen Versicherungskosten basierend auf den Vertragsdaten.
 */
@Service
public class PreisBerechnungsService {

    @Autowired
    private FileRepository fileRepository;

    /**
     * Berechnet die monatlichen Versicherungskosten basierend auf den Vertragsdaten.
     *
     * @param vertrag das Vertrag-Objekt, für das der Preis berechnet werden soll
     * @return der berechnete monatliche Versicherungspreis als String
     */
    public String postPreis(Vertrag vertrag) {

        // Extrahiere das Geburtsdatum aus dem Vertrag
        String geburtsdatum = vertrag.getGeburtsdatum();
        // Extrahiere das Geburtsjahr aus dem Geburtsdatum
        int geburtsjahr = Integer.parseInt(geburtsdatum.substring(6));

        // Höchstgeschwindigkeit des Fahrzeugs aus dem Vertrag
        int vMax = vertrag.getFahrzeug_hoechstgeschwindigkeit();

        // Berechnung des Alters
        int alter = Year.now().getValue() - geburtsjahr;

        // Vereinfachte Schadenfreiheitsklasse
        int sfKlasseVereinfacht = alter - 17;

        // Berechnung des Rabatts in Prozent basierend auf der vereinfachten Schadenfreiheitsklasse
        double rabattInProzent = getRabattInProzent(sfKlasseVereinfacht);

        // Herstellerbedingte Mehrkosten
        double herstellerBedingteMehrkosten = getHerstellerBedingteMehrkosten(vertrag);

        // Berechnung der monatlichen Versicherungssumme
        double versicherungsSummeMonatlich = (vMax) * herstellerBedingteMehrkosten * (1 - rabattInProzent);

        // Rückgabe des berechneten monatlichen Versicherungspreises als formatierter String
        return "" + Math.round(versicherungsSummeMonatlich * 100) / 100.00;
    }

    /**
     * Berechnet den Rabatt in Prozent basierend auf der vereinfachten Schadenfreiheitsklasse.
     *
     * @param sfKlasseVereinfacht die vereinfachte Schadenfreiheitsklasse
     * @return der Rabatt in Prozent als Dezimalzahl
     */
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

        // Konvertierung des Rabatts in Prozent
        double rabattInProzent = rabatt * 0.01;
        return rabattInProzent;
    }

    /**
     * Berechnet den Rabatt bis zur Schadenfreiheitsklasse 3.
     *
     * @param x die vereinfachte Schadenfreiheitsklasse
     * @return der Rabatt als Dezimalzahl
     */
    private double rechnungBisSFKlasseDrei(int x) {
        return 15 * x - 15;
    }

    /**
     * Berechnet den Rabatt bis zur Schadenfreiheitsklasse 7.
     *
     * @param x die vereinfachte Schadenfreiheitsklasse
     * @return der Rabatt als Dezimalzahl
     */
    private double rechnungBisSFKlasseSieben(int x) {
        double a = 0.41666666666667;
        double b = -7.5;
        double c = 47.0833333333334;
        double d = -55;

        return (a * x * x * x) + (b * x * x) + (c * x) + (d);
    }

    /**
     * Berechnet den Rabatt bis zur Schadenfreiheitsklasse 34.
     *
     * @param x die vereinfachte Schadenfreiheitsklasse
     * @return der Rabatt als Dezimalzahl
     */
    private double rechnungBisSFKlasseVierUndDreissig(int x) {
        double a = 0.0012809956559;
        double b = -0.1038687940241;
        double c = 3.5108100194322;
        double d = 30.4325822470923;

        return (a * x * x * x) + (b * x * x) + (c * x) + (d);
    }

    /**
     * Holt die herstellerbedingten Mehrkosten aus der Datei mit Fahrzeugherstellerdaten.
     *
     * @param vertrag das Vertrag-Objekt, für das die Mehrkosten geholt werden sollen
     * @return die herstellerbedingten Mehrkosten als Dezimalzahl
     */
    private double getHerstellerBedingteMehrkosten(Vertrag vertrag) {
        // Pfad zur Datei mit Fahrzeugherstellerdaten
        String path = fileRepository.srcPath() + "/main/resources/fahrzeugHersteller/fahrzeugHersteller.json";

        // Holt das JSON-Objekt mit Fahrzeugherstellerdaten aus der Datei
        JSONObject fahrzeugJson = fileRepository.getJsonObject(path);

        // Holt den Fahrzeughersteller aus dem Vertrag und konvertiert ihn zu Kleinbuchstaben
        String fahrzeugHersteller = vertrag.getFahrzeug_hersteller().toLowerCase();

        // Holt den herstellerbedingten Preis-Multiplikator aus dem JSON-Objekt
        double herstellerBedingterPreisMultiplikator = (double) fahrzeugJson.get(fahrzeugHersteller);

        return herstellerBedingterPreisMultiplikator;
    }
}