package Enos.SpringProject.medVoll.domain.models.consult.validadeServices;

import Enos.SpringProject.medVoll.domain.exceptions.RegisterConsultRuleException;
import Enos.SpringProject.medVoll.domain.models.consult.Consult;
import Enos.SpringProject.medVoll.domain.models.doctor.Doctor;
import Enos.SpringProject.medVoll.domain.models.patient.IPatientRepository;
import Enos.SpringProject.medVoll.domain.models.patient.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ValidatePatientConsultsService implements IConsultValidate{

    @Autowired
    private IPatientRepository patientRepository;

    @Override
    public void validate(LocalDateTime schedule, Doctor doctor, Patient patient) {
        for (Consult c : patient.getConsults()){
            if (c.getActive() != 0){
                if((c.getScheduleStart().getDayOfMonth() == schedule.getDayOfMonth())
                        && (c.getScheduleStart().getMonth() == schedule.getMonth())
                        && (c.getScheduleStart().getYear() == schedule.getYear())){
                    throw new RegisterConsultRuleException("um paciente so pode ter uma consulta por dia");
                }
            }
        }
    }
}
