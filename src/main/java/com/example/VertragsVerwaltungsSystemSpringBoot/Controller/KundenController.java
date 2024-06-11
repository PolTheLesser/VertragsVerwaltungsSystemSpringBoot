package com.example.VertragsVerwaltungsSystemSpringBoot.Controller;

import com.example.VertragsVerwaltungsSystemSpringBoot.Model.Vertrag;
import com.example.VertragsVerwaltungsSystemSpringBoot.Services.VertragsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping// == @Controller & @ResponseBody
public class KundenController { //TODO ResponseEntity zurückgeben

    @Autowired
    private VertragsService vertragsService;

    @GetMapping("/")
    public String index(){
        return "index";
    }



    @GetMapping("/vertraege") // Muss auch keine JSON lesen
    public ResponseEntity<List<Vertrag>> getVertraege() {
        return ResponseEntity.ok(vertragsService.getVertraege());
    }

    @GetMapping("/vertrag/{vsnr}") // Muss gar keine JSON lesen, da die vsnr ja schon in der URL steht (input JSON löschen?)
    public ResponseEntity<Vertrag> getVsnrVertrag(@PathVariable("vsnr") String vsnr) {
        return ResponseEntity.ok(vertragsService.getVertrag(vsnr));
    }

    @PostMapping(value = "/neu",
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vertrag> postNeuVertrag(@RequestBody Vertrag vertrag){
        return ResponseEntity.ok(vertragsService.postNeu(vertrag));
    }

    @PutMapping(value= "/ postAenderung",
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vertrag> postAenderungVertrag(@RequestBody Vertrag vertrag) {
        return ResponseEntity.ok(vertragsService.postAenderung(vertrag));
    }

    @DeleteMapping(value = "/delete", // Man könnte die DELETE-Methode genauso wie die vertrag/{vsnr} gestalten
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteVertrag(@RequestBody Vertrag vertrag) {
        return ResponseEntity.ok(vertragsService.deleteVertraegeVSNR(vertrag));
    }
}
