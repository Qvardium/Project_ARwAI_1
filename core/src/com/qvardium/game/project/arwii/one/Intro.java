package com.qvardium.game.project.arwii.one;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
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
import com.badlogic.gdx.utils.viewport.Viewport;

public class Intro extends ScreenAdapter{

	MyGame the_game;
	SpriteBatch batch;
	OrthographicCamera cam;
	Viewport viewport;
	TextureAtlas fortexture;

	Stage stage;
	BitmapFont font;

	Label text;
	ImageTextButton rus,eng;

	Sprite black, main_bg;
	float black_a=1, main_a=0;
	byte the_state=3;

	long timep,presstime;

	public Intro(MyGame game){

		the_game = game;
		batch=game.batch;
		cam=game.cam;
		viewport=game.viewport;

		fortexture = new TextureAtlas("menu/ui_menu.pack");

		black = new Sprite(new Texture(Gdx.files.internal("black.png")));
		main_bg = new Sprite(new Texture(Gdx.files.internal("intro.jpg")));

		font = new BitmapFont(Gdx.files.internal("menu/menu.fnt"));
		rus = new ImageTextButton("Русский",
				new ImageTextButton.ImageTextButtonStyle
						(new TextureRegionDrawable(fortexture.findRegion("ru")),
								new TextureRegionDrawable(fortexture.findRegion("ru")),
								new TextureRegionDrawable(fortexture.findRegion("ru")),
								font));
		eng = new ImageTextButton("English",
				new ImageTextButton.ImageTextButtonStyle
						(new TextureRegionDrawable(fortexture.findRegion("en")),
								new TextureRegionDrawable(fortexture.findRegion("en")),
								new TextureRegionDrawable(fortexture.findRegion("en")),
								font));
		text = new Label("Выберете язык           Select language",
				new Label.LabelStyle(font, font.getColor()));

		rus.setPosition(200, 300);
		eng.setPosition(770, 300);
		text.setPosition(150, 600);

		stage = new Stage(viewport, batch);

		stage.addActor(rus);
		stage.addActor(eng);
		stage.addActor(text);


	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		if(the_state==3){
			black_a-=0.01f;
			if(black_a<=0.01){
				black_a=0;
				the_state=4;
				timep = TimeUtils.millis();
				Gdx.input.setInputProcessor(stage);
			}
		}
		else if(the_state==4 && TimeUtils.timeSinceMillis(timep)>500){
			if(rus.isPressed()){
				the_game.for_lang=3;
				the_game.saveAll();
				timep = TimeUtils.millis();
				the_state=5;
			}
			else if(eng.isPressed()){
				the_game.for_lang=2;
				the_game.saveAll();
				timep = TimeUtils.millis();
				the_state=5;
			}
		}
		else if(the_state==5 && TimeUtils.timeSinceMillis(timep)>500){
			black_a+=0.01f;
			if(black_a>=0.99){
				black_a=1;
				the_game.the_first_start=false;
				the_game.saveStart();
				the_game.saveAll();
				the_state=6;
				timep = TimeUtils.millis();
			}
		}
		else if(the_state==6 && TimeUtils.timeSinceMillis(timep)>500){
			the_game.setScreen(new MenuScreen(the_game));
		}

		cam.update();
		batch.setProjectionMatrix(cam.combined);

		stage.act(delta);
		stage.draw();

		batch.begin();
		main_bg.draw(batch,main_a);
		black.draw(batch,black_a);

		batch.end();

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void dispose(){

		batch.dispose();
		fortexture.dispose();
		stage.dispose();
		font.dispose();

	}

}
