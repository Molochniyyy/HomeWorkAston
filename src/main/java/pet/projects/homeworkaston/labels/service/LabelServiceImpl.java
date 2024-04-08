package pet.projects.homeworkaston.labels.service;


import pet.projects.homeworkaston.labels.dto.LabelDTO;
import pet.projects.homeworkaston.labels.mapper.LabelMapper;
import pet.projects.homeworkaston.labels.mapper.LabelMapperImpl;
import pet.projects.homeworkaston.labels.model.Label;
import pet.projects.homeworkaston.labels.repository.LabelRepository;

import java.util.List;
import java.util.stream.Collectors;

public class LabelServiceImpl implements LabelService {
    private final LabelRepository labelRepository;
    private final LabelMapper labelMapper;

    public LabelServiceImpl() {
        this.labelRepository = new LabelRepository();
        this.labelMapper = new LabelMapperImpl();
    }

    @Override
    public LabelDTO addLabel(LabelDTO labelDTO) {
        Label label = labelRepository.addLabel(labelMapper.toLabel(labelDTO));
        return labelMapper.toDTO(label);
    }

    @Override
    public List<LabelDTO> getAllLabels() {
        List<Label> labels = labelRepository.getAllLabels();
        return labels.stream()
                .map(labelMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LabelDTO getLabelById(Long id) {
        Label label = labelRepository.getLabelById(id);
        return label != null ? labelMapper.toDTO(label) : null;
    }

    @Override
    public void updateLabel(LabelDTO labelDTO) {
        Label label = labelMapper.toLabel(labelDTO);
        labelRepository.updateLabel(label);
    }

    @Override
    public boolean deleteLabel(Long id) {
        if (labelRepository.getLabelById(id) != null) {
            labelRepository.deleteLabel(id);
            return true;
        } else {
            return false;
        }

    }
}
