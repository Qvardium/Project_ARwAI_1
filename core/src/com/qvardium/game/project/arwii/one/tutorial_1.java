package com.qvardium.game.project.arwii.one;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.qvardium.game.project.arwii.one.ByPlayer.RobotState;

public class tutorial_1 extends ScreenAdapter{

	//--------общие переменные-------------
	MyGame game_forScreen;
	SpriteBatch batch;
	OrthographicCamera cam;
	OrthographicCamera camHUD;
	Viewport viewport;
	Viewport viewportHUD;
	//-------------------------------------

	//--------для лабиринта----------------

	int x,y;
	int ww=10,hh=2;
	int CellSize=200;
	byte[][] lab = new byte[ww+ww+1][hh+hh+1];
	int start_loc,finish_lok;

	TextureAtlas walls;
	Emaze em;

	//-------------------------------------

	//--------для игрока-------------------
	ByPlayer player;
	int pl_x,pl_y,i;
	int robot_speed=100;
	float robot_jump=5,robot_jump_temp=5;
	//-------------------------------------

	//--------для интерфейса---------------
	TextureAtlas ui_atl;

	Stage stage;
	Touchpad touchpad;
	TouchpadStyle touchpadStyle;
	float maxXrange,maxYrange,minXrange,minYrange,speed_cam=20;
	float xrang=6.3f,yrang=3.8f;
	float maxXr1,maxYr1;

	ImageButton jumpButton, actionButton;

	CheckBox sel;
	CheckBoxStyle style_ch;
	BitmapFont gfont;

	Label informFPS;

	Sprite bg_black;
	float bg_black_a;
	boolean end_game=false,the_first_start=true;

	//-------------------------------------

	//-------------ДЛЯ Текущего уровня---------------
	ImageTextButton tutor_btn;
	GameState gs;
	String[] gText;
	byte theStat;
	long time1;
	long pausat;
	float ax1,ax2;
	private String g_dif;
	private String game_sms_level;

