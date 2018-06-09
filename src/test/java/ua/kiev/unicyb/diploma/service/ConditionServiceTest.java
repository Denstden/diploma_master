package ua.kiev.unicyb.diploma.service;

import com.creativewidgetworks.expressionparser.Parser;
import com.creativewidgetworks.expressionparser.Value;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ConditionServiceTest {
    @Test
    public void test(){
        final Value eval = new Parser().eval("(2+5-6) > 2");

        Map<String, String> parameterValuesToSubstitute = new HashMap<>();

        parameterValuesToSubstitute.put("1", "##2##dasdasdad");
        parameterValuesToSubstitute.put("2", "092");

        final StrSubstitutor substitutor = new StrSubstitutor(parameterValuesToSubstitute, "##", "##");

        final String replace = substitutor.replace("Sobaka ##1## dikaya ##2##");
        System.out.println(replace);

    }

}