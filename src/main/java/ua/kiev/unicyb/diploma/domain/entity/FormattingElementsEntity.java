package ua.kiev.unicyb.diploma.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Setter;import lombok.Getter;
import ua.kiev.unicyb.diploma.domain.entity.configuration.format.FormattingStrategy;
import ua.kiev.unicyb.diploma.domain.entity.question.QuestionEntity;

import javax.persistence.*;

@Getter@Setter
@Entity
@Table(name = "FORMATTING_ELEMENTS")
public class FormattingElementsEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="formatting_elements_gen")
    @SequenceGenerator(name="formatting_elements_gen", sequenceName="FORMATTING_ELEMENTS_SEQ", allocationSize=1)
    private Long formattingElementsId;

    @Column
    @Enumerated(EnumType.STRING)
    private FormattingStrategy formattingStrategy;

    @Column
    private Integer count;

    @OneToOne(mappedBy = "formattingElements", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    private QuestionEntity questionEntity;
}
