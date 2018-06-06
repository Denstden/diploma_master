package ua.kiev.unicyb.diploma.domain.entity.variant;

import lombok.Setter;import lombok.Getter;

import javax.persistence.*;

@Table(name = "VARIANT_FOOTER")
@Entity
@Getter@Setter
public class VariantFooterEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="variant_footer_gen")
    @SequenceGenerator(name="variant_footer_gen", sequenceName="VARIANT_FOOTER_SEQ", allocationSize=1)
    private Long variantFooterId;

    @Column
    private String value;
}
