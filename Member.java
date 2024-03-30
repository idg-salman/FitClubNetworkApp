/*
 **********************************************************************************************************
 * Author:      Md Afsar Uddin Salman
 * Student ID:  12190848
 * File Name:   Member.java
 * Date:        26-Mar-2024
 * Purpose:     This file contains the Member class, which is used to define the structure
 *              of the members information. Also, it is responsible for the validation of the
 *              member's information.
 **********************************************************************************************************
 */

import java.io.Serializable;

public class Member implements Serializable {

  private String firstName;
  private String lastName;
  private String address;
  private String phoneNumber;

  public Member() {
    this.firstName = "";
    this.lastName = "";
    this.address = "";
    this.phoneNumber = "";
  }

  public Member(
    String firstName,
    String lastName,
    String address,
    String phoneNumber
  ) {
    this.setFirstName(firstName);
    this.setLastName(lastName);
    this.setAddress(address);
    this.setPhoneNumber(phoneNumber);
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    if (firstName == null || firstName.trim().equals("")) {
      throw new IllegalArgumentException("First name cannot be empty");
    } else if (!firstName.trim().matches("[a-zA-Z ]+")) {
      throw new IllegalArgumentException("First name must be all letters");
    } else if (firstName.trim().length() < 2) {
      throw new IllegalArgumentException(
        "First name must be at least 2 characters"
      );
    } else if (firstName.trim().length() > 22) {
      throw new IllegalArgumentException(
        "First name must be at most 22 characters"
      );
    }

    this.firstName = firstName.trim();
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    if (lastName == null || lastName.trim().equals("")) {
      throw new IllegalArgumentException("Last name cannot be empty");
    } else if (!lastName.trim().matches("[a-zA-Z ]+")) {
      throw new IllegalArgumentException("Last name must be all letters");
    } else if (lastName.trim().length() < 2) {
      throw new IllegalArgumentException(
        "Last name must be at least 2 characters"
      );
    } else if (lastName.trim().length() > 22) {
      throw new IllegalArgumentException(
        "Last name must be at most 22 characters"
      );
    }

    this.lastName = lastName.trim();
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    if (address == null || address.trim().equals("")) {
      throw new IllegalArgumentException("Address cannot be empty");
    } else if (address.trim().length() < 10) {
      throw new IllegalArgumentException(
        "Address must be at least 10 characters"
      );
    } else if (address.trim().length() > 120) {
      throw new IllegalArgumentException(
        "Address must be at most 120 characters"
      );
    }

    this.address = address.trim();
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    if (phoneNumber == null || phoneNumber.trim().equals("")) {
      throw new IllegalArgumentException("Phone number cannot be empty");
    } else if (phoneNumber.trim().length() != 10) {
      throw new IllegalArgumentException("Phone number must be 10 digits");
    } else if (!phoneNumber.trim().matches("[0-9]+")) {
      throw new IllegalArgumentException("Phone number must be all digits");
    }

    this.phoneNumber = phoneNumber;
  }
}
