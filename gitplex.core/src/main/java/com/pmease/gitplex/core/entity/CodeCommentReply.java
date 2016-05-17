package com.pmease.gitplex.core.entity;

import java.util.Date;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLock;

import com.pmease.commons.hibernate.AbstractEntity;
import com.pmease.commons.hibernate.dao.Dao;
import com.pmease.gitplex.core.GitPlex;

/*
 * @DynamicUpdate annotation here along with various @OptimisticLock annotations
 * on certain fields tell Hibernate not to perform version check on those fields
 * which can be updated from background thread.
 */
@Entity
@DynamicUpdate 
public class CodeCommentReply extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;

	@Version
	private long version;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(nullable=false)
	private CodeComment comment;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Account user;

	@Lob
	@Column(nullable=false, length=65535)
	private String content;
	
	@Column(nullable=false)
	private Date date = new Date();
	
	@OptimisticLock(excluded=true)
	private String compareCommit;
	
	@Nullable
	public Account getUser() {
		return user;
	}

	public void setUser(@Nullable Account user) {
		this.user = user;
	}

	public long getVersion() {
		return version;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public CodeComment getComment() {
		return comment;
	}

	public void setComment(CodeComment comment) {
		this.comment = comment;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void delete() {
		GitPlex.getInstance(Dao.class).remove(this);
	}

	public String getCompareCommit() {
		return compareCommit;
	}

	public void setCompareCommit(String compareCommit) {
		this.compareCommit = compareCommit;
	}

}