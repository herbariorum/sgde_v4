package br.sgde.estudantes.controller;

import br.sgde.base.model.dao.CidadeDao;
import br.sgde.base.model.dao.EnderecoDao;
import br.sgde.base.model.dao.PreencheComboBox;
import br.sgde.base.model.entity.Cidades;
import br.sgde.base.model.entity.Endereco;
import br.sgde.base.model.entity.Estados;
import br.sgde.base.util.ComboBoxList;
import br.sgde.base.util.LogGenerator;
import br.sgde.base.util.Util;
import br.sgde.base.validation.CPF;
import br.sgde.base.validation.ValidaData;
import br.sgde.base.validation.ValidaNome;
import br.sgde.base.validation.ValidaTelefone;
import br.sgde.estudantes.model.dao.StudentDao;
import br.sgde.estudantes.model.entity.Students;
import br.sgde.estudantes.model.service.StudentService;
import br.sgde.estudantes.view.StudentsForm;
import br.sgde.estudantes.view.config.StudentCellRenderer;
import br.sgde.estudantes.view.config.StudentTableModel;
import br.sgde.exceptions.ExceptionDAO;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.TableRowSorter;
import raven.toast.Notifications;

public class StudentController implements IController {

    private Students student;
    private Endereco endereco;
    private StudentsForm frame;

    public StudentController() {

    }

