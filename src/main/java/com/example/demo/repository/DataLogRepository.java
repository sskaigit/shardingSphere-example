package com.example.demo.repository;

import com.example.demo.domain.DataLog;
import com.example.demo.domain.base.BaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface DataLogRepository  extends BaseJpaRepository<DataLog> {

    @Query(value = "SELECT * FROM log_data WHERE create_date BETWEEN :bgCreateDate AND :edCreateDate " +
            "AND IF(:operation IS NOT NULL AND :operation != '', operation = :operation, TRUE) " +
            "AND IF(:entityId IS NOT NULL AND :entityId != '', entity_id = :entityId, TRUE) " +
            "AND IF(:entityName IS NOT NULL AND :entityName != '', entity_name LIKE CONCAT('%',:entityName,'%'), TRUE) " +
            "AND IF(:creatorId IS NOT NULL, creator_id = :creatorId , TRUE) " +
            "AND IF(:creatorName IS NOT NULL AND :creatorName != '', creator_name LIKE CONCAT('%',:creatorName,'%'), TRUE) " +
            "ORDER BY create_date DESC ",
            countQuery = "SELECT count(*) FROM log_data WHERE create_date BETWEEN :bgCreateDate AND :edCreateDate " +
                    "AND IF(:operation IS NOT NULL AND :operation != '', operation = :operation, TRUE) " +
                    "AND IF(:entityId IS NOT NULL AND :entityId != '', entity_id = :entityId, TRUE) " +
                    "AND IF(:entityName IS NOT NULL AND :entityName != '', entity_name LIKE CONCAT('%',:entityName,'%'), TRUE) " +
                    "AND IF(:creatorId IS NOT NULL AND :creatorId != '', creator_id = :creatorId , TRUE) " +
                    "AND IF(:creatorName IS NOT NULL AND :creatorName != '', creator_name LIKE CONCAT('%',:creatorName,'%'), TRUE) ", nativeQuery = true)
    Page<DataLog> findPageByCondition(@Param("bgCreateDate") Date bgCreateDate, @Param("edCreateDate") Date edCreateDate,
                                      @Param("operation") String operation, @Param("entityId") String entityId,
                                      @Param("entityName") String entityName, @Param("creatorId") Long creatorId,
                                      @Param("creatorName") String creatorName, Pageable pageable);
}
