package com.example.techh.controllers;

import com.example.techh.exceptions.RecordNotFoundException;
import com.example.techh.exceptions.TooManyCharException;
import com.example.techh.models.Television;
import com.example.techh.repositories.TelevisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;


@RestController
@RequestMapping("/televisions")
public class TelevisionController {
    @Autowired
    private TelevisionRepository televisionRepository;

    public TelevisionController(TelevisionRepository televisionRepository){
        this.televisionRepository = televisionRepository;
    }

    @GetMapping
    public ResponseEntity<Iterable<Television>> getTelevisions() {
        return ResponseEntity.ok(televisionRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Television> getTelevision(@PathVariable("id") Long id) {
        Optional<Television> optionalTelevision = televisionRepository.findById(id);
        if (optionalTelevision.isEmpty()) {
            throw new RecordNotFoundException("Id " + id + " does not exist");
        }
        Television t = optionalTelevision.get();
        return ResponseEntity.ok().body(t);
    }

    @PostMapping("/addtelevision")
    public ResponseEntity<Television> addTelevision(@RequestBody Television t) throws TooManyCharException {
        if (t.getBrand().length() > 20) {
            throw new TooManyCharException("Name of brand can't be longer than 20 characters");
        }
        televisionRepository.save(t);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/" + t.getId()).toUriString());
        return ResponseEntity.created(uri).body(t);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Television> updateTelevision(@PathVariable Long id, @RequestBody Television t) {
        Optional<Television> television = televisionRepository.findById(id);
        if (television.isEmpty()) {
            throw new RecordNotFoundException("Id " + id + " does not exist");
        } else {
            Television television1 = television.get();
            television1.setType(t.getType());
            television1.setBrand(t.getBrand());
            television1.setName(t.getName());
            television1.setPrice(t.getPrice());
            television1.setAvailableSize(t.getAvailableSize());
            television1.setRefreshRate(t.getRefreshRate());
            television1.setScreenType(t.getScreenType());
            television1.setScreenQuality(t.getScreenQuality());
            television1.setSmartTv(t.getSmartTv());
            television1.setSmartTv(t.getSmartTv());
            television1.setWifi(t.getWifi());
            television1.setVoiceControl(t.getVoiceControl());
            television1.setHdr(t.getHdr());
            television1.setBluetooth(t.getBluetooth());
            television1.setAmbiLight(t.getAmbiLight());
            television1.setOriginalStock(t.getOriginalStock());
            television1.setSold(t.getSold());
            Television returnTelevision = televisionRepository.save(television1);
            return ResponseEntity.ok().body(returnTelevision);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTelevision(@PathVariable("id") Long id) {
        Optional<Television> television = televisionRepository.findById(id);
        if (television.isEmpty()) {
            throw new RecordNotFoundException("Id " + id + " does not exist");
        } else {
            televisionRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
    }}