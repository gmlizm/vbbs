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
@TableName("bbs_comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("content")
    private String content;

    @TableField("down")
    private Integer down;

    @TableField("down_ids")
    private String downIds;

    @TableField("in_time")
    private Date inTime;

    @TableField("up")
    private Integer up;

    @TableField("up_down")
    private Integer upDown;

    @TableField("up_ids")
    private String upIds;

    @TableField("topic_id")
    private Integer topicId;

    @TableField("user_id")
    private Integer userId;


}
