package com.qvardium.game.project.arwii.one;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MyGame extends Game {
	SpriteBatch batch;
	OrthographicCamera cam; 
	Viewport viewport;
	
	boolean for_reset=false, the_hard, the_medium;
	float musicVol, effectVol;
	int curentLvl;
	boolean trueLvl;
	long curent_time;
	int dif;
	int max_lvl_1, max_lvl_2, max_lvl_3;
	int for_lang;
	
	long score1,score2,score3;
	
	boolean the_first_start;
	
	private Preferences preferences;
	
	public MyGame(Integer for_help){
		for_lang=for_help;
		score1=0;
		score2=0;
		score3=0;
	}
	
	protected Preferences getPrefs()
    {
		if(preferences==null){
	        preferences = Gdx.app.getPreferences("ProjectARwAIOne");
	     }
		if(preferences==null){
			Gdx.app.log("info","null preferences");
		}
	   return preferences;
		
    }
	//---------------------------------------------------------------------
	public void saveOch1(int koloch, String kk){
		getPrefs().putInteger(kk, koloch );
	}
	public int loadOch1(String kk){
		return getPrefs().getInteger(kk,0);
	}
	public void saveOch2(int koloch, String kk){
		getPrefs().putInteger(kk, koloch );
	}
	public int loadOch2(String kk){
		return getPrefs().getInteger(kk,0);
	}
	public void saveOch3(int koloch, String kk){
		getPrefs().putInteger(kk, koloch );
	}
	public int loadOch3(String kk){
		return getPrefs().getInteger(kk,0);
	}
	//---------------------------------------------------------------------
	public void saveHardLvl(){
		getPrefs().putBoolean( "hardlvl", the_hard );
	}
	public boolean loadHardLvl(){
		return getPrefs().getBoolean("hardlvl",false);
	}
	public void saveMediumLvl(){
		getPrefs().putBoolean( "mediumlvl", the_medium );
	}
	public boolean loadMediumLvl(){
		return getPrefs().getBoolean("mediumlvl",false);
	}
	
	public void saveMusicVolume(){
		getPrefs().putFloat( "musicvolume", musicVol );
	}
	public float loadMusicVolume( ){
		return getPrefs().getFloat("musicvolume",1.0f);
	}
	
	public void saveEffectVolume(){
		getPrefs().putFloat( "effectvolume", effectVol );
	}
	public float loadEffectVolume( ){
		return getPrefs().getFloat("effectvolume",1.0f);
	}
	
	public void saveLvl(){
		getPrefs().putInteger("lvl", curentLvl );
	}
	public int loadLvl( ){
		return getPrefs().getInteger("lvl",1);
	}
	
	public void saveTrueLvl(){
		getPrefs().putBoolean( "truelvl", trueLvl );
	}
	public boolean loadTrueLvl(){
		return getPrefs().getBoolean("truelvl",true);
	}
	
	public void saveFirstStart(){
		getPrefs().putBoolean( "fStar", the_first_start );
	}
	public boolean loadFirstStart(){
		return getPrefs().getBoolean("fStar",true);
	}
	
	public void saveLang(){
		getPrefs().putInteger("langu", for_lang );
	}
	public int loadLang(){
		return getPrefs().getInteger("langu");
	}
	
	public void saveMaxLvl1(){
		getPrefs().putInteger("maxlvl1", max_lvl_1 );
	}
	public int loadMaxLvl1( ){
		return getPrefs().getInteger("maxlvl1",1);
	}
	
	public void saveMaxLvl2(){
		getPrefs().putInteger("maxlvl2", max_lvl_2 );
	}
	public int loadMaxLvl2( ){
		return getPrefs().getInteger("maxlvl2",1);
	}
	
	public void saveMaxLvl3(){
		getPrefs().putInteger("maxlvl3", max_lvl_3 );
	}
	public int loadMaxLvl3( ){
		return getPrefs().getInteger("maxlvl3",1);
	}
	
	public void saveDif(){
		getPrefs().putInteger("dif", dif );
	}
	public int loadDif( ){
		return getPrefs().getInteger("dif",1);
	}
	
	public void flushPref(){
		 getPrefs().flush();
	}
	
	public void saveAll(){
		Gdx.app.log("info","saving preferences");
		saveEffectVolume();
		saveMusicVolume();
		saveLang();
		flushPref();
	}
	public void saveTheLvl(){
		Gdx.app.log("info","saving LVL "+curentLvl);
		saveLvl();
		saveTrueLvl();
		saveDif();
		saveHardLvl();
		saveMediumLvl();
		
		if(dif==1) saveMaxLvl1();
		else if(dif==2) saveMaxLvl2();
		else if (dif==3) saveMaxLvl3();
		flushPref();
	}
	
	public void saveStart(){
		saveFirstStart();
		flushPref();
	}
	
	public void saveScore(int koloch, String kk){
		if(dif==1) saveOch1(koloch, kk);
		else if(dif==2) saveOch2(koloch, kk);
		else if(dif==3) saveOch3(koloch, kk);
		flushPref();
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		cam = new OrthographicCamera(); 
		viewport = new FitViewport(1280, 768, cam);
		
		the_hard = loadHardLvl();
		the_medium = loadMediumLvl();
		musicVol=loadMusicVolume();
		effectVol=loadEffectVolume();
		curentLvl=loadLvl();
		trueLvl=loadTrueLvl();
		
		dif = loadDif();
		/*
		max_lvl_3 = 50;
		max_lvl_2 = 50;
		max_lvl_1 = 50;
		trueLvl = true;
		*/
		
		//trueLvl = true;
		
		max_lvl_3 = loadMaxLvl3();
		max_lvl_2 = loadMaxLvl2();
		max_lvl_1 = loadMaxLvl1();
		
		for(int i=1;i<50;i++){
			score1+=loadOch1("score1"+i);
			score2+=loadOch1("score2"+i);
			score3+=loadOch1("score3"+i);
		}
		
		the_first_start = loadFirstStart();
		
		if(!the_first_start)for_lang = loadLang();
		
		//setScreen(new MenuScreen(this));
		//setScreen(new BadEnd(this));
		Gdx.input.setCatchBackKey(true);
		
		if(for_lang==1 && the_first_start)setScreen(new Intro(this));
		else setScreen(new MenuScreen(this));
		
	}

	@Override
	public void render () {
		super.render();
		
	}
	
	@Override
	public void dispose(){
		super.dispose();
		
		batch.dispose();
		
		
	}
}
