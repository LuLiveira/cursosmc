package com.lucasoliveira.cursomc.services.validation;

import com.lucasoliveira.cursomc.domain.dto.ClienteNewDTO;
import com.lucasoliveira.cursomc.domain.enums.TipoCliente;
import com.lucasoliveira.cursomc.resources.exceptions.FieldMessage;
import com.lucasoliveira.cursomc.services.validation.Utils.BR;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Override
    public void initialize(ClienteInsert ann) {
    }

    @Override
    public boolean isValid(ClienteNewDTO clienteNewDTO, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        if(clienteNewDTO.getTipoCliente().equals(TipoCliente.PESSSOAFISICA.getCod()) && !BR.isValidCPF(clienteNewDTO.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
        }

        if(clienteNewDTO.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(clienteNewDTO.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
        }

        //inclua os testes aqui
        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
