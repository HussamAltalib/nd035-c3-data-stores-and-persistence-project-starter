package com.udacity.jdnd.course3.critter.user;
import com.udacity.jdnd.course3.critter.pet.PetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private PetRepo petRepo;

    public Customer createCustomer(Customer customer){
        return customerRepo.save(customer);
    }

    public List<CustomerDTO> findAllCustomers(){
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        List<Customer> customerList = customerRepo.findAll();

        for(int i = 0; i < customerList.size(); i++){
            CustomerDTO newCustomerDTO = new CustomerDTO();
            newCustomerDTO.setId(customerList.get(i).getId());
            newCustomerDTO.setName(customerList.get(i).getName());
            newCustomerDTO.setPhoneNumber(customerList.get(i).getPhoneNumber());
            newCustomerDTO.setNotes(customerList.get(i).getNotes());

            List<Long> petIds = new ArrayList<>();
            for(int j = 0; j < customerList.get(i).getPets().size(); j++){
                petIds.add(customerList.get(i).getPets().get(j).getId());
            }
            newCustomerDTO.setPetIds(petIds);

            customerDTOList.add(newCustomerDTO);
        }
        return customerDTOList;
    }

    public Optional<Customer> getCustomerById(Long id){
        return customerRepo.findById(id);
    }

    public Employee saveEmployee(Employee employee){
        return employeeRepo.save(employee);
    }

    public Optional<Employee> getEmployee(long id){
        return employeeRepo.findById(id);
    }

    public Customer getCustomerByPet(Long petId){
        Long customerId = petRepo.getPetById(petId).getOwner().getId();

        return customerRepo.findById(customerId).get();
    }


}
