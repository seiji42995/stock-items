package com.example.domain;

/**
 * 閉包テーブルの情報を表すドメインクラス.
 * 
 * @author seiji_kitahara
 *
 */
public class Treepath {

	/** 先祖ID */
	private Integer ancestorId;
	/** 子孫ID */
	private Integer descendantId;

	public Treepath() {}

	public Treepath(Integer ancestorId, Integer descendantId) {
		super();
		this.ancestorId = ancestorId;
		this.descendantId = descendantId;
	}

	@Override
	public String toString() {
		return "Treepath [ancestorId=" + ancestorId + ", descendantId=" + descendantId + "]";
	}

	public Integer getAncestorId() {
		return ancestorId;
	}

	public void setAncestorId(Integer ancestorId) {
		this.ancestorId = ancestorId;
	}

	public Integer getDescendantId() {
		return descendantId;
	}

	public void setDescendantId(Integer descendantId) {
		this.descendantId = descendantId;
	}
}
