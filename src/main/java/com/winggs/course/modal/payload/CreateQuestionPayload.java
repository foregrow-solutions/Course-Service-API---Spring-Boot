package com.winggs.course.modal.payload;

import java.util.List;

public record CreateQuestionPayload(String question, List<CreateAnswerPayload> answers) {
}
