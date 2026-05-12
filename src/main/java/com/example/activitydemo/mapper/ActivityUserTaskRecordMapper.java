package com.example.activitydemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.activitydemo.domain.ActivityUserTaskRecord;
import com.example.activitydemo.validate.ActivityUserTaskRecordSearchValidate;
import com.example.activitydemo.vo.ActivityUserTaskRecordListedVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * 用户任务记录Mapper
 *
 * @author fei
 */
@Mapper
public interface ActivityUserTaskRecordMapper extends BaseMapper<ActivityUserTaskRecord> {

    IPage<ActivityUserTaskRecordListedVo> list(Page<ActivityUserTaskRecord> page, @Param("param") ActivityUserTaskRecordSearchValidate activityUserTaskRecordSearchValidate);

    ActivityUserTaskRecordListedVo getDetailById(@Param("id") Long id);

    @Select("select count(id) from la_activity_user_task_record where activity_id =#{activityId} and user_id = #{userId} and is_delete = 0 and create_time between #{startTime} and #{endTime}")
    int countUserRecordByDate(@Param("activityId") Long activityId, @Param("userId") Long userId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

}
