package com.qvardium.game.project.arwii.one;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.qvardium.game.project.arwii.one.ByPlayer.RobotState;

public class Last_lvl extends ScreenAdapter{

	MyGame game_forScreen;
	SpriteBatch batch;
	OrthographicCamera cam;
	OrthographicCamera camHUD;
	Viewport viewport;
	Viewport viewportHUD;

	// --------для лабиринта----------------

	int x, y;
	int ww, hh;
	int CellSize = 200;
	byte[][] lab;
	int finish_lok;

	TextureAtlas walls;
	Texture bg_fon_planet,bg_fon_lab, the_komp;

	float ddx=0;
	// -------------------------------------

	// --------для игрока-------------------

	ByPlayer player;
	int pl_x, pl_y, i;
	int robot_speed = 100;
	float robot_jump = 5, robot_jump_temp = 5;
	float raz_xcam;

	// -------------------------------------

	// --------для интерфейса---------------
	TextureAtlas ui_atl;

	Stage stage;
	Touchpad touchpad;
	TouchpadStyle touchpadStyle;
	float maxXrange, maxYrange, minXrange, minYrange, speed_cam = 20;
	float xrang = 6.3f, yrang = 3.8f;
	float maxXr1, maxYr1;

	ImageButton jumpButton, actionButton, radarButton;

	BitmapFont gfont;

	Label informFPS;
	String g_dif;

	boolean radarIsOn;

	Sprite bg_black;
	float bg_black_a;
	boolean end_game=false;
	byte for_end_game=0;

	// -------------------------------------
	// ----ДЛЯ КПК-------------------------
	boolean the_first_start = true;
	boolean kpk_on = true;
	boolean on_menu, on_mssage = true;

	Stage k_stage;
	TextureAtlas k_atl,q_atl;
	Image k_kpk, k_bg;
	ImageButton k_up, k_down, k_exit, k_menu, k_mssage, k_yes, k_reset;
	BitmapFont k_font;
	Label k_msg,k_msg_name;

	Slider slider_effect;
	Image effect_k;
	Sound btn_b;

	String[] k_str;
	String[] k_for_str;
	int k_curent = 0;
	String k_name;
	long press_time;
	// ------------------------------------
	private String k_sms_menu;
	private String game_sms_level;


