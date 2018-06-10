package ua.kiev.unicyb.diploma.service;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ua.kiev.unicyb.diploma.domain.entity.answer.QuestionAnswerEntity;
import ua.kiev.unicyb.diploma.domain.entity.answer.UserQuestionAnswers;
import ua.kiev.unicyb.diploma.domain.entity.answer.UserVariantAnswersEntity;
import ua.kiev.unicyb.diploma.domain.entity.question.QuestionEntity;
import ua.kiev.unicyb.diploma.domain.entity.question.QuestionType;
import ua.kiev.unicyb.diploma.domain.entity.user.UserEntity;
import ua.kiev.unicyb.diploma.dto.request.QuestionAnswersDto;
import ua.kiev.unicyb.diploma.dto.request.VariantAnswersDto;
import ua.kiev.unicyb.diploma.repositories.QuestionAnswerRepository;
import ua.kiev.unicyb.diploma.repositories.QuestionRepository;
import ua.kiev.unicyb.diploma.repositories.UserVariantAnswersRepository;
import ua.kiev.unicyb.diploma.security.AuthenticationService;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AnswersService {

    UserService userService;
    QuestionRepository questionRepository;
    UserVariantAnswersRepository variantAnswersRepository;
    QuestionAnswerRepository questionAnswerRepository;
    AuthenticationService authenticationService;

    public UserVariantAnswersEntity saveAnswers(final VariantAnswersDto variantAnswersDto) {
        final UserVariantAnswersEntity userVariantAnswersEntity = new UserVariantAnswersEntity();

        userVariantAnswersEntity.setVariantId(variantAnswersDto.getVariantId());
        setUser(userVariantAnswersEntity);

        setUserQuestionAnswers(variantAnswersDto, userVariantAnswersEntity);

        return variantAnswersRepository.save(userVariantAnswersEntity);
    }

    private void setUserQuestionAnswers(final VariantAnswersDto variantAnswersDto,
                                        final UserVariantAnswersEntity userVariantAnswersEntity) {


        final List<UserQuestionAnswers> userQuestionAnswers = new ArrayList<>();

        variantAnswersDto.getQuestionAnswers().forEach(questionAnswersDto -> {
            final UserQuestionAnswers userQuestionAnswer = new UserQuestionAnswers();

            final QuestionEntity questionEntity = questionRepository.findOne(questionAnswersDto.getQuestionId());
            userQuestionAnswer.setQuestion(questionEntity);

            convertQuestionAnswer(questionAnswersDto, userQuestionAnswer, questionEntity);

            userQuestionAnswers.add(userQuestionAnswer);
        });

        userVariantAnswersEntity.setUserQuestionAnswers(userQuestionAnswers);

    }

    private void convertQuestionAnswer(QuestionAnswersDto questionAnswersDto, UserQuestionAnswers userQuestionAnswer, QuestionEntity questionEntity) {
        final List<QuestionAnswerEntity> questionAnswerEntities = new ArrayList<>();

        questionAnswersDto.getAnswerIds().forEach(answer -> {
            QuestionAnswerEntity questionAnswerEntity;
            if (QuestionType.ESSAY.equals(questionEntity.getQuestionType())) {
                questionAnswerEntity = new QuestionAnswerEntity();
                questionAnswerEntity.setAnswer(answer);
            } else {
                questionAnswerEntity = questionAnswerRepository.findOne(Long.valueOf(answer));
            }

            questionAnswerEntities.add(questionAnswerEntity);
        });

        userQuestionAnswer.setQuestionAnswers(questionAnswerEntities);
    }

    private void setUser(UserVariantAnswersEntity userVariantAnswersEntity) {
        final String currentUsername = authenticationService.getCurrentUsername();
        final UserEntity user = userService.findByUsername(currentUsername);
        userVariantAnswersEntity.setUser(user);
    }
}
