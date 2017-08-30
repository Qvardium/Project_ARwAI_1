package com.qvardium.game.project.arwii.one;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TheNextLevel extends ScreenAdapter{

	Sprite bg_black;
	float bg_black_a;
	boolean end_game=false,the_start=true;
	byte for_end_game=0;

	MyGame the_game;
	SpriteBatch batch;
	OrthographicCamera camHUD;
	Viewport viewportHUD;

	Stage stage;
	Label informFPS;
	BitmapFont gfont;

	long the_time,the_min,the_sec;
	String proyden, t_min, t_sec, t_pres, for_diff;

	public TheNextLevel(MyGame ggg){
		the_game=ggg;
		batch= ggg.batch;
		camHUD = new OrthographicCamera();
		viewportHUD = new FitViewport(1280, 768, camHUD);

		the_game.curent_time=the_game.curent_time/1000;
		the_min=the_game.curent_time/60;
		the_sec=the_game.curent_time%60;



		if(!the_game.for_reset) the_game.curentLvl = the_game.curentLvl + 1;

		if(the_game.dif==1 && the_game.curentLvl>the_game.max_lvl_1) the_game.max_lvl_1=the_game.curentLvl;
		else if(the_game.dif==2 && the_game.curentLvl>the_game.max_lvl_2) the_game.max_lvl_2=the_game.curentLvl;
		else if (the_game.dif==3 && the_game.curentLvl>the_game.max_lvl_3) the_game.max_lvl_3=the_game.curentLvl;

		for_diff="";

		if(the_game.dif==1 && the_game.curentLvl==3) {
			the_game.the_medium=true;
			for_diff = "Доступен средний уровень сложности";
			if(the_game.for_lang==2) for_diff = "Medium levels is available";
		}
		else if (the_game.dif==2 && the_game.curentLvl==3) {
			the_game.the_hard=true;
			for_diff = "Доступен сложный уровень сложности";
			if(the_game.for_lang==2) for_diff = "Hard levels is available";
		}

		proyden=" уровень пройден за: ";
		t_min=" мин. ";
		t_sec=" сек.";
		t_pres="Для продолжения нажмите на экран";
		if(the_game.for_lang==2){
			proyden=" level are passed for: ";
			t_min=" min. ";
			t_sec=" sec.";
			t_pres="For continuation click the screen";
		}

		the_game.saveTheLvl();

		gfont = new BitmapFont(Gdx.files.internal("111.fnt"));
		informFPS = new Label("", new Label.LabelStyle(gfont,gfont.getColor()));
		stage = new Stage(viewportHUD,batch);
		stage.addActor(informFPS);
		informFPS.setPosition(100, 500);
		the_time = TimeUtils.millis();

		bg_black = new Sprite(new Texture(Gdx.files.internal("black.png")), 0, 0, 1280, 768);
		bg_black.setPosition(camHUD.position.x-640, camHUD.position.y-384);
		bg_black_a = 1f;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(the_game.for_reset){
			ggggg();
		}
		if(TimeUtils.timeSinceMillis(the_time)>3000 && Gdx.input.isTouched()){
			end_game=true;
		}

		if(!the_game.for_reset){
			camHUD.update();
			batch.setProjectionMatrix(camHUD.combined);

			if(TimeUtils.timeSinceMillis(the_time)>3000)
				informFPS.setText((the_game.curentLvl-1)+proyden+the_min+t_min+the_sec+t_sec+"\n"+for_diff+"\n"+"\n"+
						t_pres);

			stage.act(Gdx.graphics.getDeltaTime());
			stage.draw();
		}
		if (the_start){
			if(bg_black_a>0.01f && !end_game) {
				bg_black_a-=0.01f;
				if(bg_black_a<=0.02f && !end_game) {bg_black_a=0; the_start=false;}
				camHUD.update();
				batch.setProjectionMatrix(camHUD.combined);

				batch.begin();
				bg_black.draw(batch, bg_black_a);
				batch.end();
			}
		}
		else if(end_game){
			if(bg_black_a<0.99f) {
				bg_black_a+=0.01f;

				if(bg_black_a>=0.98) bg_black_a=1;
			}

			camHUD.update();
			batch.setProjectionMatrix(camHUD.combined);

			batch.begin();
			bg_black.draw(batch, bg_black_a);
			batch.end();

			if(bg_black_a>=1){
				ggggg();
			}
		}
	}

	public void ggggg(){
		if ((the_game.curentLvl==1 || the_game.curentLvl==2) && (the_game.dif==2 || the_game.dif==3)) the_game.setScreen(new Glava1(the_game));
		else if (the_game.curentLvl>2 && the_game.curentLvl<=10) the_game.setScreen(new Glava1(the_game));
		else if (the_game.curentLvl>10 && the_game.curentLvl<=13 && !the_game.trueLvl) the_game.setScreen(new Glava1(the_game));
		else if (the_game.curentLvl==14 && !the_game.trueLvl) the_game.setScreen(new BadEnd(the_game));
		else if (the_game.curentLvl==11 && the_game.trueLvl) the_game.setScreen(new Sz(the_game));
		else if (the_game.curentLvl>10 && the_game.curentLvl<=20 && the_game.trueLvl) the_game.setScreen(new Glava2(the_game));
		else if (the_game.curentLvl>20 && the_game.curentLvl<=30 && the_game.trueLvl) the_game.setScreen(new Glava3(the_game));
		else if (the_game.curentLvl>30 && the_game.curentLvl<=40 && the_game.trueLvl) the_game.setScreen(new Glava4(the_game));
		else if (the_game.curentLvl==47 && the_game.trueLvl) the_game.setScreen(new Sz(the_game));
		else if (the_game.curentLvl>40 && the_game.curentLvl<=49 && the_game.trueLvl) the_game.setScreen(new Glava5(the_game));
		else if (the_game.curentLvl==50 && the_game.trueLvl) the_game.setScreen(new Last_lvl(the_game));
	}
}
