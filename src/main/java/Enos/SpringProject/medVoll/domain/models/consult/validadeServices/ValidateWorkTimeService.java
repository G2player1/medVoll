package Enos.SpringProject.medVoll.domain.models.consult.validadeServices;

import Enos.SpringProject.medVoll.domain.exceptions.RegisterConsultRuleException;
import Enos.SpringProject.medVoll.domain.models.doctor.Doctor;
import Enos.SpringProject.medVoll.domain.models.patient.Patient;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Service
public class ValidateWorkTimeService implements IConsultValidate {

    @Override
    public void validate(LocalDateTime schedule, Doctor doctor, Patient patient) {
        if (schedule.getDayOfWeek() == DayOfWeek.SUNDAY){
            throw new RegisterConsultRuleException("Don't open on sundays");
        }
        if (!(schedule.getHour() >= 7 && schedule.getHour() <= 19)){
            throw new RegisterConsultRuleException("Outside of business");
        }
    }
}
