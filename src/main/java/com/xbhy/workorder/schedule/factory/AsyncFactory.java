package com.xbhy.workorder.schedule.factory;

import com.xbhy.workorder.service.OrderLogService;
import com.xbhy.workorder.service.StaffSessionService;
import com.xbhy.workorder.shiro.session.OnlineSession;
import com.xbhy.workorder.util.ServletUtils;
import com.xbhy.workorder.util.ShiroUtils;
import com.xbhy.workorder.util.SpringUtils;
import com.xbhy.workorder.vo.OrderLogVO;
import com.xbhy.workorder.vo.StaffSessionVO;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;

import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 * 
 * @author
 *
 */
@Slf4j
public class AsyncFactory
{

    /**
     * 同步session到数据库
     * 
     * @param session 在线用户会话
     * @return 任务task
     */
    public static TimerTask syncSessionToDb(final OnlineSession session)
    {
        StaffSessionVO sysUserOnline =  SpringUtils.getBean(StaffSessionService.class).queryBySessionId(String.valueOf(session.getId()));
        if(sysUserOnline != null)
            return new TimerTask() {
                @Override
                public void run() {

                }
            };
        return new TimerTask()
        {
            @Override
            public void run()
            {
                StaffSessionVO online = new StaffSessionVO();
                online.setSessionId(String.valueOf(session.getId()));
                online.setLoginName(session.getLoginName());
                online.setStartTimestamp(session.getStartTimestamp());
                online.setLastAccessTime(session.getLastAccessTime());
                online.setExpireTime(session.getTimeout());
                online.setIpaddr(session.getHost());
                online.setBrowser(session.getBrowser());
                online.setStaffId(session.getStaffId());
                online.setOs(session.getOs());
                online.setOnlineStatus(session.getStatus().getCode());
                SpringUtils.getBean(StaffSessionService.class).insert(online);

            }
        };
    }

    /**
     * 操作日志记录
     * 
     * @param orderLogVO 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOper(final OrderLogVO orderLogVO)
    {
        return new TimerTask()
        {
            @Override
            public void run()
            {
                // 远程查询操作地点
                //orderLogVO.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
                SpringUtils.getBean(OrderLogService.class).insert(orderLogVO);
            }
        };
    }


}
