package cn.wind.xboot.service.web;

import cn.wind.common.res.ApiRes;
import cn.wind.db.rc.entity.RcSecondTransactions;
import cn.wind.db.rc.service.IRcSecondTransactionsService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/9/6 10:19
 * @Description:
 */
@Service
public class WebSecondTransactManage {

    @Autowired
    private IRcSecondTransactionsService secondTransactionsService;

    @Transactional
    public ApiRes secondTransactDelete(Long secondTransactId) {

        Map<String,Object> map = Maps.newHashMap();
        map.put("SecondTransactId",secondTransactId);
        RcSecondTransactions transactions = secondTransactionsService.findOneByConditions(map);

        if(transactions != null){
            transactions.setStatus(0);
            secondTransactionsService.updateById(transactions);
        }

        return ApiRes.Custom().success();
    }
}
