package ua.kiev.unicyb.diploma.domain.entity.variant;

import lombok.Setter;import lombok.Getter;

import javax.persistence.*;

@Table(name = "VARIANT_HEADER")
@Entity
@Getter@Setter
public class VariantHeaderEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="variant_header_gen")
    @SequenceGenerator(name="variant_header_gen", sequenceName="VARIANT_HEADER_SEQ", allocationSize=1)
    private Long variantHeaderId;

    @Column
    private String value;
}
