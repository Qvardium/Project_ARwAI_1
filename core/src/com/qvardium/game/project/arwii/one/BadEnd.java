package com.qvardium.game.project.arwii.one;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class BadEnd extends ScreenAdapter{

	MyGame the_game;
	SpriteBatch batch;
	OrthographicCamera camHUD;
	Viewport viewportHUD;
	
	Sprite bg_black, bg_end;
	float bg_black_a;
	boolean end_game=false,the_start=true;
	Stage stage;
	BitmapFont gfont;
	ImageTextButton btn;
	TextureAtlas walls;
	
	Music music_end;
	
	String[] forends;
	
	long the_time;
	
	byte klick=0;
	
	public BadEnd(MyGame ggg){
		the_game=ggg;
		batch= ggg.batch;
		camHUD = new OrthographicCamera();
		viewportHUD = new FitViewport(1280, 768, camHUD);
		walls = new TextureAtlas(Gdx.files.internal("epilog/tutor.pack"));
		gfont = new BitmapFont(Gdx.files.internal("111.fnt"));
		btn  = new ImageTextButton("",
				new ImageTextButton.ImageTextButtonStyle
				(new TextureRegionDrawable(walls.findRegion("bggg")), 
						new TextureRegionDrawable(walls.findRegion("bggg")),
						new TextureRegionDrawable(walls.findRegion("bggg")),
						gfont));
		stage = new Stage(viewportHUD,batch);
		stage.addActor(btn);
		
		the_time = TimeUtils.millis();
		
		bg_black = new Sprite(new Texture(Gdx.files.internal("black.png")));
		bg_black.setPosition(camHUD.position.x-640, camHUD.position.y-384);
		bg_black_a = 1f;
		bg_end = new Sprite(new Texture(Gdx.files.internal("epilog/bad_end.jpg")));
		bg_end.setPosition(camHUD.position.x-640, camHUD.position.y-384);
		
		music_end = Gdx.audio.newMusic(Gdx.files.internal("sounds/5.mp3"));
		music_end.play();
		//music_end.setVolume(the_game.musicVol);
		
		forends = new String[6];
		 
		forends[0]="����������! "+"\n"
				+ "�� ������� �������� ���������."+"\n"+"� ������, ��������� ����!";
		forends[1]="����������� ��������...";
		forends[2]="";
		forends[3]="";
		
		if(the_game.for_lang==2){
			forends[0]="Congratulations!"+"\n"+
					"You successfully finished tests."+"\n"
					+ "And now, good night!";
			forends[1]="The experiment is failed...";
			forends[2]="";
			forends[3]="";

		}
		btn.setText(forends[klick]);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camHUD.update();
		batch.setProjectionMatrix(camHUD.combined);

		if(TimeUtils.timeSinceMillis(the_time)>5000 && Gdx.input.isTouched() && !the_start){
			klick++;
			end_game=true;
			the_time = TimeUtils.millis();
		}
		
		stage.act(Gdx.graphics.getDeltaTime());	    
	    stage.draw();
	    
	    if(klick==3 || klick==4){
			batch.begin();
			bg_end.draw(batch);
			batch.end();
		}
		
		if (the_start){
			if(bg_black_a>0.01f && !end_game) {
			bg_black_a-=0.01f;
			if(bg_black_a<=0.01f && !end_game) {bg_black_a=0; the_start=false;}
			camHUD.update();
			batch.setProjectionMatrix(camHUD.combined);
			
			batch.begin();
			bg_black.draw(batch, bg_black_a);
			batch.end();
			}
		}
		if(end_game){
			if(bg_black_a<0.99f) {
				bg_black_a+=0.01f;
				if(klick==4)music_end.setVolume(1-bg_black_a);
				if(bg_black_a>=0.99) bg_black_a=1;
			}
			
			camHUD.update();
			batch.setProjectionMatrix(camHUD.combined);
			
			batch.begin();
			bg_black.draw(batch, bg_black_a);
			batch.end();
			
			if(bg_black_a>=1){
				the_start=true;
				end_game=false;
				if(klick<=2) btn.setText(forends[klick]);
				if(klick==2) {
					btn.setVisible(false);
					klick=3;
					
				}
				if(klick==4) klick=5;
				if(klick==5){
					the_game.setScreen(new MenuScreen(the_game)); 
					music_end.stop();
				}
			}
		}
		
		
	}
	
}
