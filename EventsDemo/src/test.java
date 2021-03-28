import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class test 
{

	public static void main(String[] args)
	{
//		try {
//	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("data/audio/GameTwoSoundtrack.wav"));
//	        Clip clip = AudioSystem.getClip();
//	        clip.open(audioInputStream);
//	        clip.start();
//	        if( clip.isRunning())
//	        	System.out.println("running");
//	        System.out.println("Played");
//	    } catch(Exception ex) {
//	        System.out.println("Error with playing sound.");
//	        ex.printStackTrace();
//	    }
		
		long start = System.currentTimeMillis();
		
		while(true) {
			long end = System.currentTimeMillis();
			long interval = end - start;
			
			long seconds = interval/1000;
			long minutes = seconds/60;
			seconds = seconds%60;
			
			System.out.print( minutes<10? "0" : "");
			System.out.print( minutes );
			System.out.print(":");
			System.out.print( seconds<10? "0" : "");
			System.out.println( seconds );
		}
	}

}
