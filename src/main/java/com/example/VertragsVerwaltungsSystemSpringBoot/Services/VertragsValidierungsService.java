package com.example.VertragsVerwaltungsSystemSpringBoot.Services;

import com.example.VertragsVerwaltungsSystemSpringBoot.Data.FileRepository;
import com.example.VertragsVerwaltungsSystemSpringBoot.Exceptions.NoContractFoundException;
import com.example.VertragsVerwaltungsSystemSpringBoot.Exceptions.ValidationError;
import com.example.VertragsVerwaltungsSystemSpringBoot.Exceptions.ValidationException;
import com.example.VertragsVerwaltungsSystemSpringBoot.Model.Vertrag;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Der VertragsValidierungsService bietet Methoden zur Validierung von Vertragsdaten.
 */
@Service
public class VertragsValidierungsService {

    @Autowired
    private FileRepository fileRepository;

    private List<ValidationError> errors = new ArrayList<>();

    /**
     * Überprüft die Vollständigkeit und Validität der Vertragsdaten.
     *
     * @param vertrag der zu validierende Vertrag
     */
    public void verifyContract(Vertrag vertrag) {
        // Überprüft, ob Vorname, Nachname und Adresse gültig sind
        verifyStringAttribute("Vorname", vertrag.getVorname());
        verifyStringAttribute("Nachname", vertrag.getNachname());
        verifyStringAttribute("Addresse", vertrag.getAddresse());

        // Überprüft, ob das amtliche Kennzeichen ein gültiges Format hat
        isKennzeichenValid(vertrag.getAmtliches_kennzeichen());

        // Überprüft, ob das Versicherungsbeginndatum gültig ist
        verifyDate("Versicherungsbeginn", vertrag.getVersicherungsbeginn());

        // Validiert weitere Attribute, die für die Preisberechnung relevant sind
        isPreisValid(vertrag);

        // Wenn Fehler vorhanden sind, wird eine Ausnahme geworfen
        if (!errors.isEmpty()) {
            String errorString = "\n";
            for (ValidationError error : getErrors()) {
                errorString += "Fehler bei " + error.getAttribute() + ": " + error.getMessage() + "\n";
            }
            throw new ValidationException(errorString);
        }
    }

    /**
     * Überprüft die Änderungen an einem bestehenden Vertrag.
     *
     * @param vertrag der geänderte Vertrag
     */
    public void verifyChanges(Vertrag vertrag) {
        // Ermittelt den Pfad zur Vertragsdatei anhand der Vertragsnummer (VSNR)
        String path = fileRepository.srcPath() + "/main/resources/vertraege/" + vertrag.getVsnr() + ".json";

        // Überprüft, ob der Vertrag existiert
        doesContractExist(path);

        // Überprüft die Vertragsdaten
        verifyContract(vertrag);
    }

    /**
     * Validiert spezifische Attribute des Vertrags, die für die Preisberechnung relevant sind.
     *
     * @param vertrag der zu validierende Vertrag
     */
    public void isPreisValid(Vertrag vertrag) {
        // Überprüft den Fahrzeughersteller
        verifyVehicleManufacturer(vertrag.getFahrzeug_hersteller());

        // Überprüft die Höchstgeschwindigkeit des Fahrzeugs
        verifyMaxSpeed(vertrag.getFahrzeug_hoechstgeschwindigkeit());

        // Überprüft das Geburtsdatum
        verifyDate("Geburtsdatum", vertrag.getGeburtsdatum());

        // Überprüft, ob die Person mindestens 18 Jahre alt ist
        isPersonUnderEighteen(vertrag.getGeburtsdatum());
    }

    /**
     * Überprüft den Fahrzeughersteller.
     *
     * @param hersteller der Fahrzeughersteller
     */
    private void verifyVehicleManufacturer(String hersteller) {
        if (hersteller == null || hersteller.isEmpty()) {
            errors.add(new ValidationError("Fahrzeughersteller", "Bitte geben Sie einen Hersteller an."));
            return;
        }
        // Pfad zur Datei mit den Fahrzeugherstellern
        String path = fileRepository.srcPath() + "/main/resources/fahrzeugHersteller/fahrzeugHersteller.json";

        // Holt das JSON-Objekt, das die Fahrzeughersteller enthält
        JSONObject fahrzeugJson = fileRepository.getJsonObject(path);

        // Überprüft, ob der angegebene Hersteller in der Datei vorhanden ist
        if (!fahrzeugJson.containsKey(hersteller.toLowerCase())) {
            errors.add(new ValidationError("Fahrzeughersteller", "Dieser Hersteller wird nicht versichert."));
        }
    }

