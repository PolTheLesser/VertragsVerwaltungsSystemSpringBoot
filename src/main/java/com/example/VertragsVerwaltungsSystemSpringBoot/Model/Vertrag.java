package com.example.VertragsVerwaltungsSystemSpringBoot.Model;

/**
 * Die Vertrag-Klasse repräsentiert ein Modell für einen Vertrag mit verschiedenen Attributen.
 */
public class Vertrag {

    // Attribute eines Vertrags
    private int wagniskennziffer;
    private int vsnr;
    private double preis;
    private String vorname;
    private String nachname;
    private String geburtsdatum;
    private String addresse;
    private String versicherungsbeginn;
    private String fahrzeug_hersteller;
    private String fahrzeug_typ;
    private int fahrzeug_hoechstgeschwindigkeit;
    private String amtliches_kennzeichen;
    private Object antrags_datum;

    // Getter und Setter für die Attribute

    public int getWagniskennziffer() {
        return wagniskennziffer;
    }

    public void setWagniskennziffer(int wagniskennziffer) {
        this.wagniskennziffer = wagniskennziffer;
    }

    public int getVsnr() {
        return vsnr;
    }

    public void setVsnr(int vsnr) {
        this.vsnr = vsnr;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(String geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    public String getVersicherungsbeginn() {
        return versicherungsbeginn;
    }

    public void setVersicherungsbeginn(String versicherungsbeginn) {
        this.versicherungsbeginn = versicherungsbeginn;
    }

    public String getFahrzeug_hersteller() {
        return fahrzeug_hersteller;
    }

    public void setFahrzeug_hersteller(String fahrzeug_hersteller) {
        this.fahrzeug_hersteller = fahrzeug_hersteller;
    }

    public String getFahrzeug_typ() {
        return fahrzeug_typ;
    }

    public void setFahrzeug_typ(String fahrzeug_typ) {
        this.fahrzeug_typ = fahrzeug_typ;
    }

    public int getFahrzeug_hoechstgeschwindigkeit() {
        return fahrzeug_hoechstgeschwindigkeit;
    }

    public void setFahrzeug_hoechstgeschwindigkeit(int fahrzeug_hoechstgeschwindigkeit) {
        this.fahrzeug_hoechstgeschwindigkeit = fahrzeug_hoechstgeschwindigkeit;
    }

    public String getAmtliches_kennzeichen() {
        return amtliches_kennzeichen;
    }

    public void setAmtliches_kennzeichen(String amtliches_kennzeichen) {
        this.amtliches_kennzeichen = amtliches_kennzeichen;
    }

    public Object getAntrags_datum() {
        return antrags_datum;
    }

    public void setAntrags_datum(Object antrags_datum) {
        this.antrags_datum = antrags_datum;
    }

    public Vertrag() {
    }
}