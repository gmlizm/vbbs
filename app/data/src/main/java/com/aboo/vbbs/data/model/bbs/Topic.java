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
@TableName("bbs_topic")
public class Topic implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("comment_count")
    private Integer commentCount;

    @TableField("content")
    private String content;

    @TableField("good")
    private Boolean good;

    @TableField("in_time")
    private Date inTime;

    @TableField("last_comment_time")
    private Date lastCommentTime;

    @TableField("topic_lock")
    private Boolean topicLock;

    @TableField("modify_time")
    private Date modifyTime;

    @TableField("title")
    private String title;

    @TableField("top")
    private Boolean top;

    @TableField("up_ids")
    private String upIds;

    @TableField("url")
    private String url;

    @TableField("view")
    private Integer view;

    @TableField("node_id")
    private Integer nodeId;

    @TableField("user_id")
    private Integer userId;


}
