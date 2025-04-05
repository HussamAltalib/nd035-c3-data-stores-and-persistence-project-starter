package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepo extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByPets_Id(Long petId);

    List<Schedule> findAllByEmployees_Id(Long employeeId);

    List<Schedule> findAllByPets_Owner_Id(Long customerId);
}
