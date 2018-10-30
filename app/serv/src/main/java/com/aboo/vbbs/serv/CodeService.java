package com.aboo.vbbs.serv;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aboo.vbbs.base.util.DateUtil;
import com.aboo.vbbs.base.util.StrUtil;
import com.aboo.vbbs.data.mapper.bbs.CodeMapper;
import com.aboo.vbbs.data.model.bbs.Code;
import com.aboo.vbbs.serv.enums.CodeEnum;
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
public class CodeService extends ServiceImpl<CodeMapper, Code> {
	public Code findByEmailAndCodeAndType(String email, String code, CodeEnum type) {
		List<Code> codes = 
				super.selectList(new EntityWrapper<>(new Code().setCode(code).setEmail(email).setType(type.name())));
		if (codes.size() > 0)
			return codes.get(0);
		return null;
	}

	public void save(Code code) {
		super.insert(code);
	}

	public String genEmailCode(String email) {
		String genCode = StrUtil.randomString(6);
		Code code = findByEmailAndCodeAndType(email, genCode, CodeEnum.EMAIL);
		if (code != null) {
			return genEmailCode(email);
		} else {
			code = new Code();
			code.setCode(genCode);
			code.setExpireTime(DateUtil.getMinuteAfter(new Date(), 10));
			code.setType(CodeEnum.EMAIL.name());
			code.setEmail(email);
			code.setUsed(false);
			save(code);
			return genCode;
		}
	}

	public int validateCode(String email, String code, CodeEnum type) {
		Code code1 = findByEmailAndCodeAndType(email, code, type);
		if (code1 == null)
			return 1;// 验证码不正确
		if (DateUtil.isExpire(code1.getExpireTime()))
			return 2; // 过期了
		if (code1.getUsed())
			return 3; // 验证码已经被使用了
		code1.setUsed(true);
		save(code1);
		return 0; // 正常
	}
}
