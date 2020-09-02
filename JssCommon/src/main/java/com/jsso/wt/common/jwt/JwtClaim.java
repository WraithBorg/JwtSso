package com.jsso.wt.common.jwt;

import com.jsso.wt.common.util.CustomUtil;

import java.util.Map;

import static com.jsso.wt.common.constant.CConst.*;

/**
 * Jwt claim
 */
public class JwtClaim {
    private String id;
    private String account;//唯一登录账号
    private String password;

    /************************************* Constructor *************************************/
    public JwtClaim() {
    }

    public JwtClaim(String id, String account, String password) {
        this.id = id;
        this.account = account;
        this.password = password;
    }

    /************************************ Getter Setter ************************************/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, Object> getMap() {
        return CustomUtil.ofMap(JWT_CID, this.getId(),
                JWT_ACCOUNT, this.getAccount(), JWT_PASSWORD, this.getPassword());
    }

    @Override
    public String toString() {
        return "JwtClaim{" +
                "id='" + id + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
