package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private UserService userService;
    @Autowired
    private ScheduleRepo scheduleRepo;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = userService.createSchedule(scheduleDTO);
        return convertScheduleToDTO(schedule);
    }

    // helper method to convert to DTO
    private ScheduleDTO convertScheduleToDTO(Schedule schedule) {
        ScheduleDTO dto = new ScheduleDTO();
        dto.setId(schedule.getId());
        dto.setDate(schedule.getDate());
        dto.setActivities(schedule.getActivities());

        List<Long> employeesIds = new ArrayList<>();
        for (Employee employee : schedule.getEmployees()) {
            employeesIds.add(employee.getId());
        }
        dto.setEmployeeIds(employeesIds);

        List<Long> petIds = new ArrayList<>();
        for (Pet pet : schedule.getPets()) {
            petIds.add(pet.getId());
        }
        dto.setPetIds(petIds);

        return dto;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        for (Schedule schedule : scheduleRepo.findAll()) {
            scheduleDTOList.add(convertScheduleToDTO(schedule));
        }
        return scheduleDTOList;
//        throw new UnsupportedOperationException();
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return userService.getScheduleForPet(petId)
                .stream().map(this::convertScheduleToDTO).collect(Collectors.toList());
//        throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return userService.getScheduleForEmployee(employeeId)
                .stream().map(this::convertScheduleToDTO).collect(Collectors.toList());
//        throw new UnsupportedOperationException();
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return userService.getScheduleForCustomer(customerId)
                .stream().map(this::convertScheduleToDTO).collect(Collectors.toList());
//        throw new UnsupportedOperationException();
    }
}
