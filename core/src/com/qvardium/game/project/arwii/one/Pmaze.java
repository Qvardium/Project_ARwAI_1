package com.qvardium.game.project.arwii.one;

import com.badlogic.gdx.math.MathUtils;

public class Pmaze {

	CellMaze[][] mazeP;
	int ww,hh;
	public int start_loc, finish_lok;

	public Pmaze(int w, int h){
		ww=w;
		hh=h;
		mazeP = new CellMaze[w][h];
	}

	class CellMaze{
		boolean lw,uw;
		char ch;

		CellMaze(boolean lw,boolean uw, char a){
			this.lw=lw;
			this.uw=uw;
			this.ch=a;
		}
	}

	public void GeneratMaze(){
		int x,y,xl,yl,k,i=1;
		int[] xr = new int[ww*hh+1];
		int[] yr = new int[ww*hh+1];
		final int[] dx = {1,0,-1,0};
		final int[] dy = {0,-1,0,1};
		boolean IsTrue=true;

		for(x=0;x<ww;x++)
			for(y=0;y<hh;y++)
				mazeP[x][y]=new CellMaze(true,true,'o');

		x=MathUtils.random(ww-1);
		y=MathUtils.random(hh-1);
		mazeP[x][y].ch='i';

		for (i=0;i<4;i++){
			xl=x+dx[i];
			yl=y+dy[i];
			if (xl>=0 && yl>=0 && xl<ww && yl<hh) mazeP[xl][yl].ch='b';
		}

		while (IsTrue){

			i=0;
			for(x=0;x<ww;x++)
				for(y=0;y<hh;y++){
					if (mazeP[x][y].ch=='b' && i==0) {i=1; xr[i]=x; yr[i]=y;}
					else if (mazeP[x][y].ch=='b' && i>=1) {i++; xr[i]=x; yr[i]=y;}

				}
			k=0;
			if(i==0) break;
			i=MathUtils.random(1,i);
			x=xr[i]; y=yr[i];
			for(i=0;i<4;i++){
				xl=x+dx[i];
				yl=y+dy[i];
				if (xl>=0 && yl>=0 && xl<ww && yl<hh && mazeP[xl][yl].ch=='i'){
					k++;
					xr[k]=dx[i];
					yr[k]=dy[i];
				}
			}

			i=MathUtils.random(1,k);
			mazeP[x][y].ch='i';
			if (xr[i]==-1) mazeP[x][y].lw=false;
			else if (xr[i]==1) mazeP[x+1][y].lw=false;
			else if (yr[i]==-1) mazeP[x][y].uw=false;
			else if (yr[i]==1) mazeP[x][y+1].uw=false;

			for(i=0;i<4;i++){
				xl=x+dx[i];
				yl=y+dy[i];
				if (xl>=0 && yl>=0 && xl<ww && yl<hh){
					if (mazeP[xl][yl].ch=='o') mazeP[xl][yl].ch='b';
				}
			}


		}

	}

