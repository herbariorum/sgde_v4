
package br.sgde.estudantes.controller;


public interface IController {
    public void save(Object view);
    
//    public void update(Object view);
    
    public void delete(Object view);
    
    public void listAll(Object view, String valor);
    
    public void listById(Long id);
    
}
