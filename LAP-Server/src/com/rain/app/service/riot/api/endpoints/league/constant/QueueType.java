package com.rain.app.service.riot.api.endpoints.league.constant;

import java.util.NoSuchElementException;

import com.rain.app.service.riot.api.RiotApi;

public enum QueueType {
	ARAM_5x5("ARAM", 65),
	ARURF_5X5("All Random URF", 318),
	ASCENSION_5x5("Ascension", 96),
	BILGEWATER_5x5("Black Market Brawlers", 313),
	BILGEWATER_ARAM_5x5("Butcher's Bridge", 100),
	BOT_5x5("Historical Summoner's Rift Coop vs AI", 7),
	BOT_5x5_BEGINNER("Summoner's Rift Coop vs AI Beginner Bot", 32),
	BOT_5x5_INTERMEDIATE("Historical Summoner's Rift Coop vs AI Intermediate Bot", 33),
	BOT_5x5_INTRO("Summoner's Rift Coop vs AI Intro Bot", 31),
	BOT_ODIN_5x5("Dominion Coop vs AI", 25),
	BOT_URF_5x5("Ultra Rapid Fire games played against AI", 83),
	BOT_TT_3x3("Twisted Treeline Coop vs AI", 52),
	COUNTER_PICK("Nemesis Draft", 310),
	CUSTOM("Custom", 0),
	DEFINITELY_NOT_DOMINION_5x5("Definitely Not Dominion ", 317),
	FIRSTBLOOD_1x1("Snowdown Showdown 1v1", 72),
	FIRSTBLOOD_2x2("Snowdown Showdown 2v2", 73),
	GROUP_FINDER_5x5("Team Builder", 61),
	HEXAKILL("Twisted Treeline 6x6 Hexakill", 98),
	KING_PORO_5x5("King Poro", 300),
	NIGHTMARE_BOT_5x5_RANK1("Doom Bots Rank 1", 91),
	NIGHTMARE_BOT_5x5_RANK2("Doom Bots Rank 2", 92),
	NIGHTMARE_BOT_5x5_RANK5("Doom Bots Rank 5", 93),
	NORMAL_5x5_BLIND("Normal 5v5 Blind Pick", 2),
	NORMAL_3x3("Normal 3v3", 8),
	NORMAL_5x5_DRAFT("Normal 5v5 Draft Pick", 14),
	ODIN_5x5_BLIND("Dominion 5v5 Blind Pick", 16),
	ODIN_5x5_DRAFT("Dominion 5v5 Draft Pick", 17),
	ONEFORALL_5x5("One for All", 70),
	ONEFORALL_MIRRORMODE_5x5("One for All: Mirror Mode", 70),
	RANKED_FLEX_SR("Ranked Flex Summoner's Rift", 440),
	RANKED_FLEX_TT("Ranked Flex Twisted Treeline", 9),
	RANKED_PREMADE_3x3("Ranked Premade 3v3", 9),
	RANKED_PREMADE_5x5("Ranked Premade 5v5", 6),
	RANKED_SOLO_5x5("Ranked Solo 5v5", 4),
	RANKED_TEAM_3x3("Ranked Team 3v3", 41),
	RANKED_TEAM_5x5("Ranked Team 5v5", 42),
	SIEGE("Nexus Siege", 315),
	SR_6x6("Hexakill", 75),
	TEAM_BUILDER_DRAFT_RANKED_5x5("Ranked 5v5 Draft Pick", 410),
	TEAM_BUILDER_DRAFT_UNRANKED_5x5("Normal 5v5 Draft Pick", 400),
	TEAM_BUILDER_RANKED_SOLO("Team Builder Ranked Solo", 420),
	URF_5x5("Ultra Rapid Fire", 76);

	private String name;
	private int gameQueueConfigId;

	public static QueueType getQueueTypeByConfigId(int gameQueueConfigId) {
		for (QueueType queueType : QueueType.values()) {
			if (queueType.getGameQueueConfigId() == gameQueueConfigId) {
				return queueType;
			}
		}
		RiotApi.log.warning("Unknown queue config ID: " + gameQueueConfigId);
		throw new NoSuchElementException("Unknown queue config ID: " + gameQueueConfigId);
	}

	QueueType(String name, int gameQueueConfigId) {
		this.name = name;
		this.gameQueueConfigId = gameQueueConfigId;
	}

	public String getName() {
		return name;
	}

	public int getGameQueueConfigId() {
		return gameQueueConfigId;
	}
}
