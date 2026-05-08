import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Som {
	
	public static void tocar(String caminho) {
		
		try {
			URL url = Som.class.getResource(caminho);
			AudioInputStream audio = AudioSystem.getAudioInputStream(url);
			
			Clip clip = AudioSystem.getClip();
			clip.open(audio);
			clip.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
