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
    public List<Vertrag> getVertraege() {
        return vertragsService.getVertraege();
    }

    @GetMapping("/vertrag/{vsnr}")
    public Vertrag getVsnrVertrag(@PathVariable("vsnr") String vsnr) {
        return vertragsService.getVertrag(vsnr);
    }

    @PostMapping(value = "/neu",
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vertrag> postNeuVertrag() {
        return new ResponseEntity<Vertrag>(vertragsService.postNeu(), HttpStatus.CREATED);
    }

    @PostMapping(value= "postAenderung",
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public Vertrag postAenderungVertrag() {
        return vertragsService.postAenderung();
    }

    @DeleteMapping(value = "/delete",
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public String deleteVertrag() {
        return vertragsService.deleteVertraegeVSNR();
    }
}
