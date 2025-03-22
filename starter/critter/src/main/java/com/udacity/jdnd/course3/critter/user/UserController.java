package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setNotes(customerDTO.getNotes());
        List<Pet> petList = new ArrayList<>();
        customer.setPets(petList);
//        System.out.println("the size is: " + customerDTO.getPetIds().size());
//        if(customerDTO.getPetIds().size() > 0) {
//            for (int i = 0; i <= customerDTO.getPetIds().size(); i++) {
//                Pet newPet = new Pet();
//                petList.add(newPet);
//            }
//        }

//        Pet pet = new Pet();
//        customer.getPets().add(pet);

        Customer savedCustomer = userService.createCustomer(customer);

        CustomerDTO savedCustomerDTO = new CustomerDTO();
        savedCustomerDTO.setId(savedCustomer.getId());
        savedCustomerDTO.setName(savedCustomer.getName());
        savedCustomerDTO.setPhoneNumber(savedCustomer.getPhoneNumber());
        savedCustomerDTO.setNotes(savedCustomer.getNotes());
        List<Long> petIdsList = new ArrayList<>();
        for (int i = 0; i< savedCustomer.getPets().size(); i++){
            petIdsList.add(savedCustomer.getPets().get(i).getId());
        }
        savedCustomerDTO.setPetIds(petIdsList);

//        throw new UnsupportedOperationException();
        return savedCustomerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return userService.findAllCustomers();
//        throw new UnsupportedOperationException();
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = userService.getCustomerByPet(petId);
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());

        return customerDTO;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setSkills(employeeDTO.getSkills());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());

        Employee savedEmployee = userService.saveEmployee(employee);

        EmployeeDTO savedEmployeeDTO = new EmployeeDTO();
        savedEmployeeDTO.setId(savedEmployee.getId());
        savedEmployeeDTO.setName(savedEmployee.getName());
        savedEmployeeDTO.setSkills(savedEmployee.getSkills());
        savedEmployeeDTO.setDaysAvailable(savedEmployee.getDaysAvailable());

        return savedEmployeeDTO;
//        throw new UnsupportedOperationException();
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Optional<Employee> savedEmployee = userService.getEmployee(employeeId);
        EmployeeDTO savedEmployeeDTO = new EmployeeDTO();
        savedEmployeeDTO.setId(savedEmployee.get().getId());
        savedEmployeeDTO.setName(savedEmployee.get().getName());
        savedEmployeeDTO.setSkills(savedEmployee.get().getSkills());
        savedEmployeeDTO.setDaysAvailable(savedEmployee.get().getDaysAvailable());

        return savedEmployeeDTO;
//        throw new UnsupportedOperationException();
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        throw new UnsupportedOperationException();
    }

}
