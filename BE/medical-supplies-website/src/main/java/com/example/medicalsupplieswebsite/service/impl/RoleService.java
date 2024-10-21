package com.example.medicalsupplieswebsite.service.impl;

import com.example.medicalsupplieswebsite.entity.Role;
import com.example.medicalsupplieswebsite.repository.IRoleRepository;
import com.example.medicalsupplieswebsite.service.IRoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements IRoleService {
    private final IRoleRepository roleRepository;

    public RoleService(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Page<Role> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public Role update(Role role) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findSalesAndAccountantRoles();
    }
}
