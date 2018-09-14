package com.example.serviceimpl;

import com.example.eneity.Students;

import java.util.List;

public interface StudentsServiceImpl {

    public List<Students> list();

    public int add(Students students);

    public int update(Students students);

    public int delete(int id);

}