	public Last_lvl(MyGame game){

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

		ww = 12;
		hh = 5;

		lab = new byte[ww][hh];

		walls = new TextureAtlas(Gdx.files.internal("g5/g5map.pack"));

		finish_lok = 8;
		lab[0][0]=12;
		lab[0][hh-1]=11;
		lab[ww-1][hh-1]=14;
		lab[ww-1][0]=13;
		for(int kk=1;kk<ww-1;kk++){
			lab[kk][hh-1]=10;
			lab[kk][0]=16;
		}
		for(int kk=1;kk<hh-1;kk++){
			lab[ww-1][kk]=17;
			lab[0][kk]=15;
		}

		bg_fon_planet = new Texture("g5/1.jpg");
		bg_fon_lab = new Texture("g5/2.png");
		the_komp = new Texture("g5/3.png");

		// -----------------------------------------------

		// -----------для интерфейса----------------------

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

		informFPS = new Label("FPS: " + Gdx.graphics.getFramesPerSecond(),
				new Label.LabelStyle(gfont, gfont.getColor()));

		stage = new Stage(viewportHUD, batch);
		stage.addActor(touchpad);
		stage.addActor(jumpButton);
		stage.addActor(actionButton);
		stage.addActor(radarButton);
		stage.addActor(informFPS);

		maxXrange = ((ww) * CellSize) - 640;
		maxYrange = ((hh) * CellSize) - 380;
		maxXr1 = 100;
		maxYr1 = 100;
		minXrange = 640;
		minYrange = 384;

		jumpButton.setPosition(camHUD.position.x + 500, camHUD.position.y - 210);
		actionButton.setPosition(camHUD.position.x + 350, camHUD.position.y - 210);
		informFPS.setPosition(camHUD.position.x - 630, camHUD.position.y + 130);
		radarButton.setPosition(camHUD.position.x + 340, camHUD.position.y + 184);

		radarIsOn = false;

		bg_black = new Sprite(new Texture(Gdx.files.internal("black.png")), 0, 0, 1280, 768);
		bg_black.setPosition(camHUD.position.x-640, camHUD.position.y-384);
		bg_black_a = 1f;
		// -----------------------------------------------

		// -------------для игрока-------------------------

		player = new ByPlayer(CellSize, 200, game_forScreen.curentLvl, game_forScreen.trueLvl);
		player.RobotMay(true, true, true, true, false, false, false);

		// ------------------------------------------------

		// -----ДЛЯ КПК----------------
		q_atl = new TextureAtlas("q_qest.pack");

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
		k_mssage = new ImageButton(new TextureRegionDrawable(k_atl.findRegion("r_mess")),
				new TextureRegionDrawable(k_atl.findRegion("r_mess_p")));

		k_yes = new ImageButton(new TextureRegionDrawable(q_atl.findRegion("yes")),
				new TextureRegionDrawable(q_atl.findRegion("yes_p")));
		k_reset = new ImageButton(new TextureRegionDrawable(k_atl.findRegion("reset")),
				new TextureRegionDrawable(k_atl.findRegion("reset_p")));

		slider_effect = new Slider(0f, 1f, 0.1f, true, new Slider.SliderStyle(new TextureRegionDrawable(k_atl.findRegion("slider_s")),new TextureRegionDrawable(k_atl.findRegion("slider_p"))));

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
		k_stage.addActor(k_mssage);
		k_stage.addActor(k_msg);
		k_stage.addActor(k_msg_name);
		k_stage.addActor(k_yes);
		k_stage.addActor(k_reset);
		k_stage.addActor(slider_effect);
		k_stage.addActor(effect_k);

		k_up.setPosition(camHUD.position.x - 240, camHUD.position.y + 280);
		k_down.setPosition(camHUD.position.x - 240, camHUD.position.y - 330);
		k_menu.setPosition(camHUD.position.x + 290, camHUD.position.y + 200);
		k_mssage.setPosition(camHUD.position.x + 290, camHUD.position.y + 50);
		k_exit.setPosition(camHUD.position.x + 290, camHUD.position.y - 250);
		k_msg.setPosition(camHUD.position.x - 590, camHUD.position.y);
		k_msg_name.setPosition(camHUD.position.x - 590, camHUD.position.y+250);
		k_yes.setPosition(camHUD.position.x - 500, camHUD.position.y +50);
		k_reset.setPosition(camHUD.position.x - 500, camHUD.position.y - 150);
		slider_effect.setPosition(camHUD.position.x - 320, camHUD.position.y - 200);
		slider_effect.setSize(50, 400);
		effect_k.setPosition(camHUD.position.x - 350, camHUD.position.y - 320);
		k_bg.setPosition(51, 50);
		k_yes.setVisible(false);
		k_reset.setVisible(false);
		slider_effect.setVisible(false);
		effect_k.setVisible(false);
		k_up.setVisible(false);


		for_str();

		// ----------------------------

		press_time = TimeUtils.millis();
		Gdx.input.setInputProcessor(k_stage);

		btn_b = Gdx.audio.newSound(Gdx.files.internal("sounds/clic.mp3"));

		game_forScreen.for_reset=false;

	}


	@Override
	public void render(float delta){
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
		else if (!kpk_on)
			the_main_game(delta);

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
			else if (for_end_game==2) game_forScreen.setScreen(new TheEndGame(game_forScreen));
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
			k_up.setVisible(false);
			slider_effect.setVisible(false);
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
			k_reset.setVisible(false);
			effect_k.setVisible(false);
			press_time = TimeUtils.millis();
		} else if (k_exit.isPressed() && TimeUtils.timeSinceMillis(press_time) > 1000) {
			btn_b.play(game_forScreen.effectVol);
			Gdx.input.setInputProcessor(stage);
			on_menu = false;
			on_mssage = false;
			k_msg_name.setVisible(false);
			k_up.setVisible(false);
			k_down.setVisible(false);
			k_msg.setVisible(false);
			k_yes.setVisible(false);
			slider_effect.setVisible(false);
			k_reset.setVisible(false);
			effect_k.setVisible(false);
			kpk_on = false;
			press_time = TimeUtils.millis();
			if (the_first_start) {
				game_forScreen.curent_time = TimeUtils.millis();
				the_first_start = false;
			}
		}

