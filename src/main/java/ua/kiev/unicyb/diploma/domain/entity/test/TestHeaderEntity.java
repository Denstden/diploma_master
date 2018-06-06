package ua.kiev.unicyb.diploma.domain.entity.test;

import lombok.Setter;import lombok.Getter;

import javax.persistence.*;

@Table(name = "TEST_HEADER")
@Entity
@Getter@Setter
public class TestHeaderEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="test_header_gen")
    @SequenceGenerator(name="test_header_gen", sequenceName="TEST_HEADER_SEQ", allocationSize=1)
    private Long testHeaderId;

    @Column
    private String value;
}
