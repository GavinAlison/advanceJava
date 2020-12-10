
package com.alison.Utils.sqlutil;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author LW
 * @since 2020/11/16  10:55
 */
@Getter
@Setter
public class BaseModel {

    @ApiModelProperty(value = "创建人")
    private String createPerson;
    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "更新人")
    private String updatePerson;
    @ApiModelProperty(value = "更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    /**
     * 租户号
     */
    @ApiModelProperty(value = "租户号")
    private String contentCode;

}
