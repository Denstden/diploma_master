package ua.kiev.unicyb.diploma.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TrainingTestService {

    private Map<Long, Integer> testToLastGivenVariantMap = new HashMap<>();
    private Map<Long, Integer> testToCountVariantsMap = new HashMap<>();

    public void addTest(Long testId, Integer countVariants) {
        testToLastGivenVariantMap.put(testId, -1);
        testToCountVariantsMap.put(testId, countVariants);
    }

    public Integer getVariantNumber(Long testId) {
        final Integer countInDb = testToCountVariantsMap.get(testId);
        final Integer countGiven = testToLastGivenVariantMap.get(testId);

        Integer currentIndex;

        if ((countGiven + 1) < countInDb) {
            currentIndex = countGiven + 1;
        } else {
            currentIndex = 0;
        }

        testToLastGivenVariantMap.put(testId, currentIndex);
        return currentIndex;
    }

}
