package Enos.SpringProject.medVoll.repositorys;

import Enos.SpringProject.medVoll.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPatientRepository extends JpaRepository<Patient,Long> {

    Patient getReferenceByIdAndActive(Long id, Integer active);
    List<Patient> findByActive(Integer active);
}
