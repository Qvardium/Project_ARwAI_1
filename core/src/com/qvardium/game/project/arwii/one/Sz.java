package com.qvardium.game.project.arwii.one;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Sz extends ScreenAdapter{

	MyGame the_game;
	SpriteBatch batch;
	OrthographicCamera camHUD;
	Viewport viewportHUD;
	
	Sprite black;
	Sprite[] epilog, g2, g5;
	
	Sound[] ep,gs2,gs5;
	Music forg5;
	byte for_sounds=0;
	
	
	long the_time;
	float black_a=0f,alp1=0f,alp2=0f,alp3=0f;
	byte the_state=0;
	
	public Sz(MyGame ggg){
		the_game=ggg;
		batch= ggg.batch;
		camHUD = new OrthographicCamera();
		viewportHUD = new FitViewport(1280, 768, camHUD);
		
		black = new Sprite(new Texture("black.png"));
		
		if(the_game.curentLvl==1){
			epilog = new Sprite[5];
			ep = new Sound[3];
			ep[0] = Gdx.audio.newSound(Gdx.files.internal("epilog/sz/ep1.mp3"));
			ep[1] = Gdx.audio.newSound(Gdx.files.internal("epilog/sz/ep2.mp3"));
			ep[2] = Gdx.audio.newSound(Gdx.files.internal("epilog/sz/ep3.mp3"));
			epilog[0] = new Sprite(new Texture("epilog/sz/1.jpg"));
			epilog[1] = new Sprite(new Texture("epilog/sz/2.jpg"));
			epilog[2] = new Sprite(new Texture("epilog/sz/3.jpg"));
			epilog[3] = new Sprite(new Texture("epilog/sz/4.jpg"));
			epilog[4] = new Sprite(new Texture("epilog/sz/5r.jpg"));
			if(the_game.for_lang==2) epilog[4] = new Sprite(new Texture("epilog/sz/3e.jpg"));
			epilog[0].setPosition(-640, -384);
			epilog[1].setPosition(-204, -384);
			epilog[2].setPosition(236, -384);
			epilog[3].setPosition(-640, -384);
			epilog[4].setPosition(-640, -384);
		}
		else if(the_game.curentLvl==11 && the_game.trueLvl){
			g2 = new Sprite[4];
			for(int i = 0; i<g2.length; i++){
				g2[i] = new Sprite(new Texture("g2/sz/"+(i+1)+".jpg"));
				g2[i].setPosition(-640, -384);
			}
			if(the_game.for_lang==2){
				g2[2] = new Sprite(new Texture("g2/sz/3en.jpg"));
				g2[3] = new Sprite(new Texture("g2/sz/4en.jpg"));
				g2[2].setPosition(-640, -384);
				g2[3].setPosition(-640, -384);
			}
			gs2 = new Sound[3];
			gs2[0] = Gdx.audio.newSound(Gdx.files.internal("g2/sz/g21.mp3"));
			gs2[1] = Gdx.audio.newSound(Gdx.files.internal("g2/sz/g22.mp3"));
			gs2[2] = Gdx.audio.newSound(Gdx.files.internal("g2/sz/g23.mp3"));
			
		}
		else if(the_game.curentLvl==47 && the_game.trueLvl){
			g5 = new Sprite[6];
			for(int i = 0; i<g5.length; i++){
				g5[i] = new Sprite(new Texture("g5/sz/"+(i+1)+".jpg"));
				g5[i].setPosition(-640, -384);
			}
			if(the_game.for_lang==2){
				g5[0] = new Sprite(new Texture("g5/sz/1en.jpg"));
				g5[2] = new Sprite(new Texture("g5/sz/3en.jpg"));
				g5[4] = new Sprite(new Texture("g5/sz/5en.jpg"));
				g5[0].setPosition(-640, -384);
				g5[2].setPosition(-640, -384);
				g5[4].setPosition(-640, -384);
			}
			gs5 = new Sound[2];
			forg5 = Gdx.audio.newMusic(Gdx.files.internal("g5/sz/g51.mp3"));
			forg5.setLooping(true);
			forg5.setVolume(the_game.effectVol);
			gs5[0] = Gdx.audio.newSound(Gdx.files.internal("g5/sz/g52.mp3"));
			gs5[1] = Gdx.audio.newSound(Gdx.files.internal("g5/sz/g53.mp3"));
		}
		
		the_time = TimeUtils.millis();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camHUD.update();
		batch.setProjectionMatrix(camHUD.combined);
		
		batch.begin();
		
		if(the_game.curentLvl==1) epil(delta);
		else if(the_game.curentLvl==11 && the_game.trueLvl) gl2(delta);
		else if(the_game.curentLvl==47 && the_game.trueLvl) gl5(delta);
		
		batch.end();
	}
	
	
	public void epil(float d){
		
		if(the_state==0){
			if(TimeUtils.timeSinceMillis(the_time)>1000 && the_state==0){
				alp1=1f;
				if(for_sounds==0){
					ep[0].play(the_game.effectVol);
					for_sounds=1;
				}
			}
			if(TimeUtils.timeSinceMillis(the_time)>1500 && the_state==0){
				alp2=1f;
				if(for_sounds==1){
					ep[0].play(the_game.effectVol);
					for_sounds=2;
				}
			}
			if(TimeUtils.timeSinceMillis(the_time)>2000 && the_state==0){
				alp3=1f;
				if(for_sounds==2){
					ep[0].play(the_game.effectVol);
					for_sounds=3;
				}
			}
			if(TimeUtils.timeSinceMillis(the_time)>2500 && the_state==0){
				the_time = TimeUtils.millis();
				the_state=1;
				camHUD.zoom=0.7f;
				ep[1].play(the_game.effectVol);
			}
			epilog[0].draw(batch, alp1);
			epilog[1].draw(batch, alp2);
			epilog[2].draw(batch, alp3);
		}
		else if(the_state==1){
			epilog[3].draw(batch,alp1);
			camHUD.zoom=camHUD.zoom+(d/20);
			if(TimeUtils.timeSinceMillis(the_time)>5000){
				alp1=alp1-d;
			}
			if(alp1<=0f){
				the_time = TimeUtils.millis();
				the_state=2;
				camHUD.zoom=1f;
			}
		}
		else if(the_state==2){
			alp1=alp1+d;
			epilog[4].draw(batch,alp1);
			
			if(alp1>=1f){
				alp1=1f;
				the_time = TimeUtils.millis();
				the_state=3;
				ep[2].play(the_game.effectVol);
			}
		}
		else if(the_state==3){
			epilog[4].draw(batch,alp1);
			if(TimeUtils.timeSinceMillis(the_time)>2000){
				if(TimeUtils.timeSinceMillis(the_time)>5000 || Gdx.input.isTouched()){
					alp1=alp1-d;
				}
			}
			if(alp1<=0f){
				alp1=0f;
				the_game.setScreen(new tutorial_1(the_game));
			}
		}
		
	}
	
	public void gl2(float d){
		
		if(the_state==0){
			
			if(for_sounds==0){
				gs2[0].play(the_game.effectVol);
				for_sounds=1;
			}
			if(TimeUtils.timeSinceMillis(the_time)>1000){
				alp1=alp1+d;
			}
			
			if(alp1>=1f){
				alp1=1f;
				the_state=1;
				
				the_time = TimeUtils.millis();
			}
			
			g2[0].draw(batch, alp1);
		}
		else if(the_state==1){
			
			if(TimeUtils.timeSinceMillis(the_time)>3000){
				alp1=alp1-d;
			}
			if(alp1<=0f){
				alp1=0f;
				the_state=2;
				the_time = TimeUtils.millis();
			}
			g2[0].draw(batch, alp1);
		}
		else if(the_state==2){
			
			if(TimeUtils.timeSinceMillis(the_time)>1000){
				alp1=alp1+d;
			}
			if(alp1>=1f){
				alp1=1f;
				the_state=3;
				the_time = TimeUtils.millis();
			}
			g2[1].draw(batch, alp1);
		}
		else if(the_state==3){
			
			if(TimeUtils.timeSinceMillis(the_time)>3000){
				alp1=alp1-d;
			}
			if(alp1<=0f){
				alp1=0f;
				the_state=4;
				the_time = TimeUtils.millis();
			}
			g2[1].draw(batch, alp1);
		}
		else if(the_state==4){
			
			if(TimeUtils.timeSinceMillis(the_time)>1000){
				alp1=alp1+d;
			}
			if(alp1>=1f){
				alp1=1f;
				the_state=5;
				the_time = TimeUtils.millis();
			}
			g2[2].draw(batch, alp1);
		}
		else if(the_state==5){
			
			if(TimeUtils.timeSinceMillis(the_time)>3000){
				alp1=alp1-d;
				if(for_sounds==1){
					gs2[2].play(the_game.effectVol);
					for_sounds=2;
				}
			}
			if(alp1<=0f){
				alp1=0f;
				the_state=6;
				the_time = TimeUtils.millis();
				
			}
			g2[2].draw(batch, alp1);
		}
		else if(the_state==6){
			
			if(TimeUtils.timeSinceMillis(the_time)>1000){
				alp1=alp1+d;
			}
			if(alp1>=1f){
				alp1=1f;
				the_state=7;
				the_time = TimeUtils.millis();
				gs2[1].play(the_game.effectVol);
			}
			g2[3].draw(batch, alp1);
		}
		else if(the_state==7){
			
			if(TimeUtils.timeSinceMillis(the_time)>4000){
				alp1=alp1-d;
			}
			if(alp1<=0f){
				alp1=0f;
				the_state=8;
				the_time = TimeUtils.millis();
			}
			g2[3].draw(batch, alp1);
		}
		else if(the_state==8){
			if(TimeUtils.timeSinceMillis(the_time)>1000){
				the_game.setScreen(new Glava2(the_game));
			}
			
		}
		
	}
	
	public void gl5(float d){
		
		if(the_state==0){
			
			if(for_sounds==0){
				
				gs5[0].play(the_game.effectVol);
				for_sounds=1;
			}
			
			if(TimeUtils.timeSinceMillis(the_time)>1000){
				alp1=alp1+d;
			}
			
			if(alp1>=1f){
				alp1=1f;
				the_state=1;
				the_time = TimeUtils.millis();
				forg5.play();
			}
			
			g5[0].draw(batch, alp1);
		}
		else if(the_state==1){
			
			if(TimeUtils.timeSinceMillis(the_time)>4000){
				alp1=alp1-d;
			}
			if(alp1<=0f){
				alp1=0f;
				the_state=2;
				the_time = TimeUtils.millis();
			}
			g5[0].draw(batch, alp1);
		}
		
		else if(the_state==2){
			if(TimeUtils.timeSinceMillis(the_time)>1000){
				alp1=alp1+d;
			}
			
			if(alp1>=1f){
				alp1=1f;
				the_state=3;
				the_time = TimeUtils.millis();
			}
			
			g5[1].draw(batch, alp1);
		}
		else if(the_state==3){
			
			if(TimeUtils.timeSinceMillis(the_time)>3000){
				alp1=alp1-d;
			}
			if(alp1<=0f){
				alp1=0f;
				the_state=4;
				the_time = TimeUtils.millis();
			}
			g5[1].draw(batch, alp1);
		}
		
		else if(the_state==4){
			if(TimeUtils.timeSinceMillis(the_time)>1000){
				alp1=alp1+d;
			}
			
			if(alp1>=1f){
				alp1=1f;
				the_state=5;
				the_time = TimeUtils.millis();
			}
			
			g5[2].draw(batch, alp1);
		}
		else if(the_state==5){
			
			if(TimeUtils.timeSinceMillis(the_time)>4000){
				alp1=alp1-d;
			}
			if(alp1<=0f){
				alp1=0f;
				the_state=6;
				the_time = TimeUtils.millis();
			}
			g5[2].draw(batch, alp1);
		}
		
		else if(the_state==6){
			if(TimeUtils.timeSinceMillis(the_time)>1000){
				alp1=alp1+d;
			}
			
			if(alp1>=1f){
				alp1=1f;
				the_state=7;
				the_time = TimeUtils.millis();
			}
			
			g5[3].draw(batch, alp1);
		}
		else if(the_state==7){
			
			if(TimeUtils.timeSinceMillis(the_time)>3000){
				alp1=alp1-d;
			}
			if(alp1<=0f){
				alp1=0f;
				the_state=8;
				the_time = TimeUtils.millis();					
				gs5[1].play(the_game.effectVol);
				
			}
			g5[3].draw(batch, alp1);
		}
		
		else if(the_state==8){
			if(TimeUtils.timeSinceMillis(the_time)>1000){
				alp1=alp1+d;
			}
			
			if(alp1>=1f){
				alp1=1f;
				the_state=9;
				the_time = TimeUtils.millis();
			}
			
			g5[4].draw(batch, alp1);
		}
		else if(the_state==9){
			
			if(TimeUtils.timeSinceMillis(the_time)>4000){
				alp1=alp1-d;
			}
			if(alp1<=0f){
				alp1=0f;
				the_state=10;
				the_time = TimeUtils.millis();
			}
			g5[4].draw(batch, alp1);
		}
		
		else if(the_state==10){
			if(TimeUtils.timeSinceMillis(the_time)>1000){
				alp1=alp1+d;
			}
			
			if(alp1>=1f){
				alp1=1f;
				the_state=11;
				the_time = TimeUtils.millis();
			}
			
			g5[5].draw(batch, alp1);
		}
		else if(the_state==11){
			
			if(TimeUtils.timeSinceMillis(the_time)>3000){
				alp1=alp1-d;
				
			}
			if(alp1<=0f){
				alp1=0f;
				the_state=12;
				the_time = TimeUtils.millis();
				forg5.setLooping(false);
			}
			g5[5].draw(batch, alp1);
		}
		else if(the_state==12){
			if(TimeUtils.timeSinceMillis(the_time)>1000 && !forg5.isPlaying()){
				
				the_game.setScreen(new Glava5(the_game));
			}
			
		}
		
	}
	
	@Override
	public void resize(int width, int height) {
		viewportHUD.update(width, height);
	}
}
