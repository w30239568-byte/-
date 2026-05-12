package com.example.activitydemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.activitydemo.domain.ActivityUserAward;
import com.example.activitydemo.validate.ActivityUserAwardSearchValidate;
import com.example.activitydemo.vo.ActivityUserAwardListedVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ActivityUserAwardMapper extends BaseMapper<ActivityUserAward> {

    IPage<ActivityUserAwardListedVo> list(Page<ActivityUserAward> page, @Param("param") ActivityUserAwardSearchValidate activityUserAwardSearchValidate);

    @Select("select * from la_activity_user_award where create_user_id = #{userId} and activity_award_id = #{awardId} and is_delete = 0 limit 1")
    ActivityUserAward getByUserId(@Param("userId") Long userId, @Param("awardId") Long awardId);
}
