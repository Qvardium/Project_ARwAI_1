package com.qvardium.game.project.arwii.one;

import com.badlogic.gdx.math.MathUtils;

public class TheQuest2 {

	int x,y,otv=0;
	
	public TheQuest2(int x, int y, int lvl){
		this.x=x;
		this.y=y;
		
		if(lvl>0 && lvl<=20) otv=MathUtils.random(9);
		else if(lvl>20 && lvl<=30) otv=MathUtils.random(30);
		else if(lvl>30 && lvl<=40) otv=MathUtils.random(60);
		else if(lvl>40 && lvl<=50) otv=MathUtils.random(99);
	}
}
