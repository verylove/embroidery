package cn.wind.common.utils;

import cn.hutool.core.codec.BCD;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

/**
 * @author xukk
 * @date 2018/5/4.
 */

public class RSAUtil {
    private RSA rsa;
    private String privateKey;
    private String publicKey;
    private KeyType keyType;
    private String data;
    private String encodeData;
    public String encrypt() {
        String encode=rsa.encryptStr(data,keyType);
        byte[] bytes= StrUtil.bytes(encode, CharsetUtil.CHARSET_UTF_8);
        bytes= BCD.ascToBcd(bytes);
        return Base64.encodeUrlSafe(bytes);
    }
    public String decrypt(){

        try {
            String encode= BCD.bcdToStr(Base64.decode(encodeData));
            return StrUtil.str(rsa.decryptStr(encode,keyType), CharsetUtil.CHARSET_UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public RSAUtil privateKey(String privateKey){
        this.privateKey=privateKey;
        return this;
    }
    public RSAUtil data(String data){
        this.data=data;
        return this;
    }
    public RSAUtil encodeData(String encodeData){
        this.encodeData=encodeData;
        return this;
    }
    public RSAUtil publicKey(String publicKey){
        this.publicKey=publicKey;
        return this;
    }
    public RSAUtil keyType(KeyType keyType){
        this.keyType=keyType;
        return this;
    }
    public RSAUtil rsa(){
        this.rsa= SecureUtil.rsa(privateKey,publicKey);
        return this;
    }
    public static RSAUtil build(){
        return new RSAUtil();
    }
    public  RSAUtil initKeys(){
        this.rsa= SecureUtil.rsa();
        rsa.initKeys();
        return this;
    }
    public String getPrivateKeyBase64(){
        return this.rsa.getPrivateKeyBase64();
    }
    public String getPublicKeyBase64(){
        return this.rsa.getPublicKeyBase64();
    }
    public static void main(String[] args) {
        //RSAUtil rsaUtil=RSAUtil.build().initKeys();
        System.err.println("公钥加密——私钥解密");
        String data = "{'tax_territoriality': '浙江杭州', 'tax_company': '浙江瑞登资产管理有限公司','tax_invoice_type': '增值税普通发票','tax_payer_type': '小规模','tax_payer_code': '91330106341878268F','tax_rate': '3%'}";
        String privateKey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALN1fn6m2+nh6RDyPc4LNeRkUICp2aeGuA+XvKRXFhU8vlJSCud3jNnDV93OZ8O6WmSSsepCJBTzAyrtOb+5fF4wSekF/zV8z0+OMxvEuHovwMgTvpYsLt3t3u8h+aLrXyo63iHevbxstiBRymUuwZFjR6PKHWSeaCqL8s9a/0JtAgMBAAECgYBKZuUJ114HinzZZMVQ/n7acOibX0/4z7bR6CPyspRaO7PgZ1lT+9Gumvl74U5N7J37YoJW0BMriFEA5Bd0MI+pvhlt9qbKYbykV6giZP4MIttn66u2UWoUBGtGLeOIlJFcXwF3Uckg6NlY/hWjpfZi6llfwpaur3ORWa/Rt9mpNQJBAPLFkUB3BaoxGQ0DHN2s37359MlNm6v0KuiXRGklEi19SRkS1U7vtQLbRF+ytITub1RFtEg7XhsD4Qy2/2Ir0dcCQQC9PMYxyA6T3livJIAovUwkA2tKyLIX1PfLjydooAs3Anq94wUmxpHbl7O14ZezB46g7gkikVWgfksMmM1FYk1bAkEAm/x/iLkxBZqIawjt+i0CkfFR7CoWdRTsoQfYp+pu1JWkaxzju9VfK9exBgAv5x3AoJgs7yBeJeOHLYFDdFONfwJAdtifRI53EUjX+4756hwQoKGBFaN7rdnzkeUjA+NXh8HH4k+cYaDd48Kfe7/lbOt3GPzFwe7yjuwiKBQi/stg9wJBAJwuXbk7PmUvYohKuXyT5u+VFZNvGfUQ00/uI2OU8SP55XdGA/VL6WRWiIHAIxKllHGL9txwYacU6mAZl507DAA=";
        String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCzdX5+ptvp4ekQ8j3OCzXkZFCAqdmnhrgPl7ykVxYVPL5SUgrnd4zZw1fdzmfDulpkkrHqQiQU8wMq7Tm/uXxeMEnpBf81fM9PjjMbxLh6L8DIE76WLC7d7d7vIfmi618qOt4h3r28bLYgUcplLsGRY0ejyh1knmgqi/LPWv9CbQIDAQAB";
        System.out.println("\r加密前文字：\r\n" +data);
        RSAUtil rsaUtil= RSAUtil.build().privateKey(privateKey).publicKey(publicKey).rsa();
        String encodeStr=rsaUtil.data(data).keyType(KeyType.PublicKey).encrypt();
        System.out.println("\r加密后文字: \r\n"+encodeStr);
        String decodeStr=rsaUtil.encodeData(encodeStr).keyType(KeyType.PrivateKey).decrypt();
        System.out.println("\r解密后文字: \r\n"+decodeStr);
    }
}
