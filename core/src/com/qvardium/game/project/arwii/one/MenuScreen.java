package com.qvardium.game.project.arwii.one;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuScreen extends ScreenAdapter implements InputProcessor  {

	MyGame game_forScreen;
	SpriteBatch batch;
	OrthographicCamera cam;
	Viewport viewport;
	TextureAtlas forMenuTexture;

	Stage stage;
	Image bg_img;
	ImageTextButton startGame, setingGame, resumeGame, aboutGame, L_level, M_level, H_level, backButton;
	ImageTextButton G1,G2,G3,G4,G5;
	ImageTextButton[] select_level;
	Image musicEnable, effectEnable;
	Slider slider_music, slider_effect;
	BitmapFont font;
	CheckBox sel_lang;

	MenuState mstate;
	MenuState pstate;
	Sprite left_m, right_m,centr_m,cccc;
	float left_a, right_a,centr_a,cccc_a;
	boolean press_for_a, the_first=true;
	long timep,presstime;

	Music bg_m;
	Sound btn_r,btn_b;
	String[] k_str;



	MenuScreen(MyGame game){
		game_forScreen=game;
		batch=game.batch;
		cam=game.cam;
		viewport=game.viewport;

		forMenuTexture = new TextureAtlas("menu/ui_menu.pack");
		String file="Новая игра @Продолжить @Настройки @Об авторах @Выберите уровень сложности @Сложный @Легкий @Средний @Назад @I @II @III @IV @V @";

		if(game_forScreen.for_lang==2)
			file = "New Game @Continue @Settings @About the Authors @Select the level of difficulty @Hard @Easy @Medium @Back @I @II @III @IV @V @";

		k_str = file.split(" @");

		font = new BitmapFont(Gdx.files.internal("menu/menu.fnt"));

		sel_lang = new CheckBox("", new CheckBoxStyle(new TextureRegionDrawable(forMenuTexture.findRegion("ru")),
				new TextureRegionDrawable(forMenuTexture.findRegion("en")), font, Color.GREEN));

		bg_img = new Image(forMenuTexture.findRegion("background"));
		startGame = new ImageTextButton(k_str[0],
				new ImageTextButton.ImageTextButtonStyle
						(new TextureRegionDrawable(forMenuTexture.findRegion("button")),
								new TextureRegionDrawable(forMenuTexture.findRegion("button_p")),
								new TextureRegionDrawable(forMenuTexture.findRegion("button")),
								font));
		setingGame = new ImageTextButton(k_str[2],
				new ImageTextButton.ImageTextButtonStyle
						(new TextureRegionDrawable(forMenuTexture.findRegion("button")),
								new TextureRegionDrawable(forMenuTexture.findRegion("button_p")),
								new TextureRegionDrawable(forMenuTexture.findRegion("button")),
								font));
		resumeGame = new ImageTextButton(k_str[1],
				new ImageTextButton.ImageTextButtonStyle
						(new TextureRegionDrawable(forMenuTexture.findRegion("button")),
								new TextureRegionDrawable(forMenuTexture.findRegion("button_p")),
								new TextureRegionDrawable(forMenuTexture.findRegion("button")),
								font));
		L_level = new ImageTextButton(k_str[6],
				new ImageTextButton.ImageTextButtonStyle
						(new TextureRegionDrawable(forMenuTexture.findRegion("button")),
								new TextureRegionDrawable(forMenuTexture.findRegion("button_p")),
								new TextureRegionDrawable(forMenuTexture.findRegion("button")),
								font));
		M_level = new ImageTextButton(k_str[7],
				new ImageTextButton.ImageTextButtonStyle
						(new TextureRegionDrawable(forMenuTexture.findRegion("button")),
								new TextureRegionDrawable(forMenuTexture.findRegion("button_p")),
								new TextureRegionDrawable(forMenuTexture.findRegion("button")),
								font));
		H_level = new ImageTextButton(k_str[5],
				new ImageTextButton.ImageTextButtonStyle
						(new TextureRegionDrawable(forMenuTexture.findRegion("button")),
								new TextureRegionDrawable(forMenuTexture.findRegion("button_p")),
								new TextureRegionDrawable(forMenuTexture.findRegion("button")),
								font));
		backButton = new ImageTextButton(k_str[8],
				new ImageTextButton.ImageTextButtonStyle
						(new TextureRegionDrawable(forMenuTexture.findRegion("button")),
								new TextureRegionDrawable(forMenuTexture.findRegion("button_p")),
								new TextureRegionDrawable(forMenuTexture.findRegion("button")),
								font));

		G1 = new ImageTextButton(k_str[9],
				new ImageTextButton.ImageTextButtonStyle
						(new TextureRegionDrawable(forMenuTexture.findRegion("g1")),
								new TextureRegionDrawable(forMenuTexture.findRegion("g1p")),
								new TextureRegionDrawable(forMenuTexture.findRegion("g1p")),
								font));
		G2 = new ImageTextButton(k_str[10],
				new ImageTextButton.ImageTextButtonStyle
						(new TextureRegionDrawable(forMenuTexture.findRegion("g2")),
								new TextureRegionDrawable(forMenuTexture.findRegion("g2p")),
								new TextureRegionDrawable(forMenuTexture.findRegion("g2p")),
								font));
		G3 = new ImageTextButton(k_str[11],
				new ImageTextButton.ImageTextButtonStyle
						(new TextureRegionDrawable(forMenuTexture.findRegion("g3")),
								new TextureRegionDrawable(forMenuTexture.findRegion("g3p")),
								new TextureRegionDrawable(forMenuTexture.findRegion("g3p")),
								font));
		G4 = new ImageTextButton(k_str[12],
				new ImageTextButton.ImageTextButtonStyle
						(new TextureRegionDrawable(forMenuTexture.findRegion("g4")),
								new TextureRegionDrawable(forMenuTexture.findRegion("g4p")),
								new TextureRegionDrawable(forMenuTexture.findRegion("g4p")),
								font));
		G5 = new ImageTextButton(k_str[13],
				new ImageTextButton.ImageTextButtonStyle
						(new TextureRegionDrawable(forMenuTexture.findRegion("g5")),
								new TextureRegionDrawable(forMenuTexture.findRegion("g5p")),
								new TextureRegionDrawable(forMenuTexture.findRegion("g5p")),
								font));

		select_level = new ImageTextButton[10];
		if(!game.trueLvl){
			select_level = new ImageTextButton[13];
		}

		for(int i=0;i<select_level.length; i++){
			select_level[i] = new ImageTextButton(Integer.toString(i),
					new ImageTextButton.ImageTextButtonStyle
							(new TextureRegionDrawable(forMenuTexture.findRegion("levl")),
									new TextureRegionDrawable(forMenuTexture.findRegion("levl_p")),
									new TextureRegionDrawable(forMenuTexture.findRegion("levl_p")),
									font));
		}

		slider_music = new Slider(0f, 1f, 0.1f, true, new Slider.SliderStyle(new TextureRegionDrawable(forMenuTexture.findRegion("sl_bg")),new TextureRegionDrawable(forMenuTexture.findRegion("sl_knb"))));
		slider_effect = new Slider(0f, 1f, 0.1f, true, new Slider.SliderStyle(new TextureRegionDrawable(forMenuTexture.findRegion("sl_bg")),new TextureRegionDrawable(forMenuTexture.findRegion("sl_knb"))));

		musicEnable = new Image(forMenuTexture.findRegion("effect_on"));
		effectEnable = new Image(forMenuTexture.findRegion("sound_on"));


		left_m = new Sprite(forMenuTexture.findRegion("background"), 0, 0, 600, 768);
		right_m = new Sprite(forMenuTexture.findRegion("background"), 700, 0, 600, 768);
		right_m.setPosition(700, 0);
		centr_m = new Sprite(new Texture(Gdx.files.internal("black.png")), 0, 0, 1280, 768);
		cccc = new Sprite(forMenuTexture.findRegion("background"), 0, 0, 1280, 768);

		bg_m = Gdx.audio.newMusic(Gdx.files.internal("menu/menu.mp3"));
		bg_m.setLooping(true);
		bg_m.setVolume(game_forScreen.musicVol);
		if (game_forScreen.musicVol>0f) bg_m.play();

		btn_r = Gdx.audio.newSound(Gdx.files.internal("menu/btn_right.mp3"));
		btn_b = Gdx.audio.newSound(Gdx.files.internal("menu/btn_left.mp3"));

		for(int i=0; i<select_level.length;i++){
			if(i<5) select_level[i].setPosition((i*250)+50, 500);
			if(i>=5) select_level[i].setPosition(((i-5)*250)+50, 300);
			if(!game.trueLvl && i>=10){
				select_level[i].setPosition(((i-10)*250)+550, 100);
			}
		}

		resumeGame.setPosition(10, 500);
		startGame.setPosition(10, 350);
		setingGame.setPosition(10, 200);
		L_level.setPosition(10, 500);
		M_level.setPosition(10, 350);
		H_level.setPosition(10, 200);
		backButton.setPosition(10, 50);
		slider_music.setPosition(1100, 300);
		slider_music.setSize(80, 400);
		slider_music.setValue(game_forScreen.musicVol);
		slider_effect.setPosition(900, 300);
		slider_effect.setSize(80, 400);
		slider_effect.setValue(game_forScreen.effectVol);
		musicEnable.setPosition(1100, 100);
		effectEnable.setPosition(900, 100);
		G1.setPosition(100, 300);
		G2.setPosition(320, 300);
		G3.setPosition(540, 300);
		G4.setPosition(760, 300);
		G5.setPosition(980, 300);
		sel_lang.setPosition(100, 300);

		stage = new Stage(viewport, batch);

		stage.addActor(bg_img);

		stage.addActor(startGame);
		stage.addActor(setingGame);
		stage.addActor(slider_music);
		stage.addActor(musicEnable);
		stage.addActor(slider_effect);
		stage.addActor(effectEnable);
		if(game_forScreen.curentLvl>=2) stage.addActor(resumeGame);
		stage.addActor(L_level);
		stage.addActor(M_level);
		stage.addActor(H_level);
		stage.addActor(backButton);
		stage.addActor(sel_lang);
		stage.addActor(G1);
		stage.addActor(G2);
		stage.addActor(G3);
		stage.addActor(G4);
		stage.addActor(G5);
		for(int i=0; i<select_level.length;i++){
			stage.addActor(select_level[i]);
		}

		backButton.setVisible(false);
		L_level.setVisible(false);
		M_level.setVisible(false);
		H_level.setVisible(false);
		slider_effect.setVisible(false);
		effectEnable.setVisible(false);
		slider_music.setVisible(false);
		musicEnable.setVisible(false);
		G1.setVisible(false);
		G2.setVisible(false);
		G3.setVisible(false);
		G4.setVisible(false);
		G5.setVisible(false);
		sel_lang.setVisible(false);
		for(int i=0; i<select_level.length;i++){
			select_level[i].setVisible(false);
		}


		if(game_forScreen.for_lang==2) sel_lang.setChecked(true);

		mstate = MenuState.MainMenu;
		pstate = MenuState.IamStand;
		right_a=0;
		left_a=0;
		centr_a=1;
		cccc_a=0f;
		press_for_a=false;

		timep = TimeUtils.millis();
		presstime = 1000;

		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		if(mstate==MenuState.MainMenu){
			if(startGame.isPressed() && TimeUtils.timeSinceMillis(timep)>presstime) {
				if (game_forScreen.effectVol>0f) btn_r.play(game_forScreen.effectVol);
				pstate = MenuState.NewGame;
				press_for_a=true;
				timep = TimeUtils.millis();
			}
			else if(resumeGame.isPressed() && TimeUtils.timeSinceMillis(timep)>presstime){
				if (game_forScreen.effectVol>0f) btn_r.play(game_forScreen.effectVol);

				pstate=MenuState.Resume;
				press_for_a=true;
				timep = TimeUtils.millis();
			}
			else if(setingGame.isPressed() && TimeUtils.timeSinceMillis(timep)>presstime){
				if (game_forScreen.effectVol>0f) btn_r.play(game_forScreen.effectVol);
				pstate = MenuState.Options;
				press_for_a=true;
				timep = TimeUtils.millis();
			}
		}
		else if(mstate==MenuState.NewGame){
			if(L_level.isPressed() && TimeUtils.timeSinceMillis(timep)>presstime){
				if (game_forScreen.effectVol>0f) btn_r.play(game_forScreen.effectVol);
				game_forScreen.dif=1;
				pstate = MenuState.SelectLivel;
				press_for_a=true;
				timep = TimeUtils.millis();

			}
			else if (M_level.isPressed() && TimeUtils.timeSinceMillis(timep)>presstime){
				if (game_forScreen.effectVol>0f) btn_r.play(game_forScreen.effectVol);
				game_forScreen.dif=2;
				pstate = MenuState.SelectLivel;
				press_for_a=true;
				timep = TimeUtils.millis();
			}
			else if (H_level.isPressed() && TimeUtils.timeSinceMillis(timep)>presstime){
				if (game_forScreen.effectVol>0f) btn_r.play(game_forScreen.effectVol);
				game_forScreen.dif=3;
				pstate = MenuState.SelectLivel;
				press_for_a=true;
				timep = TimeUtils.millis();
			}
			else if (backButton.isPressed() && TimeUtils.timeSinceMillis(timep)>presstime){
				if (game_forScreen.effectVol>0f) btn_b.play(game_forScreen.effectVol);
				pstate = MenuState.MainMenu;
				press_for_a=true;
				timep = TimeUtils.millis();
			}
		}
		else if(mstate==MenuState.Options){

			if(slider_music.isDragging()) {

				game_forScreen.musicVol=slider_music.getValue();
				bg_m.setVolume(game_forScreen.musicVol);
				if(game_forScreen.musicVol<=0f) bg_m.pause();
				else if (game_forScreen.musicVol>0f) bg_m.play();

			}
			if(slider_effect.isDragging()) {

				game_forScreen.effectVol=slider_effect.getValue();
				btn_b.play(game_forScreen.effectVol);

			}
			if(sel_lang.isPressed()) {
				for_options(sel_lang.isChecked());

			}


			if (backButton.isPressed() && TimeUtils.timeSinceMillis(timep)>presstime){
				if (game_forScreen.effectVol>0f) btn_b.play(game_forScreen.effectVol);
				game_forScreen.saveAll();
				pstate=MenuState.BackOpt;
				press_for_a=true;
				timep = TimeUtils.millis();
			}
		}
		else if(mstate==MenuState.SelectLivel){
			if(G1.isPressed() && TimeUtils.timeSinceMillis(timep)>presstime){
				if (game_forScreen.effectVol>0f) btn_b.play(game_forScreen.effectVol);
				game_forScreen.curentLvl=0;
				game_forScreen.saveLvl();
				pstate=MenuState.SelectN;
				press_for_a=true;
				timep = TimeUtils.millis();
			}
			else if(G2.isPressed() && TimeUtils.timeSinceMillis(timep)>presstime){
				if (game_forScreen.effectVol>0f) btn_b.play(game_forScreen.effectVol);
				game_forScreen.curentLvl=10;
				game_forScreen.saveLvl();
				pstate=MenuState.SelectN;
				press_for_a=true;
				timep = TimeUtils.millis();
			}
			else if(G3.isPressed() && TimeUtils.timeSinceMillis(timep)>presstime){
				if (game_forScreen.effectVol>0f) btn_b.play(game_forScreen.effectVol);
				game_forScreen.curentLvl=20;
				game_forScreen.saveLvl();
				pstate=MenuState.SelectN;
				press_for_a=true;
				timep = TimeUtils.millis();
			}
			else if(G4.isPressed() && TimeUtils.timeSinceMillis(timep)>presstime){
				if (game_forScreen.effectVol>0f) btn_b.play(game_forScreen.effectVol);
				game_forScreen.curentLvl=30;
				game_forScreen.saveLvl();
				pstate=MenuState.SelectN;
				press_for_a=true;
				timep = TimeUtils.millis();
			}
			else if(G5.isPressed() && TimeUtils.timeSinceMillis(timep)>presstime){
				if (game_forScreen.effectVol>0f) btn_b.play(game_forScreen.effectVol);
				game_forScreen.curentLvl=40;
				game_forScreen.saveLvl();
				pstate=MenuState.SelectN;
				press_for_a=true;
				timep = TimeUtils.millis();
			}
			else if(backButton.isPressed() && TimeUtils.timeSinceMillis(timep)>presstime){
				if (game_forScreen.effectVol>0f) btn_b.play(game_forScreen.effectVol);
				pstate=MenuState.BackN;
				press_for_a=true;
				timep = TimeUtils.millis();
			}
		}
		else if(mstate == MenuState.MainSelect){
			if(is_select_level()){
				game_forScreen.saveLvl();
				pstate=MenuState.Resume;
				press_for_a=true;
				timep = TimeUtils.millis();
			}
			else if(backButton.isPressed() && TimeUtils.timeSinceMillis(timep)>presstime){
				if (game_forScreen.effectVol>0f) btn_b.play(game_forScreen.effectVol);
				pstate=MenuState.BackS;
				press_for_a=true;
				timep = TimeUtils.millis();
			}
		}
		//--------------------------------------------------
		if(pstate == MenuState.MainMenu){
			if(press_for_a) left_a=left_a+delta+delta;
			if(left_a>=1f){
				startGame.setVisible(true);
				resumeGame.setVisible(true);
				setingGame.setVisible(true);
				backButton.setVisible(false);
				L_level.setVisible(false);
				M_level.setVisible(false);
				H_level.setVisible(false);
				press_for_a=false;
				left_a=1f;
			}
			if(!press_for_a) left_a=left_a-delta-delta;
			if(left_a<=0){
				left_a=0f;
				pstate=MenuState.IamStand;
				mstate=MenuState.MainMenu;
			}
		}
		else if (pstate==MenuState.NewGame){
			if(press_for_a) left_a=left_a+delta+delta;
			if(left_a>=1f){
				startGame.setVisible(false);
				resumeGame.setVisible(false);
				setingGame.setVisible(false);
				backButton.setVisible(true);
				L_level.setVisible(true);
				if(game_forScreen.the_medium)M_level.setVisible(true);
				if(game_forScreen.the_hard)H_level.setVisible(true);
				press_for_a=false;
				left_a=1f;
			}
			if(!press_for_a) left_a=left_a-delta-delta;
			if(left_a<=0){
				left_a=0f;
				pstate=MenuState.IamStand;
				mstate=MenuState.NewGame;
			}
		}
		else if (pstate==MenuState.BackN){
			if(press_for_a) cccc_a=cccc_a+delta+delta;
			if(cccc_a>=1f){
				G1.setVisible(false);
				G2.setVisible(false);
				G3.setVisible(false);
				G4.setVisible(false);
				G5.setVisible(false);
				backButton.setVisible(true);
				L_level.setVisible(true);
				if(game_forScreen.the_medium)M_level.setVisible(true);
				if(game_forScreen.the_hard)H_level.setVisible(true);
				press_for_a=false;
				cccc_a=1f;
			}
			if(!press_for_a) cccc_a=cccc_a-delta-delta;
			if(cccc_a<=0){
				cccc_a=0f;
				pstate=MenuState.IamStand;
				mstate=MenuState.NewGame;
			}
		}
		else if (pstate==MenuState.Options){
			if(press_for_a) left_a=left_a+delta+delta;
			if(left_a>=1f){
				startGame.setVisible(false);
				resumeGame.setVisible(false);
				setingGame.setVisible(false);
				backButton.setVisible(true);
				sel_lang.setVisible(true);
				slider_effect.setVisible(true);
				slider_music.setVisible(true);
				musicEnable.setVisible(true);
				effectEnable.setVisible(true);
				press_for_a=false;
				left_a=1f;
			}
			if(!press_for_a) {left_a=left_a-delta-delta; right_a=right_a-delta-delta;}
			if(left_a<=0 && right_a<=0){
				left_a=0f;
				right_a=0f;
				pstate=MenuState.IamStand;
				mstate=MenuState.Options;
			}
		}
		else if (pstate==MenuState.BackOpt){
			if(press_for_a) {left_a=left_a+delta+delta; right_a=right_a+delta+delta;}
			if(left_a>=1f && right_a>=1f){
				startGame.setVisible(true);
				resumeGame.setVisible(true);
				setingGame.setVisible(true);
				backButton.setVisible(false);
				sel_lang.setVisible(false);
				slider_effect.setVisible(false);
				slider_music.setVisible(false);
				musicEnable.setVisible(false);
				effectEnable.setVisible(false);
				press_for_a=false;
				left_a=1f;
				right_a=1f;
			}
			if(!press_for_a) left_a=left_a-delta-delta;
			if(left_a<=0){
				left_a=0f;
				right_a=0f;
				pstate=MenuState.IamStand;
				mstate=MenuState.MainMenu;
			}
		}
		else if(pstate==MenuState.SelectLivel){
			if(press_for_a) cccc_a=cccc_a+delta+delta;
			if(cccc_a>=1f){
				backButton.setVisible(true);
				L_level.setVisible(false);
				M_level.setVisible(false);
				H_level.setVisible(false);
				G1.setVisible(true);
				press_for_a=false;
				right_a=0f;
				cccc_a=1f;
				if(game_forScreen.dif==1){
					for_select_glava();
				}
				else if(game_forScreen.dif==2){
					for_select_glava();
				}
				else if(game_forScreen.dif==3){
					for_select_glava();
				}
			}
			if(!press_for_a) cccc_a=cccc_a-delta-delta;
			if(cccc_a<=0){
				cccc_a=0f;
				pstate=MenuState.IamStand;
				mstate=MenuState.SelectLivel;
			}
		}
		else if(pstate==MenuState.Resume){
			if(press_for_a) centr_a=centr_a+delta+delta;
			if(centr_a>=1f){
				press_for_a=false;
				centr_a=1f;
			}
			if(!press_for_a){
				for_select_resume();
			}
		}
		else if(pstate==MenuState.SelectN){
			if(press_for_a) cccc_a=cccc_a+delta+delta;
			if(cccc_a>=1f){
				press_for_a=false;
				backButton.setVisible(true);

				G1.setVisible(false);
				G2.setVisible(false);
				G3.setVisible(false);
				G4.setVisible(false);
				G5.setVisible(false);

				right_a=0f;
				cccc_a=1f;
				int tmp_pp=0;
				if(game_forScreen.dif==1){
					tmp_pp=game_forScreen.max_lvl_1-game_forScreen.curentLvl;
				}
				else if(game_forScreen.dif==2){
					tmp_pp=game_forScreen.max_lvl_2-game_forScreen.curentLvl;
				}
				else if(game_forScreen.dif==3){
					tmp_pp=game_forScreen.max_lvl_3-game_forScreen.curentLvl;
				}

				if(tmp_pp>=9 && game_forScreen.trueLvl) tmp_pp=10;
				else if(tmp_pp>=12 && !game_forScreen.trueLvl) tmp_pp=13;

				for(int i=0; i<tmp_pp;i++){
					select_level[i].setVisible(true);
					select_level[i].setText(Integer.toString(game_forScreen.curentLvl+(i+1)));
				}
			}
			if(!press_for_a)cccc_a=cccc_a-delta-delta;
			if(cccc_a<=0f){
				cccc_a=0f;
				pstate=MenuState.IamStand;
				mstate=MenuState.MainSelect;
			}
		}
		else if(pstate==MenuState.BackS){
			if(press_for_a) cccc_a=cccc_a+delta+delta;
			if(cccc_a>=1f){
				backButton.setVisible(true);
				for(int i=0; i<select_level.length;i++){
					select_level[i].setVisible(false);
				}
				G1.setVisible(true);
				press_for_a=false;
				right_a=0f;
				cccc_a=1f;
				if(game_forScreen.dif==1){
					for_select_glava();
				}
				else if(game_forScreen.dif==2){
					for_select_glava();
				}
				else if(game_forScreen.dif==3){
					for_select_glava();
				}
			}
			if(!press_for_a) cccc_a=cccc_a-delta-delta;
			if(cccc_a<=0){
				cccc_a=0f;
				pstate=MenuState.IamStand;
				mstate=MenuState.SelectLivel;
			}
		}

		if(the_first){
			centr_a-=delta;
			if(centr_a<=0.01){
				centr_a=0f;
				the_first=false;
			}
		}

		cam.update();
		batch.setProjectionMatrix(cam.combined);

		stage.act(delta);
		stage.draw();
		batch.begin();
		right_m.draw(batch,right_a);
		left_m.draw(batch,left_a);
		centr_m.draw(batch, centr_a);
		cccc.draw(batch, cccc_a);
		batch.end();
	}

	public boolean is_select_level(){
		for (int i =0;i<select_level.length;i++){
			if(select_level[i].isPressed() && TimeUtils.timeSinceMillis(timep)>presstime) {
				if (game_forScreen.effectVol>0f) btn_b.play(game_forScreen.effectVol);
				game_forScreen.curentLvl=game_forScreen.curentLvl+(i+1);
				timep = TimeUtils.millis();
				return true;
			}
		}
		return false;
	}

	public void for_select_glava(){
		int sk=game_forScreen.max_lvl_1;
		if (game_forScreen.dif==1) sk=game_forScreen.max_lvl_1;
		else if(game_forScreen.dif==2) sk=game_forScreen.max_lvl_2;
		else if(game_forScreen.dif==3) sk=game_forScreen.max_lvl_3;
		if(sk>10 && game_forScreen.trueLvl) G2.setVisible(true);
		if(sk>20 && game_forScreen.trueLvl) G3.setVisible(true);
		if(sk>30 && game_forScreen.trueLvl) G4.setVisible(true);
		if(sk>40 && game_forScreen.trueLvl) G5.setVisible(true);
		G1.setChecked(false);
		G2.setChecked(false);
		G3.setChecked(false);
		G4.setChecked(false);
		G5.setChecked(false);
	}

	public void for_select_resume(){
		if(game_forScreen.curentLvl==1 && game_forScreen.dif==1)game_forScreen.setScreen(new Sz(game_forScreen));
		else if(game_forScreen.curentLvl==2 && game_forScreen.dif==1) game_forScreen.setScreen(new tutor_2(game_forScreen));
		else if ((game_forScreen.curentLvl==1 || game_forScreen.curentLvl==2) && (game_forScreen.dif==2 || game_forScreen.dif==3)) game_forScreen.setScreen(new Glava1(game_forScreen));
		else if (game_forScreen.curentLvl>2 && game_forScreen.curentLvl<=10) game_forScreen.setScreen(new Glava1(game_forScreen));
		else if (game_forScreen.curentLvl>10 && game_forScreen.curentLvl<=13 && !game_forScreen.trueLvl) game_forScreen.setScreen(new Glava1(game_forScreen));
		else if (game_forScreen.curentLvl==11 && game_forScreen.trueLvl) game_forScreen.setScreen(new Sz(game_forScreen));
		else if (game_forScreen.curentLvl>10 && game_forScreen.curentLvl<=20 && game_forScreen.trueLvl) game_forScreen.setScreen(new Glava2(game_forScreen));
		else if (game_forScreen.curentLvl>20 && game_forScreen.curentLvl<=30 && game_forScreen.trueLvl) game_forScreen.setScreen(new Glava3(game_forScreen));
		else if (game_forScreen.curentLvl>30 && game_forScreen.curentLvl<=40 && game_forScreen.trueLvl) game_forScreen.setScreen(new Glava4(game_forScreen));
		else if (game_forScreen.curentLvl==47 && game_forScreen.trueLvl && game_forScreen.trueLvl) game_forScreen.setScreen(new Sz(game_forScreen));
		else if (game_forScreen.curentLvl>40 && game_forScreen.curentLvl<=49 && game_forScreen.trueLvl) game_forScreen.setScreen(new Glava5(game_forScreen));
		else if (game_forScreen.curentLvl==50 && game_forScreen.trueLvl) game_forScreen.setScreen(new Last_lvl(game_forScreen));
		bg_m.stop();
	}

	enum MenuState{
		MainMenu, NewGame, SelectLivel, Options, BackOpt, BackN, Resume, SelectN, IamStand, MainSelect, BackS
	}

	public void for_options(boolean lang){
		String fff="";
		if(!lang){
			fff = "Новая игра @Продолжить @Настройки @Сложный @Легкий @Средний @Назад";
			game_forScreen.for_lang=3;
		}
		else if(lang){
			fff = "New Game @Continue @Settings @Hard @Easy @Medium @Back";
			game_forScreen.for_lang=2;
		}
		String[] tstr;
		tstr = fff.split(" @");
		startGame.setText(tstr[0]);
		setingGame.setText(tstr[2]);
		resumeGame.setText(tstr[1]);
		L_level.setText(tstr[4]);
		M_level.setText(tstr[5]);
		H_level.setText(tstr[3]);
		backButton.setText(tstr[6]);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void dispose(){

		batch.dispose();
		forMenuTexture.dispose();
		stage.dispose();
		font.dispose();
		bg_m.dispose();
		btn_r.dispose();
		btn_b.dispose();

	}

	@Override
	public boolean keyDown(int keycode) {

		if(keycode == Keys.BACK){
			if(mstate==MenuState.MainMenu) Gdx.app.exit();

		}
		else if(mstate==MenuState.NewGame && TimeUtils.timeSinceMillis(timep)>presstime){
			if (game_forScreen.effectVol>0f) btn_b.play(game_forScreen.effectVol);
			pstate = MenuState.MainMenu;
			press_for_a=true;
			timep = TimeUtils.millis();
		}
		else if(mstate==MenuState.Options && TimeUtils.timeSinceMillis(timep)>presstime){
			if (game_forScreen.effectVol>0f) btn_b.play(game_forScreen.effectVol);
			game_forScreen.saveAll();
			pstate=MenuState.BackOpt;
			press_for_a=true;
			timep = TimeUtils.millis();
		}
		else if(mstate==MenuState.SelectLivel && TimeUtils.timeSinceMillis(timep)>presstime){
			if (game_forScreen.effectVol>0f) btn_b.play(game_forScreen.effectVol);
			pstate=MenuState.BackN;
			press_for_a=true;
			timep = TimeUtils.millis();
		}
		else if(mstate == MenuState.MainSelect && TimeUtils.timeSinceMillis(timep)>presstime){
			if (game_forScreen.effectVol>0f) btn_b.play(game_forScreen.effectVol);
			pstate=MenuState.BackS;
			press_for_a=true;
			timep = TimeUtils.millis();
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
