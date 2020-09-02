package com.jsso.wt.common.util.rsa;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;

import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

/**
 * 创建非对称密钥，并写入文件
 */
public class CreatRsaKey {
    //密钥长度 于原文长度对应 以及越长速度越慢 必须大于 512
    private final static int KEY_SIZE = 2048;

    public static void main(String[] args) throws Exception {
        //一对密钥算法生成
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom();
        //设置随机种子
        secureRandom.setSeed(32);
        keyPairGen.initialize(KEY_SIZE,secureRandom);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 得到密钥字符串
        byte[] publicKeyBytes = Base64.getEncoder().encode(publicKey.getEncoded());
        byte[] privateKeyBytes = Base64.getEncoder().encode(privateKey.getEncoded());
        //保存到文件中
        Resource publicKeyResource = new FileSystemResource("src/main/resources/rsa/publickey.rsa");
        FileOutputStream publicKeyOutputStream = new FileOutputStream(publicKeyResource.getFile());
        publicKeyOutputStream.write(publicKeyBytes);
        publicKeyOutputStream.close();
        //保存到文件中
        Resource privateKeyResource = new FileUrlResource("src/main/resources/rsa/privatekey.rsa");
        FileOutputStream privateKeyOutputStream = new FileOutputStream(privateKeyResource.getFile());
        privateKeyOutputStream.write(privateKeyBytes);
        privateKeyOutputStream.close();

    }
}
