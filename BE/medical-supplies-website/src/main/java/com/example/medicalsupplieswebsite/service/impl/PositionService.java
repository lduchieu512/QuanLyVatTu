package com.example.medicalsupplieswebsite.service.impl;

import com.example.medicalsupplieswebsite.entity.Position;
import com.example.medicalsupplieswebsite.repository.IPositionRepository;
import com.example.medicalsupplieswebsite.service.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionService implements IPositionService {
    @Autowired
    private IPositionRepository iPositionRepository;
    /**
     * Created by: PhongTD
     * Date created: 12/07/2023
     * @return List position
     */
    public List<Position> findAll() {
        return iPositionRepository.findAll();

    }
    /**
     * Created by: PhongTD
     * Date created: 12/07/2023
     * @param id
     * @return position was found by id
     */
    @Override
    public Position findById(Long id) {
        return iPositionRepository.findAllById(id);
    }

    @Override
    public Position save(Position position) {
        return null;
    }

    @Override
    public Position update(Position position) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }


}
