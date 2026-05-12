package com.example.activitydemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.activitydemo.domain.ActivityAward;
import com.example.activitydemo.validate.ActivityAwardSearchValidate;
import com.example.activitydemo.vo.ActivityAwardListedVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 奖品Mapper
 *
 * @author fei
 */
@Mapper
public interface ActivityAwardMapper extends BaseMapper<ActivityAward> {

    IPage<ActivityAwardListedVo> list(Page<ActivityAward> page, @Param("param") ActivityAwardSearchValidate activityAwardSearchValidate);
}
