package com.aboo.vbbs.serv;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.aboo.vbbs.data.mapper.bbs.CollectMapper;
import com.aboo.vbbs.data.model.bbs.Collect;
import com.aboo.vbbs.data.model.bbs.Topic;
import com.aboo.vbbs.data.model.bbs.User;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
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
public class CollectService extends ServiceImpl<CollectMapper, Collect> {
	/**
	 * 查询用户收藏的话题
	 *
	 * @param p
	 * @param size
	 * @param user
	 * @return
	 */
	@Cacheable
	public Page<Collect> findByUser(int p, int size, User user) {
		return super.selectPage(new Page<Collect>(p, size, "in_time", false),
				new EntityWrapper<>(new Collect().setUserId(user.getId())));
	}

	/**
	 * 查询用户共收藏了多少篇话题
	 *
	 * @param user
	 * @return
	 */
	@Cacheable
	public long countByUser(User user) {
		return super.selectCount(new EntityWrapper<>(new Collect().setUserId(user.getId())));
	}

	/**
	 * 查询话题共被多少用户收藏
	 *
	 * @param topic
	 * @return
	 */
	@Cacheable
	public long countByTopic(Topic topic) {
		return super.selectCount(new EntityWrapper<>(new Collect().setTopicId(topic.getId())));
	}

	/**
	 * 根据用户和话题查询收藏记录
	 *
	 * @param user
	 * @param topic
	 * @return
	 */
	@Cacheable
	public Collect findByUserAndTopic(User user, Topic topic) {
		Collect entity = new Collect().setUserId(user.getId()).setTopicId(topic.getId());
		return super.selectOne(new EntityWrapper<Collect>(entity));
	}

	/**
	 * 收藏话题
	 *
	 * @param collect
	 */
	@CacheEvict(allEntries = true)
	public void save(Collect collect) {
		super.insert(collect);
	}

	/**
	 * 根据id删除收藏记录
	 *
	 * @param id
	 */
	@CacheEvict(allEntries = true)
	public void deleteById(int id) {
		super.deleteById(id);
	}

	/**
	 * 用户被删除了，删除对应的所有收藏记录（用户关联关系太多，没法删除用户，所以这个方法也没被调用）
	 *
	 * @param user
	 */
	@CacheEvict(allEntries = true)
	public void deleteByUser(User user) {
		super.delete(new EntityWrapper<>(new Collect().setUserId(user.getId())));
	}

	/**
	 * 话题被删除了，删除对应的所有收藏记录
	 *
	 * @param topic
	 */
	@CacheEvict(allEntries = true)
	public void deleteByTopic(Topic topic) {
		super.delete(new EntityWrapper<>(new Collect().setTopicId(topic.getId())));
	}
}
