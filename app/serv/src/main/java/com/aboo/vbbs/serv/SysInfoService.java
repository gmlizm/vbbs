package com.aboo.vbbs.serv;

import org.springframework.stereotype.Service;

import com.aboo.vbbs.data.mapper.bbs.SysInfoMapper;
import com.aboo.vbbs.data.model.bbs.SysInfo;
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
public class SysInfoService extends ServiceImpl<SysInfoMapper, SysInfo> {
	public SysInfo findByName(String name) {
		return super.selectOne(new EntityWrapper<>(new SysInfo().setName(name)));
	}

	// TODO 待测试验证
	public SysInfo save(SysInfo sysInfo) {
		super.insert(sysInfo);
		return sysInfo;
	}
}
