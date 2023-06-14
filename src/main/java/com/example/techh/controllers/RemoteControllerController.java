package com.example.techh.controllers;

import com.example.techh.Dtos.RemoteControllerDto;
import com.example.techh.Dtos.RemoteControllerInputDto;
import com.example.techh.services.RemoteControllerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/remotecontrollers")
public class RemoteControllerController {
        private final RemoteControllerService remoteControllerService;

        public RemoteControllerController(RemoteControllerService remoteControllerService) {
            this.remoteControllerService = remoteControllerService;
        }

    @GetMapping
    public ResponseEntity<List<RemoteControllerDto>> getRemoteControllers(){
        List<RemoteControllerDto> dtos = remoteControllerService.getRemoteControllers();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RemoteControllerDto> getRemoteController(@PathVariable("id") Long id) {
        RemoteControllerDto remoteController = remoteControllerService.getRemoteController(id);
        return ResponseEntity.ok(remoteController);
    }

    @PostMapping("/addremotecontroller")
    public ResponseEntity<RemoteControllerDto> addRemoteController(@Valid @RequestBody RemoteControllerInputDto remoteController) {
        RemoteControllerDto remotecontroller = remoteControllerService.saveRemoteController(remoteController);
        return ResponseEntity.created(null).body(remotecontroller);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RemoteControllerDto> updateRemoteController(@PathVariable Long id, @Valid @RequestBody RemoteControllerInputDto remoteController) {
        RemoteControllerDto dto = remoteControllerService.updateRemoteController(id, remoteController);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeRemoteController(@PathVariable Long id) {
        remoteControllerService.removeRemoteController(id);
        return ResponseEntity.noContent().build();
    }
}
