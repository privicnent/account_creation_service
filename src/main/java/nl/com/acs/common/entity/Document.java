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

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "Document")
@Data
@NoArgsConstructor
public class Document {

    public Document(RegisterRequest registerRequest) {
        this.documentId = UUID.randomUUID().toString();
        this.documentNumber = registerRequest.getDocument().getDocumentNumber();
        this.documentIssueDate = registerRequest.getDocument().getDocumentIssueDate();
        this.documentIssueCountry = registerRequest.getDocument().getDocumentIssueCountry();
        this.documentTypeCode = registerRequest.getDocument().getDocumentTypeCode().getValue();
    }

    @Id
    @Column(name = "documentid", nullable = false, length = 30)
    private String documentId;
    @Column(name = "documentnumber",nullable = false, length = 30)
    private String documentNumber;

    @Column(name = "documentissuedate",nullable = false)
    private LocalDate documentIssueDate;

    @Column(name = "documentissuecountry",nullable = false, length = 30)
    private String documentIssueCountry;

    @Column(name = "documenttypecode",nullable = false, length = 30)
    private String documentTypeCode;

    @OneToOne(mappedBy = "document", cascade = CascadeType.ALL)
    private Customer customer;
}
