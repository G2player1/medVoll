package Enos.SpringProject.medVoll.domain.models.consult;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IConsultRepository extends JpaRepository<Consult,Long> {

    Consult getReferenceByIdAndActive(Long id, Integer active);
    List<Consult> findByActive(Integer active);
    List<Consult> findByActiveAndDoctor_Id(Integer active, Long id);
    List<Consult> findByActiveAndPatient_Id(Integer active, Long id);
}
