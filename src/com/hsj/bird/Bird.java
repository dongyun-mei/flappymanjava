package com.hsj.bird;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Bird {
	/** �����λ��, ���λ�����������λ�� */
	int x;	int y;
	/** ���нǶ� */
	double angle;
	/** ����֡ */
	BufferedImage[] images;
	/** ��ǰͼƬ */
	BufferedImage image;
	/** ��ǰͼƬ��� */
	int index = 0;
	/** �������ٶ� */
	final double g; 
	/** ʱ������ */
	final double t;
	/** ��ʼ�����ٶ� */
	final double v0;
	/** ��ǰ�����ٶ� */
	double speed;
	/** �ƶ����� */
	double s;
	/** ��ķ�Χ, ��ķ�Χ��һ������������, ���ĵ���x,y */
	int size = 40;
	
	public Bird() throws Exception {
		this.g = 4; //�������ٶ�
		this.t = 0.25; //ÿ�μ���ļ��ʱ��
		this.v0 = 20; //��ʼ�����ٶ�
		x = 132; //��ĳ�ʼλ��
		y = 275; //��ĳ�ʼλ��
		//���ض���֡
		images = new BufferedImage[8];
		for(int i=0; i<8; i++){
			images[i] = ImageIO.read(getClass().getResource(i+".png"));
		}
		image = images[0];
	}
	
	/** ����һ��  
	 * ��ֱ����λ�Ƽ���
	 *  (1) �����ٶȼ��� V=Vo-gt  
		(2) ���׾������ S=Vot-1/2gt^2
	 * */
	public void step(){
		//V0 �Ǳ���
		double v0 = speed;
		//���㴹ֱ�����˶�, ����ʱ��t�Ժ���ٶ�, 
		double v = v0 - g*t;
		//��Ϊ�´μ���ĳ�ʼ�ٶ�
		speed = v;
		//���㴹ֱ�����˶������о���
		s = v0*t - 0.5 * g * t * t;
		//������ľ��� ����Ϊ y����ı仯
		y = y - (int)s;
		//����С�������, 
		angle = -Math.atan(s/8);
		
	}
	public void fly(){
		//����С��Ķ���֡ͼƬ, ����/4 ��Ϊ�˵����������µ�Ƶ��
		index++;
		image = images[(index/8)%images.length];
	}
	/** С�����Ϸ��� */
	public void flappy(){
		//ÿ��С��������Ծ, ���ǽ�С���ڵ�ǰ�������Գ�ʼ�ٶ� V0 ������
		speed = v0;
	}
	//����ʱ����ִ�е�, Ҫͬ������ ��ת����ϵ���������Ʒ�����Ӱ��
	public synchronized void paint(Graphics g){
		//g.drawRect(x-size/2, y-size/2, size, size);
		Graphics2D g2 = (Graphics2D)g;
		//��ת��ͼ����ϵ, ����
		g2.rotate(angle, this.x, this.y);
		//��x,y Ϊ���Ļ���ͼƬ
		int x = this.x-image.getWidth()/2;
		int y = this.y-image.getHeight()/2;
		g.drawImage(image, x, y, null);
		//��ת���� 
		g2.rotate(-angle, this.x, this.y);
	}

	@Override
	public String toString() {
		return "Bird [x=" + x + ", y=" + y + ", g=" + g + ", t=" + t + ", v0="
				+ v0 + ", speed=" + speed + ",s="+s+"]";
	}
	/** �ж����Ƿ�ͨ������ */
	public boolean pass(Column col1, Column col2) {
		return col1.x==x || col2.x==x;
	}
	/** �ж����Ƿ������Ӻ͵ط�����ײ */
	public boolean hit(Column column1, Column column2, Ground ground) {
		//��������
		if(y-size/2 >= ground.y){
			return true;
		}
		//��������
		return hit(column1) || hit(column2);
	}
	/** ��鵱ǰ���Ƿ��������� */
	public boolean hit(Column col){
		//�������������: ������ĵ�x������ ���ӿ�� + ���һ��
		if( x>col.x-col.width/2-size/2 && x<col.x+col.width/2+size/2){
			if(y>col.y-col.gap/2+size/2 && y<col.y+col.gap/2-size/2 ){
				return false;
			}
			return true;
		}
		return false;
	}
	
}




