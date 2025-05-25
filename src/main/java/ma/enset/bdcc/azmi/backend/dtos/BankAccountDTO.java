package ma.enset.bdcc.azmi.backend.dtos;

import lombok.Data;
import ma.enset.bdcc.azmi.backend.enums.AccountStatus;
import java.util.Date;

@Data
public class BankAccountDTO {
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private String type;
}
