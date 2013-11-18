package com.tixa.industry.model;

import java.io.Serializable;

import org.json.JSONObject;

/**
 * 招聘信息
 * 
 * @author Administrator
 */

public class RecruitingInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -481346843463538309L;
	private long id;
	private String createTime;//创建时间
	private String CatalogueCode;
	private String PositionCode;//职位
	private String ZoneCode;
	private String Salary;//薪资
	private String Education;//学历
	private String Experience;//经验
	private int JobType;
	private int NeedNumber;
	private int Sex;
	private String languageAbility;
	private String jobRequire;
	private String hiringCompany;
	private String companyProfile;//详细
	private String LinkMan;//联系人
	private String LinkTel;//联系电话
	private String LinkEmail;//联系邮箱
	private int Status;
	private long appID; // appID
	private long enterpriseID;

	public RecruitingInfo() {
	};

	public RecruitingInfo(JSONObject json) {
		this.id = json.optLong("ID");
		this.createTime = json.optString("createTime");
		this.CatalogueCode = json.optString("CatalogueCode");
		this.PositionCode = json.optString("PositionCode");
		this.ZoneCode = json.optString("ZoneCode");
		this.Salary = json.optString("Salary");
		this.Education = json.optString("Education");
		this.Experience = json.optString("Experience");
		this.JobType = json.optInt("Experience");
		this.NeedNumber = json.optInt("NeedNumber");
		this.Sex = json.optInt("Sex");
		this.languageAbility = json.optString("languageAbility");
		this.jobRequire = json.optString("jobRequire");
		this.hiringCompany = json.optString("hiringCompany");
		this.companyProfile = json.optString("companyProfile");
		this.LinkMan = json.optString("LinkMan");
		this.LinkTel = json.optString("LinkTel");
		this.LinkEmail = json.optString("LinkEmail");
		this.Status = json.optInt("Status");
		this.appID = json.optLong("appID");
		this.enterpriseID = json.optLong("enterpriseID");
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCatalogueCode() {
		return CatalogueCode;
	}

	public void setCatalogueCode(String catalogueCode) {
		CatalogueCode = catalogueCode;
	}

	public String getPositionCode() {
		return PositionCode;
	}

	public void setPositionCode(String positionCode) {
		PositionCode = positionCode;
	}

	public String getZoneCode() {
		return ZoneCode;
	}

	public void setZoneCode(String zoneCode) {
		ZoneCode = zoneCode;
	}

	public String getSalary() {
		return Salary;
	}

	public void setSalary(String salary) {
		Salary = salary;
	}

	public String getEducation() {
		return Education;
	}

	public void setEducation(String education) {
		Education = education;
	}

	public String getExperience() {
		return Experience;
	}

	public void setExperience(String experience) {
		Experience = experience;
	}

	public int getJobType() {
		return JobType;
	}

	public void setJobType(int jobType) {
		JobType = jobType;
	}

	public int getNeedNumber() {
		return NeedNumber;
	}

	public void setNeedNumber(int needNumber) {
		NeedNumber = needNumber;
	}

	public int getSex() {
		return Sex;
	}

	public void setSex(int sex) {
		Sex = sex;
	}

	public String getLanguageAbility() {
		return languageAbility;
	}

	public void setLanguageAbility(String languageAbility) {
		this.languageAbility = languageAbility;
	}

	public String getJobRequire() {
		return jobRequire;
	}

	public void setJobRequire(String jobRequire) {
		this.jobRequire = jobRequire;
	}

	public String getHiringCompany() {
		return hiringCompany;
	}

	public void setHiringCompany(String hiringCompany) {
		this.hiringCompany = hiringCompany;
	}

	public String getCompanyProfile() {
		return companyProfile;
	}

	public void setCompanyProfile(String companyProfile) {
		this.companyProfile = companyProfile;
	}

	public String getLinkMan() {
		return LinkMan;
	}

	public void setLinkMan(String linkMan) {
		LinkMan = linkMan;
	}

	public String getLinkTel() {
		return LinkTel;
	}

	public void setLinkTel(String linkTel) {
		LinkTel = linkTel;
	}

	public String getLinkEmail() {
		return LinkEmail;
	}

	public void setLinkEmail(String linkEmail) {
		LinkEmail = linkEmail;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public long getAppID() {
		return appID;
	}

	public void setAppID(long appID) {
		this.appID = appID;
	}

	public long getEnterpriseID() {
		return enterpriseID;
	}

	public void setEnterpriseID(long enterpriseID) {
		this.enterpriseID = enterpriseID;
	};

}
