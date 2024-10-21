package com.example.medicalsupplieswebsite.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IService<T> {
    Page<T> findAll(Pageable pageable);

    T findById(Long id);

    T update(T t);

    void deleteById(Long id);
}
