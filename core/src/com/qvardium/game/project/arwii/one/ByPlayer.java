package com.qvardium.game.project.arwii.one;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class ByPlayer {
	TextureAtlas at;
	TextureRegion stand_r,stand_l,stand_r_wall,stand_l_wall;
	RobotState robotState;
	Animation go,go_back,run,run_back,
				jump_stand_r,
				jump_stand_l,
				jump_go_r,
				jump_go_l,
				jump_run_r,
				jump_run_l,
				jump_l_on_r,
				jump_r_on_l,
				on_r_wall,
				on_l_wall,
				up_r_wall,
				up_l_wall,
				go_r_wall,
				go_l_wall,
				off_r_wall,
				off_l_wall,
				fall_l,
				fall_r,
				dead_l,
				dead_r;
	Vector2 p;
	Vector2[] pl_board;
	float anim_time;
	boolean may_go_right, may_go_left, may_go_up_wall, may_go_down_wall, in_air, touch_ground, touch_up_wall;
	Sound[] run_s, move_s;
	float for_sound;
	int for_foot=0;
	byte for_jump_s;
	
	float p_gizni=300f;
	
	public ByPlayer(float x, float y, int lvl, boolean tt) {
		if(lvl>10 && lvl<=20 && tt) at = new TextureAtlas(Gdx.files.internal("player_shaft.pack"));
		else at = new TextureAtlas(Gdx.files.internal("player_test.pack"));
		go = new Animation(0.05f, at.findRegion("go0039"),
				at.findRegion("go0041"),
				at.findRegion("go0043"),
				at.findRegion("go0045"),
				at.findRegion("go0047"),
				at.findRegion("go0049"),
				at.findRegion("go0051"),
				at.findRegion("go0053"),
				at.findRegion("go0055"),
				at.findRegion("go0057"),
				at.findRegion("go0059"),
				at.findRegion("go0061"),
				at.findRegion("go0063"),
				at.findRegion("go0065"),
				at.findRegion("go0067"));
		go_back = new Animation(0.05f, at.findRegion("go_back0039"),
				at.findRegion("go_back0041"),
				at.findRegion("go_back0043"),
				at.findRegion("go_back0045"),
				at.findRegion("go_back0047"),
				at.findRegion("go_back0049"),
				at.findRegion("go_back0051"),
				at.findRegion("go_back0053"),
				at.findRegion("go_back0055"),
				at.findRegion("go_back0057"),
				at.findRegion("go_back0059"),
				at.findRegion("go_back0061"),
				at.findRegion("go_back0063"),
				at.findRegion("go_back0065"),
				at.findRegion("go_back0067"));
		run = new Animation(0.05f,at.findRegion("run0000"),
				at.findRegion("run0001"),
				at.findRegion("run0002"),
				at.findRegion("run0003"),
				at.findRegion("run0004"),
				at.findRegion("run0005"),
				at.findRegion("run0006"),
				at.findRegion("run0007"),
				at.findRegion("run0008"),
				at.findRegion("run0009"),
				at.findRegion("run0010"),
				at.findRegion("run0011"),
				at.findRegion("run0012"),
				at.findRegion("run0013"),
				at.findRegion("run0014"),
				at.findRegion("run0015"),
				at.findRegion("run0016"),
				at.findRegion("run0017"),
				at.findRegion("run0018"));
		run_back = new Animation(0.05f,at.findRegion("run_back0000"),
				at.findRegion("run_back0001"),
				at.findRegion("run_back0002"),
				at.findRegion("run_back0003"),
				at.findRegion("run_back0004"),
				at.findRegion("run_back0005"),
				at.findRegion("run_back0006"),
				at.findRegion("run_back0007"),
				at.findRegion("run_back0008"),
				at.findRegion("run_back0009"),
				at.findRegion("run_back0010"),
				at.findRegion("run_back0011"),
				at.findRegion("run_back0012"),
				at.findRegion("run_back0013"),
				at.findRegion("run_back0014"),
				at.findRegion("run_back0015"),
				at.findRegion("run_back0016"),
				at.findRegion("run_back0017"),
				at.findRegion("run_back0018"));
		jump_stand_r = new Animation(0.05f, at.findRegion("jump_stand_r0000"),
				at.findRegion("jump_stand_r0001"),
				at.findRegion("jump_stand_r0002"),
				at.findRegion("jump_stand_r0003"),
				at.findRegion("jump_stand_r0004"),
				at.findRegion("jump_stand_r0005"),
				at.findRegion("jump_stand_r0006"),
				at.findRegion("jump_stand_r0007"),
				at.findRegion("jump_stand_r0008"),
				at.findRegion("jump_stand_r0009"),
				at.findRegion("jump_stand_r0010"),
				at.findRegion("jump_stand_r0011"),
				at.findRegion("jump_stand_r0012"),
				at.findRegion("jump_stand_r0013"),
				at.findRegion("jump_stand_r0014"),
				at.findRegion("jump_stand_r0015"),
				at.findRegion("jump_stand_r0016"),
				at.findRegion("jump_stand_r0017"),
				at.findRegion("jump_stand_r0018"),
				at.findRegion("jump_stand_r0019"),
				at.findRegion("jump_stand_r0020"),
				at.findRegion("jump_stand_r0021"),
				at.findRegion("jump_stand_r0022"),
				at.findRegion("jump_stand_r0023"),
				at.findRegion("jump_stand_r0024"),
				at.findRegion("jump_stand_r0025"),
				at.findRegion("jump_stand_r0026"),
				at.findRegion("jump_stand_r0027"),
				at.findRegion("jump_stand_r0028"));
		jump_stand_l = new Animation(0.05f,at.findRegion("jump_stand_l0000"),
				at.findRegion("jump_stand_l0001"),
				at.findRegion("jump_stand_l0002"),
				at.findRegion("jump_stand_l0003"),
				at.findRegion("jump_stand_l0004"),
				at.findRegion("jump_stand_l0005"),
				at.findRegion("jump_stand_l0006"),
				at.findRegion("jump_stand_l0007"),
				at.findRegion("jump_stand_l0008"),
				at.findRegion("jump_stand_l0009"),
				at.findRegion("jump_stand_l0010"),
				at.findRegion("jump_stand_l0011"),
				at.findRegion("jump_stand_l0012"),
				at.findRegion("jump_stand_l0013"),
				at.findRegion("jump_stand_l0014"),
				at.findRegion("jump_stand_l0015"),
				at.findRegion("jump_stand_l0016"),
				at.findRegion("jump_stand_l0017"),
				at.findRegion("jump_stand_l0018"),
				at.findRegion("jump_stand_l0019"),
				at.findRegion("jump_stand_l0020"),
				at.findRegion("jump_stand_l0021"),
				at.findRegion("jump_stand_l0022"),
				at.findRegion("jump_stand_l0023"),
				at.findRegion("jump_stand_l0024"),
				at.findRegion("jump_stand_l0025"),
				at.findRegion("jump_stand_l0026"),
				at.findRegion("jump_stand_l0027"),
				at.findRegion("jump_stand_l0028"));
		jump_go_r = new Animation(0.05f,at.findRegion("jump_go_r0000"),
				at.findRegion("jump_go_r0001"),
				at.findRegion("jump_go_r0002"),
				at.findRegion("jump_go_r0003"),
				at.findRegion("jump_go_r0004"),
				at.findRegion("jump_go_r0005"),
				at.findRegion("jump_go_r0006"),
				at.findRegion("jump_go_r0007"),
				at.findRegion("jump_go_r0008"),
				at.findRegion("jump_go_r0009"),
				at.findRegion("jump_go_r0010"),
				at.findRegion("jump_go_r0011"),
				at.findRegion("jump_go_r0012"),
				at.findRegion("jump_go_r0013"),
				at.findRegion("jump_go_r0014"),
				at.findRegion("jump_go_r0015"),
				at.findRegion("jump_go_r0016"),
				at.findRegion("jump_go_r0017"),
				at.findRegion("jump_go_r0018"),
				at.findRegion("jump_go_r0019"));
		jump_go_l = new Animation(0.05f,at.findRegion("jump_go_l0000"),
				at.findRegion("jump_go_l0001"),
				at.findRegion("jump_go_l0002"),
				at.findRegion("jump_go_l0003"),
				at.findRegion("jump_go_l0004"),
				at.findRegion("jump_go_l0005"),
				at.findRegion("jump_go_l0006"),
				at.findRegion("jump_go_l0007"),
				at.findRegion("jump_go_l0008"),
				at.findRegion("jump_go_l0009"),
				at.findRegion("jump_go_l0010"),
				at.findRegion("jump_go_l0011"),
				at.findRegion("jump_go_l0012"),
				at.findRegion("jump_go_l0013"),
				at.findRegion("jump_go_l0014"),
				at.findRegion("jump_go_l0015"),
				at.findRegion("jump_go_l0016"),
				at.findRegion("jump_go_l0017"),
				at.findRegion("jump_go_l0018"),
				at.findRegion("jump_go_l0019"));
		jump_run_l = new Animation(0.05f,at.findRegion("jump_run_l0000"),
				at.findRegion("jump_run_l0001"),
				at.findRegion("jump_run_l0002"),
				at.findRegion("jump_run_l0003"),
				at.findRegion("jump_run_l0004"),
				at.findRegion("jump_run_l0005"),
				at.findRegion("jump_run_l0006"),
				at.findRegion("jump_run_l0007"),
				at.findRegion("jump_run_l0008"),
				at.findRegion("jump_run_l0009"),
				at.findRegion("jump_run_l0010"),
				at.findRegion("jump_run_l0011"),
				at.findRegion("jump_run_l0012"),
				at.findRegion("jump_run_l0013"),
				at.findRegion("jump_run_l0014"));
		jump_run_r = new Animation(0.05f,at.findRegion("jump_run_r0000"),
				at.findRegion("jump_run_r0001"),
				at.findRegion("jump_run_r0002"),
				at.findRegion("jump_run_r0003"),
				at.findRegion("jump_run_r0004"),
				at.findRegion("jump_run_r0005"),
				at.findRegion("jump_run_r0006"),
				at.findRegion("jump_run_r0007"),
				at.findRegion("jump_run_r0008"),
				at.findRegion("jump_run_r0009"),
				at.findRegion("jump_run_r0010"),
				at.findRegion("jump_run_r0011"),
				at.findRegion("jump_run_r0012"),
				at.findRegion("jump_run_r0013"),
				at.findRegion("jump_run_r0014"));
		jump_l_on_r = new Animation(0.05f,at.findRegion("jump_l_wall_r0000"),
				at.findRegion("jump_l_wall_r0001"),
				at.findRegion("jump_l_wall_r0002"),
				at.findRegion("jump_l_wall_r0003"),
				at.findRegion("jump_l_wall_r0004"),
				at.findRegion("jump_l_wall_r0005"),
				at.findRegion("jump_l_wall_r0006"),
				at.findRegion("jump_l_wall_r0007"),
				at.findRegion("jump_l_wall_r0008"),
				at.findRegion("jump_l_wall_r0009"),
				at.findRegion("jump_l_wall_r0010"),
				at.findRegion("jump_l_wall_r0011"),
				at.findRegion("jump_l_wall_r0012"),
				at.findRegion("jump_l_wall_r0013"),
				at.findRegion("jump_l_wall_r0014"));
		jump_r_on_l = new Animation(0.05f,at.findRegion("jump_r_wall_l0000"),
				at.findRegion("jump_r_wall_l0001"),
				at.findRegion("jump_r_wall_l0002"),
				at.findRegion("jump_r_wall_l0003"),
				at.findRegion("jump_r_wall_l0004"),
				at.findRegion("jump_r_wall_l0005"),
				at.findRegion("jump_r_wall_l0006"),
				at.findRegion("jump_r_wall_l0007"),
				at.findRegion("jump_r_wall_l0008"),
				at.findRegion("jump_r_wall_l0009"),
				at.findRegion("jump_r_wall_l0010"),
				at.findRegion("jump_r_wall_l0011"),
				at.findRegion("jump_r_wall_l0012"),
				at.findRegion("jump_r_wall_l0013"),
				at.findRegion("jump_r_wall_l0014"));
		on_r_wall= new Animation(0.05f,at.findRegion("on_r_wall0000"),
				at.findRegion("on_r_wall0001"),
				at.findRegion("on_r_wall0002"),
				at.findRegion("on_r_wall0003"),
				at.findRegion("on_r_wall0004"),
				at.findRegion("on_r_wall0005"),
				at.findRegion("on_r_wall0006"),
				at.findRegion("on_r_wall0007"),
				at.findRegion("on_r_wall0008"),
				at.findRegion("on_r_wall0009"),
				at.findRegion("on_r_wall0010"),
				at.findRegion("on_r_wall0011"),
				at.findRegion("on_r_wall0012"),
				at.findRegion("on_r_wall0013"),
				at.findRegion("on_r_wall0014"));
		on_l_wall= new Animation(0.05f,at.findRegion("on_l_wall0000"),
				at.findRegion("on_l_wall0001"),
				at.findRegion("on_l_wall0002"),
				at.findRegion("on_l_wall0003"),
				at.findRegion("on_l_wall0004"),
				at.findRegion("on_l_wall0005"),
				at.findRegion("on_l_wall0006"),
				at.findRegion("on_l_wall0007"),
				at.findRegion("on_l_wall0008"),
				at.findRegion("on_l_wall0009"),
				at.findRegion("on_l_wall0010"),
				at.findRegion("on_l_wall0011"),
				at.findRegion("on_l_wall0012"),
				at.findRegion("on_l_wall0013"),
				at.findRegion("on_l_wall0014"));
		up_r_wall= new Animation(0.05f,at.findRegion("up_r_wall0000"),
				at.findRegion("up_r_wall0001"),
				at.findRegion("up_r_wall0002"),
				at.findRegion("up_r_wall0003"),
				at.findRegion("up_r_wall0004"),
				at.findRegion("up_r_wall0005"),
				at.findRegion("up_r_wall0006"),
				at.findRegion("up_r_wall0007"),
				at.findRegion("up_r_wall0008"),
				at.findRegion("up_r_wall0009"),
				at.findRegion("up_r_wall0010"),
				at.findRegion("up_r_wall0011"),
				at.findRegion("up_r_wall0012"),
				at.findRegion("up_r_wall0013"),
				at.findRegion("up_r_wall0014"),
				at.findRegion("up_r_wall0015"),
				at.findRegion("up_r_wall0016"),
				at.findRegion("up_r_wall0017"),
				at.findRegion("up_r_wall0018"),
				at.findRegion("up_r_wall0019"),
				at.findRegion("up_r_wall0020"),
				at.findRegion("up_r_wall0021"),
				at.findRegion("up_r_wall0022"),
				at.findRegion("up_r_wall0023"),
				at.findRegion("up_r_wall0024"),
				at.findRegion("up_r_wall0025"),
				at.findRegion("up_r_wall0026"),
				at.findRegion("up_r_wall0027"),
				at.findRegion("up_r_wall0028"),
				at.findRegion("up_r_wall0029"),
				at.findRegion("up_r_wall0030"),
				at.findRegion("up_r_wall0031"),
				at.findRegion("up_r_wall0032"));
		up_l_wall= new Animation(0.05f,at.findRegion("up_l_wall0000"),
				at.findRegion("up_l_wall0001"),
				at.findRegion("up_l_wall0002"),
				at.findRegion("up_l_wall0003"),
				at.findRegion("up_l_wall0004"),
				at.findRegion("up_l_wall0005"),
				at.findRegion("up_l_wall0006"),
				at.findRegion("up_l_wall0007"),
				at.findRegion("up_l_wall0008"),
				at.findRegion("up_l_wall0009"),
				at.findRegion("up_l_wall0010"),
				at.findRegion("up_l_wall0011"),
				at.findRegion("up_l_wall0012"),
				at.findRegion("up_l_wall0013"),
				at.findRegion("up_l_wall0014"),
				at.findRegion("up_l_wall0015"),
				at.findRegion("up_l_wall0016"),
				at.findRegion("up_l_wall0017"),
				at.findRegion("up_l_wall0018"),
				at.findRegion("up_l_wall0019"),
				at.findRegion("up_l_wall0020"),
				at.findRegion("up_l_wall0021"),
				at.findRegion("up_l_wall0022"),
				at.findRegion("up_l_wall0023"),
				at.findRegion("up_l_wall0024"),
				at.findRegion("up_l_wall0025"),
				at.findRegion("up_l_wall0026"),
				at.findRegion("up_l_wall0027"),
				at.findRegion("up_l_wall0028"),
				at.findRegion("up_l_wall0029"),
				at.findRegion("up_l_wall0030"),
				at.findRegion("up_l_wall0031"),
				at.findRegion("up_l_wall0032"));
		go_r_wall= new Animation(0.05f,at.findRegion("go_r_wall0000"),
				at.findRegion("go_r_wall0001"),
				at.findRegion("go_r_wall0002"),
				at.findRegion("go_r_wall0003"),
				at.findRegion("go_r_wall0004"),
				at.findRegion("go_r_wall0005"),
				at.findRegion("go_r_wall0006"),
				at.findRegion("go_r_wall0007"),
				at.findRegion("go_r_wall0008"),
				at.findRegion("go_r_wall0009"),
				at.findRegion("go_r_wall0010"),
				at.findRegion("go_r_wall0011"),
				at.findRegion("go_r_wall0012"),
				at.findRegion("go_r_wall0013"));
		go_l_wall= new Animation(0.05f,at.findRegion("go_l_wall0000"),
				at.findRegion("go_l_wall0001"),
				at.findRegion("go_l_wall0002"),
				at.findRegion("go_l_wall0003"),
				at.findRegion("go_l_wall0004"),
				at.findRegion("go_l_wall0005"),
				at.findRegion("go_l_wall0006"),
				at.findRegion("go_l_wall0007"),
				at.findRegion("go_l_wall0008"),
				at.findRegion("go_l_wall0009"),
				at.findRegion("go_l_wall0010"),
				at.findRegion("go_l_wall0011"),
				at.findRegion("go_l_wall0012"),
				at.findRegion("go_l_wall0013"));
		off_r_wall= new Animation(0.05f,at.findRegion("off_r_wall0000"),
				at.findRegion("off_r_wall0001"),
				at.findRegion("off_r_wall0002"),
				at.findRegion("off_r_wall0003"),
				at.findRegion("off_r_wall0004"),
				at.findRegion("off_r_wall0005"),
				at.findRegion("off_r_wall0006"),
				at.findRegion("off_r_wall0007"),
				at.findRegion("off_r_wall0008"));
		off_l_wall= new Animation(0.05f,at.findRegion("off_l_wall0000"),
				at.findRegion("off_l_wall0001"),
				at.findRegion("off_l_wall0002"),
				at.findRegion("off_l_wall0003"),
				at.findRegion("off_l_wall0004"),
				at.findRegion("off_l_wall0005"),
				at.findRegion("off_l_wall0006"),
				at.findRegion("off_l_wall0007"),
				at.findRegion("off_l_wall0008"));
		fall_l = new Animation(0.1f,at.findRegion("fall_l0000"),
				at.findRegion("fall_l0001"),
				at.findRegion("fall_l0002"),
				at.findRegion("fall_l0003"),
				at.findRegion("fall_l0004"),
				at.findRegion("fall_l0005"),
				at.findRegion("fall_l0006"),
				at.findRegion("fall_l0007"));
		fall_r = new Animation(0.1f,at.findRegion("fall_r0000"),
				at.findRegion("fall_r0001"),
				at.findRegion("fall_r0002"),
				at.findRegion("fall_r0003"),
				at.findRegion("fall_r0004"),
				at.findRegion("fall_r0005"),
				at.findRegion("fall_r0006"),
				at.findRegion("fall_r0007"));
		dead_r = new Animation(0.1f,at.findRegion("dead_l0000"),
				at.findRegion("dead_l0001"),
				at.findRegion("dead_l0002"),
				at.findRegion("dead_l0003"),
				at.findRegion("dead_l0004"),
				at.findRegion("dead_l0005"),
				at.findRegion("dead_l0006"),
				at.findRegion("dead_l0007"));
		dead_l = new Animation(0.1f,at.findRegion("dead_r0000"),
				at.findRegion("dead_r0001"),
				at.findRegion("dead_r0002"),
				at.findRegion("dead_r0003"),
				at.findRegion("dead_r0004"),
				at.findRegion("dead_r0005"),
				at.findRegion("dead_r0006"),
				at.findRegion("dead_r0007"));
		
		p=new Vector2(x, y);
		pl_board = new Vector2[5];
		pl_board[0]=new Vector2(x+20,y);
		pl_board[1]=new Vector2(x+30,y+170);
		pl_board[2]=new Vector2(x+80,y);
		pl_board[3]=new Vector2(x+70,y+170);
		pl_board[4]=new Vector2(x+50,y+85);
		
		
		run_s = new Sound[4];
		move_s = new Sound[4];

		for_sound=0f;
		for_jump_s = 1;
		
		for(int kk=0;kk<2;kk++){
			if(lvl>10 && lvl<=20 && tt){
				run_s[kk]= Gdx.audio.newSound(Gdx.files.internal("sounds/gos_m"+kk+".mp3"));
				run_s[kk+2]= Gdx.audio.newSound(Gdx.files.internal("sounds/runs_m"+kk+".mp3"));
			}
			else{
				run_s[kk]= Gdx.audio.newSound(Gdx.files.internal("sounds/go_m"+kk+".mp3"));
				run_s[kk+2]= Gdx.audio.newSound(Gdx.files.internal("sounds/run_m"+kk+".mp3"));
			}
		}
		for(int kk=0;kk<4;kk++){
			move_s[kk]= Gdx.audio.newSound(Gdx.files.internal("sounds/move"+(kk+1)+".mp3"));
		}
		
		robotState = RobotState.StandRight;
		stand_r = at.findRegion("stand");
		stand_l = at.findRegion("stand_back");
		stand_r_wall = at.findRegion("stand_r_wall");
		stand_l_wall = at.findRegion("stand_l_wall");
		go.setPlayMode(PlayMode.LOOP);
		go_back.setPlayMode(PlayMode.LOOP);
		run.setPlayMode(PlayMode.LOOP);
		run_back.setPlayMode(PlayMode.LOOP);
		go_l_wall.setPlayMode(PlayMode.LOOP);
		go_r_wall.setPlayMode(PlayMode.LOOP);
		touch_ground = false;
		up_r_wall.setPlayMode(PlayMode.NORMAL);
		up_l_wall.setPlayMode(PlayMode.NORMAL);
	}
	
	static enum RobotState {
		StandRight, StandLeft, 
		GoRight, GoLeft, 
		JumpStandRight, JumpStandLeft, 
		JumpRight, JumpLeft, 
		JumpLeftWallOnRight, JumpRightWallOnLeft, 
		GoRightWallUp, GoRightWallDown, 
		GoLeftWallUp, GoLeftWallDown, 
		StandRightWall, StandLeftWall,
		StandOnLeftWall,StandOnRightWall,
		OnLeftWall, OnRightWall,
		OffLeftWall, OffRightWall,
		UpLeftWall, UpRightWall,
		DownLeftWall, DownRightWall,
		FallLeft, FallRight, 
		DeadLeft, DeadRight
	}
	
	public void RobotMay(boolean go_right, boolean go_left, boolean go_up_wall, boolean go_down_wall, boolean iair, boolean tground, boolean tupwall){
		may_go_right=go_right;
		may_go_left=go_left;
		may_go_up_wall=go_up_wall;
		may_go_down_wall=go_down_wall;
		in_air=iair;
		touch_ground=tground;
		touch_up_wall=tupwall;
	}
	
	public void UpdateCord(){
		pl_board[0].x=p.x+20;
		pl_board[1].x=p.x+20;
		pl_board[2].x=p.x+80;
		pl_board[3].x=p.x+80;
		pl_board[0].y=p.y;
		pl_board[1].y=p.y+170;
		pl_board[2].y=p.y;
		pl_board[3].y=p.y+170;
		pl_board[4].x=p.x+50;
		pl_board[4].y=p.y+85;
	}
	
	public void TheSound(float d, int s, float vol, float jam, float jamt){
		for_sound+=d;
		if(robotState==RobotState.GoRight || robotState==RobotState.GoLeft){
			if(s==260 && for_sound>=0.465f){
				run_s[for_foot].play(vol);
				move_s[for_foot].play(vol);
				for_sound=0f;
				if(for_foot==0) for_foot=1;
				else if(for_foot==1) for_foot=0;
			}
			else if(s==100 && for_sound>=0.37f){
				run_s[for_foot].play(vol);
				move_s[for_foot].play(vol);
				for_sound=0f;
				if(for_foot==0) for_foot=1;
				else if(for_foot==1) for_foot=0;
			}
		}
		if(robotState==RobotState.GoLeftWallDown || robotState==RobotState.GoLeftWallUp 
		   || robotState==RobotState.GoRightWallDown|| robotState==RobotState.GoRightWallUp){
			if(for_sound>=0.37f){
				run_s[for_foot+2].play(vol);
				move_s[for_foot+2].play(vol);
				for_sound=0f;
				if(for_foot==0) for_foot=1;
				else if(for_foot==1) for_foot=0;
			}
		}
		if(robotState==RobotState.JumpStandLeft || robotState==RobotState.JumpStandRight){
			if(for_jump_s==1){
				for_jump_s=2;
				move_s[1].play(vol);
				for_sound=0f;
			}
			if(for_jump_s==2 && for_sound>=0.5f){
				move_s[0].play(vol);
				for_sound=0f;
				for_jump_s = 3;
			}
			
		}
		
		if(robotState==RobotState.JumpLeft || robotState==RobotState.JumpRight ||
				robotState==RobotState.JumpLeftWallOnRight || robotState==RobotState.JumpRightWallOnLeft){
			if(for_jump_s==1){
				for_jump_s = 3;
				move_s[2].play(vol);
				for_sound=0f;
			}
		}
		
		if(robotState==RobotState.OnLeftWall || robotState==RobotState.OnRightWall){
			if(for_jump_s==1){
				for_jump_s=2;
				move_s[1].play(vol);
				for_sound=0f;
			}
			if(for_jump_s==2 && for_sound>=0.5f){
				move_s[0].play(vol);
				for_sound=0f;
				for_jump_s = 3;
			}
		}
		
		if(robotState==RobotState.OffLeftWall || robotState==RobotState.OffRightWall){
			if(for_jump_s==1){
				for_jump_s=3;
				move_s[3].play(vol);
				for_sound=0f;
			}
		}
		
		if(robotState==RobotState.UpLeftWall || robotState==RobotState.UpRightWall){
			if(for_jump_s==1){
				for_jump_s=2;
				move_s[2].play(vol);
				for_sound=0f;
			}
			if(for_jump_s==2 && for_sound>=0.3f){
				move_s[3].play(vol);
				for_sound=0f;
				for_jump_s = 3;
			}
		}
		if(robotState==RobotState.DownLeftWall || robotState==RobotState.DownRightWall){
			if(for_jump_s==1){
				for_jump_s=2;
				move_s[3].play(vol);
				for_sound=0f;
			}
			if(for_jump_s==2 && for_sound>=0.3f){
				move_s[2].play(vol);
				for_sound=0f;
				for_jump_s = 3;
			}
		}
		
		if(for_jump_s==3 && jam==jamt && for_sound>=0.4f){
			for_jump_s=1;
			for_sound=0f;
			run_s[MathUtils.random(3)].play(vol);
		}
	}

	public void forLevel(float razn, int diff){
		p_gizni=p_gizni+(razn*diff);
		if(p_gizni<=0) p_gizni=0;
	}
	
	
	public void dispose() {

		at.dispose();
		for(int ggg=0;ggg<run_s.length;ggg++){
			run_s[ggg].dispose(); 
		}
		
		for(int ggg=0;ggg<move_s.length;ggg++){
			move_s[ggg].dispose(); 
		}
		
	}
}
