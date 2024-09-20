package com.myworkbench.form;

import java.sql.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class TaskCreate {

	private UUID uid;
	
	private String tagCd;

	private String classCd;

	private String title;

	private String titleShort;

	private String details;

	private Date plannedStartDate;

	private Date plannedEndDate;

	private Date startDate;

	private Date endDate;

	private String[] processuId = {"","","","","","","","","","","","","","","","","","","",""};

	private Integer[] num = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

	private String[] processTitle = {"","","","","","","","","","","","","","","","","","","",""};

	private String[] link = {"","","","","","","","","","","","","","","","","","","",""};

}
