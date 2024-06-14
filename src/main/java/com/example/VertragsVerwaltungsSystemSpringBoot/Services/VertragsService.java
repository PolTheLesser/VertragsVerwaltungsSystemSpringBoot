package com.example.VertragsVerwaltungsSystemSpringBoot.Services;

import com.example.VertragsVerwaltungsSystemSpringBoot.Data.FileRepository;
import com.example.VertragsVerwaltungsSystemSpringBoot.Model.Vertrag;
import com.example.VertragsVerwaltungsSystemSpringBoot.Mapper.Mapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class VertragsService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private Mapper mapper;

    @Autowired
    private PreisBerechnungsService preisBerechnungsService;

    @Autowired
    private VertragsValidierungsService validierungsService;

    public List<Vertrag> getVertraege() {

        JSONObject jsonObject;

        String path = fileRepository.srcPath() + "/main/resources/vertraege";

        Stream<Path> walk;
        List<Vertrag> vertraege = new ArrayList<>();

        try {
            walk = Files.walk(Paths.get(path));

        } catch (IOException e) {
            System.out.println("Es sind noch keine Verträge vorhanden.");
            return vertraege;
        }

        List<String> result = walk
                .map(x -> x.toString())
                .filter(f -> f.endsWith(".json"))
                .collect(Collectors.toList());

        for (String fileName : result) {

            // TODO? Jackson einbinden, um einen objectMapper zu haben, so muss ihn in nicht sleber bauen (besser oder schlechter?)

            jsonObject = fileRepository.getJsonObject(fileName);

            vertraege.add(mapper.jsonObjectToVertrag(jsonObject));
        }
        return vertraege;
    }

    public Vertrag getVertrag(String vsnr) {
        String path = fileRepository.srcPath() + "/main/resources/vertraege/" + vsnr + ".json";

        JSONObject jsonObject = fileRepository.getJsonObject(path);

        validierungsService.doesContractExist(jsonObject);

        return mapper.jsonObjectToVertrag(jsonObject);
    }

    public Vertrag postNeu(Vertrag vertrag) {

        File tempFile;

        int vsnrNeu = 100000;

        do {
            vsnrNeu++;

            tempFile = new File(fileRepository.srcPath() + "/main/resources/vertraege/" + vsnrNeu + ".json");

        } while (tempFile.isFile());

        String path = fileRepository.srcPath() + "/main/resources/vertraege/" + vsnrNeu + ".json";

        vertrag.setVsnr(vsnrNeu);

        validierungsService.verifyContract(vertrag);

        return datenUeberschreiben(path, vertrag);
    }

    public Vertrag postAenderung(Vertrag vertrag) {

        String path = fileRepository.srcPath() + "/main/resources/vertraege/" + vertrag.getVsnr() + ".json";

        validierungsService.verifyChanges(vertrag);

        return datenUeberschreiben(path, vertrag);
    }

    public String deleteVertraegeVSNR(String vsnr) {

        if (fileRepository.deleteFile(vsnr)) {
            return "Datei erfolgreich entfernt.";

        } else {
            return "Datei konnte nicht entfernt werden.";
        }
    }

    private Vertrag datenUeberschreiben(String path, Vertrag vertrag) {

        JSONObject jsonObjectNew = new JSONObject();

        vertrag.setAntrags_datum(antragsDatum());

        jsonObjectNew.put("vsnr", vertrag.getVsnr());
        jsonObjectNew.put("versicherungsbeginn", vertrag.getVersicherungsbeginn());
        jsonObjectNew.put("antragsdatum", "" + vertrag.getAntrags_datum());
        jsonObjectNew.put("amtliches_kennzeichen", vertrag.getAmtliches_kennzeichen());
        jsonObjectNew.put("fahrzeug_hersteller", vertrag.getFahrzeug_hersteller());
        jsonObjectNew.put("fahrzeug_typ", vertrag.getFahrzeug_typ());
        jsonObjectNew.put("fahrzeug_hoechstgeschwindigkeit", vertrag.getFahrzeug_hoechstgeschwindigkeit());
        jsonObjectNew.put("wagniskennziffer", vertrag.getWagniskennziffer());
        jsonObjectNew.put("nachname", vertrag.getNachname());
        jsonObjectNew.put("vorname", vertrag.getVorname());
        jsonObjectNew.put("addresse", vertrag.getAddresse());
        jsonObjectNew.put("geburtsdatum", vertrag.getGeburtsdatum());

        String putPreis = preisBerechnungsService.postPreis(vertrag);

        vertrag.setPreis(Double.parseDouble(putPreis));

        jsonObjectNew.put("preis", vertrag.getPreis());

        boolean isWritten = fileRepository.writeFile(path, jsonObjectNew);

        return vertrag;
    }

    private Object antragsDatum() {

        LocalDate datum = LocalDate.now();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        String formattedDate = datum.format(format);

        return formattedDate;
    }
}
