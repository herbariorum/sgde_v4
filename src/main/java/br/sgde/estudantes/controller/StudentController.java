package br.sgde.estudantes.controller;

import br.sgde.base.model.dao.EnderecoDao;
import br.sgde.base.model.entity.Endereco;
import br.sgde.base.util.Util;
import br.sgde.estudantes.model.dao.StudentDao;
import br.sgde.estudantes.model.entity.Students;
import br.sgde.estudantes.view.StudentsForm;
import br.sgde.estudantes.view.config.StudentCellRenderer;
import br.sgde.estudantes.view.config.StudentTableModel;
import br.sgde.exceptions.ExceptionDAO;
import java.util.List;
import javax.swing.table.TableRowSorter;
import raven.toast.Notifications;

public class StudentController implements IController {

    private Students student;
    private Endereco endereco;
    private StudentsForm frame;

    @Override
    public void save(Object view) {
        frame = (StudentsForm) view;

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
        frame = (StudentsForm) view;
        StudentDao dao = new StudentDao(Students.class);
        try {
            List<Students> students = dao.read(frame.stringDePesquisa);
            StudentTableModel modelo = new StudentTableModel(students);
            frame.getTblStudents().setModel(modelo);
            frame.getTblStudents().setDefaultRenderer(Object.class, new StudentCellRenderer());
            frame.getTblStudents().setRowSorter(new TableRowSorter<>(frame.getTblStudents().getModel()));
        } catch (ExceptionDAO ex) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.BOTTOM_LEFT, ex.getMessage());
        }
    }

    @Override
    public void listById(Long id) {
        StudentDao dao = new StudentDao(Students.class);
        EnderecoDao enderecoDao = new EnderecoDao(Endereco.class);
        student = new Students();
        endereco = new Endereco();
        try {
            student = dao.readById(id);
            preenchePanelStudent(student);
            endereco = enderecoDao.readByIdFk(student.getIdStudent());
            preenchePanelEndereco(endereco);
            frame.getTabbledPane().setSelectedIndex(0);
        } catch (ExceptionDAO ex) {

        }
    }

    private void preenchePanelStudent(Students s) {
        frame.setIdRow(s.getIdStudent());
        frame.getTxtNomeEstudante().setText(s.getNome());
        frame.getTxtCpf().setText(Util.formataCpf(s.getCpf()));
        frame.getTxtDataNascimento().setText(new Util().formatDate(s.getDta_nasc()));
        frame.getCkbStatus().setSelected(s.isStatus());
        frame.getCbxSexo().getModel().setSelectedItem(s.getSexo());
        frame.getTxtNacionalidade().setText(s.getNacionalidade());
        frame.getCbxEstadoNascimento().getModel().setSelectedItem(s.getUfnascimento());
        frame.getCbxCidadeNascimento().getModel().setSelectedItem(s.getCidadenascimento());
        frame.getTxtMae().setText(s.getNomemae());
        frame.getTxtTelefoneMae().setText(s.getTelefonemae());
        frame.getTxtPai().setText(s.getNomepai());
        frame.getTxtTelefonePai().setText(s.getTelefonepai());        
    }
    
    private void preenchePanelEndereco(Endereco e){
        frame.setIdEndereco(e.getIdaddress());
        frame.getTxtLogradouro().setText(e.getLogradouro());
        frame.getTxtNumero().setText(e.getNumero());
        frame.getTxtComplemento().setText(e.getComplemento());
        frame.getTxtBairro().setText(e.getBairro());
        frame.getCbxEstadoEndereco().getModel().setSelectedItem(e.getEstado());
        frame.getCbxCidadeEndereco().getModel().setSelectedItem(e.getCidade());
        frame.getTxtCep().setText(e.getCep());
    }

}
