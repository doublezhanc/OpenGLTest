package com.lich.opengltest;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLUtils;

public class MyRenderer implements Renderer {



    FloatBuffer vertices;
    FloatBuffer texture;
    ShortBuffer indices;
    int textureId;
    
	private FloatBuffer mTextureBuffer;
	//int texture;
	// private GLBitmap glBitmap;
	Bitmap mbitmap;
	Activity mActivity;
	GLSurfaceView mGlView;

	public MyRenderer(Activity activity, GLSurfaceView glView) {
		mActivity = activity;
		mGlView = glView;
		
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * 2 * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        vertices = byteBuffer.asFloatBuffer();
//        vertices.put( new float[] {  -80f,   -120f,0,1f,
//                                     80f,  -120f, 1f,1f,
//                                     -80f, 120f, 0f,0f,
//                                     80f,120f,   1f,0f});
        //绘制区域
        vertices.put( new float[] {  -80f,   -120f,
                                     80f,  -120f,
                                     -80f, 120f,
                                     80f,  120f});

        ByteBuffer indicesBuffer = ByteBuffer.allocateDirect(6 * 2);
        indicesBuffer.order(ByteOrder.nativeOrder());
        indices = indicesBuffer.asShortBuffer();
        indices.put(new short[] { 0, 1, 2,1,2,3});

        ByteBuffer textureBuffer = ByteBuffer.allocateDirect(4 * 2 * 4);
        textureBuffer.order(ByteOrder.nativeOrder());
        texture = textureBuffer.asFloatBuffer();
        //贴图配置 0.5->半个 ，1->一个，2->两个
        texture.put( new float[] { 0,1f,
                                    1f,1f,
                                    0f,0f,
                                    1f,0f});

        indices.position(0);
        vertices.position(0);
        texture.position(0);

	}
	
	protected void setTextureCoordinates(float[] textureCoords){

        // float is 4 bytes, therefore we multiply the number if

        // vertices with 4.

        ByteBuffer byteBuf = ByteBuffer.allocateDirect(

                                           textureCoords.length *4);

        byteBuf.order(ByteOrder.nativeOrder());

        mTextureBuffer = byteBuf.asFloatBuffer();

        mTextureBuffer.put(textureCoords);

        mTextureBuffer.position(0);

}

	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		// 因为在glVertexPointer或者是glColorPointer方法中要求传入一个直接的Buffer，
		// 所以下面的vbb1,vbb2,colorvbb1,colorvbb2皆为创建一个直接的Buffer
		// 以第一个为例说明，首先用ByteBuffer的allocateDirect方法来分配新的直接字节缓冲区。
		// 因1个Int有4个byte，所以将vertices的长度乘以4
		// order(ByteOrder.nativeOrder)方法以本机字节顺序来修改此缓冲区的字节顺序
		// 然后用asIntBuffer方法创建此字节缓冲区的视图，作为 int 缓冲区。
		// put方法将给定 int 写入此缓冲区的当前位置，然后该位置递增。
		// position方法设置此缓冲区的位置。如果标记已定义并且大于新的位置，则要丢弃该标记。
//		ByteBuffer vbb1 = ByteBuffer.allocateDirect(vertices.length * 4);
//		vbb1.order(ByteOrder.nativeOrder());
//		triangleBuffer = vbb1.asIntBuffer();
//		triangleBuffer.put(vertices);
//		triangleBuffer.position(0);
//
//		// quater的ByteBuffer
//		ByteBuffer vbb2 = ByteBuffer.allocateDirect(quater.length * 4);
//		vbb2.order(ByteOrder.nativeOrder());
//		quaterBuffer = vbb2.asIntBuffer();
//		quaterBuffer.put(quater);
//		quaterBuffer.position(0);
//
//		// color的ByteBuffer
//		ByteBuffer colorvbb1 = ByteBuffer.allocateDirect(color.length * 4);
//		colorvbb1.order(ByteOrder.nativeOrder());
//		color1Buffer = colorvbb1.asIntBuffer();
//		color1Buffer.put(color);
//		color1Buffer.position(0);
//
//		// color2的ByteBuffer
//		ByteBuffer colorvbb2 = ByteBuffer.allocateDirect(color2.length * 4);
//		colorvbb2.order(ByteOrder.nativeOrder());
//		color2Buffer = colorvbb2.asIntBuffer();
//		color2Buffer.put(color2);
//		color2Buffer.position(0);

		// 绘制Triangles
		// 清除屏幕和深度缓存
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// 重置当前的模型观察矩阵
		gl.glLoadIdentity();

		// 以下两步为绘制颜色与顶点前必做操作
		// (颜色可采用另一种简单方式，说见http://blog.csdn.net/Simdanfeg/archive/2011/03/17/6255932.aspx)
		// 允许设置顶点
		// GL10.GL_VERTEX_ARRAY顶点数组
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// 允许设置颜色
		// GL10.GL_COLOR_ARRAY颜色数组
		//gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

