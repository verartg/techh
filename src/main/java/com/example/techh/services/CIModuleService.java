package com.example.techh.services;

import com.example.techh.Dtos.CIModuleDto;
import com.example.techh.Dtos.CIModuleInputDto;
import com.example.techh.exceptions.RecordNotFoundException;
import com.example.techh.models.CIModule;
import com.example.techh.models.Television;
import com.example.techh.repositories.CIModuleRepository;
import com.example.techh.repositories.TelevisionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CIModuleService {
    private final CIModuleRepository ciModuleRepository;
    private final TelevisionRepository televisionRepository;

    public CIModuleService(CIModuleRepository ciModuleRepository, TelevisionRepository televisionRepository) {
        this.ciModuleRepository = ciModuleRepository;
        this.televisionRepository = televisionRepository;
    }

    public List<CIModuleDto> getCIModules() {
        List<CIModule> ciModules = ciModuleRepository.findAll();
        List<CIModuleDto> ciModuleDtos = new ArrayList<>();
        for (CIModule ciModule : ciModules ) {
            ciModuleDtos.add(transferCIModuleToDto(ciModule));
        }
        return ciModuleDtos;
    }

    public CIModuleDto transferCIModuleToDto(CIModule ciModule){
        CIModuleDto ciModuleDto = new CIModuleDto();

        ciModuleDto.id = ciModule.getId();
        ciModuleDto.name = ciModule.getName();
        ciModuleDto.price = ciModule.getPrice();
        ciModuleDto.type = ciModule.getType();
        return ciModuleDto;
    }

    public CIModuleDto getCIModule(Long id) {
        Optional<CIModule> ciModuleFound = ciModuleRepository.findById(id);
        if (ciModuleFound.isPresent()) {
            CIModule ciModule = ciModuleFound.get();
            return transferCIModuleToDto(ciModule);
        } else {
            throw new RecordNotFoundException("ci-module not found");
        }
    }

    public CIModuleDto saveCIModule(CIModuleInputDto ciModuleDto) {
        CIModule ciModule = transferToCIModule(ciModuleDto);
        ciModuleRepository.save(ciModule);
        return transferCIModuleToDto(ciModule);
    }

    public void removeCIModule(@RequestBody Long id) {
        Optional<CIModule> ciModuleFound = ciModuleRepository.findById(id);
        if (ciModuleFound.isPresent()){
            ciModuleRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Cannot find ci-module");
        }
    }

    public CIModuleDto updateCIModule(Long id, CIModuleInputDto newCIModule) {
        Optional<CIModule> ciModuleFound = ciModuleRepository.findById(id);
        if (ciModuleFound.isPresent()) {
            CIModule ciModule1 = ciModuleFound.get();
            ciModule1.setType(newCIModule.getType());
            ciModule1.setName(newCIModule.getName());
            ciModule1.setPrice(newCIModule.getPrice());
            CIModule returnCIModule = ciModuleRepository.save(ciModule1);

            return transferCIModuleToDto(returnCIModule);
        } else {
            throw new RecordNotFoundException("Cannot find ci-module");
        }
    }


    public CIModule transferToCIModule(CIModuleInputDto ciModuleDto) {
        var ciModule = new CIModule();

        ciModule.setName(ciModuleDto.getName());
        ciModule.setPrice(ciModuleDto.getPrice());
        ciModule.setType(ciModuleDto.getType());
        return ciModule;
    }

    public String assignCIModuleToTelevision(Long id, Long television_id){
        Optional<CIModule> ciModuleFound = ciModuleRepository.findById(id);
        Optional<Television> televisionFound = televisionRepository.findById(television_id);
        if (televisionFound.isPresent() && ciModuleFound.isPresent()) {
            CIModule cimodule1 = ciModuleFound.get();
            Television television1 = televisionFound.get();
            cimodule1.setTelevision(television1);
            ciModuleRepository.save(cimodule1);
            return "Cimodule has been assigned to television";
        } else {
            throw new RecordNotFoundException("Television or Ci-module nt found");
        }
    }
}
