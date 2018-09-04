package cn.wind.xboot.tencent.thread;

import cn.wind.db.ar.entity.ArUser;
import cn.wind.db.ar.entity.ArUserMessage;
import cn.wind.db.ar.service.impl.ArUserMessageServiceImpl;
import cn.wind.db.ar.service.impl.ArUserServiceImpl;
import cn.wind.xboot.service.PushManage;
import cn.wind.xboot.tencent.utils.SpringContextUtil;
import cn.wind.xboot.vo.PushVo;
import com.google.common.collect.Lists;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/8/31 11:27
 * @Description: 我的赞 消息推送
 */
public class MyGreatThread {

    private ArUserMessageServiceImpl messageService;

    private ArUserServiceImpl userService;

    private boolean completed = false;

    /**
     * @param fromUserId 执行操作的人
     * @param toUserId 收到消息推送的人
     * @param evaluate_id 发布的内容ID
     * @param category 1-特价纹身 2-图库找图 3-贴吧话题 4-纹纹达人 5-动态 6-作品 7-特价纹身评论 8-图库找图评论 9-贴吧话题评论 10-纹纹达人评论 11-动态评论 12-作品评论
     */
    public synchronized void myGreatThread(Long fromUserId, Long toUserId, Long evaluate_id, Integer category, String extraContent, Long extraId){
        System.out.println("线程任务");
        if(this.completed){
            return;
        }
        try{
            messageService = (ArUserMessageServiceImpl) SpringContextUtil.getBean("arUserMessageServiceImpl");
            userService = (ArUserServiceImpl) SpringContextUtil.getBean("arUserServiceImpl");

            ArUser user = userService.selectById(fromUserId);
            ArUser user2 = userService.selectById(toUserId);

            String tattooContent = utils.getTattooContent(category);

            tattooContent = user.getAccount()+"赞了您的"+tattooContent;
            PushVo vo = new PushVo();
            vo.setALIAS_LIST(Lists.newArrayList(user2.getPhone()));
            vo.setTITLE("系统消息");
            vo.setMSG_CONTENT("系统消息");
            vo.setALERT(tattooContent);
            vo.setType(1);
//            Map map= Maps.newHashMap();
//            map.put("type","1");
//            vo.setExtra(map);
            PushManage.sendPushWithCallback(vo);

            //2.保存消息
            ArUserMessage userMessage = new ArUserMessage();
            userMessage.setFromUserId(fromUserId);
            userMessage.setToUserId(toUserId);
            userMessage.setType(3);
            userMessage.setTitle(tattooContent);
            userMessage.setContent(tattooContent);
            userMessage.setCategory(category);
            userMessage.setEvaluateId(evaluate_id);
            userMessage.setExtraId(extraId);
            userMessage.setExtraContent(extraContent);
            messageService.insert(userMessage);
        }catch (Throwable t){

        }
        this.completed = true;
    }

    public boolean isCompleted(){
        return this.completed;
    }
}
