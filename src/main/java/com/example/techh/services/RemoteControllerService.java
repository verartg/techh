package com.example.techh.services;

import com.example.techh.Dtos.RemoteControllerDto;
import com.example.techh.Dtos.RemoteControllerInputDto;
import com.example.techh.exceptions.RecordNotFoundException;
import com.example.techh.models.RemoteController;
import com.example.techh.repositories.RemoteControllerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RemoteControllerService {

    private final RemoteControllerRepository remoteControllerRepository;

    public RemoteControllerService(RemoteControllerRepository remoteControllerRepository) {
        this.remoteControllerRepository = remoteControllerRepository;
    }

    public List<RemoteControllerDto> getRemoteControllers() {
        List<RemoteController> remoteControllers = remoteControllerRepository.findAll();
        List<RemoteControllerDto> remoteControllerDtos = new ArrayList<>();
        for (RemoteController remoteController : remoteControllers) {
            remoteControllerDtos.add(transferRemoteControllerToDto(remoteController));
        }
        return remoteControllerDtos;
    }

    public RemoteControllerDto transferRemoteControllerToDto(RemoteController remoteController){
        RemoteControllerDto remoteControllerDto = new RemoteControllerDto();

        remoteControllerDto.id = remoteController.getId();
        remoteControllerDto.name = remoteController.getName();
        remoteControllerDto.brand = remoteController.getBrand();
        remoteControllerDto.originalStock = remoteController.getOriginalStock();
        remoteControllerDto.batteryType = remoteController.getBatteryType();
        remoteControllerDto.compatibleWith = remoteController.getCompatibleWith();
        remoteControllerDto.price = remoteController.getPrice();
        return remoteControllerDto;
    }

    public RemoteController transferToRemoteController(RemoteControllerInputDto remoteControllerDto) {
        var remoteController = new RemoteController();
        remoteController.setId(remoteControllerDto.getId());
        remoteController.setBrand(remoteControllerDto.getBrand());
        remoteController.setName(remoteControllerDto.getName());
        remoteController.setOriginalStock(remoteControllerDto.getOriginalStock());
        remoteController.setBatteryType(remoteController.getBatteryType());
        remoteController.setCompatibleWith(remoteControllerDto.getCompatibleWith());
        remoteController.setPrice(remoteController.getPrice());
        return remoteController;
    }

    public RemoteControllerDto getRemoteController(Long id) {
        Optional<RemoteController> remoteControllerFound = remoteControllerRepository.findById(id);
        if (remoteControllerFound.isPresent()) {
            RemoteController remoteController = remoteControllerFound.get();
            return transferRemoteControllerToDto(remoteController);
        } else {
            throw new RecordNotFoundException("Remotecontroller not found");
        }
    }

    public RemoteControllerDto saveRemoteController(RemoteControllerInputDto remoteControllerDto) {
        RemoteController remoteController = transferToRemoteController(remoteControllerDto);
        remoteControllerRepository.save(remoteController);
        return transferRemoteControllerToDto(remoteController);
    }

    public void removeRemoteController(@RequestBody Long id) {
        Optional<RemoteController> remoteControllerFound = remoteControllerRepository.findById(id);
        if(remoteControllerFound.isPresent()) {
            remoteControllerRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Cannot find remotecontroller");
        }
    }

    public RemoteControllerDto updateRemoteController(Long id, RemoteControllerInputDto newRemoteController) {
        Optional<RemoteController> remoteControllerFound = remoteControllerRepository.findById(id);
        if (remoteControllerFound.isPresent()) {
            RemoteController remoteController1 = remoteControllerFound.get();
            remoteController1.setPrice(newRemoteController.getPrice());
            remoteController1.setCompatibleWith(newRemoteController.getCompatibleWith());
            remoteController1.setBatteryType(newRemoteController.getBatteryType());
            remoteController1.setOriginalStock(newRemoteController.getOriginalStock());
            remoteController1.setName(newRemoteController.getName());
            remoteController1.setBrand(newRemoteController.getBrand());
            RemoteController returnRemoteController = remoteControllerRepository.save(remoteController1);
            return transferRemoteControllerToDto(returnRemoteController);
        } else {
            throw new RecordNotFoundException("Cannot find remotecontroller");
        }
    }
}
