package com.zhao.bank1.bank2.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Admin
 */
@Data
public class AccountInfo implements Serializable {
	private Long id;
	private String accountName;
	private String accountNo;
	private String accountPassword;
	private Double accountBalance;

}
