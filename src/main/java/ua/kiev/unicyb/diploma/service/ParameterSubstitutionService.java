package ua.kiev.unicyb.diploma.service;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ParameterSubstitutionService {
    private static final String PARAMETERS_STARTS_WITH = "##";
    private static final String PARAMETERS_ENDS_WITH = PARAMETERS_STARTS_WITH;


    public String substitute(final String value, final Map<String, String> parameterValuesToSubstitute) {
        if (value == null) {
            return null;
        }

        if (value.length() < (PARAMETERS_STARTS_WITH.length() + PARAMETERS_ENDS_WITH.length() + 1)) {
            return value;
        }

        final StrSubstitutor substitutor = new StrSubstitutor(parameterValuesToSubstitute, PARAMETERS_STARTS_WITH, PARAMETERS_ENDS_WITH);

        return substitutor.replace(value);
    }
}
