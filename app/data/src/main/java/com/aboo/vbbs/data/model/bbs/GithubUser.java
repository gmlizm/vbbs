package com.aboo.vbbs.data.model.bbs;

import java.io.Serializable;

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
@TableName("bbs_github_user")
public class GithubUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("avatar_url")
    private String avatarUrl;

    @TableField("bio")
    private String bio;

    @TableField("blog")
    private String blog;

    @TableField("company")
    private String company;

    @TableField("email")
    private String email;

    @TableField("events_url")
    private String eventsUrl;

    @TableField("followers")
    private Integer followers;

    @TableField("followers_url")
    private String followersUrl;

    @TableField("following")
    private Integer following;

    @TableField("following_url")
    private String followingUrl;

    @TableField("gists_url")
    private String gistsUrl;

    @TableField("github_id")
    private String githubId;

    @TableField("hireable")
    private String hireable;

    @TableField("html_url")
    private String htmlUrl;

    @TableField("location")
    private String location;

    @TableField("login")
    private String login;

    @TableField("name")
    private String name;

    @TableField("organizations_url")
    private String organizationsUrl;

    @TableField("public_gists")
    private Integer publicGists;

    @TableField("public_repos")
    private Integer publicRepos;

    @TableField("received_events_url")
    private String receivedEventsUrl;

    @TableField("repos_url")
    private String reposUrl;

    @TableField("site_admin")
    private Boolean siteAdmin;

    @TableField("started_url")
    private String startedUrl;

    @TableField("subscriptions_url")
    private String subscriptionsUrl;

    @TableField("type")
    private String type;

    @TableField("url")
    private String url;


}
