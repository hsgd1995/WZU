package com.hxbd.clp.domain.basedata.base;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 企业获得投资、补助情况
 */
public class ProjectSubsidy implements Serializable {
    private Integer id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ttime;//投资、补助时间

    private String fromm;//投资方名称、补助部门

    private Double money;//金额（万元）

    private Integer subsidyFiedId;//投资、补助字段表id

    private Integer projectId;//企业id
    
    private Integer type;//类型

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Date getTtime() {
		return ttime;
	}

	public void setTtime(Date ttime) {
		this.ttime = ttime;
	}



    public String getFromm() {
		return fromm;
	}

	public void setFromm(String fromm) {
		this.fromm = fromm;
	}

	public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Integer getSubsidyFiedId() {
        return subsidyFiedId;
    }

    public void setSubsidyFiedId(Integer subsidyFiedId) {
        this.subsidyFiedId = subsidyFiedId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
    
    

    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ProjectSubsidy [id=" + id + ", ttime=" + ttime + ", fromm=" + fromm + ", money=" + money
				+ ", subsidyFiedId=" + subsidyFiedId + ", projectId=" + projectId + ", type=" + type + "]";
	}




	
}