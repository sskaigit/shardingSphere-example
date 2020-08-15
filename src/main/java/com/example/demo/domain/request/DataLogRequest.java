package com.example.demo.domain.request;

import com.example.demo.domain.base.BaseRequest;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class DataLogRequest extends BaseRequest {

    private String operation;
    private String entityName;
    private String entityId;
    private String beforeValues;
    private String afterValues;
    private String mark;
    private Long creatorId;
    private String creatorName;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date bgCreateDate;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date edCreateDate;
}
