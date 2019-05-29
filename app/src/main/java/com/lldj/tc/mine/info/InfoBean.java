package com.lldj.tc.mine.info;

import android.content.Context;
import android.text.TextUtils;

import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.Sharepre.SharedPreferencesUtil;

/**
 * description: <p>
 * user: lenovo<p>
 * Creat Time: 2019/1/14 15:11<p>
 * Modify Time: 2019/1/14 15:11<p>
 */


public class InfoBean {
    private String id;
    private String userName;
    private String nickName;
    private String photoUrl;
    private String phone;
    private String password;
    private String salt;
    private String icCardNo;
    private String professionalId;
    private String professionalNo;
    //职业
    private String professionalName;
    private String departmentId;
    private String departmentName;
    //职称
    private String professionalRank;
    private String skills;
    private String isApprove;
    private String isPush;
    private String  token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        if (TextUtils.isEmpty(userName)) {
            userName = "";
        }
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        if (TextUtils.isEmpty(nickName)) {
            nickName = "";
        }
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhone() {
        if (TextUtils.isEmpty(phone)) {
            phone = "";
        }
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getIcCardNo() {
        return icCardNo;
    }

    public void setIcCardNo(String icCardNo) {
        this.icCardNo = icCardNo;
    }

    public String getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(String professionalId) {
        this.professionalId = professionalId;
    }

    public String getProfessionalNo() {
        if (TextUtils.isEmpty(professionalNo)) {
            professionalNo = "";
        }
        return professionalNo;
    }

    public void setProfessionalNo(String professionalNo) {
        this.professionalNo = professionalNo;
    }

    public String getProfessionalName() {
        if (TextUtils.isEmpty(professionalName)) {
            professionalName = "";
        }
        return professionalName;
    }

    public void setProfessionalName(String professionalName) {
        this.professionalName = professionalName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        if (TextUtils.isEmpty(departmentName)) {
            departmentName = "";
        }
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getProfessionalRank() {
        if (TextUtils.isEmpty(professionalRank)) {
            professionalRank = "";
        }
        return professionalRank;
    }

    public void setProfessionalRank(String professionalRank) {
        this.professionalRank = professionalRank;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getIsApprove() {
        return isApprove;
    }

    public void setIsApprove(String isApprove) {
        this.isApprove = isApprove;
    }

    public String getIsPush() {
        return isPush;
    }

    public void setIsPush(String isPush) {
        this.isPush = isPush;
    }

    public void setUserInfo(Context pContext) {
        SharePreUtils.setUserName(pContext, getUserName());
        SharePreUtils.setNickName(pContext, getNickName());
        SharePreUtils.setDepartment(pContext, getDepartmentName());
//        SharePreUtils.setJob(pContext, getProfessionalName());
//        SharePreUtils.setJobNum(pContext, getProfessionalNo());
        SharePreUtils.setJobTitle(pContext, getProfessionalRank());
        SharedPreferencesUtil.setValue(pContext,"token",token);
    }
}
