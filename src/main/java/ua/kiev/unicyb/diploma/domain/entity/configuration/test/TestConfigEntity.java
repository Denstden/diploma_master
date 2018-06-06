package ua.kiev.unicyb.diploma.domain.entity.configuration.test;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Setter;import lombok.Getter;
import ua.kiev.unicyb.diploma.domain.entity.configuration.variant.VariantConfigEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter@Setter
@Entity
@Table(name = "TEST_CONFIGURATION")
public class TestConfigEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="test_config_gen")
    @SequenceGenerator(name="test_config_gen", sequenceName="TEST_CONFIG_SEQ", allocationSize=1)
    private Long testConfigId;

    @Column
    private String testName;

    @Column
    private Integer countVariants;

    @Column
    @Enumerated(EnumType.STRING)
    private TestType testType = TestType.TRAINING;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "TEST_CONFIG_ID")
    private List<TestHeaderConfigEntity> testHeaders = new ArrayList<>();

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "VARIANT_CONFIG_ID")
    @JsonIgnore
    private VariantConfigEntity variantConfig;

}
