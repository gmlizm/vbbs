package com.aboo.vbbs.data.model.bbs;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author yylizm
 * @since 2018-06-06
 */
@Data
@Accessors(chain = true)
@TableName("bbs_code")
public class Code implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("code")
    private String code;

    @TableField("email")
    private String email;

    @TableField("expire_time")
    private Date expireTime;

    @TableField("is_used")
    private Boolean used;

    @TableField("mobile")
    private String mobile;

    @TableField("type")
    private String type;


}
