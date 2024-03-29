package br.sgde.base.model.entity;

public class Estados {
 
   
    private Long id;
    private String nome;
    private String uf;
    
    public Estados() {
    }

    public Estados(String nome, String uf) {
        this.nome = nome;
        this.uf = uf;
    }

    public Estados(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    
    
    public Estados(Long id, String nome, String uf) {
        this.id = id;
        this.nome = nome;
        this.uf = uf;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
    
    
    
}
