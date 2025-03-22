package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private UserService userService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setType(petDTO.getType());
        pet.setName(petDTO.getName());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());
        Optional<Customer> customer = userService.getCustomerById(petDTO.getOwnerId());
        if(customer.isEmpty())
            throw new EntityNotFoundException("Customer not exist");
        pet.setOwner(customer.get());
        if (customer.get().getPets() == null) {
            customer.get().setPets(new ArrayList<>());
        }
        customer.get().getPets().add(pet);

        Pet savedPet = petService.savePet(pet);

        PetDTO savedPetDTO = new PetDTO();
        savedPetDTO.setId(savedPet.getId());
        savedPetDTO.setType(savedPet.getType());
        savedPetDTO.setName(savedPet.getName());
        savedPetDTO.setBirthDate(savedPet.getBirthDate());
        savedPetDTO.setNotes(savedPet.getNotes());
        savedPetDTO.setOwnerId(savedPet.getOwner().getId());

        return savedPetDTO;
//        throw new UnsupportedOperationException();
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Optional<Pet> pet = petService.findPetById(petId);
        if(pet.isEmpty())
            throw new EntityNotFoundException("pet not exist");

        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.get().getId());
        petDTO.setType(pet.get().getType());
        petDTO.setName(pet.get().getName());
        petDTO.setBirthDate(pet.get().getBirthDate());
        petDTO.setNotes(pet.get().getNotes());
        petDTO.setOwnerId(pet.get().getOwner().getId());
        return petDTO;
//        throw new UnsupportedOperationException();
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> petList = petService.getPets();
        List<PetDTO> petDTOList = new ArrayList<>();
        for(int i = 0; i < petList.size(); i++){
            PetDTO petDTO = new PetDTO();
            petDTO.setId(petList.get(i).getId());
            petDTO.setType(petList.get(i).getType());
            petDTO.setName(petList.get(i).getName());
            petDTO.setNotes(petList.get(i).getNotes());
            petDTO.setBirthDate(petList.get(i).getBirthDate());
            petDTO.setOwnerId(petList.get(i).getOwner().getId());
            petDTOList.add(petDTO);
        }
        return petDTOList;
//        throw new UnsupportedOperationException();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {

        List<Pet> petList = petService.getPetsByOwner(ownerId);
        List<PetDTO> petDTOList = new ArrayList<>();
        for(int i = 0; i < petList.size(); i++){
            PetDTO petDTO = new PetDTO();
            petDTO.setId(petList.get(i).getId());
            petDTO.setType(petList.get(i).getType());
            petDTO.setName(petList.get(i).getName());
            petDTO.setNotes(petList.get(i).getNotes());
            petDTO.setBirthDate(petList.get(i).getBirthDate());
            petDTO.setOwnerId(petList.get(i).getOwner().getId());
            petDTOList.add(petDTO);
        }
        return petDTOList;
//        throw new UnsupportedOperationException();
    }
}
