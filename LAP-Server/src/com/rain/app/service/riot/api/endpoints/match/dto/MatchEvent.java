package com.rain.app.service.riot.api.endpoints.match.dto;

import java.io.Serializable;
import java.util.List;

import com.rain.app.service.riot.api.Dto;

public class MatchEvent extends Dto implements Serializable {

	private static final long serialVersionUID = -2911353385877952457L;

	private String ascendedType;
	private List<Integer> assistingParticipantIds;
	private String buildingType;
	private int creatorId;
	private int itemAfter;
	private int itemBefore;
	private int itemId;
	private int killerId;
	private String laneType;
	private String levelUpType;
	private String monsterSubType;
	private String monsterType;
	private int participantId;
	private String pointCaptured;
	private MatchPosition position;
	private int skillSlot;
	private int teamId;
	private long timestamp;
	private String towerType;
	private String type;
	private int victimId;
	private String wardType;

	public String getAscendedType() {
		return ascendedType;
	}

	public List<Integer> getAssistingParticipantIds() {
		return assistingParticipantIds;
	}

	public String getBuildingType() {
		return buildingType;
	}

	public int getCreatorId() {
		return creatorId;
	}

	public int getItemAfter() {
		return itemAfter;
	}

	public int getItemBefore() {
		return itemBefore;
	}

	public int getItemId() {
		return itemId;
	}

	public int getKillerId() {
		return killerId;
	}

	public String getLaneType() {
		return laneType;
	}

	public String getLevelUpType() {
		return levelUpType;
	}

	public String getMonsterSubType() {
		return monsterSubType;
	}

	public String getMonsterType() {
		return monsterType;
	}

	public int getParticipantId() {
		return participantId;
	}

	public String getPointCaptured() {
		return pointCaptured;
	}

	public MatchPosition getPosition() {
		return position;
	}

	public int getSkillSlot() {
		return skillSlot;
	}

	public int getTeamId() {
		return teamId;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public String getTowerType() {
		return towerType;
	}

	public String getType() {
		return type;
	}

	public int getVictimId() {
		return victimId;
	}

	public String getWardType() {
		return wardType;
	}

	@Override
	public String toString() {
		return getType();
	}
}
