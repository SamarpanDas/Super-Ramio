package com.samarpan.superramio.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;


import java.util.ArrayList;
import java.util.Random;

import javax.xml.soap.Text;

public class SuperRamio extends ApplicationAdapter
{
	SpriteBatch batch;
	Texture background;   // texture is imp for adding any image
    Texture[] man;      // texture array is needed for the man as showing a number of frames(pics) continuously will give the
                       // notion of the man running

    int manState = 0;
    int pause = 0;
    float gravity = 0.2f;
    float velocity = 0;
    int manY = 0;
    Rectangle manRectangle;


    ArrayList<Integer> coinXs = new ArrayList<Integer>();
    ArrayList<Integer> coinYs = new ArrayList<Integer>();
    ArrayList<Rectangle> coinRectangles = new ArrayList<Rectangle>();
    Texture coin;
    int coinCount;


    Random random;


    ArrayList<Integer> bombXs = new ArrayList<Integer>();
    ArrayList<Integer> bombYs = new ArrayList<Integer>();
    ArrayList<Rectangle> bombRectangles = new ArrayList<Rectangle>();
    Texture bomb;
    int bombCount;






	
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


        manY = Gdx.graphics.getHeight()/2 ;


        coin = new Texture("coin.png");
        bomb = new Texture("bomb.png");
        random = new Random();


	}






	public void makeCoin()
    {
        float height = random.nextFloat() * Gdx.graphics.getHeight();
        coinYs.add((int)height);
        coinXs.add(Gdx.graphics.getWidth());
    }

    public void makeBomb()
    {
        float height = random.nextFloat() * Gdx.graphics.getHeight();
        bombYs.add((int)height);
        bombXs.add(Gdx.graphics.getWidth());
    }







	@Override
	public void render ()  // this render method is more like a loop, it calls it self over and over again
	{
	    batch.begin();   // for starting the drawing process

        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());  //showing background
                // 0, 0 is the starting pos of the getWidth and Height will fill the entire height and width



     ////////  start of coin part

        if(coinCount < 100)                                          // coin frequency controller
        {
            coinCount++;
        }else {
            coinCount = 0;
            makeCoin();
        }

        coinRectangles.clear();
        for(int i = 0; i < coinXs.size(); i++)
        {
            batch.draw(coin, coinXs.get(i), coinYs.get(i));
            coinXs.set(i, coinXs.get(i) - 4);                     // the constant value is the speed controller

            coinRectangles.add(new Rectangle(coinXs.get(i), coinYs.get(i), coin.getWidth(), coin.getHeight()));

        }

    ///// end of coin part


    /////// start of bomb part
        if(bombCount < 450)                                     //  bomb frequency controller
        {
            bombCount++;
        }else {
            bombCount = 0;
            makeBomb();
        }


        bombRectangles.clear();
        for(int i = 0; i < bombXs.size(); i++)
        {
            batch.draw(bomb, bombXs.get(i), bombYs.get(i));
            bombXs.set(i, bombXs.get(i) - 7);                    // the constant value is the speed controller

            bombRectangles.add(new Rectangle(bombXs.get(i), bombYs.get(i), bomb.getWidth(), bomb.getHeight()));

        }

    //////// end of bomb part





    ////////    working with touch

        if(Gdx.input.justTouched())
        {
            velocity = -10;
        }
    //////////////









        // The pause if else statement is adding to slow down the speed of running of our man
        if(pause < 6) // now after 8 iterations the state of the man changes making his speed look slower
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


        velocity += gravity;
        manY -= velocity;

        if(manY <= 0)
            manY = 0;



        //batch.draw(man[0], Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/4);
        batch.draw(man[manState], Gdx.graphics.getWidth()/2 - man[manState].getWidth()/2, manY);
        // getting the man at the exact center pos

        manRectangle = new Rectangle(Gdx.graphics.getWidth()/2 - man[manState].getWidth()/2, manY, man[manState].getWidth(), man[manState].getHeight());

        for(int i=0;i<coinRectangles.size();i++)
        {
            if(Intersector.overlaps(manRectangle, coinRectangles.get(i)))
            {
                Gdx.app.log("Coin ::::", "Collision !!!");
                Gdx.app.log("Index ::", Integer.toString(i));
            }
        }

        for(int i=0;i<bombRectangles.size();i++)
        {
            if(Intersector.overlaps(manRectangle, bombRectangles.get(i)))
            {
                Gdx.app.log("Bomb ::::", "Collision !!!");
                Gdx.app.log("Index ::", Integer.toString(i));
            }
        }

        batch.end();     // for ending the batch process

	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
