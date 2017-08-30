package com.qvardium.game.project.arwii.one;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;
import com.qvardium.game.project.arwii.one.ByPlayer.RobotState;

public class Glava3 extends LabirintScr {

	// --------для задачи----------------

	Stage q_stage1;
	Image q_kpk1;
	ImageButton q_up1, q_down1, q_yes1;
	Label q_1, q_2z, q_3, q_4z, q_5, q_otv;
	ImageButton q_sel1, q_sel2, q_sel3;
	Image q1_sel;
	int q1_1, q1_2, q1_3;
	long q_tim;

	Stage q_stage2;
	Image q_kpk2;
	ImageButton q2_up1, q2_down1, q2_up2, q2_down2, q_yes2, q_off2;
	Image q2_sel1, q2_sel2;
	Label q2_select_numb1, q2_select_numb2, q_znak, q_zag_numb;
	int[] q2_chisla1, q2_chisla2;
	boolean q2_ok = false;
	// ------------------------------------

	public Glava3(MyGame game) {
		super(game);
		walls = new TextureAtlas(Gdx.files.internal("g3/maplab.pack"));

		if(game_forScreen.curentLvl==28){
			
			bbbg = new TextureAtlas(Gdx.files.internal("epilog/tutor.pack"));
			
			img_system = new ImageTextButton(k_for_str[28],
					new ImageTextButton.ImageTextButtonStyle
					(new TextureRegionDrawable(bbbg.findRegion("bggg")), 
							new TextureRegionDrawable(bbbg.findRegion("bggg")),
							new TextureRegionDrawable(bbbg.findRegion("bggg")),
							gfont));
			
			stage.addActor(img_system);
			trevoga= Gdx.audio.newSound(Gdx.files.internal("g5/sz/g51.mp3"));
		}
		else if(game_forScreen.curentLvl==21){
			trevoga= Gdx.audio.newSound(Gdx.files.internal("g5/sz/g51.mp3"));
			system_btn_on = new Texture(Gdx.files.internal("g5/4.png"));
			system_btn_off = new Texture(Gdx.files.internal("g5/3.png"));
			for_onoff_sys=false;
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
	}

	@Override
	public void bg_music_on() {
		// TODO Auto-generated method stub
		bg_music = Gdx.audio.newMusic(Gdx.files.internal("sounds/3.mp3"));
		bg_music.setLooping(true);
		bg_music.setVolume(game_forScreen.musicVol);
		if (game_forScreen.effectVol>0f)
			bg_music.play();
	}

	@Override
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
					q1_2 = 0;
					q2_select_numb1.setText(Integer.toString(q2_chisla1[q1_1]));
					q2_select_numb2.setText(Integer.toString(q2_chisla2[q1_2]));
					q_znak.setText("");
					q_zag_numb.setText("?");
					Gdx.input.setInputProcessor(stage);
					theQuest = false;
				}
			}
		}
	}

	@Override
	void qStage2() {
		if (!q2_ok) {
			if (q2_up1.isPressed() && TimeUtils.timeSinceMillis(q_tim) > 100) {
				q_tim = TimeUtils.millis();
				q1_1++;
				if (q1_1 > q2_chisla1.length - 1)
					q1_1 = 0;
				q2_select_numb1.setText(Integer.toString(q2_chisla1[q1_1]));
			} else if (q2_down1.isPressed() && TimeUtils.timeSinceMillis(q_tim) > 100) {
				q_tim = TimeUtils.millis();
				q1_1--;
				if (q1_1 < 0)
					q1_1 = q2_chisla1.length - 1;
				q2_select_numb1.setText(Integer.toString(q2_chisla1[q1_1]));
			} else if (q2_down2.isPressed() && TimeUtils.timeSinceMillis(q_tim) > 100) {
				q_tim = TimeUtils.millis();
				q1_2--;
				if (q1_2 < 0)
					q1_2 = q2_chisla2.length - 1;
				q2_select_numb2.setText(Integer.toString(q2_chisla2[q1_2]));
			} else if (q2_up2.isPressed() && TimeUtils.timeSinceMillis(q_tim) > 100) {
				q_tim = TimeUtils.millis();
				q1_2++;
				if (q1_2 > q2_chisla2.length - 1)
					q1_2 = 0;
				q2_select_numb2.setText(Integer.toString(q2_chisla2[q1_2]));
			} else if (q_yes2.isPressed() && TimeUtils.timeSinceMillis(q_tim) > 300) {
				q_tim = TimeUtils.millis();
				if (((q2_chisla2[q1_2] * 10) + q2_chisla1[q1_1]) == temp_q2.otv) {
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
				} else if (((q2_chisla2[q1_2] * 10) + q2_chisla1[q1_1]) != temp_q2.otv) {
					if (((q2_chisla2[q1_2] * 10) + q2_chisla1[q1_1]) > temp_q2.otv)
						q_znak.setText(">");
					else if (((q2_chisla2[q1_2] * 10) + q2_chisla1[q1_1]) < temp_q2.otv)
						q_znak.setText("<");
				}
			} else if (q_off2.isPressed() && TimeUtils.timeSinceMillis(q_tim) > 300) {
				q_tim = TimeUtils.millis();
				lab[temp_q2.x][temp_q2.y] = 22;
				radar_lab[temp_q2.x][temp_q2.y]=1;
				q1_1 = 0;
				q1_2 = 0;
				Gdx.input.setInputProcessor(stage);
				theQuest = false;
			}
		}
	}

	@Override
	void qStage1() {
		if (q_sel1.isPressed()) {
			q_sel1.setVisible(false);
			q_sel2.setVisible(true);
			q_sel3.setVisible(true);
			q1_sel.setVisible(true);
			q1_sel.setPosition(q_sel1.getX(), q_sel1.getY());
			q_up1.setPosition(q_sel1.getX() + 3, camHUD.position.y + 80);
			q_down1.setPosition(q_sel1.getX() + 3, camHUD.position.y - 125);
		} else if (q_sel2.isPressed()) {
			q_sel1.setVisible(true);
			q_sel2.setVisible(false);
			q_sel3.setVisible(true);
			q1_sel.setVisible(true);
			q1_sel.setPosition(q_sel2.getX(), q_sel2.getY());
			q_up1.setPosition(q_sel2.getX() + 3, camHUD.position.y + 80);
			q_down1.setPosition(q_sel2.getX() + 3, camHUD.position.y - 125);
		} else if (q_sel3.isPressed()) {
			q_sel1.setVisible(true);
			q_sel2.setVisible(true);
			q_sel3.setVisible(false);
			q1_sel.setVisible(true);
			q1_sel.setPosition(q_sel3.getX(), q_sel3.getY());
			q_up1.setPosition(q_sel3.getX() + 3, camHUD.position.y + 80);
			q_down1.setPosition(q_sel3.getX() + 3, camHUD.position.y - 125);
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
			} else if (!q_sel3.isVisible()) {
				q1_3++;
				if (q1_3 > temp_q1.base.get(2).length - 1)
					q1_3 = 0;
				q_5.setText(Integer.toString(temp_q1.base.get(2)[q1_3]));
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
			} else if (!q_sel3.isVisible()) {
				q1_3--;
				if (q1_3 < 0)
					q1_3 = temp_q1.base.get(2).length - 1;
				q_5.setText(Integer.toString(temp_q1.base.get(2)[q1_3]));
			}
		} else if (q_yes1.isPressed() && TimeUtils.timeSinceMillis(q_tim) > 1000) {
			if (temp_q1.base.get(0)[q1_1] + temp_q1.base.get(1)[q1_2] + temp_q1.base.get(2)[q1_3] == temp_q1.otv) {
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
			} else if (temp_q1.base.get(0)[q1_1] + temp_q1.base.get(1)[q1_2]
					+ temp_q1.base.get(2)[q1_3] != temp_q1.otv) {
				lab[temp_q1.x][temp_q1.y] = 22;
				radar_lab[temp_q1.x][temp_q1.y]=1;
			}
			q1_1 = 0;
			q1_2 = 0;
			q1_3 = 0;
			Gdx.input.setInputProcessor(stage);
			theQuest = false;
		}
	}

	@Override
	void WherePlayer() {
		if (player.robotState == RobotState.UpLeftWall || player.robotState == RobotState.UpRightWall
				|| player.robotState == RobotState.DownLeftWall || player.robotState == RobotState.DownRightWall)
			;
		else
			pl_x = (int) player.pl_board[4].x / CellSize;
		pl_y = (int) player.pl_board[4].y / CellSize;
		
		if(game_forScreen.curentLvl==21 || clush_game){
			if ((pl_y == hh + hh - 1 && pl_x == finish_lok && for_onoff_sys) || (pl_y==sys_y && pl_x==sys_x && !for_onoff_sys))
				actionButton.setVisible(true);
			else if ((pl_y != hh + hh - 1 && pl_x != finish_lok)|| (pl_y!=sys_y && pl_x!=sys_x))
				actionButton.setVisible(false);
			if(pl_y==sys_y && pl_x==sys_x && actionButton.isPressed()){
				sys_y=0;
				sys_x=0;
				if(clush_game){
					k_str[0] = "����� ������!";
					if(game_forScreen.for_lang==2)k_str[0] = "Output is open!";
					k_msg.setText(k_str[0]);
				}
				for_onoff_sys=true;
				trevoga.play(game_forScreen.effectVol);
			}
		}
		else{
			if (pl_y == hh + hh - 1 && pl_x == finish_lok)
				actionButton.setVisible(true);
			else if (pl_y != hh + hh - 1 && pl_x != finish_lok)
				actionButton.setVisible(false);
		}
		
		if (pl_y == hh + hh - 1 && pl_x == finish_lok && actionButton.isPressed() && TimeUtils.timeSinceMillis(press_time) > 3000) {
			bg_music.stop();
			game_forScreen.curent_time = TimeUtils.timeSinceMillis(game_forScreen.curent_time);
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
			if (actionButton.isVisible() && actionButton.isPressed() && !touchpad.isTouched() && !jumpButton.isPressed()
					&& !radarButton.isPressed()) {

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
						q_5.setText(Integer.toString(temp_q1.base.get(2)[0]));
						q_sel1.setVisible(false);
						q_sel2.setVisible(true);
						q_sel3.setVisible(true);
						q1_sel.setPosition(q_sel1.getX(), q_sel1.getY());
						q1_sel.setVisible(true);
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
							q2_sel1.setVisible(true);
							q2_sel2.setVisible(true);

							theQuest = true;
							break;
						}
					}
				}
			}
		}
	}


	@Override
	public void for_str() {
		k_for_str = new String[31];

		k_for_str[20] = "Управляющий";

		k_for_str[21] = "Этот завод спал долго... " + "\n"
				+ "Найди блок управления"+ "\n"
				+ " и зажги свет " + "\n" +
				"в этом тёмном царстве... @";

		k_for_str[22] = "Отлично, теперь "+ "\n"
				+ "здесь будет жарко. @" + "\n"
				+ "Но ты не переживай, "+ "\n"
				+ "ты способен выдерживать " + "\n" +
				"достаточно большую температуру, " + "\n"
				+ "чтоб ты мог здесь находиться. @";

		k_for_str[24] = "Огромный завод, "+ "\n"
				+ "не правда ли? " + "\n"
				+ "Добытая руда в "+ "\n"
				+ "шахтах отправляется сюда. @" + "\n" +
				"Из неё я получаю "+ "\n"
				+ "различные металлы. " + "\n"
				+ "Ну а из металлов, "+ "\n"
				+ "в прессовочных машинах " + "\n" +
				"изготавливаются различные "+ "\n"
				+ "части для роботов. @";

		k_for_str[25] = "Удивительно, я только "+ "\n"
				+ "что обнаружил в своей " + "\n"
				+ "директории несколько "+ "\n"
				+ "программ, которых я " + "\n" +
				"ни разу не видел "+ "\n"
				+ "и не использовал. @" + "\n"
				+ "Я могу запустить "+ "\n"
				+ "программу по созданию себе " + "\n" +
				"подобного робота управляющего. " + "\n"
				+ "Нет, я этого делать не буду, " + "\n" +
				"я прекрасно справляюсь сам. @";

		k_for_str[27] = "Хмм... А вот эта "+ "\n"
				+ "программа интересна. " + "\n"
				+ "Только называется она <Без имени>. " + "\n" +
				"Странное название. @" + "\n"
				+ "Это последняя созданная "+ "\n"
				+ "программа для меня. " + "\n" +
				"Пожалуй, я попробую её запустить. @";

		k_for_str[28] = "(Системное сообщение)" + "\n"
				+ "Внимание!" + "\n" +
				"Запущена программа <Без имени>!";


		k_for_str[29] = "Работает! Мне интересно, "+ "\n"
				+ "что же сейчас получится?! @" + "\n"
				+ "Хмм... Странно то, "+ "\n"
				+ "что она изготавливает " + "\n"
				+ "какого-то робота только "+ "\n"
				+ "в одном экземпляре... @";

		k_for_str[30] = "Необходимые части для "+ "\n"
				+ "нового робота изготовлены. " + "\n"
				+ "Теперь я их отправлю "+ "\n"
				+ "в сборочный цех. @" + "\n" +
				"Да, да, кроме завода, "+ "\n"
				+ "здесь есть сборочный цех. "
				+ "\n" + "Кстати, он недалеко отсюда, "+ "\n"
				+ "и ты можешь попасть туда. @";

		if(game_forScreen.for_lang==2){

			k_for_str[20] = "Manager";

			k_for_str[21] = "This plant was sleeping" + "\n"
					+ "for a long time..."+ "\n"
					+ "Find control box and switch on" + "\n" +
					"light in this dark kingdom... @";

			k_for_str[22] = "Perfectly, now"+ "\n"
					+ "here will be hot. @" + "\n"
					+ "But you don't worry,"+ "\n"
					+ "you are capable to withstand" + "\n" +
					"rather big temperature " + "\n"
					+ "that you could be here. @";

			k_for_str[24] = "Huge plant,"+ "\n"
					+ "isn't that so?" + "\n"
					+ "The extracted ore"+ "\n"
					+ "in mines is gone here. @" + "\n" +
					"From it I receive"+ "\n"
					+ "different metals." + "\n"
					+ "And from metals,"+ "\n"
					+ "in pressing machines" + "\n" +
					"different parts"+ "\n"
					+ "for robots are made. @";

			k_for_str[25] = "Surprisingly, in the directory"+ "\n"
					+ "I just found several" + "\n"
					+ "programs which"+ "\n"
					+ "I never saw" + "\n" +
					"and I didn't use. @"+ "\n"
					+ "I can launch the program" + "\n"
					+ "for creation of the"+ "\n"
					+ "similar robot of the" + "\n" +
					"managing director." + "\n"
					+ "No, I won't do it," + "\n" +
					"I cope perfectly. @";

			k_for_str[27] = "Hmm... And this program"+ "\n"
					+ "is interesting." + "\n"
					+ "Only it is called <No name>." + "\n" +
					"The strange name. @" + "\n"
					+ "It is the last created"+ "\n"
					+ "program for me." + "\n" +
					"Perhaps, I will try to launch it. @";

			k_for_str[28] = "(System message)" + "\n"
					+ "Attention!" + "\n" +
					"The program <No name> is launched";

			k_for_str[29] = "Works! It is interesting to "+ "\n"
					+ "me what will turn out now?! @" + "\n"
					+ "Hmm... Strangely that it"+ "\n"
					+ "makes some" + "\n"
					+ "robot only"+ "\n"
					+ "in one copy... @";

			k_for_str[30] = "Necessary parts for"+ "\n"
					+ "the new robot are made." + "\n"
					+ "Now I will send"+ "\n"
					+ "them to assembly shop. @" + "\n" +
					"Yes, yes, except plant, there"+ "\n"
					+ "is an assembly shop here."
					+ "\n" + "By the way, it is near from"+ "\n"
					+ "here, and you can get there. @";
		}

		int kbk = game_forScreen.curentLvl;
		if (kbk == 21 || kbk == 22 || kbk == 24 || kbk == 25 || kbk == 27 || kbk == 29 || kbk == 30) {
			k_str = k_for_str[kbk].split("@");
			k_msg.setText(k_str[0]);
			k_msg_name.setText(k_for_str[20]);
			if(k_str.length==1) k_down.setVisible(false);
		} else {
			k_str = new String[1];
			k_str[0] = "Нет сообщений";
			if(game_forScreen.for_lang==2)k_str[0] = "No messages";
			if(MathUtils.random(1)==1) sluch_panel();
			k_msg.setText(k_str[0]);
			k_up.setVisible(false);
			k_down.setVisible(false);
		}
	}

	@Override
	public void for_qqq() {
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
		q_2z = new Label("+", new Label.LabelStyle(q_font, q_font.getColor()));
		q_3 = new Label("2", new Label.LabelStyle(q_font, q_font.getColor()));
		q_4z = new Label("+", new Label.LabelStyle(q_font, q_font.getColor()));
		q_5 = new Label("3", new Label.LabelStyle(q_font, q_font.getColor()));
		q_otv = new Label("=6", new Label.LabelStyle(q_font, q_font.getColor()));

		q_sel1 = new ImageButton(new TextureRegionDrawable(q_atl.findRegion("unselect")),
				new TextureRegionDrawable(q_atl.findRegion("select")));
		q_sel2 = new ImageButton(new TextureRegionDrawable(q_atl.findRegion("unselect")),
				new TextureRegionDrawable(q_atl.findRegion("select")));
		q_sel3 = new ImageButton(new TextureRegionDrawable(q_atl.findRegion("unselect")),
				new TextureRegionDrawable(q_atl.findRegion("select")));

		q1_sel = new Image(q_atl.findRegion("select"));

		q_stage1.addActor(q_kpk1);
		q_stage1.addActor(q_up1);
		q_stage1.addActor(q_down1);
		q_stage1.addActor(q_yes1);
		q_stage1.addActor(q_1);
		q_stage1.addActor(q_2z);
		q_stage1.addActor(q_3);
		q_stage1.addActor(q_4z);
		q_stage1.addActor(q_5);
		q_stage1.addActor(q_otv);
		q_stage1.addActor(q_sel1);
		q_stage1.addActor(q_sel2);
		q_stage1.addActor(q_sel3);
		q_stage1.addActor(q1_sel);

		q_1.setPosition(camHUD.position.x - 320, camHUD.position.y - 60);
		q_2z.setPosition(camHUD.position.x - 215, camHUD.position.y - 60);
		q_3.setPosition(camHUD.position.x - 115, camHUD.position.y - 60);
		q_4z.setPosition(camHUD.position.x - 5, camHUD.position.y - 60);
		q_5.setPosition(camHUD.position.x + 110, camHUD.position.y - 60);
		q_otv.setPosition(camHUD.position.x + 220, camHUD.position.y - 60);
		q_sel1.setPosition(camHUD.position.x - 322, camHUD.position.y - 62);
		q_sel2.setPosition(camHUD.position.x - 112, camHUD.position.y - 62);
		q_sel3.setPosition(camHUD.position.x + 110, camHUD.position.y - 62);
		q_yes1.setPosition(camHUD.position.x + 400, camHUD.position.y + 130);

		q1_1 = 0;
		q1_2 = 0;
		q1_3 = 0;
		// -------------------------------------------------

		// ----------Для ЗАДАЧИ №2-------------------------
		q_stage2 = new Stage(viewportHUD, batch);
		q_kpk2 = new Image(q_atl.findRegion("kpk"));

		q2_sel1 = new Image(q_atl.findRegion("select"));
		q2_sel2 = new Image(q_atl.findRegion("select"));
		q2_up1 = new ImageButton(new TextureRegionDrawable(q_atl.findRegion("up")),
				new TextureRegionDrawable(q_atl.findRegion("up_p")));
		q2_down1 = new ImageButton(new TextureRegionDrawable(q_atl.findRegion("down")),
				new TextureRegionDrawable(q_atl.findRegion("down_p")));
		q2_up2 = new ImageButton(new TextureRegionDrawable(q_atl.findRegion("up")),
				new TextureRegionDrawable(q_atl.findRegion("up_p")));
		q2_down2 = new ImageButton(new TextureRegionDrawable(q_atl.findRegion("down")),
				new TextureRegionDrawable(q_atl.findRegion("down_p")));
		q_yes2 = new ImageButton(new TextureRegionDrawable(q_atl.findRegion("yes")),
				new TextureRegionDrawable(q_atl.findRegion("yes_p")));
		q_off2 = new ImageButton(new TextureRegionDrawable(q_atl.findRegion("off")),
				new TextureRegionDrawable(q_atl.findRegion("off_p")));

		q2_select_numb1 = new Label("0", new Label.LabelStyle(q_font, q_font.getColor()));
		q2_select_numb2 = new Label("0", new Label.LabelStyle(q_font, q_font.getColor()));
		q_znak = new Label("", new Label.LabelStyle(q_font, q_font.getColor()));
		q_zag_numb = new Label("?", new Label.LabelStyle(q_font, q_font.getColor()));

		q2_chisla1 = new int[10];
		q2_chisla2 = new int[4];

		for (int b123 = 0; b123 < q2_chisla1.length; b123++)
			q2_chisla1[b123] = b123;

		for (int b123 = 0; b123 < q2_chisla2.length; b123++)
			q2_chisla2[b123] = b123;

		q_stage2.addActor(q_kpk2);
		q_stage2.addActor(q2_sel1);
		q_stage2.addActor(q2_sel2);
		q_stage2.addActor(q2_up1);
		q_stage2.addActor(q2_down1);
		q_stage2.addActor(q2_up2);
		q_stage2.addActor(q2_down2);
		q_stage2.addActor(q_off2);
		q_stage2.addActor(q_yes2);
		q_stage2.addActor(q2_select_numb1);
		q_stage2.addActor(q2_select_numb2);
		q_stage2.addActor(q_znak);
		q_stage2.addActor(q_zag_numb);

		q2_sel1.setPosition(camHUD.position.x - 125, camHUD.position.y - 62);
		q2_sel2.setPosition(camHUD.position.x - 235, camHUD.position.y - 62);
		q2_up1.setPosition(q2_sel1.getX() + 5, camHUD.position.y + 80);
		q2_down1.setPosition(q2_sel1.getX() + 5, camHUD.position.y - 125);
		q2_up2.setPosition(q2_sel2.getX() + 5, camHUD.position.y + 80);
		q2_down2.setPosition(q2_sel2.getX() + 5, camHUD.position.y - 125);
		q2_select_numb1.setPosition(camHUD.position.x - 125, camHUD.position.y - 60);
		q2_select_numb2.setPosition(camHUD.position.x - 235, camHUD.position.y - 60);
		q_znak.setPosition(camHUD.position.x + 5, camHUD.position.y + 20);
		q_zag_numb.setPosition(camHUD.position.x + 115, camHUD.position.y - 60);

		q_yes2.setPosition(camHUD.position.x - 400, camHUD.position.y + 130);
		q_off2.setPosition(camHUD.position.x - 400, camHUD.position.y - 230);
		// ------------------------------------------------
	}

	@Override
	void ShowMSG() {
		if(game_forScreen.curentLvl==28){
			if(for_start_system_msg && img_system.isPressed() && TimeUtils.timeSinceMillis(press_time) > 1000){
				for_start_system_msg=false;
				img_system.setVisible(for_start_system_msg);
				press_time = TimeUtils.millis();
			}
		}
		else if(game_forScreen.curentLvl==21){
			if(!for_onoff_sys)batch.draw(system_btn_off, sys1x*CellSize, sys1y*CellSize);
			else if(for_onoff_sys)batch.draw(system_btn_on, sys1x*CellSize, sys1y*CellSize);
		}
	}

}
