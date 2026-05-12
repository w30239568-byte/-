package com.example.activitydemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.activitydemo.domain.ActivityUserJoin;
import com.example.activitydemo.validate.ActivityUserJoinSearchValidate;
import com.example.activitydemo.vo.ActivityUserJoinListedVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ActivityUserJoinMapper extends BaseMapper<ActivityUserJoin> {

    IPage<ActivityUserJoinListedVo> list(Page<ActivityUserJoin> page, @Param("param") ActivityUserJoinSearchValidate activityUserJoinSearchValidate);

    @Select("select * from la_activity_user_join where activity_id =#{activityId} and is_delete = 0 limit 1")
    ActivityUserJoin getByActivityId(@Param("activityId") Long activityId);

    @Select("select * from la_activity_user_join where activity_id =#{activityId} and user_id = #{userId} and is_delete = 0 limit 1")
    ActivityUserJoin getByUserIdAndActivityId(@Param("activityId") Long activityId, @Param("userId") Long userId);

    List<ActivityUserJoinListedVo> getUserJoinActivity();
}
