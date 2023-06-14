package com.example.techh.services;

import com.example.techh.Dtos.WallBracketDto;
import com.example.techh.Dtos.WallBracketInputDto;
import com.example.techh.exceptions.RecordNotFoundException;
import com.example.techh.models.WallBracket;
import com.example.techh.repositories.WallBracketRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WallBracketService {
    private final WallBracketRepository wallBracketRepository;
    public WallBracketService(WallBracketRepository wallBracketRepository) {
        this.wallBracketRepository = wallBracketRepository;
    }

    public List<WallBracketDto> getWallBrackets() {
        List<WallBracket> wallBrackets = wallBracketRepository.findAll();
        List<WallBracketDto> wallBracketDtos = new ArrayList<>();
        for (WallBracket wallBracket : wallBrackets ) {
            wallBracketDtos.add(transferWallBracketToDto(wallBracket));
        }
        return wallBracketDtos;
    }

    public WallBracketDto transferWallBracketToDto(WallBracket wallBracket){
        WallBracketDto wallBracketDto = new WallBracketDto();

        wallBracketDto.id = wallBracket.getId();
        wallBracketDto.name = wallBracket.getName();
        wallBracketDto.price = wallBracket.getPrice();
        wallBracketDto.size = wallBracket.getSize();
        wallBracketDto.adjustable = wallBracket.isAdjustable();
        return wallBracketDto;
    }

    public WallBracket transferToWallBracket(WallBracketInputDto wallBracketDto) {
        var wallBracket = new WallBracket();

        wallBracket.setAdjustable(wallBracketDto.isAdjustable());
        wallBracket.setSize(wallBracketDto.getSize());
        wallBracket.setName(wallBracketDto.getName());
        wallBracket.setPrice(wallBracketDto.getPrice());
        return wallBracket;
    }

    public WallBracketDto getWallBracket(Long id) {
        Optional<WallBracket> wallBracketFound = wallBracketRepository.findById(id);
        if (wallBracketFound.isPresent()) {
            WallBracket wallBracket = wallBracketFound.get();
            return transferWallBracketToDto(wallBracket);
        } else {
            throw new RecordNotFoundException("Wallbracket not found");
        }
    }

    public WallBracketDto saveWallBracket(WallBracketInputDto wallBracketDto) {
        WallBracket wallBracket = transferToWallBracket(wallBracketDto);
        wallBracketRepository.save(wallBracket);
        return transferWallBracketToDto(wallBracket);
    }

    public void removeWallBracket(@RequestBody Long id) {
        Optional<WallBracket> wallBracketFound = wallBracketRepository.findById(id);
        if (wallBracketFound.isPresent()) {
            wallBracketRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Cannot find wallbracket");
        }
    }

    public WallBracketDto updateWallBracket(Long id, WallBracketInputDto newWallBracket) {
        Optional<WallBracket> wallBracketFound = wallBracketRepository.findById(id);
        if (wallBracketFound.isPresent()) {
            WallBracket wallBracket1 = wallBracketFound.get();

            wallBracket1.setPrice(newWallBracket.getPrice());
            wallBracket1.setName(newWallBracket.getName());
            wallBracket1.setSize(newWallBracket.getSize());
            wallBracket1.setAdjustable(newWallBracket.isAdjustable());
            WallBracket returnWallBracket = wallBracketRepository.save(wallBracket1);

            return transferWallBracketToDto(returnWallBracket);
        } else {
            throw new RecordNotFoundException("Cannot find wallbracket");
        }
    }
}