		// 左移1.5单位，并移入屏幕6.0
//		gl.glTranslatef(-1.5f, 0.0f, -6.0f);
//
//		// GL_FIXED,GL_FLOAT,GL_UNSIGNED_BYTE
//		// 更多信息见 http://www.devx.com/wireless/Article/32879/1954
//		// 参数中的GL_FIXED表示我们之前定义的one为单位长度
//		// 设置三角形
//		gl.glVertexPointer(3, GL10.GL_FIXED, 0, triangleBuffer);
//		// 设置三角形颜色
//		//gl.glColorPointer(4, GL10.GL_FIXED, 0, color1Buffer);
//		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
//		// 绘制三角形
//		// GL10.GL_TRIANGLES:把每三个顶点作为一个独立的三角形。顶点3n-2，3n-1和3n定义了第n个三角形，总共绘制N/3个三角形。
//		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
//		
		

		// 重置当前模型的观察矩阵
		//gl.glLoadIdentity();

//		// 左移1.5单位，并移入屏幕6.0
//		gl.glTranslatef(1.5f, 0.0f, -6.0f);
//		// 设置正方形
//		gl.glVertexPointer(3, GL10.GL_FIXED, 0, quaterBuffer);
//		//gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, quaterBuffer);
//        gl.glTexCoordPointer(2, GL10.GL_FLOAT,0, mTextureBuffer);
//		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
//		// 设置正方形颜色
//		//gl.glColorPointer(4, GL10.GL_FIXED, 0, color2Buffer);
//		// GL_TRIANGLE_STRIP：绘制一组相连的三角形。对于奇数点n，顶点n，n+1和n+2定义了第n个三角形；
//		// 对于偶数n，顶点n+1，n和n+2定义了第n个三角形，总共绘制N-2个三角形。
//		// 绘制正方形
//		gl.glDrawElements(GL10.GL_TRIANGLES, 6,
//	            GL10.GL_UNSIGNED_BYTE, quaterBuffer);
		//gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		
        gl.glViewport(0, 0, mGlView.getWidth(), mGlView.getHeight());
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        //glOrthof(左，右，下，上，进，远) Xmin-Xmax   Ymin-Ymax     Zmin-Zmax
        gl.glOrthof(-160, 160, -240, 240, 1, -1);

        gl.glEnable(GL10.GL_TEXTURE_2D);
        //绑定纹理ID
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertices);

        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texture);
        // gl.glRotatef(1, 0, 1, 0);
        gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 6,
                GL10.GL_UNSIGNED_SHORT, indices);
		
		

		// 取消颜色设置
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		// 取消顶点设置
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		float ratio = (float) width / height;
		// 设置OpenGL场景的大小,(0,0)表示窗口内部视口的左下角，(w,h)指定了视口的大小
		gl.glViewport(0, 0, width, height);
		// 设置投影矩阵
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// 重置投影矩阵
		gl.glLoadIdentity();
		// 设置视口的大小
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
		// 以下两句告诉opengl es，以后所有的变换都将影响这个模型(即我们绘制的图形)
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();

	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
		// TODO Auto-generated method stub
		// 告诉系统对透视进行修正(选择效率优先还是速度优先，这里我们选择速度优先)
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		// 用黑色来清除屏幕颜色
		gl.glClearColor(0, 0, 0, 0);
		// 启用阴影平滑
		gl.glShadeModel(GL10.GL_SMOOTH);

		// 设置深度缓存
		gl.glClearDepthf(1.0f);
		// 启用深度测试
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// 所做深度测试的类型
		gl.glDepthFunc(GL10.GL_LEQUAL);
		// 启用2D纹理贴图
		gl.glEnable(GL10.GL_TEXTURE_2D);
		//setTextureCoordinates(texCoords);

		// mbitmap = BitmapFactory.decodeResource(mActivity.getResources(),
		// R.drawable.ic_launcher);
		loadTexture(gl);

	}

	private void loadTexture(GL10 gl) {
		Bitmap bitmap = null;
		try {
			// 加载位图
			bitmap = BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.texture);
			int[] textures = new int[1];
			// 指定生成N个纹理（第一个参数指定生成1个纹理），
			// textures数组将负责存储所有纹理的代号。
			gl.glGenTextures(1, textures, 0);
			// 获取textures纹理数组中的第一个纹理
			textureId = textures[0];
			// 通知OpenGL将texture纹理绑定到GL10.GL_TEXTURE_2D目标中
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
			// 设置纹理被缩小（距离视点很远时被缩小）时候的滤波方式
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
			// 设置纹理被放大（距离视点很近时被方法）时候的滤波方式
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
			// 设置在横向、纵向上都是平铺纹理
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
			// 加载位图生成纹理
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		} finally {
			// 生成纹理之后，回收位图
			if (bitmap != null)
				bitmap.recycle();
		}
	}
	

}