		if (on_menu) {
			if (k_yes.isPressed() && TimeUtils.timeSinceMillis(press_time) > 300) {
				btn_b.play(game_forScreen.effectVol);
				end_game=true;
				for_end_game=1;
				press_time = (TimeUtils.millis()+10000);
			}
			else if(k_reset.isPressed() && TimeUtils.timeSinceMillis(press_time) > 300){
				btn_b.play(game_forScreen.effectVol);
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
				effect_k.setVisible(true);
				slider_effect.setVisible(true);
				k_up.setVisible(true);
				slider_effect.setValue(game_forScreen.effectVol);
			}
			else if(k_up.isPressed() && TimeUtils.timeSinceMillis(press_time) > 300){
				btn_b.play(game_forScreen.effectVol);
				press_time = TimeUtils.millis();
				k_yes.setVisible(true);
				k_msg.setVisible(true);
				k_reset.setVisible(true);
				k_down.setVisible(true);
				effect_k.setVisible(false);
				slider_effect.setVisible(false);
				k_up.setVisible(false);
				game_forScreen.saveAll();
			}
			else if (slider_effect.isDragging()){
				game_forScreen.effectVol=slider_effect.getValue();
				btn_b.play(game_forScreen.effectVol);
			}
			else if ((k_exit.isPressed() || k_menu.isPressed() || k_mssage.isPressed()) && TimeUtils.timeSinceMillis(press_time) > 300){
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
			} else if (k_down.isPressed() && TimeUtils.timeSinceMillis(press_time) > 300) {
				btn_b.play(game_forScreen.effectVol);
				press_time = TimeUtils.millis();
				k_curent++;
				k_up.setVisible(true);
				if (k_curent == k_str.length - 1) {
					k_down.setVisible(false);
				}
				k_msg.setText(k_str[k_curent]);
			}
		}

		batch.end();

