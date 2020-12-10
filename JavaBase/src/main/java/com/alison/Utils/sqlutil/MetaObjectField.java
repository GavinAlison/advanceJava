
package com.alison.Utils.sqlutil;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 描述 字段信息
 * </p>
 *
 * @author lw
 * @since 2020-11-05
 */

@Getter
@Setter
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "字段信息实体")
public class MetaObjectField extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字段ID
     */
    @TableId(type = IdType.ID_WORKER_STR)
    private String objectFieldId;

    /**
     * 租户号
     */
    private String contentCode;

    /**
     * 创建人
     */
    private String createPerson;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private String updatePerson;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 字段标识
     */
    private String fieldCode;

    private String fieldFormat;

    /**
     * 字段长度
     */
    private Integer fieldLength;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 字段类型
     */
    private String fieldType;

    /**
     * 是否是主键
     */
    private Boolean isKey;

    /**
     * 字段类型
     */
    private String objectType;

    private Integer scaleLength;


}
