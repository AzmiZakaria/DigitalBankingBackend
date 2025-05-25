package ma.enset.bdcc.azmi.backend.web;

import lombok.AllArgsConstructor;
import ma.enset.bdcc.azmi.backend.entities.Customer;
import ma.enset.bdcc.azmi.backend.services.BankAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class CustomerRestController {
    private BankAccountService bankAccountService;

    @GetMapping("/customers")
    public List<Customer> customers() {
        return bankAccountService.listCustomers();
    }
}
