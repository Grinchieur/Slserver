package load;

import java.util.Map;

import com.esotericsoftware.kryonet.Server;

public class Tickrate extends Thread {
	Server server = null;
	Map<Integer, PlayerActionSer> player = null;
	public Tickrate(Server server, Map<Integer, PlayerActionSer> connectedPlayer) {
		this.server = server;
		this.player = connectedPlayer;
	}
	
	public void run( ) {
		try {
			while(true) {
				Thread.sleep(500);
				if(player.size() != 0) {
					for(PlayerActionSer p : player.values() )
					
					server.sendToAllUDP(p);
					
				}
			}
			
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
