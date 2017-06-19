package com.rain.app.service.riot.api.endpoints.constants.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.rain.app.service.riot.api.Dto;

public class BasicData extends Dto implements Serializable {

	private static final long serialVersionUID = 4928673824824907056L;

	private String colloq;
	private boolean consumeOnFull;
	private boolean consumed;
	private int depth;
	private String decription;
	private List<String> from;
	private Gold gold;
	private String group;
	private boolean hideFromAll;
	private int id;
	private Image image;
	private boolean inStore;
	private List<String> into;
	private Map<String, Boolean> maps;
	private String name;
	private String plaintext;
	private String requiredChampion;
	private MetaData rune;
	private String sanitizedDescription;
	private int specialRecipe;
	private int stacks;
	private BasicDataStats stats;
	private List<String> tags;

	public String getColloq() {
		return colloq;
	}

	public String getDecription() {
		return decription;
	}

	public int getDepth() {
		return depth;
	}

	public List<String> getFrom() {
		return from;
	}

	public Gold getGold() {
		return gold;
	}

	public String getGroup() {
		return group;
	}

	public int getId() {
		return id;
	}

	public Image getImage() {
		return image;
	}

	public List<String> getInto() {
		return into;
	}

	public Map<String, Boolean> getMaps() {
		return maps;
	}

	public String getName() {
		return name;
	}

	public String getPlaintext() {
		return plaintext;
	}

	public String getRequiredChampion() {
		return requiredChampion;
	}

	public MetaData getRune() {
		return rune;
	}

	public String getSanitizedDescription() {
		return sanitizedDescription;
	}

	public int getSpecialRecipe() {
		return specialRecipe;
	}

	public int getStacks() {
		return stacks;
	}

	public BasicDataStats getStats() {
		return stats;
	}

	public List<String> getTags() {
		return tags;
	}

	public boolean isConsumeOnFull() {
		return consumeOnFull;
	}

	public boolean isConsumed() {
		return consumed;
	}

	public boolean isHideFromAll() {
		return hideFromAll;
	}

	public boolean isInStore() {
		return inStore;
	}

	@Override
	public String toString() {
		return getId() + ": " + getName();
	}
}
