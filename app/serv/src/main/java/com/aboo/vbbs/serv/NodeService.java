package com.aboo.vbbs.serv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.aboo.vbbs.data.mapper.bbs.NodeMapper;
import com.aboo.vbbs.data.model.bbs.Node;
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
public class NodeService extends ServiceImpl<NodeMapper, Node> {
	@Cacheable
	public List findAll(boolean child) {
		List<Map<String, Object>> nodes = new ArrayList<>();
		if (child) {//TODO 待处理
			List<Node> nodeList = super.selectList(new EntityWrapper<Node>().ne("pid", 0));
			return nodeList;
		} else {
			List<Node> pNodes = this.findByPid(0);
			for (Node pn : pNodes) {
				Map<String, Object> map = new HashMap<>();
				List<Node> cNodes = this.findByPid(pn.getId());
				map.put("name", pn.getName());
				map.put("list", cNodes);
				nodes.add(map);
			}
			return nodes;
		}
	}

	@Cacheable
	public List<Node> findByPid(int pid) {
		return super.selectList(new EntityWrapper<>(new Node().setPid(pid)));
	}

	@CacheEvict(allEntries = true)
	public void save(Node node) {
		super.insert(node);
	}

	@CacheEvict(allEntries = true)
	public void deleteById(int id) {
		super.deleteById(id);
	}

	@Cacheable
	public Node findById(int id) {
		return super.selectById(id);
	}

	@Cacheable
	public Node findByValue(String value) {
		return super.selectOne(new EntityWrapper<>(new Node().setValue(value)));
	}

	@CacheEvict(allEntries = true)
	public void deleteByPid(int pid) {
		super.delete(new EntityWrapper<>(new Node().setPid(pid)));
	}

	// number : 1 or -1
	@CacheEvict(allEntries = true)
	public void dealTopicCount(Node node, int number) {
		// 处理当前的节点
		node.setTopicCount(node.getTopicCount() + number);
		this.save(node);

		// 处理当前节点的父节点
		int pid = node.getPid();
		Node pNode = this.findById(pid);
		pNode.setTopicCount(pNode.getTopicCount() + number);
		this.save(pNode);
	}

	@CacheEvict(allEntries = true)
	public void clearCache() {
	}
}
