package com.qvardium.game.project.arwii.one;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.qvardium.game.project.arwii.one.ByPlayer.RobotState;

public class tutor_2 extends ScreenAdapter {

	// ---------------------
	MyGame game_forScreen;
	SpriteBatch batch;
	OrthographicCamera cam;
	OrthographicCamera camHUD;
	Viewport viewport;
	Viewport viewportHUD;

	// -------------------------------------

	// ------------------------
	int x, y;
	int ww, hh;
	int CellSize = 200;
	byte[][] lab;
	int start_loc, finish_lok, flx, fly;

	TextureAtlas walls;
	Emaze em;
	Kmaze km;
	Pmaze pm;
	// -------------------------------------

	// ---------------------------
	ByPlayer player;
	int pl_x, pl_y, i;
	int robot_speed = 100;
	float robot_jump = 5, robot_jump_temp = 5;
	// -------------------------------------

	// -----------------------
	TextureAtlas ui_atl;

	Stage stage;
	Touchpad touchpad;
	TouchpadStyle touchpadStyle;
	float maxXrange, maxYrange, minXrange, minYrange, speed_cam = 20;
	float xrang = 6.3f, yrang = 3.8f;
	float maxXr1, maxYr1;

	ImageButton jumpButton, actionButton, radarButton;

	CheckBox sel;
	CheckBoxStyle style_ch;
	BitmapFont gfont;

	Label informFPS, timer_info;

	boolean radarIsOn;

	Sprite bg_black;
	float bg_black_a;
	boolean end_game=false,the_start=true;
	byte for_end_game=0;
	// -------------------------------------
	// ------------------------
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

	ImageTextButton q1_tutor_btn, q2_tutor_btn;

	Stage q_stage2;
	Image q_kpk2;
	ImageButton q_up2, q_down2, q_yes2, q_off2;
	Image q_sel4;
	Label q_select_numb, q_znak, q_zag_numb;
	int[] q_chisla;
	boolean q2_ok = false;
	// ------------------------------------

	// -----------------------------
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
	Label k_msg;
	InputMultiplexer multiplexer;

	Slider slider_music, slider_effect;
	Image music_k, effect_k;
	Sound btn_b;

	String[] k_str;
	int k_curent = 0;
	String k_name;
	long press_time;
	// ------------------------------------

	//---------------- Для текущего уровня--------
	ImageTextButton tutor_btn,k_tutor_btn;
	byte[][] lab_t, lab_o;
	long time1;
	byte theStat;
	float ax1;
	long pausat;
	private String g_dif;
	//-------------------------------------
	private String k_sms_menu;
	private String game_sms_level;

