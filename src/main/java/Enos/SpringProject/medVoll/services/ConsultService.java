package Enos.SpringProject.medVoll.services;

import Enos.SpringProject.medVoll.enums.ExpertiseEnum;
import Enos.SpringProject.medVoll.models.Consult;
import Enos.SpringProject.medVoll.models.Doctor;
import Enos.SpringProject.medVoll.models.Patient;
import Enos.SpringProject.medVoll.models.dto.reads.ReadConsultDTO;
import Enos.SpringProject.medVoll.models.dto.registers.RegisterConsultDTO;
import Enos.SpringProject.medVoll.repositorys.IConsultRepository;
import Enos.SpringProject.medVoll.repositorys.IDoctorRepository;
import Enos.SpringProject.medVoll.repositorys.IPatientRepository;
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
    public void registerConsult(RegisterConsultDTO registerConsultDTO) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu:HH:mm:ss");
        var schedule = LocalDateTime.parse(registerConsultDTO.horario(),dateTimeFormatter);
        var patient = patientRepository.getReferenceByIdAndActive(registerConsultDTO.pacienteId(), 1);
        Doctor doctor;
        if(registerConsultDTO.medicoId() != null){
            doctor = doctorRepository.getReferenceByIdAndActive(registerConsultDTO.medicoId(), 1);
        } else {
            doctor = selectRandomDoctor(registerConsultDTO.especialidade(), schedule);
        }
        if (!verifyConsult(schedule,doctor,patient)){
            return;
        }
        Consult consult = new Consult(doctor,patient,schedule);
        doctor.addConsult(consult);
        patient.addConsult(consult);
        consultRepository.save(consult);
    }

    @Transactional
    private Doctor selectRandomDoctor(String expertise, LocalDateTime schedule){
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
                if (schedule.isAfter(c.getScheduleStart()) && schedule.isBefore(c.getScheduleEnd())){
                    doctor = null;
                    break;
                }
            }
            if(doctor != null){
                break;
            }
        }
        return doctor;
    }


    //verifica se a consulta pode ser marcada
    private boolean verifyConsult(LocalDateTime schedule, Doctor doctor, Patient patient){
        var aux = LocalDateTime.now();
        if (doctor == null){
            return false;
        }
        if (patient == null){
            return false;
        }
        if (schedule == null){
            return false;
        }
        if (schedule.getDayOfWeek() == DayOfWeek.SUNDAY){
            return false;
        }
        if (!(schedule.getHour() > 7 && schedule.getHour() < 19)){
            return false;
        }
        if(ChronoUnit.MINUTES.between(schedule,aux) < 30){
            return false;
        }
        for (Consult c : patient.getConsults()){
            if((c.getScheduleStart().getDayOfMonth() == schedule.getDayOfMonth())
                    && (c.getScheduleStart().getMonth() == schedule.getMonth())
                    && (c.getScheduleStart().getYear() == schedule.getYear())){
                return false;
            }
        }
        for (Consult c : doctor.getConsults()){
            if (schedule.isAfter(c.getScheduleStart()) && schedule.isBefore(c.getScheduleEnd())){
                return false;
            }
        }
        return true;
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
        consults = aux
                .stream()
                .map(ReadConsultDTO::new)
                .toList();
        return new PageImpl<>(consults);
    }

    @Transactional
    public void deleteConsult(Long id) {
        var consult = consultRepository.getReferenceByIdAndActive(id,1);
        consult.consultDeleteLogical();
    }
}
