package cn.wind.xboot.handler;

import cn.wind.auth.domain.UserInfo;
import cn.wind.common.utils.IpInfoUtil;
import cn.wind.common.utils.ThreadPoolUtil;
import cn.wind.db.sys.entity.SysLog;
import cn.wind.db.sys.service.ISysLogService;
import cn.wind.klog.annotation.Log;
import cn.wind.klog.handlers.LogHandler;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * <p>Title: MyLogAroundHandler</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/6/29
 */
@Service
public class MyLogHandler implements LogHandler<Log,Object>{
    private static final ThreadLocal<Date> beginTimeThreadLocal = new NamedThreadLocal<Date>("ThreadLocal beginTime");
    @Autowired(required = false)
    private HttpServletRequest request;
    @Autowired
    private ISysLogService sysLogService;
    public static final Logger logger = org.slf4j.LoggerFactory.getLogger(MyLogHandler.class);

    @Override
    public Object handlerBefore(Log log, Object[] args) {
                //线程绑定变量（该数据只有当前请求的线程可见）
        Date beginTime=new Date();
        beginTimeThreadLocal.set(beginTime);
        return null;
    }

    @Override
    public Object handlerAfter(Log log, Object[] args) {
        Object object= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserInfo user=null;
        if(object!=null){
            if(object instanceof UserInfo){
                user = (UserInfo) object;
            }
        }
        SysLog sysLog=new SysLog();
        //日志标题
        sysLog.setName(log.value());
        //日志请求url
        sysLog.setRequestUrl(request.getRequestURI());
        //请求方式
        sysLog.setRequestType(request.getMethod());
        //请求参数
        Map<String,String[]> logParams=request.getParameterMap();
        sysLog.setMapToParams(logParams);
        //请求用户
        sysLog.setUsername(user==null?"":user.getUsername());
        //请求IP
        sysLog.setIp(IpInfoUtil.getIpAddr(request));
        sysLog.setDelFlag(0);
        //IP地址
        sysLog.setIpInfo(IpInfoUtil.getIpCity(IpInfoUtil.getIpAddr(request)));
        sysLog.setCreateBy(""+(user==null?"":user.getUserId()));
        sysLog.setModifyBy(sysLog.getCreateBy());
        sysLog.setCreateTime(new Date());
        sysLog.setModifyTime(sysLog.getCreateTime());
        //请求开始时间
        Date logStartTime = beginTimeThreadLocal.get();

        long beginTime = beginTimeThreadLocal.get().getTime();
        long endTime = System.currentTimeMillis();
        //请求耗时
        Long logElapsedTime = endTime - beginTime;
        sysLog.setCostTime(logElapsedTime.intValue());

        //调用线程保存至ES
        ThreadPoolUtil.getPool().execute(new SaveLogThread(sysLog,sysLogService ));
        return null;
    }

    @Override
    public Object handlerError(Log log, Object[] args, Throwable error) {
        return null;
    }
}
