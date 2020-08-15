package com.example.demo.domain;

import com.example.demo.domain.base.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name="log_data")
public class DataLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 操作类型
     */
    @NotNull
    private String operation;

    /**
     * 实体名
     */
    @NotNull
    private String entityName;

    /**
     * 实体主键
     */
    @NotNull
    private String entityId;

    /**
     * 变更之前的值
     */
    @Lob
    private String beforeValues;

    /**
     * 变更之后的值
     */
    @Lob
    private String afterValues;

    /**
     * 数据变更标记
     */
    @Lob
    private String mark;

    /**
     * 创建人ID
     */
    private Long creatorId;

    /**
     * 创建人名称
     */
    private String creatorName;
}
