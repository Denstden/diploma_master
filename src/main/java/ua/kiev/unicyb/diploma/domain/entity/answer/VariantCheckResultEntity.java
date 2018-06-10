package ua.kiev.unicyb.diploma.domain.entity.answer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table
@Entity(name = "VARIANT_CHECK_RESULT")
public class VariantCheckResultEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="variant_check_result_gen")
    @SequenceGenerator(name="variant_check_result_gen", sequenceName="VARIANT_CHECK_RESULT_SEQ", allocationSize=1)
    private Long variantCheckResultId;

    @Column
    private Double points;
    @Column
    private Double total;
    @Column
    private Boolean isComplete = true;
}
