package com.example.service;

import com.example.dao.StudentsMapper;
import com.example.eneity.Students;
import com.example.serviceimpl.StudentsServiceImpl;

import java.util.List;

public class StudentsService implements StudentsServiceImpl {

    private StudentsMapper StudentsDao;

    @Override
    public int add(Students students) {

        int count = StudentsDao.insert(students);

        return count;
    }

    @Override
    public int update(Students students) {

        int count = StudentsDao.updateByPrimaryKey(students);

        return count;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public List<Students> list() {
        return null;
    }
}
