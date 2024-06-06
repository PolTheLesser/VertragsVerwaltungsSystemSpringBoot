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

    @GetMapping("/vertraege")
    public ResponseEntity<List<Vertrag>> getVertraege() {
        return new ResponseEntity<List<Vertrag>>(vertragsService.getVertraege(), HttpStatus.CREATED);
    }

    @GetMapping("/vertrag/{vsnr}")
    public ResponseEntity<Vertrag> getVsnrVertrag(@PathVariable("vsnr") String vsnr) {
        return new ResponseEntity<Vertrag>(vertragsService.getVertrag(vsnr), HttpStatus.CREATED);
    }

    @PostMapping(value = "/neu",
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vertrag> postNeuVertrag(@ResponseBody ) {
        return new ResponseEntity<Vertrag>(vertragsService.postNeu(), HttpStatus.CREATED);
    }

    @PostMapping(value= "postAenderung",
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vertrag> postAenderungVertrag() {
        return new ResponseEntity<Vertrag>(vertragsService.postAenderung(), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/delete",
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteVertrag() {
        return new ResponseEntity<String>(vertragsService.deleteVertraegeVSNR(), HttpStatus.CREATED);
    }
}
