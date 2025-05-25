package ma.enset.bdcc.azmi.backend.dtos;

import lombok.Data;

@Data
public class SavingBankAccountDTO extends BankAccountDTO {
    private double interestRate;
}
