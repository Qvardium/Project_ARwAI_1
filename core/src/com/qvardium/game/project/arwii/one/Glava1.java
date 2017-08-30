package com.qvardium.game.project.arwii.one;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;

public class Glava1 extends LabirintScr{

	public Glava1(MyGame game) {
		
		super(game);
		
		if(game_forScreen.curentLvl==10){
		img_system = new ImageTextButton(k_for_str[11],
				new ImageTextButton.ImageTextButtonStyle
				(new TextureRegionDrawable(walls.findRegion("bggg")), 
						new TextureRegionDrawable(walls.findRegion("bggg")),
						new TextureRegionDrawable(walls.findRegion("bggg")),
						gfont));
		trevoga= Gdx.audio.newSound(Gdx.files.internal("g5/sz/g51.mp3"));
		
		stage.addActor(img_system);
		}
		
	}
	
	@Override
	void k_msg_chek() {
		if(game_forScreen.curentLvl==7 && k_curent>=2){
			k_msg_name.setText(k_for_str[2]);
		}
	}

	@Override
	public void for_str(){
		k_for_str = new String[21];

		k_for_str[15] = "(Системное сообщение)";

		if(game_forScreen.dif==2){
			k_for_str[16] = "В среднем уровне "+"\n"
					+ "сложности на радаре "+"\n"
					+ "не видны стены с задачами @"+"\n"+
					"Чтобы их видеть на радаре,"+"\n"
					+ "вам нужно найти "+"\n"
					+ "модуль взлома <?> @"+"\n"+
					"Модули взлома случайным "+"\n"
					+ "образом разбросаны "+"\n"
					+ "по всему лабиринту @";
			k_for_str[17] = "Теперь ваш робот "+"\n"
					+ "не бессмертен, "+"\n"
					+ "у него есть шкала прочности "+"\n"
					+ "в левом верхнем углу @"+"\n"+
					"Робот теряет прочность, "+"\n"
					+ "если падает с большой "+"\n"
					+ "высоты, чем выше тем "+"\n"
					+ "больше он теряет прочности @"+"\n"+
					"Если запас прочности "+"\n"
					+ "израсходован, то робот "+"\n"
					+ "погибнет и вам придётся "+"\n"
					+ "проходить данный уровень "+"\n"
					+ "с начала @";
		}
		else if(game_forScreen.dif==3){
			k_for_str[16] = "На сложных уровнях "+"\n"
					+ "лабиринт на радаре скрыт @"+"\n"+
					"Изначально уже отмечено "+"\n"
					+ "место нахождения выхода @"+"\n"+
					"В тех местах где вы уже "+"\n"
					+ "были лабиринт будет открытым @";
			k_for_str[17] = "Так же на сложных уровнях "+"\n"
					+ "как и на средних, "+"\n"
					+ "есть запас прочности и "+"\n"
					+ "возможность найти модуль "+"\n"
					+ "открывающий стены, "+"\n"
					+ "которые можно убрать @"+"\n"+
					"Теперь в лабиринте вы "+"\n"
					+ "можете найти модуль "+"\n"
					+ "взлома <О>, который "+"\n"
					+ "открывает весь лабиринт @"+"\n"+
					"Как и модуль взлома <?>, "+"\n"
					+ "так и <О> разбросаны по "+"\n"
					+ "всему лабиринту случайным образом @";
		}

		k_for_str[1]="Неизвестный";
		k_for_str[2]="Неон";

		k_for_str[3]=
				"Привет :-)@"+"\n"+
						"Как у тебя дела?@"+"\n"+
						"Судя по моим данным, "+"\n"
						+ "все твои системы "+"\n"+
						"находятся в отличном состоянии.@"+"\n"+
						"Я знаю, что ты "+"\n"
						+ "не можешь мне ответить,"+"\n"+
						"но ты прекрасно "+"\n"
						+ "понимаешь человеческую"+"\n"+
						"речь и ты осознаешь то,"+"\n"
						+ "что я говорю.@"+"\n"+
						"А сейчас найди выход"+"\n"
						+ "из этого лабиринта"+"\n"+
						"и не забудь выйти из него :-)@";

		k_for_str[4]=
				"И снова привет! :-) @"+"\n"+
						"Кстати, поздравляю"+"\n"
						+ "тебя с успешным "+"\n"+
						"прохождением 3-х уровней! @"+"\n"+
						"Вот ещё один лабиринт."+"\n"
						+ "Он чуть-чуть больше "+"\n"+
						"предыдущего, но я думаю"+"\n"
						+ "ты освоишься "+"\n"+
						"и найдешь из него выход. @"+"\n"+
						"Удачи! ;-) @";

		k_for_str[5]=
				"Слушай, я, наверное,"+"\n"
						+ "не буду каждый раз "+"\n"+
						"приветствовать тебя, "+"\n"
						+ "думаю ты не обидишься? "+"\n"+
						"Думаю нет :-) @"+"\n"+
						"В общем, я так же"+"\n"
						+ "не вижу смысла говорить "+"\n"+
						"каждый раз, что "+"\n"
						+ "тебе нужно делать. @"+"\n"+
						"Думаю, ты уже понял, что нужно "+"\n"+
						"просто проходить лабиринт ;-) @"+"\n"+
						"Вперед! :-) @";

		k_for_str[7]=
				"Ну как, получается? Молодец! @"+"\n"+
						"Кстати, я ведь "+"\n"
						+ "забыл представиться... @"+"\n"+
						"Меня зовут Неон, "+"\n"
						+ "и я твой друг :-) @"+"\n"+
						"Да, друзья должны "+"\n"
						+ "помогать друг другу, "+"\n"+
						"но, к сожалению, "+"\n"
						+ "я не могу помогать тебе "+"\n"+
						"проходить лабиринты. @"+"\n"+
						"Ты помогаешь мне, проходя их :-) @"+"\n"+
						"После того, как ты "+"\n"
						+ "пройдешь все лабиринты, "+"\n"+
						"я помогу тебе ;-) @";

		k_for_str[10]=
				"Отлично! Ещё 3 лабиринта "+"\n"
						+ "и ты у цели! @"+"\n"+
						"Знаешь, эту лабораторию "+"\n"
						+ "я строил 2 года. "+"\n"+
						"В ней я ставил "+"\n"
						+ "различные эксперименты "+"\n"+
						"по взаимодействию искусственного "+"\n"+
						"интеллекта с окружающей его средой. "+"\n"+
						"А ещё здесь недалеко есть шахты. @"+"\n"+
						"Хотя, я думаю, "+"\n"
						+ "тебе это неинтересно. @"+"\n"+
						"В общем, продолжай в том же духе, "+"\n"+
						"а я пойду, "+"\n"
						+ "поем и вздремну немного :-) @"+"\n"+
						"Не заблудись! :-) @";

		k_for_str[11]="(Системное сообщение) "+"\n"+
				"Внимание! Обнаружена ошибка в лабиринте! "+"\n"+
				"Ошибка помечена красным цветом на радаре. "+"\n"+
				"Исправьте её.";

		k_for_str[13]=
				"Ого! Да ты монстр! "+"\n"
						+ "Я в хорошем смысле :-) @"+"\n"+
						"Молодец! Отлично! Прекрасно! @"+"\n"+
						"А теперь последнее "+"\n"
						+ "для тебя задание! @"+"\n"+
						"Тут есть специальная кнопка, "+"\n"+
						"найди и нажми на неё. "+"\n"+
						"После чего тебе "+"\n"
						+ "откроется дверь, "+"\n"+
						"которая приведёт "+"\n"
						+ "тебя ко мне ;-) @";

		if(game_forScreen.for_lang==2){

			k_for_str[15] = "(System message)";

			if(game_forScreen.dif==2){
				k_for_str[16] = "On medium level"+"\n"
						+ "on the radar walls"+"\n"
						+ "with tasks aren't visible @"+"\n"+
						"That to see them on the"+"\n"
						+ "radar, you need to find"+"\n"
						+ "the cracking module <?> @"+"\n"+
						"Modules of cracking"+"\n"
						+ "are in a random way"+"\n"
						+ "scattered on all maze @";

				k_for_str[17] = "Now your robot isn't"+"\n"
						+ "immortal, it has"+"\n"
						+ "durability scale in"+"\n"
						+ "the upper left corner @"+"\n"+
						"The robot loses durability"+"\n"
						+ "if falls from big height,"+"\n"
						+ "the above the more"+"\n"
						+ "it loses durabilities"+"\n"+
						"If margin of safety"+"\n"
						+ "is spent, then the robot"+"\n"
						+ "will perish and you"+"\n"
						+ "should pass this level"+"\n"
						+ "since the beginning @";
			}
			else if(game_forScreen.dif==3){
				k_for_str[16] = "At the hard levels the"+"\n"
						+ "maze on the radar is hidden @"+"\n"+
						"The location of an output"+"\n"
						+ "is initially already marked @"+"\n"+
						"In those places where you were"+"\n"
						+ "already a maze will be open @";

				k_for_str[17] = "Also at the hard levels"+"\n"
						+ "as well as on medium,"+"\n"
						+ "there are a margin of safety"+"\n"
						+ "and an opportunity to find"+"\n"
						+ "the module opening walls"+"\n"
						+ "which can be cleaned @"+"\n"+
						"Now you can find the"+"\n"
						+ "cracking module in"+"\n"
						+ "a maze <O> which"+"\n"
						+ "opens all maze @"+"\n"+
						"As well as cracking module <?>,"+"\n"
						+ "and <O> are scattered on"+"\n"
						+ "all maze in a random way @";
			}

			k_for_str[1]="Unknown";
			k_for_str[2]="Neon";

			k_for_str[3]=
					"Hi! :-)@"+"\n"+
							"How are you?@"+"\n"+
							"Judging by my data,"+"\n"
							+ "all your systems"+"\n"+
							"are in an excellent status.@"+"\n"+
							"I know that you"+"\n"
							+ "can't answer me,"+"\n"+
							"but you understand"+"\n"
							+ "the human speech perfectly"+"\n"+
							"and you realize"+"\n"
							+ "what I tell.@"+"\n"+
							"And now find a way out"+"\n"
							+ "of this maze and don't"+"\n"+
							"forget to quit it :-)@";


			k_for_str[4]=
					"And hi again! :-) @"+"\n"+
							"By the way, I congratulate"+"\n"
							+ "you with a successful"+"\n"+
							"passing of 3 levels! @"+"\n"+
							"Here is one more maze."+"\n"
							+ "It is slightly more previous,"+"\n"+
							"but I think you will be"+"\n"
							+ "mastered and you will"+"\n"+
							"find a way out of it. @"+"\n"+
							"Good luck! ;-) @";

			k_for_str[5]=
					"Listen, I, probably,"+"\n"
							+ "won't welcome you "+"\n"+
							"every time, I think you"+"\n"
							+ "won't take offense?"+"\n"+
							"I think I’m not :-) @"+"\n"+
							"Generally, I also don't"+"\n"
							+ "see sense to tell every"+"\n"+
							"time that you"+"\n"
							+ "need to do. @"+"\n"+
							"I think, you have already "+"\n"+
							"understood that it is necessary"+"\n"
							+ "just to pass a maze ;-) @"+"\n"+
							"Forward! :-) @";

			k_for_str[7]=
					"Well as, it turns out? Well done! @"+"\n"+
							"By the way,"+"\n"
							+ "I forgot to provide... @"+"\n"+
							"My name is Neon,"+"\n"
							+ "and I am your friend :-) @"+"\n"+
							"Yes, friends should"+"\n"
							+ "help each other,"+"\n"+
							"but, unfortunately,"+"\n"
							+ "I can't help you"+"\n"+
							"to pass mazes. @"+"\n"+
							"You help me, passing them :-) @"+"\n"+
							"After you pass"+"\n"
							+ "all mazes,"+"\n"+
							"I will help you ;-) @";

			k_for_str[10]=
					"It's cool! 3 more mazes"+"\n"
							+ "and you are at the purpose! @"+"\n"+
							"You know, I have been building"+"\n"
							+ "this laboratory for 2 years."+"\n"+
							"In it I put different"+"\n"
							+ "experiments on interaction"+"\n"+
							"of artificial intelligence"+"\n"+
							"with the environment surrounding it."+"\n"+
							"And near here there are mines. @"+"\n"+
							"Though, I think,"+"\n"
							+ "it is uninteresting to you. @"+"\n"+
							"Generally, continue in the same spirit,"+"\n"+
							"and I will go, I will eat"+"\n"
							+ "and I take a nap a little :-) @"+"\n"+
							"Don't get lost! :-) @";

			k_for_str[11]="(System message)"+"\n"+
					"Attention! The error in a maze is noticed!"+"\n"+
					"The error is marked in the red"+"\n"+
					"color on a radar. Correct it.";

			k_for_str[13]=
					"Wow! You are a monster!"+"\n"
							+ "I am in a good sense :-) @"+"\n"+
							"Well done! It's cool! Great! @"+"\n"+
							"And now last"+"\n"
							+ "job for you! @"+"\n"+
							"Here is a special button,"+"\n"+
							"find and click it."+"\n"+
							"Then to you the door"+"\n"
							+ "which will"+"\n"+
							"bring you to"+"\n"
							+ "me will open ;-) @";
		}

		int kbk = game_forScreen.curentLvl;
		if(kbk==7 || kbk==5 || kbk==4 || kbk==3 || kbk==13 || kbk==10){
			k_str = k_for_str[kbk].split("@");
			k_msg.setText(k_str[0]);
			if(k_str.length==1) k_down.setVisible(false);
			if(kbk==7 || kbk==5 || kbk==4 || kbk==3) k_msg_name.setText(k_for_str[1]);
			else if(kbk==13 || kbk==10) k_msg_name.setText(k_for_str[2]);
		}
		else if((game_forScreen.dif==2 || game_forScreen.dif==3) && (kbk==1 || kbk==2)){
			k_msg_name.setText(k_for_str[15]);
			k_str = k_for_str[kbk+15].split("@");
			k_msg.setText(k_str[0]);
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

	@Override
	void ShowMSG() {
		if(game_forScreen.curentLvl==10){
			if(for_start_system_msg && img_system.isPressed() && TimeUtils.timeSinceMillis(press_time) > 1000){			
					for_start_system_msg=false;
					img_system.setVisible(for_start_system_msg);
					press_time = TimeUtils.millis();
			}
		}
	}
}
