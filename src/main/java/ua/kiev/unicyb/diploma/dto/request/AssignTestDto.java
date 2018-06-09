package ua.kiev.unicyb.diploma.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class AssignTestDto {
    private Long testId;
    private List<Long> userIds;
}
