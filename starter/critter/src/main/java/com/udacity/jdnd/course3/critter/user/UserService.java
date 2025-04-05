package com.udacity.jdnd.course3.critter.user;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepo;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.ScheduleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.DayOfWeek;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private PetRepo petRepo;

    @Autowired
    private ScheduleRepo scheduleRepo;


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

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId){
        System.out.println("daysAvailable: " + daysAvailable);
        Employee employee = employeeRepo.findById(employeeId).get();
        employee.setDaysAvailable(daysAvailable);
        employeeRepo.save(employee);
    }

    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeDTO){
        DayOfWeek requestedDay = employeeDTO.getDate().getDayOfWeek();
        Set<EmployeeSkill> requestedSkills = employeeDTO.getSkills();

        List<Employee> employeeList = employeeRepo.findAll();
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();

        for(Employee employee : employeeList){
            boolean isAvailable = employee.getDaysAvailable().contains(requestedDay);
            boolean hasAllSkills = employee.getSkills().containsAll(requestedSkills);

            if (isAvailable && hasAllSkills) {
                EmployeeDTO newEmployeeDTO = new EmployeeDTO();
                newEmployeeDTO.setId(employee.getId());
                newEmployeeDTO.setName(employee.getName());
                newEmployeeDTO.setDaysAvailable(employee.getDaysAvailable());
                newEmployeeDTO.setSkills(employee.getSkills());
                employeeDTOList.add(newEmployeeDTO);
            }
        }

        return employeeDTOList;
    }

    public Schedule createSchedule(ScheduleDTO dto) {
        Schedule schedule = new Schedule();
        schedule.setDate(dto.getDate());
        schedule.setActivities(dto.getActivities());

        List<Employee> employees = employeeRepo.findAllById(dto.getEmployeeIds());
        List<Pet> pets = petRepo.findAllById(dto.getPetIds());

        schedule.setEmployees(employees);
        schedule.setPets(pets);

        return scheduleRepo.save(schedule);
    }

    public List<Schedule> getScheduleForEmployee(Long employeeId) {
        return scheduleRepo.findAllByEmployees_Id(employeeId);
    }

    public List<Schedule> getScheduleForPet(Long petId) {
        return scheduleRepo.findAllByPets_Id(petId);
    }

    public List<Schedule> getScheduleForCustomer(Long customerId) {
        return scheduleRepo.findAllByPets_Owner_Id(customerId);
    }

}
