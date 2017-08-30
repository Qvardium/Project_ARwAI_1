package com.qvardium.game.project.arwii.one;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.qvardium.game.project.arwii.one.ByPlayer.RobotState;

public class LabirintScr extends ScreenAdapter {

	// --------общие переменные-------------
	MyGame game_forScreen;
	SpriteBatch batch;
	OrthographicCamera cam;
	OrthographicCamera camHUD;
	Viewport viewport;
	Viewport viewportHUD;
	// -------------------------------------
	// --------для лабиринта----------------

	int x, y;
	int ww, hh;
	int CellSize = 200;
	byte[][] lab;
	int start_loc, finish_lok, flx, fly;

	TextureAtlas walls;
	Emaze em;
	Kmaze km;
	Pmaze pm;

	Music bg_music;
	// -------------------------------------

	// --------для игрока-------------------

	ByPlayer player;
	int pl_x, pl_y, i;
	int robot_speed = 100;
	float robot_jump = 5, robot_jump_temp = 5;

	// -------------------------------------

	// --------для интерфейса---------------
	TextureAtlas ui_atl;

	Stage stage;
	Touchpad touchpad;
	TouchpadStyle touchpadStyle;
	float maxXrange, maxYrange, minXrange, minYrange, speed_cam = 20;
	float xrang = 6.3f, yrang = 3.8f;
	float maxXr1, maxYr1;

	ImageButton jumpButton, actionButton, nextButton, radarButton;

	CheckBox sel;
	CheckBoxStyle style_ch;
	BitmapFont gfont;

	Label informFPS, timer_info;
	String g_dif;

	boolean radarIsOn;
	byte[][] radar_lab;

	Sprite bg_black;
	float bg_black_a;
	boolean end_game=false;
	byte for_end_game=0;

	// -------------------------------------
	// --------для задачи----------------
	boolean theQuest = false;
	Array<TheQuest1> tq1;
	Array<TheQuest2> tq2;
	byte temp_number_qest;
	TheQuest1 temp_q1;
	TheQuest2 temp_q2;

	TextureAtlas q_atl;

	Stage q_stage1;
	Image q_kpk1;
	ImageButton q_up1, q_down1, q_yes1;
	BitmapFont q_font;
	Label q_1, q_2, q_3, q_otv;
	ImageButton q_sel1, q_sel2;
	Image q_sel3;
	int q1_1, q1_2;
	long q_tim;

	Stage q_stage2;
	Image q_kpk2;
	ImageButton q_up2, q_down2, q_yes2, q_off2;
	Image q_sel4;
	Label q_select_numb, q_znak, q_zag_numb;
	int[] q_chisla;
	boolean q2_ok = false;

	// ------------------------------------

	// ----ДЛЯ КПК-------------------------
	private static final float HALF_TAP_SQUARE_SIZE = 20.0f;
	private static final float TAP_COUNT_INTERVAL = 0.4f;
	private static final float LONG_PRESS_DURATION = 1.1f;
	private static final float MAX_FLING_DELAY = 0.15f;

	boolean the_first_start = true;
	boolean kpk_on = true;
	boolean on_radar, on_menu, on_mssage = true;

	private GestureDetector gestureDetector;
	GestureHandler gghh;

	Stage k_stage;
	TextureAtlas k_atl;
	Image k_kpk, k_bg;
	ImageButton k_up, k_down, k_exit, k_menu, k_radar, k_mssage, k_yes, k_reset;
	BitmapFont k_font;
	Label k_msg,k_msg_name;
	InputMultiplexer multiplexer;

	Slider slider_music, slider_effect;
	Image music_k, effect_k;
	Sound btn_b;

	String[] k_str;
	String[] k_for_str;
	String k_str1;
	int k_curent = 0;
	String k_name;
	long press_time;
	// ------------------------------------

	//-------------ДЛЯ СИСТЕМНОГО СООБЩЕНИЯ-----------
	boolean for_start_system_msg=true;
	ImageTextButton img_system;
	TextureAtlas bbbg;
	Sound trevoga;

	boolean for_onoff_sys=true, clush_game=false;
	Texture system_btn_on, system_btn_off;
	int sys_x=0,sys_y=0,sys1x=0,sys1y=0;
	String k_sms_menu;
	//------------------------------------------------
	String game_sms_level;

	//---------Для среднего уровня сложности-----------
	boolean pokazat_z=true;
	Texture vzlom_z;
	int[] vzlz_x,vzlz_y, povt_x,povt_y;
	//-------------------------------------------------

	//---------Для СЛОЖНОГО уровня сложности-----------
	boolean pokaz_o=true, otkryt_radar=false;
	Texture vzlom_o;
	int[] vzlo_x,vzlo_y, povt_x1,povt_y1;
	byte[][] temp_radar_lab;
	//-------------------------------------------------

	public LabirintScr(MyGame game) {

		game_forScreen = game;
		batch = game.batch;
		cam = game.cam;
		viewport = game.viewport;
		viewport.update(1280, 768, true);
		cam.position.x = 640;
		cam.position.y = 384;
		camHUD = new OrthographicCamera();
		viewportHUD = new FitViewport(1280, 768, camHUD);


		if(game.dif==1) g_dif = "Легкий";
		if(game.dif==2) g_dif = "Средний";
		if(game.dif==3) g_dif = "Сложный";
		k_sms_menu = "Выйти в главное меню"+"\n"+"\n"+"\n"+"\n"+"Начать заново";
		game_sms_level= " Уровень ";

		if(game_forScreen.for_lang==2){
			if(game.dif==1) g_dif = "Easy";
			if(game.dif==2) g_dif = "Medium";
			if(game.dif==3) g_dif = "Hard";
			k_sms_menu = "Exit to menu"+"\n"+"\n"+"\n"+"\n"+"Restart that level";
			game_sms_level= " Level ";
		}
		// ------для лабиринта----------------------------
		if (game_forScreen.dif == 3) {
			ww = game_forScreen.curentLvl + 16;
			hh = (ww / 2) + 10;
		} else if (game_forScreen.dif == 2) {
			ww = game_forScreen.curentLvl + 13;
			hh = (ww / 2) + 7;
		} else {
			ww = game_forScreen.curentLvl + 11;
			hh = (ww / 2) + 2;
		}
		lab = new byte[ww + ww + 1][hh + hh + 1];

		walls = new TextureAtlas(Gdx.files.internal("epilog/tutor.pack"));

		if (game_forScreen.dif == 3) {
			km = new Kmaze(ww, hh);
			km.GeneratMaze();
			km.ConvMaze(lab);
			km.Quest(lab, game_forScreen.dif-1);

			boolean proverka_na_sf;
			do {
				km.StartFinishAction(lab);
				start_loc = km.start_loc;
				finish_lok = km.finish_lok;
				if (start_loc == 0 || finish_lok == 0)
					proverka_na_sf = true;
				else
					proverka_na_sf = false;
			} while (proverka_na_sf);
		} else if (game_forScreen.dif == 2) {
			pm = new Pmaze(ww, hh);
			pm.GeneratMaze();
			pm.ConvMaze(lab);
			pm.Quest(lab, game_forScreen.dif-1);

			boolean proverka_na_sf;
			do {
				pm.StartFinishAction(lab);
				start_loc = pm.start_loc;
				finish_lok = pm.finish_lok;
				if (start_loc == 0 || finish_lok == 0)
					proverka_na_sf = true;
				else
					proverka_na_sf = false;
			} while (proverka_na_sf);
		} else {
			em = new Emaze(ww, hh);
			em.GeneratMaze();
			em.ConvMaze(lab);
			em.Quest(lab, 0);

			boolean proverka_na_sf;
			do {
				em.StartFinishAction(lab);
				start_loc = em.start_loc;
				finish_lok = em.finish_lok;
				if (start_loc == 0 || finish_lok == 0)
					proverka_na_sf = true;
				else
					proverka_na_sf = false;
			} while (proverka_na_sf);
		}
		flx = 0;
		fly = 0;

		int isp = 0;
		for (x = 1; x < ww + ww - 1; x++)
			for (y = 1; y < hh + hh - 1; y++) {
				if (lab[x][y] == 25 || lab[x][y] == 26 || lab[x][y] == 27)
					isp++;
			}

		if (isp > 0) {
			tq1 = new Array<TheQuest1>();
			tq2 = new Array<TheQuest2>();
			for (x = 1; x < ww + ww - 1; x++)
				for (y = 1; y < hh + hh - 1; y++)
					if (lab[x][y] == 25 || lab[x][y] == 26 || lab[x][y] == 27) {
						int kkk = MathUtils.random(1);
						if (kkk == 1)
							tq1.add(new TheQuest1(x, y, game_forScreen.curentLvl));
						else if (kkk == 0)
							tq2.add(new TheQuest2(x, y, game_forScreen.curentLvl));
					}
		}
		if (game_forScreen.curentLvl == 10) {
			boolean fflx;
			fflx = true;
			do {
				for (int b1 = tq1.size - 1; b1 > 0; b1--) {
					int kkk = MathUtils.random(1);
					if (kkk == 1) {
						flx = tq1.get(b1).x;
						fly = tq1.get(b1).y;
						fflx = false;
						break;
					}
				}
				if (flx == 0 && fly == 0) {
					for (int b1 = tq2.size - 1; b1 > 0; b1--) {
						int kkk = MathUtils.random(1);
						if (kkk == 1) {
							flx = tq2.get(b1).x;
							fly = tq2.get(b1).y;
							fflx = false;
							break;
						}
					}
				}
			} while (fflx);
		}


		// -----------------------------------------------

		// -----------для интерфейса----------------------

		radar_lab = new byte[ww + ww + 1][hh + hh + 1];

		for(int bk=0; bk<ww+ww+1;bk++){
			for(int bg=0;bg<hh+hh+1;bg++){
				if(lab[bk][bg]==98) radar_lab[bk][bg]=98;
				else if(lab[bk][bg]==99) radar_lab[bk][bg]=99;
				else if(lab[bk][bg]==0) radar_lab[bk][bg]=0;
				else if(lab[bk][bg]==25 || lab[bk][bg]==26 || lab[bk][bg]==27) radar_lab[bk][bg]=2;
				else if(lab[bk][bg]!=0) radar_lab[bk][bg]=1;
			}
		}

		ui_atl = new TextureAtlas("ui_game.pack");

		touchpadStyle = new TouchpadStyle();
		touchpadStyle.background = new TextureRegionDrawable(ui_atl.findRegion("touch_tabel"));
		touchpadStyle.knob = new TextureRegionDrawable(ui_atl.findRegion("touch_tabel_knob"));
		touchpad = new Touchpad(10, touchpadStyle);
		touchpad.setBounds(15, 15, 300, 300);
		touchpad.setPosition(30, 30);

		jumpButton = new ImageButton(new TextureRegionDrawable(ui_atl.findRegion("jump")),
				new TextureRegionDrawable(ui_atl.findRegion("jump_pres")));
		actionButton = new ImageButton(new TextureRegionDrawable(ui_atl.findRegion("action")),
				new TextureRegionDrawable(ui_atl.findRegion("action_pres")));
		nextButton = new ImageButton(new TextureRegionDrawable(ui_atl.findRegion("zoom_btn_in")),
				new TextureRegionDrawable(ui_atl.findRegion("zoom_btn_in_press")));
		radarButton = new ImageButton(new TextureRegionDrawable(ui_atl.findRegion("radar_a")),
				new TextureRegionDrawable(ui_atl.findRegion("radar_a")));

		gfont = new BitmapFont(Gdx.files.internal("111.fnt"));
		style_ch = new CheckBoxStyle(new TextureRegionDrawable(ui_atl.findRegion("select_cam_rob")),
				new TextureRegionDrawable(ui_atl.findRegion("select_cam_rob_pres")), gfont, Color.GREEN);
		sel = new CheckBox("Camera", style_ch);

		informFPS = new Label("FPS: " + Gdx.graphics.getFramesPerSecond(),
				new Label.LabelStyle(gfont, gfont.getColor()));
		timer_info = new Label("", new Label.LabelStyle(gfont, gfont.getColor()));

		stage = new Stage(viewportHUD, batch);
		stage.addActor(touchpad);
		stage.addActor(jumpButton);
		stage.addActor(actionButton);
		stage.addActor(nextButton);
		stage.addActor(radarButton);
		// stage.addActor(sel);
		stage.addActor(informFPS);
		stage.addActor(timer_info);

		maxXrange = ((ww + ww + 1) * CellSize) - 640;
		maxYrange = ((hh + hh + 1) * CellSize) - 380;
		maxXr1 = 100;
		maxYr1 = 100;
		minXrange = 640;
		minYrange = 384;

		jumpButton.setPosition(camHUD.position.x + 500, camHUD.position.y - 210);
		actionButton.setPosition(camHUD.position.x + 350, camHUD.position.y - 210);
		sel.setPosition(camHUD.position.x, camHUD.position.y - 340);
		informFPS.setPosition(camHUD.position.x - 630, camHUD.position.y + 130);
		nextButton.setPosition(camHUD.position.x + 500, camHUD.position.y - 350);
		radarButton.setPosition(camHUD.position.x + 340, camHUD.position.y + 184);
		timer_info.setPosition(camHUD.position.x - 50, camHUD.position.y + 330);

		nextButton.setVisible(false);
		sel.setChecked(true);
		radarIsOn = false;

		bg_black = new Sprite(new Texture(Gdx.files.internal("black.png")), 0, 0, 1280, 768);
		bg_black.setPosition(camHUD.position.x-640, camHUD.position.y-384);
		bg_black_a = 1f;
		// -----------------------------------------------

		// -------------для игрока-------------------------

		player = new ByPlayer(start_loc * CellSize, 200, game_forScreen.curentLvl, game_forScreen.trueLvl);
		player.RobotMay(true, true, true, true, false, false, false);

		// ------------------------------------------------

		// ------------Для ЗАДАЧИ ---------------------------

		for_qqq();

		// -----ДЛЯ КПК----------------
		float kkk = 0;
		if (game_forScreen.curentLvl >= 3 && game_forScreen.curentLvl <= 5) {
			kkk = (hh + hh + 1);
			kkk = 668 / kkk;
		} else {
			kkk = (ww + ww + 1);
			kkk = 850 / kkk;
		}
		gghh = new GestureHandler(kkk, 53, 50, ww, hh);
		gestureDetector = new GestureDetector(HALF_TAP_SQUARE_SIZE, TAP_COUNT_INTERVAL, LONG_PRESS_DURATION,
				MAX_FLING_DELAY, gghh);
		k_atl = new TextureAtlas(Gdx.files.internal("radar/ui_radar.pack"));
		k_kpk = new Image(k_atl.findRegion("main_radar"));
		k_bg = new Image(k_atl.findRegion("main_bg"));
		k_font = new BitmapFont(Gdx.files.internal("radar/kpk.fnt"));
		k_up = new ImageButton(new TextureRegionDrawable(q_atl.findRegion("up")),
				new TextureRegionDrawable(q_atl.findRegion("up_p")));
		k_down = new ImageButton(new TextureRegionDrawable(q_atl.findRegion("down")),
				new TextureRegionDrawable(q_atl.findRegion("down_p")));
		k_menu = new ImageButton(new TextureRegionDrawable(k_atl.findRegion("r_menu")),
				new TextureRegionDrawable(k_atl.findRegion("r_menu_p")));
		k_exit = new ImageButton(new TextureRegionDrawable(k_atl.findRegion("r_exit")),
				new TextureRegionDrawable(k_atl.findRegion("r_exit_p")));
		k_radar = new ImageButton(new TextureRegionDrawable(k_atl.findRegion("r_rad")),
				new TextureRegionDrawable(k_atl.findRegion("r_rad_p")));
		k_mssage = new ImageButton(new TextureRegionDrawable(k_atl.findRegion("r_mess")),
				new TextureRegionDrawable(k_atl.findRegion("r_mess_p")));

		k_yes = new ImageButton(new TextureRegionDrawable(q_atl.findRegion("yes")),
				new TextureRegionDrawable(q_atl.findRegion("yes_p")));
		k_reset = new ImageButton(new TextureRegionDrawable(k_atl.findRegion("reset")),
				new TextureRegionDrawable(k_atl.findRegion("reset_p")));

		slider_music = new Slider(0f, 1f, 0.1f, true, new Slider.SliderStyle(new TextureRegionDrawable(k_atl.findRegion("slider_s")),new TextureRegionDrawable(k_atl.findRegion("slider_p"))));
		slider_effect = new Slider(0f, 1f, 0.1f, true, new Slider.SliderStyle(new TextureRegionDrawable(k_atl.findRegion("slider_s")),new TextureRegionDrawable(k_atl.findRegion("slider_p"))));

		music_k = new Image(k_atl.findRegion("sound_on"));
		effect_k = new Image(k_atl.findRegion("effect_on"));

		k_msg = new Label("", new Label.LabelStyle(k_font, k_font.getColor()));
		k_msg_name = new Label("", new Label.LabelStyle(k_font, k_font.getColor()));
		k_stage = new Stage(viewportHUD, batch);

		k_stage.addActor(k_bg);
		k_stage.addActor(k_kpk);
		k_stage.addActor(k_up);
		k_stage.addActor(k_down);
		k_stage.addActor(k_menu);
		k_stage.addActor(k_exit);
		k_stage.addActor(k_radar);
		k_stage.addActor(k_mssage);
		k_stage.addActor(k_msg);
		k_stage.addActor(k_msg_name);
		k_stage.addActor(k_yes);
		k_stage.addActor(k_reset);
		k_stage.addActor(slider_music);
		k_stage.addActor(slider_effect);
		k_stage.addActor(music_k);
		k_stage.addActor(effect_k);

		k_up.setPosition(camHUD.position.x - 240, camHUD.position.y + 280);
		k_down.setPosition(camHUD.position.x - 240, camHUD.position.y - 330);
		k_menu.setPosition(camHUD.position.x + 290, camHUD.position.y + 200);
		k_mssage.setPosition(camHUD.position.x + 290, camHUD.position.y + 50);
		k_radar.setPosition(camHUD.position.x + 290, camHUD.position.y - 100);
		k_exit.setPosition(camHUD.position.x + 290, camHUD.position.y - 250);
		k_msg.setPosition(camHUD.position.x - 590, camHUD.position.y);
		k_msg_name.setPosition(camHUD.position.x - 590, camHUD.position.y+250);
		k_yes.setPosition(camHUD.position.x - 500, camHUD.position.y +50);
		k_reset.setPosition(camHUD.position.x - 500, camHUD.position.y - 150);
		slider_music.setPosition(camHUD.position.x - 120, camHUD.position.y - 200);
		slider_effect.setPosition(camHUD.position.x - 320, camHUD.position.y - 200);
		slider_music.setSize(50, 400);
		slider_effect.setSize(50, 400);
		music_k.setPosition(camHUD.position.x - 150, camHUD.position.y - 320);
		effect_k.setPosition(camHUD.position.x - 350, camHUD.position.y - 320);
		k_bg.setPosition(51, 50);
		k_yes.setVisible(false);
		k_reset.setVisible(false);
		slider_music.setVisible(false);
		slider_effect.setVisible(false);
		music_k.setVisible(false);
		effect_k.setVisible(false);
		k_up.setVisible(false);



		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(k_stage);
		multiplexer.addProcessor(gestureDetector);

		for_str();

		// ----------------------------

		press_time = TimeUtils.millis();
		Gdx.input.setInputProcessor(multiplexer);


		bg_music_on();

		btn_b = Gdx.audio.newSound(Gdx.files.internal("sounds/clic.mp3"));

		game_forScreen.for_reset=false;

		if(game.dif>=2) for_midl_dif_c();
		if(game.dif==3) for_hard_dif_c();
	}

