package ma.enset.bdcc.azmi.backend.repositories;

import ma.enset.bdcc.azmi.backend.entities.Customer;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustumerRepository extends JpaRepository<Customer, Long> {
    Customer findByName(String name);
    
    @Query("SELECT c FROM Customer c WHERE c.name LIKE %:keyword%")
    List<Customer> searchCustomers(@Param("keyword") String keyword);
}
