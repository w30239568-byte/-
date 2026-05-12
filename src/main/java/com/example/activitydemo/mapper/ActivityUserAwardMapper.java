package com.example.activitydemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.activitydemo.domain.ActivityUserAward;
import com.example.activitydemo.validate.ActivityUserAwardSearchValidate;
import com.example.activitydemo.vo.ActivityUserAwardListedVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户奖品Mapper
 *
 * @author fei
 */
@Mapper
public interface ActivityUserAwardMapper extends BaseMapper<ActivityUserAward> {

    IPage<ActivityUserAwardListedVo> list(Page<ActivityUserAward> page, @Param("param") ActivityUserAwardSearchValidate activityUserAwardSearchValidate);
}
