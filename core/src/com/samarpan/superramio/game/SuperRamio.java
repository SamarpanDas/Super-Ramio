package com.samarpan.superramio.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;



import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

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
    BitmapFont font;
    Texture dizzy;

    int score = 0;
    int gameState = 0;
    int sleep = 0;


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


    Sound coinSound;
    Sound dieSound;
    Sound jumpSound;








	
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

        dizzy = new Texture("dizzy-1.png");


        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(10);


        coinSound = Gdx.audio.newSound(Gdx.files.internal("coin.wav"));
        dieSound = Gdx.audio.newSound(Gdx.files.internal("die.wav"));
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));

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

        if(gameState == 1)
        {

            // GAME IS LIVE !!!


            ///////// start of coin part

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
                jumpSound.play();
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

        }                                           // END OF GAMESTATE = 1


        else if(gameState == 0)
        {
            // WAITING TO START

            if(Gdx.input.justTouched())  // game will start once screen is touched
            {
                gameState = 1;
            }
        }

        else if(gameState == 2)
        {
            // GAME OVER






            if(Gdx.input.justTouched())  // game will start once screen is touched
            {
                gameState = 1;

                manY = Gdx.graphics.getHeight()/2 ;
                score = 0;
                velocity = 0;
                coinXs.clear();
                coinYs.clear();
                coinRectangles.clear();
                coinCount = 0;

                bombXs.clear();
                bombYs.clear();
                bombRectangles.clear();
                bombCount = 0;
            }
        }







        if(gameState == 2)
        {
            batch.draw(dizzy, Gdx.graphics.getWidth()/2 - man[manState].getWidth()/2, manY);

            /*

            if(sleep == 1)
            {
                try{
                    Thread.sleep(2000);

                }catch (Exception e)
                {
                    e.printStackTrace();
                }

                sleep = 0;
            }
            */





        }else {
            //batch.draw(man[0], Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/4);
            batch.draw(man[manState], Gdx.graphics.getWidth()/2 - man[manState].getWidth()/2, manY);
            // getting the man at the exact center pos
        }




        manRectangle = new Rectangle(Gdx.graphics.getWidth()/2 - man[manState].getWidth()/2, manY, man[manState].getWidth(), man[manState].getHeight());

        for(int i=0;i<coinRectangles.size();i++)
        {
            if(Intersector.overlaps(manRectangle, coinRectangles.get(i)))
            {
                Gdx.app.log("Coin ::::", "Collision !!!");
                Gdx.app.log("Index ::", Integer.toString(i));

                score++;
                coinSound.play();
                coinRectangles.remove(i);
                coinXs.remove(i);
                coinYs.remove(i);
                break;
            }
        }

        for(int i=0;i<bombRectangles.size();i++)
        {
            if(Intersector.overlaps(manRectangle, bombRectangles.get(i)))
            {
                Gdx.app.log("Bomb ::::", "Collision !!!");
                Gdx.app.log("Index ::", Integer.toString(i));
                dieSound.play();


                gameState = 2;     // ending the game
                sleep = 1;

            }
        }


        font.draw(batch, String.valueOf(score), 100, 200);

        batch.end();     // for ending the batch process

	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
