package pet.projects.homeworkaston.labels.service;


import pet.projects.homeworkaston.labels.dto.LabelDTO;

import java.util.List;

public interface LabelService {
    LabelDTO addLabel(LabelDTO labelDTO);

    List<LabelDTO> getAllLabels();

    LabelDTO getLabelById(Long id);

    public void updateLabel(LabelDTO labelDTO);

    boolean deleteLabel(Long id);
}

