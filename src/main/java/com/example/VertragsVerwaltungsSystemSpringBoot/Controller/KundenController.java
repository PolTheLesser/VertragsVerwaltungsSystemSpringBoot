package com.example.VertragsVerwaltungsSystemSpringBoot.Controller;

import com.example.VertragsVerwaltungsSystemSpringBoot.Model.Vertrag;
import com.example.VertragsVerwaltungsSystemSpringBoot.Services.VertragsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/neu") //TODO PostMapping   // WIEEEEEE?
    public ResponseEntity<Vertrag> postNeuVertrag() {
        return new ResponseEntity<Vertrag>(vertragsService.postNeu(), HttpStatus.CREATED);
    }

    @PostMapping("postAenderung") //TODO PostMapping
    public Vertrag postAenderungVertrag() {
        return vertragsService.postAenderung();
    }

    @DeleteMapping("/delete")
    public String deleteVertrag() {
        return vertragsService.deleteVertraegeVSNR();
    }
}
