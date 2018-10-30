package com.aboo.vbbs.serv;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.aboo.vbbs.data.mapper.bbs.NotificationMapper;
import com.aboo.vbbs.data.model.bbs.Notification;
import com.aboo.vbbs.data.model.bbs.Topic;
import com.aboo.vbbs.data.model.bbs.User;
import com.aboo.vbbs.serv.enums.NotificationEnum;
import com.aboo.vbbs.serv.util.UserUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
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
public class NotificationService extends ServiceImpl<NotificationMapper, Notification> {

	@Autowired
	private UserService userService;

	/**
	 * 保存通知
	 *
	 * @param notification
	 */
	@CacheEvict(allEntries = true)
	public void save(Notification notification) {
		super.insert(notification);
	}

	public void sendNotification(User user, Topic topic, String content) {
		// 给话题作者发送通知
		if (user.getId() != topic.getUserId()) {
			this.sendNotification(user, userService.findById(topic.getUserId()), NotificationEnum.COMMENT.name(), topic,
					content);
		}
		// 给At用户发送通知
		List<String> atUsers = UserUtil.fetchUsers(null, content);
		for (String u : atUsers) {
			u = u.replace("@", "").trim();
			if (!u.equals(user.getUsername())) {
				User _user = userService.findByUsername(u);
				if (_user != null) {
					this.sendNotification(user, _user, NotificationEnum.AT.name(), topic, content);
				}
			}
		}
	}

	/**
	 * 发送通知
	 *
	 * @param user
	 * @param targetUser
	 * @param action
	 * @param topic
	 * @param content
	 */
	public void sendNotification(User user, User targetUser, String action, Topic topic, String content) {
		Notification notification = new Notification();
		notification.setUserId(user.getId());
		notification.setTargetUserId(targetUser.getId());
		notification.setInTime(new Date());
		notification.setTopicId(topic.getId());
		notification.setAction(action);
		notification.setContent(content);
		notification.setRead(false);
		save(notification);
	}

	/**
	 * 根据用户查询通知
	 *
	 * @param p
	 * @param size
	 * @param targetUser
	 * @param isRead
	 * @return
	 */
	@Cacheable
	public Page<Notification> findByTargetUserAndIsRead(int p, int size, User targetUser, Boolean isRead) {
		Wrapper<Notification> wrapper = new EntityWrapper<>(
				new Notification().setTargetUserId(targetUser.getId()).setRead(isRead));
		wrapper.orderBy("is_read", true).orderBy("in_time", false);
		return super.selectPage(new Page<Notification>(p, size), wrapper);

	}

	/**
	 * 根据用户查询已读/未读的通知
	 *
	 * @param targetUser
	 * @param isRead
	 * @return
	 */
	@Cacheable
	public long countByTargetUserAndIsRead(User targetUser, boolean isRead) {
		return super.selectCount(
				new EntityWrapper<>(new Notification().setTargetUserId(targetUser.getId()).setRead(isRead)));
	}

	/**
	 * 根据阅读状态查询通知
	 *
	 * @param targetUser
	 * @param isRead
	 * @return
	 */
	@Cacheable
	public List<Notification> findByTargetUserAndIsRead(User targetUser, boolean isRead) {
		return super.selectList(
				new EntityWrapper<>(new Notification().setTargetUserId(targetUser.getId()).setRead(isRead)));
	}

	/**
	 * 批量更新通知的状态
	 *
	 * @param targetUser
	 */
	@CacheEvict(allEntries = true)
	public void updateByIsRead(User targetUser) {
		List<Notification> notifications = super.selectList(
				new EntityWrapper<>(new Notification().setTargetUserId(targetUser.getId())));
		if (notifications == null) {
			return;
		}
		notifications.forEach(it -> {
			it.setRead(true);
		});
		super.updateBatchById(notifications);
	}

	/**
	 * 删除用户的通知
	 *
	 * @param user
	 */
	@CacheEvict(allEntries = true)
	public void deleteByUser(Integer userId) {
		super.delete(new EntityWrapper<>(new Notification().setUserId(userId)));
	}

	/**
	 * 删除目标用户的通知
	 *
	 * @param user
	 */
	@CacheEvict(allEntries = true)
	public void deleteByTargetUser(Integer userId) {
		super.delete(new EntityWrapper<>(new Notification().setTargetUserId(userId)));
	}

	/**
	 * 话题被删除了，删除由话题引起的通知信息
	 *
	 * @param topic
	 */
	@CacheEvict(allEntries = true)
	public void deleteByTopic(Serializable topicId) {
		super.delete(new EntityWrapper<Notification>().eq("topic_id", topicId));
	}

}
