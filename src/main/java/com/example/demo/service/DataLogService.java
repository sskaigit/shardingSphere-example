package com.example.demo.service;

import com.example.demo.domain.DataLog;
import com.example.demo.domain.request.DataLogRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DataLogService{

    Page<DataLog> findPageByCondition(DataLogRequest request, Pageable pageable);
}