	public void for_midl_dif_c(){
		pokazat_z=false;
		vzlom_z = new Texture(Gdx.files.internal("modulez.jpg"));

		vzlz_x = new int[3];
		vzlz_y = new int[3];
		povt_x = new int[3];
		povt_y = new int[3];

		int iiii=0;
		do {
			for (x = 1; x < ww + ww - 1; x++){
				for (y = 2; y < hh + hh - 2; y++) {
					if (lab[x][y] == 0 && lab[x+1][y] == 0 && lab[x-1][y] == 0
							&& lab[x][y-1] == 21 && lab[x][y+1] == 21
							&& MathUtils.random(2)==1){
						boolean thth=true;
						if(iiii>=1){
							for(int kbkb=0;kbkb<iiii;kbkb++){
								if(povt_x[kbkb]==x && povt_y[kbkb]==y) thth=false;
							}
						}
						if(thth){
							vzlz_x[iiii]=x;
							vzlz_y[iiii]=y;
							povt_x[iiii]=x;
							povt_y[iiii]=y;
							iiii++;
							if (iiii==3) break;
						}
					}
				}
				if (iiii==3) break;
			}

		} while (iiii!=3);

	}

	public void for_hard_dif_c(){
		pokaz_o=false;
		vzlom_o = new Texture(Gdx.files.internal("moduleo.jpg"));

		vzlo_x = new int[3];
		vzlo_y = new int[3];
		povt_x1 = new int[3];
		povt_y1 = new int[3];

		int iiii=0;
		do {
			for (x = 1; x < ww + ww - 1; x++){
				for (y = 2; y < hh + hh - 2; y++) {
					if (lab[x][y] == 0 && lab[x+1][y] == 0 && lab[x-1][y] == 0
							&& lab[x][y-1] == 21 && lab[x][y+1] == 21
							&& MathUtils.random(2)==1){
						boolean thth=true;
						if(iiii>=1){
							for(int kbkb=0;kbkb<iiii;kbkb++){
								if(povt_x[kbkb]==x && povt_y[kbkb]==y && povt_x1[kbkb]==x && povt_y1[kbkb]==y) thth=false;
							}
						}
						if(thth){
							vzlo_x[iiii]=x;
							vzlo_y[iiii]=y;
							povt_x1[iiii]=x;
							povt_y1[iiii]=y;
							iiii++;
							if (iiii==3) break;
						}
					}
				}
				if (iiii==3) break;
			}

		} while (iiii!=3);

		temp_radar_lab = new byte[ww + ww + 1][hh + hh + 1];

		for(int bk=0; bk<ww+ww+1;bk++){
			for(int bg=0;bg<hh+hh+1;bg++){
				temp_radar_lab[bk][bg]=radar_lab[bk][bg];
			}
		}

		for(int bk=0; bk<ww+ww+1;bk++){
			for(int bg=0;bg<hh+hh+1;bg++){
				if(radar_lab[bk][bg]==0) radar_lab[bk][bg]=1;
				if(radar_lab[bk][bg]==2) radar_lab[bk][bg]=1;
			}
		}
	}

	public void bg_music_on() {
		// TODO Auto-generated method stub
		bg_music = Gdx.audio.newMusic(Gdx.files.internal("sounds/1.mp3"));
		bg_music.setLooping(true);
		bg_music.setVolume(game_forScreen.musicVol);
		if(game_forScreen.musicVol>0f) bg_music.play();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (the_first_start){
			for_first_start();
			if(bg_black_a>0.01f && !end_game) {
				bg_black_a-=0.01f;
				if(bg_black_a<=0.02f && !end_game) bg_black_a=0;
				camHUD.update();
				batch.setProjectionMatrix(camHUD.combined);

				batch.begin();
				bg_black.draw(batch, bg_black_a);
				batch.end();
			}
		}
		else if (kpk_on)
			for_kpk_start();
		else if (!kpk_on && !theQuest)
			the_main_game(delta);
		else if (theQuest)
			the_quest(delta);

		if(end_game) this_for_end_game();
	}

