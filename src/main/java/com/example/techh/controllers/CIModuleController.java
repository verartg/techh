package com.example.techh.controllers;

import com.example.techh.Dtos.CIModuleDto;
import com.example.techh.Dtos.CIModuleInputDto;
import com.example.techh.services.CIModuleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ci-modules")
public class CIModuleController {
    private final CIModuleService ciModuleService;
    public CIModuleController(CIModuleService ciModuleService) {
        this.ciModuleService = ciModuleService;
    }

    @GetMapping
    public ResponseEntity<List<CIModuleDto>> getCIModules(){
        List<CIModuleDto> ciModuleDtos;
        return ResponseEntity.ok().body(ciModuleService.getCIModules());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CIModuleDto> getCIModule(@PathVariable("id") Long id) {
        CIModuleDto ciModule = ciModuleService.getCIModule(id);
        return ResponseEntity.ok().body(ciModule);
    }

    @PostMapping("/addcimodule")
    public ResponseEntity<CIModuleDto> addCIModule(@Valid @RequestBody CIModuleInputDto ciModule) {
        CIModuleDto cimodule = ciModuleService.saveCIModule(ciModule);
        return ResponseEntity.created(null).body(cimodule);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CIModuleDto> updateCIModule(@PathVariable Long id, @Valid @RequestBody CIModuleInputDto ciModule) {
        CIModuleDto dto = ciModuleService.updateCIModule(id, ciModule);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/{id}/television/{television_id}")
    public ResponseEntity<String> assignCIModuleToTelevision(@PathVariable Long id, @PathVariable Long television_id) {
        return ResponseEntity.ok(ciModuleService.assignCIModuleToTelevision(id, television_id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeCIModule(@PathVariable Long id) {
        ciModuleService.removeCIModule(id);
        return ResponseEntity.noContent().build();
    }
}
