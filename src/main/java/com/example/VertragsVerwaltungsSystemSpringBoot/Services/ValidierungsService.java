package com.example.VertragsVerwaltungsSystemSpringBoot.Services;

import com.example.VertragsVerwaltungsSystemSpringBoot.Data.FileRepository;
import com.example.VertragsVerwaltungsSystemSpringBoot.Model.Vertrag;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ValidierungsService {
    // Klasse mit Methoden, die der Validierung sämtlicher Daten dienen

    @Autowired
    private FileRepository fileRepository;

    public boolean isPreisValid(Vertrag vertrag){

        try {
            if (!checkIfFahrzeugHerstellerExists(vertrag)) {
                return false;
            }

            int fahrzeugHoechstgeschwindigkeit = vertrag.getFahrzeug_hoechstgeschwindigkeit();

            if (fahrzeugHoechstgeschwindigkeit == 0 || fahrzeugHoechstgeschwindigkeit > 250) {
                return false;
            }

            String datum = vertrag.getGeburtsdatum();
            Date aktuallesDatum = new Date();

            Date geburtsdatum;

            try {
                geburtsdatum = new SimpleDateFormat("dd.MM.yyyy").parse(datum);

            } catch (ParseException e) {
                System.out.println("Datum im falschen Format angegeben. (DD/MM/YYYY)");
                return false;
            }


            if (isPersonUnderEighteen(geburtsdatum, aktuallesDatum)) {
                System.out.println("Die Person muss 18 Jahre oder älter sein");
                return false;
            }
        } catch (NullPointerException e) {
            System.out.println("Bitte validiere, ob du alle Werte korrekt eingegeben hast.");
            return false;

        } catch (ClassCastException e) {
            System.out.println("Bitte validiere, ob du alle Werte korrekt eingegeben hast.");
            return false;

        }
        return true;
    }

    public boolean isVersicherungsbeginnValid(Vertrag vertrag) {

        LocalDate versicherungsBeginn = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String versicherungsBeginnString = vertrag.getVersicherungsbeginn();

            versicherungsBeginn = LocalDate.parse(versicherungsBeginnString, formatter);

        } catch (Exception e) {
            System.out.println("Datum im falschen Format angegeben. (DD/MM/YYYY)");
            return false;
        }
        if (versicherungsBeginn.isBefore(LocalDate.now())) {
            System.out.println("Der Versicherungsbeginn darf nicht in der Vergangenheit liegen.");
            return false;
        }
        return true;
    }

    public boolean isAenderungVertragValid(Vertrag vertrag) {

        String vorname = null;
        String nachname = null;
        String addresse = null;
        String fahrzeugTyp = null;
        boolean richtigesKennzeichenFormat = false;

        try {
            vorname = vertrag.getVorname();

            nachname = vertrag.getNachname();

            addresse = vertrag.getAddresse();

            fahrzeugTyp = vertrag.getFahrzeug_typ();

            String kennzeichen = vertrag.getAmtliches_kennzeichen();

            richtigesKennzeichenFormat = !kennzeichen.matches("[A-Z]{1,3}-[A-Z]{1,2}-[0-9]{1,4}");

        } catch (NullPointerException e) {
            System.out.println("Bitte validiere, ob du alle Werte korrekt eingegeben hast.");
            return false;
        } catch (ClassCastException e) {
            System.out.println("Bitte validiere, ob du alle Werte korrekt eingegeben hast.");
            return false;
        }

        if (isPreisValid(vertrag) == false) {
            return false;
        }

        if (vorname == null) {
            return false;
        } else if (nachname.equals(null)) {
            return false;
        } else if (addresse.equals(null)) {
            return false;
        } else if (fahrzeugTyp.equals(null)) {
            return false;
        } else if (richtigesKennzeichenFormat) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isNeuVertragValid(Vertrag vertrag) {

        if (isAenderungVertragValid(vertrag) && isVersicherungsbeginnValid(vertrag)) {
            return true;
        }
        return false;
    }

    private static LocalDate getVersicherungsBeginn(JSONObject jsonObject) {

        String versicherungsBeginnString = (String) jsonObject.get("versicherungsbeginn");

        int jahrDesVersicherungsBeginns = Integer.parseInt(versicherungsBeginnString.substring(6));

        int monatDesVersicherungsBeginns = Integer.parseInt(versicherungsBeginnString.substring(3, 5));

        int tagDesVersicherungsBeginns = Integer.parseInt(versicherungsBeginnString.substring(0, 2));

        return LocalDate.of(jahrDesVersicherungsBeginns, monatDesVersicherungsBeginns, tagDesVersicherungsBeginns);
    }

    private boolean checkIfFahrzeugHerstellerExists(Vertrag vertrag) {

        String path = fileRepository.srcPath() + "\\\\main\\\\resources\\\\fahrzeugHersteller\\\\fahrzeugHersteller.json";

        JSONObject fahrzeugJson = fileRepository.getJsonObject(path);

        String fahrzeugHersteller = vertrag.getFahrzeug_hersteller().toLowerCase();

        if (fahrzeugJson.containsKey(fahrzeugHersteller)) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isPersonUnderEighteen(Date geburtsdatum, Date vergleichsdatum) {

        long millisProJahr = 1000L * 60 * 60 * 24 * 365;
        long alterInJahren = (vergleichsdatum.getTime() - geburtsdatum.getTime()) / millisProJahr;

        return alterInJahren < 18;
    }

    public boolean checkForApproval(String eingabe) {

        String[] approval = {"ja", "jo", "yes", "ye", "jau", "klar", "jq", "js", "jw", "jy", "ka", "ia", "ua", "ha", "ma"};

        List<String> approvalList = Arrays.asList(approval);

        if (approvalList.contains(eingabe)) {
            return true;
        }
        return false;
    }
}