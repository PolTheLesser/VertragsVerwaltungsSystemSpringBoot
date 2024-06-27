package com.example.VertragsVerwaltungsSystemSpringBoot.Controller;

import com.example.VertragsVerwaltungsSystemSpringBoot.Model.Vertrag;
import com.example.VertragsVerwaltungsSystemSpringBoot.Services.VertragsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vertraege")
public class VertraegeController {

    @Autowired
    private VertragsService vertragsService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<Vertrag>> getVertraege() {
        return ResponseEntity.ok(vertragsService.findVertraege());
    }
}