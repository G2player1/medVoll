package Enos.SpringProject.medVoll.repositorys;

import Enos.SpringProject.medVoll.enums.ExpertiseEnum;
import Enos.SpringProject.medVoll.models.Doctor;
import Enos.SpringProject.medVoll.models.dto.reads.ReadDoctorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IDoctorRepository extends JpaRepository<Doctor,Long> {

    @Query("SELECT d FROM Doctor d JOIN d.expertises de JOIN de.expertise e WHERE e.expertise = :expertise")
    Page<ReadDoctorDTO> findDoctorsByExpertise(Pageable pageable, @Param("expertise") ExpertiseEnum expertise);
}
