package Enos.SpringProject.medVoll.domain.models.consult;

import Enos.SpringProject.medVoll.domain.models.consult.validadeServices.IConsultValidate;
import Enos.SpringProject.medVoll.domain.models.expertise.ExpertiseEnum;
import Enos.SpringProject.medVoll.domain.exceptions.RegisterConsultRuleException;
import Enos.SpringProject.medVoll.domain.exceptions.RequestBdException;
import Enos.SpringProject.medVoll.domain.models.doctor.Doctor;
import Enos.SpringProject.medVoll.domain.models.patient.Patient;
import Enos.SpringProject.medVoll.domain.models.consult.dto.ReadConsultDTO;
import Enos.SpringProject.medVoll.domain.models.consult.dto.RegisterConsultDTO;
import Enos.SpringProject.medVoll.domain.models.doctor.IDoctorRepository;
import Enos.SpringProject.medVoll.domain.models.patient.IPatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ConsultService {

    @Autowired
    private IConsultRepository consultRepository;
    @Autowired
    private IDoctorRepository doctorRepository;
    @Autowired
    private IPatientRepository patientRepository;
    @Autowired
    private List<IConsultValidate> validates;

    @Transactional
    public ReadConsultDTO registerConsult(RegisterConsultDTO registerConsultDTO) {
        var schedule = LocalDateTime.parse(registerConsultDTO.horario(),DateTimeFormatter.ofPattern("dd/MM/uuuu:HH:mm:ss"));
        var patient = patientRepository.getReferenceByIdAndActive(registerConsultDTO.paciente_id(), 1);
        var doctor = selectDoctor(registerConsultDTO.medico_id(),registerConsultDTO.especialidade(),schedule);
        if(doctor == null){
            throw new EntityNotFoundException("Doctor not found");
        }
        if (patient == null){
            throw new EntityNotFoundException("Patient not found");
        }
        validateConsultRegister(schedule,doctor,patient);
        Consult consult = new Consult(doctor,patient,schedule);
        doctor.addConsult(consult);
        patient.addConsult(consult);
        consultRepository.save(consult);
        return new ReadConsultDTO(consult);
    }

    @Transactional
    private Doctor selectDoctor(Long id, String expertise, LocalDateTime schedule){
        if(id != null){
            return doctorRepository.getReferenceByIdAndActive(id, 1);
        } else {
            List<Doctor> doctors;
            Doctor doctor = null;
            if (expertise != null){
                doctors = doctorRepository.findByExpertises_ExpertiseAndActive(ExpertiseEnum.fromString(expertise),1);
            } else {
                doctors =  doctorRepository.findByActive(1);
            }
            for (Doctor d : doctors){
                doctor = d;
                for (Consult c : d.getConsults()){
                    if (c.getActive() != 0) {
                        if (!schedule.isBefore(c.getScheduleStart()) && !schedule.isAfter(c.getScheduleEnd()) || schedule.isEqual(c.getScheduleStart())){
                            doctor = null;
                        }
                    }
                }
                if(doctor != null){
                    break;
                }
            }
            return doctor;
        }
    }
    
    private void validateConsultRegister(LocalDateTime schedule, Doctor doctor, Patient patient){
        validates.forEach(iConsultValidate -> iConsultValidate.validate(schedule,doctor,patient));
    }

    @Transactional
    public Page<ReadConsultDTO> getConsultsInDB() {
        List<ReadConsultDTO> consults;
        List<Consult> aux = consultRepository.findByActive(1);
        consults = aux
                .stream()
                .map(ReadConsultDTO::new)
                .toList();
        return new PageImpl<>(consults);
    }

    @Transactional
    public Page<ReadConsultDTO> getDoctorConsults(Long id) {
        List<ReadConsultDTO> consults;
        List<Consult> aux = consultRepository.findByActiveAndDoctor_Id(1,id);
        if (aux == null){
            throw new RequestBdException("o id do medico é invalido");
        }
        consults = aux
                .stream()
                .map(ReadConsultDTO::new)
                .toList();
        return new PageImpl<>(consults);
    }

    @Transactional
    public Page<ReadConsultDTO> getPatientConsults(Long id) {
        List<ReadConsultDTO> consults;
        List<Consult> aux = consultRepository.findByActiveAndPatient_Id(1,id);
        if (aux == null){
            throw new RequestBdException("o id do paciente é invalido");
        }
        consults = aux
                .stream()
                .map(ReadConsultDTO::new)
                .toList();
        return new PageImpl<>(consults);
    }

    @Transactional
    public void deleteConsult(Long id) {
        var consult = consultRepository.getReferenceByIdAndActive(id,1);
        if (consult == null){
            throw new RequestBdException("o id da consulta é invalido");
        }
        consult.consultDeleteLogical();
    }

    @Transactional
    public ReadConsultDTO getConsultById(Long id) {
        var consult = consultRepository.getReferenceByIdAndActive(id,1);
        if (consult == null){
            throw new RequestBdException("o id da consulta é invalido");
        }
        return new ReadConsultDTO(consult);
    }
}
