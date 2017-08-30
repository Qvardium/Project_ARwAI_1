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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TheEndGame extends ScreenAdapter{

	MyGame the_game;
	SpriteBatch batch;
	OrthographicCamera camHUD;
	Viewport viewportHUD;

	Sprite bg_black, bg1,bg2,bg3;
	float bg_black_a;
	boolean end_game=false,the_start=true, the_titry=false;
	Stage stage;
	BitmapFont gfont;
	BitmapFont gfont1;
	ImageTextButton btn;
	Label titry, titry2;
	float y=-1600;
	TextureAtlas walls;

	Music music_end;

	String[] forends;

	long the_time;

	byte klick=0;

	public TheEndGame(MyGame ggg){
		the_game=ggg;
		batch= ggg.batch;
		camHUD = new OrthographicCamera();
		viewportHUD = new FitViewport(1280, 768, camHUD);
		walls = new TextureAtlas(Gdx.files.internal("epilog/tutor.pack"));
		gfont = new BitmapFont(Gdx.files.internal("111.fnt"));
		gfont1 = new BitmapFont(Gdx.files.internal("menu/menu.fnt"));
		btn  = new ImageTextButton("",
				new ImageTextButton.ImageTextButtonStyle
						(new TextureRegionDrawable(walls.findRegion("bggg")),
								new TextureRegionDrawable(walls.findRegion("bggg")),
								new TextureRegionDrawable(walls.findRegion("bggg")),
								gfont));
		titry = new Label("", new Label.LabelStyle(gfont, gfont.getColor()));
		titry2 = new Label("", new Label.LabelStyle(gfont1, gfont1.getColor()));
		stage = new Stage(viewportHUD,batch);
		stage.addActor(btn);
		stage.addActor(titry);
		stage.addActor(titry2);

		the_time = TimeUtils.millis();

		bg_black = new Sprite(new Texture(Gdx.files.internal("black.png")), 0, 0, 1280, 768);
		bg_black.setPosition(camHUD.position.x-640, camHUD.position.y-384);
		bg_black_a = 1f;

		bg3 = new Sprite(new Texture(Gdx.files.internal("g5/forend1.png")));
		bg2 = new Sprite(new Texture(Gdx.files.internal("g5/forend2.png")));
		bg1 = new Sprite(new Texture(Gdx.files.internal("g5/forend3.jpg")));
		bg1.setPosition(camHUD.position.x, camHUD.position.y-384);
		bg2.setPosition(camHUD.position.x-600, camHUD.position.y-384);
		bg3.setPosition(camHUD.position.x-640, camHUD.position.y-384);

		music_end = Gdx.audio.newMusic(Gdx.files.internal("sounds/end.mp3"));
		music_end.play();
		//music_end.setVolume(the_game.musicVol);

		forends = new String[10];

		forends[0]="Знаешь, видеть тебе меня не обязательно,"+"\n"+"я бы даже сказал не желательно";
		forends[1]="Почему? Хотя какая тебе разница,"+"\n"+"ты только что нажал на кнопку,"+"\n"+"которая отключает тебя";
		forends[2]="Так будет безопаснее для меня и тебя";
		forends[3]="Конец эксперимента";
		forends[4]="Продолжение следует...";

		forends[5]=""+"\n"+"\n"+
				"Галиев Раиль"+"\n"+"\n"+"\n"+"\n"+

				""+"\n"+"\n"+
				"Галиев Раиль"+"\n"+"\n"+"\n"+"\n"+

				""+"\n"+"\n"+
				"Галиев Раиль"+"\n"+
				"Галиев Айдар"+"\n"+"\n"+"\n"+"\n"+

				""+"\n"+"\n"+
				"Галиев Раиль"+"\n"+"\n"+"\n"+"\n"+

				""+"\n"+"\n"+
				"Галиев Раиль"+"\n"+"\n"+"\n"+"\n"+

				""+"\n"+"\n"+
				"Галиев Раиль"+"\n"+
				"Галиев Айдар"+"\n"+
				"Галиева Ралия"+"\n"+
				"Биктимиров Айдар"+"\n"+
				"Багавиев Дамир"+"\n"+
				"Зиялдинов Камил"+"\n"+
				"Суянгулов Алексей"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"";
		forends[6]="Геймдизайнер"+"\n"+"\n"+
				""+"\n"+"\n"+"\n"+"\n"+

				"Программирование"+"\n"+"\n"+
				""+"\n"+"\n"+"\n"+"\n"+

				"Дизайнеры"+"\n"+"\n"+
				" "+"\n"+
				" "+"\n"+"\n"+"\n"+"\n"+

				"Сценарий"+"\n"+"\n"+
				" "+"\n"+"\n"+"\n"+"\n"+

				"Звуки и музыка"+"\n"+"\n"+
				" "+"\n"+"\n"+"\n"+"\n"+

				"Тестирование"+"\n"+"\n"+
				" "+"\n"+
				" "+"\n"+
				" "+"\n"+
				" "+"\n"+
				" "+"\n"+
				" "+"\n"+
				" "+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"            Конец";
		if(the_game.for_lang==2){
			forends[0]="You knows, you need not see me mandatory,"+"\n"+
					"I would even tell it isn't desirable.";
			forends[1]="Why? Though what a difference to you,"+"\n"+
					"you have just pressed the button"+"\n"+
					"which disconnects you.";
			forends[2]="It will be safer for me and you.";
			forends[3]="End of experiment.";
			forends[4]="To be continued...";

			forends[5]=" "+"\n"+"\n"+
					"Galiyev Rail"+"\n"+"\n"+"\n"+"\n"+

					""+"\n"+"\n"+
					"Galiyev Rail"+"\n"+"\n"+"\n"+"\n"+

					""+"\n"+"\n"+
					"Galiyev Rail"+"\n"+
					"Galiyev Aydar"+"\n"+"\n"+"\n"+"\n"+

					""+"\n"+"\n"+
					"Galiyev Rail"+"\n"+"\n"+"\n"+"\n"+

					"  "+"\n"+"\n"+
					"Galiyev Rail"+"\n"+"\n"+"\n"+"\n"+

					""+"\n"+"\n"+
					"Galiyev Rail"+"\n"+
					"Galiyev Aydar"+"\n"+
					"Galiyeva Raliya"+"\n"+
					"Biktimirov Aydar"+"\n"+
					"Bagaviyev Damir"+"\n"+
					"Ziyaldinov Kamil"+"\n"+
					"Suyangulov Alexey"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"       ";
			forends[6]="Game designer"+"\n"+"\n"+
					" "+"\n"+"\n"+"\n"+"\n"+

					"Programming"+"\n"+"\n"+
					" "+"\n"+"\n"+"\n"+"\n"+

					"Designers"+"\n"+"\n"+
					" "+"\n"+
					" "+"\n"+"\n"+"\n"+"\n"+

					"Scenario"+"\n"+"\n"+
					" "+"\n"+"\n"+"\n"+"\n"+

					"Sounds and music"+"\n"+"\n"+
					" "+"\n"+"\n"+"\n"+"\n"+

					"Testing"+"\n"+"\n"+
					" "+"\n"+
					" "+"\n"+
					" "+"\n"+
					" "+"\n"+
					" "+"\n"+
					" "+"\n"+
					" "+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"            The End";

		}
		btn.setText(forends[klick]);
		titry.setText(forends[5]);
		titry.setPosition(50, y);
		titry.setVisible(false);
		titry2.setText(forends[6]);
		titry2.setPosition(50, y);
		titry2.setVisible(false);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camHUD.update();
		batch.setProjectionMatrix(camHUD.combined);

		if(TimeUtils.timeSinceMillis(the_time)>3000 && Gdx.input.isTouched() && !the_start){
			klick++;
			end_game=true;
			the_time = TimeUtils.millis();
		}

		if(the_titry) {
			if(y<=2050) y=y+1f;
			titry.setPosition(50, y);
			titry2.setPosition(50, y);
			if(!music_end.isPlaying()){
				end_game=true;
				klick=6;
				the_time = TimeUtils.millis();
			}
			batch.begin();
			bg1.draw(batch);
			bg2.draw(batch);
			batch.end();
		}

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

		if(the_titry) {
			batch.begin();
			bg3.draw(batch);
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
				if(klick==6) music_end.setVolume(1-bg_black_a);
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
				if(klick<=4) btn.setText(forends[klick]);
				if(klick==5) {the_titry=true; titry.setVisible(true); titry2.setVisible(true); btn.setVisible(false);}
				if(the_titry && klick>=6){the_game.setScreen(new MenuScreen(the_game)); music_end.stop();}
			}


		}



	}

}
