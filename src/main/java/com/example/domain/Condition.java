package com.example.domain;

/**
 * コンディション情報を表すドメインクラス.
 * 
 * @author seiji_kitahara
 *
 */
public class Condition {

	/** コンディションID */
	private Integer conditionId;
	/** コンディション状況 */
	private String conditionDescription;

	public Condition() {
	}

	public Condition(Integer conditionId, String conditionDescription) {
		super();
		this.conditionId = conditionId;
		this.conditionDescription = conditionDescription;
	}

	@Override
	public String toString() {
		return "Condition [conditionId=" + conditionId + ", conditionDescription=" + conditionDescription + "]";
	}

	public Integer getConditionId() {
		return conditionId;
	}

	public void setConditionId(Integer conditionId) {
		this.conditionId = conditionId;
	}

	public String getConditionDescription() {
		return conditionDescription;
	}

	public void setConditionDescription(String conditionDescription) {
		this.conditionDescription = conditionDescription;
	}

}
