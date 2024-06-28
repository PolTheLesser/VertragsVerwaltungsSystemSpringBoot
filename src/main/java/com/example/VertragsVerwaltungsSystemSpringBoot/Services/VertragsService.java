package com.example.VertragsVerwaltungsSystemSpringBoot.Services;

import com.example.VertragsVerwaltungsSystemSpringBoot.Data.FileRepository;
import com.example.VertragsVerwaltungsSystemSpringBoot.Mapper.Mapper;
import com.example.VertragsVerwaltungsSystemSpringBoot.Model.Vertrag;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Der VertragsService bietet Methoden zur Verwaltung von Vertragsdaten.
 */
@Service
public class VertragsService {

    // Injektion des FileRepository, Mapper, PreisBerechnungsService und Validerungesservice.
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private Mapper mapper;

    @Autowired
    private PreisBerechnungsService preisBerechnungsService;

    @Autowired
    private VertragsValidierungsService validierungsService;

    /**
     * Liest alle vorhandenen Verträge aus den JSON-Dateien und gibt sie als Liste von Vertrag-Objekten zurück.
     *
     * @return Liste von Vertrag-Objekten
     */
    public List<Vertrag> findVertraege() {
        // Liste zur Speicherung der gefundenen Verträge
        List<Vertrag> vertraege = new ArrayList<>();

        // Dateinamen aller Vertragsdateien abrufen
        List<String> result = fileRepository.getFilenames();

        // Für jeden Dateinamen die zugehörige JSON-Daten lesen und in Vertrag-Objekte umwandeln
        for (String fileName : result) {
            JSONObject jsonObject = fileRepository.getJsonObject(fileName);
            vertraege.add(mapper.jsonObjectToVertrag(jsonObject));
        }

        return vertraege;
    }

    /**
     * Sucht nach einem Vertrag anhand seiner Versicherungsnummer (VSNR).
     *
     * @param vsnr die Versicherungsnummer des gesuchten Vertrags
     * @return das gefundene Vertrag-Objekt
     */
    public Vertrag findVertragWithVsnr(String vsnr) {
        // Pfad zur Vertragsdatei anhand der Versicherungsnummer erstellen
        String path = fileRepository.srcPath() + "/main/resources/vertraege/" + vsnr + ".json";

        // JSON-Daten der Vertragsdatei lesen
        JSONObject jsonObject = fileRepository.getJsonObject(path);

        // Überprüfen, ob die Vertragsdatei existiert
        validierungsService.doesContractExist(path);

        // JSON-Daten in ein Vertrag-Objekt umwandeln und zurückgeben
        return mapper.jsonObjectToVertrag(jsonObject);
    }

    /**
     * Legt einen neuen Vertrag an und speichert ihn als JSON-Datei ab.
     *
     * @param vertrag der anzulegende Vertrag
     * @return der angelegte Vertrag
     */
    public Vertrag neuVertragAnlegen(Vertrag vertrag) {
        int vsnrNeu = 100000;

        // Generiere eine neue eindeutige Versicherungsnummer (VSNR)
        File tempFile;
        do {
            vsnrNeu++;
            tempFile = new File(fileRepository.srcPath() + "/main/resources/vertraege/" + vsnrNeu + ".json");
        } while (tempFile.isFile());

        // Setze die neue VSNR im Vertrag
        String path = fileRepository.srcPath() + "/main/resources/vertraege/" + vsnrNeu + ".json";
        vertrag.setVsnr(vsnrNeu);

        // Validiere den neuen Vertrag
        validierungsService.verifyContract(vertrag);

        // Schreibe die Vertragsdaten in die JSON-Datei und aktualisiere den Preis
        return datenUeberschreiben(path, vertrag);
    }

    /**
     * Aktualisiert einen vorhandenen Vertrag mit neuen Daten und speichert die Änderungen als JSON-Datei ab.
     *
     * @param vertrag der zu aktualisierende Vertrag
     * @return der aktualisierte Vertrag
     */
    public Vertrag vertragsAenderung(Vertrag vertrag) {
        String path = fileRepository.srcPath() + "/main/resources/vertraege/" + vertrag.getVsnr() + ".json";

        // Validiere die Änderungen am Vertrag
        validierungsService.verifyChanges(vertrag);

        // Schreibe die aktualisierten Vertragsdaten in die JSON-Datei und aktualisiere den Preis
        return datenUeberschreiben(path, vertrag);
    }

    /**
     * Löscht einen Vertrag anhand seiner Versicherungsnummer (VSNR).
     *
     * @param vsnr die Versicherungsnummer des zu löschenden Vertrags
     * @return eine Nachricht über den Erfolg des Löschvorgangs
     */
    public String deleteVertragByVsnr(String vsnr) {
        if (fileRepository.deleteFile(vsnr)) {
            return "Datei erfolgreich entfernt.";
        } else {
            return "Datei konnte nicht entfernt werden.";
        }
    }

    /**
     * Schreibt die Vertragsdaten in eine JSON-Datei und berechnet den Preis.
     *
     * @param path    der Pfad zur JSON-Datei
     * @param vertrag der Vertrag, dessen Daten geschrieben werden sollen
     * @return der geschriebene Vertrag
     */
    private Vertrag datenUeberschreiben(String path, Vertrag vertrag) {
        // Erstelle ein neues JSON-Objekt für die Vertragsdaten
        JSONObject jsonObjectNew = new JSONObject();

        // Setze das aktuelle Antragsdatum im Vertrag
        vertrag.setAntrags_datum(antragsDatum());

        // Setze die Vertragsdaten in das JSON-Objekt
        jsonObjectNew.put("vsnr", vertrag.getVsnr());
        jsonObjectNew.put("versicherungsbeginn", vertrag.getVersicherungsbeginn());
        jsonObjectNew.put("antragsdatum", "" + vertrag.getAntrags_datum());
        jsonObjectNew.put("amtliches_kennzeichen", vertrag.getAmtliches_kennzeichen());
        jsonObjectNew.put("fahrzeug_hersteller", vertrag.getFahrzeug_hersteller());
        jsonObjectNew.put("fahrzeug_typ", vertrag.getFahrzeug_typ());
        jsonObjectNew.put("fahrzeug_hoechstgeschwindigkeit", vertrag.getFahrzeug_hoechstgeschwindigkeit());
        jsonObjectNew.put("wagniskennziffer", 112); // Standardwert für Wagniskennziffer
        jsonObjectNew.put("nachname", vertrag.getNachname());
        jsonObjectNew.put("vorname", vertrag.getVorname());
        jsonObjectNew.put("addresse", vertrag.getAddresse());
        jsonObjectNew.put("geburtsdatum", vertrag.getGeburtsdatum());

        // Berechne und setze den Preis im Vertrag
        String putPreis = preisBerechnungsService.postPreis(vertrag);
        vertrag.setPreis(Double.parseDouble(putPreis));
        jsonObjectNew.put("preis", vertrag.getPreis());

        // Aktualisiere den Vertrag im Mapper und schreibe die Vertragsdaten in die JSON-Datei
        vertrag = mapper.jsonObjectToVertrag(jsonObjectNew);
        boolean isWritten = fileRepository.writeFile(path, jsonObjectNew);

        return vertrag;
    }

    /**
     * Gibt das aktuelle Antragsdatum als formatierten String zurück.
     *
     * @return das aktuelle Antragsdatum als String
     */
    private Object antragsDatum() {
        LocalDate datum = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return datum.format(format);
    }
}