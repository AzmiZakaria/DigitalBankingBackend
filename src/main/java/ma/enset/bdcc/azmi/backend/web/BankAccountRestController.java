package ma.enset.bdcc.azmi.backend.web;

import lombok.AllArgsConstructor;
import ma.enset.bdcc.azmi.backend.dtos.*;
import ma.enset.bdcc.azmi.backend.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/accounts")
public class BankAccountRestController {
    private BankAccountService bankAccountService;

    @PostMapping("/current")
    public CurrentBankAccountDTO saveCurrentBankAccount(
            @RequestParam double initialBalance,
            @RequestParam double overDraft,
            @RequestParam Long customerId) {
        return bankAccountService.saveCurrentBankAccount(initialBalance, overDraft, customerId);
    }

    @PostMapping("/saving")
    public SavingBankAccountDTO saveSavingBankAccount(
            @RequestParam double initialBalance,
            @RequestParam double interestRate,
            @RequestParam Long customerId) {
        return bankAccountService.saveSavingBankAccount(initialBalance, interestRate, customerId);
    }

    @GetMapping("/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) {
        return bankAccountService.getBankAccount(accountId);
    }

    @GetMapping
    public List<BankAccountDTO> listAccounts() {
        return bankAccountService.bankAccountList();
    }

    // @GetMapping("/{accountId}/operations")
    // public List<AccountOperationDTO> getHistory(@PathVariable String accountId) {
    //     return bankAccountService.accountHistory(accountId);
    // }

    // @GetMapping("/{accountId}/pageOperations")
    // public AccountHistoryDTO getAccountHistory(
    //         @PathVariable String accountId,
    //         @RequestParam(name = "page", defaultValue = "0") int page,
    //         @RequestParam(name = "size", defaultValue = "5") int size) {
    //     return bankAccountService.getAccountHistory(accountId, page, size);
    // }

    // @PostMapping("/debit")
    // public DebitDTO debit(@RequestBody DebitDTO debitDTO) {
    //     this.bankAccountService.debit(debitDTO.getAccountId(), debitDTO.getAmount(), debitDTO.getDescription());
    //     return debitDTO;
    // }

    // @PostMapping("/credit")
    // public CreditDTO credit(@RequestBody CreditDTO creditDTO) {
    //     this.bankAccountService.credit(creditDTO.getAccountId(), creditDTO.getAmount(), creditDTO.getDescription());
    //     return creditDTO;
    // }

    // @PostMapping("/transfer")
    // public void transfer(@RequestBody TransferRequestDTO transferRequestDTO) {
    //     this.bankAccountService.transfer(
    //             transferRequestDTO.getAccountSource(),
    //             transferRequestDTO.getAccountDestination(),
    //             transferRequestDTO.getAmount());
    // }
}
