package com.example.activitydemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.activitydemo.domain.ActivityAward;
import com.example.activitydemo.validate.ActivityAwardSearchValidate;
import com.example.activitydemo.vo.ActivityAwardListedVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ActivityAwardMapper extends BaseMapper<ActivityAward> {

    IPage<ActivityAwardListedVo> list(Page<ActivityAward> page, @Param("param") ActivityAwardSearchValidate activityAwardSearchValidate);

    @Update("update la_activity_award set use_num = ifnull(use_num,0) + 1 where id = #{awardId} and is_delete = 0")
    int incrUseNum(@Param("awardId") Long awardId);

    @Update("update la_activity_award set use_num = ifnull(use_num,0) + 1 where id = #{awardId} and is_delete = 0 and ifnull(use_num,0) < ifnull(limit_num,0)")
    int incrUseNumWithLimit(@Param("awardId") Long awardId);
}
