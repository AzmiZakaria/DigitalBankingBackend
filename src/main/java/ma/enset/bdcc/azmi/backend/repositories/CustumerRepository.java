package ma.enset.bdcc.azmi.backend.repositories;

import ma.enset.bdcc.azmi.backend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustumerRepository extends JpaRepository<Customer, Long> {

    Customer findByName(String zakaria);
}
