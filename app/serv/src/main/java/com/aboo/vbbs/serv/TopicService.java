package com.aboo.vbbs.serv;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.aboo.vbbs.data.mapper.bbs.TopicMapper;
import com.aboo.vbbs.data.model.bbs.Node;
import com.aboo.vbbs.data.model.bbs.Topic;
import com.aboo.vbbs.data.model.bbs.User;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yylizm
 * @since 2018-06-04
 */
@Service
public class TopicService extends ServiceImpl<TopicMapper, Topic> {

	@Autowired
	private CollectService collectService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private CommentService commentService;

	/**
	 * save topic
	 *
	 * @param topic
	 */
	@CacheEvict(allEntries = true)
	public Topic save(Topic topic) {
		super.insert(topic);
		return topic;
	}

	/**
	 * query topic by id
	 *
	 * @param id
	 * @return
	 */
	@Cacheable
	public Topic findById(int id) {
		return super.selectById(id);
	}

	/**
	 * delete topic by id
	 *
	 * @param id
	 */
	@CacheEvict(allEntries = true)
	public void deleteById(int id) {
		Topic topic = findById(id);
		if (topic != null) {
			// 删除收藏这个话题的记录
			collectService.deleteByTopic(topic);
			// 删除通知里提到的话题
			notificationService.deleteByTopic(topic);
			// 删除话题下面的评论
			commentService.deleteByTopic(topic);

			// 删除话题
			super.deleteById(topic.getId());
		}
	}

	/**
	 * delete topic by user
	 *
	 * @param user
	 */
	@CacheEvict(allEntries = true)
	public void deleteByUser(User user) {
		super.delete(new EntityWrapper<>(new Topic().setUserId(user.getId())));
	}

	/**
	 * 分页查询话题列表
	 *
	 * @param p
	 * @param size
	 * @return
	 */
	@Cacheable
	public Page<Topic> page(int p, int size, String tab) {
		Topic topic = new Topic();
		Wrapper<Topic> wrapper = null;

		switch (tab) {
		case "default":
			break;
		case "good":
			topic.setGood(true);
			break;
		case "noanswer":
			topic.setCommentCount(0);
			break;
		case "newest":
			wrapper = new EntityWrapper<Topic>().orderBy("in_time", false);
			break;
		default:
			return null;
		}

		if(wrapper == null) {
			wrapper = new EntityWrapper<Topic>().orderBy("top", false).orderBy("in_time", false).orderBy("last_comment_time", false);
		}
		return super.selectPage(new Page<Topic>(p, size), wrapper);
	}

	/**
	 * 搜索
	 *
	 * @param p
	 * @param size
	 * @param q
	 * @return
	 */
	@Cacheable
	public Page<Topic> search(int p, int size, String q) {
		if (StringUtils.isEmpty(q))
			return null;
		Wrapper<Topic> wrapper = new EntityWrapper<Topic>().like("title", "%" + q + "%").like("content", "%" + q + "%")
				.orderBy("in_time", false);
		return super.selectPage(new Page<Topic>(p, size), wrapper);
	}

	/**
	 * 查询用户的话题
	 *
	 * @param p
	 * @param size
	 * @param user
	 * @return
	 */
	@Cacheable
	public Page<Topic> findByUser(int p, int size, User user) {
		Wrapper<Topic> wrapper = new EntityWrapper<>(new Topic().setUserId(user.getId())).orderBy("in_time", false);
		return super.selectPage(new Page<Topic>(p, size), wrapper);
	}

	/**
	 * 查询在date1与date2之前发帖总数
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	@Cacheable
	public int countByInTimeBetween(Date date1, Date date2) {
		Wrapper<Topic> wrapper = Condition.wrapper();
		wrapper.between("in_time", date1, date2);
		return super.selectCount(wrapper);
	}

	/**
	 * 根据标题查询话题（防止发布重复话题）
	 *
	 * @param title
	 * @return
	 */
	@Cacheable
	public Topic findByTitle(String title) {
		return super.selectOne(new EntityWrapper<>(new Topic().setTitle(title)));
	}

	@Cacheable
	public Page<Topic> findByNode(Node node, int p, int size) {
		Wrapper<Topic> wrapper = new EntityWrapper<>(new Topic().setNodeId(node.getId()));
		wrapper.orderDesc(Lists.asList("top", "in_time", null));
		return super.selectPage(new Page<Topic>(p, size), wrapper);
	}

	// 查询节点下有多少话题数
	public long countByNode(com.aboo.vbbs.data.model.bbs.Node node) {
		return super.selectCount(new EntityWrapper<>(new Topic().setNodeId(node.getId())));
	}
}
