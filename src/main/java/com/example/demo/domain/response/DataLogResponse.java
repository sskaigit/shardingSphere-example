package com.example.demo.domain.response;

import com.example.demo.domain.base.BaseResponse;
import lombok.Data;

@Data
public class DataLogResponse extends BaseResponse {

    private String operation;
    private String entityName;
    private String entityId;
    private String beforeValues;
    private String afterValues;
    private String mark;
    private Long creatorId;
    private String creatorName;
}