    /**
     * Überprüft die Höchstgeschwindigkeit des Fahrzeugs.
     *
     * @param vMax die Höchstgeschwindigkeit
     */
    private void verifyMaxSpeed(int vMax) {
        if (vMax > 250) {
            errors.add(new ValidationError("Höchstgeschwindigkeit", "Die Höchstgeschwindigkeit darf 250 km/h nicht überschreiten."));
        }
        if (vMax <= 0) {
            errors.add(new ValidationError("Höchstgeschwindigkeit", "Die Höchstgeschwindigkeit muss mehr als 0 km/h betragen."));
        }
    }

    /**
     * Überprüft das Datumsformat eines Attributes.
     *
     * @param attributeName der Name des Attributes
     * @param date das zu überprüfende Datum
     */
    private void verifyDate(String attributeName, String date) {
        if (date == null || date.isEmpty()) {
            errors.add(new ValidationError(attributeName, "Eingabe konnte nicht validiert werden, bitte überprüfen Sie die Eingabe."));
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        try {
            LocalDate birthDate = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            errors.add(new ValidationError(attributeName, "Falsches Datumsformat. Erwartet: dd.MM.yyyy."));
        }
    }

    /**
     * Überprüft, ob eine Person jünger als 18 Jahre ist.
     *
     * @param geburtsdatum das Geburtsdatum der Person
     */
    private void isPersonUnderEighteen(String geburtsdatum) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        try {
            LocalDate birthDate = LocalDate.parse(geburtsdatum, formatter);
            LocalDate currentDate = LocalDate.now();
            long age = ChronoUnit.YEARS.between(birthDate, currentDate);

            if (age < 18) {
                errors.add(new ValidationError("Geburtsdatum", "Person is under 18 years old."));
            }
        } catch (DateTimeParseException e) {
            // Wenn ein Fehler geworfen wird, wird eine DateTimeParseException geworfen
        }
    }

    /**
     * Überprüft, ob ein String-Attribut nicht leer oder null ist.
     *
     * @param attributeName der Name des Attributes
     * @param stringAttr der zu überprüfende String-Wert
     */
    private void verifyStringAttribute(String attributeName, String stringAttr) {
        if (stringAttr == null || stringAttr.isEmpty()) {
            errors.add(new ValidationError(attributeName, "Eingabe konnte nicht validiert werden, bitte überprüfen Sie die Eingabe."));
        }
    }

    /**
     * Überprüft das Format des amtlichen Kennzeichens.
     *
     * @param kennzeichen das amtliche Kennzeichen
     */
    private void isKennzeichenValid(String kennzeichen) {
        if (kennzeichen == null || kennzeichen.isEmpty()) {
            errors.add(new ValidationError("Amtliches Kennzeichen", "Eingabe konnte nicht validiert werden, bitte überprüfen Sie die Eingabe."));
            return;
        }

        String pattern = "^[A-ZÄÖÜ]{1,3}-[A-ZÄÖÜ]{1,2}-\\d{1,4}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(kennzeichen);

        if (!m.matches()) {
            errors.add(new ValidationError("Amtliches Kennzeichen", "Ungültiges Deutsches Kennzeichenformat. (XX-XX-1234)"));
        }
    }

    /**
     * Gibt die Liste der Fehler zurück.
     *
     * @return Liste der Fehler
     */
    public List<ValidationError> getErrors() {
        return errors;
    }

    /**
     * Überprüft, ob ein Vertrag an einem bestimmten Pfad existiert.
     *
     * @param path der Pfad zur Datei des Vertrags
     */
    public void doesContractExist(String path) {
        File file = new File(path);
        if (!file.isFile()) {
            throw new NoContractFoundException("Der Vertrag existiert nicht.");
        }
    }
}
