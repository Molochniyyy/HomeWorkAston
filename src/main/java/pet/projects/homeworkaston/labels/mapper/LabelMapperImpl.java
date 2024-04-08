package pet.projects.homeworkaston.labels.mapper;

import pet.projects.homeworkaston.labels.dto.LabelDTO;
import pet.projects.homeworkaston.labels.model.Label;

public class LabelMapperImpl implements LabelMapper {

    @Override
    public LabelDTO toDTO(Label label) {
        if (label == null) {
            return null;
        }
        LabelDTO labelDTO = new LabelDTO();
        labelDTO.setId(label.getId());
        labelDTO.setName(label.getName());
        return labelDTO;
    }

    @Override
    public Label toLabel(LabelDTO labelDTO) {
        if (labelDTO == null) {
            return null;
        }
        Label label = new Label();
        label.setId(labelDTO.getId());
        label.setName(labelDTO.getName());
        return label;
    }
}

