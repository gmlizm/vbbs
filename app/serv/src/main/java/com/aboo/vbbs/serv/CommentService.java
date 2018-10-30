package com.aboo.vbbs.serv;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.aboo.vbbs.base.constant.Constants;
import com.aboo.vbbs.data.mapper.bbs.CommentMapper;
import com.aboo.vbbs.data.model.bbs.Comment;
import com.aboo.vbbs.data.model.bbs.Topic;
import com.aboo.vbbs.data.model.bbs.User;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yylizm
 * @since 2018-06-04
 */
@Service
public class CommentService extends ServiceImpl<CommentMapper, Comment> {
	
	@Autowired
	private TopicService topicService;
	 /**
	   * 根据id查询评论
	   *
	   * @param id
	   * @return
	   */
	  @Cacheable
	  public Comment findById(int id) {
	    return super.selectById(id);
	  }

	  /**
	   * 保存评论
	   *
	   * @param comment
	   */
	  @CacheEvict(allEntries = true)
	  public void save(Comment comment) {
	    super.insert(comment);
	  }

	  /**
	   * 根据id删除评论
	   *
	   * @param id
	   * @return
	   */
	  @CacheEvict(allEntries = true)
	  public Map delete(int id) {
	    Map map = new HashMap();
	    Comment comment = findById(id);
	    if (comment != null) {
	      map.put("topicId", comment.getTopicId());
	      Topic topic = topicService.findById(comment.getTopicId());
	      topic.setCommentCount(topic.getCommentCount() - 1);
	      topicService.save(topic);
	      super.deleteById(id);
	    }
	    return map;
	  }

	  /**
	   * 删除用户发布的所有评论
	   *
	   * @param user
	   */
	  @CacheEvict(allEntries = true)
	  public void deleteByUser(User user) {
	    super.delete(new EntityWrapper<Comment>().eq("user_id", user.getId()));
	  }

	  /**
	   * 根据话题删除评论
	   *
	   * @param topic
	   */
	  @CacheEvict(allEntries = true)
	  public void deleteByTopic(Topic topic) {
		  super.delete(new EntityWrapper<Comment>().eq("topic_id", topic.getId()));
	  }

	  /**
	   * 赞
	   *
	   * @param userId
	   * @param comment
	   */
	  @CacheEvict(allEntries = true) //有更新操作都要清一下缓存
	  public Comment up(int userId, Comment comment) {
	    String upIds = comment.getUpIds();
	    if (upIds == null) upIds = Constants.COMMA;
	    if (!upIds.contains(Constants.COMMA + userId + Constants.COMMA)) {
	      comment.setUp(comment.getUp() + 1);
	      comment.setUpIds(upIds + userId + Constants.COMMA);

	      String downIds = comment.getDownIds();
	      if (downIds == null) downIds = Constants.COMMA;
	      if (downIds.contains(Constants.COMMA + userId + Constants.COMMA)) {
	        comment.setDown(comment.getDown() - 1);
	        downIds = downIds.replace(Constants.COMMA + userId + Constants.COMMA, Constants.COMMA);
	        comment.setDownIds(downIds);
	      }
	      int count = comment.getUp() - comment.getDown();
	      comment.setUpDown(count > 0 ? count : 0);
	      save(comment);
	    }
	    return comment;
	  }

	  /**
	   * 取消赞
	   *
	   * @param userId
	   * @param commentId
	   */
	  @CacheEvict(allEntries = true) //有更新操作都要清一下缓存
	  public Comment cancelUp(int userId, int commentId) {
	    Comment comment = findById(commentId);
	    if (comment != null) {
	      String upIds = comment.getUpIds();
	      if (upIds == null) upIds = Constants.COMMA;
	      if (upIds.contains(Constants.COMMA + userId + Constants.COMMA)) {
	        comment.setUp(comment.getUp() - 1);
	        upIds = upIds.replace(Constants.COMMA + userId + Constants.COMMA, Constants.COMMA);
	        comment.setUpIds(upIds);

	        int count = comment.getUp() - comment.getDown();
	        comment.setUpDown(count > 0 ? count : 0);
	        save(comment);
	      }
	    }
	    return comment;
	  }

	  /**
	   * 踩
	   *
	   * @param userId
	   * @param comment
	   */
	  @CacheEvict(allEntries = true) //有更新操作都要清一下缓存
	  public Comment down(int userId, Comment comment) {
	    String downIds = comment.getDownIds();
	    if (downIds == null) downIds = Constants.COMMA;
	    if (!downIds.contains(Constants.COMMA + userId + Constants.COMMA)) {
	      comment.setDown(comment.getDown() + 1);
	      comment.setDownIds(downIds + userId + Constants.COMMA);

	      String upIds = comment.getUpIds();
	      if (upIds == null) upIds = Constants.COMMA;
	      if (upIds.contains(Constants.COMMA + userId + Constants.COMMA)) {
	        comment.setUp(comment.getUp() - 1);
	        upIds = upIds.replace(Constants.COMMA + userId + Constants.COMMA, Constants.COMMA);
	        comment.setUpIds(upIds);
	      }
	      int count = comment.getUp() - comment.getDown();
	      comment.setUpDown(count > 0 ? count : 0);
	      save(comment);
	    }
	    return comment;
	  }

	  /**
	   * 取消踩
	   *
	   * @param userId
	   * @param commentId
	   */
	  @CacheEvict(allEntries = true) //有更新操作都要清一下缓存
	  public Comment cancelDown(int userId, int commentId) {
	    Comment comment = findById(commentId);
	    if (comment != null) {
	      String downIds = comment.getDownIds();
	      if (downIds == null) downIds = Constants.COMMA;
	      if (downIds.contains(Constants.COMMA + userId + Constants.COMMA)) {
	        comment.setDown(comment.getDown() - 1);
	        downIds = downIds.replace(Constants.COMMA + userId + Constants.COMMA, Constants.COMMA);
	        comment.setDownIds(downIds);

	        int count = comment.getUp() - comment.getDown();
	        comment.setUpDown(count > 0 ? count : 0);
	        save(comment);
	      }
	    }
	    return comment;
	  }

	  /**
	   * 根据话题查询评论列表
	   *
	   * @param topic
	   * @return
	   */
	  @Cacheable
	  public List<Comment> findByTopic(Topic topic) {
	    return super.selectList(new EntityWrapper<>(new Comment().setTopicId(topic.getId()))
	    		.orderBy("up_down", false).orderBy("down", true).orderBy("in_time",true));
	  }

	  /**
	   * 分页查询评论列表
	   *
	   * @param p
	   * @param size
	   * @return
	   */
	  @Cacheable
	  public Page<Comment> page(int p, int size) {
	    return super.selectPage(new Page<Comment>(p,size,"in_time", false));
	  }

	  /**
	   * 查询用户的评论列表
	   *
	   * @param p
	   * @param size
	   * @param user
	   * @return
	   */
	  @Cacheable
	  public Page<Comment> findByUser(int p, int size, User user) {
	    return super.selectPage(new Page<Comment>(p,size,"in_time", false),
	    		new EntityWrapper<Comment>().eq("user_id", user.getId()));
	  }
}
