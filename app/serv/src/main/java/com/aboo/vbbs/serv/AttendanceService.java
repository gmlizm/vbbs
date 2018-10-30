package com.aboo.vbbs.serv;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.aboo.vbbs.data.mapper.bbs.AttendanceMapper;
import com.aboo.vbbs.data.model.bbs.Attendance;
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
public class AttendanceService extends ServiceImpl<AttendanceMapper, Attendance> {

	public Attendance findByUserAndInTime(User user, Date date1, Date date2) {
		Attendance entity = new Attendance().setUserId(user.getId());
		return super.selectOne(new EntityWrapper<Attendance>(entity).between("in_time", date1, date2));
	}


	public void save(Attendance attendance) {
		super.insert(attendance);
	}
}
