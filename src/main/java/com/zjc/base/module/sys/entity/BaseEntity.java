package com.zjc.base.module.sys.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 2025098674980576843L;
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 版本号
     */
    @Version
    private Long revesion;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private Long addby;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date adddate;

    /**
     * 创建来源应用
     */
    @TableField(fill = FieldFill.INSERT)
    private String addapp;

    /**
     * 创建来源设备名
     */
    @TableField(fill = FieldFill.INSERT)
    private String addtrml;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.UPDATE)
    private Long updby;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date upddate;

    /**
     * 更新来源应用
     */
    @TableField(fill = FieldFill.UPDATE)
    private String updapp;

    /**
     * 更新来源设备名
     */
    @TableField(fill = FieldFill.UPDATE)
    private String updtrml;

    /**
     * 删除标记：0，未删除（缺省值），1、逻辑删除
     */
    @TableLogic
    private Integer delflg;

    /**
     * 标准预留字段01
     */
    private String standardCol01;

    /**
     * 标准预留字段02
     */
    private String standardCol02;

    /**
     * 标准预留字段03
     */
    private String standardCol03;

    /**
     * 标准预留字段04
     */
    private String standardCol04;

    /**
     * 标准预留字段05
     */
    private String standardCol05;

    /**
     * 标准预留字段06
     */
    private String standardCol06;

    /**
     * 标准预留字段07
     */
    private String standardCol07;

    /**
     * 标准预留字段08
     */
    private String standardCol08;

    /**
     * 标准预留字段09
     */
    private String standardCol09;

    /**
     * 标准预留字段10
     */
    private String standardCol10;

    /**
     * 标准预留字段11
     */
    private BigDecimal standardCol11;

    /**
     * 标准预留字段12
     */
    private BigDecimal standardCol12;

    /**
     * 标准预留字段13
     */
    private BigDecimal standardCol13;

    /**
     * 标准预留字段14
     */
    private Date standardCol14;

    /**
     * 标准预留字段15
     */
    private Date standardCol15;

    /**
     * @Description: 清空add，upd  Info
     * @Param: []
     * @return: void
     * @Date: 2020/11/18
     */
    public void clear(){
        this.setAddapp(null);
        this.setAddby(null);
        this.setAdddate(null);
        this.setAddtrml(null);

        this.setUpdapp(null);
        this.setUpdby(null);
        this.setUpddate(null);
        this.setUpdtrml(null);
    }
}
