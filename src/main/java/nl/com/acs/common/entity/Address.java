package nl.com.acs.common.entity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.com.acs.model.RegisterRequest;

import java.util.UUID;

@Entity
@Table(name = "Address")
@Data
@NoArgsConstructor
public class Address {
    public Address(RegisterRequest registerRequest) {
        this.addressId = UUID.randomUUID().toString();
        this.street = registerRequest.getAddress().getStreet();
        this.houseNumber = registerRequest.getAddress().getHouseNumber();
        this.postalCode = registerRequest.getAddress().getPostalCode();
        this.city = registerRequest.getAddress().getCity();
        this.country = registerRequest.getAddress().getCountry();
    }

    @Id
    @Column(name = "addressid", nullable = false, length = 30)
    private String addressId;
    @Column(name = "street", nullable = false, length = 50)
    private String street;
    @Column(name = "housenumber", nullable = false, length = 30)
    private int houseNumber;
    @Column(name = "postalcode", nullable = false, length = 10)
    private String postalCode;
    @Column(name = "city", nullable = false, length = 30)
    private String city;
    @Column(name = "country", nullable = false, length = 30)
    private String country;

    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
    private Customer customer;
}
