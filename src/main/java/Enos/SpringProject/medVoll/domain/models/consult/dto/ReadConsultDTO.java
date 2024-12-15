package Enos.SpringProject.medVoll.domain.models.consult.dto;

import Enos.SpringProject.medVoll.domain.models.consult.Consult;
import Enos.SpringProject.medVoll.domain.models.doctor.dto.ReadDoctorDTO;
import Enos.SpringProject.medVoll.domain.models.patient.dto.ReadPatientDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReadConsultDTO {

    private Long id;
    private ReadDoctorDTO medico;
    private ReadPatientDTO paciente;
    private LocalDateTime horario_inicio;
    private LocalDateTime horario_fim;

    public ReadConsultDTO(Consult consult){
        this.id = consult.getId();
        this.medico = new ReadDoctorDTO(consult.getDoctor());
        this.paciente = new ReadPatientDTO(consult.getPatient());
        this.horario_inicio = consult.getScheduleStart();
        this.horario_fim = consult.getScheduleEnd();
    }
}
