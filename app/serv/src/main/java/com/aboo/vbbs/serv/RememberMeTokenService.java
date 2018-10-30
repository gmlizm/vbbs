package com.aboo.vbbs.serv;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.aboo.vbbs.data.mapper.bbs.RememberMeTokenMapper;
import com.aboo.vbbs.data.model.bbs.RememberMeToken;
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
public class RememberMeTokenService extends ServiceImpl<RememberMeTokenMapper, RememberMeToken> {

	public void createNewToken(RememberMeToken token, int loginPoints) {

		List<RememberMeToken> tokens = super.selectList(
				new EntityWrapper<>(new RememberMeToken().setUsername(token.getUsername())).orderBy("date", false));

		if (tokens != null && tokens.size() >= loginPoints) {
			int end = tokens.size() - loginPoints + 1;
			for (int i = 0; i < end; i++) {
				super.deleteById(tokens.get(i));
			}
		}

		RememberMeToken rememberMeToken = new RememberMeToken();
		BeanUtils.copyProperties(token, rememberMeToken);
		super.insert(rememberMeToken);
	}

	public void updateToken(String series, String tokenValue, Date lastUsed) {
		RememberMeToken rememberMeToken = super.selectOne(new EntityWrapper<>(new RememberMeToken().setSeries(series)));
		if (rememberMeToken != null) {
			rememberMeToken.setTokenValue(tokenValue);
			rememberMeToken.setDate(lastUsed);
			super.updateById(rememberMeToken);
		}
	}

	public RememberMeToken getTokenForSeries(String series) {
		RememberMeToken rememberMeToken = super.selectOne(new EntityWrapper<>(new RememberMeToken().setSeries(series)));
		if (rememberMeToken == null) {
			// TODO log
		}
		return rememberMeToken;
	}

	public void removeUserTokens(String username) {
		super.delete(new EntityWrapper<>(new RememberMeToken().setUsername(username)));
	}

	public RememberMeToken getBySeries(String series) {
		return super.selectOne(new EntityWrapper<>(new RememberMeToken().setSeries(series)));
	}

	public boolean deleteByUsername(String username) {
		boolean result = super.delete(new EntityWrapper<>(new RememberMeToken().setUsername(username)));
		return result;
	}

	public List<RememberMeToken> getAllByUsernameOrderByDate(String username){
		return super.selectList(new EntityWrapper<>(new RememberMeToken().setUsername(username)).orderBy("date", true));
	}
}
