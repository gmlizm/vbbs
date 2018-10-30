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
@TableName("bbs_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("attempts")
    private Integer attempts;

    @TableField("attempts_time")
    private Date attemptsTime;

    @TableField("avatar")
    private String avatar;

    @TableField("bio")
    private String bio;

    @TableField("block")
    private Boolean block;

    @TableField("email")
    private String email;

    @TableField("in_time")
    private Date inTime;

    @TableField("password")
    private String password;

    @TableField("score")
    private Integer score;

    @TableField("space_size")
    private Long spaceSize;

    @TableField("token")
    private String token;

    @TableField("url")
    private String url;

    @TableField("username")
    private String username;

    @TableField("github_user_id")
    private Integer githubUserId;


}
