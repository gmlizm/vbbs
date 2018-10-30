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
@TableName("bbs_notification")
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("action")
    private String action;

    @TableField("content")
    private String content;

    @TableField("in_time")
    private Date inTime;

    @TableField("is_read")
    private Boolean read;

    @TableField("target_user_id")
    private Integer targetUserId;

    @TableField("topic_id")
    private Integer topicId;

    @TableField("user_id")
    private Integer userId;


}
