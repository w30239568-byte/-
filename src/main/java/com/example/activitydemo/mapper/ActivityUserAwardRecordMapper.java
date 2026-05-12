package com.example.activitydemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.activitydemo.domain.ActivityAward;
import com.example.activitydemo.domain.ActivityUserAwardRecord;
import com.example.activitydemo.validate.ActivityAwardSearchValidate;
import com.example.activitydemo.validate.ActivityUserAwardRecordSearchValidate;
import com.example.activitydemo.vo.ActivityUserAwardRecordListedVo;
import com.example.activitydemo.vo.ActivityUserAwardVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户奖品记录Mapper
 *
 * @author fei
 */
@Mapper
public interface ActivityUserAwardRecordMapper extends BaseMapper<ActivityUserAwardRecord> {

    IPage<ActivityUserAwardRecordListedVo> list(Page<ActivityUserAwardRecord> page, @Param("param") ActivityUserAwardRecordSearchValidate activityUserAwardRecordSearchValidate);

    IPage<ActivityUserAwardVo> userGetList(Page<ActivityAward> page,@Param("param") ActivityAwardSearchValidate searchValidate);
}
