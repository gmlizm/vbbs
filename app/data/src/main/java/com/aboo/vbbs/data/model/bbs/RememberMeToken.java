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
@TableName("bbs_remember_me_token")
public class RememberMeToken implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("date")
    private Date date;

    @TableField("series")
    private String series;

    @TableField("token_value")
    private String tokenValue;

    @TableField("username")
    private String username;


}
