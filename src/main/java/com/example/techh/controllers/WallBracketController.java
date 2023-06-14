package com.example.techh.controllers;

import com.example.techh.Dtos.WallBracketDto;
import com.example.techh.Dtos.WallBracketInputDto;
import com.example.techh.services.WallBracketService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/wallbrackets")
public class WallBracketController {
    private final WallBracketService wallBracketService;
    public WallBracketController(WallBracketService wallBracketService){
        this.wallBracketService = wallBracketService;
    }

    @GetMapping
    public ResponseEntity<List<WallBracketDto>> getWallBrackets(){
        List<WallBracketDto> wallBracketDtos = wallBracketService.getWallBrackets();
        return ResponseEntity.ok(wallBracketDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WallBracketDto> getWallBracket(@PathVariable("id") Long id) {
        WallBracketDto wallBracket = wallBracketService.getWallBracket(id);
        return ResponseEntity.ok(wallBracket);
    }

    @PostMapping("/addwallbracket")
    public ResponseEntity<WallBracketDto> addWallBracket(@Valid @RequestBody WallBracketInputDto wallBracket) {
        WallBracketDto dto = wallBracketService.saveWallBracket(wallBracket);
        return ResponseEntity.created(null).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WallBracketDto> updateWallBracket(@PathVariable Long id, @Valid @RequestBody WallBracketInputDto wallBracket) {
        WallBracketDto dto = wallBracketService.updateWallBracket(id, wallBracket);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeWallBracket(@PathVariable Long id) {
        wallBracketService.removeWallBracket(id);
        return ResponseEntity.noContent().build();
    }

    // Deze methode haalt alle televisies op die aan een bepaalde wallbracket gekoppeld zijn.
    // Deze methode maakt gebruikt van de televisionWallBracketService.
//    @GetMapping("/wallbrackets/televisions/{wallBracketId}")
//    public ResponseEntity<Collection<TelevisionDto>> getTelevisionsByWallBracketId(@PathVariable("wallBracketId") Long wallBracketId){
//        return ResponseEntity.ok(televisionWallBracketService.getTelevisionsByWallBracketId(wallBracketId));
//    }
}
