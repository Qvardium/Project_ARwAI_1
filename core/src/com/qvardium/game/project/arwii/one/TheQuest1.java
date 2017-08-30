package com.qvardium.game.project.arwii.one;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class TheQuest1 {

	int x,y,lvl,otv=0;
	int dif;
	
	
	Array<int[]> base;
	
	public TheQuest1(int x, int y, int lvl){
		this.x=x;
		this.y=y;
		this.lvl=lvl;
		
		if(lvl>0 && lvl<=20)dif=2;
		else if(lvl>20 && lvl<=30)dif=3;
		else if(lvl>30 && lvl<=50)dif=4;
		//else if(lvl>40 && lvl<=50)dif=5;
		
		base = new Array<int[]>();
		
		Gen(dif);
	}
	
	void Gen(int dif2){

		for(int n=1;n<=dif2;n++){
			int[] temp = new int[dif2*4];
			int iii = 0;
			for(int k=0;k<dif2*4;k++){
				temp[k]=MathUtils.random(8)+1;
			}
			for(int p1=1;p1<10;p1++){
				int p2=0;
				for(int p3=0;p3<temp.length;p3++){
					if(temp[p3]==p1) p2++;
					if(p2==2) {temp[p3]=0; p2=1;}
				}
			}
			for(int p1=0; p1<temp.length;p1++)
				if(temp[p1]!=0) iii++;
			int[] chisla = new int[iii];
			int p2=0;
			for(int p1=0;p1<temp.length;p1++){
				if(temp[p1]!=0) {chisla[p2]=temp[p1]; p2++;}
			}
			base.add(chisla);
		}
		for(int n=0;n<dif2;n++){
			int[] tmp = base.get(n);
			int kk=tmp.length-1;
			if(dif2==2 || dif2==3) otv=otv+tmp[MathUtils.random(kk)];
			else if(dif2==4) {
				int bb=MathUtils.random(1);
				if(bb==0) otv=otv+tmp[MathUtils.random(kk)];
				else if(bb==1) otv=otv-tmp[MathUtils.random(kk)];
			}
			/*else if(dif2==5) {
				int bb=MathUtils.random(2);
				if(bb==0) otv=otv+tmp[MathUtils.random(kk)];
				else if(bb==1) otv=otv-tmp[MathUtils.random(kk)];
				else if(bb==2) otv=otv*tmp[MathUtils.random(kk)];
			}*/
		}
	}
	
}
