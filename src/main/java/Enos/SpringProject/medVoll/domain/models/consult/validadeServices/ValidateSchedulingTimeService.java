package Enos.SpringProject.medVoll.domain.models.consult.validadeServices;

import Enos.SpringProject.medVoll.domain.exceptions.RegisterConsultRuleException;
import Enos.SpringProject.medVoll.domain.models.doctor.Doctor;
import Enos.SpringProject.medVoll.domain.models.patient.Patient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class ValidateSchedulingTimeService implements IConsultValidate{

    @Override
    public void validate(LocalDateTime schedule, Doctor doctor, Patient patient) {
        var aux = LocalDateTime.now();
        if(ChronoUnit.MINUTES.between(aux,schedule) < 30){
            throw new RegisterConsultRuleException("scheduling must be at least 30 min early");
        }
    }
}
