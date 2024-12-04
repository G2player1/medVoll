package Enos.SpringProject.medVoll.domain.services;

import Enos.SpringProject.medVoll.domain.enums.ExpertiseEnum;
import Enos.SpringProject.medVoll.domain.exceptions.RegisterConsultRuleException;
import Enos.SpringProject.medVoll.domain.exceptions.RequestBdException;
import Enos.SpringProject.medVoll.domain.models.Consult;
import Enos.SpringProject.medVoll.domain.models.Doctor;
import Enos.SpringProject.medVoll.domain.models.Patient;
import Enos.SpringProject.medVoll.domain.models.dto.reads.ReadConsultDTO;
import Enos.SpringProject.medVoll.domain.models.dto.registers.RegisterConsultDTO;
import Enos.SpringProject.medVoll.domain.repositorys.IConsultRepository;
import Enos.SpringProject.medVoll.domain.repositorys.IDoctorRepository;
import Enos.SpringProject.medVoll.domain.repositorys.IPatientRepository;
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

    @Transactional
    public ReadConsultDTO registerConsult(RegisterConsultDTO registerConsultDTO) {
        var schedule = LocalDateTime.parse(registerConsultDTO.horario(),DateTimeFormatter.ofPattern("dd/MM/uuuu:HH:mm:ss"));
        var patient = patientRepository.getReferenceByIdAndActive(registerConsultDTO.paciente_id(), 1);
        var doctor = selectDoctor(registerConsultDTO.medico_id(),registerConsultDTO.especialidade(),schedule);
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
        var aux = LocalDateTime.now();
        if (patient == null){
            throw new RequestBdException("não foi possivel resgatar o paciente do banco de dados");
        }
        if (doctor == null){
            throw new RequestBdException("não foi possivel resgatar o medico do banco de dados");
        }
        if (schedule == null){
            throw new RegisterConsultRuleException("não foi possivel definir o horario");
        }
        if (schedule.getDayOfWeek() == DayOfWeek.SUNDAY){
            throw new RegisterConsultRuleException("não se pode agendar no domingo");
        }
        if (!(schedule.getHour() >= 7 && schedule.getHour() <= 19)){
            throw new RegisterConsultRuleException("não é possivel agendar fora do horario de trabalho");
        }
        if(ChronoUnit.MINUTES.between(aux,schedule) < 30){
            throw new RegisterConsultRuleException("agendamento deve ter pelo menos 30 min de antecedencia");
        }
        if (schedule.isBefore(aux)){
            throw new RegisterConsultRuleException("data de agendamento é invalida");
        }
        for (Consult c : patient.getConsults()){
            if (c.getActive() != 0){
                if((c.getScheduleStart().getDayOfMonth() == schedule.getDayOfMonth())
                        && (c.getScheduleStart().getMonth() == schedule.getMonth())
                        && (c.getScheduleStart().getYear() == schedule.getYear())){
                    throw new RegisterConsultRuleException("um paciente so pode ter uma consulta por dia");
                }
            }
        }
        for (Consult c : doctor.getConsults()){
            if(c.getActive() != 0) {
                if (!schedule.isBefore(c.getScheduleStart()) && !schedule.isAfter(c.getScheduleEnd()) || schedule.isEqual(c.getScheduleStart())) {
                    throw new RegisterConsultRuleException("o medico indicado ja possui consulta nesse horario");
                }
            }
        }
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
