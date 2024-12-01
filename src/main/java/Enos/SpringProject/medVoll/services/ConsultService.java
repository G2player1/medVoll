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
        var patient = patientRepository.getReferenceByIdAndActive(registerConsultDTO.paciente_id(), 1);
        Doctor doctor;
        if(registerConsultDTO.medico_id() != null){
            doctor = doctorRepository.getReferenceByIdAndActive(registerConsultDTO.medico_id(), 1);
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
            System.out.println(doctor);
            for (Consult c : d.getConsults()){
                if (c.getActive() != 0) {
                    System.out.println(c);
                    System.out.println("start: " + c.getScheduleStart());
                    System.out.println("Schedule: " + schedule);
                    System.out.println("end: " + c.getScheduleEnd());
                    if (!schedule.isBefore(c.getScheduleStart()) && !schedule.isAfter(c.getScheduleEnd()) || schedule.isEqual(c.getScheduleStart())){
                        doctor = null;
                        System.out.println(doctor);
                    }
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
            System.out.println("medico nulo ou ocupado");
            return false;
        }
        if (patient == null){
            System.out.println("paciente nulo");
            return false;
        }
        if (schedule == null){
            System.out.println("horario nulo");
            return false;
        }
        if (schedule.getDayOfWeek() == DayOfWeek.SUNDAY){
            System.out.println("horario é domingo");
            return false;
        }
        if (!(schedule.getHour() >= 7 && schedule.getHour() <= 19)){
            System.out.println("não esta aberto");
            return false;
        }
        if(ChronoUnit.MINUTES.between(aux,schedule) < 30){
            System.out.println("muito cedo");
            return false;
        }
        if (schedule.isBefore(aux)){
            System.out.println("a data é invalida");
            return false;
        }
        for (Consult c : patient.getConsults()){
            if (c.getActive() != 0){
                if((c.getScheduleStart().getDayOfMonth() == schedule.getDayOfMonth())
                        && (c.getScheduleStart().getMonth() == schedule.getMonth())
                        && (c.getScheduleStart().getYear() == schedule.getYear())){
                    System.out.println("paciente tem outra consulta");
                    return false;
                }
            }
        }
        for (Consult c : doctor.getConsults()){
            if(c.getActive() != 0) {
                if (!schedule.isBefore(c.getScheduleStart()) && !schedule.isAfter(c.getScheduleEnd()) || schedule.isEqual(c.getScheduleStart())) {
                    System.out.println("medico ocupado nesse horario");
                    return false;
                }
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
