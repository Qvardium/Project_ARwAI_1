package com.qvardium.game.project.arwii.one;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Kmaze {

	CellMaze[][] mazeK;
	int ww,hh;
	public int start_loc, finish_lok;

	Array<Walls> wl = new Array<Walls>();

	final int[] dx={1,0,-1,0};
	final int[] dy={0,-1,0,1};

	public Kmaze(int w, int h) {
		ww=w;
		hh=h;
		mazeK = new CellMaze[w][h];
	}

	class CellMaze{
		boolean lw,uw;
		int n;

		CellMaze(boolean lw,boolean uw, int a){
			this.lw=lw;
			this.uw=uw;
			this.n=a;
		}
	}

	class Walls{
		int xn,yn,xf,yf;
		boolean lw,uw;
		Walls(int xn, int yn, int xf, int yf,boolean lw, boolean uw){
			this.xn=xn;
			this.xf=xf;
			this.yn=yn;
			this.yf=yf;
			this.lw=lw;
			this.uw=uw;
		}
	}

	public void GeneratMaze(){

		int x,y,i,loc;
		Walls tw;

		for(x=0;x<ww;x++)
			for(y=0;y<hh;y++)
				mazeK[x][y] = new CellMaze(true,true,0);


		for(x=0;x<ww;x++)
			for(y=0;y<hh;y++){
				if(x!=ww-1) wl.add(new Walls(x,y,x+1,y,true,false));
				else if(x==ww-1 && y!=hh-1) wl.add(new Walls(x,y,x,y+1,false,true));
				if (y!=hh-1) wl.add(new Walls(x,y,x,y+1,false,true));
			}

		i=wl.size-1;
		loc=ww*hh;

		while (loc>1){

			tw=wl.removeIndex(MathUtils.random(i));
			i--;
			if (!IsConect(tw.xn,tw.yn,tw.xf,tw.yf)){
				if(tw.lw) mazeK[tw.xf][tw.yf].lw=false;
				if(tw.uw) mazeK[tw.xf][tw.yf].uw=false;
				loc--;
			}
		}

	}


	boolean IsConect(int xn, int yn, int xf, int yf){
		int x,y,i,n=1;
		boolean NoSol;

		for(x=0;x<ww;x++)
			for(y=0;y<hh;y++)
				mazeK[x][y].n=0;

		mazeK[xn][yn].n=n;

		do{
			NoSol=true;
			for(x=0;x<ww;x++){
				for(y=0;y<hh;y++){
					if (mazeK[x][y].n==n){
						for (i=0;i<4;i++){
							if (CanGo(x,y,dx[i],dy[i]) && mazeK[x+dx[i]][y+dy[i]].n==0){
								NoSol=false;
								mazeK[x+dx[i]][y+dy[i]].n=n+1;
								if(x+dx[i]==xf && y+dy[i]==yf){
									return true;
								}
							}
						}
					}
				}
			}
			n=n+1;
		}while(!NoSol);

		return false;

	}

	boolean CanGo(int x, int y, int dx, int dy){
		if (dx==-1 && x!=0) return !mazeK[x][y].lw;
		else if (dx==1 && x!=ww-1) return !mazeK[x+1][y].lw;
		else if (dy==-1 && y!=0) return !mazeK[x][y].uw;
		else if (dy==1 && y!=hh-1) return !mazeK[x][y+1].uw;
		else return false;
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
				if(mazeK[x][y].lw){
					lab[x1-1][y1-1]=1;
					lab[x1-1][y1]=1;
					lab[x1-1][y1+1]=1;
				}
				if(mazeK[x][y].uw){
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
