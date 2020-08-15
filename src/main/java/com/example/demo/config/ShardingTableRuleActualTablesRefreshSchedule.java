package com.example.demo.config;

import com.alibaba.druid.util.StringUtils;
import com.example.demo.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.core.rule.DataNode;
import org.apache.shardingsphere.core.rule.ShardingRule;
import org.apache.shardingsphere.core.rule.TableRule;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 动态分表，并自动建表
 */
@Slf4j
@Component
@EnableScheduling
public class ShardingTableRuleActualTablesRefreshSchedule implements InitializingBean {

    @Value("${dynamic.table.names}")
    private String[] dynamicTables;

    @Value("${dynamic.table.startDate}")
    private String startDate;

    @Autowired
    @Qualifier("shardingDataSource")
    private DataSource shardingDataSource;

    public ShardingTableRuleActualTablesRefreshSchedule() {
    }

    /**
     * 水平分表，动态分表刷新定时任务
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void actualTablesRefresh() throws NoSuchFieldException, IllegalAccessException, SQLException {
        log.info("---------------动态分表刷新定时任务------------------");
        ShardingDataSource dataSource = (ShardingDataSource) shardingDataSource;
        if (dynamicTables == null || dynamicTables.length == 0) {
            return;
        }
        if (StringUtils.isEmpty(startDate) || !startDate.contains("-")) {
            return;
        }
        for (String tablesName : dynamicTables) {
            // 自动创建表
            this.createTable(tablesName);
            TableRule tableRule = null;
            try {
                ShardingRule shardingRule = dataSource.getRuntimeContext().getRule();
                tableRule = shardingRule.getTableRule(tablesName);
                log.info("逻辑表：{},动态分表配置中。。。。", tablesName);
            } catch (Exception e) {
                log.error("逻辑表：{},不存在配置！", tablesName);
                continue;
            }
            List<DataNode> dataNodes = tableRule.getActualDataNodes();

            // 对应yml配置中的actual-data-nodes
            Field actualDataNodesField = TableRule.class.getDeclaredField("actualDataNodes");
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(actualDataNodesField, actualDataNodesField.getModifiers() & ~Modifier.FINAL);
            actualDataNodesField.setAccessible(true);

            LocalDateTime localDateTime = LocalDateTime.of(Integer.parseInt(startDate.split("-")[0]),
                    Integer.parseInt(startDate.split("-")[1]), 1, 0, 0, new Random().nextInt(59));
            LocalDateTime now = LocalDateTime.now();

            String dataSourceName = dataNodes.get(0).getDataSourceName();
            String logicTableName = tableRule.getLogicTable();
            StringBuilder stringBuilder = new StringBuilder(10).append(dataSourceName).append(".").append(logicTableName);
            final int length = stringBuilder.length();
            List<DataNode> newDataNodes = new ArrayList<>();
            while (true) {
                stringBuilder.setLength(length);
                stringBuilder.append(localDateTime.format(DateTimeFormatter.ofPattern("yyyyMM")));
                DataNode dataNode = new DataNode(stringBuilder.toString());
                newDataNodes.add(dataNode);
                localDateTime = localDateTime.plusMonths(1L);
                if (localDateTime.isAfter(now)) {
                    break;
                }
            }
            log.info("ShardingTableRuleActualTablesRefreshSchedule.actualTablesRefresh  初始化最新分表--> dataNodeStart={}, dataNodeEnd={}",newDataNodes.get(0).getTableName(),newDataNodes.get(newDataNodes.size()-1).getTableName());
            actualDataNodesField.set(tableRule, newDataNodes);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        actualTablesRefresh();
    }

    private void createTable(String orgTableName){
        String tableName = orgTableName + DateUtil.getCurrent2Str("yyyyMM");
        try{
            String creatSql = "create table IF NOT EXISTS "+tableName +" like log_data";
            Connection connection = shardingDataSource.getConnection();
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(creatSql);
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            log.info("ShardingTableRuleActualTablesRefreshSchedule.createTable  创建表失败--> tableName={}, e={}",tableName, e.getMessage());
        }
    }
}
