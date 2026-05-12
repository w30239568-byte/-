package com.example.activitydemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.activitydemo.domain.ActivityTaskItem;
import com.example.activitydemo.validate.ActivityTaskItemSearchValidate;
import com.example.activitydemo.vo.ActivityTaskItemListedVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 任务列Mapper
 *
 * @author fei
 */
@Mapper
public interface ActivityTaskItemMapper extends BaseMapper<ActivityTaskItem> {

    IPage<ActivityTaskItemListedVo> list(Page<ActivityTaskItem> page, @Param("param") ActivityTaskItemSearchValidate activityTaskItemSearchValidate);
}
