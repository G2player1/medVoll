package Enos.SpringProject.medVoll.domain.models.consult.validadeServices;

import Enos.SpringProject.medVoll.domain.models.consult.dto.RegisterConsultDTO;
import Enos.SpringProject.medVoll.domain.models.doctor.Doctor;
import Enos.SpringProject.medVoll.domain.models.patient.Patient;

import java.time.LocalDateTime;

public interface IConsultValidate {

    void validate(LocalDateTime schedule, Doctor doctor, Patient patient);
}
