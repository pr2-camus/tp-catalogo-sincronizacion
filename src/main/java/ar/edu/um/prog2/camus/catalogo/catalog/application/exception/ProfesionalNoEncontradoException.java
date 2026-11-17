package ar.edu.um.prog2.camus.catalogo.catalog.application.exception;

import ar.edu.um.prog2.camus.catalogo.error.BusinessException;
import ar.edu.um.prog2.camus.catalogo.error.ErrorKind;

public class ProfesionalNoEncontradoException extends BusinessException {

    public ProfesionalNoEncontradoException(Long profesionalId) {
        super("PROFESSIONAL_NOT_FOUND", ErrorKind.NOT_FOUND, "No existe el profesional con id " + profesionalId);
    }
}
