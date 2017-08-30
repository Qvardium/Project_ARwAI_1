package com.qvardium.game.project.arwii.one;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Emaze {

	int ww,hh;
	public int start_loc, finish_lok;
	CellMaze[][] mazeE;
	
	public Emaze(int width, int height){
		ww=width;
		hh=height;
		mazeE=new CellMaze[ww][hh];
	}
	
	class CellMaze{
		boolean rw,dw;
		int cs;
		
		CellMaze(boolean rw,boolean dw, int i){
			this.rw=rw;
			this.dw=dw;
			this.cs=i;
		}
	}
	
	public void GeneratMaze(){
		
		int x,y=0,i=1,r;
		Array<Integer> xx = new Array<Integer>(); 
		
		
		for(x=0;x<ww;x++,i++)
			if (x==ww-1) mazeE[x][y]=new CellMaze(true,false,i);
			else mazeE[x][y]=new CellMaze(false,false,i);
		
		for(x=0;x<ww;x++){
			r=MathUtils.random(1);
			if (r==1) mazeE[x][y].rw=true;
			else if (r==0 && x!=ww-1) mazeE[x+1][y].cs=mazeE[x][y].cs;
		}
		
		for(x=0;x<ww;x++){
			if (x==0 && !mazeE[x][y].rw) 
				xx.add(x);
			else if (x!=ww-1 && !mazeE[x][y].rw) {
				xx.add(x);
			}
			else if (x!=0 && mazeE[x][y].rw && !mazeE[x-1][y].rw){
				xx.add(x);
				i=xx.size;
				for(;i>1;i--){
					r=MathUtils.random(i-1);
					//if(MathUtils.random(1)==1) {
						mazeE[xx.removeIndex(r)][y].dw=true;
				//	}
					//else 
					//	xx.removeIndex(r);
				}
				xx.clear();
			}
			else if (x!=0 && mazeE[x][y].rw && mazeE[x-1][y].rw) xx.clear();
		}
		
		for(x=1;x<ww;x++){
			for(y=1;y<hh;y++){
				if (y!=hh-1){
					
					for(x=0;x<ww;x++)
						mazeE[x][y]=new CellMaze(mazeE[x][y-1].rw,mazeE[x][y-1].dw,mazeE[x][y-1].cs);
					
					for(x=0;x<ww;x++){
						if(mazeE[x][y].rw && x!=ww-1) mazeE[x][y].rw=false;
						if(x==0 && mazeE[x][y].dw) {mazeE[x][y].dw=false; mazeE[x][y].cs=1;}
						else if (mazeE[x][y].dw) {mazeE[x][y].dw=false; mazeE[x][y].cs=mazeE[x-1][y].cs+1;}
					}
					
					for(x=0;x<ww;x++){
						r=MathUtils.random(1);
						//if (x!=ww-1 && mazeE[x+1][y].cs==mazeE[x][y].cs) mazeE[x][y].rw=true;
						 if (r==1) mazeE[x][y].rw=true;
						else if (r==0 && x!=ww-1) mazeE[x+1][y].cs=mazeE[x][y].cs;						
					}
					
					for(x=0;x<ww;x++){
						if (x==0 && !mazeE[x][y].rw) 
							xx.add(x);
						else if (x!=ww-1 && !mazeE[x][y].rw) {
							xx.add(x);
						}
						else if (x!=0 && mazeE[x][y].rw && !mazeE[x-1][y].rw){
							xx.add(x);
							i=xx.size;
							for(;i>1;i--){
								r=MathUtils.random(i-1);
						//		if(MathUtils.random(1)==1) {
									mazeE[xx.removeIndex(r)][y].dw=true;
						//		}
						//		else 
						//			xx.removeIndex(r);
							}
							xx.clear();
						}
						else if (x!=0 && mazeE[x][y].rw && mazeE[x-1][y].rw) xx.clear();
					}
					
				}
				else if (y==hh-1){
					for(x=0;x<ww;x++)
						if (x==ww-1) mazeE[x][y]=new CellMaze(true,true,mazeE[x][y-1].cs);
						else mazeE[x][y]=new CellMaze(false,true,mazeE[x][y-1].cs);
					
				//	for(x=0;x<ww;x++)
					//	if(x!=ww-1 && mazeE[x][y].cs!=mazeE[x+1][y].cs) mazeE[x][y].rw=false;
					
					//for(x=0;x<ww;x++)
					//	if(x!=ww-1 && mazeE[x][y-1].rw) mazeE[x][y].rw=false;
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
				if(mazeE[x][y].rw){
					lab[x1+1][y1-1]=1;
					lab[x1+1][y1]=1;
					lab[x1+1][y1+1]=1;
				}
				if(mazeE[x][y].dw){
					lab[x1-1][y1+1]=1;
					lab[x1][y1+1]=1;
					lab[x1+1][y1+1]=1;
				}
			}
		}
		
		for (x=0,y=0;y<hh+hh+1;y++) lab[x][y]=1;
		for (x=0,y=0;x<ww+ww+1;x++) lab[x][y]=1;
		
		
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
		//???????????? ?????????
		
				lab[0][hh+hh]=11;
				lab[0][0]=12;
				lab[ww+ww][0]=13;
				lab[ww+ww][hh+hh]=14;


		//?????? ????
				for (x=ww+ww,y=1;y<hh+hh;y++) {
					if ((lab[x][y-1]==1 || lab[x][y-1]==17) && lab[x][y+1]==1 && lab[x-1][y]==1) lab[x][y]=74;
					else lab[x][y]=17;
				}
		//?????? ????
				for (x=1,y=hh+hh;x<ww+ww;x++) {
					if (lab[x][y-1]==1 && lab[x+1][y]==1 && (lab[x-1][y]==1 || lab[x-1][y]==10)) lab[x][y]=71;
					else lab[x][y]=10;
				}
		//????? ????
				for (x=0,y=1;y<hh+hh;y++) {
					if ((lab[x][y-1]==1 || lab[x][y-1]==15) && lab[x][y+1]==1 && lab[x+1][y]==1) lab[x][y]=72;
					else lab[x][y]=15;
				}
		//??????? ????
				for (x=1,y=0;x<ww+ww;x++) {
					if (lab[x+1][y]==1 && lab[x][y+1]==1 && (lab[x-1][y]==1 || lab[x-1][y]==16)) lab[x][y]=73;
					else lab[x][y]=16;
				}

		//??????
				
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

		
		for(y=1,x=1;x<ww+ww;x++){
			if(lab[x][y]==98) {lab[x][y]=0; start_loc=0; break;}
		}
		for(y=hh+hh-1,x=ww+ww-1;x>1;x--){
			if(lab[x][y]==99){lab[x][y]=0; finish_lok=0; break;}
		}	
		lab[1][1]=98;
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
	}

	//----???????-------------
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
