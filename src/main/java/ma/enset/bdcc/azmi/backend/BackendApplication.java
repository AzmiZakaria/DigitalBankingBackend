package ma.enset.bdcc.azmi.backend;

import ma.enset.bdcc.azmi.backend.dtos.BankAccountDTO;
import ma.enset.bdcc.azmi.backend.dtos.CurrentBankAccountDTO;
import ma.enset.bdcc.azmi.backend.dtos.CustomerDTO;
import ma.enset.bdcc.azmi.backend.dtos.SavingBankAccountDTO;
import ma.enset.bdcc.azmi.backend.entities.*;
import ma.enset.bdcc.azmi.backend.enums.AccountStatus;
import ma.enset.bdcc.azmi.backend.enums.OperationType;
import ma.enset.bdcc.azmi.backend.repositories.AccountOperationRepository;
import ma.enset.bdcc.azmi.backend.repositories.BankAccountRepository;
import ma.enset.bdcc.azmi.backend.repositories.CustumerRepository;
import ma.enset.bdcc.azmi.backend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

   @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService) {
        return args -> {
            
            try {
                // Test saveCustomer with DTO
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setName("zakaria");
                customerDTO.setEmail("zakaria@gmail.com");
                CustomerDTO savedCustomer = bankAccountService.saveCustomer(customerDTO);
                System.out.println("=== Customer saved successfully with ID: " + savedCustomer.getId());
                Stream.of("yassine", "hamza", "amine", "fatima", "salma", "ayoub", "sara", "mohamed", "iman", "reda").forEach(name->{
                    CustomerDTO customer = new CustomerDTO();
                    customer.setName(name);
                    customer.setEmail(name+"@gmail.com");
                    bankAccountService.saveCustomer(customer);
                });
                bankAccountService.listCustomers().forEach(customer -> {
                    try {
                        // Create a current account
                        CurrentBankAccountDTO currentAccountDTO = bankAccountService.saveCurrentBankAccount(
                                Math.random() * 9000 + 1000, // balance between 1000 and 10000
                                5000, // overdraft
                                customer.getId()
                        );
                        // Create a saving account
                        SavingBankAccountDTO savingAccountDTO = bankAccountService.saveSavingBankAccount(
                                Math.random() * 9000 + 1000, // balance between 1000 and 10000
                                4.5, // interest rate
                                customer.getId()
                        );
                        // Make some operations on current account
                        bankAccountService.credit(currentAccountDTO.getId(), 2000, "Initial credit");
                        bankAccountService.debit(currentAccountDTO.getId(), 500, "Initial debit");
                        bankAccountService.credit(currentAccountDTO.getId(), 1200, "Salary deposit");
                        bankAccountService.debit(currentAccountDTO.getId(), 300, "Grocery shopping");
                        bankAccountService.credit(currentAccountDTO.getId(), 800, "Refund");
                        bankAccountService.debit(currentAccountDTO.getId(), 100, "Coffee shop");
                        // Make some operations on saving account
                        bankAccountService.credit(savingAccountDTO.getId(), 1500, "Initial credit");
                        bankAccountService.debit(savingAccountDTO.getId(), 300, "Initial debit");
                        bankAccountService.credit(savingAccountDTO.getId(), 700, "Monthly saving");
                        bankAccountService.debit(savingAccountDTO.getId(), 200, "Emergency withdrawal");
                        bankAccountService.credit(savingAccountDTO.getId(), 500, "Bonus");
                        bankAccountService.debit(savingAccountDTO.getId(), 100, "Online purchase");
                    } catch (Exception e) {
                        System.err.println("Error creating accounts or operations for customer " + customer.getName() + ": " + e.getMessage());
                    }
                });
                
                // Test saveCurrentBankAccount
                CurrentBankAccountDTO currentAccount = bankAccountService.saveCurrentBankAccount(10000, 5000, savedCustomer.getId());
                System.out.println("=== Current Account created: " + currentAccount.getId());

                // Test saveSavingBankAccount
                SavingBankAccountDTO savingAccount = bankAccountService.saveSavingBankAccount(12000, 5.5, savedCustomer.getId());
                System.out.println("=== Saving Account created: " + savingAccount.getId());

                // Test credit operation
                bankAccountService.credit(currentAccount.getId(), 1000, "First credit");
                System.out.println("=== Credit operation done ===");

                // Test debit operation
                bankAccountService.debit(currentAccount.getId(), 500, "First debit");
                System.out.println("=== Debit operation done ===");

                // Test transfer
                bankAccountService.transfer(
                    currentAccount.getId(),
                    savingAccount.getId(),
                    1000
                );
                System.out.println("=== Transfer operation done ===");
                // Test getBankAccount
                BankAccountDTO retrievedAccount = bankAccountService.getBankAccount(currentAccount.getId());
                System.out.println("=== Retrieved account balance: " + retrievedAccount.getBalance());

                // Test listCustomers
                System.out.println("=== List of Customers ===");
                bankAccountService.listCustomers().forEach(customer -> {
                    System.out.println(customer.getName());
                });

            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }

    // @Bean @Transactional
    CommandLineRunner start(CustumerRepository custumerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository){
        return args -> {
            Stream.of("zakaria","yassine").forEach(name->{
                Customer customer= new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                custumerRepository.save(customer);
            });

            // create saving accounts for customer withe name "zakaria"
            SavingAccount savingAccount1 = new SavingAccount();
            savingAccount1.setId(UUID.randomUUID().toString());
            savingAccount1.setCreatedAt(new Date());
            savingAccount1.setCustomer(custumerRepository.findByName("zakaria"));
            savingAccount1.setBalance(Math.random()*10000);
            savingAccount1.setInterestRate(5.5);
            savingAccount1.setStatus(AccountStatus.CREATED);
            bankAccountRepository.save(savingAccount1);

            // create current accounts for customer withe name "yassine"
            CurrentAccount currentAccount2 = new CurrentAccount();
            currentAccount2.setId(UUID.randomUUID().toString());
            currentAccount2.setCreatedAt(new Date());
            currentAccount2.setCustomer(custumerRepository.findByName("yassine"));
            currentAccount2.setBalance(Math.random()*10000);
            currentAccount2.setOverDraft(10000);
            currentAccount2.setStatus(AccountStatus.CREATED);
            bankAccountRepository.save(currentAccount2);

            // create saving accounts for customer withe name "yassine"
            SavingAccount savingAccount2 = new SavingAccount();
            savingAccount2.setId(UUID.randomUUID().toString());
            savingAccount2.setCreatedAt(new Date());
            savingAccount2.setCustomer(custumerRepository.findByName("yassine"));
            savingAccount2.setBalance(Math.random()*10000);
            savingAccount2.setInterestRate(5.5);
            savingAccount2.setStatus(AccountStatus.CREATED);
            bankAccountRepository.save(savingAccount2);

            // Add operations for all accounts
            bankAccountRepository.findAll().forEach(b->{
                for (int i=0;i<10;i++){
                    AccountOperation accountOperation= new AccountOperation();
                    accountOperation.setAmount(Math.random()*12000);
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setType(Math.random()>0.5? OperationType.DEBIT: OperationType.CREDIT);
                    accountOperation.setBankAccount(b);
                    accountOperationRepository.save(accountOperation);
                }
                // Display account details
                BankAccount bankAccount = bankAccountRepository.findById(b.getId()).orElse(null);
                if (bankAccount != null) {
                    System.out.println("========== Account Details ==========");
                    System.out.println("Account ID: " + bankAccount.getId());
                    System.out.println("Account Balance: " + bankAccount.getBalance());
                    System.out.println("Account Created At: " + bankAccount.getCreatedAt());
                    System.out.println("Account Status: " + bankAccount.getStatus());
                    System.out.println("Customer Name: " + bankAccount.getCustomer().getName());
                    System.out.println("Customer Email: " + bankAccount.getCustomer().getEmail());

                    if (bankAccount instanceof SavingAccount) {
                        System.out.println("Account Type: Saving Account");
                        System.out.println("Interest Rate: " + ((SavingAccount) bankAccount).getInterestRate());
                    } else if (bankAccount instanceof CurrentAccount) {
                        System.out.println("Account Type: Current Account");
                        System.out.println("OverDraft: " + ((CurrentAccount) bankAccount).getOverDraft());
                    }

                    System.out.println("----- Example Account Operation -----");
                    if (!bankAccount.getAccountOperations().isEmpty()) {
                        AccountOperation a = bankAccount.getAccountOperations().iterator().next();
                        System.out.println("Operation ID: " + a.getId());
                        System.out.println("Operation Amount: " + a.getAmount());
                        System.out.println("Operation Type: " + a.getType());
                        System.out.println("Operation Date: " + a.getOperationDate());
                    } else {
                        System.out.println("No operations found for this account.");
                    }
                    System.out.println("=====================================");
                }
            });
        };
    }
}
