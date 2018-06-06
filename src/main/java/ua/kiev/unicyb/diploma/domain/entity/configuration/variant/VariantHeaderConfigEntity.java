package ua.kiev.unicyb.diploma.domain.entity.configuration.variant;

import lombok.Setter;import lombok.Getter;

import javax.persistence.*;


@Table(name = "VARIANT_HEADER_CONFIG")
@Entity
@Getter@Setter
public class VariantHeaderConfigEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="variant_header_config_gen")
    @SequenceGenerator(name="variant_header_config_gen", sequenceName="VARIANT_HEADER_CONFIG_SEQ", allocationSize=1)
    private Long variantHeaderConfigId;

    @Column
    private String value;
}
