package load;

import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class MyListener extends  Listener{
	Map<Integer, PlayerActionSer> connectedPlayer = null;
	Server server = null;
	public MyListener(Map<Integer, PlayerActionSer> connectedMap, Server ser){
		connectedPlayer = connectedMap;
		server = ser;
		
	}
	
	/*public void connected(Connection c){
		
		
		PlayerActionSer newConnected = new PlayerActionSer();
		newConnected.c = c;
		newConnected.setId(c.getID());
		System.out.println("Connection received 1.");
		if(connectedPlayer.size() == 0)
		{
			AddPlayer newPlayer = new AddPlayer();
			newPlayer.id = c.getID();
			newPlayer.x = 300;
			newPlayer.y = 300;
			
			connectedPlayer.put(c.getID(), newConnected);
			System.out.println("Connection received 2.");
		}
		else {
			for(PlayerActionSer p : connectedPlayer.values()){
				if(!(p.c == c)) {
					
				
				AddPlayer newPlayer = new AddPlayer();
				newPlayer.id = p.c.getID();
				newPlayer.x = p.getPosXPlayer();
				newPlayer.y = p.getPosYPlayer();
				
				c.sendTCP(newPlayer);
				connectedPlayer.put(c.getID(), newConnected);
				}
				System.out.println("Connection received 3.");
			}
		}
		
	}*/
	
	
	public void received(Connection c, Object o){
		if(o instanceof PlayerActionSer){
			PlayerActionSer packet = (PlayerActionSer) o;
			connectedPlayer.get(packet.getId()).updatePAS(packet);
			server.sendToAllExceptTCP(c.getID(), packet);
			System.out.println(" PAS RECU BY " +c.getID());
			
			System.out.println("received and sent an update to all packet");
			
			
		}
		if (o instanceof RequestConnection){
			RequestConnection connectRequest = (RequestConnection) o;
			if (connectedPlayer.size() == 0)
			{
				System.out.println(" size = 0 " +c.getID());
				AddPlayer newPlayer = new AddPlayer();
				newPlayer.setId(connectRequest.id);
				newPlayer.setX(300);
				newPlayer.setY(300);
				PlayerActionSer tempPAS = new PlayerActionSer(newPlayer);
				connectedPlayer.put(connectRequest.id, tempPAS);
				server.sendToAllExceptTCP(c.getID(), newPlayer);//TCP Car cette information est primordial et doit absolument être reçu par les autres joueurs.
				AcceptedConnection accepted = new AcceptedConnection();
				accepted.id = connectRequest.id;
				accepted.x = 300;
				accepted.y = 300;
				c.sendTCP(accepted);
				
			}
			else {
				System.out.println(" size >1  " +c.getID());
				if(connectedPlayer.containsKey(connectRequest.id))
				{
					System.out.println(" ERREUR ID DEJA PRESENT " + c.getID());
				}
				else {
					System.out.println(" dans else " +c.getID());
					AddPlayer newPlayer = new AddPlayer();
					newPlayer.setId(connectRequest.id);
					newPlayer.setX(300);
					newPlayer.setY(300);
					PlayerActionSer tempPAS = new PlayerActionSer(newPlayer);
					connectedPlayer.put(connectRequest.id, tempPAS);
					server.sendToAllExceptTCP(c.getID(), newPlayer);
					AcceptedConnection accepted = new AcceptedConnection();
					accepted.id = connectRequest.id;
					accepted.x = 300;
					accepted.y = 300;
					c.sendTCP(accepted);
					for(PlayerActionSer p : connectedPlayer.values()){
						newPlayer.setId(p.getId());
						newPlayer.setX(p.getPosXPlayer());
						newPlayer.setY(p.getPosYPlayer());
						server.sendToTCP(c.getID(), newPlayer);
					}
				}
			}
		}
		
			
		
			
		
	}
	
	public void disconnected(Connection c){
		connectedPlayer.remove(c.getID());
		PacketRemovePlayer packet = new PacketRemovePlayer();
		packet.id = c.getID();
		server.sendToAllExceptTCP(c.getID(), packet);
		System.out.println("Connection dropped.");
	}

}
