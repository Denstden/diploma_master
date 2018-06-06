package ua.kiev.unicyb.diploma.domain.entity.variant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Setter;import lombok.Getter;
import ua.kiev.unicyb.diploma.domain.entity.question.QuestionEntity;
import ua.kiev.unicyb.diploma.domain.entity.test.TestEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter@Setter
@Table(name = "VARIANT")
@Entity
public class VariantEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="variant_gen")
    @SequenceGenerator(name="variant_gen", sequenceName="VARIANT_SEQ", allocationSize=1)
    private Long variantId;

    @Column
    private String name;

    @Column
    private Double mark;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "VARIANT_ID")
    private List<VariantHeaderEntity> variantHeaders = new ArrayList<>();

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "VARIANT_ID")
    private List<VariantFooterEntity> variantFooters = new ArrayList<>();

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "VARIANT_ID")
    private List<QuestionEntity> questions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "TEST_ID")
    @JsonIgnore
    private TestEntity test;
}
