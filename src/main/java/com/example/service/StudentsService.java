package com.example.service;

import com.example.dao.StudentsMapper;
import com.example.eneity.Students;
import com.example.eneity.StudentsExample;
import com.example.serviceimpl.StudentsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "StudentsServiceImpl")
public class StudentsService implements StudentsServiceImpl {

    @Autowired
    public StudentsMapper StudentsDao;

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

        int count = StudentsDao.deleteByPrimaryKey(id);

        return count;
    }

    @Override
    public List<Students> list() {

        StudentsExample studentsExample = new StudentsExample();

        StudentsExample.Criteria criteria = studentsExample.createCriteria();

        //StudentsExample.Criteria criteria = studentsExample.createCriteria();
        //criteria.andAgeIsNotNull();

        List<Students> list = StudentsDao.selectByExample(studentsExample);

        return list;
    }
}
