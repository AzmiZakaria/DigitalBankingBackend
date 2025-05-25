package ma.enset.bdcc.azmi.backend.dtos;

import lombok.Data;
import ma.enset.bdcc.azmi.backend.enums.OperationType;

import java.util.Date;

@Data
public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;
}

