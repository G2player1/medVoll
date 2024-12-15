package Enos.SpringProject.medVoll.domain.models.consult.validadeServices;

import Enos.SpringProject.medVoll.domain.exceptions.RegisterConsultRuleException;
import Enos.SpringProject.medVoll.domain.models.consult.Consult;
import Enos.SpringProject.medVoll.domain.models.doctor.Doctor;
import Enos.SpringProject.medVoll.domain.models.patient.Patient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ValidateDoctorConsultsService implements IConsultValidate{

    @Override
    public void validate(LocalDateTime schedule, Doctor doctor, Patient patient) {
        for (Consult c : doctor.getConsults()){
            if(c.getActive() != 0) {
                if (!schedule.isBefore(c.getScheduleStart()) && !schedule.isAfter(c.getScheduleEnd()) || schedule.isEqual(c.getScheduleStart())) {
                    throw new RegisterConsultRuleException("o medico indicado ja possui consulta nesse horario");
                }
            }
        }
    }
}