	public tutor_2(MyGame game) {
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
		// ----------------------------------
		ww = 13;
		hh = 8;

		lab = new byte[ww + ww + 1][hh + hh + 1];
		lab_t = new byte[ww + ww + 1][hh + hh + 1];
		lab_o = new byte[ww + ww + 1][hh + hh + 1];

		walls = new TextureAtlas(Gdx.files.internal("epilog/tutor.pack"));

		em = new Emaze(ww, hh);
		em.GeneratMaze();
		em.ConvMaze(lab);
		em.Quest(lab, 1);

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

		flx = 0;
		fly = 0;

		int isp = 0;
		for (x = 1; x < ww + ww - 1; x++)
			for (y = 1; y < hh + hh - 1; y++) {
				if (lab[x][y] == 25 || lab[x][y] == 26 || lab[x][y] == 27)
					isp++;
			}

		tq1 = new Array<TheQuest1>();
		tq2 = new Array<TheQuest2>();

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
		for(int ff=1;ff<10;ff++){
			lab_t[ff][0]=16;
			lab_t[ff][4]=10;
			lab_t[ff][2]=21;
		}
		for(int ff=1;ff<4;ff++){
			lab_t[0][ff]=15;
			lab_t[10][ff]=17;
		}
		lab_t[1][1]=98;
		lab_t[9][1]=99;
		lab_t[0][2]=72;
		lab_t[10][2]=74;
		lab_t[3][2]=34;
		lab_t[3][1]=27;
		lab_t[3][0]=73;
		lab_t[5][2]=34;
		lab_t[5][1]=27;
		lab_t[5][0]=73;
		lab_t[0][0]=12;
		lab_t[0][4]=11;
		lab_t[10][0]=13;
		lab_t[10][4]=12;
		lab_o=lab;
		lab=lab_t;
		int kkk1=1;
		for (x = 1; x < ww + ww - 1; x++)
			for (y = 1; y < hh + hh - 1; y++)
				if (lab[x][y] == 25 || lab[x][y] == 26 || lab[x][y] == 27) {
					if (kkk1 == 1){
						tq1.add(new TheQuest1(x, y, game_forScreen.curentLvl));
						kkk1=0;
					}
					else if (kkk1 == 0)
						tq2.add(new TheQuest2(x, y, game_forScreen.curentLvl));
				}
		// -----------------------------------------------

		// ---------------------------------
		ui_atl = new TextureAtlas("ui_game.pack");

		touchpadStyle = new TouchpadStyle();
		touchpadStyle.background = new TextureRegionDrawable(ui_atl.findRegion("touch_tabel"));
		touchpadStyle.knob = new TextureRegionDrawable(ui_atl.findRegion("touch_tabel_knob"));
		touchpad = new Touchpad(10, touchpadStyle);
		touchpad.setBounds(15, 15, 300, 300);

		jumpButton = new ImageButton(new TextureRegionDrawable(ui_atl.findRegion("jump")),
				new TextureRegionDrawable(ui_atl.findRegion("jump_pres")));
		actionButton = new ImageButton(new TextureRegionDrawable(ui_atl.findRegion("action")),
				new TextureRegionDrawable(ui_atl.findRegion("action_pres")));
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
		radarButton.setPosition(camHUD.position.x + 340, camHUD.position.y + 184);
		timer_info.setPosition(camHUD.position.x - 50, camHUD.position.y + 330);

		sel.setChecked(true);
		radarIsOn = false;
		// -----------------------------------------------

		// --------------------------------------
		player = new ByPlayer(start_loc * CellSize, 200, game_forScreen.curentLvl, game_forScreen.trueLvl);
		player.RobotMay(true, true, true, true, false, false, false);
		// ------------------------------------------------

		// ---------------------------------------
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

		q1_tutor_btn  = new ImageTextButton("",
				new ImageTextButton.ImageTextButtonStyle
						(new TextureRegionDrawable(walls.findRegion("bggg")),
								new TextureRegionDrawable(walls.findRegion("bggg")),
								new TextureRegionDrawable(walls.findRegion("bggg")),
								gfont));

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
		q_stage1.addActor(q1_tutor_btn);

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

		// -----------------------------------
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
		q2_tutor_btn  = new ImageTextButton("",
				new ImageTextButton.ImageTextButtonStyle
						(new TextureRegionDrawable(walls.findRegion("bggg")),
								new TextureRegionDrawable(walls.findRegion("bggg")),
								new TextureRegionDrawable(walls.findRegion("bggg")),
								gfont));

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
		q_stage2.addActor(q2_tutor_btn);

		q_yes2.setPosition(camHUD.position.x - 400, camHUD.position.y + 130);
		q_off2.setPosition(camHUD.position.x - 400, camHUD.position.y - 230);
		// ------------------------------------------------

		// ---------------------
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

		k_tutor_btn  = new ImageTextButton("",
				new ImageTextButton.ImageTextButtonStyle
						(new TextureRegionDrawable(walls.findRegion("bggg")),
								new TextureRegionDrawable(walls.findRegion("bggg")),
								new TextureRegionDrawable(walls.findRegion("bggg")),
								gfont));

		k_msg = new Label("", new Label.LabelStyle(k_font, k_font.getColor()));
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
		k_stage.addActor(k_yes);
		k_stage.addActor(k_reset);
		//k_stage.addActor(slider_music);
		k_stage.addActor(slider_effect);
		//k_stage.addActor(music_k);
		k_stage.addActor(effect_k);
		k_stage.addActor(k_tutor_btn);

		k_up.setPosition(camHUD.position.x - 240, camHUD.position.y + 280);
		k_down.setPosition(camHUD.position.x - 240, camHUD.position.y - 330);
		k_menu.setPosition(camHUD.position.x + 290, camHUD.position.y + 200);
		k_mssage.setPosition(camHUD.position.x + 290, camHUD.position.y + 50);
		k_radar.setPosition(camHUD.position.x + 290, camHUD.position.y - 100);
		k_exit.setPosition(camHUD.position.x + 290, camHUD.position.y - 250);
		k_msg.setPosition(camHUD.position.x - 590, camHUD.position.y);
		k_yes.setPosition(camHUD.position.x - 500, camHUD.position.y + 50);
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
		k_down.setVisible(false);

		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(k_stage);
		multiplexer.addProcessor(gestureDetector);

		btn_b = Gdx.audio.newSound(Gdx.files.internal("sounds/clic.mp3"));
		game_forScreen.for_reset=false;

		String file = "Здесь могут быть "+"\n"
				+ "входящие сообщения,"+"\n"+
				"которые приходят "+"\n"
				+ "в начале уровня @ Вы дошли до второго уровня"+"\n"+
				"@ Чтобы увидеть лабиринт в большом"+"\n"+
				"масштабе нажмите на радар"+"\n"+
				"@ Здесь вы можете просматривать уровень,"+"\n"+
				"увеличивая/уменьшая масштаб и перемещая"+"\n"+
				"его по экрану"+"\n"+
				"@ "+"\n"+"\n"+
				"Так же вы можете прочитать"+"\n"+
				"входящие сообщения"+"\n"+
				"@ Выйти в главное меню,"+"\n"+
				"перезапустить уровень и"+"\n"+
				"настроить громкость"+"\n"+
				"@ Чтобы начать управлять роботом"+"\n"+
				"нажмите на кнопку выход"+"\n"+"\n"+"\n"+
				"@ В лабиринте встречаются стены"+"\n"+
				"со знаком вопроса."+"\n"+
				"На радаре они обозначены"+"\n"+
				"зеленым цветом"+"\n"+
				"@ Вы можете убрать эту стену,"+"\n"+
				"если решите задачу"+"\n"+
				"@ Для решения задачи подойдете"+"\n"+
				"к стене со знаком вопроса"+"\n"+
				"и нажмете кнопку действия"+"\n"+
				"@ Есть два вида задач. В одной задаче"+"\n"+
				"нужно решить пример,"+"\n"+
				"а в другой отгадать число"+"\n"+
				"@ Чем выше уровень, тем сложнее задача"+"\n"+
				"и больше лабиринт!"+"\n"+
				"@ Если не правильно решить пример"+"\n"+
				"или отказаться от угадывания числа,"+"\n"+
				"то эта стена станет обычной,"+"\n"+
				"и вы ее больше не сможете убрать"+"\n"+
				"@ Если вы решили задачу правильно,"+"\n"+
				"то стена исчезнет и возможно"+"\n"+
				"ваш путь до выхода станет короче"+"\n"+
				"@ Это последний обучающий уровень."+"\n"+
				"Желаю удачи в поиске выхода!"+"\n"+
				"@ ";

		if(game_forScreen.for_lang==2){
			file = "Here can be incoming"+"\n"
					+ "messages which come at"+"\n"+
					"the beginning"+"\n"
					+ "of the level @ "

					+ "You have reached the second level"+"\n"+

					"@ To see a maze on a large"+"\n"+
					"scale click a radar"+"\n"+

					"@ Here you can view level,"+"\n"+
					"increasing/reducing scale and moving"+"\n"+
					"it on the screen"+"\n"+

					"@ "+"\n"+"\n"+
					"Also you can read"+"\n"+
					"incoming messages"+"\n"+

					"@ Quit in the main menu,"+"\n"+
					"restart level and"+"\n"+
					"set up volume"+"\n"+

					"@ To begin controlling the robot"+"\n"+
					"press the output button"+"\n"+"\n"+"\n"+

					"@ In a maze question signed"+"\n"+
					"walls can be met."+"\n"+
					"On a radar they are designated"+"\n"+
					"in the green color"+"\n"+

					"@ You can clean this wall"+"\n"+
					"if solve the problem"+"\n"+

					"@ For the solution of the problem"+"\n"+
					"approach a signed wall of a question"+"\n"+
					"and press the action button"+"\n"+

					"@ There are two types of problems. In one "+"\n"+
					"problem it is necessary to solve an example,"+"\n"+
					"and in another to guess a number"+"\n"+

					"@ The level is higher, the problem is more"+"\n"+
					"maze and more difficult!"+"\n"+

					"@ ЕIf you don’t solve an example"+"\n"+
					"or to refuse guessing of number correctly,"+"\n"+
					"this wall will become normal,"+"\n"+
					"and you won't be able to clean it any more"+"\n"+

					"@ If you solved the problem correctly,"+"\n"+
					"the wall would disappear and perhaps"+"\n"+
					"your way to an output would become shorter"+"\n"+

					"@ It is the last learning level."+"\n"+
					"I wish good luck in search of the output!"+"\n"+
					"@ ";


		}

		k_str = file.split("@ ");
		k_msg.setText(k_str[0]);
		// ----------------------------

		tutor_btn  = new ImageTextButton(k_str[1],
				new ImageTextButton.ImageTextButtonStyle
						(new TextureRegionDrawable(walls.findRegion("bggg")),
								new TextureRegionDrawable(walls.findRegion("bggg")),
								new TextureRegionDrawable(walls.findRegion("bggg")),
								gfont));
		time1 = TimeUtils.millis();
		theStat = 1;

		touchpad.setVisible(false);
		jumpButton.setVisible(false);
		actionButton.setVisible(false);
		radarButton.setVisible(false);
		informFPS.setVisible(false);
		timer_info.setVisible(false);
		k_msg.setVisible(false);
		kpk_on=false;
		stage.addActor(tutor_btn);

		pausat=1000;

		press_time = TimeUtils.millis();
		Gdx.input.setInputProcessor(stage);

		bg_black = new Sprite(new Texture(Gdx.files.internal("black.png")), 0, 0, 1280, 768);
		bg_black.setPosition(camHUD.position.x-640, camHUD.position.y-384);
		bg_black_a = 1f;
	}


	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (TimeUtils.timeSinceMillis(time1) > pausat && theStat == 1 && tutor_btn.isPressed()){
			radarButton.setVisible(true);
			time1 = TimeUtils.millis();
			ax1=800;
			tutor_btn.setText(k_str[2]);
			theStat=2;
			stage.addActor(radarButton);
		}
		else if (TimeUtils.timeSinceMillis(time1) > pausat && theStat == 2 && radarButton.isPressed()){
			time1 = TimeUtils.millis();
			k_tutor_btn.setText(k_str[3]);
			tutor_btn.setText(k_str[7]);
			theStat=3;
			kpk_on=true;
			on_radar = true;
			gghh.on_radar=on_radar;
			Gdx.input.setInputProcessor(multiplexer);
			press_time = TimeUtils.millis();
		}
		else if (TimeUtils.timeSinceMillis(time1) > pausat && theStat == 3 && k_tutor_btn.isPressed()){
			time1 = TimeUtils.millis();
			k_tutor_btn.setText(k_str[4]);
			theStat=4;
		}
		else if (TimeUtils.timeSinceMillis(time1) > pausat && theStat == 4 && k_tutor_btn.isPressed()){
			time1 = TimeUtils.millis();
			k_tutor_btn.setText(k_str[5]);
			theStat=5;
		}
		else if (TimeUtils.timeSinceMillis(time1) > pausat && theStat == 5 && k_tutor_btn.isPressed()){
			time1 = TimeUtils.millis();
			k_tutor_btn.setText(k_str[6]);
			theStat=6;
		}
		else if (TimeUtils.timeSinceMillis(time1) > pausat && theStat == 6 && k_tutor_btn.isPressed()){
			time1 = TimeUtils.millis();
			theStat = 7;
			k_tutor_btn.setVisible(false);
			radarButton.setVisible(false);
			radarButton.setVisible(false);
		}
		else if (TimeUtils.timeSinceMillis(time1) > pausat && theStat == 7 && tutor_btn.isPressed() && !kpk_on){
			time1 = TimeUtils.millis();
			tutor_btn.setText(k_str[7]);
			theStat=8;

		}
		else if (TimeUtils.timeSinceMillis(time1) > pausat && theStat == 8 && tutor_btn.isPressed()){
			time1 = TimeUtils.millis();
			tutor_btn.setText(k_str[8]);
			theStat=9;
		}
		else if (TimeUtils.timeSinceMillis(time1) > pausat && theStat == 9 && tutor_btn.isPressed()){
			time1 = TimeUtils.millis();
			tutor_btn.setText(k_str[9]);
			theStat=10;
		}
		else if (TimeUtils.timeSinceMillis(time1) > pausat && theStat == 10 && tutor_btn.isPressed()){
			time1 = TimeUtils.millis();
			tutor_btn.setVisible(false);

			radarButton.setVisible(true);
			touchpad.setVisible(true);
			jumpButton.setVisible(true);
			actionButton.setVisible(true);
			informFPS.setVisible(true);
			timer_info.setVisible(true);

			q1_tutor_btn.setText(k_str[10]);
			q2_tutor_btn.setText(k_str[10]);
			theStat=10;
		}
		else if(theStat==10 && theQuest){
			theStat=11;
		}
		else if (TimeUtils.timeSinceMillis(time1) > pausat && theStat == 11 && (q1_tutor_btn.isPressed() || q2_tutor_btn.isPressed())){
			time1 = TimeUtils.millis();
			q1_tutor_btn.setText(k_str[11]);
			q2_tutor_btn.setText(k_str[11]);
			theStat=12;
		}
		else if (TimeUtils.timeSinceMillis(time1) > pausat && theStat == 12 && (q1_tutor_btn.isPressed() || q2_tutor_btn.isPressed())){
			time1 = TimeUtils.millis();
			q1_tutor_btn.setText(k_str[12]);
			q2_tutor_btn.setText(k_str[12]);
			theStat=13;
		}
		else if (TimeUtils.timeSinceMillis(time1) > pausat && theStat == 13 && (q1_tutor_btn.isPressed() || q2_tutor_btn.isPressed())){
			time1 = TimeUtils.millis();
			q1_tutor_btn.setText(k_str[13]);
			q2_tutor_btn.setText(k_str[13]);
			theStat=14;
		}
		else if (TimeUtils.timeSinceMillis(time1) > pausat && theStat == 14 && (q1_tutor_btn.isPressed() || q2_tutor_btn.isPressed())){
			time1 = TimeUtils.millis();
			q1_tutor_btn.setText(k_str[14]);
			q2_tutor_btn.setText(k_str[14]);
			theStat=15;
		}
		else if (TimeUtils.timeSinceMillis(time1) > pausat && theStat == 15 && (q1_tutor_btn.isPressed() || q2_tutor_btn.isPressed())){
			time1 = TimeUtils.millis();
			q1_tutor_btn.setVisible(false);
			q2_tutor_btn.setVisible(false);
			theStat=16;
		}

