package com.aboo.vbbs.serv;

import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.aboo.vbbs.data.mapper.bbs.GithubUserMapper;
import com.aboo.vbbs.data.model.bbs.GithubUser;
import com.aboo.vbbs.data.model.bbs.User;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yylizm
 * @since 2018-06-04
 */
@Service
public class GithubUserService extends ServiceImpl<GithubUserMapper, GithubUser> {

	/**
	 * 根据github的登录名查询github登录记录
	 *
	 * @param login
	 * @return
	 */
	@Cacheable
	public GithubUser findByLogin(String login) {
		return super.selectOne(new EntityWrapper<>(new GithubUser().setLogin(login)));
	}

	/**
	 * 根据本地用户查询github登录记录
	 *
	 * @param user
	 * @return
	 */
	@Cacheable
	public GithubUser findByUser(User user) {
		return super.selectById(user.getGithubUserId());
	}

	@CacheEvict(allEntries = true)
	public GithubUser save(GithubUser githubUser) {
		super.insert(githubUser);
		return githubUser;
	}

	public GithubUser convert(Map<String,Object> map, GithubUser githubUser) {
		githubUser.setGithubId(map.get("id").toString());
		githubUser.setLogin(map.get("login").toString());
		githubUser.setAvatarUrl(map.get("avatar_url").toString());
		githubUser.setUrl(map.get("url") != null ? map.get("url").toString() : null);
		githubUser.setHtmlUrl(map.get("html_url") != null ? map.get("html_url").toString() : null);
		githubUser.setFollowersUrl(map.get("followers_url") != null ? map.get("followers_url").toString() : null);
		githubUser.setFollowingUrl(map.get("following_url") != null ? map.get("following_url").toString() : null);
		githubUser.setGistsUrl(map.get("gists_url") != null ? map.get("gists_url").toString() : null);
		githubUser.setStartedUrl(map.get("started_url") != null ? map.get("started_url").toString() : null);
		githubUser.setSubscriptionsUrl(
				map.get("subscriptions_url") != null ? map.get("subscriptions_url").toString() : null);
		githubUser.setOrganizationsUrl(
				map.get("organizations_url") != null ? map.get("organizations_url").toString() : null);
		githubUser.setReposUrl(map.get("repos_url") != null ? map.get("repos_url").toString() : null);
		githubUser.setEventsUrl(map.get("events_url") != null ? map.get("events_url").toString() : null);
		githubUser.setReceivedEventsUrl(
				map.get("received_events_url") != null ? map.get("received_events_url").toString() : null);
		githubUser.setType(map.get("type") != null ? map.get("type").toString() : null);
		githubUser.setSiteAdmin(map.get("site_admin") != null && (boolean) map.get("site_admin"));
		githubUser.setName(map.get("name") != null ? map.get("name").toString() : null);
		githubUser.setCompany(map.get("company") != null ? map.get("company").toString() : null);
		githubUser.setBlog(map.get("blog") != null ? map.get("blog").toString() : null);
		githubUser.setLocation(map.get("location") != null ? map.get("location").toString() : null);
		githubUser.setEmail(map.get("email") != null ? map.get("email").toString() : null);
		githubUser.setHireable(map.get("hireable") != null ? map.get("hireable").toString() : null);
		githubUser.setBio(map.get("bio") != null ? map.get("bio").toString() : null);
		githubUser.setPublicRepos(map.get("public_repos") != null ? (int) map.get("public_repos") : 0);
		githubUser.setPublicGists(map.get("public_gists") != null ? (int) map.get("public_gists") : 0);
		githubUser.setFollowers(map.get("followers") != null ? (int) map.get("followers") : 0);
		githubUser.setFollowing(map.get("following") != null ? (int) map.get("following") : 0);
		return githubUser;
	}
}
