package fr.utbm.tr54.robot;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import lejos.utility.Delay;

/**
 * Follow the track and stop before hitting obstacles (other robot)
 * @author rdulieu
 *
 */
public class MainTest {
	
	private static boolean isOrange=false;
	
	public static void main(String[] args) throws IOException {
		
		Pilot.init(60, 23, 40);
		LEDController.switchOff();
		SensorController sControl = new SensorController();
    
	 	sControl.start();

		Delay.msDelay(500);


		while(true){

			if(Pilot.distance(5) < 0.20f){
				Pilot.stop();
			}else if (Pilot.distance(5) >= 0.20f){
				
				if(sControl.sample.isBlue()){
					Pilot.forward();
				}else{
					Pilot.set_speed(60);
					
					if(sControl.sample.isWhite()){
						Pilot.turn_right();
						while(sControl.sample.isWhite()){
							Pilot.turn_right();
						}
						
					}else if(sControl.sample.isBlack()){
						Pilot.turn_left();
						while(sControl.sample.isBlack()){
							Pilot.turn_left();
						}
						
					}else if(sControl.sample.isOrange()){
						Pilot.forward();
						if(isOrange){
							LEDController.switchGreen();
						}else{
							LEDController.switchOrange();
						}
						isOrange=!isOrange;
					}else{
						Pilot.set_speed(50);
					}

				}
				
			}

		}
	}
}
