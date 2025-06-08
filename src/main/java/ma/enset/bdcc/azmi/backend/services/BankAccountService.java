package ma.enset.bdcc.azmi.backend.services;

import java.util.List;
import ma.enset.bdcc.azmi.backend.dtos.*;
import org.springframework.data.domain.Page;

public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId);
    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId);
    List<CustomerDTO> listCustomers();
    List<CustomerDTO> searchCustomers(String keyword);

    BankAccountDTO getBankAccount(String accountId);
    List<BankAccountDTO> bankAccountList();
    CustomerDTO getCustomer(Long customerId);
    CustomerDTO updateCustomer(CustomerDTO customerDTO);
    void deleteCustomer(Long customerId);
    void debit(String accountId, double amount, String description);
    void credit(String accountId, double amount, String description);
    void transfer(String accountIdSource, String accountIdDestination, double amount);

    List<AccountOperationDTO> accountHistory(String accountId);
    AccountHistoryDTO getAccountHistory(String accountId, int page, int size);
}
