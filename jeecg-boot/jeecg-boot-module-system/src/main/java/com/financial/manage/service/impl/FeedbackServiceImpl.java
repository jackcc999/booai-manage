package com.financial.manage.service.impl;

import com.financial.manage.entity.Feedback;
import com.financial.manage.mapper.FeedbackMapper;
import com.financial.manage.service.IFeedbackService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 用户反馈
 * @Author: jeecg-boot
 * @Date:   2025-09-21
 * @Version: V1.0
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements IFeedbackService
{

}
