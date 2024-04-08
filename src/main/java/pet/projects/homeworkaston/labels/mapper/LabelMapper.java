package pet.projects.homeworkaston.labels.mapper;


import pet.projects.homeworkaston.labels.dto.LabelDTO;
import pet.projects.homeworkaston.labels.model.Label;

public interface LabelMapper {
    LabelDTO toDTO(Label label);

    Label toLabel(LabelDTO labelDTO);
}

