package com.dh.DentalClinicMVC.service.impl;

import com.dh.DentalClinicMVC.entity.Dentist;
import com.dh.DentalClinicMVC.exception.ResourceNotFoundException;
import com.dh.DentalClinicMVC.repository.IDentistRepository;
import com.dh.DentalClinicMVC.service.IDentistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DentistServiceImpl implements IDentistService {

    private IDentistRepository dentistRepository;

    @Autowired
    public DentistServiceImpl(IDentistRepository dentistRepository) {
        this.dentistRepository = dentistRepository;
    }


    @Override
    public Dentist save(Dentist dentist) {
        return dentistRepository.save(dentist);
    }

    @Override
    public Optional<Dentist> findById(Long id) {
        return dentistRepository.findById(id);
    }

    @Override
    public void update(Dentist dentist) {
        dentistRepository.save(dentist);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Optional<Dentist> dentist = dentistRepository.findById(id);
        if(dentist.isPresent()) {
            dentistRepository.deleteById(id);
        }else {
            throw new ResourceNotFoundException("No se puede eliminar un odontólogo que no existe en la base de datos");
        }
    }

    @Override
    public List<Dentist> findAll() {
        return dentistRepository.findAll();
    }

    @Override
    public Optional<Dentist> findByRegistration(Integer registration) {
        return dentistRepository.findByRegistration(registration);
    }
}
