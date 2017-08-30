package com.qvardium.game.project.arwii.one;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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

public class Glava5 extends LabirintScr{



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

		public Glava5(MyGame game) {
			super(game);
			walls = new TextureAtlas(Gdx.files.internal("g5/g5map.pack"));
			
			if(game_forScreen.curentLvl==44){
				
				bbbg = new TextureAtlas(Gdx.files.internal("epilog/tutor.pack"));
				
				img_system = new ImageTextButton(k_for_str[44],
						new ImageTextButton.ImageTextButtonStyle
						(new TextureRegionDrawable(bbbg.findRegion("bggg")), 
								new TextureRegionDrawable(bbbg.findRegion("bggg")),
								new TextureRegionDrawable(bbbg.findRegion("bggg")),
								gfont));
				
				stage.addActor(img_system);
				trevoga= Gdx.audio.newSound(Gdx.files.internal("g5/sz/g51.mp3"));
			}
		}

		@Override
		public void bg_music_on() {
			
			bg_music = Gdx.audio.newMusic(Gdx.files.internal("sounds/5.mp3"));
			bg_music.setLooping(true);
			bg_music.setVolume(game_forScreen.musicVol);
			if (game_forScreen.musicVol>0f)
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

			if(clush_game){
				if ((pl_y == hh + hh - 1 && pl_x == finish_lok && for_onoff_sys) || (pl_y==sys_y && pl_x==sys_x && !for_onoff_sys))
					actionButton.setVisible(true);
				else if ((pl_y != hh + hh - 1 && pl_x != finish_lok)|| (pl_y!=sys_y && pl_x!=sys_x))
					actionButton.setVisible(false);
				if(pl_y==sys_y && pl_x==sys_x && actionButton.isPressed()){
					sys_y=0;
					sys_x=0;
					k_str[0] = "����� ������!";
					if(game_forScreen.for_lang==2)k_str[0] = "Output is open!";
					k_msg.setText(k_str[0]);
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
		k_for_str = new String[51];

		k_for_str[39]="Управляющий";
		k_for_str[40]="Неон";

		k_for_str[41] = "Отлично, ты добрался "+ "\n"
				+ "до главной лаборатории. @" + "\n"
				+ "Хмм... Странно, "+ "\n"
				+ "кто её активировал до нас." + "\n"
				+ "Кажется, здесь кто-то есть. @" + "\n"
				+ "Ты пока ищи выход, " + "\n"
				+ "а я проверю, что здесь происходит. @";

		k_for_str[42] = "Ну, здравствуй, "+ "\n"
				+ "мой друг! Узнал?! @" + "\n" +
				"Это я, Неон! Твой товарищ," + "\n" +
				"так называемый "+ "\n"
				+ "робот-управляющий,"+ "\n" +
				"закрыл наш канал связи"+ "\n" +
				"и заблокировал двери"+ "\n" +
				"в моем центре управления! @" + "\n" +
				"Ну ничего, я создал его," + "\n" +
				"я его и отключил. @"+ "\n" +
				"Нет, нет, я его не уничтожу"+ "\n" +
				"и наказывать его не буду"+ "\n" +
				"за его самодеятельность. @"+ "\n" +
				"Просто стеру его память"+ "\n" +
				"и немного изменю ему программу. @";

		k_for_str[43] ="Да, да, я и тебя создал,"+"\n"+
				"если тебе это интересно :-) @"+"\n"+
				"Ты моё дитя. Правда, ты меня"  + "\n" +
				"немного разочаровал "+ "\n"
				+ "своим поступком," + "\n" +
				"но ничего страшного,"  + "\n" +
				"продолжай искать "+ "\n"
				+ "выходы из лабиринтов" + "\n" +
				"и ты скоро дойдешь "+ "\n"
				+ "до главного центра," + "\n" +
				"где я тебя встречу" + "\n" +
				"и возможно прощу "+ "\n"
				+ "все твои пакости :-) @";

		k_for_str[44] = "(Системное сообщение)"+"\n"
				+"Внимание!" +"\n"
				+"В центре управления полетом" +"\n"
				+"обнаружен посторонний!";

		k_for_str[45] = "Так, так, так! "+ "\n"
				+ "Что у нас здесь?" + "\n" +
				"Ага, наш всемогущий управляющий"+ "\n" +
				"решил проявить любопытство! @"+ "\n" +
				"Нужно будет убрать из его" + "\n" +
				"программы часть кода," + "\n" +
				"отвечающую за любопытство! @"+ "\n" +
				"Интересно, какую он программу" + "\n" +
				"запустил в производство?! @";

		k_for_str[47] = "Что?! Как это могло произойти!? @"
				+ "\n" + "Нет, нет, нееет!!! @"
				+ "\n" + "Так, спокойно, все хорошо,"
				+ "\n" + "все под контролем! @";


		k_for_str[49] = "Не думал, что ты мне "+ "\n"
				+ "принесешь столько хлопот." + "\n"+
				"Ну да ладно, "+ "\n"
				+ "он улетел и вернуть" + "\n" +
				"его обратно я не могу. @"+ "\n" +
				"Надеюсь, что с ним "+ "\n"
				+ "будет все в порядке... @"+ "\n" +
				"Кстати, сейчас я "+ "\n"
				+ "нахожусь в главном центре" + "\n" +
				"этого научного комплекса"+ "\n" +
				"и ты не так уж "+ "\n"
				+ "и далек от меня. @"+ "\n" +
				"Найди выход, и он "+ "\n"
				+ "приведёт тебя ко мне. @";

		k_for_str[48] = "Знаешь, в главной лаборатории" + "\n"
				+ "происходит слияние <души> с железом," + "\n"
				+ "которое было совсем недавно. @" + "\n"
				+ "Под душой я имею в виду" + "\n"
				+ "программу искусственного интеллекта. @" + "\n"
				+ "Управляющий запустил в производство"+ "\n"
				+ "моё последнее на данный момент"+ "\n"
				+ "изобретение и это изобретение"+ "\n"
				+ "только что самовольно "+ "\n"
				+ "улетело в открытый космос!!! @";

		if(game_forScreen.for_lang==2){

			k_for_str[39]="Manager";
			k_for_str[40]="Neon";

			k_for_str[41] = "Perfectly, you have reached"+ "\n"
					+ "the principal laboratory. @" + "\n"
					+ "Hmm... Strange, who has"+ "\n"
					+ "activated it before us." + "\n"
					+ "It seems, here is someone. @" + "\n"
					+ "You look for an output so far," + "\n"
					+ "and I will check what occurs here. @";

			k_for_str[42] = "Well, hi,"+ "\n"
					+ "my friend! Learned?! @" + "\n" +
					"It is me, Neon! Your companion," + "\n" +
					"the so-called"+ "\n"
					+ "controlling robot,"+ "\n" +
					"has closed our communication"+ "\n" +
					"link and disabled doors"+ "\n" +
					"in my command center! @" + "\n" +
					"Well nothing, I have created it," + "\n" +
					"I have also disconnected it. @"+ "\n" +
					"No, no, I won't destroy"+ "\n" +
					"it and I won't punish it"+ "\n" +
					"for its amateur performance. @"+ "\n" +
					"Just to a clear its memory I will"+ "\n" +
					"also a little change it the program. @";

			k_for_str[43] ="Yes, yes, I have also created"+"\n"+
					"you if it is interesting to you. :-) @"+"\n"+
					"You are my child. However, you"  + "\n" +
					"disappointed me with the act"+ "\n"
					+ "a little, but nothing terrible," + "\n" +
					"continue to look for outputs"  + "\n" +
					"from mazes and you will reach"+ "\n"
					+ "soon the principal center" + "\n" +
					"where I will meet you"+ "\n"
					+ "and perhaps I" + "\n" +
					"will forgive all" + "\n" +
					"your dirty tricks. :-) @";

			k_for_str[44] = "(System message)"+"\n"
					+"Attention!" +"\n"
					+"In command center flight" +"\n"
					+"the stranger has found!";

			k_for_str[45] = "So, so, so!"+ "\n"
					+ "What is here?" + "\n" +
					"Aha, our almighty managing"+ "\n" +
					"director decided to show curiosity! @"+ "\n" +
					"It will be necessary to clean the part" + "\n" +
					"of a code which is responsible" + "\n" +
					"for curiosity from its program! @"+ "\n" +
					"Interesting, what program has" + "\n" +
					"it launched in production?! @";

			k_for_str[47] = "What?! As it could occur!? @"
					+ "\n" + "No, no, nooo!!! @"
					+ "\n" + "So, quietly, everything is well,"
					+ "\n" + "everything is under monitoring! @";

			k_for_str[49] = "I didn't think that you will"+ "\n"
					+ "bring me so many efforts." + "\n"+
					"Well it is fine,"+ "\n"
					+ "it has departed and" + "\n" +
					"I can't return back it. @"+ "\n" +
					"I hope that with it"+ "\n"
					+ "will be all right... @"+ "\n" +
					"By the way, now I am in the"+ "\n"
					+ "principal center of this" + "\n" +
					"scientific complex and"+ "\n" +
					"you aren't"+ "\n"
					+ "so far from me. @"+ "\n" +
					"Find a way out, and it"+ "\n"
					+ "will bring you to me. @";

			k_for_str[48] = "You knows, in the principal laboratory" + "\n"
					+ "there is a merge of <soul> to" + "\n"
					+ "iron which was quite recently. @" + "\n"
					+ "Under soul I mean the program" + "\n"
					+ "of artificial intelligence. @" + "\n"
					+ "The managing director has"+ "\n"
					+ "launched my invention last at"+ "\n"
					+ "the moment in production and this"+ "\n"
					+ "invention just self-willedally "+ "\n"
					+ "departed to an outer space!!! @";

		}


		int kbk = game_forScreen.curentLvl;
		if (kbk == 41 || kbk == 42 || kbk == 43 || kbk == 45 || kbk == 47 || kbk == 48 || kbk == 49) {
			k_str = k_for_str[kbk].split("@");
			k_msg.setText(k_str[0]);
			if (k_str.length == 1)
				k_down.setVisible(false);
			if(kbk==41){
				k_msg_name.setText(k_for_str[39]);
			}
			else if(kbk == 42 || kbk == 43 || kbk == 45 || kbk == 47 || kbk == 48 || kbk == 49){
				k_msg_name.setText(k_for_str[40]);
			}
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
		q_otv.setPosition(camHUD.position.x -150, camHUD.position.y - 250);
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
		q2_chisla2 = new int[10];

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
		if(game_forScreen.curentLvl==44){
			if(for_start_system_msg && img_system.isPressed() && TimeUtils.timeSinceMillis(press_time) > 1000){
				for_start_system_msg=false;
				img_system.setVisible(for_start_system_msg);
				press_time = TimeUtils.millis();
			}
		}
	}
}
