/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.scedu.domain.entities.grade.services;

import br.com.ifba.scedu.domain.entities.curso.model.Curso;
import br.com.ifba.scedu.domain.entities.curso.repository.CursoRepository;
import br.com.ifba.scedu.domain.entities.grade.exceptions.GradeNotFoundException;
import br.com.ifba.scedu.domain.entities.grade.exceptions.NullGradeException;
import br.com.ifba.scedu.domain.entities.grade.model.DTOs.GradeViewDTO;
import br.com.ifba.scedu.domain.entities.grade.model.Grade;
import br.com.ifba.scedu.domain.entities.grade.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lara
 */
@Service
public class GradeService {

    @Autowired
    GradeRepository repository;
    
    @Autowired
    CursoRepository cursoRepository;  // Injetar o repositório de Curso

    @Transactional
    public GradeViewDTO save(Grade grade) {
        if (grade == null) {
            throw new NullGradeException("Grade object cannot be null");
        }

        // Verificar se o curso associado à grade existe
        if (grade.getCourse() != null) {
            Curso curso = cursoRepository.findById(grade.getCourse().getId())
                    .orElseThrow(() -> new RuntimeException("Curso not found"));
            grade.setCourse(curso);
        }

        repository.save(grade);

        return new GradeViewDTO(
                grade.getId(),
                grade.getCode(),
                grade.getName(),
                grade.getCurriculumCode(),
                grade.getCourse() != null ? grade.getCourse().getId() : null
        );
    }

    @Transactional(readOnly = true)
    public Page<Grade> findAll(Pageable pageable) {
        Page<Grade> grades = this.repository.findAll(pageable);
        if (grades.isEmpty()) {
            throw new GradeNotFoundException("No grades found");
        }
        return grades;
    }

    @Transactional(readOnly = true)
    public GradeViewDTO findById(Long id) {
        var grade = repository.findById(id)
                .orElseThrow(() -> new GradeNotFoundException("No records found for this ID"));

        return new GradeViewDTO(
                grade.getId(),
                grade.getCode(),
                grade.getName(),
                grade.getCurriculumCode(),
                grade.getCourse() != null ? grade.getCourse().getId() : null
        );
    }

    @Transactional
    public GradeViewDTO update(GradeViewDTO newGrade, Long id) {
        var currentGrade = repository.findById(id)
                .orElseThrow(() -> new GradeNotFoundException("No records found for this ID"));
        
        currentGrade.setName(newGrade.name());
        currentGrade.setCurriculumCode(newGrade.curriculumCode());
        currentGrade.setCode(newGrade.code());

        // Atualizar curso se necessário
        if (newGrade.courseId() != null) {
            Curso curso = cursoRepository.findById(newGrade.courseId())
                    .orElseThrow(() -> new RuntimeException("Curso not found"));
            currentGrade.setCourse(curso);
        }

        repository.save(currentGrade);

        return new GradeViewDTO(
                currentGrade.getId(),
                currentGrade.getCode(),
                currentGrade.getName(),
                currentGrade.getCurriculumCode(),
                currentGrade.getCourse() != null ? currentGrade.getCourse().getId() : null
        );
    }

    @Transactional
    public void delete(Long id) {
        Grade entity = repository.findById(id)
                .orElseThrow(() -> new GradeNotFoundException("No records found for this ID"));
        repository.delete(entity);
    }
}

