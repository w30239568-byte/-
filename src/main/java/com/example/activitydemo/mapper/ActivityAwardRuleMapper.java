package com.example.activitydemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.activitydemo.domain.ActivityAwardRule;
import com.example.activitydemo.validate.ActivityAwardRuleSearchValidate;
import com.example.activitydemo.vo.ActivityAwardRuleListedVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 奖励规则Mapper
 *
 * @author fei
 */
@Mapper
public interface ActivityAwardRuleMapper extends BaseMapper<ActivityAwardRule> {

    IPage<ActivityAwardRuleListedVo> list(Page<ActivityAwardRule> page, @Param("param") ActivityAwardRuleSearchValidate activityAwardRuleSearchValidate);

    @Update("update la_activity_award_rule set is_delete=1  where activity_award_id=#{awardId}")
    void deleteByAwardId(@Param("awardId") Long awardId);

    @Select("select * from la_activity_award_rule where  is_delete = 0")
    List<ActivityAwardRule> getAllRules();
}
