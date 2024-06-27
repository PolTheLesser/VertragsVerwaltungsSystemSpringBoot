package com.example.VertragsVerwaltungsSystemSpringBoot.Controller;

import com.example.VertragsVerwaltungsSystemSpringBoot.Model.Vertrag;
import com.example.VertragsVerwaltungsSystemSpringBoot.Services.VertragsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vertrag")
public class VertragController {

    @Autowired
    private VertragsService vertragsService;

    @GetMapping(value = "/{vsnr}")
    public ResponseEntity<Vertrag> getVertragFromVsnr(@PathVariable("vsnr") String vsnr) {
        return ResponseEntity.ok(vertragsService.findVertragWithVsnr(vsnr));
    }

    @PostMapping(value = "/new",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vertrag> postNeuVertragAnlegen(@RequestBody Vertrag vertrag) {
        return ResponseEntity.ok(vertragsService.neuVertragAnlegen(vertrag));
    }

    @PutMapping(value = "/change",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vertrag> putAenderungVertrag(@RequestBody Vertrag vertrag) {
        return ResponseEntity.ok(vertragsService.vertragsAenderung(vertrag));
    }

    @DeleteMapping(value = "/delete/{vsnr}")
    public ResponseEntity<String> deleteVertragVsnr(@PathVariable String vsnr) {
        return ResponseEntity.ok(vertragsService.deleteVertragByVsnr(vsnr));
    }
}
