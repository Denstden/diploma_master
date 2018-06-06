package ua.kiev.unicyb.diploma.domain.entity.configuration.variant;

import lombok.Setter;import lombok.Getter;

import javax.persistence.*;


@Table(name = "VARIANT_FOOTER_CONFIG")
@Entity
@Getter@Setter
public class VariantFooterConfigEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="variant_footer_config_gen")
    @SequenceGenerator(name="variant_footer_config_gen", sequenceName="VARIANT_FOOTER_CONFIG_SEQ", allocationSize=1)
    private Long variantFooterConfigId;

    @Column
    private String value;
}
