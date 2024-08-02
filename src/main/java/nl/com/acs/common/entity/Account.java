package nl.com.acs.common.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Account")
@Data
@NoArgsConstructor
public class Account {
    @Id
    @Column(name = "accountid",nullable = false, length = 30)
    private String accountId;
    @Column(name = "iban",nullable = false, length = 30)
    private String iban;

    @ManyToOne
    @JoinColumn(name = "customerid", nullable = false)
    private Customer customer;
}
