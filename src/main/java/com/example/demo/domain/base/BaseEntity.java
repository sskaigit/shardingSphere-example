package com.example.demo.domain.base;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@MappedSuperclass
public class BaseEntity implements Serializable{

	private static final long serialVersionUID = 6919494151434472722L;

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    protected Long id;

    protected String createUser;

    protected Date createDate;

    protected Date updateDate;
    
    protected Integer sequence;

    protected String description;

    protected Boolean enable;
    
    @PrePersist
    public void prePersist() {
    	Boolean enable = this.getEnable();
    	if(enable == null) {
    		this.setEnable(true);
    	}
    	Date createDate = this.getCreateDate();
    	if(createDate == null) {
    		this.setCreateDate(Calendar.getInstance().getTime());
    	}
    	Date updateDate = this.getUpdateDate();
    	if(updateDate == null) {
    		this.setUpdateDate(Calendar.getInstance().getTime());
    	}
    }
    
    @PreUpdate
    public void preUpdate() {
    	Date updateDate = this.getUpdateDate();
    	if(updateDate == null) {
    		this.setUpdateDate(Calendar.getInstance().getTime());
    	}
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

    public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
}