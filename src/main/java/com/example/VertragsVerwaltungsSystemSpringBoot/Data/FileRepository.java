package com.example.VertragsVerwaltungsSystemSpringBoot.Data;

import com.example.VertragsVerwaltungsSystemSpringBoot.Exceptions.NoContractFoundException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Die FileRepository-Klasse bietet Methoden zum Lesen, Schreiben und Löschen
 * von JSON-Dateien sowie zum Abrufen von Dateinamen aus einem bestimmten Verzeichnis.
 */
@Repository
public class FileRepository {

    /**
     * Liest ein JSON-Objekt aus einer Datei.
     *
     * @param path der Pfad zur JSON-Datei
     * @return das JSON-Objekt oder null, wenn ein Fehler auftritt
     */
    public JSONObject getJsonObject(String path) {
        JSONObject jsonObject = null;
        FileReader reader = null;

        try {
            JSONParser jsonParser = new JSONParser();
            reader = new FileReader(path);
            jsonObject = (JSONObject) jsonParser.parse(reader);

        } catch (IOException | ParseException e) {
            System.out.println("Bitte validiere, ob du alle Werte korrekt eingegeben hast.");
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.out.println("Bitte validiere, ob du alle Werte korrekt eingegeben hast.");
            }
        }
        return jsonObject;
    }

    /**
     * Schreibt ein JSON-Objekt in eine Datei.
     *
     * @param path der Pfad zur Datei
     * @param jsonObject das zu schreibende JSON-Objekt
     * @return true, wenn der Schreibvorgang erfolgreich ist, andernfalls false
     */
    public boolean writeFile(String path, JSONObject jsonObject) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Löscht eine Datei anhand der Versicherungsnummer (VSNR).
     *
     * @param vsnr die Versicherungsnummer der zu löschenden Datei
     * @return true, wenn die Datei erfolgreich gelöscht wird, andernfalls false
     */
    public boolean deleteFile(String vsnr) {
        File delFile = new File(srcPath() + "/main/resources/vertraege/" + vsnr + ".json");
        if (!delFile.exists()) {
            System.out.println("Bitte validiere, ob du alle Werte korrekt eingegeben hast.");
            return false;
        }
        return delFile.delete();
    }

    /**
     * Gibt den Pfad zum src-Verzeichnis des Projekts zurück.
     *
     * @return der Pfad zum src-Verzeichnis
     */
    public String srcPath() {
        String basePath = System.getProperty("user.dir");
        String srcPath = Paths.get(basePath, "src").toString();
        return srcPath.replace("\\", "\\\\");
    }

    /**
     * Ruft eine Liste von Dateinamen im Verzeichnis der Verträge ab.
     *
     * @return eine Liste von Dateinamen
     * @throws NoContractFoundException wenn keine Verträge gefunden werden
     */
    public List<String> getFilenames() {
        String path = srcPath() + "/main/resources/vertraege";
        try (Stream<Path> walk = Files.walk(Paths.get(path))) {
            List<String> result = walk.map(Path::toString)
                    .filter(f -> f.endsWith(".json"))
                    .collect(Collectors.toList());
            if (result.isEmpty()) {
                throw new NoContractFoundException("Es sind noch keine Verträge vorhanden.");
            }
            return result;
        } catch (IOException e) {
            throw new NoContractFoundException("Es sind noch keine Verträge vorhanden.");
        }
    }
}
