package br.sgde.base.model.entity;


public class ListBancos {
    

    private Long cod;
    private String banco;

    public ListBancos() {
    }

    public ListBancos(Long cod, String banco) {
        this.cod = cod;
        this.banco = banco;
    }

    public Long getCod() {
        return cod;
    }

    public void setCod(Long cod) {
        this.cod = cod;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }


}
