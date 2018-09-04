package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserMessage;
import cn.wind.db.ar.dao.ArUserMessageMapper;
import cn.wind.db.ar.service.IArUserMessageService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户消息数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-08-30
 */
@Service
public class ArUserMessageServiceImpl extends ServiceImpl<ArUserMessageMapper, ArUserMessage> implements IArUserMessageService {

}
