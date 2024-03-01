package br.sgde.estudantes.controller;

import br.sgde.base.model.dao.EnderecoDao;
import br.sgde.base.model.entity.Endereco;
import br.sgde.estudantes.model.dao.StudentDao;
import br.sgde.estudantes.model.entity.Student;
import br.sgde.estudantes.view.StudentForm;
import br.sgde.exceptions.ExceptionDAO;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentController implements IController {

    private Student student;
    private StudentForm frame;

    @Override
    public void save(Object view) {
        frame = (StudentForm) view;
        student = new Student();

    }

    @Override
    public void update(Object view) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Object view) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void listAll(Object view) {
       StudentDao dao = new StudentDao(Student.class);
        try {
            Student s  = dao.read(value)
        } catch (ExceptionDAO ex) {
            
        }
    }

    @Override
    public void listById(Long id){
        StudentDao dao = new StudentDao(Student.class);
        EnderecoDao enderecoDao = new EnderecoDao(Endereco.class);
        try {
            Student s = dao.readById(id);
            Endereco endereco = enderecoDao.readByIdFk(s.getId());
        } catch (ExceptionDAO ex) {

        }
    }
}
