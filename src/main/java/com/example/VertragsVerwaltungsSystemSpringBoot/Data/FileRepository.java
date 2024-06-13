package com.example.VertragsVerwaltungsSystemSpringBoot.Data;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;


@Repository
public class FileRepository {

    public JSONObject getJsonObject(String path) {

        try {
            JSONParser jsonParser = new JSONParser();

            FileReader reader = new FileReader(path);

            return (JSONObject) jsonParser.parse((reader));

        } catch (IOException e) {
            System.out.println("Bitte validiere, ob du alle Werte korrekt eingegeben hast.");
            return null;

        } catch (ParseException e) {
            System.out.println("Bitte validiere, ob du alle Werte korrekt eingegeben hast.");
            return null;
        }
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
}
