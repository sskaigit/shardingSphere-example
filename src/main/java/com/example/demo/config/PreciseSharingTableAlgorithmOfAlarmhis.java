package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@Slf4j
public class PreciseSharingTableAlgorithmOfAlarmhis implements PreciseShardingAlgorithm<Date> {
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMM");
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> shardingValue) {
        StringBuffer tableName = new StringBuffer();
        log.info("执行操作的表名{}",shardingValue.getLogicTableName() + dateformat.format(shardingValue.getValue()));
        tableName.append(shardingValue.getLogicTableName()).append(dateformat.format(shardingValue.getValue()));
        return tableName.toString();
    }
}
