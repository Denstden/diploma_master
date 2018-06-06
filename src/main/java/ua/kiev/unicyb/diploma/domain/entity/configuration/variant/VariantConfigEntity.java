package ua.kiev.unicyb.diploma.domain.entity.configuration.variant;

import lombok.Setter;import lombok.Getter;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.description.QuestionConfigEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.test.TestConfigEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter@Setter
@Entity
@Table(name = "VARIANT_CONFIG")
public class VariantConfigEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="variant_config_gen")
    @SequenceGenerator(name="variant_config_gen", sequenceName="VARIANT_CONFIG_SEQ", allocationSize=1)
    private Long variantConfigId;

    @Column
    private Double points;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "VARIANT_CONFIG_ID")
    private List<VariantHeaderConfigEntity> variantHeader = new ArrayList<>();

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "VARIANT_CONFIG_ID")
    private List<VariantFooterConfigEntity> variantFooter = new ArrayList<>();

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "VARIANT_CONFIG_ID")
    private List<QuestionConfigEntity> questionConfigs = new ArrayList<>();

    @OneToOne(mappedBy = "variantConfig", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private TestConfigEntity testConfig;
}