	public byte[][] ConvMaze(byte[][] lab){

		int x,y,x1,y1;
		byte[][] temp_lab = new byte[ww+ww+1][hh+hh+1];

		for(x=0;x<ww+ww+1;x++){
			for(y=0;y<hh+hh+1;y++){
				lab[x][y]=0;
			}
		}

		for (x=0,x1=1;x<ww;x++,x1=x1+2){
			for(y=0,y1=1;y<hh;y++,y1=y1+2){
				if(mazeP[x][y].lw){
					lab[x1-1][y1-1]=1;
					lab[x1-1][y1]=1;
					lab[x1-1][y1+1]=1;
				}
				if(mazeP[x][y].uw){
					lab[x1-1][y1-1]=1;
					lab[x1][y1-1]=1;
					lab[x1+1][y1-1]=1;
				}
			}
		}

		for (x=ww+ww,y=0;y<hh+hh+1;y++) lab[x][y]=1;
		for (x=0,y=hh+hh;x<ww+ww+1;x++) lab[x][y]=1;

		for (y=0,y1=hh+hh;y<hh+hh+1;y++,y1--){
			for (x=0;x<ww+ww+1;x++){
				temp_lab[x][y1]=lab[x][y];
			}
		}
		for (y=0;y<hh+hh+1;y++){
			for (x=0;x<ww+ww+1;x++){
				lab[x][y]=temp_lab[x][y];
			}
		}

		//Ќормализаци¤ лабиринта

		lab[0][hh+hh]=11;
		lab[0][0]=12;
		lab[ww+ww][0]=13;
		lab[ww+ww][hh+hh]=14;


		//правый край
		for (x=ww+ww,y=1;y<hh+hh;y++) {
			if ((lab[x][y-1]==1 || lab[x][y-1]==17) && lab[x][y+1]==1 && lab[x-1][y]==1) lab[x][y]=74;
			else lab[x][y]=17;
		}
		//нижний край
		for (x=1,y=hh+hh;x<ww+ww;x++) {
			if (lab[x][y-1]==1 && lab[x+1][y]==1 && (lab[x-1][y]==1 || lab[x-1][y]==10)) lab[x][y]=71;
			else lab[x][y]=10;
		}
		//левый край
		for (x=0,y=1;y<hh+hh;y++) {
			if ((lab[x][y-1]==1 || lab[x][y-1]==15) && lab[x][y+1]==1 && lab[x+1][y]==1) lab[x][y]=72;
			else lab[x][y]=15;
		}
		//верхний край
		for (x=1,y=0;x<ww+ww;x++) {
			if (lab[x+1][y]==1 && lab[x][y+1]==1 && (lab[x-1][y]==1 || lab[x-1][y]==16)) lab[x][y]=73;
			else lab[x][y]=16;
		}

		//внтури

		for(x=1;x<ww+ww;x++)
			for(y=1;y<hh+hh;y++){
				if((lab[x+1][y]==1 || lab[x+1][y]==21) && (lab[x-1][y]==1 || lab[x-1][y]==21) && (lab[x][y-1]==1 || lab[x][y-1]==22) && (lab[x][y+1]==1 || lab[x][y+1]==22)) lab[x][y]=4;
				else if ((lab[x][y-1]==1 || lab[x][y-1]==22) && (lab[x][y+1]==1 || lab[x][y+1]==22) && (lab[x+1][y]==1 || lab[x+1][y]==21)) lab[x][y]=31;
				else if ((lab[x+1][y]==1 || lab[x+1][y]==21) && (lab[x][y+1]==1 || lab[x][y+1]==22) && (lab[x-1][y]==1 || lab[x-1][y]==21)) lab[x][y]=32;
				else if ((lab[x][y-1]==1 || lab[x][y-1]==22) && (lab[x][y+1]==1 || lab[x][y+1]==22) && (lab[x-1][y]==1 || lab[x-1][y]==21)) lab[x][y]=33;
				else if ((lab[x][y-1]==1 || lab[x][y-1]==22) && (lab[x+1][y]==1 || lab[x+1][y]==21) && (lab[x-1][y]==1 || lab[x-1][y]==21)) lab[x][y]=34;
				else if (lab[x][y-1]==0 && lab[x+1][y-1]==0 && lab[x+1][y]==0 && lab[x+1][y+1]==0 && lab[x][y+1]==0 && lab[x][y]==1) lab[x][y]=51;
				else if (lab[x-1][y]==0 && lab[x-1][y+1]==0 && lab[x][y+1]==0 && lab[x+1][y+1]==0 && lab[x+1][y]==0 && lab[x][y]==1) lab[x][y]=52;
				else if (lab[x][y-1]==0 && lab[x-1][y-1]==0 && lab[x-1][y]==0 && lab[x-1][y+1]==0 && lab[x][y+1]==0 && lab[x][y]==1) lab[x][y]=53;
				else if (lab[x-1][y]==0 && lab[x-1][y-1]==0 && lab[x][y-1]==0 && lab[x+1][y-1]==0 && lab[x+1][y]==0 && lab[x][y]==1) lab[x][y]=54;
				else if ((lab[x-1][y]==1 || lab[x-1][y]==21) && (lab[x][y+1]==1 || lab[x][y+1]==22) && lab[x][y-1]==0 && lab[x+1][y]==0 && lab[x+1][y-1]==0) lab[x][y]=61;
				else if ((lab[x-1][y]==1 || lab[x-1][y]==21) && (lab[x][y-1]==1 || lab[x][y-1]==22) && lab[x+1][y]==0 && lab[x][y+1]==0 && lab[x+1][y+1]==0) lab[x][y]=62;
				else if ((lab[x+1][y]==1 || lab[x+1][y]==21) && (lab[x][y-1]==1 || lab[x][y-1]==22) && lab[x-1][y]==0 && lab[x][y+1]==0 && lab[x-1][y+1]==0) lab[x][y]=63;
				else if ((lab[x+1][y]==1 || lab[x+1][y]==21) && (lab[x][y+1]==1 || lab[x][y+1]==22) && lab[x-1][y]==0 && lab[x][y-1]==0 && lab[x-1][y-1]==0) lab[x][y]=64;
				else if (lab[x][y-1]==0 && lab[x][y+1]==0 && lab[x][y]==1) lab[x][y]=21;
				else if (lab[x-1][y]==0 && lab[x+1][y]==0 && lab[x][y]==1) lab[x][y]=22;
			}

		return lab;

	}

