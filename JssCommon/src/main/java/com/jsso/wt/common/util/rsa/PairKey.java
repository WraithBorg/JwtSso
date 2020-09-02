package com.jsso.wt.common.util.rsa;

import org.springframework.core.io.ClassPathResource;
import sun.misc.BASE64Decoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 根据文件生成非对称密钥
 */
public class PairKey {
    //公钥路径  src/main/resources/ec/publickey.ec
    private static final String PUBLIC_URI = "rsa/publickey.rsa";
    //私钥路径  src/main/resources/ec/privatekey.ec
    private static final String PRIVATE_URI = "rsa/privatekey.rsa";
    //加密类型 可以换为EC
    private static final String ALGORITHM_TYPE = "RSA";
    //存放RSA密钥对
    public static KeyPair keyPair;      //注意这里嗷，这里是非对称加密的容器

    static {
        try{
            ClassPathResource publicCpr = new ClassPathResource(PUBLIC_URI);
            ClassPathResource privateCpr = new ClassPathResource(PRIVATE_URI);
            //初始化数据
            FileInputStream publicInputStream =
                    new FileInputStream( publicCpr.getFile());
            //读取公钥
            X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(
                    new BASE64Decoder().decodeBuffer(publicInputStream));
            publicInputStream.close();
            //读取私钥
            FileInputStream privateInputStream =
                    new FileInputStream(privateCpr.getFile());
            PKCS8EncodedKeySpec bobPriKeySpec = new PKCS8EncodedKeySpec(
                    new BASE64Decoder().decodeBuffer(privateInputStream));
            privateInputStream.close();
            // 密钥工厂
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_TYPE);
            // 取公钥对象
            PublicKey publicKey = keyFactory.generatePublic(bobPubKeySpec);
            // 取私钥对象
            PrivateKey privateKey = keyFactory.generatePrivate(bobPriKeySpec);
            // 放入容器
            keyPair = new KeyPair(publicKey,privateKey);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }
}