		k_bg.setVisible(false);
		k_stage.act(Gdx.graphics.getDeltaTime());
		k_stage.draw();

	}

	void the_main_game(float d) {
		cam.update();
		batch.setProjectionMatrix(cam.combined);

		showGP();

		if (radarButton.isPressed() && !touchpad.isTouched() && touchpad.getKnobPercentX()==0 && touchpad.getKnobPercentY()==0 && !actionButton.isPressed() && !jumpButton.isPressed() && TimeUtils.timeSinceMillis(press_time) > 500) {
			kpk_on = true;
			on_mssage = true;
			Gdx.input.setInputProcessor(k_stage);
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

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	void Radar() {
		batch.draw(ui_atl.findRegion("r_ram"), cam.position.x + 340, cam.position.y + 184);
		int frx, fry;
		if (ww <= 13 && hh <= 8) {

			frx = 490 - (((ww) * 10) / 2);
			fry = 284 - (((hh) * 10) / 2);
			for (x = 0; x < ww; x++)
				for (y = 0; y < hh; y++) {
					if (lab[x][y] != 0)
						batch.draw(ui_atl.findRegion("wall"), (x * 10) + cam.position.x + frx,
								(y * 10) + cam.position.y + fry);
				}
			batch.draw(ui_atl.findRegion("where"), (pl_x * 10) + cam.position.x + frx,
					(pl_y * 10) + cam.position.y + fry);
		}
	}

	void WherePlayer() {
		if (player.robotState == RobotState.UpLeftWall || player.robotState == RobotState.UpRightWall
				|| player.robotState == RobotState.DownLeftWall || player.robotState == RobotState.DownRightWall)
			;
		else
			pl_x = (int) player.pl_board[4].x / CellSize;
		pl_y = (int) player.pl_board[4].y / CellSize;

		if (pl_x == finish_lok)
			actionButton.setVisible(true);
		else if (pl_x != finish_lok)
			actionButton.setVisible(false);
		if (pl_y == 1 && pl_x == finish_lok && actionButton.isPressed()) {
			end_game=true;
			for_end_game=2;
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
			if(player.anim_time>=0.8f) player.anim_time=0;
		}

		else if (player.robotState == RobotState.FallRight) {
			player.anim_time+=Gdx.graphics.getDeltaTime();
			if(player.anim_time>=0.8f){
				player.anim_time=0.8f;
				player.robotState = RobotState.StandRight;
			}
			batch.draw(player.fall_r.getKeyFrame(player.anim_time), player.p.x, player.p.y);
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
	}

	void ForStand() {
		player.anim_time = 0;


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

	void ForGo() {
		player.anim_time = player.anim_time + Gdx.graphics.getDeltaTime();


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

	void ForJump() {

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

	void ForWall() {

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

	void showGP() {


		float kkk=cam.position.x;
		if (cam.position.x < maxXrange || cam.position.x - 635 > 0 || cam.position.y < maxYrange
				|| cam.position.y - 380 > 0) {
			if (player.p.x < cam.position.x - 100){
				cam.position.x = cam.position.x + ((player.p.x - (cam.position.x - 100)) * 0.06f);

			}
			if (player.p.x > cam.position.x + 50){
				cam.position.x = cam.position.x + ((player.p.x - (cam.position.x + 50)) * 0.06f);

			}
			if (player.p.y < cam.position.y - 50)
				cam.position.y = cam.position.y + ((player.p.y - (cam.position.y - 50)) * 0.06f);
			if (player.p.y > cam.position.y + 10)
				cam.position.y = cam.position.y + ((player.p.y - (cam.position.y + 10)) * 0.06f);
			raz_xcam=cam.position.x-kkk;
			if(cam.position.x>minXrange && cam.position.x<maxXrange && (raz_xcam>1 || raz_xcam<-1)){

				if(touchpad.getKnobPercentX() > 0.1f) ddx=ddx+(raz_xcam/4);
				else if (touchpad.getKnobPercentX() < -0.1f) ddx=ddx+(raz_xcam/4);
			}
		}
		if (cam.position.x > maxXrange)
			cam.position.x = maxXrange;
		if (cam.position.x < minXrange)
			cam.position.x = minXrange;
		if (cam.position.y > maxYrange)
			cam.position.y = CellSize * (hh) - 385;
		if (cam.position.y < minYrange)
			cam.position.y = 385;

	}

	private void ShowBackground() {

		batch.draw(bg_fon_planet, ddx, 0);
		batch.draw(bg_fon_lab, 200, 200, 2000,600);
		batch.draw(the_komp, 1600,200);

	}

	void ShowM() {

		for (x = 0; x < ww; x++) {
			for (y = 0; y < hh; y++) {
				if (lab[x][y] == 4)
					batch.draw(walls.findRegion("4"), (x) * CellSize, (y) * CellSize);
				else if (lab[x][y] == 31)
					batch.draw(walls.findRegion("31"), (x) * CellSize, (y) * CellSize);
				else if (lab[x][y] == 32)
					batch.draw(walls.findRegion("34"), (x) * CellSize, (y) * CellSize);
				else if (lab[x][y] == 33)
					batch.draw(walls.findRegion("33"), (x) * CellSize, (y) * CellSize);
				else if (lab[x][y] == 34)
					batch.draw(walls.findRegion("32"), (x) * CellSize, (y) * CellSize);
				else if (lab[x][y] == 51)
					batch.draw(walls.findRegion("51"), (x) * CellSize, (y) * CellSize);
				else if (lab[x][y] == 52)
					batch.draw(walls.findRegion("54"), (x) * CellSize, (y) * CellSize);
				else if (lab[x][y] == 53)
					batch.draw(walls.findRegion("53"), (x) * CellSize, (y) * CellSize);
				else if (lab[x][y] == 54)
					batch.draw(walls.findRegion("52"), (x) * CellSize, (y) * CellSize);
				else if (lab[x][y] == 61)
					batch.draw(walls.findRegion("62"), (x) * CellSize, (y) * CellSize);
				else if (lab[x][y] == 62)
					batch.draw(walls.findRegion("61"), (x) * CellSize, (y) * CellSize);
				else if (lab[x][y] == 63)
					batch.draw(walls.findRegion("64"), (x) * CellSize, (y) * CellSize);
				else if (lab[x][y] == 64)
					batch.draw(walls.findRegion("63"), (x) * CellSize, (y) * CellSize);
				else if (lab[x][y] == 21)
					batch.draw(walls.findRegion("21"), (x) * CellSize, (y) * CellSize);
				else if (lab[x][y] == 22)
					batch.draw(walls.findRegion("22"), (x) * CellSize, (y) * CellSize);

				else if (lab[x][y] == 71)
					batch.draw(walls.findRegion("73"), (x) * CellSize, (y) * CellSize);
				else if (lab[x][y] == 72)
					batch.draw(walls.findRegion("72"), (x) * CellSize, (y) * CellSize);
				else if (lab[x][y] == 73)
					batch.draw(walls.findRegion("71"), (x) * CellSize, (y) * CellSize);
				else if (lab[x][y] == 74)
					batch.draw(walls.findRegion("74"), (x) * CellSize, (y) * CellSize);
				else if (lab[x][y] == 11)
					batch.draw(walls.findRegion("12"), (x) * CellSize, (y) * CellSize);
				else if (lab[x][y] == 12)
					batch.draw(walls.findRegion("11"), (x) * CellSize, (y) * CellSize);
				else if (lab[x][y] == 13)
					batch.draw(walls.findRegion("14"), (x) * CellSize, (y) * CellSize);
				else if (lab[x][y] == 14)
					batch.draw(walls.findRegion("13"), (x) * CellSize, (y) * CellSize);
				else if (lab[x][y] == 10)
					batch.draw(walls.findRegion("16"), (x) * CellSize, (y) * CellSize);
				else if (lab[x][y] == 15)
					batch.draw(walls.findRegion("15"), (x) * CellSize, (y) * CellSize);
				else if (lab[x][y] == 16)
					batch.draw(walls.findRegion("10"), (x) * CellSize, (y) * CellSize);
				else if (lab[x][y] == 17)
					batch.draw(walls.findRegion("17"), (x) * CellSize, (y) * CellSize);
			}
		}
	}

	public void for_str() {
		k_for_str = new String[1];
		k_msg_name.setText("Неон");

		k_for_str[0] = "И снова, здравствуй!" + "\n" +
				"Я рад тебя приветствовать" + "\n" +
				"в моём тихом и скромном местечке! @" + "\n" +
				"Здесь открывается очень "+ "\n" +
				"красивый вид на горы"+ "\n" +
				"и на планету, "+ "\n" +
				"на которую улетел <Объект 7> @" + "\n" +
				"Думаю тебе не терпится увидеть меня." + "\n" +
				"Подойди к блоку управления"+ "\n" +
				"и нажми на зеленую кнопку :-) @";

		if(game_forScreen.for_lang==2){
			k_msg_name.setText("Neon");

			k_for_str[0] = "And again, hi!" + "\n" +
					"I am glad you to welcome" + "\n" +
					"in my quiet and modest place! @" + "\n" +
					"Here is very beautiful view"+ "\n" +
					"of mountains and"+ "\n" +
					"the planet on which"+ "\n" +
					"<Object 7> has flown. @" + "\n" +
					"I think you can't wait to see me." + "\n" +
					"Approach control box"+ "\n" +
					"and press the green button. :-) @";

		}

		k_str = k_for_str[0].split("@");
		k_msg.setText(k_str[0]);

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void dispose(){

		batch.dispose();
		walls.dispose();
		bg_fon_planet.dispose();
		bg_fon_lab.dispose();
		ui_atl.dispose();
		stage.dispose();
		gfont.dispose();
		k_stage.dispose();
		k_atl.dispose();
		q_atl.dispose();
		k_font.dispose();
		btn_b.dispose();

	}

}
