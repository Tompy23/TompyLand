package net.tompy.tlc.entity;

import java.awt.Color;

public class EntityCharacterImpl implements ClientEntity 
{
	private String name;
	private Color color;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
