package com.pancredit.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author DhanyaJ
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PanCredit {
	@JsonProperty("Id")
	String Id;

	@JsonProperty("ApplicationId")
	int	ApplicationId;

	@JsonProperty("Type")
	String Type;

	@JsonProperty("Summary")
	String Summary;

	@JsonProperty("Amount")
	float Amount;

	@JsonProperty("PostingDate")
	String PostingDate;

	@JsonProperty("IsCleared")
	boolean IsCleared; 

	@JsonProperty("ClearedDate")
	String ClearedDate;

	//	Generate getters And setters
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public int getApplicationId() {
		return ApplicationId;
	}
	public void setApplicationId(int applicationId) {
		ApplicationId = applicationId;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getSummary() {
		return Summary;
	}
	public void setSummary(String summary) {
		Summary = summary;
	}
	public float getAmount() {
		return Amount;
	}
	public void setAmount(float amount) {
		Amount = amount;
	}
	public String getPostingDate() {
		return PostingDate;
	}
	public void setPostingDate(String postingDate) {
		PostingDate = postingDate;
	}
	public boolean isIsCleared() {
		return IsCleared;
	}
	public void setIsCleared(boolean isCleared) {
		IsCleared = isCleared;
	}
	public String getClearedDate() {
		return ClearedDate;
	}
	public void setClearedDate(String clearedDate) {
		ClearedDate = clearedDate;
	}

}
