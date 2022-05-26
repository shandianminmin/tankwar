package tankwar;


import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;


public class Music {
	private String musicPath;
	private volatile boolean run = true;  
	private Thread mainThread;  
	
	private AudioInputStream audioStream;
	private AudioFormat audioFormat;
	private SourceDataLine sourceDataLine;
	
	public Music(String musicPath) {
		this.musicPath = musicPath;
		prefetch();
	}
	private void prefetch(){
		try{
	    audioStream = AudioSystem.getAudioInputStream(new File(musicPath));
		audioFormat = audioStream.getFormat();
		DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,
				audioFormat,AudioSystem.NOT_SPECIFIED);
		sourceDataLine = (SourceDataLine)AudioSystem.getLine(dataLineInfo);
		
		sourceDataLine.open(audioFormat);
		sourceDataLine.start();
		
		}catch(UnsupportedAudioFileException ex){
			ex.printStackTrace();
		}catch(LineUnavailableException ex){
			ex.printStackTrace();
		}catch(IOException ex){
			ex.printStackTrace();
		}
		
	}
	protected void finalize() throws Throwable{
		super.finalize();
		sourceDataLine.drain();
		sourceDataLine.close();
		audioStream.close();
	}
	private void playMusic(boolean loop)throws InterruptedException {
		try{
				if(loop){
					while(true){
						playMusic();
					}
				}else{
					playMusic();
					sourceDataLine.drain();
					sourceDataLine.close();
					audioStream.close();
				}
			
		}catch(IOException ex){
			ex.printStackTrace();
		}
		
		
	}
	private void playMusic(){
		try{
			synchronized(this){
				run = true;
			}
			audioStream = AudioSystem.getAudioInputStream(new File(musicPath));
			int count;
			byte tempBuff[] = new byte[1024];
			
				while((count = audioStream.read(tempBuff,0,tempBuff.length)) != -1){
					synchronized(this){
					while(!run)
						wait();
					}
					sourceDataLine.write(tempBuff,0,count);		
			}
 
		}catch(UnsupportedAudioFileException ex){
			ex.printStackTrace();
		}catch(IOException ex){
			ex.printStackTrace();
		}catch(InterruptedException ex){
			ex.printStackTrace();
		}
		
	}
	private void stopMusic(){
		synchronized(this){
			run = false;
			notifyAll();
		}
	}
	private void continueMusic(){
		synchronized(this){
			 run = true;
			 notifyAll();
		}
	}
	public void start(boolean loop){
		mainThread = new Thread(new Runnable(){
			public void run(){
				try {
					playMusic(loop);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		mainThread.start();
	}
	public void stop(){
		new Thread(new Runnable(){
			public void run(){
				stopMusic();
				
			}
		}).start();
	}
	public void continues(){
		new Thread(new Runnable(){
			public void run(){
				continueMusic();
			}
		}).start();
		}
}