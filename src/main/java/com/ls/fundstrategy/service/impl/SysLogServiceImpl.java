package com.ls.fundstrategy.service.impl;

import com.ls.fundstrategy.mapper.SysOperateLogMapper;
import com.ls.fundstrategy.model.database.SysOperateLog;
import com.ls.fundstrategy.service.SuperServiceImpl;
import com.ls.fundstrategy.service.SysLogService;
import org.springframework.stereotype.Service;

@Service
public class SysLogServiceImpl extends SuperServiceImpl<SysOperateLogMapper, SysOperateLog> implements SysLogService {
}
