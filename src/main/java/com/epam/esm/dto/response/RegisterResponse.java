package com.epam.esm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public class RegisterResponse {
        private String name;
        private String email;
        private String token;
    }