		if (kpk_on) for_kpk_start();
		else if (!kpk_on && !theQuest) the_main_game(delta);
		else if (theQuest) the_quest(delta);

		for2tut();

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
				if(for_end_game==1) game_forScreen.setScreen(new MenuScreen(game_forScreen));
				else if (for_end_game==2) game_forScreen.setScreen(new TheNextLevel(game_forScreen));
			}
		}
	}

	void for2tut(){
		if(theStat==2){
			cam.update();
			batch.setProjectionMatrix(cam.combined);
			batch.begin();
			{
				Radar();
				ax1=ax1+(30*Gdx.graphics.getDeltaTime());
				if(ax1>830) ax1=800;
				batch.draw(walls.findRegion("gogo"),ax1,600,150,120);
			}
			batch.end();
		}
		else if(theStat==4){
			cam.update();
			batch.setProjectionMatrix(cam.combined);
			batch.begin();
			{
				ax1=ax1+(30*Gdx.graphics.getDeltaTime());
				if(ax1>830) ax1=800;
				batch.draw(walls.findRegion("gogo"),ax1,450,150,120);
			}
			batch.end();
		}
		else if(theStat==5){
			cam.update();
			batch.setProjectionMatrix(cam.combined);
			batch.begin();
			{
				ax1=ax1+(30*Gdx.graphics.getDeltaTime());
				if(ax1>830) ax1=800;
				batch.draw(walls.findRegion("gogo"),ax1,600,150,120);
			}
			batch.end();
		}
		else if(theStat==6){
			cam.update();
			batch.setProjectionMatrix(cam.combined);
			batch.begin();
			{
				ax1=ax1+(30*Gdx.graphics.getDeltaTime());
				if(ax1>830) ax1=800;
				batch.draw(walls.findRegion("gogo"),ax1,150,150,120);
			}
			batch.end();
		}
	}


	void for_kpk_start(){

		WherePlayer();

		camHUD.update();
		batch.setProjectionMatrix(camHUD.combined);

		k_bg.setVisible(true);
		k_stage.act(Gdx.graphics.getDeltaTime());
		k_stage.draw();

		batch.begin();


		if (k_menu.isPressed()&& TimeUtils.timeSinceMillis(press_time) > 300){
			btn_b.play(game_forScreen.effectVol);
			on_menu = true;
			on_mssage = false;
			on_radar = false;
			gghh.on_radar=on_radar;
			k_up.setVisible(false);
			slider_effect.setVisible(false);
			slider_music.setVisible(false);
			music_k.setVisible(false);
			effect_k.setVisible(false);
			k_down.setVisible(true);
			k_msg.setPosition(camHUD.position.x - 400, camHUD.position.y);
			k_msg.setText(k_sms_menu);
			k_msg.setVisible(true);
			k_yes.setVisible(true);
			k_reset.setVisible(true);
			press_time = TimeUtils.millis();
		}
		else if (k_mssage.isPressed()&& TimeUtils.timeSinceMillis(press_time) > 300){
			btn_b.play(game_forScreen.effectVol);
			on_menu = false;
			on_mssage = true;
			on_radar = false;
			gghh.on_radar=on_radar;
			k_msg.setText(k_str[k_curent]);
			k_msg.setPosition(camHUD.position.x - 590, camHUD.position.y);
			k_up.setVisible(true);
			k_down.setVisible(false);
			if(k_curent==0) k_up.setVisible(false);
			if(k_curent==k_str.length-1) k_down.setVisible(false);
			k_msg.setVisible(true);
			k_yes.setVisible(false);
			slider_effect.setVisible(false);
			slider_music.setVisible(false);
			k_reset.setVisible(false);
			music_k.setVisible(false);
			effect_k.setVisible(false);
			press_time = TimeUtils.millis();
		}
		else if (k_radar.isPressed() && TimeUtils.timeSinceMillis(press_time) > 300){
			btn_b.play(game_forScreen.effectVol);
			on_menu = false;
			on_mssage = false;
			on_radar = true;
			gghh.on_radar=on_radar;
			k_up.setVisible(false);
			k_down.setVisible(false);
			k_msg.setVisible(false);
			k_yes.setVisible(false);
			slider_effect.setVisible(false);
			slider_music.setVisible(false);
			k_reset.setVisible(false);
			music_k.setVisible(false);
			effect_k.setVisible(false);
			float kkk=0;
			if (game_forScreen.curentLvl>=3 && game_forScreen.curentLvl<=5) {
				kkk = (hh+hh+1);
				gghh.zzz=668/kkk;
			}
			else {
				kkk = (ww+ww+1);
				gghh.zzz=850/kkk;
			}

			gghh.dx=53;
			gghh.dy=50;
			press_time = TimeUtils.millis();
		}
		else if (k_exit.isPressed() && TimeUtils.timeSinceMillis(press_time)>1000) {
			btn_b.play(game_forScreen.effectVol);
			Gdx.input.setInputProcessor(stage);
			on_menu = false;
			on_mssage = false;
			on_radar = false;
			gghh.on_radar=on_radar;
			k_up.setVisible(false);
			k_down.setVisible(false);
			k_msg.setVisible(false);
			k_yes.setVisible(false);
			slider_effect.setVisible(false);
			slider_music.setVisible(false);
			k_reset.setVisible(false);
			music_k.setVisible(false);
			effect_k.setVisible(false);
			kpk_on=false;
			press_time = TimeUtils.millis();
			if (the_first_start) {
				game_forScreen.curent_time=TimeUtils.millis();
				the_first_start=false;
			}
		}

		if (on_menu) {
			if (k_yes.isPressed() && TimeUtils.timeSinceMillis(press_time) > 300) {
				btn_b.play(game_forScreen.effectVol);
				//bg_music.stop();
				press_time = TimeUtils.millis()+10000;
				for_end_game=1;
				end_game=true;
			}
			else if(k_reset.isPressed() && TimeUtils.timeSinceMillis(press_time) > 300){
				btn_b.play(game_forScreen.effectVol);
				//bg_music.stop();
				game_forScreen.for_reset=true;
				for_end_game=2;
				end_game=true;
				press_time = TimeUtils.millis()+10000;
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
				//bg_music.setVolume(game_forScreen.musicVol);
				//if(game_forScreen.musicVol<=0f) bg_music.pause();
				//else if (game_forScreen.musicVol>0f) bg_music.play();
			}
			else if (slider_effect.isDragging()){
				game_forScreen.effectVol=slider_effect.getValue();
				btn_b.play(game_forScreen.effectVol);
			}
			else if ((k_exit.isPressed() || k_menu.isPressed() || k_mssage.isPressed() || k_radar.isPressed()) && TimeUtils.timeSinceMillis(press_time) > 300){
				game_forScreen.saveAll();
			}
		}
		else if (on_radar){
			for(x=0;x<ww+ww+1;x++)
				for(y=0;y<hh+hh+1;y++){
					if (lab[x][y]!=0 && lab[x][y]!=98 && lab[x][y]!=99) batch.draw(k_atl.findRegion("wall"), (x*gghh.zzz)+gghh.dx, (y*gghh.zzz)+gghh.dy,gghh.zzz,gghh.zzz);
					if (lab[x][y]==98) batch.draw(k_atl.findRegion("wall_s"), (x*gghh.zzz)+gghh.dx, (y*gghh.zzz)+gghh.dy,gghh.zzz,gghh.zzz);
					if (lab[x][y]==99) batch.draw(k_atl.findRegion("wall_f"), (x*gghh.zzz)+gghh.dx, (y*gghh.zzz)+gghh.dy,gghh.zzz,gghh.zzz);
					if (lab[x][y] == 25 || lab[x][y] == 26 || lab[x][y] == 27){
						batch.draw(k_atl.findRegion("wall_z"), (x * gghh.zzz) + gghh.dx, (y * gghh.zzz) + gghh.dy,
								gghh.zzz, gghh.zzz);
					}
				}
			batch.draw(k_atl.findRegion("wall_p"), (pl_x*gghh.zzz)+gghh.dx, (pl_y*gghh.zzz)+gghh.dy,gghh.zzz,gghh.zzz);
		}
		batch.end();

		k_bg.setVisible(false);
		if(theStat>=16){
			k_stage.act(Gdx.graphics.getDeltaTime());
			k_stage.draw();
		}
	}

	void the_main_game(float d){
		cam.update();
		batch.setProjectionMatrix(cam.combined);

		showGP();

		if (radarButton.isPressed() && !touchpad.isTouched() && touchpad.getKnobPercentX()==0 && touchpad.getKnobPercentY()==0 && !actionButton.isPressed() && !jumpButton.isPressed() && TimeUtils.timeSinceMillis(press_time) > 500) {
			kpk_on = true;
			on_radar = true;
			gghh.on_radar=on_radar;
			Gdx.input.setInputProcessor(multiplexer);
			press_time = TimeUtils.millis();
		}

		batch.begin();
		{
			ShowBackground();
			WherePlayer();
			PlayerLogic();
			if(game_forScreen.effectVol>0f) player.TheSound(d, robot_speed, game_forScreen.effectVol, robot_jump, robot_jump_temp);
			ShowM();
			Radar();
			player.UpdateCord();
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

	void the_quest(float delta){
		if(temp_number_qest==1){
			camHUD.update();
			batch.setProjectionMatrix(camHUD.combined);
			qStage1();
			q_stage1.act(delta);
			q_stage1.draw();
		}
		else if(temp_number_qest==2){
			camHUD.update();
			batch.setProjectionMatrix(camHUD.combined);
			qStage2();
			q_stage2.act(delta);
			q_stage2.draw();
			if(q2_ok){
				if(TimeUtils.timeSinceMillis(q_tim)>2000){
					q2_ok=false;
					q1_1=0;
					q_select_numb.setText(Integer.toString(q_chisla[q1_1]));
					q_znak.setText("");
					q_zag_numb.setText("?");
					Gdx.input.setInputProcessor(stage);
					theQuest=false;
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
					if(theStat==16) {lab=lab_o; theStat=17;}
				} else if (q_chisla[q1_1] != temp_q2.otv) {
					if (q_chisla[q1_1] > temp_q2.otv)
						q_znak.setText(">");
					else if (q_chisla[q1_1] < temp_q2.otv)
						q_znak.setText("<");
				}
			} else if (q_off2.isPressed() && TimeUtils.timeSinceMillis(q_tim) > 300) {
				if(theStat==16) {
					lab=lab_o;
					theStat=17;
					q_tim = TimeUtils.millis();
					q1_1 = 0;
					Gdx.input.setInputProcessor(stage);
					theQuest = false;
				}
				else if(theStat==17){
					q_tim = TimeUtils.millis();
					lab[temp_q2.x][temp_q2.y] = 22;
					q1_1 = 0;
					Gdx.input.setInputProcessor(stage);
					theQuest = false;
				}
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
			} else if (temp_q1.base.get(0)[q1_1] + temp_q1.base.get(1)[q1_2] != temp_q1.otv) {
				if(theStat==16) {lab=lab_o; theStat=17;}
				else if(theStat==17) lab[temp_q1.x][temp_q1.y] = 22;
			}
			q1_1 = 0;
			q1_2 = 0;
			Gdx.input.setInputProcessor(stage);
			theQuest = false;
		}

	}

	void Radar(){
		batch.draw(ui_atl.findRegion("r_ram"), cam.position.x+340, cam.position.y+184);
		int frx,fry;
		if(ww<=13 && hh<=8){

			frx=490-(((ww+ww+1)*10)/2);
			fry=284-(((hh+hh+1)*10)/2);
			for(x=0;x<ww+ww+1;x++)
				for(y=0;y<hh+hh+1;y++){
					if (lab[x][y]!=0 && lab[x][y]!=98 && lab[x][y]!=99) batch.draw(ui_atl.findRegion("wall"), (x*10)+cam.position.x+frx, (y*10)+cam.position.y+fry);
					if (lab[x][y]==98) batch.draw(ui_atl.findRegion("wall_s"), (x*10)+cam.position.x+frx, (y*10)+cam.position.y+fry);
					if (lab[x][y]==99) batch.draw(ui_atl.findRegion("wall_f"), (x*10)+cam.position.x+frx, (y*10)+cam.position.y+fry);
				}
			batch.draw(ui_atl.findRegion("where"), (pl_x*10)+cam.position.x+frx, (pl_y*10)+cam.position.y+fry);
		}
		if(ww>=14 && hh>=9){
			frx=355;
			fry=199;
			int nx=0,ny=0,ex=27,ey=17,dx=0,dy=0;
			if(pl_x>13) {nx=pl_x-14; ex=pl_x+13;}
			if(pl_y>8) {ny=pl_y-9; ey=pl_y+8;}
			if(pl_x>ww+ww-13) {nx=ww+ww-26; ex=ww+ww+1;}
			if(pl_y>hh+hh-8) {ny=hh+hh-16; ey=hh+hh+1;}

			for(dx=0;nx+dx<ex;dx++)
				for(dy=0;ny+dy<ey;dy++){
					if (lab[nx+dx][ny+dy]!=0 && lab[nx+dx][ny+dy]!=98 && lab[nx+dx][ny+dy]!=99) batch.draw(ui_atl.findRegion("wall"), (dx*10)+cam.position.x+frx, (dy*10)+cam.position.y+fry);
					if (lab[nx+dx][ny+dy]==98) batch.draw(ui_atl.findRegion("wall_s"), (dx*10)+cam.position.x+frx, (dy*10)+cam.position.y+fry);
					if (lab[nx+dx][ny+dy]==99) batch.draw(ui_atl.findRegion("wall_f"), (dx*10)+cam.position.x+frx, (dy*10)+cam.position.y+fry);
					if(game_forScreen.curentLvl==10 && nx+dx==flx && ny+dy==fly) batch.draw(ui_atl.findRegion("wall_f"), (dx*10)+cam.position.x+frx, (dy*10)+cam.position.y+fry);
				}
			batch.draw(ui_atl.findRegion("where"), ((pl_x-nx)*10)+cam.position.x+frx, ((pl_y-ny)*10)+cam.position.y+fry);
		}
	}


	void WherePlayer() {
		if (player.robotState == RobotState.UpLeftWall || player.robotState == RobotState.UpRightWall
				|| player.robotState == RobotState.DownLeftWall || player.robotState == RobotState.DownRightWall)
			;
		else
			pl_x = (int) player.pl_board[4].x / CellSize;
		pl_y = (int) player.pl_board[4].y / CellSize;

		if ((pl_y == hh + hh - 1 && pl_x == finish_lok) || (pl_x==flx && pl_y==fly) )
			actionButton.setVisible(true);
		else if (pl_y != hh + hh - 1 && pl_x != finish_lok)
			actionButton.setVisible(false);

		if (pl_y == hh + hh - 1 && pl_x == finish_lok && actionButton.isPressed() && TimeUtils.timeSinceMillis(time1) > 3000) {
			game_forScreen.curent_time=TimeUtils.timeSinceMillis(game_forScreen.curent_time);
			for_end_game=2;
			end_game=true;
			time1 = TimeUtils.millis();
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
				if (temp_number_qest == 1){ ttx = pl_x + 1; player.may_go_right=true;}
				else if (temp_number_qest == 2) {ttx = pl_x - 1; player.may_go_left=true;}
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

	void PlayerLogic(){

		if(player.robotState==RobotState.StandRight){
			ForStand();
			batch.draw(player.stand_r, player.p.x, player.p.y);

		}

		else if(player.robotState==RobotState.StandLeft){
			ForStand();
			batch.draw(player.stand_l, player.p.x, player.p.y);

		}

		else if(player.robotState==RobotState.GoRight){
			ForGo();
			if(touchpad.getKnobPercentX()>0.9f) batch.draw(player.run.getKeyFrame(player.anim_time), player.p.x, player.p.y);
			else batch.draw(player.go.getKeyFrame(player.anim_time), player.p.x, player.p.y);

		}

		else if(player.robotState==RobotState.GoLeft){
			ForGo();
			if(touchpad.getKnobPercentX()<-0.9f) batch.draw(player.run_back.getKeyFrame(player.anim_time), player.p.x, player.p.y);
			else batch.draw(player.go_back.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		}

		else if(player.robotState==RobotState.JumpStandRight){
			ForJump();
			batch.draw(player.jump_stand_r.getKeyFrame(player.anim_time), player.p.x, player.p.y);

		}

		else if(player.robotState==RobotState.JumpStandLeft){
			ForJump();
			batch.draw(player.jump_stand_l.getKeyFrame(player.anim_time), player.p.x, player.p.y);

		}

		else if(player.robotState==RobotState.JumpRight){
			ForJump();
			if(robot_speed==100) batch.draw(player.jump_go_r.getKeyFrame(player.anim_time), player.p.x, player.p.y);
			else if(robot_speed==260) batch.draw(player.jump_go_r.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		}

		else if(player.robotState==RobotState.JumpLeft){
			ForJump();
			if(robot_speed==100) batch.draw(player.jump_go_l.getKeyFrame(player.anim_time), player.p.x, player.p.y);
			else if(robot_speed==260) batch.draw(player.jump_go_l.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		}

		else if(player.robotState==RobotState.JumpLeftWallOnRight){
			ForJump();
			batch.draw(player.jump_l_on_r.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		}

		else if(player.robotState==RobotState.JumpRightWallOnLeft){
			ForJump();
			batch.draw(player.jump_r_on_l.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		}

		else if(player.robotState==RobotState.StandLeftWall){
			ForWall();
			batch.draw(player.stand_l_wall, player.p.x, player.p.y);
		}

		else if(player.robotState==RobotState.StandRightWall){
			ForWall();
			batch.draw(player.stand_r_wall, player.p.x, player.p.y);
		}

		else if(player.robotState==RobotState.GoLeftWallUp){
			ForWall();
			if(player.go_l_wall.getPlayMode()==PlayMode.LOOP_REVERSED) player.go_l_wall.setPlayMode(PlayMode.LOOP);
			batch.draw(player.go_l_wall.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		}

		else if(player.robotState==RobotState.GoLeftWallDown){
			ForWall();
			if(player.go_l_wall.getPlayMode()==PlayMode.LOOP) player.go_l_wall.setPlayMode(PlayMode.LOOP_REVERSED);
			batch.draw(player.go_l_wall.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		}

		else if(player.robotState==RobotState.GoRightWallUp){
			ForWall();
			if(player.go_r_wall.getPlayMode()==PlayMode.LOOP_REVERSED) player.go_r_wall.setPlayMode(PlayMode.LOOP);
			batch.draw(player.go_r_wall.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		}

		else if(player.robotState==RobotState.GoRightWallDown){
			ForWall();
			if(player.go_r_wall.getPlayMode()==PlayMode.LOOP) player.go_r_wall.setPlayMode(PlayMode.LOOP_REVERSED);
			batch.draw(player.go_r_wall.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		}

		// -----------------------Р В РІР‚в„ўР В РїС—Р…Р В Р Р‹Р В РїС—Р…Р В РЎС› Р В РЎСљР В РЎвЂ™ Р В РЎв„ўР В Р’В Р В РЎвЂ™Р В Р’В® Р В Р Р‹Р В РЎС›Р В РІР‚СћР В РЎСљР В Р’В« Р В РІР‚вЂќР В РЎвЂ™Р В РЎСџР В Р’В Р В Р’В«Р В РІР‚СљР В РїС—Р…Р В РІР‚в„ўР В РЎвЂ™Р В РІР‚СћР В РЎС› Р В РЎСљР В РЎвЂ™ Р В Р Р‹Р В РЎС›Р В РІР‚СћР В РЎСљР В Р в‚¬ Р В РїС—Р… Р В Р Р‹Р В РЎСџР В Р’В Р В Р’В«Р В РІР‚СљР В РїС—Р…Р В РІР‚в„ўР В РЎвЂ™Р В РІР‚СћР В РЎС› Р В Р Р‹ Р В РЎСљР В РІР‚СћР В РІР‚Сћ-----------------------

		else if(player.robotState==RobotState.OnLeftWall){
			ForWall();
			batch.draw(player.on_l_wall.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		}
		else if(player.robotState==RobotState.OnRightWall){
			ForWall();
			batch.draw(player.on_r_wall.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		}
		else if(player.robotState==RobotState.OffLeftWall){
			ForJump();
			batch.draw(player.off_l_wall.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		}
		else if(player.robotState==RobotState.OffRightWall){
			ForJump();
			batch.draw(player.off_r_wall.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		}
		else if(player.robotState==RobotState.StandOnLeftWall){
			ForWall();
			batch.draw(player.off_l_wall.getKeyFrame(0.2f), player.p.x, player.p.y);
		}
		else if(player.robotState==RobotState.StandOnRightWall){
			ForWall();
			batch.draw(player.off_r_wall.getKeyFrame(0.2f), player.p.x, player.p.y);
		}

		// -----------------Р В РІР‚вЂќР В РЎвЂ™Р В РІР‚С”Р В РІР‚СћР В РІР‚вЂќР В РЎвЂ™Р В РІР‚СћР В РЎС› Р В РїС—Р… Р В РІР‚в„ўР В Р’В«Р В РІР‚С”Р В РІР‚СћР В РІР‚вЂќР В РЎвЂ™Р В РІР‚СћР В РЎС› Р В Р Р‹Р В РЎвЂє Р В Р Р‹Р В РЎС›Р В РІР‚СћР В РЎСљР В Р’В«----------------------------

		else if(player.robotState==RobotState.UpLeftWall){
			if(player.up_l_wall.getPlayMode()==PlayMode.REVERSED) player.up_l_wall.setPlayMode(PlayMode.NORMAL);
			ForWall();
			batch.draw(player.up_l_wall.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		}
		else if(player.robotState==RobotState.UpRightWall){
			if(player.up_r_wall.getPlayMode()==PlayMode.REVERSED) player.up_r_wall.setPlayMode(PlayMode.NORMAL);
			ForWall();
			batch.draw(player.up_r_wall.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		}
		else if(player.robotState==RobotState.DownLeftWall){
			if(player.up_l_wall.getPlayMode()==PlayMode.NORMAL) player.up_l_wall.setPlayMode(PlayMode.REVERSED);
			ForWall();
			batch.draw(player.up_l_wall.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		}
		else if(player.robotState==RobotState.DownRightWall){
			if(player.up_r_wall.getPlayMode()==PlayMode.NORMAL) player.up_r_wall.setPlayMode(PlayMode.REVERSED);
			ForWall();
			batch.draw(player.up_r_wall.getKeyFrame(player.anim_time), player.p.x, player.p.y);
		}
	}

	void ForStand(){
		player.anim_time = 0;
		if(sel.isChecked()){

			if(jumpButton.isPressed()){
				if (!player.in_air && player.robotState==RobotState.StandRight) player.robotState=RobotState.JumpStandRight;
				else if (!player.in_air && player.robotState==RobotState.StandLeft) player.robotState=RobotState.JumpStandLeft;
			}

			else if (touchpad.getKnobPercentX()>0.1f && player.may_go_right && !player.in_air) player.robotState=RobotState.GoRight;
			else if (touchpad.getKnobPercentX()<-0.1f && player.may_go_left && !player.in_air) player.robotState=RobotState.GoLeft;
		}
	}

	void ForGo(){
		player.anim_time = player.anim_time + Gdx.graphics.getDeltaTime();
		if(sel.isChecked()){

			if (touchpad.getKnobPercentX()<-0.9f || touchpad.getKnobPercentX()>0.9f) robot_speed=260;
			else robot_speed=100;

			if(touchpad.getKnobPercentX()>0.1f){
				if((lab[pl_x+1][pl_y]==22 || lab[pl_x+1][pl_y]==25 || lab[pl_x+1][pl_y]==26 || lab[pl_x+1][pl_y]==27 || lab[pl_x+1][pl_y]==17) && (player.pl_board[2].x>((pl_x+1)*CellSize))) {
					player.may_go_left=true;
					player.may_go_right=false;
					if((lab[pl_x+1][pl_y+1]==22 || lab[pl_x+1][pl_y+1]==52 || lab[pl_x+1][pl_y+1]==17 || lab[pl_x+1][pl_y+1]==63 || lab[pl_x+1][pl_y+1]==31)
							&& (lab[pl_x][pl_y-1]!=0)
							){
						player.robotState=RobotState.StandRightWall;
						player.may_go_left=true;
						player.may_go_right=false;
						robot_speed=150;
						player.p.y=player.p.y+10;
						player.p.x=((pl_x+1)*CellSize)-80;
						player.anim_time = 0;
					}
				}
				else if(lab[pl_x][pl_y-1]==0 && lab[pl_x][pl_y]==0){
					if(player.pl_board[0].x>(pl_x)*CellSize && player.pl_board[2].x<(pl_x+1)*CellSize){
						player.robotState=RobotState.JumpStandRight;
						robot_jump_temp=-1;
						player.anim_time=1.30f;
						player.touch_up_wall=true;
						player.in_air=true;
					}
					else if(player.pl_board[4].x>(pl_x)*CellSize && touchpad.getKnobPercentX()>-0.1f && touchpad.getKnobPercentY()<-0.5f){
						player.anim_time=0;
						player.in_air=true;
						player.p.x=((pl_x)*CellSize)-64;
						player.robotState=RobotState.DownLeftWall;
					}
				}
			}
			else if(touchpad.getKnobPercentX()<-0.1f){
				if ((lab[pl_x-1][pl_y]==22 || lab[pl_x-1][pl_y]==25 || lab[pl_x-1][pl_y]==26 || lab[pl_x-1][pl_y]==27 || lab[pl_x-1][pl_y]==15) && (player.pl_board[0].x<((pl_x)*CellSize))) {
					player.may_go_left=false;
					player.may_go_right=true;
					if((lab[pl_x-1][pl_y+1]==22 || lab[pl_x-1][pl_y+1]==52 || lab[pl_x-1][pl_y+1]==33 || lab[pl_x-1][pl_y+1]==62 || lab[pl_x-1][pl_y+1]==15)
							&& (lab[pl_x][pl_y-1]!=0)
							){
						player.robotState=RobotState.StandLeftWall;
						player.may_go_left=false;
						player.may_go_right=true;
						robot_speed=150;
						player.p.y=player.p.y+10;
						player.p.x=(pl_x*CellSize)-20;
						player.anim_time = 0;
					}
				}
				else if(lab[pl_x][pl_y-1]==0 && lab[pl_x][pl_y]==0){
					if(player.pl_board[2].x<(pl_x+1)*CellSize && player.pl_board[0].x>(pl_x)*CellSize){
						player.robotState=RobotState.JumpStandLeft;
						robot_jump_temp=-1;
						player.anim_time=1.30f;
						player.touch_up_wall=true;
						player.in_air=true;
					}
					else if(player.pl_board[4].x<(pl_x+1)*CellSize && touchpad.getKnobPercentX()<-0.1f && touchpad.getKnobPercentY()<-0.5f){
						player.anim_time=0;
						player.in_air=true;
						player.p.x=((pl_x+1)*CellSize)-35;
						player.robotState=RobotState.DownRightWall;
					}
				}
			}

			if((touchpad.getKnobPercentX()>0.1f || touchpad.getKnobPercentX()<-0.1f) &&
					( lab[pl_x][pl_y]==0 && player.pl_board[2].x<((pl_x+1)*CellSize) && player.pl_board[0].x>((pl_x)*CellSize)) ){
				player.may_go_left=true; player.may_go_right=true;
			}

			if(jumpButton.isPressed()){
				if (touchpad.getKnobPercentX()>0.1f && !player.in_air) {player.anim_time=0; player.robotState=RobotState.JumpRight;}
				else if (touchpad.getKnobPercentX()<-0.1f && !player.in_air) {player.anim_time=0; player.robotState=RobotState.JumpLeft;}
			}
			else if (touchpad.getKnobPercentX()>0.1f && player.may_go_right && !player.in_air) {player.robotState=RobotState.GoRight; player.p.x=player.p.x+(robot_speed*Gdx.graphics.getDeltaTime());}
			else if (touchpad.getKnobPercentX()<-0.1f && player.may_go_left && !player.in_air) {player.robotState=RobotState.GoLeft; player.p.x=player.p.x-(robot_speed*Gdx.graphics.getDeltaTime());}
			else if (touchpad.getKnobPercentX()>-0.1f && touchpad.getKnobPercentX()<0.1f){
				if(player.robotState==RobotState.GoRight) player.robotState=RobotState.StandRight;
				else if (player.robotState==RobotState.GoLeft) player.robotState=RobotState.StandLeft;
			}
			if(player.in_air) player.in_air=false;
			if(player.robotState==RobotState.StandLeftWall || player.robotState==RobotState.StandRightWall) {player.may_go_left=true; player.may_go_right=true;}
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
					if(robot_jump_temp<=-12f) player.anim_time = 0.3f;
					if(robot_jump_temp<=-24f) player.anim_time = 0;
				} else if (player.robotState == RobotState.JumpLeft || player.robotState == RobotState.JumpStandLeft
						|| player.robotState == RobotState.JumpRightWallOnLeft) {
					player.robotState = RobotState.StandLeft;
					player.anim_time = 0;
					if(robot_jump_temp<=-6f){
						player.robotState = RobotState.FallLeft;
						player.anim_time = 0.5f;
					}
					if(robot_jump_temp<=-12f) player.anim_time = 0.3f;
					if(robot_jump_temp<=-24f) player.anim_time = 0;
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


	void ForWall(){
		if(sel.isChecked()){

			if(player.robotState==RobotState.OnLeftWall || player.robotState==RobotState.OnRightWall){
				player.anim_time = player.anim_time + Gdx.graphics.getDeltaTime();
				if(player.anim_time>=0.4f && player.anim_time<=0.7f){
					player.p.y=player.p.y+(450*Gdx.graphics.getDeltaTime());
				}
				if (player.anim_time>=0.75f) {
					if (player.robotState==RobotState.OnLeftWall) {player.anim_time=0; player.p.x=((pl_x)*CellSize)-20; player.robotState=RobotState.StandLeftWall;}
					else if (player.robotState==RobotState.OnRightWall) {player.anim_time=0; player.p.x=((pl_x)*CellSize)+120; player.robotState=RobotState.StandRightWall;}
					robot_speed=150;
				}
			}
			else if(player.robotState==RobotState.UpLeftWall || player.robotState==RobotState.UpRightWall){
				player.anim_time = player.anim_time + Gdx.graphics.getDeltaTime();
				if(player.anim_time>=0.1f && player.anim_time<=0.25f){
					player.p.y=player.p.y+(80*Gdx.graphics.getDeltaTime());
				}
				else if(player.anim_time>=0.25f && player.anim_time<=0.35f){
					player.p.y=player.p.y+(40*Gdx.graphics.getDeltaTime());
				}
				else if(player.anim_time>=0.40f && player.anim_time<=0.6f){
					player.p.y=player.p.y+(80*Gdx.graphics.getDeltaTime());
				}
				else if(player.anim_time>=0.6f && player.anim_time<=0.65f){
					player.p.y=player.p.y+(280*Gdx.graphics.getDeltaTime());
				}
				else if(player.anim_time>=0.65f && player.anim_time<=0.7f){
					player.p.y=player.p.y+(70*Gdx.graphics.getDeltaTime());
				}
				else if(player.anim_time>=0.7f && player.anim_time<=0.75f){
					player.p.y=player.p.y+(220*Gdx.graphics.getDeltaTime());
				}
				else if(player.anim_time>=0.75f && player.anim_time<=0.8f){
					player.p.y=player.p.y+(80*Gdx.graphics.getDeltaTime());
				}
				else if(player.anim_time>=0.8f && player.anim_time<=0.85f){
					player.p.y=player.p.y+(20*Gdx.graphics.getDeltaTime());
				}
				else if(player.anim_time>=1.05f && player.anim_time<=1.2f){
					player.p.y=player.p.y+(100*Gdx.graphics.getDeltaTime());
				}
				else if(player.anim_time>=1.2f && player.anim_time<=1.3f){
					player.p.y=player.p.y+(180*Gdx.graphics.getDeltaTime());
				}
				else if(player.anim_time>=1.3f && player.anim_time<=1.35f){
					player.p.y=player.p.y+(200*Gdx.graphics.getDeltaTime());
				}
				else if(player.anim_time>=1.35f && player.anim_time<=1.4f){
					player.p.y=player.p.y+(400*Gdx.graphics.getDeltaTime());
				}
				else if(player.anim_time>=1.4f && player.anim_time<=1.45f){
					player.p.y=player.p.y+(300*Gdx.graphics.getDeltaTime());
				}
				else if(player.anim_time>=1.45f && player.anim_time<=1.6f){
					player.p.y=(pl_y)*CellSize;
				}
				if(player.anim_time>=0.8f && player.anim_time<=0.9f){
					if (player.robotState==RobotState.UpLeftWall) player.p.x=player.p.x-(280*Gdx.graphics.getDeltaTime());
					else if (player.robotState==RobotState.UpRightWall) player.p.x=player.p.x+(280*Gdx.graphics.getDeltaTime());
				}
				else if(player.anim_time>=0.9f && player.anim_time<=1.15f){
					if (player.robotState==RobotState.UpLeftWall) player.p.x=player.p.x-(50*Gdx.graphics.getDeltaTime());
					else if (player.robotState==RobotState.UpRightWall) player.p.x=player.p.x+(50*Gdx.graphics.getDeltaTime());
				}
				if (player.anim_time>=1.65f) {
					if (player.robotState==RobotState.UpLeftWall) { player.robotState=RobotState.StandLeft;}
					else if (player.robotState==RobotState.UpRightWall) { player.robotState=RobotState.StandRight;}
					robot_speed=100;
				}
			}

			else if(player.robotState==RobotState.DownLeftWall || player.robotState==RobotState.DownRightWall){
				player.anim_time = player.anim_time + Gdx.graphics.getDeltaTime();
				if(player.anim_time>=1.4f && player.anim_time<=1.55f){
					player.p.y=player.p.y-(80*Gdx.graphics.getDeltaTime());
				}
				else if(player.anim_time>=1.3f && player.anim_time<=1.4f){
					player.p.y=player.p.y-(40*Gdx.graphics.getDeltaTime());
				}
				else if(player.anim_time>=1.05f && player.anim_time<=1.25f){
					player.p.y=player.p.y-(80*Gdx.graphics.getDeltaTime());
				}
				else if(player.anim_time>=1f && player.anim_time<=1.05f){
					player.p.y=player.p.y-(280*Gdx.graphics.getDeltaTime());
				}
				else if(player.anim_time>=0.95f && player.anim_time<=1f){
					player.p.y=player.p.y-(70*Gdx.graphics.getDeltaTime());
				}
				else if(player.anim_time>=0.9f && player.anim_time<=0.95f){
					player.p.y=player.p.y-(220*Gdx.graphics.getDeltaTime());
				}
				else if(player.anim_time>=0.85f && player.anim_time<=0.9f){
					player.p.y=player.p.y-(80*Gdx.graphics.getDeltaTime());
				}
				else if(player.anim_time>=0.8f && player.anim_time<=0.85f){
					player.p.y=player.p.y-(20*Gdx.graphics.getDeltaTime());
				}
				else if(player.anim_time>=0.45f && player.anim_time<=0.6f){
					player.p.y=player.p.y-(100*Gdx.graphics.getDeltaTime());
				}
				else if(player.anim_time>=0.35f && player.anim_time<=0.45f){
					player.p.y=player.p.y-(180*Gdx.graphics.getDeltaTime());
				}
				else if(player.anim_time>=0.3f && player.anim_time<=0.35f){
					player.p.y=player.p.y-(200*Gdx.graphics.getDeltaTime());
				}
				else if(player.anim_time>=0.25f && player.anim_time<=0.3f){
					player.p.y=player.p.y-(400*Gdx.graphics.getDeltaTime());
				}
				else if(player.anim_time>=0.2f && player.anim_time<=0.25f){
					player.p.y=player.p.y-(700*Gdx.graphics.getDeltaTime());
				}
				else if(player.anim_time>=0.05f && player.anim_time<=0.2f){
					player.p.y=(pl_y)*CellSize;
				}
				if(player.anim_time>=0.75f && player.anim_time<=0.85f){
					if (player.robotState==RobotState.DownLeftWall) player.p.x=player.p.x+(280*Gdx.graphics.getDeltaTime());
					else if (player.robotState==RobotState.DownRightWall) player.p.x=player.p.x-(300*Gdx.graphics.getDeltaTime());
				}
				else if(player.anim_time>=0.5f && player.anim_time<=0.75f){
					if (player.robotState==RobotState.DownLeftWall) player.p.x=player.p.x+(50*Gdx.graphics.getDeltaTime());
					else if (player.robotState==RobotState.DownRightWall) player.p.x=player.p.x-(50*Gdx.graphics.getDeltaTime());
				}
				if (player.anim_time>=1.55f) {
					robot_speed=150;
					player.anim_time = 0;
					player.p.y=player.p.y-20;
					if (player.robotState==RobotState.DownLeftWall) {
						player.robotState=RobotState.StandLeftWall;
						player.p.x=((pl_x)*CellSize)-20;
					}
					else if (player.robotState==RobotState.DownRightWall) {
						player.robotState=RobotState.StandRightWall;
						player.p.x=((pl_x+1)*CellSize)-80;
					}
				}
			}
			else if(player.robotState==RobotState.StandOnLeftWall || player.robotState==RobotState.StandOnRightWall){
				if(player.robotState==RobotState.StandOnLeftWall){
					if(touchpad.getKnobPercentY()>0.5f) player.robotState=RobotState.OnLeftWall;
					else if(touchpad.getKnobPercentY()<-0.5f) player.robotState=RobotState.OffLeftWall;
				}
				else if(player.robotState==RobotState.StandOnRightWall){
					if(touchpad.getKnobPercentY()>0.5f) player.robotState=RobotState.OnRightWall;
					else if(touchpad.getKnobPercentY()<-0.5f) player.robotState=RobotState.OffRightWall;
				}
				if(player.robotState==RobotState.OffLeftWall || player.robotState==RobotState.OffRightWall){
					player.anim_time = 0;
					robot_jump_temp=-1;
				}
			}

			if(jumpButton.isPressed()){
				if(touchpad.getKnobPercentX()<-0.8f && player.robotState==RobotState.StandRightWall) player.robotState=RobotState.JumpRightWallOnLeft;
				else if(touchpad.getKnobPercentX()>0.8f && player.robotState==RobotState.StandLeftWall) player.robotState=RobotState.JumpLeftWallOnRight;
				else if((touchpad.getKnobPercentX()<0.1f && touchpad.getKnobPercentX()>-0.1f)&& player.robotState==RobotState.StandRightWall) {
					player.robotState=RobotState.JumpStandRight;
					player.anim_time=0.85f;
					robot_jump_temp=-1;
					player.touch_up_wall=true;
					player.p.x=player.p.x-20;
					robot_speed=260;
				}
				else if((touchpad.getKnobPercentX()<0.1f && touchpad.getKnobPercentX()>-0.1f) && player.robotState==RobotState.StandLeftWall) {
					player.robotState=RobotState.JumpStandLeft;
					player.anim_time=0.85f;
					robot_jump_temp=-1;
					player.touch_up_wall=true;
					player.p.x=player.p.x+20;
					robot_speed=260;
				}
			}
			if ((player.robotState==RobotState.StandRightWall || player.robotState==RobotState.GoRightWallDown)&& touchpad.getKnobPercentY()>0.5f) player.robotState=RobotState.GoRightWallUp;
			else if ((player.robotState==RobotState.StandRightWall || player.robotState==RobotState.GoRightWallUp) && touchpad.getKnobPercentY()<-0.5f) player.robotState=RobotState.GoRightWallDown;
			else if ((player.robotState==RobotState.StandLeftWall || player.robotState==RobotState.GoLeftWallDown) && touchpad.getKnobPercentY()>0.5f) player.robotState=RobotState.GoLeftWallUp;
			else if ((player.robotState==RobotState.StandLeftWall || player.robotState==RobotState.GoLeftWallUp) && touchpad.getKnobPercentY()<-0.5f) player.robotState=RobotState.GoLeftWallDown;
			else if (touchpad.getKnobPercentY()>-0.1f && touchpad.getKnobPercentY()<0.1f && (player.robotState==RobotState.GoRightWallUp || player.robotState==RobotState.GoRightWallDown)) {player.robotState=RobotState.StandRightWall; player.anim_time = 0;}
			else if (touchpad.getKnobPercentY()>-0.1f && touchpad.getKnobPercentY()<0.1f && (player.robotState==RobotState.GoLeftWallUp || player.robotState==RobotState.GoLeftWallDown)) {player.robotState=RobotState.StandLeftWall; player.anim_time = 0;}
			else if ((player.robotState==RobotState.GoRightWallUp || player.robotState==RobotState.GoLeftWallUp)) {
				player.anim_time = player.anim_time + Gdx.graphics.getDeltaTime();
				if((lab[pl_x][pl_y+1]==21 || lab[pl_x][pl_y+1]==10) && player.pl_board[3].y>( (pl_y+1)*CellSize )-5) player.p.y=player.p.y;
				else player.p.y=player.p.y+(robot_speed*Gdx.graphics.getDeltaTime());
				if( (lab[pl_x-1][pl_y+1]==0 || lab[pl_x-1][pl_y+1]==99) && ( (player.pl_board[1].y>( (pl_y+1)*CellSize ) ) && player.robotState==RobotState.GoLeftWallUp) ){
					player.robotState=RobotState.UpLeftWall;
					player.anim_time = 0;
				}
				else if ( (lab[pl_x+1][pl_y+1]==0 || lab[pl_x+1][pl_y+1]==99) && ( (player.pl_board[3].y>((pl_y+1)*CellSize)) && player.robotState==RobotState.GoRightWallUp) ){
					player.robotState=RobotState.UpRightWall;
					player.anim_time = 0;
				}
			}
			else if (player.robotState==RobotState.GoRightWallDown || player.robotState==RobotState.GoLeftWallDown) {
				player.anim_time = player.anim_time + Gdx.graphics.getDeltaTime();
				if((lab[pl_x][pl_y-1]==21 || lab[pl_x][pl_y-1]==16) && player.pl_board[2].y<( (pl_y)*CellSize )+5) {
					if (player.robotState==RobotState.GoRightWallDown) {player.robotState=RobotState.StandRight; player.p.x=player.p.x-20;}
					else if (player.robotState==RobotState.GoLeftWallDown) {player.robotState=RobotState.StandLeft; player.p.x=player.p.x+20;}
					player.p.y=(pl_y)*CellSize;
				}
				else if(player.robotState==RobotState.GoRightWallDown && (lab[pl_x+1][pl_y]==0 || lab[pl_x+1][pl_y]==98) && (player.pl_board[3].y<((pl_y+1)*CellSize)+30)){
					player.anim_time=0;
					robot_jump_temp=-1;
					player.robotState=RobotState.StandOnRightWall;
				}
				else if(player.robotState==RobotState.GoLeftWallDown && (lab[pl_x-1][pl_y]==0 || lab[pl_x-1][pl_y]==98) && (player.pl_board[1].y<((pl_y+1)*CellSize)+30)){
					player.anim_time=0;
					robot_jump_temp=-1;
					player.robotState=RobotState.StandOnLeftWall;
				}
				else player.p.y=player.p.y-(robot_speed*Gdx.graphics.getDeltaTime());
			}
		}

	}

	void showGP() {
		if(sel.isChecked()) {
			sel.setText("Robot");
			if(cam.position.x<maxXrange || cam.position.x-635>0 || cam.position.y<maxYrange || cam.position.y-380>0){
				if (player.p.x<cam.position.x-100) cam.position.x=cam.position.x+((player.p.x-(cam.position.x-100))*0.06f);
				if (player.p.x>cam.position.x+50) cam.position.x=cam.position.x+((player.p.x-(cam.position.x+50))*0.06f);
				if (player.p.y<cam.position.y-50) cam.position.y=cam.position.y+((player.p.y-(cam.position.y-50))*0.06f);
				if (player.p.y>cam.position.y+10) cam.position.y=cam.position.y+((player.p.y-(cam.position.y+10))*0.06f);
			}
			if(cam.position.x>maxXrange) cam.position.x=CellSize*(ww+ww+1)-641;
			if(cam.position.x<minXrange) cam.position.x=641;
			if(cam.position.y>maxYrange) cam.position.y=CellSize*(hh+hh+1)-385;
			if(cam.position.y<minYrange) cam.position.y=385;
		}
		else {
			if(cam.position.x>maxXrange) cam.position.x=CellSize*(ww+ww+1)-643;
			if(cam.position.x<minXrange) cam.position.x=643;
			if(cam.position.y>maxYrange) cam.position.y=CellSize*(hh+hh+1)-387;
			if(cam.position.y<minYrange) cam.position.y=387;
			if(cam.position.x<maxXrange || cam.position.x-640>0 || cam.position.y<maxYrange || cam.position.y-384>0){
				cam.position.x=cam.position.x+touchpad.getKnobPercentX()*speed_cam;
				cam.position.y=cam.position.y+touchpad.getKnobPercentY()*speed_cam;
			}
			sel.setText("Camera");
		}
	}

	private void ShowBackground() {

		//batch.draw(bbb, 0, 0,3000,3000);
		for (x = 0; x < ww + ww + 1; x++)
			for (y = 0; y < hh + hh + 1; y++) {
				if (lab[x][y] == 0 || lab[x][y] == 25 || lab[x][y] == 26 || lab[x][y] == 27)
					batch.draw(walls.findRegion("fone_labyrint"), x * CellSize, y * CellSize);
				if (lab[x][y] == 98)
					batch.draw(walls.findRegion("finish"), x * CellSize, y * CellSize);
				if (lab[x][y] == 99)
					batch.draw(walls.findRegion("start"), x * CellSize, y * CellSize);
			}
		if(game_forScreen.curentLvl==10) batch.draw(walls.findRegion("finish"), flx * CellSize, fly * CellSize);
	}

	void ShowM(){

		for (x = 0; x < ww + ww + 1; x++) {
			for (y = 0; y < hh + hh + 1; y++) {
				if (lab[x][y] == 4)
					batch.draw(walls.findRegion("4"), x * CellSize, y * CellSize);
				else if (lab[x][y] == 31)
					batch.draw(walls.findRegion("31"), x * CellSize, y * CellSize);
				else if (lab[x][y] == 32)
					batch.draw(walls.findRegion("34"), x * CellSize, y * CellSize);
				else if (lab[x][y] == 33)
					batch.draw(walls.findRegion("33"), x * CellSize, y * CellSize);
				else if (lab[x][y] == 34)
					batch.draw(walls.findRegion("32"), x * CellSize, y * CellSize);
				else if (lab[x][y] == 51)
					batch.draw(walls.findRegion("51"), x * CellSize, y * CellSize);
				else if (lab[x][y] == 52)
					batch.draw(walls.findRegion("54"), x * CellSize, y * CellSize);
				else if (lab[x][y] == 53)
					batch.draw(walls.findRegion("53"), x * CellSize, y * CellSize);
				else if (lab[x][y] == 54)
					batch.draw(walls.findRegion("52"), x * CellSize, y * CellSize);
				else if (lab[x][y] == 61)
					batch.draw(walls.findRegion("62"), x * CellSize, y * CellSize);
				else if (lab[x][y] == 62)
					batch.draw(walls.findRegion("61"), x * CellSize, y * CellSize);
				else if (lab[x][y] == 63)
					batch.draw(walls.findRegion("64"), x * CellSize, y * CellSize);
				else if (lab[x][y] == 64)
					batch.draw(walls.findRegion("63"), x * CellSize, y * CellSize);
				else if (lab[x][y] == 21)
					batch.draw(walls.findRegion("21"), x * CellSize, y * CellSize);
				else if (lab[x][y] == 22)
					batch.draw(walls.findRegion("22"), x * CellSize, y * CellSize);

				else if (lab[x][y] == 27)
					batch.draw(walls.findRegion("27"), x * CellSize, y * CellSize);
				else if (lab[x][y] == 26)
					batch.draw(walls.findRegion("26"), x * CellSize, y * CellSize);
				else if (lab[x][y] == 25)
					batch.draw(walls.findRegion("25"), x * CellSize, y * CellSize);

				else if (lab[x][y] == 71)
					batch.draw(walls.findRegion("73"), x * CellSize, y * CellSize);
				else if (lab[x][y] == 72)
					batch.draw(walls.findRegion("72"), x * CellSize, y * CellSize);
				else if (lab[x][y] == 73)
					batch.draw(walls.findRegion("71"), x * CellSize, y * CellSize);
				else if (lab[x][y] == 74)
					batch.draw(walls.findRegion("74"), x * CellSize, y * CellSize);
				else if (lab[x][y] == 11)
					batch.draw(walls.findRegion("12"), x * CellSize, y * CellSize);
				else if (lab[x][y] == 12)
					batch.draw(walls.findRegion("11"), x * CellSize, y * CellSize);
				else if (lab[x][y] == 13)
					batch.draw(walls.findRegion("14"), x * CellSize, y * CellSize);
				else if (lab[x][y] == 14)
					batch.draw(walls.findRegion("13"), x * CellSize, y * CellSize);
				else if (lab[x][y] == 10)
					batch.draw(walls.findRegion("16"), x * CellSize, y * CellSize);
				else if (lab[x][y] == 15)
					batch.draw(walls.findRegion("15"), x * CellSize, y * CellSize);
				else if (lab[x][y] == 16)
					batch.draw(walls.findRegion("10"), x * CellSize, y * CellSize);
				else if (lab[x][y] == 17)
					batch.draw(walls.findRegion("17"), x * CellSize, y * CellSize);
			}
		}
	}


	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void dispose(){

		batch.dispose();
		walls.dispose();
		ui_atl.dispose();
		stage.dispose();
		gfont.dispose();
		q_atl.dispose();
		q_stage1.dispose();
		q_stage2.dispose();
	}

}
