package com.samarpan.superramio.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import javax.xml.soap.Text;

public class SuperRamio extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;   // texture is imp for adding any image
    Texture[] man;      // texture array is needed for the man as showing a number of frames(pics) continuously will give the
                       // notion of the man running

    int manState = 0;
    int pause = 0;

	
	@Override
	public void create ()
	{
		batch = new SpriteBatch();
		background = new Texture("bg.png");  // assigning the bg.png to background
        man = new Texture[4];
        man[0] = new Texture("frame-1.png");
        man[1] = new Texture("frame-2.png");
        man[2] = new Texture("frame-3.png");
        man[3] = new Texture("frame-4.png");

	}

	@Override
	public void render ()  // this render method is more like a loop, it calls it self over and over again
	{
	    batch.begin();   // for starting the drawing process

        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());  //showing background
                // 0, 0 is the starting pos of the getWidth and Height will fill the entire height and width



        // The pause if else statement is adding to slow down the speed of running of our man
        if(pause < 8) // now after 8 iterations the state of the man changes making his speed look slower
        {
            pause++;
        }else {
            pause=0;
            if(manState < 3)  // man state is actually changing the image of of the man to be displayed
            {
                manState++;
            }else {
                manState = 0;
            }
        }



        //batch.draw(man[0], Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/4);
        batch.draw(man[manState], Gdx.graphics.getWidth()/2 - man[manState].getWidth()/2, Gdx.graphics.getHeight()/2 - man[manState].getWidth()/2);
        // getting the man at the exact center pos


        batch.end();     // for ending the batch process

	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
