package com.xbhy.workorder.shiro.service;


import com.xbhy.workorder.service.StaffSessionService;
import com.xbhy.workorder.shiro.session.OnlineSession;
import com.xbhy.workorder.vo.StaffSessionVO;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Optional;

/**
 * 会话db操作处理
 * 
 * @author
 */
@Component
public class SysShiroService
{
    @Autowired
    private StaffSessionService staffSessionService;

    /**
     * 删除会话
     *
     * @param staffSessionVO 会话信息
     */
    public void deleteSession(StaffSessionVO staffSessionVO)
    {
        staffSessionService.deleteById(staffSessionVO.getId());
    }

    /**
     * 获取会话信息
     *
     * @param sessionId
     * @return
     */
    public Session getSession(String sessionId)
    {
        StaffSessionVO userOnline = staffSessionService.queryBySessionId(sessionId);
        return Optional.ofNullable(userOnline).isPresent() ? null : createSession(userOnline);
    }

    public Session createSession(StaffSessionVO userOnline)
    {
        OnlineSession onlineSession = new OnlineSession();
        if (Optional.ofNullable(userOnline).isPresent())
        {
            onlineSession.setId(userOnline.getSessionId());
            onlineSession.setBrowser(userOnline.getBrowser());
            onlineSession.setOs(userOnline.getOs());
            onlineSession.setLoginName(userOnline.getLoginName());
            onlineSession.setStartTimestamp(userOnline.getStartTimestamp());
            onlineSession.setLastAccessTime(userOnline.getLastAccessTime());
            onlineSession.setTimeout(userOnline.getExpireTime());
        }
        return onlineSession;
    }
}
