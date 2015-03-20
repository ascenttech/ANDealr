package com.ascentsmartwaves.andealr.data;

/**
 * Created by ADMIN on 11-03-2015.
 */
public class MerchantProfileData
{
    String firstName;
    String lastName;
    String contactNo;
    String alternateNo;
    String emailID;
    String dateOfBirth;
    String merchantHandle;
    String gender;
    String photo;

    public MerchantProfileData(String firstName,String lastName,String contactNo,String alternateNo,String emailID,String dateOfBirth,String merchantHandle,String gender,String photo)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNo = contactNo;
        this.alternateNo = alternateNo;
        this.emailID = emailID;
        this.dateOfBirth = dateOfBirth;
        this.merchantHandle = merchantHandle;
        this.gender = gender;
        this.photo = photo;
    }


    public String getPhoto() {
        return photo;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public String getAlternateNo() {
        return alternateNo;
    }

    public String getEmailID() {
        return emailID;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getMerchantHandle() {
        return merchantHandle;
    }

    public String getGender() {
        return gender;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMerchantHandle(String merchantHandle) {
        this.merchantHandle = merchantHandle;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public void setAlternateNo(String alternateNo) {
        this.alternateNo = alternateNo;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }



}
