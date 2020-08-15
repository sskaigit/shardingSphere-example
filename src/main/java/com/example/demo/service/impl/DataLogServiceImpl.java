package com.example.demo.service.impl;

import com.example.demo.domain.DataLog;
import com.example.demo.domain.request.DataLogRequest;
import com.example.demo.repository.DataLogRepository;
import com.example.demo.service.DataLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class DataLogServiceImpl implements DataLogService {
	
	@Autowired
	private DataLogRepository dataLogRepository;

	@Override
	public Page<DataLog> findPageByCondition(DataLogRequest request, Pageable pageable) {
		// 默认从今天0点到此时
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		Date bgDate = request.getBgCreateDate() != null ? request.getBgCreateDate() : cal.getTime();
		Date edDate = request.getEdCreateDate() != null ? request.getEdCreateDate() : new Date();
		return dataLogRepository.findPageByCondition(bgDate, edDate, request.getOperation(), request.getEntityId(), request.getEntityName(), request.getCreatorId(), request.getCreatorName(), pageable);
	}
}
