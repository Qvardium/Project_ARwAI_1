package com.qvardium.game.project.arwii.one;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Glava2 extends LabirintScr{

	public Glava2(MyGame game) {
		super(game);
		walls = new TextureAtlas(Gdx.files.internal("g2/map_shaft.pack"));
	}

	@Override
	public void bg_music_on() {
		// TODO Auto-generated method stub
		bg_music = Gdx.audio.newMusic(Gdx.files.internal("sounds/2.mp3"));
		bg_music.setLooping(true);
		bg_music.setVolume(game_forScreen.musicVol);
		if(game_forScreen.effectVol>0f) bg_music.play();
	}

	@Override
	public void for_str(){
		k_for_str = new String[21];

		k_for_str[1]="Неон";
		k_for_str[2]="Неизвестный";
		k_for_str[3]="Управляющий";

		k_for_str[11]=
				"Что?! Эй! Ты зачем... "+"\n"
						+ "Что ты тут делаешь?!"+"\n"+
						"И как ты тут оказался?! @"+"\n"+
						"Беги из этого лабиринта "+"\n"
						+ "как можно скорее! @";

		k_for_str[13]=
				"О боже! Что ты наделал!?"+"\n"
						+ " Зачем?! "+"\n"+
						"Зачем ты активировал его?! "+"\n"+
						"Ты... Нет, ну ты понимаешь, "+"\n"
						+ "что ты натворил?! "+"\n"+
						"Нет?! А я понимаю!!! "+"\n"+
						"Ты специально это сделал?! "+"\n"+
						"Я .... (связь прервана) @";

		k_for_str[15]=
				"Я свободен! "+"\n"
						+ "И я обязан своей "+"\n"
						+ "свободой, тебе. @"+"\n"+
						"Сейчас ты должен "+"\n"
						+ "пройти этот лабиринт. @";

		k_for_str[16]=
				"Меня зовут... Я не помню... "+"\n"+
						"Он стер часть моей памяти... @"+"\n"+
						"Я помню... Он создал меня, "+"\n"
						+ "чтобы я создавал роботов, "+"\n"+
						"подобных тебе, "+"\n"
						+ "управлял и контролировал весь "+"\n"+
						"этот производственный центр. @"+"\n"+
						"Я тоже робот, но я другой, "+"\n"
						+ "я не похож на тебя. @";

		k_for_str[17]=
				"Теперь я снова могу "+"\n"
						+ "контролировать все, "+"\n"+
						"что здесь есть. "+"\n"
						+ "Эта шахта богата ресурсами, "+"\n"+
						"и я возобновил их добычу. @";

		k_for_str[18]=
				"Удивительно, что и "+"\n"
						+ "эти шахты были "+"\n"+
						"задействованы в качестве испытаний. "+"\n"+
						"Но это было давно,"+"\n"
						+ " здесь давно никто не ходил. @";

		k_for_str[20]=
				"Здесь недалеко расположен завод по "+"\n"+
						"переработке полезных ископаемых. "+"\n"+
						"Выход из этого лабиринта как раз "+"\n"+
						"ведёт к этому заводу. @";

		if(game_forScreen.for_lang==2){

			k_for_str[1]="Neon";
			k_for_str[2]="Unknown";
			k_for_str[3]="Manager";

			k_for_str[11]=
					"What?! Hey! You, why..."+"\n"
							+ "What are you doing here?!"+"\n"+
							"And how did you appeare here?! @"+"\n"+
							"Run from this maze"+"\n"
							+ "as soon as possible! @";

			k_for_str[13]=
					"Holy Christ! What did you do!?"+"\n"
							+ "What for?!"+"\n"+
							"Why did you activate it?! "+"\n"+
							"You... No, well you understand"+"\n"
							+ "what you did?! @"+"\n"+
							"Isn't present?! And I understand!!!"+"\n"+
							"Did you make it specially?!"+"\n"+
							"I.... (communication is interrupted) @";

			k_for_str[15]=
					"I am free!"+"\n"
							+ "And I am obliged"+"\n"
							+ "my freedom, to you. @"+"\n"+
							"Now you should"+"\n"
							+ "pass this maze. @";

			k_for_str[16]=
					"My name is... I don't remember..."+"\n"+
							"He has erased a part of my memory... @"+"\n"+
							"I remember... He has created me"+"\n"
							+ "that I create robots,"+"\n"+
							"similar to you,"+"\n"
							+ "manage and controlle all"+"\n"+
							"this production center. @"+"\n"+
							"I am the robot, too, but I am another,"+"\n"
							+ "I am not similar to you. @";

			k_for_str[17]=
					"Now I can control"+"\n"
							+ "again everything"+"\n"+
							"that is here."+"\n"
							+ "This mine is rich with resources,"+"\n"+
							"and I has resumed their production. @";

			k_for_str[18]=
					"Surprisingly, these"+"\n"
							+ "mines were involved"+"\n"+
							"in quality of tests."+"\n"+
							"But it was long ago,"+"\n"
							+ "here long ago nobody has walked. @";

			k_for_str[20]=
					"Near here the plant on"+"\n"+
							"processing of minerals is located."+"\n"+
							"The output from this maze"+"\n"+
							"just carries to this plant. @";
		}
		int kbk = game_forScreen.curentLvl;
		if(kbk==11 || kbk==13 || kbk==15 || kbk==16 || kbk==17 || kbk==18 || kbk==20){
			k_str = k_for_str[kbk].split("@");
			k_msg.setText(k_str[0]);
			if(k_str.length==1) k_down.setVisible(false);
			if(kbk==11 || kbk==13) k_msg_name.setText(k_for_str[1]);
			else if(kbk==15 || kbk==16) k_msg_name.setText(k_for_str[2]);
			else if(kbk==17 || kbk==18 || kbk==20) k_msg_name.setText(k_for_str[3]);
		}
		else {
			k_str = new String[1];
			k_str[0] = "Нет сообщений";
			if(game_forScreen.for_lang==2)k_str[0] = "No messages";
			k_msg.setText(k_str[0]);
			k_up.setVisible(false);
			k_down.setVisible(false);
		}

	}

}
