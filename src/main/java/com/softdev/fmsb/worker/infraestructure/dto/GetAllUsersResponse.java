package com.softdev.fmsb.worker.infraestructure.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.softdev.fmsb.auth.model.User;
import com.softdev.fmsb.worker.infraestructure.UserEssentialsProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GetAllUsersResponse {
    private List<UserEssentials> users;
}
