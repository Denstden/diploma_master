package ua.kiev.unicyb.diploma.domain.entity.test;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Setter;import lombok.Getter;
import ua.kiev.unicyb.diploma.domain.entity.configuration.test.TestConfigEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.test.TestType;
import ua.kiev.unicyb.diploma.domain.entity.user.UserEntity;
import ua.kiev.unicyb.diploma.domain.entity.variant.VariantEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "TEST")
@Entity
@Getter@Setter
public class TestEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="test_gen")
    @SequenceGenerator(name="test_gen", sequenceName="TEST_SEQ", allocationSize=1)
    private Long testId;

    @Column
    private String testName;

    @Column
    @Enumerated(EnumType.STRING)
    private TestType testType;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "TEST_ID")
    private List<TestHeaderEntity> headers = new ArrayList<>();

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "test", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<VariantEntity> variants = new ArrayList<>();

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "TEST_CONFIG_ID")
    @JsonIgnore
    private TestConfigEntity testConfig;

    @ManyToMany
    @JoinTable(name = "TEST_USER",
            joinColumns = { @JoinColumn(name = "TEST_ID") },
            inverseJoinColumns = { @JoinColumn(name = "USER_ID") })
    @JsonIgnore
    private Set<UserEntity> users = new HashSet<>();

}
