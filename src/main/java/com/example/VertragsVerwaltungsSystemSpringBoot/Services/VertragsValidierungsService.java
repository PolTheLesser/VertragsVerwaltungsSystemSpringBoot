package com.example.VertragsVerwaltungsSystemSpringBoot.Services;

import com.example.VertragsVerwaltungsSystemSpringBoot.Data.FileRepository;
import com.example.VertragsVerwaltungsSystemSpringBoot.Exceptions.ValidationError;
import com.example.VertragsVerwaltungsSystemSpringBoot.Model.Vertrag;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class VertragsValidierungsService {

    @Autowired
    private FileRepository fileRepository;

    private List<ValidationError> errors = new ArrayList<>();

    public void verifyChanges(Vertrag vertrag) {
        verifyStringAttribute("Vorname", vertrag.getVorname());
        verifyStringAttribute("Nachname", vertrag.getNachname());
        verifyStringAttribute("Addresse", vertrag.getAddresse());
        isKennzeichenValid(vertrag.getAmtliches_kennzeichen());
        verifyDate("Versicherungsbeginn", vertrag.getVersicherungsbeginn());
        isPreisValid(vertrag);

        if (!errors.isEmpty()) {
            for (ValidationError error : errors) {
                System.out.println("Fehler beim " + error.getAttribute() + ": " + error.getMessage());
            }
        }
    }

    public void verifyContract(Vertrag vertrag) {
        verifyChanges(vertrag);
    }

    public void isPreisValid(Vertrag vertrag) {
        verifyVehicleManufacturer(vertrag.getFahrzeug_hersteller());
        verifyMaxSpeed(vertrag.getFahrzeug_hoechstgeschwindigkeit());
        verifyDate("Geburtsdatum", vertrag.getGeburtsdatum());
        isPersonUnderEighteen(vertrag.getGeburtsdatum());
    }

    private void verifyVehicleManufacturer(String hersteller) {
        if (hersteller == null || hersteller.isEmpty()) {
            errors.add(new ValidationError("Fahrzeughersteller", "Bitte geben Sie einen Hersteller an."));
            return;
        }
        String path = fileRepository.srcPath() + "/main/resources/fahrzeugHersteller/fahrzeugHersteller.json";

        JSONObject fahrzeugJson = fileRepository.getJsonObject(path);

        if (!fahrzeugJson.containsKey(hersteller)) {
            errors.add(new ValidationError("Fahrzughersteller", "Dieser Hersteller wird nicht versichert."));
        }
    }

    private void verifyMaxSpeed(int vMax) {
        if (vMax > 250) {
            errors.add(new ValidationError("Höchstgeschwindigkeit", "Die Höchstgeschindigkeit darf 250 km/h nicht überscchreiten."));
        }
        if (vMax <= 0) {
            errors.add(new ValidationError("Höchstgeschwindigkeit", "Die Höchstgeschwindigkeit muss mehr als 0 km/h betragen."));
        }
    }


    private void verifyDate(String attributeName, String date) {
        if (date == null || date.isEmpty()) {
            errors.add(new ValidationError(attributeName, "Eingabe konnte nicht validiert werden, bitte überprüfen Sie die Eingabe"));
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        LocalDate birthDate = null;
        try {
            birthDate = LocalDate.parse(date, formatter);
        } catch (Exception e) {
            errors.add(new ValidationError(date,"Falsches Datumsformat. dd.MM.yyyy erwartet."));
        }
    }

    private void isPersonUnderEighteen(String geburtsdatum) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        LocalDate birthDate = LocalDate.parse(geburtsdatum, formatter);

        LocalDate currentDate = LocalDate.now();
        long age = ChronoUnit.YEARS.between(birthDate, currentDate);

        if (age < 18) {
            errors.add(new ValidationError("geburtsdatum", "Person is under 18 years old."));
        }
    }

    private void verifyStringAttribute(String attributeName, String stringAttr) {
        if (stringAttr == null || stringAttr.isEmpty()) {
            errors.add(new ValidationError(attributeName, "Eingabe konnte nicht validiert werden, bitte überprüfen Sie die Eingabe."));
        }
    }

    private void isKennzeichenValid(String kennzeichen) {
        if (kennzeichen == null || kennzeichen.isEmpty()) {
            errors.add(new ValidationError("Amtliches Kennzeichen", "Eingabe konnte nicht validiert werden, bitte überprüfen Sie die Eingabe."));
        }

        String pattern = "^[A-ZÄÖÜ]{1,3}-[A-ZÄÖÜ]{1,2}-\\d{1,4}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(kennzeichen);

        if (!m.matches()) {
            errors.add(new ValidationError("Amtliches Kennzeichen", "Ungültiges Deutsches KennzeichenFormat. (XX-XX-1234)"));
        }
    }
}
