
package br.sgde.base.model.entity;


public class Cidades {
    

    private long id;
    private String nome;
    private int estado_id;
    

    public Cidades() {
    }

    public Cidades(long id, String nome, int estado_id) {
        this.id = id;
        this.nome = nome;
        this.estado_id = estado_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getEstado_id() {
        return estado_id;
    }

    public void setEstado_id(int estado_id) {
        this.estado_id = estado_id;
    }
    
    
}
