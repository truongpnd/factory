package com.amitgroup.sqldatabase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amitgroup.sqldatabase.entities.MaintenancePerson;

@Repository
public interface MaintenancePersonRepository extends JpaRepository<MaintenancePerson, Long>{
    
    //delete by maintenancerequest id
    void deleteByMaintenanceRequestId(Long id);
}
