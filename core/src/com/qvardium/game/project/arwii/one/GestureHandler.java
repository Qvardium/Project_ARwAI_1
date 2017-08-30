package com.qvardium.game.project.arwii.one;

import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class GestureHandler  implements GestureListener{

	float zzz;
	int dx,dy;
	boolean on_radar;
	
	private int ww,hh;
	private float max_size,min_size;
	
	public GestureHandler(float zz, int xx, int yy, int w, int h){
		zzz=zz;
		min_size=zzz;
		max_size=50;
		ww=w;
		hh=h;
		dx=xx;
		dy=yy;
		
	}	
	
	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		if(on_radar && zzz>=min_size && zzz<=max_size) zzz=zzz+((distance - initialDistance)/1000);
		if(on_radar && zzz<=min_size) zzz=min_size;
		if(on_radar && zzz>=max_size) zzz=max_size;
		proverka();
		return false;
	}
	
	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		if(x>45 && x<912 && y>40 && y<724 && on_radar){
			dx=dx+(int)deltaX;
			dy=dy-(int)deltaY;
			proverka();
		}
		return false;
	}
	
	void proverka(){
		/*if(zzz<=min_size){
			if (dx>910-(min_size*(ww+ww+1))) dx=(int)(908-(min_size*(ww+ww+1)));
			else if (dx<50) dx=53;
			if (dy>718-(min_size*(hh+hh+1))) dy= (int)(715-(min_size*(hh+hh+1)));
			else if (dy<48) dy=50;
		}
		else if(zzz>=max_size){
			if (dx<910+(-max_size*(ww+ww+1))) dx= (int)((-max_size*(ww+ww+1))+908);
			else if (dx>50) dx=49;
			if (dy<718+(-max_size*(hh+hh+1))) dy= (int)((-max_size*(hh+hh+1))+715);
			else if (dy>50) dy=49;
		}
		else */if(zzz>=min_size && zzz<=max_size){
			if (dx<910+(-zzz*(ww+ww+1))) dx=(int)((-zzz*(ww+ww+1))+908);
			else if (dx>50) dx=49;
			if (dy<718+(-zzz*(hh+hh+1))) dy= (int)((-zzz*(hh+hh+1))+715);
			else if (dy>50) dy=49;
		}
	}
	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		//zzz+=1;
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void pinchStop() {
		// TODO Auto-generated method stub
		
	}

}
