package ua.kiev.unicyb.diploma.converter;

import com.creativewidgetworks.expressionparser.Parser;
import com.creativewidgetworks.expressionparser.ParserException;
import com.creativewidgetworks.expressionparser.Value;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ua.kiev.unicyb.diploma.domain.entity.answer.QuestionAnswerEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.AnswerDescriptionEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.parameterized.ParameterizedEntity;
import ua.kiev.unicyb.diploma.domain.entity.question.ParameterizedValue;
import ua.kiev.unicyb.diploma.service.ParameterSubstitutionService;
import ua.kiev.unicyb.diploma.service.ParameterizedService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class QuestionAnswerConverter {
    ParameterSubstitutionService parameterSubstitutionService;

    public QuestionAnswerEntity toEntity(AnswerDescriptionEntity dto, List<ParameterizedValue> parameterizedValues) {
        final QuestionAnswerEntity entity = new QuestionAnswerEntity();

        String answer = dto.getAnswer();
        if (parameterizedValues != null && !parameterizedValues.isEmpty()) {
            final Map<String, String> parameters = convertToMap(parameterizedValues);
            final String substituted = parameterSubstitutionService.substitute(answer, parameters);
            try {
                final Value result = new Parser().eval(substituted);
                if (result != null && !result.asString().startsWith("com.creativewidgetworks.expressionparser")) {
                    answer = result.asString();
                }
            } catch (Exception e) {
            }

        }
        entity.setAnswer(answer);
        entity.setIsCorrect(dto.getIsCorrect());

        return entity;
    }

    private Map<String, String> convertToMap(List<ParameterizedValue> parameterizedValues) {
        final Map<String, String> map = new HashMap<>();
        parameterizedValues.forEach(parameterizedValue -> {
            map.put(parameterizedValue.getName(), parameterizedValue.getValue());
        });
        return map;
    }
}
