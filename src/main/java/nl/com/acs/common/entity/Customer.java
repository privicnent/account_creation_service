package nl.com.acs.common.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.com.acs.model.RegisterRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Customer")
@Data
@NoArgsConstructor
public class Customer {

    public Customer(RegisterRequest registerRequest) {
        this.customerId = UUID.randomUUID().toString();
        this.givenName = registerRequest.getGivenName();
        this.nameInitial = registerRequest.getNameInitial();
        this.surname = registerRequest.getSurname();
        this.birthDate = registerRequest.getBirthDate();
        this.userName = registerRequest.getUserName();
    }

    @Id
    @Column(name = "customerid", nullable = false, length = 30)
    private String customerId;

    @Column(name = "givenname", nullable = false, length = 50)
    private String givenName;

    @Column(name = "nameinitial", nullable = false, length = 1)
    private String nameInitial;

    @Column(name = "surname", nullable = false, length = 50)
    private String surname;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthDate;

    @Column(name = "username", nullable = false, length = 50, unique = true)
    private String userName;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @OneToOne
    @JoinColumn(name = "documentid", referencedColumnName = "documentid")
    private Document document;

    @OneToOne
    @JoinColumn(name = "addressid", referencedColumnName = "addressid")
    private Address address;

}
