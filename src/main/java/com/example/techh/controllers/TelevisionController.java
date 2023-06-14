package com.example.techh.controllers;

import com.example.techh.Dtos.TelevisionDto;
import com.example.techh.Dtos.TelevisionInputDto;
import com.example.techh.exceptions.TooManyCharException;
import com.example.techh.services.TelevisionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

//Pas de RequestMappings in de TelevisionController, zodat de controller het verzoek doet aan de servicelaag
// en het response ook ontvangt van de servicelaag
// (dit is omdat we de Controller eigenlijk zo dom mogelijk willen houden).
@RestController
@RequestMapping("/televisions")
public class TelevisionController {

    // We importeren hier (via de constructor, maar het mag ook @Autowired zijn) nu de Service in plaats van direct de Repository.
    private final TelevisionService televisionService;
//    private final TelevisionWallBracketService televisionWallBracketService;

    //Maak in de TelevisionController een @Autowired om de Service te kunnen gebruiken in de Controller.
    public TelevisionController(TelevisionService televisionService) {
        this.televisionService = televisionService;
    }

    // Je ziet dat de return waarde van deze methode nu ResponseEntity<List<TelevisionDto>> is in plaats van <ResponseEntity<List<Television>>
    @GetMapping
    public ResponseEntity<List<TelevisionDto>> getTelevisions(@RequestParam(value = "brand", required = false) Optional<String> brand) {

        List<TelevisionDto> televisionDtos;

        if (brand.isEmpty()) {
            televisionDtos = televisionService.getTelevisions();
        } else {
            televisionDtos = televisionService.getTelevisionsByBrand(brand.get());
        }
        return ResponseEntity.ok().body(televisionService.getTelevisions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TelevisionDto> getTelevision(@PathVariable("id") Long id) {
        TelevisionDto television = televisionService.getTelevision(id);

        return ResponseEntity.ok().body(television);
    }

    @PostMapping("/addtelevision")
    public ResponseEntity<TelevisionDto> addTelevision(@Valid @RequestBody TelevisionInputDto television) throws TooManyCharException {
        if (television.getBrand().length() > 20) {
            throw new TooManyCharException("Name of brand can't be longer than 20 characters");
        }
        TelevisionDto dto = televisionService.saveTelevision(television);
//        URI uri = URI.create(ServletUriComponentsBuilder
//                .fromCurrentRequest().path("/" + television.getId()).toUriString());
        return ResponseEntity.created(null).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTelevision(@PathVariable Long id, @Valid @RequestBody TelevisionInputDto television) {
        TelevisionDto dto = televisionService.updateTelevision(id, television);
            return ResponseEntity.ok().body(dto);
        }
        //in de uitwerkingen staat onderstaande ook met een requestbody en een pathvariable.
    @PutMapping("/{id}/remote/{remotecontrollerId}")
    public ResponseEntity<TelevisionDto> assignRemoteControllerToTelevision(@PathVariable Long id, @PathVariable Long remotecontrollerId) {
            return ResponseEntity.ok(televisionService.assignRemoteControllerToTelevision(id, remotecontrollerId));
    }
    @PutMapping("/{id}/wallbracket/{wallbracket_id}")
    public ResponseEntity<String> assignWallBracketToTelevision(@PathVariable Long id, @PathVariable Long wallbracket_id) {
        return ResponseEntity.ok(televisionService.assignWallBracketToTelevision(id, wallbracket_id));
    }
    //onderstaande komt uit de uitwerkingen.
    // Deze methode is om alle wallbrackets op te halen die aan een bepaalde television gekoppeld zijn.
    // Deze methode maakt gebruik van de televisionWallBracketService.
//    @GetMapping("/televisions/wallBrackets/{televisionId}")
//    public ResponseEntity<Collection<WallBracketDto>> getWallBracketsByTelevisionId(@PathVariable("televisionId") Long televisionId){
//        return ResponseEntity.ok(televisionWallBracketService.getWallBracketsByTelevisionId(televisionId));
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeTelevision(@PathVariable Long id) {
            televisionService.removeTelevision(id);
            return ResponseEntity.noContent().build();
        }
    }