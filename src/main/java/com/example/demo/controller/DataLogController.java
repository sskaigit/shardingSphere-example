package com.example.demo.controller;

import com.example.demo.domain.DataLog;
import com.example.demo.domain.request.DataLogRequest;
import com.example.demo.service.DataLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log/data")
public class DataLogController {

	@Autowired
	private DataLogService dataLogService;

	@GetMapping("/page")
	public Page<DataLog> getPage(DataLogRequest request, @RequestParam Integer pageNo, @RequestParam Integer pageSize) {
		return dataLogService.findPageByCondition(request, PageRequest.of(pageNo, pageSize));
	}
}
