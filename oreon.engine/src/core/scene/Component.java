package core.scene;

import core.math.Transform;

public abstract class Component{
	
	private GameObject parent;
	
	public void update(){};
	
	public void input(){};
	
	public void render(){};
	
	public GameObject getParent() {
		return parent;
	}

	public void setParent(GameObject parent) {
		this.parent = parent;
	}

	public Transform getWorldTransform()
	{
		return getParent().getWorldTransform();
	}
	
	public Transform getLocalTransform()
	{
		return getParent().getLocalTransform();
	}
}