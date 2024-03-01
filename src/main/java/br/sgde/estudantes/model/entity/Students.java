
package br.sgde.estudantes.model.entity;

import br.sgde.base.model.entity.Endereco;
import java.math.BigDecimal;
import java.time.LocalDate;


/**
 *
 * @author elias
 */

public class Students {
    private Long idstudent;
    private String nome;
    private String cpf;
    private LocalDate dta_nasc;
    private String sexo;
    private boolean status;
    private String nomemae;
    private String nomepai;
    private String telefonemae;
    private String telefonepai;
    private String nacionalidade;
    private String ufnascimento;
    private String cidadenascimento;
    private BigDecimal salario;
    
    private Endereco endereco;

    public Students(Long idstudent, String nome, String cpf, LocalDate dta_nasc, String sexo, boolean status, String nomemae, String nomepai, String telefonemae, String telefonepai, String nacionalidade, String ufnascimento, String cidadenascimento, BigDecimal salario, Endereco endereco) {
        this.idstudent = idstudent;
        this.nome = nome;
        this.cpf = cpf;
        this.dta_nasc = dta_nasc;
        this.sexo = sexo;
        this.status = status;
        this.nomemae = nomemae;
        this.nomepai = nomepai;
        this.telefonemae = telefonemae;
        this.telefonepai = telefonepai;
        this.nacionalidade = nacionalidade;
        this.ufnascimento = ufnascimento;
        this.cidadenascimento = cidadenascimento;
        this.salario = salario;
        this.endereco = endereco;
    }


    public Students() {
    }

 

    public Long getIdStudent() {
        return idstudent;
    }

    public void setIdStudent(Long idstudent) {
        this.idstudent = idstudent;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDta_nasc() {
        return dta_nasc;
    }

    public void setDta_nasc(LocalDate dta_nasc) {
        this.dta_nasc = dta_nasc;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Long getIdstudent() {
        return idstudent;
    }

    public void setIdstudent(Long idstudent) {
        this.idstudent = idstudent;
    }

    public String getNomemae() {
        return nomemae;
    }

    public void setNomemae(String nomemae) {
        this.nomemae = nomemae;
    }

    public String getNomepai() {
        return nomepai;
    }

    public void setNomepai(String nomepai) {
        this.nomepai = nomepai;
    }

    public String getTelefonemae() {
        return telefonemae;
    }

    public void setTelefonemae(String telefonemae) {
        this.telefonemae = telefonemae;
    }

    public String getTelefonepai() {
        return telefonepai;
    }

    public void setTelefonepai(String telefonepai) {
        this.telefonepai = telefonepai;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getUfnascimento() {
        return ufnascimento;
    }

    public void setUfnascimento(String ufnascimento) {
        this.ufnascimento = ufnascimento;
    }

    public String getCidadenascimento() {
        return cidadenascimento;
    }

    public void setCidadenascimento(String cidadenascimento) {
        this.cidadenascimento = cidadenascimento;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "Students{" + "idstudent=" + idstudent + ", nome=" + nome + ", cpf=" + cpf + ", dta_nasc=" + dta_nasc + ", sexo=" + sexo + ", status=" + status + ", nomemae=" + nomemae + ", nomepai=" + nomepai + ", telefonemae=" + telefonemae + ", telefonepai=" + telefonepai + ", nacionalidade=" + nacionalidade + ", ufnascimento=" + ufnascimento + ", cidadenascimento=" + cidadenascimento + ", salario=" + salario + ", endereco=" + endereco + '}';
    }

    
    
    
}
