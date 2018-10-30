package com.aboo.vbbs.serv;

import java.io.Serializable;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.aboo.vbbs.data.mapper.bbs.ScoreLogMapper;
import com.aboo.vbbs.data.model.bbs.ScoreLog;
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
public class ScoreLogService extends ServiceImpl<ScoreLogMapper, ScoreLog> {

	@CacheEvict(allEntries = true)
	public void save(ScoreLog scoreLog) {
		super.insert(scoreLog);
	}

	@Cacheable
	public Page<ScoreLog> findScoreByUser(Integer p, int size, Serializable userId) {
		return super.selectPage(new Page<ScoreLog>(p, size, "in_time", false),
				new EntityWrapper<ScoreLog>().eq("user_id", userId));
	}

}
