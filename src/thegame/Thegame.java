package thegame;

import java.io.InputStream;
import java.net.URL;
import java.util.*;
import javafx.animation.*;
import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Thegame extends Application {
   final int OB=20;
   boolean jumpDisable=false;
   boolean isPaused=true;
   boolean eggShowing=false;
   boolean eggAvailable=true;
   boolean playerInAir=false;
   boolean isInvincible=false;
   int seconds =200;
   int points = 0;
   int highScore = 0;
   int pointsDrops = 0;
   int invincDrops = 0;
   int invincDuration=0;
   int time=81000;
   Label lbl = new Label("Press UP to begin!");
   Label lblGameOver = new Label("");
   Label lblPoints = new Label("Score: "+points);
   Label lblHighScore = new Label("High Score: "+highScore);
   Label lblProjectile = new Label("Egg Available");
   Label lblInvinc = new Label("");
   Label lblGameTitle = new Label("Truck-A-Duck");
   int check;
   int max=6000;
   int min = 1920;
    @Override
    public void start(Stage primaryStage) {
       Group root = new Group();
            Random rand = new Random();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Truck-A-Duck");
        primaryStage.setScene(scene);
        primaryStage.setHeight(1080);
        primaryStage.setWidth(1920);
        primaryStage.show();
        lbl.setLayoutX(600);
        lbl.setLayoutY(400);
        lbl.setFont(Font.font("Impact", FontWeight.BOLD, 50));
        lblGameOver.setFont(Font.font("Impact", FontWeight.BOLD, 70));
        lblGameOver.setLayoutX(500);
        lblGameOver.setLayoutY(250);
        lblPoints.setLayoutX(1550);
        lblPoints.setLayoutY(20);
        lblPoints.setFont(Font.font("Impact", FontWeight.BOLD, 45));
        lblHighScore.setLayoutX(1550);
        lblHighScore.setLayoutY(75);
        lblHighScore.setFont(Font.font("Impact", FontWeight.BOLD, 45));
        lblProjectile.setTextFill(Color.RED);
        lblProjectile.setLayoutX(20);
        lblProjectile.setLayoutY(470);
        lblProjectile.setFont(Font.font("Impact", FontWeight.BOLD, 45));
        lblInvinc.setLayoutX(700);
        lblInvinc.setLayoutY(400);
        lblInvinc.setTextFill(Color.RED);
        lblInvinc.setFont(Font.font("Impact", FontWeight.BOLD, 90));
        lblGameTitle.setFont(Font.font("Impact", FontWeight.BOLD, 120));
        lblGameTitle.setTextFill(Color.GREEN);
        lblGameTitle.setLayoutX(500);
        lblGameTitle.setLayoutY(150);
        Canvas canvas = new Canvas(1920,1080);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Sprite ob;
        Sprite player = new Sprite();
        Sprite egg = new Sprite();
        Sprite road = new Sprite();
        Sprite back = new Sprite();
        Sprite pointsDrop = new Sprite();
        Sprite invincDrop = new Sprite();
        ArrayList <Sprite> obList = new ArrayList <Sprite>();
        root.getChildren().add(canvas);
        root.getChildren().add(lbl);
        root.getChildren().add(lblPoints);
        root.getChildren().add(lblHighScore);
        root.getChildren().add(lblProjectile);
        root.getChildren().add(lblInvinc);
        root.getChildren().add(lblGameTitle);
        root.getChildren().add(lblGameOver);
        
        //DRAW ROAD
        road.setPosition(0, 700);
        road.drawRoad(gc);
        
        //DRAW BACKGROUND
        back.setPosition(0,0);
        back.drawBack(gc);
        
        //DRAWS THE PLAYER'S CHARACTER
        player.setPosition(200,770);
        player.setHeight(30);
        player.setWidth(50);
        player.drawChar(gc);
        
        //DRAW EGG
        egg.setPosition(400,-770);
        egg.setHeight(60);
        egg.setWidth(20);
        egg.drawEgg(gc);
        
        //DRAW POINTS DROP
        pointsDrop.setPosition(1940,720);
        pointsDrop.setWidth(35);
        pointsDrop.setHeight(35);
        pointsDrop.drawPoints(gc);
        
        //ADDS INVINCIBILITY DROP
        invincDrop.setPosition(1940,720);
        invincDrop.setWidth(35);
        invincDrop.setHeight(35);
        invincDrop.drawInvincibility(gc);
        
        //DRAW OBSTACLES AND CHECK FOR COLLISION BETWEEN OBSTACLES
        for (int i=0; i<OB; i++)
		{
                    ob = new Sprite();
                   // ast.setImage("file:.png");
                    ob.setHeight(40);
                    ob.setWidth(60);
                    
                    ob.setPosition((double)rand.nextInt(max-min)+min,770);
                    
                do{
                check=0; 
                  for(int f = 0; f < i; f++){
                    while(obList.get(f).intersect(ob)){
                        ob.setPosition((double)rand.nextInt(max-min)+min,770);
                        check=1;
                    }
                    
                }
                 
                } while (check==1);
                    
                    obList.add(ob);
                }
        URL path = this.getClass().getResource("/violin.wav");
        AudioClip gameOverSong = new AudioClip(path.toString());
        path = this.getClass().getResource("/theme.mp3");
        AudioClip theme = new AudioClip(path.toString());
        path = this.getClass().getResource("/points.mp3");
        AudioClip pointsSound = new AudioClip(path.toString());
        path = this.getClass().getResource("/shield.mp3");
        AudioClip shieldSound = new AudioClip(path.toString());
        //ENDS THE PROGRAM WHEN THE WINDOW CLOSES
        primaryStage.setOnCloseRequest(event -> {
            System.exit(0);
        });
        
        
        Timeline loop = new Timeline();
        KeyFrame kfc = new KeyFrame(Duration.millis(25), new EventHandler<ActionEvent>() {
            
            @Override
           public void handle(ActionEvent event) {
                
            
            gc.clearRect(0, 0, 1920, 1080);
            road.drawRoad(gc);
            back.drawBack(gc);
            Timer t = new Timer();
            Timer g = new Timer();
            
                
            
            //MAKES OBSTACLES MOVE
            if(!isPaused)
            {
            for(int i=0;i<10;i++){
                
                obList.get(i).addVelo(-100);
                
                if (obList.get(i).getVelo()<=-500)
                {
                    obList.get(i).setVelo(-500);
                }
                
                obList.get(i).update(0.01);
                obList.get(i).drawOb(gc);
            }
            //MAKES THE OBSTACLES MOVE BACK TO THE RIGHT
            for (int i=0; i<OB; i++)
		{
                    if(obList.get(i).getPostionX()<-30)
                    {
                        obList.get(i).setPosition((double)rand.nextInt(max-min)+min,770);
                        
                    }
                }
            }
            //MAKES THE Egg MOVE TO THE RIGHT
            if(eggShowing)
            {
                egg.setVelo(100);
                egg.addVelo(100);
                if (egg.getVelo()<=500)
                {
                    egg.setVelo(500);
                }
                
                egg.update(0.01);
                egg.drawEgg(gc);
            }
            //MAKES THE EGG DISAPPEAR
            for (int i=0; i<OB; i++)
		{
                    if(obList.get(i).intersect(egg))
                    {
                        egg.setPosition(-200, 770);
                        eggShowing=false;
                        obList.get(i).setPosition((double)rand.nextInt(max-min)+min,770);
                       //HIT EFFECT
                        URL path = this.getClass().getResource("/carexplosion.wav");
                        AudioClip eggexplose = new AudioClip(path.toString());
                               eggexplose.play();
                    }
                    else if (egg.getPostionX()>1920)
                    {
                        egg.setPosition(-200, 770);
                        eggShowing=false;
                        obList.get(i).setPosition((double)rand.nextInt(max-min)+min,770);
                    }
                }
            
            //DISABLES THE EGG WHEN THE PLAYER IS IN THE AIR
            if(player.getPositionY()!=770)
                playerInAir=true;
            else
                playerInAir=false;
                
            //MAKES THE PLAYER MOVE
               scene.setOnKeyPressed(f->{
                   switch (f.getCode()){
                       case DOWN:
                       if (player.getPositionY()<=769){
                       player.jump(150);
                       }
                       break;
                           
                       case UP:
                           //JUMP
                           if (player.getPositionY()>769){
                               player.setGravity(20);
                               player.jump(-60);
                               //JUMP FX
                               URL path = this.getClass().getResource("/jump.wav");
                               AudioClip jump = new AudioClip(path.toString());
                               jump.play();
                           }
                           
                           
                           //RESTART
                           if(isPaused)
                           {
                               gameOverSong.stop();
                               time=81000;
                               t.schedule(new TimerTask() {
                               @Override
                               public void run() {
                                  theme.setVolume(100);
                                  
                               theme.play();
                               
                               }}, 0, time);
                               pointsDrops=0;
                               invincDrops=0;
                               points=0;
                               lbl.setText("");
                               lblGameOver.setText("");
                               lblGameTitle.setText("");
                               seconds=250;
                               lblProjectile.setTextFill(Color.RED);
                               lblProjectile.setText("Egg Available");
                               isPaused=false;
                               eggAvailable=true;
                               isInvincible=false;
                               pointsDrop.setPosition(1940,720);
                               gc.clearRect(0, 0, 1920, 1080);
                               road.drawRoad(gc);
                               back.drawBack(gc);
                               player.drawChar(gc);
                               
                                for (int i=0; i<OB; i++)
                                {
                                                                    
                                    obList.get(i).setPosition((double)rand.nextInt(max-min)+min,770);
                        
                                }
                            
            
                               loop.playFrom(Duration.ZERO);
                            }
                           break;
                           
                           
                       case SPACE:
                           if(eggAvailable&&!playerInAir)
                           {
                            egg.setPosition(215,770);
                            lblProjectile.setTextFill(Color.RED);
                            lblProjectile.setText("Egg Available");
                            eggShowing=true;
                            eggAvailable=false;
                            //THROW FX
                            URL path = this.getClass().getResource("/throw.wav");
                            AudioClip eggthrow = new AudioClip(path.toString());
                               eggthrow.play();
                           }
                           break;
                   }
               });
               //PROJECTILE DELAY
               if(!eggAvailable){
                   seconds--;
                   lblProjectile.setTextFill(Color.BLACK);
                   lblProjectile.setText("Egg Available in: "+seconds);
                   if (seconds==0){
                       eggAvailable=true;
                       seconds=200;
                       lblProjectile.setTextFill(Color.RED);
                       lblProjectile.setText("Egg Available");
                   }
               }
               
               //HIGHEST AND LANDING IN GROUND
               if (player.getPositionY()>770)
               {
                player.setJumpZero();
                player.setPosition(200,770);
                //LANING FX
                URL path = this.getClass().getResource("/jumpland.wav");
                AudioClip land = new AudioClip(path.toString());
                land.play();
               }
               
               //THE PLAYER CAN'T GO UNDER THE GROUND
               else if (player.getPositionY()>770){
                   player.setPosition(200,770);
                   player.jump(-60);
                   jumpDisable= false;
                   //LANING FX
                   URL path = this.getClass().getResource("/jumpland.wav");
                   AudioClip land = new AudioClip(path.toString());
                    land.play();
               }
               
               
               //ENDS THE GAME WHEN THE PLAYER HITS AN OBSTACLE (GAME OVER)
               for (int i=0; i<OB; i++)
		{
                    if(player.intersect(obList.get(i))&&!isInvincible)
                    {
                        
                        egg.drawGrayEgg(gc);
                        road.drawGrayRoad(gc);
                        back.drawGrayBack(gc);
                        
                        for(int j=0;j<OB;j++)
                        {   
                            obList.get(j).drawGrayOb(gc);
                        }
                        lblProjectile.setTextFill(Color.GRAY);
                        theme.setVolume(0);
                        loop.stop();
                        lblGameOver.setText("                       Game Over \n "
                                +   "              Press UP to restart");
                        isPaused=true;
                        theme.stop();
                        time=0;
                        g.schedule(new TimerTask() {
                        @Override
                        public void run() {
                       gameOverSong.setVolume(9001);
                        gameOverSong.play();
                          
                        }}, 0, 276000);
                    }
                }
               
                //ADDS POINTS
               if(!isPaused)
               {
                   pointsDrops++;
                   invincDrops++;
                   points++;
                   lblPoints.setText("Score: "+points);
               }
               
               
              
               //SPAWNS EXTRA POINTS DROP
               if(pointsDrops>300)
               {
                
                pointsDrop.addVelo(-100);
                
                if (pointsDrop.getVelo()<=-500)
                {
                    pointsDrop.setVelo(-500);
                }
                
                pointsDrop.update(0.01);
                pointsDrop.drawPoints(gc);
               
               }
               
               //ADDS EXTRA POINTS WHEN THE PLAYER TOUCHES THE POINTS DROP
               if(player.intersect(pointsDrop))
               {
                   pointsSound.play();
                   points+=100;
                   pointsDrop.setPosition(1940, 720);
                   pointsDrops=0;
               }
               
               //MAKES POINTS DROP GO BACK TO THE RIGHT
               if(pointsDrop.getPostionX()<0)
               {
                   pointsDrops=0;
                   pointsDrop.setPosition(1940,720);
               }
               //SPAWNS INVINCIBILITY DROP
               if(invincDrops>700)
               {
               
                invincDrop.addVelo(-100);
                
                if (invincDrop.getVelo()<=-500)
                {
                    invincDrop.setVelo(-500);
                }
                
                invincDrop.update(0.01);
                invincDrop.drawInvincibility(gc);
                
               }
               
               //MAKES THE PLAYER INVINCIBLE FOR A FEW SECONDS WHEN HE TOUCHES THE SHIELD
               if(player.intersect(invincDrop))
               {
                   shieldSound.play();
                   isInvincible=true;
                   invincDrop.setPosition(1940, 720);
                   lblInvinc.setText("INVINCIBLE");
                   invincDrops=0;
               }
               
               //REMOVES INVINCIBILITY
               if(isInvincible)
               {
                   invincDuration++;
                   if(invincDuration>250)
                   {
                       isInvincible=false;
                       invincDuration=0;
                       lblInvinc.setText("");
                   }
               }
               
               //MAKES INVINCIBILITY DROP GO BACK TO THE RIGHT
               if(invincDrop.getPostionX()<0)
               {
                    invincDrops=0;
                    invincDrop.setPosition(1940,720);
               }
               //PLAYER JUMP ANIMATION AND JUMP SPEED
                player.jumpAnim(0.06);
                player.drawChar(gc);
                
                
                //SETS THE HIGH SCORE
                if(isPaused&&points>highScore)
                {
                    highScore=points;
                    lblHighScore.setText("High Score: "+highScore);
                }
                
           }});
        
      
            loop.getKeyFrames().add(kfc);
            loop.setCycleCount(Timeline.INDEFINITE);
            loop.play();
            
         
       
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

class Sprite{
        private double width;
        private double height;
	private double posx;
	private double posy;
	private double velocityX;
        private double jumpHeight;
        private double gravity;
	private Image duck=new Image(this.getClass().getResource("/player.png").toString());
        private Image road=new Image(this.getClass().getResource("/road.png").toString());
        private Image back=new Image(this.getClass().getResource("/back.png").toString());
        private Image egg=new Image(this.getClass().getResource("/egg.png").toString());
        private Image car=new Image(this.getClass().getResource("/car.png").toString());
        private Image points=new Image(this.getClass().getResource("/points.png").toString());
        private Image shield=new Image(this.getClass().getResource("/shield.png").toString());
        private Image grayroad=new Image(this.getClass().getResource("/gray/road.png").toString());
        private Image grayback=new Image(this.getClass().getResource("/gray/back.png").toString());
        private Image grayegg=new Image(this.getClass().getResource("/gray/egg.png").toString());
        private Image graycar=new Image(this.getClass().getResource("/gray/car.png").toString());
        
        private int fW=64, fL=40;//Player
        private int obsFW= 93,obsFL=49;//Obstacles
        private int cF=1;
        
        

    public void setPosition(double x, double y){
	posx=x;
        posy=y;
    }
    
    public double getPostionX(){
        return posx;
    }
    
    public double getPositionY()
    {
        return posy;
    }
    
    public void setVelo(double x){
	velocityX=x;
    }
    
     public void setWidth(double w){
	width=w;
    }
     
      public void setHeight(double h){
        height=h;
    }

    public double getVelo(){
        return velocityX;
    }
    public void addVelo(double x){
	velocityX+=x;
    }
    
    public void jump(double y)
    {
        jumpHeight+=y;
    }
    
    public void update(double time){
	posx += velocityX * time;
    }
    
    public void jumpAnim(double time)
    {
        posy += jumpHeight * time;
        jumpHeight+= gravity * time;
    }

    public Rectangle2D getBound(){
	return new Rectangle2D(posx,posy,width,height);
    }
   

    boolean intersect (Sprite s){
        return (s.getBound().intersects(this.getBound()));
    }
    
    public void drawOb(GraphicsContext gc){
                int fx= cF%4*obsFW;   
                int fy= cF/4*obsFL;
                gc.drawImage(car, fx, fy, obsFW, obsFL, this.getPostionX(), this.getPositionY(), 93, 49);
               cF++;
                if (cF>3)
                {
                    cF=0;
                }
                
    }
    public void drawGrayOb(GraphicsContext gc){
        int fx= cF%4*obsFW;   
                int fy= cF/4*obsFL;
                gc.drawImage(graycar, fx, fy, obsFW, obsFL, this.getPostionX(), this.getPositionY(), 93, 49);
               cF++;
                if (cF>3)
                {
                    cF=0;
                }
    }
    public void drawChar(GraphicsContext gc)
    {
                int fx= cF%4*fW;   
                int fy= cF/4*fL;
                gc.drawImage(duck, fx, fy, fW, fL, this.getPostionX(), this.getPositionY(), fW, fL);
                cF++;
                if (cF>3)
                {
                    cF=0;
                }
                
    }
    
    public void drawEgg(GraphicsContext gc)
    {
                int fx= 0%1*25;   
                int fy= 0/1*25;
                gc.drawImage(egg, fx, fy, 25, 25, this.getPostionX(), this.getPositionY(), 25, 25);
    }
    
    public void drawGrayEgg(GraphicsContext gc){
        int fx= 0%1*25;   
                int fy= 0/1*25;
                gc.drawImage(grayegg, fx, fy, 25, 25, this.getPostionX(), this.getPositionY(), 25, 25);
    }
    public void drawRoad(GraphicsContext gc)
    {
                int fx= cF%4*fW;   
                int fy= cF/4*fL;
                gc.drawImage(road, fx, fy, 1920, 300, this.getPostionX(), this.getPositionY(), 1920, 300);
                
    }
    public void drawGrayRoad(GraphicsContext gc){
        int fx= cF%4*fW;   
                int fy= cF/4*fL;
                gc.drawImage(grayroad, fx, fy, 1920, 300, this.getPostionX(), this.getPositionY(), 1920, 300);
    }
    public void drawBack(GraphicsContext gc)
    {
                int fx= cF%4*fW;   
                int fy= cF/4*fL;
                gc.drawImage(back, fx, fy, 1920, 780, this.getPostionX(), this.getPositionY(), 1920, 780);
                
    }
    public void drawGrayBack(GraphicsContext gc){
        int fx= cF%4*fW;   
                int fy= cF/4*fL;
                gc.drawImage(grayback, fx, fy, 1920, 780, this.getPostionX(), this.getPositionY(), 1920, 780);
    }
    public void drawPoints(GraphicsContext gc)
    {
                int fx= 0%1*35;   
                int fy= 0/1*35;
                gc.drawImage(points, fx, fy, 35, 35, this.getPostionX(), this.getPositionY(), 35, 35);
    }
    
    public void drawInvincibility(GraphicsContext gc)
    {
        int fx= 0%1*35;   
                int fy= 0/1*35;
                gc.drawImage(shield, fx, fy, 35, 35, this.getPostionX(), this.getPositionY(), 35, 35);
    }
    
    
    public void setJumpZero(){
        jumpHeight=0;
        gravity=0;
    }
    public void setGravity(double g){
        gravity = g;
    }
    
     
    
}