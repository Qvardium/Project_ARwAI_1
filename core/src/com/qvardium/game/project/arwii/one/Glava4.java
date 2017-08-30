package com.qvardium.game.project.arwii.one;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;
import com.qvardium.game.project.arwii.one.ByPlayer.RobotState;

public class Glava4 extends LabirintScr {



	Stage q_stage1;
	Image q_kpk1;
	ImageButton q_up1, q_down1, q_yes1;
	Label q_1, q_3, q_5, q_7, q_otv, q_otv1;
	ImageButton q_sel1, q_sel3, q_sel5, q_sel7;
	Image q1_sel;
	int q1_1, q1_2, q1_3, q1_4;
	long q_tim;
	int for_otv;
	CheckBox q_sel2z,q_sel4z,q_sel6z;

	Stage q_stage2;
	Image q_kpk2;
	ImageButton q2_up1, q2_down1, q2_up2, q2_down2, q_yes2, q_off2;
	Image q2_sel1, q2_sel2;
	Label q2_select_numb1, q2_select_numb2, q_znak, q_zag_numb;
	int[] q2_chisla1, q2_chisla2;
	boolean q2_ok = false;
	// ------------------------------------

	public Glava4(MyGame game) {
		super(game);
		walls = new TextureAtlas(Gdx.files.internal("g4/sbor.pack"));
		
		if(game_forScreen.curentLvl==31 || game_forScreen.curentLvl==32
				|| game_forScreen.curentLvl==33 || game_forScreen.curentLvl==34
				|| game_forScreen.curentLvl==35){
			
			bbbg = new TextureAtlas(Gdx.files.internal("epilog/tutor.pack"));
			trevoga= Gdx.audio.newSound(Gdx.files.internal("g5/sz/g51.mp3"));
			img_system = new ImageTextButton(k_for_str[game_forScreen.curentLvl-10],
					new ImageTextButton.ImageTextButtonStyle
					(new TextureRegionDrawable(bbbg.findRegion("bggg")), 
							new TextureRegionDrawable(bbbg.findRegion("bggg")),
							new TextureRegionDrawable(bbbg.findRegion("bggg")),
							gfont));
			
			if(game_forScreen.curentLvl==35) stage.addActor(img_system);
		}
		if(game_forScreen.curentLvl==31 || game_forScreen.curentLvl==32
				|| game_forScreen.curentLvl==33 || game_forScreen.curentLvl==34){
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
	}

	@Override
	public void bg_music_on() {
		// TODO Auto-generated method stub
		bg_music = Gdx.audio.newMusic(Gdx.files.internal("sounds/4.mp3"));
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
			q_sel3.setVisible(true);			 
			q_sel5.setVisible(true);	
			q_sel7.setVisible(true);
			q1_sel.setVisible(true);
			q1_sel.setPosition(q_sel1.getX(), q_sel1.getY());
			q_up1.setPosition(q_sel1.getX() + 3, camHUD.position.y + 80);
			q_down1.setPosition(q_sel1.getX() + 3, camHUD.position.y - 125);
		}  else if (q_sel3.isPressed()) {
			q_sel1.setVisible(true);			 
			q_sel3.setVisible(false);			 
			q_sel5.setVisible(true);			 
			q_sel7.setVisible(true);
			q1_sel.setVisible(true);
			q1_sel.setPosition(q_sel3.getX(), q_sel3.getY());
			q_up1.setPosition(q_sel3.getX() + 3, camHUD.position.y + 80);
			q_down1.setPosition(q_sel3.getX() + 3, camHUD.position.y - 125);
		} else if (q_sel5.isPressed()) {
			q_sel1.setVisible(true);			
			q_sel3.setVisible(true);			
			q_sel5.setVisible(false);			 
			q_sel7.setVisible(true);
			q1_sel.setVisible(true);
			q1_sel.setPosition(q_sel5.getX(), q_sel5.getY());
			q_up1.setPosition(q_sel5.getX() + 3, camHUD.position.y + 80);
			q_down1.setPosition(q_sel5.getX() + 3, camHUD.position.y - 125);
		} else if (q_sel7.isPressed()) {
			q_sel1.setVisible(true);			 
			q_sel3.setVisible(true);			 
			q_sel5.setVisible(true);		 
			q_sel7.setVisible(false);
			q1_sel.setVisible(true);
			q1_sel.setPosition(q_sel7.getX(), q_sel7.getY());
			q_up1.setPosition(q_sel7.getX() + 3, camHUD.position.y + 80);
			q_down1.setPosition(q_sel7.getX() + 3, camHUD.position.y - 125);
		} else if (q_up1.isPressed() && TimeUtils.timeSinceMillis(q_tim) > 300) {
			q_tim = TimeUtils.millis();
			if (!q_sel1.isVisible()) {
				q1_1++;
				if (q1_1 > temp_q1.base.get(0).length - 1)
					q1_1 = 0;
				q_1.setText(Integer.toString(temp_q1.base.get(0)[q1_1]));
			} else if (!q_sel3.isVisible()) {
				q1_2++;
				if (q1_2 > temp_q1.base.get(1).length - 1)
					q1_2 = 0;
				q_3.setText(Integer.toString(temp_q1.base.get(1)[q1_2]));
			} else if (!q_sel5.isVisible()) {
				q1_3++;
				if (q1_3 > temp_q1.base.get(2).length - 1)
					q1_3 = 0;
				q_5.setText(Integer.toString(temp_q1.base.get(2)[q1_3]));
			} else if (!q_sel7.isVisible()) {
				q1_4++;
				if (q1_4 > temp_q1.base.get(3).length - 1)
					q1_4 = 0;
				q_7.setText(Integer.toString(temp_q1.base.get(3)[q1_4]));
			}
		} else if (q_down1.isPressed() && TimeUtils.timeSinceMillis(q_tim) > 300) {
			q_tim = TimeUtils.millis();
			if (!q_sel1.isVisible()) {
				q1_1--;
				if (q1_1 < 0)
					q1_1 = temp_q1.base.get(0).length - 1;
				q_1.setText(Integer.toString(temp_q1.base.get(0)[q1_1]));
			} else if (!q_sel3.isVisible()) {
				q1_2--;
				if (q1_2 < 0)
					q1_2 = temp_q1.base.get(1).length - 1;
				q_3.setText(Integer.toString(temp_q1.base.get(1)[q1_2]));
			} else if (!q_sel5.isVisible()) {
				q1_3--;
				if (q1_3 < 0)
					q1_3 = temp_q1.base.get(2).length - 1;
				q_5.setText(Integer.toString(temp_q1.base.get(2)[q1_3]));
			} else if (!q_sel7.isVisible()) {
				q1_4--;
				if (q1_4 < 0)
					q1_4 = temp_q1.base.get(3).length - 1;
				q_7.setText(Integer.toString(temp_q1.base.get(3)[q1_4]));
			} 
		} else if (q_yes1.isPressed() && TimeUtils.timeSinceMillis(q_tim) > 1000) {
			for_otv=temp_q1.base.get(0)[q1_1];
			summ(q_sel2z.isChecked(), temp_q1.base.get(1)[q1_2]);
			summ(q_sel4z.isChecked(), temp_q1.base.get(2)[q1_3]);
			summ(q_sel6z.isChecked(), temp_q1.base.get(3)[q1_4]);
			if (for_otv == temp_q1.otv) {
				for_otv=0;
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
			} else if (for_otv != temp_q1.otv) {
				lab[temp_q1.x][temp_q1.y] = 22;
				radar_lab[temp_q1.x][temp_q1.y]=1;
			}
			q1_1 = 0;
			q1_2 = 0;
			q1_3 = 0;
			q1_4=0;
			for_otv=0;
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

		if(game_forScreen.curentLvl==31 || game_forScreen.curentLvl==32
				|| game_forScreen.curentLvl==33 || game_forScreen.curentLvl==34 || clush_game){
			if ((pl_y == hh + hh - 1 && pl_x == finish_lok && for_onoff_sys) 
					|| (pl_y==sys_y && pl_x==sys_x && !for_onoff_sys))
				actionButton.setVisible(true);
			else if ((pl_y != hh + hh - 1 && pl_x != finish_lok)|| (pl_y!=sys_y && pl_x!=sys_x))
				actionButton.setVisible(false);
			if(pl_y==sys_y && pl_x==sys_x && actionButton.isPressed()){
				sys_y=0;
				sys_x=0;
				for_onoff_sys=true;
				for_start_system_msg=true;
				if(clush_game){
					k_str[0] = "����� ������!";
					if(game_forScreen.for_lang==2)k_str[0] = "Output is open!";
					k_msg.setText(k_str[0]);
				}
				trevoga.play(game_forScreen.effectVol);
				stage.addActor(img_system);
				press_time = TimeUtils.millis();
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
						q_7.setText(Integer.toString(temp_q1.base.get(3)[0]));
						q_sel1.setVisible(false);
						q_sel3.setVisible(true);
						q_sel5.setVisible(true);
						q_sel7.setVisible(true);
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
		k_for_str = new String[41];

		k_for_str[20] = "Управляющий";

		k_for_str[21] = "(Системное сообщение)" + "\n" + "Реактор №1 запущен";
		k_for_str[22] = "(Системное сообщение)" + "\n" + "Реактор №2 запущен";
		k_for_str[23] = "(Системное сообщение)" + "\n" + "Реактор №3 запущен";
		k_for_str[24] = "(Системное сообщение)" + "\n" + "Реактор №4 запущен";
		k_for_str[25] = "(Системное сообщение)" + "\n" + "Внимание! "+ "\n"
				+ "Атомная электростанция "+ "\n"
				+ "работает на максимальной мощности! "+ "\n"
				+ "Радиационный фон выше нормы!";

		k_for_str[31] = "Могу я тебя попросить"+ "\n"
				+ " об одной просьбе? @" + "\n"
				+ "Сборочный цех не работает..." + "\n" +
				"Атомная электростанция отключена. @" + "\n"
				+ "Её нужно запустить. "+ "\n"
				+ "Найди блоки управления реакторами." + "\n" +
				"Их всего 4, запусти каждую," + "\n"
				+ "и тогда сборочный цех заработает. @";
		k_for_str[32] = "Отлично, запусти ещё 3 реактора @";

		k_for_str[33] = "Активируй ещё 2 реактора @";

		k_for_str[34] = "Остался последний реактор, " + "\n" +
				"запусти его, и сборочный цех "	+ "\n" +
				"начнёт сборку нового робота. @";

		k_for_str[35] = "Теперь здесь радиационный" + "\n" +
				"фон выше нормы, " + "\n" +
				"но ты не переживай," + "\n" +
				"эта радиация для тебя нипочём @";

		k_for_str[37] = "Из завода готовые "+ "\n"
				+ "детали поставляются" + "\n"
				+ "по конвейеру в сборочный цех," + "\n" +
				"где затем производится "+ "\n"
				+ "сборка всех деталей. @";

		k_for_str[39] = "Наш новый робот "+ "\n"
				+ "почти готов. @" + "\n"
				+ "Хмм, конвейер направил "+ "\n"
				+ "его в какую-то комнату." + "\n" +
				"У меня нет доступа "+ "\n"
				+ "к этой комнате." + "\n"
				+ "Что происходит?! @";

		k_for_str[40] = "Так, наш новый "+ "\n"
				+ "робот готов. @" + "\n"
				+ "Сейчас я его направлю "+ "\n"
				+ "в главную лабораторию." + "\n"
				+ "Это здесь, не далеко, "+ "\n"
				+ "ты найдешь туда дверь. @";

		if(game_forScreen.for_lang==2){

			k_for_str[20] = "Manager";

			k_for_str[21] = "(System message)" + "\n" + "Reactor No. 1 is launched";
			k_for_str[22] = "(System message)" + "\n" + "Reactor No. 2 is launched";
			k_for_str[23] = "(System message)" + "\n" + "Reactor No. 3 is launched";
			k_for_str[24] = "(System message)" + "\n" + "Reactor No. 4 is launched";

			k_for_str[25] = "(System message)" + "\n" + "Attention!"+ "\n"
					+ "The nuclear power plant works"+ "\n"
					+ "at the maximum power!"+ "\n"
					+ "The radiation background is higher than norm!";

			k_for_str[31] = "Can I ask you"+ "\n"
					+ "about one request? @" + "\n"
					+ "The assembly shop doesn't work..." + "\n" +
					"The nuclear power plant"+ "\n"
					+ "has been disconnected. @" + "\n"
					+ "It needs to be launched."+ "\n"
					+ "Find control boxes reactors." + "\n" +
					"There are 4, launch everything, and" + "\n"
					+ "then the assembly shop will earn. @";

			k_for_str[32] = "Perfectly, launch 3 more reactors @";

			k_for_str[33] = "Activate 2 more reactors @";

			k_for_str[34] = "There is the last reactor, launch it," + "\n" +
					"and the assembly shop will"	+ "\n" +
					"begin assembly of the new robot. @";

			k_for_str[35] = "Now here the radiation" + "\n" +
					"background is higher than norm," + "\n" +
					"but you don't worry, this" + "\n" +
					"radiation isn’t dangerous for you @";

			k_for_str[37] = "From plant ready details are"+ "\n"
					+ "delivered on the pipeline" + "\n"
					+ "in assembly shop" + "\n" +
					"where assembly of"+ "\n"
					+ "all details is made. @";

			k_for_str[39] = "Our new robot"+ "\n"
					+ "is almost ready. @" + "\n"
					+ "Hmm, the pipeline sent"+ "\n"
					+ "it to some room." + "\n" +
					"I have no access"+ "\n"
					+ "to this room." + "\n"
					+ "What is going on?! @";

			k_for_str[40] = "So, our new"+ "\n"
					+ "robot is ready. @" + "\n"
					+ "Now I will send it to"+ "\n"
					+ "the principal laboratory." + "\n"
					+ "It is here, not far, you"+ "\n"
					+ "will find a door there. @";

		}

		int kbk = game_forScreen.curentLvl;
		if (kbk == 31 || kbk == 32 || kbk == 33 || kbk == 34 || kbk == 35 || kbk == 37 || kbk == 39 || kbk == 40) {
			k_str = k_for_str[kbk].split("@");
			k_msg.setText(k_str[0]);
			k_msg_name.setText(k_for_str[20]);
			if (k_str.length == 1)
				k_down.setVisible(false);
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

	public void summ(boolean zzz, int cur){
		if(zzz) for_otv=for_otv+cur;
		else for_otv=for_otv-cur;
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
		q_3 = new Label("2", new Label.LabelStyle(q_font, q_font.getColor()));
		q_5 = new Label("3", new Label.LabelStyle(q_font, q_font.getColor()));
		q_7 = new Label("4", new Label.LabelStyle(q_font, q_font.getColor()));
		q_otv = new Label("=10", new Label.LabelStyle(q_font, q_font.getColor()));
		q_otv1 = new Label("=", new Label.LabelStyle(q_font, q_font.getColor()));

		q_sel1 = new ImageButton(new TextureRegionDrawable(q_atl.findRegion("unselect")),
				new TextureRegionDrawable(q_atl.findRegion("select")));
		q_sel2z = new CheckBox("",new CheckBoxStyle(new TextureRegionDrawable(q_atl.findRegion("minus")),
				new TextureRegionDrawable(q_atl.findRegion("plus")), gfont, Color.GREEN));
		q_sel3 = new ImageButton(new TextureRegionDrawable(q_atl.findRegion("unselect")),
				new TextureRegionDrawable(q_atl.findRegion("select")));
		q_sel4z = new CheckBox("",new CheckBoxStyle(new TextureRegionDrawable(q_atl.findRegion("minus")),
				new TextureRegionDrawable(q_atl.findRegion("plus")), gfont, Color.GREEN));
		q_sel5 = new ImageButton(new TextureRegionDrawable(q_atl.findRegion("unselect")),
				new TextureRegionDrawable(q_atl.findRegion("select")));
		q_sel6z = new CheckBox("",new CheckBoxStyle(new TextureRegionDrawable(q_atl.findRegion("minus")),
				new TextureRegionDrawable(q_atl.findRegion("plus")), gfont, Color.GREEN));
		q_sel7 = new ImageButton(new TextureRegionDrawable(q_atl.findRegion("unselect")),
				new TextureRegionDrawable(q_atl.findRegion("select")));

		q1_sel = new Image(q_atl.findRegion("select"));

		q_stage1.addActor(q_kpk1);
		q_stage1.addActor(q_up1);
		q_stage1.addActor(q_down1);
		q_stage1.addActor(q_yes1);
		q_stage1.addActor(q_1);
		q_stage1.addActor(q_3);
		q_stage1.addActor(q_5);
		q_stage1.addActor(q_7);
		q_stage1.addActor(q_otv);
		q_stage1.addActor(q_otv1);
		q_stage1.addActor(q_sel1);
		q_stage1.addActor(q_sel2z);
		q_stage1.addActor(q_sel3);
		q_stage1.addActor(q_sel4z);
		q_stage1.addActor(q_sel5);
		q_stage1.addActor(q_sel6z);
		q_stage1.addActor(q_sel7);
		q_stage1.addActor(q1_sel);

		q_1.setPosition(camHUD.position.x - 453, camHUD.position.y - 60);
		q_3.setPosition(camHUD.position.x - 237, camHUD.position.y - 60);
		q_5.setPosition(camHUD.position.x - 21, camHUD.position.y - 60);
		q_7.setPosition(camHUD.position.x + 195, camHUD.position.y - 60);
		q_otv.setPosition(camHUD.position.x - 150, camHUD.position.y - 250);
		q_otv1.setPosition(camHUD.position.x +350, camHUD.position.y - 60);
		q_sel1.setPosition(camHUD.position.x - 460, camHUD.position.y - 62);
		q_sel2z.setPosition(camHUD.position.x - 352, camHUD.position.y - 62);
		q_sel3.setPosition(camHUD.position.x - 244, camHUD.position.y - 62);
		q_sel4z.setPosition(camHUD.position.x - 136, camHUD.position.y - 62);
		q_sel5.setPosition(camHUD.position.x - 28, camHUD.position.y - 62);
		q_sel6z.setPosition(camHUD.position.x + 80, camHUD.position.y - 62);
		q_sel7.setPosition(camHUD.position.x + 188, camHUD.position.y - 62);
		q_yes1.setPosition(camHUD.position.x + 400, camHUD.position.y + 130);

		q1_1 = 0;
		q1_2 = 0;
		q1_3 = 0;
		q1_4 = 0;

		for_otv=0;
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
		q2_chisla2 = new int[7];

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
		if(game_forScreen.curentLvl==35){
			if(for_start_system_msg && img_system.isPressed() && TimeUtils.timeSinceMillis(press_time) > 1000){
				for_start_system_msg=false;
				img_system.setVisible(for_start_system_msg);
				press_time = TimeUtils.millis();
			}
		}
		else if(game_forScreen.curentLvl==31 || game_forScreen.curentLvl==32
				|| game_forScreen.curentLvl==33 || game_forScreen.curentLvl==34){

			if(!for_onoff_sys)batch.draw(system_btn_off, sys1x*CellSize, sys1y*CellSize);
			else if(for_onoff_sys)batch.draw(system_btn_on, sys1x*CellSize, sys1y*CellSize);

			if(for_start_system_msg	&& img_system.isPressed() && TimeUtils.timeSinceMillis(press_time) > 1000){
				for_start_system_msg=false;
				img_system.setVisible(for_start_system_msg);
				press_time = TimeUtils.millis();
			}
		}
	}

}