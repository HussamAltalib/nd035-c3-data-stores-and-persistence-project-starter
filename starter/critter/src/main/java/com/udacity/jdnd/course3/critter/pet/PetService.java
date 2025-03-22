package com.udacity.jdnd.course3.critter.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    private PetRepo petRepo;

    public Pet savePet(Pet pet){
        return petRepo.save(pet);
    }

    public Optional<Pet> findPetById(Long id){
        return petRepo.findById(id);
    }

    public List<Pet> getPets(){
        return petRepo.findAll();
    }

    public List<Pet> getPetsByOwner(Long ownerId){
        return petRepo.getPetsByOwnerId(ownerId);
    }
}
