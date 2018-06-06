package ua.kiev.unicyb.diploma.domain.entity.configuration.format;

import lombok.Setter;import lombok.Getter;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.AbstractQuestionDescriptionEntity;

import javax.persistence.*;

@Getter@Setter
@Entity
@Table(name = "FORMATTING_ELEMENTS_CONFIG")
public class FormattingElementsConfigEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="formatting_elements_config_gen")
    @SequenceGenerator(name="formatting_elements_config_gen", sequenceName="FORMATTING_ELEMENTS_CONFIG_SEQ", allocationSize=1)
    private Long formattingElementsConfigId;

    @Column
    @Enumerated(EnumType.STRING)
    private FormattingStrategy formattingStrategy = FormattingStrategy.COLUMNS;

    @Column
    private Integer count;

    @OneToOne(mappedBy = "formattingElements", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private AbstractQuestionDescriptionEntity questionDescriptionEntity;
}
