package rw.adrielsoft.wisdom.main.sr.dto;

import java.util.List;

public class StudentRegistrationRequest {
    private String fname;
    private String mName;
    private String fPhone;
    private String mPhone;
    private String fNid;
    private String mNid;
    private String name;
    private String otherName;
    private String gender;
    private String insurance;
    private String healthMood;
    private String branch;
    private String term;
    private String studyClass;
    private String studyMood;
    private String country;
    private String city;
    private String phoneNo;
    private String totalDue;
    private String cId;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOtherName() {
		return otherName;
	}
	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getInsurance() {
		return insurance;
	}
	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}
	public String getStudyMood() {
		return studyMood;
	}
	public void setStudyMood(String studyMood) {
		this.studyMood = studyMood;
	}
	public String getStudyClass() {
		return studyClass;
	}
	public void setStudyClass(String studyClass) {
		this.studyClass = studyClass;
	}
	public String getTotalDue() {
		return totalDue;
	}
	public void setTotalDue(String totalDue) {
		this.totalDue = totalDue;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public String getfPhone() {
		return fPhone;
	}
	public void setfPhone(String fPhone) {
		this.fPhone = fPhone;
	}
	public String getmPhone() {
		return mPhone;
	}
	public void setmPhone(String mPhone) {
		this.mPhone = mPhone;
	}
	public String getfNid() {
		return fNid;
	}
	public void setfNid(String fNid) {
		this.fNid = fNid;
	}
	public String getmNid() {
		return mNid;
	}
	public void setmNid(String mNid) {
		this.mNid = mNid;
	}
	public String getHealthMood() {
		return healthMood;
	}
	public void setHealthMood(String healthMood) {
		this.healthMood = healthMood;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	

	public String getcId() {
		return cId;
	}
	public void setcId(String cId) {
		this.cId = cId;
	}
	@Override
	public String toString() {
		return "StudentRegistrationRequest [fname=" + fname + ", mName=" + mName + ", fPhone=" + fPhone + ", mPhone="
				+ mPhone + ", fNid=" + fNid + ", mNid=" + mNid + ", name=" + name + ", otherName=" + otherName
				+ ", gender=" + gender + ", insurance=" + insurance + ", healthMood=" + healthMood + ", branch="
				+ branch + ", term=" + term + ", studyClass=" + studyClass + ", studyMood=" + studyMood + ", country="
				+ country + ", city=" + city + ", phoneNo=" + phoneNo + ", totalDue=" + totalDue + "]";
	}

    
}