    @Override
    public void save(Object view) {
        frame = (StudentsForm) view;
        StudentDao dao = new StudentDao();
        List<String> fieldsToValidate = Arrays.asList("txtNome", "txtNacionalidade", "txtNomeMae", "txtLogradouro");
        boolean isValidEmpty = StudentService.areSpecificFieldsValid(frame, fieldsToValidate);
        boolean isValidNome = new ValidaNome().valida(frame.getTxtNomeEstudante().getText().strip());
        boolean isValidCPF = new CPF(frame.getTxtCpf().getText().replace(".", "").replace("-", "")).isCPF();
        boolean isValidDate = new ValidaData("dd/MM/yyyy").valida(frame.getTxtDataNascimento().getText().strip());
        boolean isValidMae = new ValidaNome().valida(frame.getTxtMae().getText().strip());
        boolean isValidTelefone = new ValidaTelefone().valida(frame.getTxtTelefoneMae().getText().strip());
        boolean isValidSexo = frame.getCbxSexo().getSelectedIndex() == 0;
        
        if (!isValidEmpty){
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_LEFT, "Os campos marcados com (*) não podem ficar vazios.");
            return;
        }
        if (!isValidNome){
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_LEFT, "O campo nome deve ter no mínimo duas palavras.");
            return;
        }
        if (!isValidCPF){
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_LEFT, "O CPF deve ser válido.");
            return;
        }
        if (!isValidDate){
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_LEFT, "A data deve ser válida.");
            return;
        }
        if (!isValidMae){
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_LEFT, "O campo nome deve ter no mínimo duas palavras.");
            return;
        }
        if (!isValidTelefone) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_LEFT, "Digite um telefone válido.");
            return;
        }
        if (isValidSexo) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_LEFT, "Selecione o sexo.");
            return;
        }
        endereco = new Endereco(
                frame.getIdEndereco(),
                frame.getTxtLogradouro().getText(),
                frame.getTxtNumero().getText(),
                frame.getTxtComplemento().getText(),
                frame.getTxtBairro().getText(),
                frame.getCbxEstadoEndereco().getSelectedItem().toString(),
                frame.getCbxCidadeEndereco().getSelectedItem().toString(),
                new Util().apenasNumero(frame.getTxtCep().getText())
        );

        student = new Students(
                frame.getIdRow(),
                frame.getTxtNomeEstudante().getText(),
                new Util().apenasNumero(frame.getTxtCpf().getText()),
                new Util().formatDateToUs(frame.getTxtDataNascimento().getText()),
                frame.getCbxSexo().getSelectedItem().toString(),
                frame.getCkbStatus().isSelected(),
                frame.getTxtMae().getText(),
                frame.getTxtPai().getText(),
                new Util().apenasNumero(frame.getTxtTelefoneMae().getText()),
                new Util().apenasNumero(frame.getTxtTelefonePai().getText()),
                frame.getTxtNacionalidade().getText(),
                frame.getCbxEstadoNascimento().getSelectedItem().toString(),
                frame.getCbxCidadeNascimento().getSelectedItem().toString(),
                endereco);

        try {
            if (frame.getIdRow() == null) {
                dao.save(student);
            } else {
                dao.update(student);
            }
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.BOTTOM_LEFT, "Registro salvo com sucesso.");
            frame.preencherTabela("");
            this.cancelaAction();
        } catch (ExceptionDAO ex) {
            LogGenerator.generateLog(ex.getMessage());
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.BOTTOM_LEFT, "Erro ao salvar o registro.");
            this.cancelaAction();
        }

    }

    @Override
    public void delete(Object view) {
        StudentDao dao = new StudentDao();
        if (frame.getIdRow() == null) {
            JOptionPane.showMessageDialog(null, "Por favor, selecione um registro na tabela.");
        } else {
            if (JOptionPane.showConfirmDialog(null, "Confirme a exclusão do item.", "Confirmação",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                student = new Students();
                student.setIdStudent(frame.getIdRow());
                try {
                    dao.delete(student);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.BOTTOM_LEFT, "Registro excluído com sucesso.");
                    frame.preencherTabela("");
                    this.cancelaAction();
                } catch (ExceptionDAO ex) {
                    LogGenerator.generateLog(ex.getMessage());
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.BOTTOM_LEFT, "Ocorreu um erro ao excluir o registro.");
                }
            } else {
                this.cancelaAction();
            }
        }
    }

    @Override
    public void listAll(Object view, String valor) {
        frame = (StudentsForm) view;
        StudentDao dao = new StudentDao();
        try {
            List<Students> students = dao.listByValue(valor);
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
        StudentDao dao = new StudentDao();
        EnderecoDao enderecoDao = new EnderecoDao(Endereco.class);
        student = new Students();
        endereco = new Endereco();
        try {
            student = dao.findById(id);
            preenchePanelStudent(student);
            endereco = enderecoDao.readByIdFk(student.getIdStudent());
            preenchePanelEndereco(endereco);
            frame.getTabbledPane().setSelectedIndex(0);
            habilitaBotoes(false);
            habilitaCampos(true);
        } catch (ExceptionDAO ex) {

        }
    }

    public void cancelaAction() {
        this.limpaForm();
        this.habilitaBotoes(true);
        this.habilitaCampos(false);
        this.preencheComboEstadoNascimento();
        this.preencheComboEstadoEndereco();
        if (frame.getIdRow() != null) {
            frame.setIdRow(null);
        }
        if (frame.getIdEndereco() != null) {
            frame.setIdEndereco(null);
        }
        frame.getTblStudents().clearSelection();
        if (frame.getTabbledPane().getSelectedIndex() != 1) {
            frame.getTabbledPane().setSelectedIndex(1);
        }
    }

    public void adicionaAction() {
        this.limpaForm();
        this.habilitaBotoes(false);
        this.habilitaCampos(true);
        this.preencheComboEstadoNascimento();
        this.preencheComboEstadoEndereco();
        if (frame.getIdRow() != null) {
            frame.setIdRow(null);
        }
        if (frame.getIdEndereco() != null) {
            frame.setIdEndereco(null);
        }
        frame.getTblStudents().clearSelection();
        if (frame.getTabbledPane().getSelectedIndex() != 0) {
            frame.getTabbledPane().setSelectedIndex(0);
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

    private void preenchePanelEndereco(Endereco e) {
        frame.setIdEndereco(e.getIdaddress());
        frame.getTxtLogradouro().setText(e.getLogradouro());
        frame.getTxtNumero().setText(e.getNumero());
        frame.getTxtComplemento().setText(e.getComplemento());
        frame.getTxtBairro().setText(e.getBairro());
        frame.getCbxEstadoEndereco().getModel().setSelectedItem(e.getEstado());
        frame.getCbxCidadeEndereco().getModel().setSelectedItem(e.getCidade());
        frame.getTxtCep().setText(e.getCep());
    }

    public void limpaForm() {
        frame.setIdRow(null);
        frame.getTxtNomeEstudante().setText(null);
        frame.getTxtCpf().setText(null);
        frame.getTxtDataNascimento().setText(null);
        frame.getCkbStatus().setSelected(false);
        frame.getCbxSexo().setSelectedIndex(0);
        frame.getTxtNacionalidade().setText(null);
        frame.getCbxEstadoNascimento().setSelectedIndex(0);
        frame.getTxtMae().setText(null);
        frame.getTxtTelefoneMae().setText(null);
        frame.getTxtPai().setText(null);
        frame.getTxtTelefonePai().setText(null);
        frame.setIdEndereco(null);
        frame.getTxtLogradouro().setText(null);
        frame.getTxtNumero().setText(null);
        frame.getTxtComplemento().setText(null);
        frame.getTxtBairro().setText(null);
        frame.getCbxEstadoEndereco().setSelectedIndex(0);
        frame.getTxtCep().setText(null);
    }

    public void preencheComboEstadoNascimento() {
        PreencheComboBox combo = new PreencheComboBox(Estados.class);
        try {
            combo.comboBoxEstado();
            frame.getCbxEstadoNascimento().removeAllItems();
            for (ComboBoxList c : combo.getList()) {
                frame.getCbxEstadoNascimento().addItem(c.toString());
            }
        } catch (ExceptionDAO ex) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.BOTTOM_LEFT, ex.getMessage());
            LogGenerator.generateLog(ex.getMessage());
        }
    }

    public void preencheComboEstadoEndereco() {
        PreencheComboBox combo = new PreencheComboBox(Estados.class);
        try {
            combo.comboBoxEstado();
            frame.getCbxEstadoEndereco().removeAllItems();
            for (ComboBoxList c : combo.getList()) {
                frame.getCbxEstadoEndereco().addItem(c.toString());
            }
        } catch (ExceptionDAO ex) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.BOTTOM_LEFT, ex.getMessage());
            LogGenerator.generateLog(ex.getMessage());
        }
    }

    public void preencheComboCidadeNascimento() {
        CidadeDao dao = new CidadeDao(Cidades.class);
        Integer idUf = frame.getCbxEstadoNascimento().getSelectedIndex() + 1;
        List<Cidades> cidades;
        frame.getCbxCidadeNascimento().removeAllItems();
        try {
            cidades = dao.list(idUf.longValue());
            for (Cidades c : cidades) {
                frame.getCbxCidadeNascimento().addItem(c.getNome());
            }
        } catch (ExceptionDAO ex) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.BOTTOM_LEFT, ex.getMessage());
            LogGenerator.generateLog(ex.getMessage());
        }
    }

    public void preencheComboCidadeEndereco() {
        CidadeDao dao = new CidadeDao(Cidades.class);
        Integer idUf = frame.getCbxEstadoEndereco().getSelectedIndex() + 1;
        List<Cidades> cidades;
        frame.getCbxCidadeEndereco().removeAllItems();
        try {
            cidades = dao.list(idUf.longValue());
            for (Cidades c : cidades) {
                frame.getCbxCidadeEndereco().addItem(c.getNome());
            }
        } catch (ExceptionDAO ex) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.BOTTOM_LEFT, ex.getMessage());
            LogGenerator.generateLog(ex.getMessage());
        }
    }

    public void habilitaBotoes(boolean valor) {
        frame.getLblIncluir().setEnabled(valor);
        frame.getLblSalvar().setEnabled(!valor);
        frame.getLblCancelar().setEnabled(!valor);
    }

    public void habilitaCampos(boolean valor) {
        frame.getTxtNomeEstudante().setEnabled(valor);
        frame.getTxtCpf().setEnabled(valor);
        frame.getTxtDataNascimento().setEnabled(valor);
        frame.getCkbStatus().setEnabled(valor);
        frame.getCbxSexo().setEnabled(valor);
        frame.getTxtNacionalidade().setEnabled(valor);
        frame.getCbxEstadoNascimento().setEnabled(valor);
        frame.getCbxCidadeNascimento().setEnabled(valor);
        frame.getTxtMae().setEnabled(valor);
        frame.getTxtTelefoneMae().setEnabled(valor);
        frame.getTxtPai().setEnabled(valor);
        frame.getTxtTelefonePai().setEnabled(valor);
        frame.getTxtLogradouro().setEnabled(valor);
        frame.getTxtNumero().setEnabled(valor);
        frame.getTxtComplemento().setEnabled(valor);
        frame.getTxtBairro().setEnabled(valor);
        frame.getCbxEstadoEndereco().setEnabled(valor);
        frame.getCbxCidadeEndereco().setEnabled(valor);
        frame.getTxtCep().setEnabled(valor);
    }

}
