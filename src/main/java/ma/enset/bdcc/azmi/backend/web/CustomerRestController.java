package ma.enset.bdcc.azmi.backend.web;

import lombok.AllArgsConstructor;
import ma.enset.bdcc.azmi.backend.dtos.CustomerDTO;
import ma.enset.bdcc.azmi.backend.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class CustomerRestController {
    private BankAccountService bankAccountService;

    @GetMapping("/customers")
    public List<CustomerDTO> customers() {
        return bankAccountService.listCustomers();
    }

    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) {
        return bankAccountService.getCustomer(customerId);
    }
    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return bankAccountService.saveCustomer(customerDTO);
    }
    @PutMapping("/customers/{customerId}")
    public CustomerDTO updateCustomer(@PathVariable Long customerId, @RequestBody CustomerDTO customerDTO){
        customerDTO.setId(customerId);
        return bankAccountService.updateCustomer(customerDTO);
    }
    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id){
        bankAccountService.deleteCustomer(id);
    }
}
