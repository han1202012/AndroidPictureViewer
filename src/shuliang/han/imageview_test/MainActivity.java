package shuliang.han.imageview_test;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	private int[] images;		//图片资源id数组
	private int currentImage;	//当前显示图片
	private int alpha;			//透明度
	
	private Matrix matrix;
	private int anglel;			//角度
	
	private int imageWidth;
	private int imageHeight;
	
	private int addWidth;
	private int addHeight;
	
	private ImageView image_all;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		init();
		
		
	}
	
	//初始化成员变量
	private void init() {
		images = new int[]{R.drawable.mary1, R.drawable.mary2};
		currentImage = Integer.MAX_VALUE / 2;
		alpha = 255;
		
		matrix = new Matrix();
		
		image_all = (ImageView) findViewById(R.id.image_all);
		image_all.setImageResource(images[currentImage % images.length]);
	}
	
	public void onClick(View view) {
		int id = view.getId();
		
		switch (id) {
			case R.id.alpha_plus:	//增大透明度
				alpha += 20;
				if(alpha >= 255)
					alpha = 255;
				image_all.setAlpha(alpha);
				break;
				
			case R.id.alpha_minus:  //减小透明度
				alpha -= 20;
				if(alpha <= 0)
					alpha = 0;
				image_all.setAlpha(alpha);
				break;
				
			case R.id.next_page:	//显示下一张图片
				//为了保证图片能够循环, 这里模运算是关键, 显示图片的下标始终是长度的模
				image_all.setImageResource(images[ ++currentImage % images.length ]);
				break;
			
			case R.id.prev_page:	//显示上一张图片
				image_all.setImageResource(images[ --currentImage % images.length ]);
				break;
				
			case R.id.big:			//放大图片
				imageWidth += addWidth;
				imageHeight += addHeight;
					
				image_all.setLayoutParams(new LinearLayout.LayoutParams(imageWidth, imageHeight));
				break;
				
			case R.id.small:		//缩小图片
				imageWidth -= addWidth;
				imageHeight -= addHeight;
				if(imageWidth <= 0 || imageHeight <=0){
					imageWidth += addWidth;
					imageHeight += addHeight;
				}
				
				image_all.setLayoutParams(new LinearLayout.LayoutParams(imageWidth, imageHeight));
				break;
				
			case R.id.turn_left:	//向左旋转
				anglel += 45;
				matrix.setRotate(anglel);
				Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(images[currentImage % images.length])).getBitmap();
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),bitmap.getHeight(), matrix, true); 
				image_all.setImageBitmap(bitmap);
				break;
				
			case R.id.turn_right:	//向右旋转
				anglel -= 45;
				matrix.setRotate(anglel);
				Bitmap bitmap1 = ((BitmapDrawable) getResources().getDrawable(images[currentImage % images.length])).getBitmap();
				bitmap1 = Bitmap.createBitmap(bitmap1, 0, 0, bitmap1.getWidth(),bitmap1.getHeight(), matrix, true); 
				image_all.setImageBitmap(bitmap1);
				break;
				
			default:
				break;
		}
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		imageWidth = image_all.getWidth();
		imageHeight = image_all.getHeight();
		
		addWidth = imageWidth / 5;
		addHeight = imageHeight / 5;
	}

}

