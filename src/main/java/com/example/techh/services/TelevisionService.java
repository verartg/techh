package com.example.techh.services;

import com.example.techh.Dtos.TelevisionDto;
import com.example.techh.Dtos.TelevisionInputDto;
import com.example.techh.exceptions.RecordNotFoundException;
import com.example.techh.models.RemoteController;
import com.example.techh.models.Television;
import com.example.techh.models.WallBracket;
import com.example.techh.repositories.RemoteControllerRepository;
import com.example.techh.repositories.TelevisionRepository;
import com.example.techh.repositories.WallBracketRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TelevisionService {

    private final TelevisionRepository televisionRepository;
    private final RemoteControllerRepository remoteControllerRepository;
    private final WallBracketRepository wallBracketRepository;

    public TelevisionService(TelevisionRepository televisionRepository,
                             RemoteControllerRepository remoteControllerRepository,
                             WallBracketRepository wallBracketRepository) {
        this.televisionRepository = televisionRepository;
        this.remoteControllerRepository = remoteControllerRepository;
        this.wallBracketRepository = wallBracketRepository;
    }

    public List<TelevisionDto> getTelevisions() {
        List<Television> televisions = televisionRepository.findAll();
        List<TelevisionDto> televisionDtos = new ArrayList<>();
        for (Television television : televisions ) {
            televisionDtos.add(transferTelevisionToDto(television));
        }
        return televisionDtos;
    }

    public List<TelevisionDto> getTelevisionsByBrand(String brand) {
        List<Television> televisions = televisionRepository.findAllTelevisionsByBrandEqualsIgnoreCase(brand);
        List<TelevisionDto> televisionDtos = new ArrayList<>();
        for (Television television : televisions) {
            televisionDtos.add(transferTelevisionToDto(television));
        }
        return televisionDtos;
    }

    public TelevisionDto transferTelevisionToDto(Television television){
        TelevisionDto televisionDto = new TelevisionDto();

        televisionDto.name = television.getName();
        televisionDto.brand = television.getBrand();
        televisionDto.id = television.getId();
        televisionDto.type = television.getType();
        televisionDto.name = television.getName();
        televisionDto.price = television.getPrice();
        televisionDto.availableSize = television.getAvailableSize();
        televisionDto.refreshRate = television.getRefreshRate();
        televisionDto.screenType = television.getScreenType();
        televisionDto.screenQuality = television.getScreenQuality();
        televisionDto.smartTv = television.getSmartTv();
        televisionDto.wifi = television.getWifi();
        televisionDto.voiceControl = television.getVoiceControl();
        televisionDto.hdr = television.getHdr();
        televisionDto.bluetooth = television.getBluetooth();
        televisionDto.ambiLight = television.getAmbiLight();
        televisionDto.originalStock = television.getOriginalStock();
        televisionDto.sold = television.getSold();
        return televisionDto;
    }

    public Television transferToTelevision(TelevisionInputDto televisionDto) {
        var television = new Television();

        television.setType(televisionDto.getType());
        television.setBrand(televisionDto.getBrand());
        television.setName(televisionDto.getName());
        television.setPrice(televisionDto.getPrice());
        television.setAvailableSize(televisionDto.getAvailableSize());
        television.setScreenType(televisionDto.getScreenType());
        television.setScreenQuality(televisionDto.getScreenQuality());
        television.setSmartTv(televisionDto.getSmartTv());
        television.setWifi(televisionDto.getWifi());
        television.setVoiceControl(televisionDto.getVoiceControl());
        television.setHdr(televisionDto.getHdr());
        television.setBluetooth(televisionDto.getBluetooth());
        television.setAmbiLight(televisionDto.getAmbiLight());
        television.setOriginalStock(televisionDto.getOriginalStock());
        television.setSold(televisionDto.getSold());

        return television;
    }

    //in de uitwerkingen halen ze cimodule en remotecontroller erbij:
//    public TelevisionDto getTelevisionById(Long id) {
//
//            if (televisionRepository.findById(id).isPresent()){
//                Television tv = televisionRepository.findById(id).get();
//                TelevisionDto dto =transferToDto(tv);
//                if(tv.getCiModule() != null){
//                    dto.setCiModuleDto(ciModuleService.transferToDto(tv.getCiModule()));
//                }
//                if(tv.getRemoteController() != null){
//                    dto.setRemoteControllerDto(remoteControllerService.transferToDto(tv.getRemoteController()));
//                }
//
//                return transferToDto(tv);
//            } else {
//                throw new RecordNotFoundException("geen televisie gevonden");
//            }
//        }
    public TelevisionDto getTelevision(Long id) {
        Optional<Television> optionalTelevision = televisionRepository.findById(id);
        if (optionalTelevision.isEmpty()) {
            throw new RecordNotFoundException("television not found");}
        Television television = optionalTelevision.get();
        return transferTelevisionToDto(television);
    }
    // In deze methode moeten we twee keer een vertaal methode toepassen.
    // De eerste keer van dto naar televsion, omdat de parameter een dto is.
    // De tweede keer van television naar dto, omdat de return waarde een dto is.
    public TelevisionDto saveTelevision(TelevisionInputDto televisionDto) {
        Television television = transferToTelevision(televisionDto);
        televisionRepository.save(television);
        return transferTelevisionToDto(television);
    }

    //televisionFound is geen goede benaming. Want hij returnt altijd een television. optionalTelevision is beter.
    public void removeTelevision(@RequestBody Long id) {
        Optional<Television> televisionFound = televisionRepository.findById(id);
        if (televisionFound.isPresent()) {
            televisionRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Cannot find television");
        }
    }

    public TelevisionDto updateTelevision(Long id, TelevisionInputDto newTelevision) {
        Optional<Television> televisionFound = televisionRepository.findById(id);
        if (televisionFound.isPresent()) {
            Television television1 = televisionFound.get();

            television1.setAmbiLight(newTelevision.getAmbiLight());
            television1.setAvailableSize(newTelevision.getAvailableSize());
            television1.setAmbiLight(newTelevision.getAmbiLight());
            television1.setBluetooth(newTelevision.getBluetooth());
            television1.setBrand(newTelevision.getBrand());
            television1.setHdr(newTelevision.getHdr());
            television1.setName(newTelevision.getName());
            television1.setOriginalStock(newTelevision.getOriginalStock());
            television1.setPrice(newTelevision.getPrice());
            television1.setRefreshRate(newTelevision.getRefreshRate());
            television1.setScreenQuality(newTelevision.getScreenQuality());
            television1.setScreenType(newTelevision.getScreenType());
            television1.setSmartTv(newTelevision.getSmartTv());
            television1.setSold(newTelevision.getSold());
            television1.setType(newTelevision.getType());
            television1.setVoiceControl(newTelevision.getVoiceControl());
            television1.setWifi(newTelevision.getWifi());
            Television returnTelevision = televisionRepository.save(television1);

            return transferTelevisionToDto(returnTelevision);
        } else {
            throw new RecordNotFoundException("Cannot find television");
        }
    }

    public TelevisionDto assignRemoteControllerToTelevision(Long id, Long remotecontrollerId) {
        Optional<Television> televisionFound = televisionRepository.findById(id);
        Optional<RemoteController> remoteControllerFound = remoteControllerRepository.findById(remotecontrollerId);
        if (televisionFound.isPresent() && remoteControllerFound.isPresent()) {
            Television television1 = televisionFound.get();
            RemoteController remote1 = remoteControllerFound.get();
            television1.setRemoteController(remote1);
            Television returnTelevision = televisionRepository.save(television1);
            return transferTelevisionToDto(returnTelevision);
        } else {
            throw new RecordNotFoundException("Remote and/or television not found");
        }
    }

    public String assignWallBracketToTelevision(Long id, Long wallbracket_id) {
        Optional<Television> televisionFound = televisionRepository.findById(id);
        Optional<WallBracket> wallBracketFound = wallBracketRepository.findById(wallbracket_id);
        if (televisionFound.isPresent() && wallBracketFound.isPresent()){
            Television television1 = televisionFound.get();
            WallBracket wallBracket1 = wallBracketFound.get();
            List<WallBracket> wallBracketList = television1.getWallBrackets();
            wallBracketList.add(wallBracket1);
            television1.setWallBrackets(wallBracketList);
            televisionRepository.save(television1);
            return "hoera";
        } else {
            throw new RecordNotFoundException("Television and/or wallbracket not found");
        }
    }
}