	public void StartFinishAction(byte[][] lab){
		int x,y;
		//----делаем старт----		//----делаем финиш-----
		for(y=1,x=1;x<ww+ww;x++){
			if(lab[x][y]==98) {lab[x][y]=0; start_loc=0; break;}
		}
		for(y=hh+hh-1,x=ww+ww-1;x>1;x--){
			if(lab[x][y]==99){lab[x][y]=0; finish_lok=0; break;}
		}
		for(y=1,x=1;x<ww+ww;x++){
			if(lab[x][y+1]!=0 && lab[x][y]==0){
				if(MathUtils.random(2)==1) { lab[x][y]=98; break;}
			}
		}
		for(y=hh+hh-1,x=ww+ww-1;x>1;x--){
			if(lab[x][y-1]!=0 && lab[x][y]==0){
				if(MathUtils.random(2)==1) { lab[x][y]=99; break;}
			}
		}
		for(y=1,x=1;x<ww+ww;x++){
			if(lab[x][y]==98) {start_loc=x; break;}
		}
		for(y=hh+hh-1,x=ww+ww-1;x>1;x--){
			if(lab[x][y]==99){finish_lok=x; break;}
		}
		//----«агадки-------------

	}

	//----«агадки-------------
	public void Quest(byte[][] lab, int rand1){
		int x,y;
		for(x=1;x<ww+ww-1;x++)
			for(y=1;y<hh+hh-1;y++){
				if(lab[x][y]==22 && (lab[x-1][y]==0) && (lab[x+1][y]==0)
						&& (lab[x-1][y-1]!=0) && (lab[x-1][y+1]!=0)
						&& (lab[x+1][y-1]!=0) && (lab[x+1][y+1]!=0)
						&& (lab[x][y-1]!=0) && (lab[x][y+1]!=0) && MathUtils.random(rand1)==0)
					lab[x][y]=27;
				if(lab[x][y]==22 && (lab[x-1][y]==0) && (lab[x+1][y]==0)
						&& (lab[x-1][y-1]==0) && (lab[x-1][y+1]==0)
						&& (lab[x+1][y-1]!=0) && (lab[x+1][y+1]!=0)
						&& (lab[x][y-1]!=0) && (lab[x][y+1]!=0) && MathUtils.random(rand1)==0)
					lab[x][y]=26;
				if(lab[x][y]==22 && (lab[x-1][y]==0) && (lab[x+1][y]==0)
						&& (lab[x-1][y-1]!=0) && (lab[x-1][y+1]!=0)
						&& (lab[x][y-1]!=0) && (lab[x][y+1]!=0)
						&& (lab[x+1][y-1]==0) && (lab[x+1][y+1]==0) && MathUtils.random(rand1)==0)
					lab[x][y]=25;
			}
	}

}
