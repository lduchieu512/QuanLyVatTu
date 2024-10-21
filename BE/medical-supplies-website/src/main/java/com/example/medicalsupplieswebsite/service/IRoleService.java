package com.example.medicalsupplieswebsite.service;

import com.example.medicalsupplieswebsite.entity.Role;

import java.util.List;

public interface IRoleService extends IService<Role> {
    List<Role> getAllRoles();
}
