package com.example.activitydemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.activitydemo.domain.ActivityTaskRelation;
import com.example.activitydemo.validate.ActivityTaskRelationSearchValidate;
import com.example.activitydemo.vo.ActivityTaskRelationListedVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 活动与任务关联Mapper
 *
 * @author fei
 */
@Mapper
public interface ActivityTaskRelationMapper extends BaseMapper<ActivityTaskRelation> {

    IPage<ActivityTaskRelationListedVo> list(Page<ActivityTaskRelation> page, @Param("param") ActivityTaskRelationSearchValidate activityTaskRelationSearchValidate);

    @Update("update la_activity_task_relation set is_delete=1 where activity_id=#{activityId}")
    void deleteTask(@Param("activityId") Long activityId);

    @Select("select task_item_id from la_activity_task_relation where activity_id =#{activityId} and is_delete = 0 order by sorts asc")
    List<Long> getItemTaskList(@Param("activityId") Long activityId);

    @Update("update la_activity_task_relation set is_delete=1 where task_item_id=#{taskId}")
    void deleteRelation(@Param("taskId") Long taskId);

    Integer countNumByActivityId(@Param("activityId") Long activityId);

    ActivityTaskRelation getNotCompleteTask(@Param("activityId") Long activityId, @Param("userId") Long userId);

}
