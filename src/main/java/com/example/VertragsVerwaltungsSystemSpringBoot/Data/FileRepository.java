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


@Repository
public class FileRepository {

    public JSONObject getJsonObject(String path) {

        JSONObject jsonObject = null;
        FileReader reader = null;

        try {
            JSONParser jsonParser = new JSONParser();

            reader = new FileReader(path);

            jsonObject = (JSONObject) jsonParser.parse(reader);

        } catch (IOException e) {
            System.out.println("Bitte validiere, ob du alle Werte korrekt eingegeben hast.");
        } catch (ParseException e) {
            System.out.println("Bitte validiere, ob du alle Werte korrekt eingegeben hast.");
        }
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Bitte validiere, ob du alle Werte korrekt eingegeben hast.");
        }
        return jsonObject;
    }

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

    public boolean deleteFile(String vsnr) {

        File delFile = null;

        try {

            delFile = new File(srcPath() + "/main/resources/vertraege/" + vsnr + ".json");

        } catch (NullPointerException e) {
            System.out.println("Bitte validiere, ob du alle Werte korrekt eingegeben hast.");
            return false;

        } catch (ClassCastException e) {
            System.out.println("Bitte validiere, ob du alle Werte korrekt eingegeben hast.");
            return false;
        }
        return delFile.delete();
    }

    public String srcPath() {

        String basePath = System.getProperty("user.dir");

        String srcPath = Paths.get(basePath, "src").toString();

        return srcPath.replace("\\", "\\\\");
    }

    public List<String> getFilenames() {

        String path = srcPath() + "/main/resources/vertraege";

        Stream<Path> walk;

        try {
            walk = Files.walk(Paths.get(path));

        } catch (IOException e) {
            throw new NoContractFoundException("Es sind noch keine Verträge vorhanden.");
        }

        List<String> result = walk
                .map(x -> x.toString())
                .filter(f -> f.endsWith(".json"))
                .collect(Collectors.toList());
        walk.close();

        if (result.isEmpty()) {
            throw new NoContractFoundException("Es sind noch keine Verträge vorhanden.");
        }

        return result;
    }
}
