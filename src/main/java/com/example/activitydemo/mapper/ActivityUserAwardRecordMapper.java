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
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ActivityUserAwardRecordMapper extends BaseMapper<ActivityUserAwardRecord> {

    IPage<ActivityUserAwardRecordListedVo> list(Page<ActivityUserAwardRecord> page, @Param("param") ActivityUserAwardRecordSearchValidate activityUserAwardRecordSearchValidate);

    IPage<ActivityUserAwardVo> userGetList(Page<ActivityAward> page, @Param("param") ActivityAwardSearchValidate searchValidate);

    @Select("select award_id from la_activity_user_award_record where user_id = #{userId} and activity_id = #{activityId} and is_delete = 0")
    List<Long> getAllAwardByUserId(@Param("userId") Long userId, @Param("activityId") Long activityId);

    @Select("select count(id) from la_activity_user_award_record where type = #{type} and user_id = #{userId} and is_delete = 0")
    Integer getNumAwardIdByUserId(@Param("type") Integer type, @Param("userId") Long userId);

    @Select("select count(id) from la_activity_user_award_record where user_id = #{userId} and award_id = #{awardId} and activity_id = #{activityId} and is_delete = 0")
    Integer countByUserAndAwardAndActivity(@Param("userId") Long userId, @Param("awardId") Long awardId, @Param("activityId") Long activityId);
}
