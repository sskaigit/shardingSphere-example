package com.example.demo.domain.base;

import lombok.Data;

import java.util.Date;

@Data
public class BaseResponse {

	private Long id;
	private String createUser;
	private Date createDate;
	private Date updateDate;
	private Integer sequence;
	private String description;
	private Boolean enable;
}