	private void this_for_end_game() {

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
			if(for_end_game==1) game_forScreen.setScreen(new MenuScreen(game_forScreen));
			else if (for_end_game==2) game_forScreen.setScreen(new TheNextLevel(game_forScreen));
		}
	}

	void for_first_start() {
		for_kpk_start();

	}

	void for_kpk_start() {

		WherePlayer();

		camHUD.update();
		batch.setProjectionMatrix(camHUD.combined);

		k_bg.setVisible(true);
		k_stage.act(Gdx.graphics.getDeltaTime());
		k_stage.draw();

		batch.begin();

		if (k_menu.isPressed()&& TimeUtils.timeSinceMillis(press_time) > 300) {
			btn_b.play(game_forScreen.effectVol);
			on_menu = true;
			on_mssage = false;
			on_radar = false;
			gghh.on_radar = on_radar;
			k_up.setVisible(false);
			slider_effect.setVisible(false);
			slider_music.setVisible(false);
			music_k.setVisible(false);
			effect_k.setVisible(false);
			k_msg_name.setVisible(false);
			k_down.setVisible(true);
			k_msg.setPosition(camHUD.position.x - 400, camHUD.position.y);
			k_msg.setText(k_sms_menu);
			k_msg.setVisible(true);
			k_yes.setVisible(true);
			k_reset.setVisible(true);
			press_time = TimeUtils.millis();

		} else if (k_mssage.isPressed()&& TimeUtils.timeSinceMillis(press_time) > 300) {
			btn_b.play(game_forScreen.effectVol);
			on_menu = false;
			on_mssage = true;
			on_radar = false;
			gghh.on_radar = on_radar;
			k_msg.setText(k_str[k_curent]);
			k_msg.setPosition(camHUD.position.x - 590, camHUD.position.y);
			k_msg_name.setVisible(true);
			k_up.setVisible(true);
			k_down.setVisible(true);
			if (k_curent == 0)
				k_up.setVisible(false);
			if (k_curent == k_str.length - 1)
				k_down.setVisible(false);
			k_msg.setVisible(true);
			k_yes.setVisible(false);
			slider_effect.setVisible(false);
			slider_music.setVisible(false);
			k_reset.setVisible(false);
			music_k.setVisible(false);
			effect_k.setVisible(false);
			press_time = TimeUtils.millis();
		} else if (k_radar.isPressed()&& TimeUtils.timeSinceMillis(press_time) > 300) {
			btn_b.play(game_forScreen.effectVol);
			on_menu = false;
			on_mssage = false;
			on_radar = true;
			gghh.on_radar = on_radar;
			k_msg_name.setVisible(false);
			k_up.setVisible(false);
			k_down.setVisible(false);
			k_msg.setVisible(false);
			k_yes.setVisible(false);
			slider_effect.setVisible(false);
			slider_music.setVisible(false);
			k_reset.setVisible(false);
			music_k.setVisible(false);
			effect_k.setVisible(false);
			float kkk = 0;
			if (game_forScreen.curentLvl >= 3 && game_forScreen.curentLvl <= 5) {
				kkk = (hh + hh + 1);
				gghh.zzz = 668 / kkk;
			} else {
				kkk = (ww + ww + 1);
				gghh.zzz = 850 / kkk;
			}

			gghh.dx = 53;
			gghh.dy = 50;
			press_time = TimeUtils.millis();
		} else if (k_exit.isPressed() && TimeUtils.timeSinceMillis(press_time) > 1000) {
			btn_b.play(game_forScreen.effectVol);
			Gdx.input.setInputProcessor(stage);
			on_menu = false;
			on_mssage = false;
			on_radar = false;
			gghh.on_radar = on_radar;
			k_msg_name.setVisible(false);
			k_up.setVisible(false);
			k_down.setVisible(false);
			k_msg.setVisible(false);
			k_yes.setVisible(false);
			slider_effect.setVisible(false);
			slider_music.setVisible(false);
			k_reset.setVisible(false);
			music_k.setVisible(false);
			effect_k.setVisible(false);
			kpk_on = false;
			press_time = TimeUtils.millis();
			if (the_first_start) {
				game_forScreen.curent_time = TimeUtils.millis();
				the_first_start = false;
				if (game_forScreen.curentLvl==10 || game_forScreen.curentLvl==28
						|| game_forScreen.curentLvl==35 || game_forScreen.curentLvl==44)trevoga.play(game_forScreen.effectVol);
			}
		}

		if (on_menu) {
			if (k_yes.isPressed() && TimeUtils.timeSinceMillis(press_time) > 300) {
				btn_b.play(game_forScreen.effectVol);
				bg_music.stop();
				end_game=true;
				for_end_game=1;
				press_time = (TimeUtils.millis()+10000);
			}
			else if(k_reset.isPressed() && TimeUtils.timeSinceMillis(press_time) > 300){
				btn_b.play(game_forScreen.effectVol);
				bg_music.stop();
				game_forScreen.for_reset=true;
				end_game=true;
				for_end_game=2;
				press_time = (TimeUtils.millis()+10000);
			}
			else if(k_down.isPressed() && TimeUtils.timeSinceMillis(press_time) > 300){
				btn_b.play(game_forScreen.effectVol);
				press_time = TimeUtils.millis();
				k_yes.setVisible(false);
				k_msg.setVisible(false);
				k_reset.setVisible(false);
				k_down.setVisible(false);
				music_k.setVisible(true);
				effect_k.setVisible(true);
				slider_music.setVisible(true);
				slider_effect.setVisible(true);
				k_up.setVisible(true);
				slider_music.setValue(game_forScreen.musicVol);
				slider_effect.setValue(game_forScreen.effectVol);
			}
			else if(k_up.isPressed() && TimeUtils.timeSinceMillis(press_time) > 300){
				btn_b.play(game_forScreen.effectVol);
				press_time = TimeUtils.millis();
				k_yes.setVisible(true);
				k_msg.setVisible(true);
				k_reset.setVisible(true);
				k_down.setVisible(true);
				music_k.setVisible(false);
				effect_k.setVisible(false);
				slider_music.setVisible(false);
				slider_effect.setVisible(false);
				k_up.setVisible(false);
				game_forScreen.saveAll();
			}
			else if(slider_music.isDragging()){
				game_forScreen.musicVol=slider_music.getValue();
				bg_music.setVolume(game_forScreen.musicVol);
				if(game_forScreen.musicVol<=0f) bg_music.pause();
				else if (game_forScreen.musicVol>0f) bg_music.play();
			}
			else if (slider_effect.isDragging()){
				game_forScreen.effectVol=slider_effect.getValue();
				btn_b.play(game_forScreen.effectVol);
			}
			else if ((k_exit.isPressed() || k_menu.isPressed() || k_mssage.isPressed() || k_radar.isPressed()) && TimeUtils.timeSinceMillis(press_time) > 300){
				game_forScreen.saveAll();
			}
		} else if (on_mssage) {
			if (k_up.isPressed() && TimeUtils.timeSinceMillis(press_time) > 300) {
				btn_b.play(game_forScreen.effectVol);
				press_time = TimeUtils.millis();
				k_curent--;
				k_down.setVisible(true);
				if (k_curent == 0) {
					k_up.setVisible(false);
				}
				k_msg.setText(k_str[k_curent]);
				k_msg_chek();
			} else if (k_down.isPressed() && TimeUtils.timeSinceMillis(press_time) > 300) {
				btn_b.play(game_forScreen.effectVol);
				press_time = TimeUtils.millis();
				k_curent++;
				k_up.setVisible(true);
				if (k_curent == k_str.length - 1) {
					k_down.setVisible(false);
				}
				k_msg.setText(k_str[k_curent]);
				k_msg_chek();
			}
		} else if (on_radar) {
			for (x = 0; x < ww + ww + 1; x++)
				for (y = 0; y < hh + hh + 1; y++) {
					if (radar_lab[x][y] == 1)
						batch.draw(k_atl.findRegion("wall"), (x * gghh.zzz) + gghh.dx, (y * gghh.zzz) + gghh.dy,
								gghh.zzz, gghh.zzz);
					if (radar_lab[x][y] == 98)
						batch.draw(k_atl.findRegion("wall_s"), (x * gghh.zzz) + gghh.dx, (y * gghh.zzz) + gghh.dy,
								gghh.zzz, gghh.zzz);
					if (radar_lab[x][y] == 99 && for_onoff_sys)
						batch.draw(k_atl.findRegion("wall_f"), (x * gghh.zzz) + gghh.dx, (y * gghh.zzz) + gghh.dy,
								gghh.zzz, gghh.zzz);

					if (game_forScreen.curentLvl == 21 || game_forScreen.curentLvl == 31
							|| game_forScreen.curentLvl == 32 || game_forScreen.curentLvl == 33
							|| game_forScreen.curentLvl == 34 || clush_game){
						if(!for_onoff_sys) batch.draw(k_atl.findRegion("wall_f"), (sys_x * gghh.zzz) + gghh.dx, (sys_y * gghh.zzz) + gghh.dy,
								gghh.zzz, gghh.zzz);
					}

					//------------------------------------------------------------------
					if (radar_lab[x][y] == 2){
						if(pokazat_z)batch.draw(k_atl.findRegion("wall_z"), (x * gghh.zzz) + gghh.dx, (y * gghh.zzz) + gghh.dy,
								gghh.zzz, gghh.zzz);
						else batch.draw(k_atl.findRegion("wall"), (x * gghh.zzz) + gghh.dx, (y * gghh.zzz) + gghh.dy,
								gghh.zzz, gghh.zzz);
					}
					//------------------------------------------------------------------------
					if (game_forScreen.curentLvl == 10 && x == flx && y == fly)
						batch.draw(k_atl.findRegion("wall_f"), (x * gghh.zzz) + gghh.dx, (y * gghh.zzz) + gghh.dy,
								gghh.zzz, gghh.zzz);
				}
			batch.draw(k_atl.findRegion("wall_p"), (pl_x * gghh.zzz) + gghh.dx, (pl_y * gghh.zzz) + gghh.dy, gghh.zzz,
					gghh.zzz);
		}

		batch.end();

		k_bg.setVisible(false);
		k_stage.act(Gdx.graphics.getDeltaTime());
		k_stage.draw();

	}

	void k_msg_chek() {


	}

	void the_main_game(float d) {
		cam.update();
		batch.setProjectionMatrix(cam.combined);

		showGP();

		if (radarButton.isPressed() && !touchpad.isTouched() && touchpad.getKnobPercentX()==0 && touchpad.getKnobPercentY()==0 && !actionButton.isPressed() && !jumpButton.isPressed() && TimeUtils.timeSinceMillis(press_time) > 500) {
			kpk_on = true;
			on_radar = true;
			gghh.on_radar = on_radar;
			Gdx.input.setInputProcessor(multiplexer);
			press_time = TimeUtils.millis();
		}

		batch.begin();
		{
			ShowBackground();
			ShowMSG();
			if(clush_game) ShowCMS();
			if(!pokazat_z) showVzlz();
			if(!pokaz_o) showVzlo();
			WherePlayer();
			PlayerLogic();
			if(game_forScreen.effectVol>0f) player.TheSound(d, robot_speed, game_forScreen.effectVol, robot_jump, robot_jump_temp);
			ShowM();
			Radar();
			player.UpdateCord();
			if(game_forScreen.dif>=2) showLevel();
			if(!otkryt_radar && game_forScreen.dif==3) showFullRadar();


		}
		batch.end();

		camHUD.update();
		batch.setProjectionMatrix(camHUD.combined);

		informFPS.setText("FPS: " + Gdx.graphics.getFramesPerSecond() + "\n" + g_dif+"\n"+game_forScreen.curentLvl + game_sms_level);
		if(!end_game){
			long kkk = TimeUtils.timeSinceMillis(game_forScreen.curent_time) / 1000;
			if ((kkk / 60) < 10 && (kkk % 60) < 10)
				timer_info.setText("0" + (kkk / 60) + ":" + "0" + (kkk % 60));
			else if ((kkk % 60) > 10 && (kkk / 60) < 10)
				timer_info.setText("0" + (kkk / 60) + ":" + (kkk % 60));
			else if ((kkk % 60) < 10 && (kkk / 60) > 10)
				timer_info.setText((kkk / 60) + ":" + "0" + (kkk % 60));
			else if ((kkk % 60) > 10 && (kkk / 60) > 10)
				timer_info.setText((kkk / 60) + ":" + (kkk % 60));
		}
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	private void showFullRadar() {

		for(int ee=-1;ee<2;ee++){
			if(lab[pl_x+ee][pl_y]==0) radar_lab[pl_x+ee][pl_y]=0;
			if(lab[pl_x][pl_y+ee]==0) radar_lab[pl_x][pl_y+ee]=0;

		}
	}

	private void showLevel() {

		batch.draw(ui_atl.findRegion("level_bar"), cam.position.x - 630, cam.position.y + 310);
		batch.draw(ui_atl.findRegion("level"), cam.position.x - 575, cam.position.y + 315, player.p_gizni,50);

	}

	private void showVzlo() {
		for(int i=0;i<3;i++){
			batch.draw(vzlom_o, (vzlo_x[i]*CellSize)+50, (vzlo_y[i]*CellSize)+50);
			if(pl_x==vzlo_x[i] && pl_y==vzlo_y[i]) {
				pokaz_o=true;
				otkryt_radar=true;
				radar_lab=temp_radar_lab;
				for(int bk=0; bk<ww+ww+1;bk++){
					for(int bg=0;bg<hh+hh+1;bg++){
						radar_lab[bk][bg]=temp_radar_lab[bk][bg];
					}
				}
			}
		}
	}

	private void showVzlz() {
		for(int i=0;i<3;i++){
			batch.draw(vzlom_z, (vzlz_x[i]*CellSize)+50, (vzlz_y[i]*CellSize)+50);
			if(pl_x==vzlz_x[i] && pl_y==vzlz_y[i]) {
				pokazat_z=true;
				if(game_forScreen.dif==3){
					for(int bk=0; bk<ww+ww+1;bk++){
						for(int bg=0;bg<hh+hh+1;bg++){
							if(temp_radar_lab[bk][bg]==2)
								radar_lab[bk][bg]=2;
						}
					}
				}
			}
		}
	}

	private void ShowCMS() {

		if(!for_onoff_sys)batch.draw(system_btn_off, sys1x*CellSize, sys1y*CellSize);
		else if(for_onoff_sys)batch.draw(system_btn_on, sys1x*CellSize, sys1y*CellSize);

	}

	void ShowMSG() {


	}

	void the_quest(float delta) {
		if (temp_number_qest == 1) {
			camHUD.update();
			batch.setProjectionMatrix(camHUD.combined);
			qStage1();
			q_stage1.act(delta);
			q_stage1.draw();
		} else if (temp_number_qest == 2) {
			camHUD.update();
			batch.setProjectionMatrix(camHUD.combined);
			qStage2();
			q_stage2.act(delta);
			q_stage2.draw();
			if (q2_ok) {
				if (TimeUtils.timeSinceMillis(q_tim) > 2000) {
					q2_ok = false;
					q1_1 = 0;
					q_select_numb.setText(Integer.toString(q_chisla[q1_1]));
					q_znak.setText("");
					q_zag_numb.setText("?");
					Gdx.input.setInputProcessor(stage);
					theQuest = false;
				}
			}
		}
	}

	void qStage2() {
		if (!q2_ok) {
			if (q_up2.isPressed() && TimeUtils.timeSinceMillis(q_tim) > 100) {
				q_tim = TimeUtils.millis();
				q1_1++;
				if (q1_1 > q_chisla.length - 1)
					q1_1 = 0;
				q_select_numb.setText(Integer.toString(q_chisla[q1_1]));
			} else if (q_down2.isPressed() && TimeUtils.timeSinceMillis(q_tim) > 100) {
				q_tim = TimeUtils.millis();
				q1_1--;
				if (q1_1 < 0)
					q1_1 = q_chisla.length - 1;
				q_select_numb.setText(Integer.toString(q_chisla[q1_1]));
			} else if (q_yes2.isPressed() && TimeUtils.timeSinceMillis(q_tim) > 300) {
				q_tim = TimeUtils.millis();
				if (q_chisla[q1_1] == temp_q2.otv) {
					q_znak.setText("=");
					q_zag_numb.setText(Integer.toString(temp_q2.otv));

					q2_ok = true;
					if (lab[temp_q2.x][temp_q2.y] == 25) {
						if (lab[temp_q2.x][temp_q2.y + 1] == 62 && lab[temp_q2.x][temp_q2.y - 1] == 61) {
							lab[temp_q2.x][temp_q2.y + 1] = 51;
							lab[temp_q2.x][temp_q2.y - 1] = 51;
							lab[temp_q2.x][temp_q2.y] = 0;
						} else if (lab[temp_q2.x][temp_q2.y + 1] == 62 && lab[temp_q2.x][temp_q2.y - 1] == 33) {
							lab[temp_q2.x][temp_q2.y + 1] = 51;
							lab[temp_q2.x][temp_q2.y - 1] = 62;
							lab[temp_q2.x][temp_q2.y] = 0;
						} else if (lab[temp_q2.x][temp_q2.y + 1] == 33 && lab[temp_q2.x][temp_q2.y - 1] == 33) {
							lab[temp_q2.x][temp_q2.y + 1] = 61;
							lab[temp_q2.x][temp_q2.y - 1] = 62;
							lab[temp_q2.x][temp_q2.y] = 0;
						} else if (lab[temp_q2.x][temp_q2.y + 1] == 33 && lab[temp_q2.x][temp_q2.y - 1] == 61) {
							lab[temp_q2.x][temp_q2.y + 1] = 61;
							lab[temp_q2.x][temp_q2.y - 1] = 51;
							lab[temp_q2.x][temp_q2.y] = 0;
						}
					} else if (lab[temp_q2.x][temp_q2.y] == 26) {
						if (lab[temp_q2.x][temp_q2.y + 1] == 31 && lab[temp_q2.x][temp_q2.y - 1] == 64) {
							lab[temp_q2.x][temp_q2.y + 1] = 64;
							lab[temp_q2.x][temp_q2.y - 1] = 53;
							lab[temp_q2.x][temp_q2.y] = 0;
						} else if (lab[temp_q2.x][temp_q2.y + 1] == 63 && lab[temp_q2.x][temp_q2.y - 1] == 64) {
							lab[temp_q2.x][temp_q2.y + 1] = 53;
							lab[temp_q2.x][temp_q2.y - 1] = 53;
							lab[temp_q2.x][temp_q2.y] = 0;
						} else if (lab[temp_q2.x][temp_q2.y + 1] == 63 && lab[temp_q2.x][temp_q2.y - 1] == 31) {
							lab[temp_q2.x][temp_q2.y + 1] = 53;
							lab[temp_q2.x][temp_q2.y - 1] = 63;
							lab[temp_q2.x][temp_q2.y] = 0;
						} else if (lab[temp_q2.x][temp_q2.y + 1] == 31 && lab[temp_q2.x][temp_q2.y - 1] == 31) {
							lab[temp_q2.x][temp_q2.y + 1] = 64;
							lab[temp_q2.x][temp_q2.y - 1] = 63;
							lab[temp_q2.x][temp_q2.y] = 0;
						}
					} else if (lab[temp_q2.x][temp_q2.y] == 27) {
						if (lab[temp_q2.x][temp_q2.y + 1] == 34 && lab[temp_q2.x][temp_q2.y - 1] == 32) {
							lab[temp_q2.x][temp_q2.y + 1] = 21;
							lab[temp_q2.x][temp_q2.y - 1] = 21;
							lab[temp_q2.x][temp_q2.y] = 0;
						} else if (lab[temp_q2.x][temp_q2.y + 1] == 34 && lab[temp_q2.x][temp_q2.y - 1] == 4) {
							lab[temp_q2.x][temp_q2.y + 1] = 21;
							lab[temp_q2.x][temp_q2.y - 1] = 34;
							lab[temp_q2.x][temp_q2.y] = 0;
						} else if (lab[temp_q2.x][temp_q2.y + 1] == 4 && lab[temp_q2.x][temp_q2.y - 1] == 32) {
							lab[temp_q2.x][temp_q2.y + 1] = 32;
							lab[temp_q2.x][temp_q2.y - 1] = 21;
							lab[temp_q2.x][temp_q2.y] = 0;
						} else if (lab[temp_q2.x][temp_q2.y + 1] == 4 && lab[temp_q2.x][temp_q2.y - 1] == 4) {
							lab[temp_q2.x][temp_q2.y + 1] = 32;
							lab[temp_q2.x][temp_q2.y - 1] = 34;
							lab[temp_q2.x][temp_q2.y] = 0;
						} else if (lab[temp_q2.x][temp_q2.y + 1] == 4 && lab[temp_q2.x][temp_q2.y - 1] == 73) {
							lab[temp_q2.x][temp_q2.y + 1] = 32;
							lab[temp_q2.x][temp_q2.y - 1] = 16;
							lab[temp_q2.x][temp_q2.y] = 0;
						} else if (lab[temp_q2.x][temp_q2.y + 1] == 34 && lab[temp_q2.x][temp_q2.y - 1] == 73) {
							lab[temp_q2.x][temp_q2.y + 1] = 21;
							lab[temp_q2.x][temp_q2.y - 1] = 16;
							lab[temp_q2.x][temp_q2.y] = 0;
						}
					}
					radar_lab[temp_q2.x][temp_q2.y]=0;
				} else if (q_chisla[q1_1] != temp_q2.otv) {
					if (q_chisla[q1_1] > temp_q2.otv)
						q_znak.setText(">");
					else if (q_chisla[q1_1] < temp_q2.otv)
						q_znak.setText("<");
				}
			} else if (q_off2.isPressed() && TimeUtils.timeSinceMillis(q_tim) > 300) {
				q_tim = TimeUtils.millis();
				lab[temp_q2.x][temp_q2.y] = 22;
				radar_lab[temp_q2.x][temp_q2.y]=1;
				q1_1 = 0;
				Gdx.input.setInputProcessor(stage);
				theQuest = false;
			}
		}
	}

	void qStage1() {
		if (q_sel1.isPressed()) {
			q_sel1.setVisible(false);
			q_sel2.setVisible(true);
			q_sel3.setVisible(true);
			q_sel3.setPosition(q_sel1.getX(), q_sel1.getY());
			q_up1.setPosition(q_sel1.getX() + 3, camHUD.position.y + 80);
			q_down1.setPosition(q_sel1.getX() + 3, camHUD.position.y - 125);
		} else if (q_sel2.isPressed()) {
			q_sel1.setVisible(true);
			q_sel2.setVisible(false);
			q_sel3.setVisible(true);
			q_sel3.setPosition(q_sel2.getX(), q_sel2.getY());
			q_up1.setPosition(q_sel2.getX() + 3, camHUD.position.y + 80);
			q_down1.setPosition(q_sel2.getX() + 3, camHUD.position.y - 125);
		} else if (q_up1.isPressed() && TimeUtils.timeSinceMillis(q_tim) > 300) {
			q_tim = TimeUtils.millis();
			if (!q_sel1.isVisible()) {
				q1_1++;
				if (q1_1 > temp_q1.base.get(0).length - 1)
					q1_1 = 0;
				q_1.setText(Integer.toString(temp_q1.base.get(0)[q1_1]));
			} else if (!q_sel2.isVisible()) {
				q1_2++;
				if (q1_2 > temp_q1.base.get(1).length - 1)
					q1_2 = 0;
				q_3.setText(Integer.toString(temp_q1.base.get(1)[q1_2]));
			}
		} else if (q_down1.isPressed() && TimeUtils.timeSinceMillis(q_tim) > 300) {
			q_tim = TimeUtils.millis();
			if (!q_sel1.isVisible()) {
				q1_1--;
				if (q1_1 < 0)
					q1_1 = temp_q1.base.get(0).length - 1;
				q_1.setText(Integer.toString(temp_q1.base.get(0)[q1_1]));
			} else if (!q_sel2.isVisible()) {
				q1_2--;
				if (q1_2 < 0)
					q1_2 = temp_q1.base.get(1).length - 1;
				q_3.setText(Integer.toString(temp_q1.base.get(1)[q1_2]));
			}
		} else if (q_yes1.isPressed() && TimeUtils.timeSinceMillis(q_tim) > 1000) {
			if (temp_q1.base.get(0)[q1_1] + temp_q1.base.get(1)[q1_2] == temp_q1.otv) {
				if (lab[temp_q1.x][temp_q1.y] == 25) {
					if (lab[temp_q1.x][temp_q1.y + 1] == 62 && lab[temp_q1.x][temp_q1.y - 1] == 61) {
						lab[temp_q1.x][temp_q1.y + 1] = 51;
						lab[temp_q1.x][temp_q1.y - 1] = 51;
						lab[temp_q1.x][temp_q1.y] = 0;
					} else if (lab[temp_q1.x][temp_q1.y + 1] == 62 && lab[temp_q1.x][temp_q1.y - 1] == 33) {
						lab[temp_q1.x][temp_q1.y + 1] = 51;
						lab[temp_q1.x][temp_q1.y - 1] = 62;
						lab[temp_q1.x][temp_q1.y] = 0;
					} else if (lab[temp_q1.x][temp_q1.y + 1] == 33 && lab[temp_q1.x][temp_q1.y - 1] == 33) {
						lab[temp_q1.x][temp_q1.y + 1] = 61;
						lab[temp_q1.x][temp_q1.y - 1] = 62;
						lab[temp_q1.x][temp_q1.y] = 0;
					} else if (lab[temp_q1.x][temp_q1.y + 1] == 33 && lab[temp_q1.x][temp_q1.y - 1] == 61) {
						lab[temp_q1.x][temp_q1.y + 1] = 61;
						lab[temp_q1.x][temp_q1.y - 1] = 51;
						lab[temp_q1.x][temp_q1.y] = 0;
					}
				} else if (lab[temp_q1.x][temp_q1.y] == 26) {
					if (lab[temp_q1.x][temp_q1.y + 1] == 31 && lab[temp_q1.x][temp_q1.y - 1] == 64) {
						lab[temp_q1.x][temp_q1.y + 1] = 64;
						lab[temp_q1.x][temp_q1.y - 1] = 53;
						lab[temp_q1.x][temp_q1.y] = 0;
					} else if (lab[temp_q1.x][temp_q1.y + 1] == 63 && lab[temp_q1.x][temp_q1.y - 1] == 64) {
						lab[temp_q1.x][temp_q1.y + 1] = 53;
						lab[temp_q1.x][temp_q1.y - 1] = 53;
						lab[temp_q1.x][temp_q1.y] = 0;
					} else if (lab[temp_q1.x][temp_q1.y + 1] == 63 && lab[temp_q1.x][temp_q1.y - 1] == 31) {
						lab[temp_q1.x][temp_q1.y + 1] = 53;
						lab[temp_q1.x][temp_q1.y - 1] = 63;
						lab[temp_q1.x][temp_q1.y] = 0;
					} else if (lab[temp_q1.x][temp_q1.y + 1] == 31 && lab[temp_q1.x][temp_q1.y - 1] == 31) {
						lab[temp_q1.x][temp_q1.y + 1] = 64;
						lab[temp_q1.x][temp_q1.y - 1] = 63;
						lab[temp_q1.x][temp_q1.y] = 0;
					}
				} else if (lab[temp_q1.x][temp_q1.y] == 27) {
					if (lab[temp_q1.x][temp_q1.y + 1] == 34 && lab[temp_q1.x][temp_q1.y - 1] == 32) {
						lab[temp_q1.x][temp_q1.y + 1] = 21;
						lab[temp_q1.x][temp_q1.y - 1] = 21;
						lab[temp_q1.x][temp_q1.y] = 0;
					} else if (lab[temp_q1.x][temp_q1.y + 1] == 34 && lab[temp_q1.x][temp_q1.y - 1] == 4) {
						lab[temp_q1.x][temp_q1.y + 1] = 21;
						lab[temp_q1.x][temp_q1.y - 1] = 34;
						lab[temp_q1.x][temp_q1.y] = 0;
					} else if (lab[temp_q1.x][temp_q1.y + 1] == 4 && lab[temp_q1.x][temp_q1.y - 1] == 32) {
						lab[temp_q1.x][temp_q1.y + 1] = 32;
						lab[temp_q1.x][temp_q1.y - 1] = 21;
						lab[temp_q1.x][temp_q1.y] = 0;
					} else if (lab[temp_q1.x][temp_q1.y + 1] == 4 && lab[temp_q1.x][temp_q1.y - 1] == 4) {
						lab[temp_q1.x][temp_q1.y + 1] = 32;
						lab[temp_q1.x][temp_q1.y - 1] = 34;
						lab[temp_q1.x][temp_q1.y] = 0;
					} else if (lab[temp_q1.x][temp_q1.y + 1] == 4 && lab[temp_q1.x][temp_q1.y - 1] == 73) {
						lab[temp_q1.x][temp_q1.y + 1] = 32;
						lab[temp_q1.x][temp_q1.y - 1] = 16;
						lab[temp_q1.x][temp_q1.y] = 0;
					} else if (lab[temp_q1.x][temp_q1.y + 1] == 34 && lab[temp_q1.x][temp_q1.y - 1] == 73) {
						lab[temp_q1.x][temp_q1.y + 1] = 21;
						lab[temp_q1.x][temp_q1.y - 1] = 16;
						lab[temp_q1.x][temp_q1.y] = 0;
					}
				}
				radar_lab[temp_q1.x][temp_q1.y]=0;
			} else if (temp_q1.base.get(0)[q1_1] + temp_q1.base.get(1)[q1_2] != temp_q1.otv) {
				lab[temp_q1.x][temp_q1.y] = 22;
				radar_lab[temp_q1.x][temp_q1.y]=1;
			}
			q1_1 = 0;
			q1_2 = 0;
			Gdx.input.setInputProcessor(stage);
			theQuest = false;
		}

	}

	void Radar() {
		batch.draw(ui_atl.findRegion("r_ram"), cam.position.x + 340, cam.position.y + 184);
		int frx, fry;
		if (ww <= 13 && hh <= 8) {

			frx = 490 - (((ww + ww + 1) * 10) / 2);
			fry = 284 - (((hh + hh + 1) * 10) / 2);
			for (x = 0; x < ww + ww + 1; x++)
				for (y = 0; y < hh + hh + 1; y++) {
					if (radar_lab[x][y] == 1)
						batch.draw(ui_atl.findRegion("wall"), (x * 10) + cam.position.x + frx,
								(y * 10) + cam.position.y + fry);
					if (radar_lab[x][y] == 98)
						batch.draw(ui_atl.findRegion("wall_s"), (x * 10) + cam.position.x + frx,
								(y * 10) + cam.position.y + fry);
					if (radar_lab[x][y] == 99)
						batch.draw(ui_atl.findRegion("wall_f"), (x * 10) + cam.position.x + frx,
								(y * 10) + cam.position.y + fry);
				}
			if (game_forScreen.curentLvl == 10)
				batch.draw(ui_atl.findRegion("wall_f"), (flx * 10) + cam.position.x + frx,
						(fly * 10) + cam.position.y + fry);
			batch.draw(ui_atl.findRegion("where"), (pl_x * 10) + cam.position.x + frx,
					(pl_y * 10) + cam.position.y + fry);
		}
		if (ww >= 14 && hh >= 9) {
			frx = 355;
			fry = 199;
			int nx = 0, ny = 0, ex = 27, ey = 17, dx = 0, dy = 0;
			if (pl_x > 13) {
				nx = pl_x - 14;
				ex = pl_x + 13;
			}
			if (pl_y > 8) {
				ny = pl_y - 9;
				ey = pl_y + 8;
			}
			if (pl_x > ww + ww - 13) {
				nx = ww + ww - 26;
				ex = ww + ww + 1;
			}
			if (pl_y > hh + hh - 8) {
				ny = hh + hh - 16;
				ey = hh + hh + 1;
			}

			for (dx = 0; nx + dx < ex; dx++)
				for (dy = 0; ny + dy < ey; dy++) {
					if (radar_lab[nx + dx][ny + dy] == 1)
						batch.draw(ui_atl.findRegion("wall"), (dx * 10) + cam.position.x + frx,
								(dy * 10) + cam.position.y + fry);
					if (radar_lab[nx + dx][ny + dy] == 2){
						if(pokazat_z)batch.draw(ui_atl.findRegion("wall_z"), (dx * 10) + cam.position.x + frx,
								(dy * 10) + cam.position.y + fry);
						else batch.draw(ui_atl.findRegion("wall"), (dx * 10) + cam.position.x + frx,
								(dy * 10) + cam.position.y + fry);
					}
					if (radar_lab[nx + dx][ny + dy] == 98)
						batch.draw(ui_atl.findRegion("wall_s"), (dx * 10) + cam.position.x + frx,
								(dy * 10) + cam.position.y + fry);
					if (radar_lab[nx + dx][ny + dy] == 99 && for_onoff_sys)
						batch.draw(ui_atl.findRegion("wall_f"), (dx * 10) + cam.position.x + frx,
								(dy * 10) + cam.position.y + fry);

					if (game_forScreen.curentLvl == 21 || game_forScreen.curentLvl == 31
							|| game_forScreen.curentLvl == 32 || game_forScreen.curentLvl == 33
							|| game_forScreen.curentLvl == 34 || clush_game){
						if(!for_onoff_sys && radar_lab[nx + dx][ny + dy] == 5)
							batch.draw(ui_atl.findRegion("wall_f"), (dx * 10) + cam.position.x + frx,
									(dy * 10) + cam.position.y + fry);
					}

					if (game_forScreen.curentLvl == 10 && nx + dx == flx && ny + dy == fly)
						batch.draw(ui_atl.findRegion("wall_f"), (dx * 10) + cam.position.x + frx,
								(dy * 10) + cam.position.y + fry);
				}
			batch.draw(ui_atl.findRegion("where"), ((pl_x - nx) * 10) + cam.position.x + frx,
					((pl_y - ny) * 10) + cam.position.y + fry);
		}
	}

	void WherePlayer() {
		if (player.robotState == RobotState.UpLeftWall || player.robotState == RobotState.UpRightWall
				|| player.robotState == RobotState.DownLeftWall || player.robotState == RobotState.DownRightWall)
			;
		else
			pl_x = (int) player.pl_board[4].x / CellSize;
		pl_y = (int) player.pl_board[4].y / CellSize;

		if ((pl_y == hh + hh - 1 && pl_x == finish_lok) || (pl_x == flx && pl_y == fly))
			actionButton.setVisible(true);
		else if (pl_y != hh + hh - 1 && pl_x != finish_lok)
			actionButton.setVisible(false);
		if (pl_x == flx && pl_y == fly && actionButton.isPressed() && game_forScreen.curentLvl == 10 && TimeUtils.timeSinceMillis(press_time) > 3000) {
			game_forScreen.curent_time = TimeUtils.timeSinceMillis(game_forScreen.curent_time);
			game_forScreen.trueLvl = true;
			bg_music.stop();
			end_game=true;
			for_end_game=2;
			press_time = TimeUtils.millis();
		}
		if (pl_y == hh + hh - 1 && pl_x == finish_lok && actionButton.isPressed() && TimeUtils.timeSinceMillis(press_time) > 3000) {
			game_forScreen.curent_time = TimeUtils.timeSinceMillis(game_forScreen.curent_time);
			if (game_forScreen.curentLvl == 10) game_forScreen.trueLvl = false;
			bg_music.stop();
			end_game=true;
			for_end_game=2;
			press_time = TimeUtils.millis();
		}
		if (player.robotState == RobotState.StandRight || player.robotState == RobotState.StandLeft) {
			if (lab[pl_x + 1][pl_y] == 27 || lab[pl_x - 1][pl_y] == 27 || lab[pl_x + 1][pl_y] == 25
					|| lab[pl_x - 1][pl_y] == 26) {
				actionButton.setVisible(true);
				if (lab[pl_x + 1][pl_y] == 27)
					temp_number_qest = 1;
				else if (lab[pl_x - 1][pl_y] == 27)
					temp_number_qest = 2;
				else if (lab[pl_x + 1][pl_y] == 25)
					temp_number_qest = 1;
				else if (lab[pl_x - 1][pl_y] == 26)
					temp_number_qest = 2;
			}
			if (actionButton.isVisible() && actionButton.isPressed() && !touchpad.isTouched() && !jumpButton.isPressed() && !radarButton.isPressed()) {

				int ttx = 0;
				if (temp_number_qest == 1) {
					ttx = pl_x + 1;
					player.may_go_right = true;
				} else if (temp_number_qest == 2) {
					ttx = pl_x - 1;
					player.may_go_left = true;
				}
				q_tim = TimeUtils.millis();
				for (int i = 0; i < tq1.size; i++) {
					temp_q1 = tq1.get(i);
					if (temp_q1.x == ttx && temp_q1.y == pl_y) {
						temp_number_qest = 1;
						Gdx.input.setInputProcessor(q_stage1);
						q_otv.setText("=" + temp_q1.otv);
						q_1.setText(Integer.toString(temp_q1.base.get(0)[0]));
						q_3.setText(Integer.toString(temp_q1.base.get(1)[0]));
						q_sel1.setVisible(false);
						q_sel2.setVisible(true);
						q_sel3.setPosition(q_sel1.getX(), q_sel1.getY());
						q_sel3.setVisible(true);
						q_up1.setPosition(q_sel1.getX() + 3, camHUD.position.y + 80);
						q_down1.setPosition(q_sel1.getX() + 3, camHUD.position.y - 125);
						theQuest = true;
						break;
					}
				}
				if (!theQuest) {
					for (int i = 0; i < tq2.size; i++) {
						temp_q2 = tq2.get(i);
						if (temp_q2.x == ttx && temp_q2.y == pl_y) {
							temp_number_qest = 2;
							Gdx.input.setInputProcessor(q_stage2);
							q_sel4.setVisible(true);
							q_sel4.setPosition(camHUD.position.x - 125, camHUD.position.y - 62);
							q_up2.setPosition(q_sel4.getX() + 5, camHUD.position.y + 80);
							q_down2.setPosition(q_sel4.getX() + 5, camHUD.position.y - 125);
							q_select_numb.setPosition(camHUD.position.x - 115, camHUD.position.y - 60);
							q_znak.setPosition(camHUD.position.x + 5, camHUD.position.y + 20);
							q_zag_numb.setPosition(camHUD.position.x + 115, camHUD.position.y - 60);
							theQuest = true;
							break;
						}
					}
				}
			}
		}
	}

	void PlayerLogic() {

		if (player.robotState == RobotState.StandRight) {
			ForStand();
			batch.draw(player.stand_r, player.p.x, player.p.y);

		}

		else if (player.robotState == RobotState.StandLeft) {
			ForStand();
			batch.draw(player.stand_l, player.p.x, player.p.y);

		}

		else if (player.robotState == RobotState.GoRight) {
			ForGo();
			if (touchpad.getKnobPercentX() > 0.9f)
				batch.draw(player.run.getKeyFrame(player.anim_time), player.p.x, player.p.y);
			else
				batch.draw(player.go.getKeyFrame(player.anim_time), player.p.x, player.p.y);

		}

		else if (player.robotState == RobotState.GoLeft) {
			ForGo();
			if (touchpad.getKnobPercentX() < -0.9f)
				batch.draw(player.run_back.getKeyFrame(player.anim_time), player.p.x, player.p.y);
			else
				batch.draw(player.go_back.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		}

		else if (player.robotState == RobotState.JumpStandRight) {
			ForJump();
			batch.draw(player.jump_stand_r.getKeyFrame(player.anim_time), player.p.x, player.p.y);

		}

		else if (player.robotState == RobotState.JumpStandLeft) {
			ForJump();
			batch.draw(player.jump_stand_l.getKeyFrame(player.anim_time), player.p.x, player.p.y);

		}

		else if (player.robotState == RobotState.JumpRight) {
			ForJump();
			if (robot_speed == 100)
				batch.draw(player.jump_go_r.getKeyFrame(player.anim_time), player.p.x, player.p.y);
			else if (robot_speed == 260)
				batch.draw(player.jump_go_r.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		}

		else if (player.robotState == RobotState.JumpLeft) {
			ForJump();
			if (robot_speed == 100)
				batch.draw(player.jump_go_l.getKeyFrame(player.anim_time), player.p.x, player.p.y);
			else if (robot_speed == 260)
				batch.draw(player.jump_go_l.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		}

		else if (player.robotState == RobotState.JumpLeftWallOnRight) {
			ForJump();
			batch.draw(player.jump_l_on_r.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		}

		else if (player.robotState == RobotState.JumpRightWallOnLeft) {
			ForJump();
			batch.draw(player.jump_r_on_l.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		}

		else if (player.robotState == RobotState.FallLeft) {
			player.anim_time+=Gdx.graphics.getDeltaTime();
			if(player.anim_time>=0.8f){
				player.anim_time=0.8f;
				player.robotState = RobotState.StandLeft;
			}
			batch.draw(player.fall_l.getKeyFrame(player.anim_time), player.p.x, player.p.y);
			if(game_forScreen.dif>=2 && player.p_gizni==0f && player.anim_time>=0.2f) {
				player.robotState = RobotState.DeadLeft;
				player.anim_time=0;
			}
			if(player.anim_time>=0.8f) player.anim_time=0;
		}

		else if (player.robotState == RobotState.FallRight) {
			player.anim_time+=Gdx.graphics.getDeltaTime();
			if(player.anim_time>=0.8f){
				player.anim_time=0.8f;
				player.robotState = RobotState.StandRight;
			}
			batch.draw(player.fall_r.getKeyFrame(player.anim_time), player.p.x, player.p.y);
			if(game_forScreen.dif>=2 && player.p_gizni==0f && player.anim_time>=0.2f) {
				player.robotState = RobotState.DeadRight;
				player.anim_time=0;
			}
			if(player.anim_time>=0.8f) player.anim_time=0;
		}

		else if (player.robotState == RobotState.StandLeftWall) {
			ForWall();
			batch.draw(player.stand_l_wall, player.p.x, player.p.y);
		}

		else if (player.robotState == RobotState.StandRightWall) {
			ForWall();
			batch.draw(player.stand_r_wall, player.p.x, player.p.y);
		}

		else if (player.robotState == RobotState.GoLeftWallUp) {
			ForWall();
			if (player.go_l_wall.getPlayMode() == PlayMode.LOOP_REVERSED)
				player.go_l_wall.setPlayMode(PlayMode.LOOP);
			batch.draw(player.go_l_wall.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		}

		else if (player.robotState == RobotState.GoLeftWallDown) {
			ForWall();
			if (player.go_l_wall.getPlayMode() == PlayMode.LOOP)
				player.go_l_wall.setPlayMode(PlayMode.LOOP_REVERSED);
			batch.draw(player.go_l_wall.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		}

		else if (player.robotState == RobotState.GoRightWallUp) {
			ForWall();
			if (player.go_r_wall.getPlayMode() == PlayMode.LOOP_REVERSED)
				player.go_r_wall.setPlayMode(PlayMode.LOOP);
			batch.draw(player.go_r_wall.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		}

		else if (player.robotState == RobotState.GoRightWallDown) {
			ForWall();
			if (player.go_r_wall.getPlayMode() == PlayMode.LOOP)
				player.go_r_wall.setPlayMode(PlayMode.LOOP_REVERSED);
			batch.draw(player.go_r_wall.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		}

		// -----------------------ВИСИТ НА КРАЮ СТЕНЫ ЗАПРЫГИВАЕТ НА СТЕНУ И
		// СПРЫГИВАЕТ С НЕЕ-----------------------

		else if (player.robotState == RobotState.OnLeftWall) {
			ForWall();
			batch.draw(player.on_l_wall.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		} else if (player.robotState == RobotState.OnRightWall) {
			ForWall();
			batch.draw(player.on_r_wall.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		} else if (player.robotState == RobotState.OffLeftWall) {
			ForJump();
			batch.draw(player.off_l_wall.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		} else if (player.robotState == RobotState.OffRightWall) {
			ForJump();
			batch.draw(player.off_r_wall.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		} else if (player.robotState == RobotState.StandOnLeftWall) {
			ForWall();
			batch.draw(player.off_l_wall.getKeyFrame(0.2f), player.p.x, player.p.y);
		} else if (player.robotState == RobotState.StandOnRightWall) {
			ForWall();
			batch.draw(player.off_r_wall.getKeyFrame(0.2f), player.p.x, player.p.y);
		}

		// -----------------ЗАЛЕЗАЕТ И ВЫЛЕЗАЕТ СО
		// СТЕНЫ----------------------------

		else if (player.robotState == RobotState.UpLeftWall) {
			if (player.up_l_wall.getPlayMode() == PlayMode.REVERSED)
				player.up_l_wall.setPlayMode(PlayMode.NORMAL);
			ForWall();
			batch.draw(player.up_l_wall.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		} else if (player.robotState == RobotState.UpRightWall) {
			if (player.up_r_wall.getPlayMode() == PlayMode.REVERSED)
				player.up_r_wall.setPlayMode(PlayMode.NORMAL);
			ForWall();
			batch.draw(player.up_r_wall.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		} else if (player.robotState == RobotState.DownLeftWall) {
			if (player.up_l_wall.getPlayMode() == PlayMode.NORMAL)
				player.up_l_wall.setPlayMode(PlayMode.REVERSED);
			ForWall();
			batch.draw(player.up_l_wall.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		} else if (player.robotState == RobotState.DownRightWall) {
			if (player.up_r_wall.getPlayMode() == PlayMode.NORMAL)
				player.up_r_wall.setPlayMode(PlayMode.REVERSED);
			ForWall();
			batch.draw(player.up_r_wall.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		}

		else if(player.robotState == RobotState.DeadRight){
			if(!end_game)player.anim_time+=Gdx.graphics.getDeltaTime();
			if(player.anim_time>=0.8f && !end_game){
				bg_music.stop();
				game_forScreen.for_reset=true;
				end_game=true;
				for_end_game=2;
				press_time = (TimeUtils.millis()+10000);
				player.anim_time=0.8f;
			}
			batch.draw(player.dead_r.getKeyFrame(player.anim_time), player.p.x, player.p.y);

		}else if(player.robotState == RobotState.DeadLeft){
			if(!end_game)player.anim_time+=Gdx.graphics.getDeltaTime();
			if(player.anim_time>=0.8f && !end_game){
				bg_music.stop();
				game_forScreen.for_reset=true;
				end_game=true;
				for_end_game=2;
				press_time = (TimeUtils.millis()+10000);
				player.anim_time=0.8f;
			}
			batch.draw(player.dead_l.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		}
	}

	void ForStand() {
		player.anim_time = 0;
		if (sel.isChecked()) {

			if (jumpButton.isPressed()) {
				if (!player.in_air && player.robotState == RobotState.StandRight)
					player.robotState = RobotState.JumpStandRight;
				else if (!player.in_air && player.robotState == RobotState.StandLeft)
					player.robotState = RobotState.JumpStandLeft;
			}

			else if (touchpad.getKnobPercentX() > 0.1f && player.may_go_right && !player.in_air)
				player.robotState = RobotState.GoRight;
			else if (touchpad.getKnobPercentX() < -0.1f && player.may_go_left && !player.in_air)
				player.robotState = RobotState.GoLeft;
		}
	}

	void ForGo() {
		player.anim_time = player.anim_time + Gdx.graphics.getDeltaTime();
		if (sel.isChecked()) {

			if (touchpad.getKnobPercentX() < -0.9f || touchpad.getKnobPercentX() > 0.9f)
				robot_speed = 260;
			else
				robot_speed = 100;

			if (touchpad.getKnobPercentX() > 0.1f) {
				if ((lab[pl_x + 1][pl_y] == 22 || lab[pl_x + 1][pl_y] == 25 || lab[pl_x + 1][pl_y] == 26
						|| lab[pl_x + 1][pl_y] == 27 || lab[pl_x + 1][pl_y] == 17)
						&& (player.pl_board[2].x > ((pl_x + 1) * CellSize))) {
					player.may_go_left = true;
					player.may_go_right = false;
					if ((lab[pl_x + 1][pl_y + 1] == 22 || lab[pl_x + 1][pl_y + 1] == 52 || lab[pl_x + 1][pl_y + 1] == 17
							|| lab[pl_x + 1][pl_y + 1] == 63 || lab[pl_x + 1][pl_y + 1] == 31)
							&& (lab[pl_x][pl_y - 1] != 0)) {
						player.robotState = RobotState.StandRightWall;
						player.may_go_left = true;
						player.may_go_right = false;
						robot_speed = 150;
						player.p.y = player.p.y + 10;
						player.p.x = ((pl_x + 1) * CellSize) - 80;
						player.anim_time = 0;
					}
				} else if (lab[pl_x][pl_y - 1] == 0 && lab[pl_x][pl_y] == 0) {
					if (player.pl_board[0].x > (pl_x) * CellSize && player.pl_board[2].x < (pl_x + 1) * CellSize) {
						player.robotState = RobotState.JumpStandRight;
						robot_jump_temp = -1;
						player.anim_time = 1.30f;
						player.touch_up_wall = true;
						player.in_air = true;
					} else if (player.pl_board[4].x > (pl_x) * CellSize && touchpad.getKnobPercentX() > -0.1f
							&& touchpad.getKnobPercentY() < -0.5f) {
						player.anim_time = 0;
						player.in_air = true;
						player.p.x = ((pl_x) * CellSize) - 64;
						player.robotState = RobotState.DownLeftWall;
					}
				}
			} else if (touchpad.getKnobPercentX() < -0.1f) {
				if ((lab[pl_x - 1][pl_y] == 22 || lab[pl_x - 1][pl_y] == 25 || lab[pl_x - 1][pl_y] == 26
						|| lab[pl_x - 1][pl_y] == 27 || lab[pl_x - 1][pl_y] == 15)
						&& (player.pl_board[0].x < ((pl_x) * CellSize))) {
					player.may_go_left = false;
					player.may_go_right = true;
					if ((lab[pl_x - 1][pl_y + 1] == 22 || lab[pl_x - 1][pl_y + 1] == 52 || lab[pl_x - 1][pl_y + 1] == 33
							|| lab[pl_x - 1][pl_y + 1] == 62 || lab[pl_x - 1][pl_y + 1] == 15)
							&& (lab[pl_x][pl_y - 1] != 0)) {
						player.robotState = RobotState.StandLeftWall;
						player.may_go_left = false;
						player.may_go_right = true;
						robot_speed = 150;
						player.p.y = player.p.y + 10;
						player.p.x = (pl_x * CellSize) - 20;
						player.anim_time = 0;
					}
				} else if (lab[pl_x][pl_y - 1] == 0 && lab[pl_x][pl_y] == 0) {
					if (player.pl_board[2].x < (pl_x + 1) * CellSize && player.pl_board[0].x > (pl_x) * CellSize) {
						player.robotState = RobotState.JumpStandLeft;
						robot_jump_temp = -1;
						player.anim_time = 1.30f;
						player.touch_up_wall = true;
						player.in_air = true;
					} else if (player.pl_board[4].x < (pl_x + 1) * CellSize && touchpad.getKnobPercentX() < -0.1f
							&& touchpad.getKnobPercentY() < -0.5f) {
						player.anim_time = 0;
						player.in_air = true;
						player.p.x = ((pl_x + 1) * CellSize) - 35;
						player.robotState = RobotState.DownRightWall;
					}
				}
			}

			if ((touchpad.getKnobPercentX() > 0.1f || touchpad.getKnobPercentX() < -0.1f) && (lab[pl_x][pl_y] == 0
					&& player.pl_board[2].x < ((pl_x + 1) * CellSize) && player.pl_board[0].x > ((pl_x) * CellSize))) {
				player.may_go_left = true;
				player.may_go_right = true;
			}

			if (jumpButton.isPressed()) {
				if (touchpad.getKnobPercentX() > 0.1f && !player.in_air) {
					player.anim_time = 0;
					player.robotState = RobotState.JumpRight;
				} else if (touchpad.getKnobPercentX() < -0.1f && !player.in_air) {
					player.anim_time = 0;
					player.robotState = RobotState.JumpLeft;
				}
			} else if (touchpad.getKnobPercentX() > 0.1f && player.may_go_right && !player.in_air) {
				player.robotState = RobotState.GoRight;
				player.p.x = player.p.x + (robot_speed * Gdx.graphics.getDeltaTime());
			} else if (touchpad.getKnobPercentX() < -0.1f && player.may_go_left && !player.in_air) {
				player.robotState = RobotState.GoLeft;
				player.p.x = player.p.x - (robot_speed * Gdx.graphics.getDeltaTime());
			} else if (touchpad.getKnobPercentX() > -0.1f && touchpad.getKnobPercentX() < 0.1f) {
				if (player.robotState == RobotState.GoRight)
					player.robotState = RobotState.StandRight;
				else if (player.robotState == RobotState.GoLeft)
					player.robotState = RobotState.StandLeft;
			}
			if (player.in_air)
				player.in_air = false;
			if (player.robotState == RobotState.StandLeftWall || player.robotState == RobotState.StandRightWall) {
				player.may_go_left = true;
				player.may_go_right = true;
			}
		}
	}

	void ForJump() {
		if (sel.isChecked()) {

			player.anim_time = player.anim_time + Gdx.graphics.getDeltaTime();

			if (player.robotState == RobotState.JumpStandRight || player.robotState == RobotState.JumpStandLeft
					|| player.robotState == RobotState.OffLeftWall || player.robotState == RobotState.OffRightWall) {
				if (player.anim_time >= 0.85f || (player.robotState == RobotState.OffLeftWall
						|| player.robotState == RobotState.OffRightWall)) {
					robot_jump_temp = robot_jump_temp - (9 * Gdx.graphics.getDeltaTime());
					player.p.y = player.p.y + robot_jump_temp;
				}
			}

			if (player.robotState == RobotState.JumpRight || player.robotState == RobotState.JumpLeft) {
				if (robot_speed == 100 && player.anim_time >= 0.5f) {
					robot_jump_temp = robot_jump_temp - (9 * Gdx.graphics.getDeltaTime());
					player.p.y = player.p.y + robot_jump_temp;
				} else if (robot_speed == 260 && player.anim_time >= 0.15f) {
					robot_jump_temp = robot_jump_temp - (9 * Gdx.graphics.getDeltaTime());
					player.p.y = player.p.y + robot_jump_temp;
				}

			}

			if (player.robotState == RobotState.JumpLeftWallOnRight
					|| player.robotState == RobotState.JumpRightWallOnLeft) {
				if (player.anim_time >= 0.55f) {
					robot_jump_temp = robot_jump_temp - (9 * Gdx.graphics.getDeltaTime());
					player.p.y = player.p.y + robot_jump_temp;
					if (player.robotState == RobotState.JumpLeftWallOnRight && player.may_go_right)
						player.p.x = player.p.x + (robot_speed * Gdx.graphics.getDeltaTime());
					else if (player.robotState == RobotState.JumpRightWallOnLeft && player.may_go_left)
						player.p.x = player.p.x - (robot_speed * Gdx.graphics.getDeltaTime());
				}
			}

			if (!player.touch_up_wall
					&& (lab[pl_x][pl_y + 1] == 53 || lab[pl_x][pl_y + 1] == 21 || lab[pl_x][pl_y + 1] == 64
					|| lab[pl_x][pl_y + 1] == 61 || lab[pl_x][pl_y + 1] == 51 || lab[pl_x][pl_y + 1] == 32
					|| lab[pl_x][pl_y + 1] == 54 || lab[pl_x][pl_y + 1] == 10)
					&& (player.pl_board[1].y > (pl_y + 1) * CellSize || player.pl_board[3].y > (pl_y + 1) * CellSize)) {
				robot_jump_temp = -1;
				player.touch_up_wall = true;
			} else if (!player.touch_up_wall
					&& ((lab[pl_x - 1][pl_y + 1] == 53 || lab[pl_x - 1][pl_y + 1] == 64 || lab[pl_x - 1][pl_y + 1] == 61
					|| lab[pl_x - 1][pl_y + 1] == 51 || lab[pl_x - 1][pl_y + 1] == 54
					|| lab[pl_x - 1][pl_y + 1] == 10)
					|| (lab[pl_x + 1][pl_y + 1] == 53 || lab[pl_x + 1][pl_y + 1] == 64
					|| lab[pl_x + 1][pl_y + 1] == 61 || lab[pl_x + 1][pl_y + 1] == 51
					|| lab[pl_x + 1][pl_y + 1] == 54 || lab[pl_x + 1][pl_y + 1] == 10))
					&& (player.pl_board[1].x < ((pl_x) * CellSize) || player.pl_board[3].x > ((pl_x + 1) * CellSize))
					&& (player.pl_board[1].y > (pl_y + 1) * CellSize || player.pl_board[3].y > (pl_y + 1) * CellSize)) {
				robot_jump_temp = -1;
				player.touch_up_wall = true;
			}
			if (!player.touch_ground
					&& ((lab[pl_x][pl_y - 1] == 53 || lab[pl_x][pl_y - 1] == 21 || lab[pl_x][pl_y - 1] == 52
					|| lab[pl_x][pl_y - 1] == 62 || lab[pl_x][pl_y - 1] == 63 || lab[pl_x][pl_y - 1] == 34
					|| lab[pl_x][pl_y - 1] == 16 || lab[pl_x][pl_y - 1] == 51)
					&& (player.pl_board[0].y < (pl_y) * CellSize || player.pl_board[2].y < (pl_y) * CellSize))
					|| (((lab[pl_x - 1][pl_y - 1] == 51 || lab[pl_x - 1][pl_y - 1] == 52
					|| lab[pl_x - 1][pl_y - 1] == 62) && player.pl_board[0].y < (pl_y) * CellSize
					&& player.pl_board[0].x < (pl_x * CellSize) - 10)
					|| ((lab[pl_x + 1][pl_y - 1] == 53 || lab[pl_x + 1][pl_y - 1] == 52
					|| lab[pl_x + 1][pl_y - 1] == 63) && player.pl_board[2].y < (pl_y) * CellSize
					&& player.pl_board[2].x > ((pl_x + 1) * CellSize) + 10))) {

				player.p.y = (pl_y) * CellSize;
				player.touch_ground = false;
				player.touch_up_wall = false;
				if (player.robotState == RobotState.JumpRight || player.robotState == RobotState.JumpStandRight
						|| player.robotState == RobotState.JumpLeftWallOnRight) {
					player.robotState = RobotState.StandRight;
					player.anim_time = 0;
					if(robot_jump_temp<=-6f){
						player.robotState = RobotState.FallRight;
						player.anim_time = 0.5f;
					}
					if(robot_jump_temp<=-12f) {
						player.anim_time = 0.3f;
						if(game_forScreen.dif>=2)player.forLevel(robot_jump_temp+12f,game_forScreen.dif);
					}
					if(robot_jump_temp<=-24f) {
						player.anim_time = 0;
						if(game_forScreen.dif>=2)player.forLevel(robot_jump_temp,game_forScreen.dif);
					}

				} else if (player.robotState == RobotState.JumpLeft || player.robotState == RobotState.JumpStandLeft
						|| player.robotState == RobotState.JumpRightWallOnLeft) {
					player.robotState = RobotState.StandLeft;
					player.anim_time = 0;
					if(robot_jump_temp<=-6f){
						player.robotState = RobotState.FallLeft;
						player.anim_time = 0.5f;
					}
					if(robot_jump_temp<=-12f) {
						player.anim_time = 0.3f;
						if(game_forScreen.dif>=2)player.forLevel(robot_jump_temp+12f,game_forScreen.dif);
					}
					if(robot_jump_temp<=-24f) {
						player.anim_time = 0;
						if(game_forScreen.dif>=2)player.forLevel(robot_jump_temp,game_forScreen.dif);
					}
				}
				robot_jump_temp = robot_jump;
			}
			if (player.robotState == RobotState.OffLeftWall || player.robotState == RobotState.OffRightWall) {
				player.anim_time = player.anim_time + Gdx.graphics.getDeltaTime();
				if (player.anim_time >= 0.4f) {
					if (player.robotState == RobotState.OffLeftWall)
						player.p.x = player.p.x - (150 * Gdx.graphics.getDeltaTime());
					else if (player.robotState == RobotState.OffRightWall)
						player.p.x = player.p.x + (150 * Gdx.graphics.getDeltaTime());
				}
				if (player.pl_board[0].y <= pl_y * CellSize) {
					if (player.robotState == RobotState.OffLeftWall) {
						player.p.y = pl_y * CellSize;
						player.robotState = RobotState.StandLeft;
					} else if (player.robotState == RobotState.OffRightWall) {
						player.p.y = pl_y * CellSize;
						player.robotState = RobotState.StandRight;
					}
				}
			}
			//
			// ПРИЖКИ В Л Е В О
			//
			if (player.robotState == RobotState.JumpRightWallOnLeft || player.robotState == RobotState.JumpLeft
					|| player.robotState == RobotState.JumpStandLeft) {
				if ((player.robotState == RobotState.JumpLeft || player.robotState == RobotState.JumpStandLeft)
						&& (lab[pl_x - 1][pl_y] == 22 || lab[pl_x - 1][pl_y] == 25 || lab[pl_x - 1][pl_y] == 26
						|| lab[pl_x - 1][pl_y] == 27 || lab[pl_x - 1][pl_y] == 15)
						&& ((player.pl_board[0].x < ((pl_x) * CellSize))
						|| (player.pl_board[1].x < ((pl_x) * CellSize)))) {
					player.may_go_left = false;
					player.may_go_right = true;
				}
				if ((lab[pl_x - 1][pl_y] == 0 || lab[pl_x - 1][pl_y] == 99)
						&& (lab[pl_x - 1][pl_y - 1] == 51 || lab[pl_x - 1][pl_y - 1] == 52
						|| lab[pl_x - 1][pl_y - 1] == 62)
						&& (player.pl_board[4].y > (pl_y) * CellSize) && (player.pl_board[0].x < (pl_x) * CellSize)) {
					player.robotState = RobotState.UpLeftWall;
					player.p.x = ((pl_x) * CellSize) - 20;
					player.anim_time = 0.8f;
					player.p.y = ((pl_y - 1) * CellSize) + 100;
					player.touch_ground = false;
					player.touch_up_wall = false;
					robot_jump_temp = robot_jump;
				} else if ((lab[pl_x - 1][pl_y + 1] == 0 || lab[pl_x - 1][pl_y + 1] == 99)
						&& (lab[pl_x - 1][pl_y] == 51 || lab[pl_x - 1][pl_y] == 52 || lab[pl_x - 1][pl_y] == 62)
						&& (player.pl_board[1].y > (pl_y + 1) * CellSize)
						&& (player.pl_board[0].x < (pl_x) * CellSize)) {
					player.robotState = RobotState.UpLeftWall;
					player.p.x = ((pl_x) * CellSize) - 20;
					player.anim_time = 0.4f;
					player.p.y = ((pl_y) * CellSize) + 50;
					player.touch_ground = false;
					player.touch_up_wall = false;
					robot_jump_temp = robot_jump;
				} else if ((lab[pl_x - 1][pl_y + 1] == 22 || lab[pl_x - 1][pl_y + 1] == 25
						|| lab[pl_x - 1][pl_y + 1] == 52 || lab[pl_x - 1][pl_y + 1] == 33
						|| lab[pl_x - 1][pl_y + 1] == 62 || lab[pl_x - 1][pl_y + 1] == 15
						|| lab[pl_x - 1][pl_y + 1] == 54 || lab[pl_x - 1][pl_y + 1] == 51
						|| lab[pl_x - 1][pl_y + 1] == 61) && (lab[pl_x - 1][pl_y] == 0 || lab[pl_x - 1][pl_y] == 98)
						&& (player.pl_board[1].x - 20 < ((pl_x) * CellSize))
						&& (player.pl_board[1].y > ((pl_y + 1) * CellSize) + 30)) {
					player.p.x = ((pl_x) * CellSize) - 20;
					player.anim_time = 0;
					player.robotState = RobotState.StandOnLeftWall;
					player.touch_ground = false;
					player.touch_up_wall = false;
					player.may_go_left = true;
					robot_jump_temp = robot_jump;
				} else if ((((lab[pl_x - 1][pl_y] == 22 || lab[pl_x - 1][pl_y] == 25 || lab[pl_x - 1][pl_y] == 52
						|| lab[pl_x - 1][pl_y] == 33 || lab[pl_x - 1][pl_y] == 62 || lab[pl_x - 1][pl_y] == 15
						|| lab[pl_x - 1][pl_y] == 54 || lab[pl_x - 1][pl_y] == 51 || lab[pl_x - 1][pl_y] == 61)
						&& (player.pl_board[1].y < (pl_y + 1) * CellSize && player.pl_board[4].y > (pl_y) * CellSize))
						|| ((lab[pl_x - 1][pl_y] == 22 || lab[pl_x - 1][pl_y] == 25 || lab[pl_x - 1][pl_y] == 33
						|| lab[pl_x - 1][pl_y] == 15 || lab[pl_x - 1][pl_y] == 54 || lab[pl_x - 1][pl_y] == 61)
						&& (lab[pl_x - 1][pl_y - 1] == 0 || lab[pl_x - 1][pl_y - 1] == 98)
						&& (lab[pl_x - 1][pl_y + 1] == 22 || lab[pl_x - 1][pl_y + 1] == 25
						|| lab[pl_x - 1][pl_y + 1] == 52 || lab[pl_x - 1][pl_y + 1] == 33
						|| lab[pl_x - 1][pl_y + 1] == 62 || lab[pl_x - 1][pl_y + 1] == 15)
						&& (player.pl_board[4].y > (pl_y) * CellSize
						&& player.pl_board[1].y < (pl_y + 2) * CellSize))
						|| ((lab[pl_x - 1][pl_y] == 22 || lab[pl_x - 1][pl_y] == 25 || lab[pl_x - 1][pl_y] == 33
						|| lab[pl_x - 1][pl_y] == 15)
						&& (lab[pl_x - 1][pl_y - 1] == 15 || lab[pl_x - 1][pl_y - 1] == 22
						|| lab[pl_x - 1][pl_y - 1] == 25 || lab[pl_x - 1][pl_y - 1] == 33
						|| lab[pl_x - 1][pl_y - 1] == 54 || lab[pl_x - 1][pl_y - 1] == 61)
						&& (player.pl_board[4].y > (pl_y) * CellSize
						&& player.pl_board[1].y < (pl_y + 1) * CellSize)

				)) && (player.pl_board[1].x - 10 < pl_x * CellSize)) {
					player.p.x = ((pl_x) * CellSize) - 20;
					player.anim_time = 0;
					player.robotState = RobotState.StandLeftWall;
					robot_speed = 150;
					player.touch_ground = false;
					player.touch_up_wall = false;
					robot_jump_temp = robot_jump;
				}

				if(lab[pl_x][pl_y]!=0 && lab[pl_x][pl_y]!=99 && lab[pl_x][pl_y]!=98){
					pl_x=pl_x+1;
					player.p.x = ((pl_x) * CellSize) - 20;
					player.anim_time = 0;
					player.robotState = RobotState.StandLeftWall;
					robot_speed = 150;
					player.touch_ground = false;
					player.touch_up_wall = false;
					robot_jump_temp = robot_jump;
				}

				if (player.robotState == RobotState.StandLeftWall || player.robotState == RobotState.OnLeftWall
						|| player.robotState == RobotState.UpLeftWall) {
					player.may_go_left = true;
					player.may_go_right = true;
				}
			}
			//
			// ПРИЖКИ В П Р А В О
			//
			else if (player.robotState == RobotState.JumpLeftWallOnRight || player.robotState == RobotState.JumpRight
					|| player.robotState == RobotState.JumpStandRight) {
				if ((player.robotState == RobotState.JumpRight || player.robotState == RobotState.JumpStandRight)
						&& (lab[pl_x + 1][pl_y] == 22 || lab[pl_x + 1][pl_y] == 25 || lab[pl_x + 1][pl_y] == 26
						|| lab[pl_x + 1][pl_y] == 27 || lab[pl_x + 1][pl_y] == 17)
						&& ((player.pl_board[2].x > ((pl_x + 1) * CellSize))
						|| (player.pl_board[3].x > ((pl_x + 1) * CellSize)))) {
					player.may_go_left = true;
					player.may_go_right = false;
				}
				if ((lab[pl_x + 1][pl_y] == 0 || lab[pl_x + 1][pl_y] == 99)
						&& (lab[pl_x + 1][pl_y - 1] == 52 || lab[pl_x + 1][pl_y - 1] == 53
						|| lab[pl_x + 1][pl_y - 1] == 63)
						&& (player.pl_board[4].y > (pl_y) * CellSize)
						&& (player.pl_board[2].x > (pl_x + 1) * CellSize)) {
					player.robotState = RobotState.UpRightWall;
					player.p.x = ((pl_x + 1) * CellSize) - 80;
					player.anim_time = 0.8f;
					player.p.y = ((pl_y - 1) * CellSize) + 100;
					player.touch_ground = false;
					player.touch_up_wall = false;
					robot_jump_temp = robot_jump;
				} else if ((lab[pl_x + 1][pl_y + 1] == 0 || lab[pl_x + 1][pl_y + 1] == 99)
						&& (lab[pl_x + 1][pl_y] == 52 || lab[pl_x + 1][pl_y] == 53 || lab[pl_x + 1][pl_y] == 63)
						&& (player.pl_board[3].y > (pl_y + 1) * CellSize)
						&& (player.pl_board[2].x > (pl_x + 1) * CellSize)) {
					player.robotState = RobotState.UpRightWall;
					player.p.x = ((pl_x + 1) * CellSize) - 80;
					player.anim_time = 0.4f;
					player.p.y = ((pl_y) * CellSize) + 50;
					player.touch_ground = false;
					player.touch_up_wall = false;
					robot_jump_temp = robot_jump;
				} else if ((lab[pl_x + 1][pl_y + 1] == 22 || lab[pl_x + 1][pl_y + 1] == 52
						|| lab[pl_x + 1][pl_y + 1] == 17 || lab[pl_x + 1][pl_y + 1] == 63
						|| lab[pl_x + 1][pl_y + 1] == 31 || lab[pl_x + 1][pl_y + 1] == 64
						|| lab[pl_x + 1][pl_y + 1] == 52 || lab[pl_x + 1][pl_y + 1] == 54
						|| lab[pl_x + 1][pl_y + 1] == 53) && (lab[pl_x + 1][pl_y] == 0 || lab[pl_x + 1][pl_y] == 98)
						&& (player.pl_board[3].x + 10 > ((pl_x + 1) * CellSize) - 5)
						&& (player.pl_board[3].y > ((pl_y + 1) * CellSize) + 30)) {
					player.p.x = ((pl_x) * CellSize) + 120;
					player.anim_time = 0;
					player.robotState = RobotState.StandOnRightWall;
					player.touch_ground = false;
					player.touch_up_wall = false;
					player.may_go_right = true;
					robot_jump_temp = robot_jump;
				} else if ((((lab[pl_x + 1][pl_y] == 22 || lab[pl_x + 1][pl_y] == 26 || lab[pl_x + 1][pl_y] == 52
						|| lab[pl_x + 1][pl_y] == 17 || lab[pl_x + 1][pl_y] == 63 || lab[pl_x + 1][pl_y] == 31
						|| lab[pl_x + 1][pl_y] == 64 || lab[pl_x + 1][pl_y] == 54 || lab[pl_x + 1][pl_y] == 53)
						&& (player.pl_board[3].y < (pl_y + 1) * CellSize && player.pl_board[4].y > (pl_y) * CellSize))
						|| ((lab[pl_x + 1][pl_y] == 22 || lab[pl_x + 1][pl_y] == 26 || lab[pl_x + 1][pl_y] == 17
						|| lab[pl_x + 1][pl_y] == 31 || lab[pl_x + 1][pl_y] == 64 || lab[pl_x + 1][pl_y] == 54)
						&& (lab[pl_x + 1][pl_y - 1] == 0 || lab[pl_x + 1][pl_y - 1] == 98)
						&& (lab[pl_x + 1][pl_y + 1] == 22 || lab[pl_x + 1][pl_y + 1] == 26
						|| lab[pl_x + 1][pl_y + 1] == 52 || lab[pl_x + 1][pl_y + 1] == 17
						|| lab[pl_x + 1][pl_y + 1] == 63 || lab[pl_x + 1][pl_y + 1] == 31)
						&& (player.pl_board[4].y > (pl_y) * CellSize
						&& player.pl_board[3].y < (pl_y + 2) * CellSize))
						|| ((lab[pl_x + 1][pl_y] == 22 || lab[pl_x + 1][pl_y] == 26 || lab[pl_x + 1][pl_y] == 17
						|| lab[pl_x + 1][pl_y] == 31)
						&& (lab[pl_x + 1][pl_y - 1] == 17 || lab[pl_x + 1][pl_y - 1] == 22
						|| lab[pl_x + 1][pl_y - 1] == 26 || lab[pl_x + 1][pl_y - 1] == 31
						|| lab[pl_x + 1][pl_y - 1] == 54 || lab[pl_x + 1][pl_y - 1] == 64)
						&& (player.pl_board[4].y > (pl_y) * CellSize
						&& player.pl_board[3].y < (pl_y + 1) * CellSize)))
						&& (player.pl_board[3].x + 20 > (pl_x + 1) * CellSize)) {
					player.p.x = ((pl_x + 1) * CellSize) - 80;
					player.anim_time = 0;
					player.robotState = RobotState.StandRightWall;
					robot_speed = 150;
					player.touch_ground = false;
					player.touch_up_wall = false;
					robot_jump_temp = robot_jump;
				}

				if(lab[pl_x][pl_y]!=0 && lab[pl_x][pl_y]!=99 && lab[pl_x][pl_y]!=98){
					pl_x=pl_x-1;
					player.p.x = ((pl_x + 1) * CellSize) - 80;
					player.anim_time = 0;
					player.robotState = RobotState.StandRightWall;
					robot_speed = 150;
					player.touch_ground = false;
					player.touch_up_wall = false;
					robot_jump_temp = robot_jump;
				}

				if (player.robotState == RobotState.StandRightWall || player.robotState == RobotState.OnRightWall
						|| player.robotState == RobotState.UpRightWall) {
					player.may_go_left = true;
					player.may_go_right = true;
				}
			}
			if (player.robotState == RobotState.JumpRight && player.may_go_right)
				player.p.x = player.p.x + (robot_speed * Gdx.graphics.getDeltaTime());
			else if (player.robotState == RobotState.JumpLeft && player.may_go_left)
				player.p.x = player.p.x - (robot_speed * Gdx.graphics.getDeltaTime());
		}
	}

	void ForWall() {
		if (sel.isChecked()) {

			if (player.robotState == RobotState.OnLeftWall || player.robotState == RobotState.OnRightWall) {
				player.anim_time = player.anim_time + Gdx.graphics.getDeltaTime();
				if (player.anim_time >= 0.4f && player.anim_time <= 0.7f) {
					player.p.y = player.p.y + (450 * Gdx.graphics.getDeltaTime());
				}
				if (player.anim_time >= 0.75f) {
					if (player.robotState == RobotState.OnLeftWall) {
						player.anim_time = 0;
						player.p.x = ((pl_x) * CellSize) - 20;
						player.robotState = RobotState.StandLeftWall;
					} else if (player.robotState == RobotState.OnRightWall) {
						player.anim_time = 0;
						player.p.x = ((pl_x) * CellSize) + 120;
						player.robotState = RobotState.StandRightWall;
					}
					robot_speed = 150;
				}
			} else if (player.robotState == RobotState.UpLeftWall || player.robotState == RobotState.UpRightWall) {
				player.anim_time = player.anim_time + Gdx.graphics.getDeltaTime();
				if (player.anim_time >= 0.1f && player.anim_time <= 0.25f) {
					player.p.y = player.p.y + (80 * Gdx.graphics.getDeltaTime());
				} else if (player.anim_time >= 0.25f && player.anim_time <= 0.35f) {
					player.p.y = player.p.y + (40 * Gdx.graphics.getDeltaTime());
				} else if (player.anim_time >= 0.40f && player.anim_time <= 0.6f) {
					player.p.y = player.p.y + (80 * Gdx.graphics.getDeltaTime());
				} else if (player.anim_time >= 0.6f && player.anim_time <= 0.65f) {
					player.p.y = player.p.y + (280 * Gdx.graphics.getDeltaTime());
				} else if (player.anim_time >= 0.65f && player.anim_time <= 0.7f) {
					player.p.y = player.p.y + (70 * Gdx.graphics.getDeltaTime());
				} else if (player.anim_time >= 0.7f && player.anim_time <= 0.75f) {
					player.p.y = player.p.y + (220 * Gdx.graphics.getDeltaTime());
				} else if (player.anim_time >= 0.75f && player.anim_time <= 0.8f) {
					player.p.y = player.p.y + (80 * Gdx.graphics.getDeltaTime());
				} else if (player.anim_time >= 0.8f && player.anim_time <= 0.85f) {
					player.p.y = player.p.y + (20 * Gdx.graphics.getDeltaTime());
				} else if (player.anim_time >= 1.05f && player.anim_time <= 1.2f) {
					player.p.y = player.p.y + (100 * Gdx.graphics.getDeltaTime());
				} else if (player.anim_time >= 1.2f && player.anim_time <= 1.3f) {
					player.p.y = player.p.y + (180 * Gdx.graphics.getDeltaTime());
				} else if (player.anim_time >= 1.3f && player.anim_time <= 1.35f) {
					player.p.y = player.p.y + (200 * Gdx.graphics.getDeltaTime());
				} else if (player.anim_time >= 1.35f && player.anim_time <= 1.4f) {
					player.p.y = player.p.y + (400 * Gdx.graphics.getDeltaTime());
				} else if (player.anim_time >= 1.4f && player.anim_time <= 1.45f) {
					player.p.y = player.p.y + (300 * Gdx.graphics.getDeltaTime());
				} else if (player.anim_time >= 1.45f && player.anim_time <= 1.6f) {
					player.p.y = (pl_y) * CellSize;
				}
				if (player.anim_time >= 0.8f && player.anim_time <= 0.9f) {
					if (player.robotState == RobotState.UpLeftWall)
						player.p.x = player.p.x - (280 * Gdx.graphics.getDeltaTime());
					else if (player.robotState == RobotState.UpRightWall)
						player.p.x = player.p.x + (280 * Gdx.graphics.getDeltaTime());
				} else if (player.anim_time >= 0.9f && player.anim_time <= 1.15f) {
					if (player.robotState == RobotState.UpLeftWall)
						player.p.x = player.p.x - (50 * Gdx.graphics.getDeltaTime());
					else if (player.robotState == RobotState.UpRightWall)
						player.p.x = player.p.x + (50 * Gdx.graphics.getDeltaTime());
				}
				if (player.anim_time >= 1.65f) {
					if (player.robotState == RobotState.UpLeftWall) {
						player.robotState = RobotState.StandLeft;
					} else if (player.robotState == RobotState.UpRightWall) {
						player.robotState = RobotState.StandRight;
					}
					robot_speed = 100;
				}
			}

			else if (player.robotState == RobotState.DownLeftWall || player.robotState == RobotState.DownRightWall) {
				player.anim_time = player.anim_time + Gdx.graphics.getDeltaTime();
				if (player.anim_time >= 1.4f && player.anim_time <= 1.55f) {
					player.p.y = player.p.y - (80 * Gdx.graphics.getDeltaTime());
				} else if (player.anim_time >= 1.3f && player.anim_time <= 1.4f) {
					player.p.y = player.p.y - (40 * Gdx.graphics.getDeltaTime());
				} else if (player.anim_time >= 1.05f && player.anim_time <= 1.25f) {
					player.p.y = player.p.y - (80 * Gdx.graphics.getDeltaTime());
				} else if (player.anim_time >= 1f && player.anim_time <= 1.05f) {
					player.p.y = player.p.y - (280 * Gdx.graphics.getDeltaTime());
				} else if (player.anim_time >= 0.95f && player.anim_time <= 1f) {
					player.p.y = player.p.y - (70 * Gdx.graphics.getDeltaTime());
				} else if (player.anim_time >= 0.9f && player.anim_time <= 0.95f) {
					player.p.y = player.p.y - (220 * Gdx.graphics.getDeltaTime());
				} else if (player.anim_time >= 0.85f && player.anim_time <= 0.9f) {
					player.p.y = player.p.y - (80 * Gdx.graphics.getDeltaTime());
				} else if (player.anim_time >= 0.8f && player.anim_time <= 0.85f) {
					player.p.y = player.p.y - (20 * Gdx.graphics.getDeltaTime());
				} else if (player.anim_time >= 0.45f && player.anim_time <= 0.6f) {
					player.p.y = player.p.y - (100 * Gdx.graphics.getDeltaTime());
				} else if (player.anim_time >= 0.35f && player.anim_time <= 0.45f) {
					player.p.y = player.p.y - (180 * Gdx.graphics.getDeltaTime());
				} else if (player.anim_time >= 0.3f && player.anim_time <= 0.35f) {
					player.p.y = player.p.y - (200 * Gdx.graphics.getDeltaTime());
				} else if (player.anim_time >= 0.25f && player.anim_time <= 0.3f) {
					player.p.y = player.p.y - (400 * Gdx.graphics.getDeltaTime());
				} else if (player.anim_time >= 0.2f && player.anim_time <= 0.25f) {
					player.p.y = player.p.y - (700 * Gdx.graphics.getDeltaTime());
				} else if (player.anim_time >= 0.05f && player.anim_time <= 0.2f) {
					player.p.y = (pl_y) * CellSize;
				}
				if (player.anim_time >= 0.75f && player.anim_time <= 0.85f) {
					if (player.robotState == RobotState.DownLeftWall)
						player.p.x = player.p.x + (280 * Gdx.graphics.getDeltaTime());
					else if (player.robotState == RobotState.DownRightWall)
						player.p.x = player.p.x - (300 * Gdx.graphics.getDeltaTime());
				} else if (player.anim_time >= 0.5f && player.anim_time <= 0.75f) {
					if (player.robotState == RobotState.DownLeftWall)
						player.p.x = player.p.x + (50 * Gdx.graphics.getDeltaTime());
					else if (player.robotState == RobotState.DownRightWall)
						player.p.x = player.p.x - (50 * Gdx.graphics.getDeltaTime());
				}
				if (player.anim_time >= 1.55f) {
					robot_speed = 150;
					player.anim_time = 0;
					player.p.y = player.p.y - 20;
					if (player.robotState == RobotState.DownLeftWall) {
						player.robotState = RobotState.StandLeftWall;
						player.p.x = ((pl_x) * CellSize) - 20;
					} else if (player.robotState == RobotState.DownRightWall) {
						player.robotState = RobotState.StandRightWall;
						player.p.x = ((pl_x + 1) * CellSize) - 80;
					}
				}
			} else if (player.robotState == RobotState.StandOnLeftWall
					|| player.robotState == RobotState.StandOnRightWall) {
				if (player.robotState == RobotState.StandOnLeftWall) {
					if (touchpad.getKnobPercentY() > 0.5f)
						player.robotState = RobotState.OnLeftWall;
					else if (touchpad.getKnobPercentY() < -0.5f)
						player.robotState = RobotState.OffLeftWall;
				} else if (player.robotState == RobotState.StandOnRightWall) {
					if (touchpad.getKnobPercentY() > 0.5f)
						player.robotState = RobotState.OnRightWall;
					else if (touchpad.getKnobPercentY() < -0.5f)
						player.robotState = RobotState.OffRightWall;
				}
				if (player.robotState == RobotState.OffLeftWall || player.robotState == RobotState.OffRightWall) {
					player.anim_time = 0;
					robot_jump_temp = -1;
				}
			}

			if (jumpButton.isPressed()) {
				if (touchpad.getKnobPercentX() < -0.8f && player.robotState == RobotState.StandRightWall)
					player.robotState = RobotState.JumpRightWallOnLeft;
				else if (touchpad.getKnobPercentX() > 0.8f && player.robotState == RobotState.StandLeftWall)
					player.robotState = RobotState.JumpLeftWallOnRight;
				else if ((touchpad.getKnobPercentX() < 0.1f && touchpad.getKnobPercentX() > -0.1f)
						&& player.robotState == RobotState.StandRightWall) {
					player.robotState = RobotState.JumpStandRight;
					player.anim_time = 0.85f;
					robot_jump_temp = -1;
					player.touch_up_wall = true;
					player.p.x = player.p.x - 20;
					robot_speed = 260;
				} else if ((touchpad.getKnobPercentX() < 0.1f && touchpad.getKnobPercentX() > -0.1f)
						&& player.robotState == RobotState.StandLeftWall) {
					player.robotState = RobotState.JumpStandLeft;
					player.anim_time = 0.85f;
					robot_jump_temp = -1;
					player.touch_up_wall = true;
					player.p.x = player.p.x + 20;
					robot_speed = 260;
				}
			}
			if ((player.robotState == RobotState.StandRightWall || player.robotState == RobotState.GoRightWallDown)
					&& touchpad.getKnobPercentY() > 0.5f)
				player.robotState = RobotState.GoRightWallUp;
			else if ((player.robotState == RobotState.StandRightWall || player.robotState == RobotState.GoRightWallUp)
					&& touchpad.getKnobPercentY() < -0.5f)
				player.robotState = RobotState.GoRightWallDown;
			else if ((player.robotState == RobotState.StandLeftWall || player.robotState == RobotState.GoLeftWallDown)
					&& touchpad.getKnobPercentY() > 0.5f)
				player.robotState = RobotState.GoLeftWallUp;
			else if ((player.robotState == RobotState.StandLeftWall || player.robotState == RobotState.GoLeftWallUp)
					&& touchpad.getKnobPercentY() < -0.5f)
				player.robotState = RobotState.GoLeftWallDown;
			else if (touchpad.getKnobPercentY() > -0.1f && touchpad.getKnobPercentY() < 0.1f
					&& (player.robotState == RobotState.GoRightWallUp
					|| player.robotState == RobotState.GoRightWallDown)) {
				player.robotState = RobotState.StandRightWall;
				player.anim_time = 0;
			} else if (touchpad.getKnobPercentY() > -0.1f && touchpad.getKnobPercentY() < 0.1f
					&& (player.robotState == RobotState.GoLeftWallUp
					|| player.robotState == RobotState.GoLeftWallDown)) {
				player.robotState = RobotState.StandLeftWall;
				player.anim_time = 0;
			} else if ((player.robotState == RobotState.GoRightWallUp
					|| player.robotState == RobotState.GoLeftWallUp)) {
				player.anim_time = player.anim_time + Gdx.graphics.getDeltaTime();
				if ((lab[pl_x][pl_y + 1] == 21 || lab[pl_x][pl_y + 1] == 10)
						&& player.pl_board[3].y > ((pl_y + 1) * CellSize) - 5)
					player.p.y = player.p.y;
				else
					player.p.y = player.p.y + (robot_speed * Gdx.graphics.getDeltaTime());
				if ((lab[pl_x - 1][pl_y + 1] == 0 || lab[pl_x - 1][pl_y + 1] == 99)
						&& ((player.pl_board[1].y > ((pl_y + 1) * CellSize))
						&& player.robotState == RobotState.GoLeftWallUp)) {
					player.robotState = RobotState.UpLeftWall;
					player.anim_time = 0;
				} else if ((lab[pl_x + 1][pl_y + 1] == 0 || lab[pl_x + 1][pl_y + 1] == 99)
						&& ((player.pl_board[3].y > ((pl_y + 1) * CellSize))
						&& player.robotState == RobotState.GoRightWallUp)) {
					player.robotState = RobotState.UpRightWall;
					player.anim_time = 0;
				}
			} else if (player.robotState == RobotState.GoRightWallDown
					|| player.robotState == RobotState.GoLeftWallDown) {
				player.anim_time = player.anim_time + Gdx.graphics.getDeltaTime();
				if ((lab[pl_x][pl_y - 1] == 21 || lab[pl_x][pl_y - 1] == 16)
						&& player.pl_board[2].y < ((pl_y) * CellSize) + 5) {
					if (player.robotState == RobotState.GoRightWallDown) {
						player.robotState = RobotState.StandRight;
						player.p.x = player.p.x - 20;
					} else if (player.robotState == RobotState.GoLeftWallDown) {
						player.robotState = RobotState.StandLeft;
						player.p.x = player.p.x + 20;
					}
					player.p.y = (pl_y) * CellSize;
				} else if (player.robotState == RobotState.GoRightWallDown
						&& (lab[pl_x + 1][pl_y] == 0 || lab[pl_x + 1][pl_y] == 98)
						&& (player.pl_board[3].y < ((pl_y + 1) * CellSize) + 30)) {
					player.anim_time = 0;
					robot_jump_temp = -1;
					player.robotState = RobotState.StandOnRightWall;
				} else if (player.robotState == RobotState.GoLeftWallDown
						&& (lab[pl_x - 1][pl_y] == 0 || lab[pl_x - 1][pl_y] == 98)
						&& (player.pl_board[1].y < ((pl_y + 1) * CellSize) + 30)) {
					player.anim_time = 0;
					robot_jump_temp = -1;
					player.robotState = RobotState.StandOnLeftWall;
				} else
					player.p.y = player.p.y - (robot_speed * Gdx.graphics.getDeltaTime());
			}
		}

	}

	void showGP() {
		if (sel.isChecked()) {
			sel.setText("Robot");
			if (cam.position.x < maxXrange || cam.position.x - 635 > 0 || cam.position.y < maxYrange
					|| cam.position.y - 380 > 0) {
				if (player.p.x < cam.position.x - 100)
					cam.position.x = cam.position.x + ((player.p.x - (cam.position.x - 100)) * 0.06f);
				if (player.p.x > cam.position.x + 50)
					cam.position.x = cam.position.x + ((player.p.x - (cam.position.x + 50)) * 0.06f);
				if (player.p.y < cam.position.y - 50)
					cam.position.y = cam.position.y + ((player.p.y - (cam.position.y - 50)) * 0.06f);
				if (player.p.y > cam.position.y + 10)
					cam.position.y = cam.position.y + ((player.p.y - (cam.position.y + 10)) * 0.06f);
			}
			if (cam.position.x > maxXrange)
				cam.position.x = CellSize * (ww + ww + 1) - 641;
			if (cam.position.x < minXrange)
				cam.position.x = 641;
			if (cam.position.y > maxYrange)
				cam.position.y = CellSize * (hh + hh + 1) - 385;
			if (cam.position.y < minYrange)
				cam.position.y = 385;
		} else {
			if (cam.position.x > maxXrange)
				cam.position.x = CellSize * (ww + ww + 1) - 643;
			if (cam.position.x < minXrange)
				cam.position.x = 643;
			if (cam.position.y > maxYrange)
				cam.position.y = CellSize * (hh + hh + 1) - 387;
			if (cam.position.y < minYrange)
				cam.position.y = 387;
			if (cam.position.x < maxXrange || cam.position.x - 640 > 0 || cam.position.y < maxYrange
					|| cam.position.y - 384 > 0) {
				cam.position.x = cam.position.x + touchpad.getKnobPercentX() * speed_cam;
				cam.position.y = cam.position.y + touchpad.getKnobPercentY() * speed_cam;
			}
			sel.setText("Camera");
		}
	}

	void ShowBackground() {

		int nx = 0, ny = 0, ex = 27, ey = 17, dx = 0, dy = 0;
		if (pl_x > 13) {
			nx = pl_x - 14;
			ex = pl_x + 13;
		}
		if (pl_y > 8) {
			ny = pl_y - 9;
			ey = pl_y + 8;
		}
		if (pl_x > ww + ww - 13) {
			nx = ww + ww - 26;
			ex = ww + ww + 1;
		}
		if (pl_y > hh + hh - 8) {
			ny = hh + hh - 16;
			ey = hh + hh + 1;
		}

		for (dx = 0; nx + dx < ex; dx++)
			for (dy = 0; ny + dy < ey; dy++) {
				if (lab[nx + dx][ny + dy] == 0 || lab[nx + dx][ny + dy] == 25 || lab[nx + dx][ny + dy] == 26
						|| lab[nx + dx][ny + dy] == 27)
					batch.draw(walls.findRegion("fone_labyrint"), (nx + dx) * CellSize, (ny + dy) * CellSize);
				if (lab[nx + dx][ny + dy] == 98)
					batch.draw(walls.findRegion("start"), (nx + dx) * CellSize, (ny + dy) * CellSize);
				if (lab[nx + dx][ny + dy] == 99)
					batch.draw(walls.findRegion("finish"), (nx + dx) * CellSize, (ny + dy) * CellSize);
			}
		if (game_forScreen.curentLvl == 10)
			batch.draw(walls.findRegion("finish"), flx * CellSize, fly * CellSize);
	}

	void ShowM() {

		int nx = 0, ny = 0, ex = 27, ey = 17, dx = 0, dy = 0;
		if (pl_x > 13) {
			nx = pl_x - 14;
			ex = pl_x + 13;
		}
		if (pl_y > 8) {
			ny = pl_y - 9;
			ey = pl_y + 8;
		}
		if (pl_x > ww + ww - 13) {
			nx = ww + ww - 26;
			ex = ww + ww + 1;
		}
		if (pl_y > hh + hh - 8) {
			ny = hh + hh - 16;
			ey = hh + hh + 1;
		}
		for (dx = 0; nx + dx < ex; dx++) {
			for (dy = 0; ny + dy < ey; dy++) {
				if (lab[nx + dx][ny + dy] == 4)
					batch.draw(walls.findRegion("4"), (nx + dx) * CellSize, (ny + dy) * CellSize);
				else if (lab[nx + dx][ny + dy] == 31)
					batch.draw(walls.findRegion("31"), (nx + dx) * CellSize, (ny + dy) * CellSize);
				else if (lab[nx + dx][ny + dy] == 32)
					batch.draw(walls.findRegion("34"), (nx + dx) * CellSize, (ny + dy) * CellSize);
				else if (lab[nx + dx][ny + dy] == 33)
					batch.draw(walls.findRegion("33"), (nx + dx) * CellSize, (ny + dy) * CellSize);
				else if (lab[nx + dx][ny + dy] == 34)
					batch.draw(walls.findRegion("32"), (nx + dx) * CellSize, (ny + dy) * CellSize);
				else if (lab[nx + dx][ny + dy] == 51)
					batch.draw(walls.findRegion("51"), (nx + dx) * CellSize, (ny + dy) * CellSize);
				else if (lab[nx + dx][ny + dy] == 52)
					batch.draw(walls.findRegion("54"), (nx + dx) * CellSize, (ny + dy) * CellSize);
				else if (lab[nx + dx][ny + dy] == 53)
					batch.draw(walls.findRegion("53"), (nx + dx) * CellSize, (ny + dy) * CellSize);
				else if (lab[nx + dx][ny + dy] == 54)
					batch.draw(walls.findRegion("52"), (nx + dx) * CellSize, (ny + dy) * CellSize);
				else if (lab[nx + dx][ny + dy] == 61)
					batch.draw(walls.findRegion("62"), (nx + dx) * CellSize, (ny + dy) * CellSize);
				else if (lab[nx + dx][ny + dy] == 62)
					batch.draw(walls.findRegion("61"), (nx + dx) * CellSize, (ny + dy) * CellSize);
				else if (lab[nx + dx][ny + dy] == 63)
					batch.draw(walls.findRegion("64"), (nx + dx) * CellSize, (ny + dy) * CellSize);
				else if (lab[nx + dx][ny + dy] == 64)
					batch.draw(walls.findRegion("63"), (nx + dx) * CellSize, (ny + dy) * CellSize);
				else if (lab[nx + dx][ny + dy] == 21)
					batch.draw(walls.findRegion("21"), (nx + dx) * CellSize, (ny + dy) * CellSize);
				else if (lab[nx + dx][ny + dy] == 22)
					batch.draw(walls.findRegion("22"), (nx + dx) * CellSize, (ny + dy) * CellSize);

				else if (lab[nx + dx][ny + dy] == 27)
					batch.draw(walls.findRegion("27"), (nx + dx) * CellSize, (ny + dy) * CellSize);
				else if (lab[nx + dx][ny + dy] == 26)
					batch.draw(walls.findRegion("26"), (nx + dx) * CellSize, (ny + dy) * CellSize);
				else if (lab[nx + dx][ny + dy] == 25)
					batch.draw(walls.findRegion("25"), (nx + dx) * CellSize, (ny + dy) * CellSize);

				else if (lab[nx + dx][ny + dy] == 71)
					batch.draw(walls.findRegion("73"), (nx + dx) * CellSize, (ny + dy) * CellSize);
				else if (lab[nx + dx][ny + dy] == 72)
					batch.draw(walls.findRegion("72"), (nx + dx) * CellSize, (ny + dy) * CellSize);
				else if (lab[nx + dx][ny + dy] == 73)
					batch.draw(walls.findRegion("71"), (nx + dx) * CellSize, (ny + dy) * CellSize);
				else if (lab[nx + dx][ny + dy] == 74)
					batch.draw(walls.findRegion("74"), (nx + dx) * CellSize, (ny + dy) * CellSize);
				else if (lab[nx + dx][ny + dy] == 11)
					batch.draw(walls.findRegion("12"), (nx + dx) * CellSize, (ny + dy) * CellSize);
				else if (lab[nx + dx][ny + dy] == 12)
					batch.draw(walls.findRegion("11"), (nx + dx) * CellSize, (ny + dy) * CellSize);
				else if (lab[nx + dx][ny + dy] == 13)
					batch.draw(walls.findRegion("14"), (nx + dx) * CellSize, (ny + dy) * CellSize);
				else if (lab[nx + dx][ny + dy] == 14)
					batch.draw(walls.findRegion("13"), (nx + dx) * CellSize, (ny + dy) * CellSize);
				else if (lab[nx + dx][ny + dy] == 10)
					batch.draw(walls.findRegion("16"), (nx + dx) * CellSize, (ny + dy) * CellSize);
				else if (lab[nx + dx][ny + dy] == 15)
					batch.draw(walls.findRegion("15"), (nx + dx) * CellSize, (ny + dy) * CellSize);
				else if (lab[nx + dx][ny + dy] == 16)
					batch.draw(walls.findRegion("10"), (nx + dx) * CellSize, (ny + dy) * CellSize);
				else if (lab[nx + dx][ny + dy] == 17)
					batch.draw(walls.findRegion("17"), (nx + dx) * CellSize, (ny + dy) * CellSize);
			}
		}
	}

	public void for_str() {
		k_for_str = new String[21];

		int kbk = game_forScreen.curentLvl;
		if (kbk == 7 || kbk == 5 || kbk == 4 || kbk == 3 || kbk == 20 || kbk == 10) {
			k_str = k_for_str[kbk].split("@");
			k_msg.setText(k_str[0]);
		} else {
			k_str = new String[10];
			k_str[0] = "Нет сообщений";
			if(game_forScreen.for_lang==2)k_str[0] = "No messages";

			if(MathUtils.random(1)==1) sluch_panel();

			k_msg.setText(k_str[0]);
			k_up.setVisible(false);
			k_down.setVisible(false);
		}
	}

	public void sluch_panel() {
		clush_game=true;
		trevoga= Gdx.audio.newSound(Gdx.files.internal("g5/sz/g51.mp3"));

		k_str[0] = "Выход закрыт! "+"\n"
				+ "Найдите панель управления, "+"\n"
				+ "чтобы открыть выход";
		if(game_forScreen.for_lang==2)k_str[0] = "Output is closed!"+"\n"
				+ "Find the control panel,"+"\n"
				+ "to open the exit";



		system_btn_on = new Texture(Gdx.files.internal("g5/4.png"));
		system_btn_off = new Texture(Gdx.files.internal("g5/3.png"));
		for_onoff_sys=false;
		for_start_system_msg=false;
		boolean proverka_na_sf;
		do {
			for (x = 1; x < ww + ww - 1; x++){
				for (y = 2; y < hh + hh - 2; y++) {
					if (lab[x][y] == 0 && lab[x+1][y] == 0 && lab[x-1][y] == 0 && lab[x][y-1] == 21 && lab[x][y+1] == 21 && MathUtils.random(2)==1){
						sys_x=x;
						sys_y=y;
						sys1x=x;
						sys1y=y;
					}
				}
			}
			if (sys_x == 0 || sys_y == 0)
				proverka_na_sf = true;
			else
				proverka_na_sf = false;
		} while (proverka_na_sf);
		radar_lab[sys1x][sys1y]=5;

	}

	public void for_qqq(){
		// ----------Для ЗАДАЧИ №1-------------------------
		q_stage1 = new Stage(viewportHUD, batch);
		q_atl = new TextureAtlas("q_qest.pack");
		q_kpk1 = new Image(q_atl.findRegion("kpk"));
		q_font = new BitmapFont(Gdx.files.internal("quest.fnt"));

		q_up1 = new ImageButton(new TextureRegionDrawable(q_atl.findRegion("up")),
				new TextureRegionDrawable(q_atl.findRegion("up_p")));
		q_down1 = new ImageButton(new TextureRegionDrawable(q_atl.findRegion("down")),
				new TextureRegionDrawable(q_atl.findRegion("down_p")));
		q_yes1 = new ImageButton(new TextureRegionDrawable(q_atl.findRegion("yes")),
				new TextureRegionDrawable(q_atl.findRegion("yes_p")));

		q_1 = new Label("1", new Label.LabelStyle(q_font, q_font.getColor()));
		q_2 = new Label("+", new Label.LabelStyle(q_font, q_font.getColor()));
		q_3 = new Label("2", new Label.LabelStyle(q_font, q_font.getColor()));
		q_otv = new Label("=3", new Label.LabelStyle(q_font, q_font.getColor()));

		q_sel1 = new ImageButton(new TextureRegionDrawable(q_atl.findRegion("unselect")),
				new TextureRegionDrawable(q_atl.findRegion("select")));
		q_sel2 = new ImageButton(new TextureRegionDrawable(q_atl.findRegion("unselect")),
				new TextureRegionDrawable(q_atl.findRegion("select")));
		q_sel3 = new Image(q_atl.findRegion("select"));

		q_stage1.addActor(q_kpk1);
		q_stage1.addActor(q_up1);
		q_stage1.addActor(q_down1);
		q_stage1.addActor(q_yes1);
		q_stage1.addActor(q_1);
		q_stage1.addActor(q_2);
		q_stage1.addActor(q_3);
		q_stage1.addActor(q_otv);
		q_stage1.addActor(q_sel1);
		q_stage1.addActor(q_sel2);
		q_stage1.addActor(q_sel3);

		q_1.setPosition(camHUD.position.x - 215, camHUD.position.y - 60);
		q_2.setPosition(camHUD.position.x - 105, camHUD.position.y - 60);
		q_3.setPosition(camHUD.position.x + 5, camHUD.position.y - 60);
		q_otv.setPosition(camHUD.position.x + 120, camHUD.position.y - 60);
		q_sel1.setPosition(camHUD.position.x - 222, camHUD.position.y - 62);
		q_sel2.setPosition(camHUD.position.x - 2, camHUD.position.y - 62);
		q_yes1.setPosition(camHUD.position.x - 400, camHUD.position.y + 130);

		q1_1 = 0;
		q1_2 = 0;
		// -------------------------------------------------

		// ----------Для ЗАДАЧИ №2-------------------------
		q_stage2 = new Stage(viewportHUD, batch);
		q_kpk2 = new Image(q_atl.findRegion("kpk"));

		q_sel4 = new Image(q_atl.findRegion("select"));
		q_up2 = new ImageButton(new TextureRegionDrawable(q_atl.findRegion("up")),
				new TextureRegionDrawable(q_atl.findRegion("up_p")));
		q_down2 = new ImageButton(new TextureRegionDrawable(q_atl.findRegion("down")),
				new TextureRegionDrawable(q_atl.findRegion("down_p")));
		q_yes2 = new ImageButton(new TextureRegionDrawable(q_atl.findRegion("yes")),
				new TextureRegionDrawable(q_atl.findRegion("yes_p")));
		q_off2 = new ImageButton(new TextureRegionDrawable(q_atl.findRegion("off")),
				new TextureRegionDrawable(q_atl.findRegion("off_p")));

		q_select_numb = new Label("0", new Label.LabelStyle(q_font, q_font.getColor()));
		q_znak = new Label("", new Label.LabelStyle(q_font, q_font.getColor()));
		q_zag_numb = new Label("?", new Label.LabelStyle(q_font, q_font.getColor()));

		q_chisla = new int[10];
		for (int b123 = 0; b123 < q_chisla.length; b123++)
			q_chisla[b123] = b123;

		q_stage2.addActor(q_kpk2);
		q_stage2.addActor(q_sel4);
		q_stage2.addActor(q_up2);
		q_stage2.addActor(q_down2);
		q_stage2.addActor(q_off2);
		q_stage2.addActor(q_yes2);
		q_stage2.addActor(q_select_numb);
		q_stage2.addActor(q_znak);
		q_stage2.addActor(q_zag_numb);

		q_yes2.setPosition(camHUD.position.x - 400, camHUD.position.y + 130);
		q_off2.setPosition(camHUD.position.x - 400, camHUD.position.y - 230);
		// ------------------------------------------------
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void dispose() {

		batch.dispose();
		walls.dispose();
		ui_atl.dispose();
		stage.dispose();
		gfont.dispose();
		q_atl.dispose();
		q_stage1.dispose();
		q_stage2.dispose();
		bg_music.dispose();
		k_stage.dispose();
		k_atl.dispose();
		btn_b.dispose();
		bbbg.dispose();
		trevoga.dispose();
		system_btn_on.dispose();
		system_btn_off.dispose();
		vzlom_z.dispose();
		vzlom_o.dispose();
		player.dispose();

	}



}