	public tutorial_1(MyGame game) {

		game_forScreen=game;
		batch= game.batch;
		cam=game.cam;
		viewport=game.viewport;
		viewport.update(1280, 768, true);
		cam.position.x = 640;
		cam.position.y = 384;
		camHUD = new OrthographicCamera();
		viewportHUD = new FitViewport(1280, 768, camHUD);
		//-------------ДЛЯ Текущего уровня---------------

		if(game.dif==1) g_dif = "Легкий";
		if(game.dif==2) g_dif = "Средний";
		if(game.dif==3) g_dif = "Сложный";
		game_sms_level= " Уровень ";

		if(game_forScreen.for_lang==2){
			if(game.dif==1) g_dif = "Easy";
			if(game.dif==2) g_dif = "Medium";
			if(game.dif==3) g_dif = "Hard";
			game_sms_level= " Level ";
		}

		gText = new String[8];
		gText[0] = "Добро пожаловать на" +"\n"+"Проект АРсИИ: Эксперимент"+"\n"+
				"(автономный робот"+"\n"+
				"с искусственным интеллектом)"+"\n";

		gText[1] = "Для управления роботом используйте тачпад"+"\n";
		gText[2] = "Чтобы совершить прыжок,"+"\n"+
				"используйте кнопку в правом нижнем углу"+"\n";
		gText[3] = "В правом верхнем углу расположен радар."+"\n"+
				"Черным цветом обозначены стены,"+"\n"+
				"желтым ваше текущее расположение,"+"\n"+
				"синим вход в лабиринт и красным"+"\n"+
				"выход из лабиринта"+"\n";
		gText[4] = "Для прохождения уровня нужно дойти"
				+ "\n"+"до красной двери. Дойдите до нее"+"\n";
		gText[5] = "Теперь нажмите кнопку действия,"
				+ "\n"+ "расположенная рядом с кнопкой прыжка"+"\n";
		gText[6] = "Отлично!"
				+ "\n"+ "Вы успешно прошли первый уровень!"+"\n";
		gText[7] = "";

		if(game_forScreen.for_lang==2){
			gText[0] = "Welcome to Project ARwAI: Experiment"+"\n"+
					"(autonomous robot"+"\n"+
					"with artificial intelligence)"+"\n";
			gText[1] = "For control of the robot use the touchpad"+"\n";
			gText[2] = "To make a jump, use the button"+"\n"+
					"in the lower right corner"+"\n";

			gText[3] = "In the upper right corner the radar is located."+"\n"+
					"By black color is designated walls,"+"\n"+
					"yellow - your current layout,"+"\n"+
					"blue - an input in a maze"+"\n"+
					"and red - an output from a maze"+"\n";

			gText[4] = "For passing the level it is necessary"
					+ "\n"+"to reach a red door. Reach it"+"\n";

			gText[5] = "Now press the action button,"
					+ "\n"+ "close next to the jump button"+"\n";

			gText[6] = "It's cool!"
					+ "\n"+ "You passed the first level successfully!"+"\n";

			gText[7] = "";
		}

		theStat=1;
		time1=TimeUtils.millis();
		pausat=1500;
		ax1=30;
		ax2=200;
		gs=GameState.tut1;
		//------для лабиринта----------------------------
		walls = new TextureAtlas(Gdx.files.internal("epilog/tutor.pack"));
		em = new Emaze(ww, hh);
		em.GeneratMaze();
		em.ConvMaze(lab);

		boolean proverka_na_sf;
		do {
			em.StartFinishAction(lab);
			start_loc=em.start_loc;
			finish_lok=em.finish_lok;
			if(start_loc==0 || finish_lok==0) proverka_na_sf=true;
			else proverka_na_sf=false;
		} while (proverka_na_sf);

		//-----------------------------------------------

		//-----------для интерфейса----------------------
		ui_atl = new TextureAtlas("ui_game.pack");

		touchpadStyle = new TouchpadStyle();
		touchpadStyle.background = new TextureRegionDrawable(ui_atl.findRegion("touch_tabel"));
		touchpadStyle.knob = new TextureRegionDrawable(ui_atl.findRegion("touch_tabel_knob"));
		touchpad = new Touchpad(10, touchpadStyle);
		touchpad.setBounds(15, 15, 300, 300);

		jumpButton = new ImageButton(new TextureRegionDrawable(ui_atl.findRegion("jump")), new TextureRegionDrawable(ui_atl.findRegion("jump_pres")));
		actionButton = new ImageButton(new TextureRegionDrawable(ui_atl.findRegion("action")), new TextureRegionDrawable(ui_atl.findRegion("action_pres")));
		//nextButton = new ImageButton(new TextureRegionDrawable(ui_atl.findRegion("zoom_btn_in")),	new TextureRegionDrawable(ui_atl.findRegion("zoom_btn_in_press")));

		gfont = new BitmapFont(Gdx.files.internal("111.fnt"));
		style_ch = new CheckBoxStyle(new TextureRegionDrawable(ui_atl.findRegion("select_cam_rob")), new TextureRegionDrawable(ui_atl.findRegion("select_cam_rob_pres")), gfont, Color.GREEN);
		sel=new CheckBox("Camera", style_ch);

		informFPS = new Label("FPS: "+Gdx.graphics.getFramesPerSecond()+"\n"+game_forScreen.curentLvl+" Уровень", new Label.LabelStyle(gfont,gfont.getColor()));

		tutor_btn  = new ImageTextButton(gText[0],
				new ImageTextButton.ImageTextButtonStyle
						(new TextureRegionDrawable(walls.findRegion("bggg")),
								new TextureRegionDrawable(walls.findRegion("bggg")),
								new TextureRegionDrawable(walls.findRegion("bggg")),
								gfont));

		stage = new Stage(viewportHUD,batch);


		stage.addActor(informFPS);
		stage.addActor(tutor_btn);

		Gdx.input.setInputProcessor(stage);

		maxXrange=((ww+ww+1)*CellSize)-640;
		maxYrange=((hh+hh+1)*CellSize)-380;
		maxXr1=100;
		maxYr1=100;
		minXrange=640;
		minYrange=384;


		jumpButton.setPosition(camHUD.position.x+500, camHUD.position.y-210);
		actionButton.setPosition(camHUD.position.x+350, camHUD.position.y-210);
		sel.setPosition(camHUD.position.x, camHUD.position.y-340);
		informFPS.setPosition(camHUD.position.x-630, camHUD.position.y+130);
		//nextButton.setPosition(camHUD.position.x+500, camHUD.position.y-350);

		actionButton.setVisible(false);
		sel.setChecked(true);
		//-----------------------------------------------

		//-------------для игрока-------------------------
		player= new ByPlayer((start_loc*CellSize)+CellSize,200,game_forScreen.curentLvl, game_forScreen.trueLvl);
		player.RobotMay(true,true,true,true,false,false,false);
		//------------------------------------------------

		bg_black = new Sprite(new Texture(Gdx.files.internal("black.png")), 0, 0, 1280, 768);
		bg_black.setPosition(camHUD.position.x-640, camHUD.position.y-384);
		bg_black_a = 1f;

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		forTheTutuor(delta);

		if (the_first_start){
			if(bg_black_a>0.01f && !end_game) {
				bg_black_a-=0.01f;
				if(bg_black_a<=0.02f && !end_game) {bg_black_a=0; the_first_start=false;}
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

			if(bg_black_a>=1)game_forScreen.setScreen(new tutor_2(game_forScreen));
		}
	}

	void forTheTutuor(float d){
		if(gs==GameState.theGame){
			cam.update();
			batch.setProjectionMatrix(cam.combined);

			showGP();

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

			informFPS.setText("FPS: "+Gdx.graphics.getFramesPerSecond()+"\n"+g_dif+"\n"+game_forScreen.curentLvl+game_sms_level);

			stage.act(Gdx.graphics.getDeltaTime());
			stage.draw();

		}
		else if(gs==GameState.tut1){
			if(theStat==1){

				forTutor(gText[0]);

				if(TimeUtils.timeSinceMillis(time1)>pausat && tutor_btn.isPressed()) theStat=2;
			}
			if(theStat==2){
				stage.addActor(touchpad);
				gs=GameState.tut2;
				time1=TimeUtils.millis();
			}
		}
		else if(gs==GameState.tut2){

			forTutor(gText[1]);

			cam.update();
			batch.setProjectionMatrix(cam.combined);
			batch.begin();
			{
				ax1=ax1-(30*Gdx.graphics.getDeltaTime());
				ax2=ax2+(30*Gdx.graphics.getDeltaTime());
				if(ax1<10 && ax2>220) { ax1=30; ax2=200;}
				batch.draw(walls.findRegion("back"),ax1,108,100,120);
				batch.draw(walls.findRegion("gogo"),ax2,108,100,120);
			}
			batch.end();

			if(tutor_btn.isPressed() && TimeUtils.timeSinceMillis(time1)>pausat){
				stage.addActor(jumpButton);
				gs=GameState.tut3;
				time1=TimeUtils.millis();
				ax1=1010;
			}
		}
		else if(gs==GameState.tut3){

			forTutor(gText[2]);

			cam.update();
			batch.setProjectionMatrix(cam.combined);
			batch.begin();
			{
				ax1=ax1+(30*Gdx.graphics.getDeltaTime());
				if(ax1>1040) ax1=1010;
				batch.draw(walls.findRegion("gogo"),ax1,160,100,120);
			}
			batch.end();

			if(tutor_btn.isPressed() && TimeUtils.timeSinceMillis(time1)>pausat){
				gs=GameState.tut4;
				time1=TimeUtils.millis();
				ax1=800;
			}
		}
		else if(gs==GameState.tut4){

			forTutor(gText[3]);

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

			if(tutor_btn.isPressed() && TimeUtils.timeSinceMillis(time1)>pausat){
				gs=GameState.tut5;
				time1=TimeUtils.millis();
			}
		}
		else if(gs==GameState.tut5){

			forTutor(gText[4]);

			cam.update();
			batch.setProjectionMatrix(cam.combined);
			batch.begin();
			{
				Radar();
			}
			batch.end();

			if(tutor_btn.isPressed() && TimeUtils.timeSinceMillis(time1)>pausat){
				tutor_btn.setVisible(false);
				gs=GameState.theGame;
				time1=TimeUtils.millis();
			}
		}
		else if(gs==GameState.tut6){

			forTutor(gText[5]);

			cam.update();
			batch.setProjectionMatrix(cam.combined);
			batch.begin();
			{
				ax1=ax1+(30*Gdx.graphics.getDeltaTime());
				if(ax1>250) ax1=220;
				batch.draw(walls.findRegion("gogo"),cam.position.x+ax1,cam.position.y-210,100,120);
			}
			batch.end();

			if(actionButton.isPressed() && TimeUtils.timeSinceMillis(time1)>pausat){

				gs=GameState.tut7;
				time1=TimeUtils.millis();
			}
		}
		else if(gs==GameState.tut7){

			forTutor(gText[6]);

			cam.update();
			batch.setProjectionMatrix(cam.combined);
			batch.begin();
			{
				Radar();
			}
			batch.end();

			if(TimeUtils.timeSinceMillis(time1)>pausat) tutor_btn.setVisible(true);
			if(tutor_btn.isPressed() && TimeUtils.timeSinceMillis(time1)>pausat){
				actionButton.setVisible(false);
				game_forScreen.curentLvl=2;
				if(game_forScreen.dif==1 && game_forScreen.curentLvl>game_forScreen.max_lvl_1) game_forScreen.max_lvl_1=game_forScreen.curentLvl;
				else if(game_forScreen.dif==2 && game_forScreen.curentLvl>game_forScreen.max_lvl_2) game_forScreen.max_lvl_2=game_forScreen.curentLvl;
				else if (game_forScreen.dif==3 && game_forScreen.curentLvl>game_forScreen.max_lvl_3) game_forScreen.max_lvl_3=game_forScreen.curentLvl;
				game_forScreen.saveTheLvl();
				end_game=true;
			}
		}
	}

	void forTutor(String s){
		cam.update();
		batch.setProjectionMatrix(cam.combined);

		showGP();

		batch.begin();
		{
			ShowBackground();
			WherePlayer();
			PlayerLogic();
			ShowM();
			player.UpdateCord();

		}
		batch.end();

		camHUD.update();
		batch.setProjectionMatrix(camHUD.combined);
		tutor_btn.setText(s);
		//informFPS.setText(s);

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	void Radar(){
		int frx,fry;

		batch.draw(ui_atl.findRegion("r_ram"), cam.position.x+340, cam.position.y+184);

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
	}

	void ShowNumber(){
		for(x=0;x<ww+ww+1;x++)
			for(y=0;y<hh+hh+1;y++){
				gfont.draw(batch,Integer.toString(lab[x][y]), x*CellSize+100, y*CellSize+100);
			}
	}

	void WherePlayer(){
		if(player.robotState==RobotState.UpLeftWall || player.robotState==RobotState.UpRightWall ||
				player.robotState==RobotState.DownLeftWall || player.robotState==RobotState.DownRightWall);
		else pl_x=(int)player.pl_board[4].x/CellSize;
		pl_y=(int)player.pl_board[4].y/CellSize;
		if(pl_y==hh+hh-1 && pl_x==finish_lok && gs==GameState.theGame) {stage.addActor(actionButton); actionButton.setVisible(true); tutor_btn.setVisible(true); gs=GameState.tut6; time1=TimeUtils.millis(); ax1=220;}
		else if (pl_y!=hh+hh-1 && pl_x!=finish_lok) actionButton.setVisible(false);
		if(pl_y==hh+hh-1 && pl_x==finish_lok && actionButton.isPressed() && gs==GameState.theGame) {gs=GameState.tut7; time1=TimeUtils.millis();}
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

		// -----------------------ВИСИТ НА КРАЮ СТЕНЫ ЗАПРЫГИВАЕТ НА СТЕНУ И СПРЫГИВАЕТ С НЕЕ-----------------------

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

		// -----------------ЗАЛЕЗАЕТ И ВЫЛЕЗАЕТ СО СТЕНЫ----------------------------

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
		if(sel.isChecked() && gs==GameState.theGame){

			if(jumpButton.isPressed()){
				if (!player.in_air && player.robotState==RobotState.StandRight) player.robotState=RobotState.JumpStandRight;
				else if (!player.in_air && player.robotState==RobotState.StandLeft) player.robotState=RobotState.JumpStandLeft;
			}

			else if (touchpad.getKnobPercentX()>0.1f && player.may_go_right && !player.in_air) player.robotState=RobotState.GoRight;
			else if (touchpad.getKnobPercentX()<-0.1f && player.may_go_left && !player.in_air) player.robotState=RobotState.GoLeft;

		}
	}

	void ForGo(){

		if(sel.isChecked() && gs==GameState.theGame){
			player.anim_time = player.anim_time + Gdx.graphics.getDeltaTime();
			if (touchpad.getKnobPercentX()<-0.9f || touchpad.getKnobPercentX()>0.9f) robot_speed=260;
			else robot_speed=100;

			if(touchpad.getKnobPercentX()>0.1f){
				if((lab[pl_x+1][pl_y]==22 || lab[pl_x+1][pl_y]==17) && (player.pl_board[2].x>((pl_x+1)*CellSize))) {
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
				if ((lab[pl_x-1][pl_y]==22 || lab[pl_x-1][pl_y]==15) && (player.pl_board[0].x<((pl_x)*CellSize))) {
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

	void ForJump(){
		if(sel.isChecked()){

			player.anim_time = player.anim_time + Gdx.graphics.getDeltaTime();

			if(player.robotState==RobotState.JumpStandRight || player.robotState==RobotState.JumpStandLeft || player.robotState==RobotState.OffLeftWall || player.robotState==RobotState.OffRightWall){
				if(player.anim_time>=0.85f || (player.robotState==RobotState.OffLeftWall || player.robotState==RobotState.OffRightWall)){
					robot_jump_temp=robot_jump_temp-(9*Gdx.graphics.getDeltaTime());
					player.p.y=player.p.y+robot_jump_temp;
				}
			}

			if(player.robotState==RobotState.JumpRight || player.robotState==RobotState.JumpLeft){
				if(robot_speed==100 && player.anim_time>=0.5f){
					robot_jump_temp=robot_jump_temp-(9*Gdx.graphics.getDeltaTime());
					player.p.y=player.p.y+robot_jump_temp;
				}
				else if(robot_speed==260 && player.anim_time>=0.15f){
					robot_jump_temp=robot_jump_temp-(9*Gdx.graphics.getDeltaTime());
					player.p.y=player.p.y+robot_jump_temp;
				}

			}

			if(player.robotState==RobotState.JumpLeftWallOnRight || player.robotState==RobotState.JumpRightWallOnLeft){
				if(player.anim_time>=0.55f){
					robot_jump_temp=robot_jump_temp-(9*Gdx.graphics.getDeltaTime());
					player.p.y=player.p.y+robot_jump_temp;
					if (player.robotState==RobotState.JumpLeftWallOnRight && player.may_go_right) player.p.x=player.p.x+(robot_speed*Gdx.graphics.getDeltaTime());
					else if (player.robotState==RobotState.JumpRightWallOnLeft && player.may_go_left) player.p.x=player.p.x-(robot_speed*Gdx.graphics.getDeltaTime());
				}
			}

			if (!player.touch_up_wall &&
					(lab[pl_x][pl_y+1]==53 || lab[pl_x][pl_y+1]==21 ||
							lab[pl_x][pl_y+1]==64 || lab[pl_x][pl_y+1]==61 ||
							lab[pl_x][pl_y+1]==51 || lab[pl_x][pl_y+1]==32 ||
							lab[pl_x][pl_y+1]==54 || lab[pl_x][pl_y+1]==10)
					&&(player.pl_board[1].y>(pl_y+1)*CellSize || player.pl_board[3].y>(pl_y+1)*CellSize)) {
				robot_jump_temp=-1;
				player.touch_up_wall=true;
			}
			else if(!player.touch_up_wall && ((lab[pl_x-1][pl_y+1]==53 ||
					lab[pl_x-1][pl_y+1]==64 || lab[pl_x-1][pl_y+1]==61 ||
					lab[pl_x-1][pl_y+1]==51 || lab[pl_x-1][pl_y+1]==54 || lab[pl_x-1][pl_y+1]==10) ||
					(lab[pl_x+1][pl_y+1]==53 || lab[pl_x+1][pl_y+1]==64 || lab[pl_x+1][pl_y+1]==61 ||
							lab[pl_x+1][pl_y+1]==51 || lab[pl_x+1][pl_y+1]==54 || lab[pl_x+1][pl_y+1]==10))
					&& (player.pl_board[1].x<((pl_x)*CellSize) || player.pl_board[3].x>((pl_x+1)*CellSize))
					&&(player.pl_board[1].y>(pl_y+1)*CellSize || player.pl_board[3].y>(pl_y+1)*CellSize)) {
				robot_jump_temp=-1;
				player.touch_up_wall=true;
			}
			if(!player.touch_ground && (
					(lab[pl_x][pl_y-1]==53 || lab[pl_x][pl_y-1]==21 ||
							lab[pl_x][pl_y-1]==52 || lab[pl_x][pl_y-1]==62 ||
							lab[pl_x][pl_y-1]==63 || lab[pl_x][pl_y-1]==34 ||
							lab[pl_x][pl_y-1]==16 || lab[pl_x][pl_y-1]==51) && (player.pl_board[0].y<(pl_y)*CellSize || player.pl_board[2].y<(pl_y)*CellSize)
			)
					||
					(
							((lab[pl_x-1][pl_y-1]==51 || lab[pl_x-1][pl_y-1]==52 || lab[pl_x-1][pl_y-1]==62) && player.pl_board[0].y<(pl_y)*CellSize && player.pl_board[0].x<(pl_x*CellSize)-10 ) ||
									((lab[pl_x+1][pl_y-1]==53 || lab[pl_x+1][pl_y-1]==52 || lab[pl_x+1][pl_y-1]==63) && player.pl_board[2].y<(pl_y)*CellSize && player.pl_board[2].x>((pl_x+1)*CellSize)+10 )
					)){
				robot_jump_temp=robot_jump;
				player.p.y=(pl_y)*CellSize;
				player.touch_ground=false;
				player.touch_up_wall=false;
				if(player.robotState==RobotState.JumpRight || player.robotState==RobotState.JumpStandRight || player.robotState==RobotState.JumpLeftWallOnRight) {player.robotState=RobotState.StandRight; player.anim_time =0;}
				else if (player.robotState==RobotState.JumpLeft || player.robotState==RobotState.JumpStandLeft || player.robotState==RobotState.JumpRightWallOnLeft) {player.robotState=RobotState.StandLeft; player.anim_time =0;}
			}
			if(player.robotState==RobotState.OffLeftWall || player.robotState==RobotState.OffRightWall){
				player.anim_time = player.anim_time + Gdx.graphics.getDeltaTime();
				if(player.anim_time>=0.4f){
					if (player.robotState==RobotState.OffLeftWall) player.p.x=player.p.x-(150*Gdx.graphics.getDeltaTime());
					else if (player.robotState==RobotState.OffRightWall) player.p.x=player.p.x+(150*Gdx.graphics.getDeltaTime());
				}
				if (player.pl_board[0].y<=pl_y*CellSize) {
					if (player.robotState==RobotState.OffLeftWall) {player.p.y=pl_y*CellSize; player.robotState=RobotState.StandLeft;}
					else if (player.robotState==RobotState.OffRightWall) {player.p.y=pl_y*CellSize; player.robotState=RobotState.StandRight;}
				}
			}
			//
			// ПРИЖКИ В     Л Е В О
			//
			if (player.robotState==RobotState.JumpRightWallOnLeft || player.robotState==RobotState.JumpLeft || player.robotState==RobotState.JumpStandLeft){
				if ( (player.robotState==RobotState.JumpLeft || player.robotState==RobotState.JumpStandLeft) && (lab[pl_x-1][pl_y]==22 || lab[pl_x-1][pl_y]==15) && ((player.pl_board[0].x<((pl_x)*CellSize)) || (player.pl_board[1].x<((pl_x)*CellSize)))) {
					player.may_go_left=false;
					player.may_go_right=true;
				}
				if( (lab[pl_x-1][pl_y]==0 || lab[pl_x-1][pl_y]==99) && (lab[pl_x-1][pl_y-1]==51 || lab[pl_x-1][pl_y-1]==52 || lab[pl_x-1][pl_y-1]==62) &&
						(player.pl_board[4].y>(pl_y)*CellSize) && (player.pl_board[0].x<(pl_x)*CellSize) ){
					player.robotState=RobotState.UpLeftWall;
					player.p.x=((pl_x)*CellSize)-20;
					player.anim_time = 0.8f;
					player.p.y=((pl_y-1)*CellSize)+100;
					player.touch_ground=false;
					player.touch_up_wall=false;
					robot_jump_temp=robot_jump;
				}
				else if( (lab[pl_x-1][pl_y+1]==0 || lab[pl_x-1][pl_y+1]==99) && (lab[pl_x-1][pl_y]==51 || lab[pl_x-1][pl_y]==52 || lab[pl_x-1][pl_y]==62) &&
						(player.pl_board[1].y>(pl_y+1)*CellSize) && (player.pl_board[0].x<(pl_x)*CellSize) ){
					player.robotState=RobotState.UpLeftWall;
					player.p.x=((pl_x)*CellSize)-20;
					player.anim_time = 0.4f;
					player.p.y=((pl_y)*CellSize)+50;
					player.touch_ground=false;
					player.touch_up_wall=false;
					robot_jump_temp=robot_jump;
				}
				else if ((lab[pl_x-1][pl_y+1]==22 ||
						lab[pl_x-1][pl_y+1]==52 ||
						lab[pl_x-1][pl_y+1]==33 ||
						lab[pl_x-1][pl_y+1]==62 ||
						lab[pl_x-1][pl_y+1]==15 ||
						lab[pl_x-1][pl_y+1]==54 ||
						lab[pl_x-1][pl_y+1]==51 ||
						lab[pl_x-1][pl_y+1]==61) &&
						(lab[pl_x-1][pl_y]==0 || lab[pl_x-1][pl_y]==98)
						&& (player.pl_board[1].x-20<((pl_x)*CellSize)) && (player.pl_board[1].y>((pl_y+1)*CellSize)+30)) {
					player.p.x=((pl_x)*CellSize)-20;
					player.anim_time = 0;
					player.robotState=RobotState.StandOnLeftWall;
					player.touch_ground=false;
					player.touch_up_wall=false;
					player.may_go_left=true;
					robot_jump_temp=robot_jump;
				}
				else if ((
						((lab[pl_x-1][pl_y]==22 || lab[pl_x-1][pl_y]==52 ||
								lab[pl_x-1][pl_y]==33 || lab[pl_x-1][pl_y]==62 ||
								lab[pl_x-1][pl_y]==15 || lab[pl_x-1][pl_y]==54 ||
								lab[pl_x-1][pl_y]==51 || lab[pl_x-1][pl_y]==61)
								&& (player.pl_board[1].y<(pl_y+1)*CellSize && player.pl_board[4].y>(pl_y)*CellSize))
								||
								(
										(lab[pl_x-1][pl_y]==22 || lab[pl_x-1][pl_y]==33 ||
												lab[pl_x-1][pl_y]==15 || lab[pl_x-1][pl_y]==54 ||
												lab[pl_x-1][pl_y]==61) && (lab[pl_x-1][pl_y-1]==0 || lab[pl_x-1][pl_y-1]==98)&&
												(lab[pl_x-1][pl_y+1]==22 || lab[pl_x-1][pl_y+1]==52 ||
														lab[pl_x-1][pl_y+1]==33 || lab[pl_x-1][pl_y+1]==62 ||
														lab[pl_x-1][pl_y+1]==15) && (player.pl_board[4].y>(pl_y)*CellSize && player.pl_board[1].y<(pl_y+2)*CellSize))
								|| (
								(lab[pl_x-1][pl_y]==22 || lab[pl_x-1][pl_y]==33 || lab[pl_x-1][pl_y]==15) &&
										(lab[pl_x-1][pl_y-1]==15 || lab[pl_x-1][pl_y-1]==22 || lab[pl_x-1][pl_y-1]==33 || lab[pl_x-1][pl_y-1]==54 || lab[pl_x-1][pl_y-1]==61) &&
										(player.pl_board[4].y>(pl_y)*CellSize && player.pl_board[1].y<(pl_y+1)*CellSize)

						)
				)&& (player.pl_board[1].x-10<pl_x*CellSize) ) {
					player.p.x=((pl_x)*CellSize)-20;
					player.anim_time = 0;
					player.robotState=RobotState.StandLeftWall;
					robot_speed=150;
					player.touch_ground=false;
					player.touch_up_wall=false;
					robot_jump_temp=robot_jump;
				}

				if(player.robotState==RobotState.StandLeftWall || player.robotState==RobotState.OnLeftWall || player.robotState==RobotState.UpLeftWall){
					player.may_go_left=true; player.may_go_right=true;
				}
			}
			//
			// ПРИЖКИ В     П Р А В О
			//
			else if (player.robotState==RobotState.JumpLeftWallOnRight || player.robotState==RobotState.JumpRight || player.robotState==RobotState.JumpStandRight){
				if((player.robotState==RobotState.JumpRight || player.robotState==RobotState.JumpStandRight) && (lab[pl_x+1][pl_y]==22 || lab[pl_x+1][pl_y]==17) && ((player.pl_board[2].x>((pl_x+1)*CellSize)) || (player.pl_board[3].x>((pl_x+1)*CellSize)))) {
					player.may_go_left=true;
					player.may_go_right=false;
				}
				if( (lab[pl_x+1][pl_y]==0 || lab[pl_x+1][pl_y]==99) && (lab[pl_x+1][pl_y-1]==52 || lab[pl_x+1][pl_y-1]==53 || lab[pl_x+1][pl_y-1]==63) &&
						(player.pl_board[4].y>(pl_y)*CellSize) && (player.pl_board[2].x>(pl_x+1)*CellSize) ){
					player.robotState=RobotState.UpRightWall;
					player.p.x=((pl_x+1)*CellSize)-80;
					player.anim_time = 0.8f;
					player.p.y=((pl_y-1)*CellSize)+100;
					player.touch_ground=false;
					player.touch_up_wall=false;
					robot_jump_temp=robot_jump;
				}
				else if( (lab[pl_x+1][pl_y+1]==0 || lab[pl_x+1][pl_y+1]==99) && (lab[pl_x+1][pl_y]==52 || lab[pl_x+1][pl_y]==53 || lab[pl_x+1][pl_y]==63) &&
						(player.pl_board[3].y>(pl_y+1)*CellSize) && (player.pl_board[2].x>(pl_x+1)*CellSize) ){
					player.robotState=RobotState.UpRightWall;
					player.p.x=((pl_x+1)*CellSize)-80;
					player.anim_time = 0.4f;
					player.p.y=((pl_y)*CellSize)+50;
					player.touch_ground=false;
					player.touch_up_wall=false;
					robot_jump_temp=robot_jump;
				}
				else if((lab[pl_x+1][pl_y+1]==22 ||
						lab[pl_x+1][pl_y+1]==52 ||
						lab[pl_x+1][pl_y+1]==17 ||
						lab[pl_x+1][pl_y+1]==63 ||
						lab[pl_x+1][pl_y+1]==31 ||
						lab[pl_x+1][pl_y+1]==64 ||
						lab[pl_x+1][pl_y+1]==52 ||
						lab[pl_x+1][pl_y+1]==54 ||
						lab[pl_x+1][pl_y+1]==53) &&
						(lab[pl_x+1][pl_y]==0 || lab[pl_x+1][pl_y]==98)
						&& (player.pl_board[3].x+10>((pl_x+1)*CellSize)-5) && (player.pl_board[3].y>((pl_y+1)*CellSize)+30)) {
					player.p.x=((pl_x)*CellSize)+120;
					player.anim_time = 0;
					player.robotState=RobotState.StandOnRightWall;
					player.touch_ground=false;
					player.touch_up_wall=false;
					player.may_go_right=true;
					robot_jump_temp=robot_jump;
				}
				else if( (
						((lab[pl_x+1][pl_y]==22 || lab[pl_x+1][pl_y]==52 ||
								lab[pl_x+1][pl_y]==17 || lab[pl_x+1][pl_y]==63 ||
								lab[pl_x+1][pl_y]==31 || lab[pl_x+1][pl_y]==64 ||
								lab[pl_x+1][pl_y]==54 || lab[pl_x+1][pl_y]==53)
								&& (player.pl_board[3].y<(pl_y+1)*CellSize && player.pl_board[4].y>(pl_y)*CellSize))
								||
								(
										(lab[pl_x+1][pl_y]==22 || lab[pl_x+1][pl_y]==17 ||
												lab[pl_x+1][pl_y]==31 || lab[pl_x+1][pl_y]==64 ||
												lab[pl_x+1][pl_y]==54) && (lab[pl_x+1][pl_y-1]==0 || lab[pl_x+1][pl_y-1]==98) &&
												(lab[pl_x+1][pl_y+1]==22 || lab[pl_x+1][pl_y+1]==52 ||
														lab[pl_x+1][pl_y+1]==17 || lab[pl_x+1][pl_y+1]==63 ||
														lab[pl_x+1][pl_y+1]==31) && (player.pl_board[4].y>(pl_y)*CellSize && player.pl_board[3].y<(pl_y+2)*CellSize))
								|| (
								(lab[pl_x+1][pl_y]==22 || lab[pl_x+1][pl_y]==17 || lab[pl_x+1][pl_y]==31) &&
										(lab[pl_x+1][pl_y-1]==17 || lab[pl_x+1][pl_y-1]==22 || lab[pl_x+1][pl_y-1]==31 || lab[pl_x+1][pl_y-1]==54 || lab[pl_x+1][pl_y-1]==64) &&
										(player.pl_board[4].y>(pl_y)*CellSize && player.pl_board[3].y<(pl_y+1)*CellSize)
						)) && (player.pl_board[3].x+20>(pl_x+1)*CellSize) ) {
					player.p.x=((pl_x+1)*CellSize)-80;
					player.anim_time = 0;
					player.robotState=RobotState.StandRightWall;
					robot_speed=150;
					player.touch_ground=false;
					player.touch_up_wall=false;
					robot_jump_temp=robot_jump;
				}

				if(player.robotState==RobotState.StandRightWall || player.robotState==RobotState.OnRightWall || player.robotState==RobotState.UpRightWall){
					player.may_go_left=true; player.may_go_right=true;
				}
			}
			if (player.robotState==RobotState.JumpRight && player.may_go_right) player.p.x=player.p.x+(robot_speed*Gdx.graphics.getDeltaTime());
			else if (player.robotState==RobotState.JumpLeft && player.may_go_left) player.p.x=player.p.x-(robot_speed*Gdx.graphics.getDeltaTime());
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

		for(x=0;x<ww+ww+1;x++)
			for(y=0;y<hh+hh+1;y++){
				if (lab[x][y]==0) batch.draw(walls.findRegion("fone_labyrint"), x*CellSize, y*CellSize);
				if (lab[x][y]==98) batch.draw(walls.findRegion("finish"), x*CellSize, y*CellSize);
				if (lab[x][y]==99) batch.draw(walls.findRegion("start"), x*CellSize, y*CellSize);
			}

	}

	void ShowM(){

		for(x=0;x<ww+ww+1;x++){
			for(y=0;y<hh+hh+1;y++){
				if (lab[x][y]==4) batch.draw(walls.findRegion("4"), x*CellSize, y*CellSize);
				else if (lab[x][y]==31) batch.draw(walls.findRegion("31"), x*CellSize, y*CellSize);
				else if (lab[x][y]==32) batch.draw(walls.findRegion("34"), x*CellSize, y*CellSize);
				else if (lab[x][y]==33) batch.draw(walls.findRegion("33"), x*CellSize, y*CellSize);
				else if (lab[x][y]==34) batch.draw(walls.findRegion("32"), x*CellSize, y*CellSize);
				else if (lab[x][y]==51) batch.draw(walls.findRegion("51"), x*CellSize, y*CellSize);
				else if (lab[x][y]==52) batch.draw(walls.findRegion("54"), x*CellSize, y*CellSize);
				else if (lab[x][y]==53) batch.draw(walls.findRegion("53"), x*CellSize, y*CellSize);
				else if (lab[x][y]==54) batch.draw(walls.findRegion("52"), x*CellSize, y*CellSize);
				else if (lab[x][y]==61) batch.draw(walls.findRegion("62"), x*CellSize, y*CellSize);
				else if (lab[x][y]==62) batch.draw(walls.findRegion("61"), x*CellSize, y*CellSize);
				else if (lab[x][y]==63) batch.draw(walls.findRegion("64"), x*CellSize, y*CellSize);
				else if (lab[x][y]==64) batch.draw(walls.findRegion("63"), x*CellSize, y*CellSize);
				else if (lab[x][y]==21) batch.draw(walls.findRegion("21"), x*CellSize, y*CellSize);
				else if (lab[x][y]==22) batch.draw(walls.findRegion("22"), x*CellSize, y*CellSize);

				else if (lab[x][y]==71) batch.draw(walls.findRegion("73"), x*CellSize, y*CellSize);
				else if (lab[x][y]==72) batch.draw(walls.findRegion("72"), x*CellSize, y*CellSize);
				else if (lab[x][y]==73) batch.draw(walls.findRegion("71"), x*CellSize, y*CellSize);
				else if (lab[x][y]==74) batch.draw(walls.findRegion("74"), x*CellSize, y*CellSize);
				else if (lab[x][y]==11) batch.draw(walls.findRegion("12"), x*CellSize, y*CellSize);
				else if (lab[x][y]==12) batch.draw(walls.findRegion("11"), x*CellSize, y*CellSize);
				else if (lab[x][y]==13) batch.draw(walls.findRegion("14"), x*CellSize, y*CellSize);
				else if (lab[x][y]==14) batch.draw(walls.findRegion("13"), x*CellSize, y*CellSize);
				else if (lab[x][y]==10) batch.draw(walls.findRegion("16"), x*CellSize, y*CellSize);
				else if (lab[x][y]==15) batch.draw(walls.findRegion("15"), x*CellSize, y*CellSize);
				else if (lab[x][y]==16) batch.draw(walls.findRegion("10"), x*CellSize, y*CellSize);
				else if (lab[x][y]==17) batch.draw(walls.findRegion("17"), x*CellSize, y*CellSize);
			}
		}
	}

	static enum GameState{
		theGame,tut1,tut2,tut3,tut4,tut5,tut6,tut7
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

	}
}
