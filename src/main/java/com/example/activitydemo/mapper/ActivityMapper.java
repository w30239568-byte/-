package com.example.activitydemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.activitydemo.domain.Activity;
import com.example.activitydemo.validate.ActivitySearchValidate;
import com.example.activitydemo.vo.ActivityListedVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 活动Mapper
 *
 * @author fei
 */
@Mapper
public interface ActivityMapper extends BaseMapper<Activity> {

    IPage<ActivityListedVo> list(Page<Activity> page, @Param("param") ActivitySearchValidate activitySearchValidate);
}
