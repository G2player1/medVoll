package Enos.SpringProject.medVoll.repositorys;

import Enos.SpringProject.medVoll.enums.ExpertiseEnum;
import Enos.SpringProject.medVoll.models.Doctor;
import Enos.SpringProject.medVoll.models.dto.DoctorListingDataDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDoctorRepository extends JpaRepository<Doctor,Long> {

    @Query("SELECT d FROM Doctor d JOIN d.expertises de JOIN de.expertise e WHERE e.expertise = :expertise")
    List<DoctorListingDataDTO> findDoctorsByExpertise(@Param("expertise") ExpertiseEnum expertise);
}
