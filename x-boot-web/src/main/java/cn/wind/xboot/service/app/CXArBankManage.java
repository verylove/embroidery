package cn.wind.xboot.service.app;

import cn.wind.db.ar.entity.ArUserBank;
import cn.wind.db.ar.service.IArUserBankService;
import cn.wind.xboot.dto.app.ar.arUserBankDto;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/24 17:38
 * @Description:
 */
@Service
public class CXArBankManage {

    @Autowired
    private IArUserBankService userBankService;

    @Transactional
    public void delete(Long userId, Long bankId)throws Exception {
        ArUserBank bank = userBankService.selectById(bankId);
        if(bank!=null){
            bank.setStatus(0);
            if(bank.getType()==1){
                bank.setType(0);
            }
            userBankService.updateById(bank);
            Map<String,Object> map = Maps.newHashMap();
            map.put("userId",userId);
            map.put("status",1);
            List<ArUserBank> banks = userBankService.findAllByConditions(map);

            if(banks!=null){
                ArUserBank bank1 = banks.get(0);
                bank1.setType(1);
                userBankService.updateById(bank1);
            }
        }
    }

    @Transactional
    public void defaultBankCard(Long bankId, Long userId)throws Exception {
        ArUserBank bank2 = userBankService.selectById(bankId);
        if(bank2==null){
            throw new Exception();
        }

        Map<String,Object> map = Maps.newHashMap();
        map.put("userId",userId);
        map.put("status",1);
        map.put("type",1);
        List<ArUserBank> banks = userBankService.findAllByConditions(map);
        ArUserBank bank = banks.get(0);
        bank.setType(0);
        userBankService.updateById(bank);

        bank2.setType(1);
        userBankService.updateById(bank2);
    }

    @Transactional
    public void addBankCard(arUserBankDto dto, Long userId)throws Exception {
        Map<String,Object> map = Maps.newHashMap();
        map.put("userId",userId);
        map.put("status",1);
        map.put("type",1);
        List<ArUserBank> banks = userBankService.findAllByConditions(map);
        ArUserBank bank = new ArUserBank();
        bank.setUserId(userId);
        bank.setBankCard(dto.getBankCard());
        bank.setIdentityCard(dto.getIdentityCard());
        bank.setBankName(dto.getBankName());
        bank.setBranchName(dto.getBranchName());
        if(banks==null || banks.size()<1){//无银行卡
            bank.setType(1);
        }else {
            bank.setType(0);
        }
        userBankService.insert(bank);
    }
}
