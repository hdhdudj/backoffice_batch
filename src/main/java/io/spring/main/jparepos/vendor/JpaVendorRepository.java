package io.spring.main.jparepos.vendor;


import io.spring.main.model.vendor.entity.Cmvdmr;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaVendorRepository extends JpaRepository<Cmvdmr, String> {
}
