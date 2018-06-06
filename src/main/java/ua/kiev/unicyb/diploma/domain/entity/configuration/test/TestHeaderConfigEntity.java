package ua.kiev.unicyb.diploma.domain.entity.configuration.test;

import lombok.Setter;import lombok.Getter;

import javax.persistence.*;


@Table(name = "TEST_HEADER_CONFIG")
@Entity
@Getter@Setter
public class TestHeaderConfigEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="test_header_config_gen")
    @SequenceGenerator(name="test_header_config_gen", sequenceName="TEST_HEADER_CONFIG_SEQ", allocationSize=1)
    private Long testHeaderConfigId;

    @Column
    private String value;
}
