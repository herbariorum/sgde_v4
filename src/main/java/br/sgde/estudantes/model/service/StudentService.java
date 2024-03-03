package br.sgde.estudantes.model.service;

import java.awt.Component;
import java.awt.Container;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JTextField;


public class StudentService {

    public StudentService() {
    }

    public static List<Component> getComponentsAsList(Container cont) {
        return Stream.of(cont.getComponents())
                     .flatMap(c -> c instanceof Container ? Stream.concat(Stream.of(c), getComponentsAsList((Container) c).stream()) : Stream.of(c))
                     .collect(Collectors.toList());
    }

    public static boolean areSpecificFieldsValid(Container cont, List<String> namesToValidate) {
        List<Component> components = getComponentsAsList(cont);
        return components.stream()
                         .filter(c -> c instanceof JTextField && namesToValidate.contains(c.getName()))
                         .map(c -> (JTextField) c)
                         .allMatch(t -> !t.getText().isEmpty());
    }
    
    
}
