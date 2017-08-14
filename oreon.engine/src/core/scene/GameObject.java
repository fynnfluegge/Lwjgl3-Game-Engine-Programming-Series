 package core.scene;

import java.util.HashMap;

public class GameObject extends Node{

	private HashMap<String, Component> components;
	
	public GameObject()
	{
		components = new HashMap<String, Component>();
	}
	
	public void addComponent(String string, Component component)
	{
		component.setParent(this);
		components.put(string, component);
	}
	
	public void update()
	{	
		for (String key : components.keySet()) {
			components.get(key).update();
		}
	}
	
	public void input()
	{
		for (String key : components.keySet()) {
			components.get(key).input();
		}
	}
	
	public void render()
	{
		for (String key : components.keySet()) {
			components.get(key).render();
		}
	}

	public HashMap<String, Component> getComponents() {
		return components;
	}
	
	public Component getComponent(String component)
	{
		return this.components.get(component);
	}
	
}
