package cn.wind.common.utils;


import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;

import javax.crypto.spec.SecretKeySpec;


/**
 *
 * @author xukk
 * @date 2018/5/4
 */
public class AESUtil {
    private static final String CONSTANT_ALGORITHM="AES/CBC/PKCS5Padding";
    private static final String CONSTANT_KEY="y2W89L6BkRAFljhN";
    private static final String CONSTANT_IV="dMitHORyqbeYVE0o";
    private static final Integer INIT_NUM = 128;
    public static AESUtil util=new AESUtil();
    static {
        util.aes= SecureUtil.aes();
        util.aes.setIv(CONSTANT_IV.getBytes());
        util.aes.init(CONSTANT_ALGORITHM,new SecretKeySpec(CONSTANT_KEY.getBytes(), SymmetricAlgorithm.AES.getValue()));
    }

    private String algorithm;

    private AES aes;

    private  String key;

    private  String iv;

    private String data;

    private String encodeData;

    public static AESUtil build(){
        AESUtil aesUtil=new AESUtil();
        return aesUtil;
    }
    public AESUtil aes(){
        this.aes= SecureUtil.aes();
        this.aes.setIv(iv.getBytes());
        this.aes.init(algorithm,new SecretKeySpec(key.getBytes(), SymmetricAlgorithm.AES.getValue()));
        return this;
    }
    public String encrypt() {
        return Base64.encodeUrlSafe(aes.encrypt(data.getBytes()));
    }
    public String decrypt(){
        return aes.decryptStrFromBase64(encodeData);
    }

    public AESUtil data(String data){
        this.data=data;
        return this;
    }
    public AESUtil encodeData(String encodeData){
        this.encodeData=encodeData;
        return this;
    }
    public AESUtil iv(String iv){
        this.iv=iv;
        return this;
    }
    public AESUtil key(String key){
        this.key=key;
        return this;
    }
    public static void main(String[] args) {
        System.err.println("公钥加密——私钥解密");
        String data = "{'tax_territoriality': '浙江杭州', 'tax_company': '浙江瑞登资产管理有限公司','tax_invoice_type': '增值税普通发票','tax_payer_type': '小规模','tax_payer_code': '91330106341878268F','tax_rate': '3%'}";
        AESUtil rsaUtil=AESUtil.util;
        String encodeStr=rsaUtil.data(data).encrypt();
        System.out.println("\r加密后文字: \r\n"+encodeStr);
        String decodeStr=rsaUtil.encodeData(encodeStr).decrypt();
        System.out.println("\r解密后文字: \r\n"+decodeStr);
    }
}
