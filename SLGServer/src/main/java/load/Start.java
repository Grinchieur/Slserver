package load;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;


public class Start extends Listener {
	static Map<Integer, PlayerActionSer> connectedPlayer = new HashMap<Integer, PlayerActionSer>();
	//static List<> ;
	static Server server ;
	
	static int counter = 0;
	/*public static void addListenerTO(Connection c) {
		ConnectionPlayer.add(c);
		
		
		ConnectionPlayer.get(ConnectionPlayer.size() -1)
			.addListener(new Listener() {
	        public void received (Connection connection, Object object) {
	        	 if (object instanceof PlayerActionSer) {
	        		 System.out.println("in listener connectionPlayer");
		        	   //reçoi les données de chaques clients.
		        	   PlayerActionSer recu = (PlayerActionSer)object;
		        	   boolean found = false;
		        	   int i = 0;
		        	   int j;
		        	   System.out.println("recu PAS from : " + recu.getId());
		        	   recu.printPlayerServ();
		        	   
		        	   while(!found && i < connectedPlayer.size()) {
		        		   System.out.println("While i : " + i);
			            	  if(connectedPlayer.get(i).getId() == recu.getId())
			            	  {
			            		  found = true;
			            		  System.out.println("FOUND !!!!!");
			            	  }
			            	  else {
			            		  i++;
			            	  }
			              }
		        	   if (found) {
		        		   //connectedPlayer.set(i, recu);
		        		   System.out.println("IN FOUND !!!!!");
		        		   connectedPlayer.get(i).updatePAS(recu);
		        		   connectedPlayer.get(i).printPlayerServ();
		        		   j = i;
		        		   for (i = 0; i < connectedPlayer.size(); i++)
		 		          {
		        			   
		        			   
		        			  if (i != j) {
		        				  System.out.println("sending to player : "+i);
		        				  connectedPlayer.get(i).printPlayerServ();
		        				  ConnectionPlayer.get(j).sendUDP(connectedPlayer.get(i));
		        				  System.out.println("CONNECTION : " + ConnectionPlayer.get(j).toString());
		        			  }
		 		        	  
		 		          }
		        	   }
		        	   
			          
		              
		           }
		           
		           
		        }
		        
			   
		     
	        });
		
		
	}*/
	public static void main(String[] args) throws IOException {
		
		
		//connectedPlayer = new ArrayList<PlayerActionSer>();
		//ConnectionPlayer = new ArrayList<Connection>();
		server = new Server();
		Kryo kryo = server.getKryo();
	    kryo.register(PacketRemovePlayer.class);
	    kryo.register(AddPlayer.class);
	    kryo.register(PlayerActionSer.class);
	    kryo.register(RequestConnection.class);
	    kryo.register(AcceptedConnection.class);
	    
	    server.start();
	    server.bind(54555, 54556);
	    
	    
	    System.out.println("yo");
	    server.addListener(new MyListener(connectedPlayer, server));
	    Tickrate serverTickrate = new Tickrate(server, connectedPlayer);
	    serverTickrate.start();
	}
	    
	    
	 /*  server.addListener(new Listener() {
	        public void received (Connection connection, Object object) {
	           if (object instanceof SomeRequest) {
	        	   //Verify if the id is already here, if yes, it's bounce back a error
	        	   //Yes it add it to the playerlist
	        	   SomeResponse response = new SomeResponse();
	        	   int i= 0;
	        	   boolean alreadyConnec = false;
	              SomeRequest request = (SomeRequest)object;
	              System.out.println(request.idSend);
	              if(connectedPlayer.size() == 0) {
	            	  PlayerActionSer temp = new PlayerActionSer();
	            	  temp.setId(request.idSend);
	            	  response.text = "OK";
	            	  connectedPlayer.add(temp);
	            	  addListenerTO(connection);
	            	  
	            	  
	              }
	              else {
	            	  while(!alreadyConnec && i < connectedPlayer.size()) {
		            	  if(connectedPlayer.get(0).getId() == request.idSend)
		            	  {
		            		  alreadyConnec = true;
		            	  }
		            	  else {
		            		  i++;
		            	  }
		              }
	            	  if(alreadyConnec) {
		            	  response.text = "IDALREADYHERE";
		              }
		              else {
		            	  PlayerActionSer temp = new PlayerActionSer();
		            	  temp.setId(request.idSend);
		            	  connectedPlayer.add(temp);
		            	  ConnectionPlayer.add(connection);
		            	  response.text = "OK";
		              }
	              }
	              
	              
	     
	              
	              
	              ConnectionPlayer.get(i).sendTCP(response);
	              
	              System.out.println("CONNECTION : " +ConnectionPlayer.get(i).toString());
	              for (i = 0; i < (connectedPlayer.size()); i++)
	              {
	            	  System.out.println("dqsdqsd");
	            	  ConnectionPlayer.get(i).sendTCP(connectedPlayer.get(i));
	            	  System.out.println("dqsdqsd\n");
	              }
	           }
	           
	           
	    
	    
	    server.addListener(new Listener() {
	        public void received (Connection connection, Object object) {
	           if (object instanceof SomeRequest) {
	        	   //Verify if the id is already here, if yes, it's bounce back a error
	        	   //Yes it add it to the playerlist
	        	   SomeResponse response = new SomeResponse();
	        	   int i= 0;
	        	   boolean alreadyConnec = false;
	              SomeRequest request = (SomeRequest)object;
	              System.out.println(request.idSend);
	              if(connectedPlayer.size() == 0) {
	            	  PlayerActionSer temp = new PlayerActionSer();
	            	  temp.setId(request.idSend);
	            	  response.text = "OK";
	            	  connectedPlayer.add(temp);
	            	  ConnectionPlayer.add(connection);
	              }
	              else {
	            	  while(!alreadyConnec && i < connectedPlayer.size()) {
		            	  if(connectedPlayer.get(0).getId() == request.idSend)
		            	  {
		            		  alreadyConnec = true;
		            	  }
		            	  else {
		            		  i++;
		            	  }
		              }
	            	  if(alreadyConnec) {
		            	  response.text = "IDALREADYHERE";
		              }
		              else {
		            	  PlayerActionSer temp = new PlayerActionSer();
		            	  temp.setId(request.idSend);
		            	  connectedPlayer.add(temp);
		            	  ConnectionPlayer.add(connection);
		            	  response.text = "OK";
		              }
	              }
	              
	              
	     
	              
	              
	              ConnectionPlayer.get(i).sendTCP(response);
	              
	              System.out.println("CONNECTION : " +ConnectionPlayer.get(i).toString());
	              for (i = 0; i < (connectedPlayer.size() -1); i++)
	              {
	            	  System.out.println("dqsdqsd\n\n\n\n");
	            	  connection.sendTCP(connectedPlayer.get(i));
	              }
	           }
	           if (object instanceof PlayerActionSer) {
	        	   //reçoi les données de chaques clients.
	        	   PlayerActionSer recu = (PlayerActionSer)object;
	        	   boolean found = false;
	        	   int i = 0;
	        	   int j;
	        	   System.out.println("recu PAS");
	        	   recu.printPlayerServ();
	        	   
	        	   while(!found && i < connectedPlayer.size()) {
	        		   System.out.println("While i : " + i);
		            	  if(connectedPlayer.get(i).getId() == recu.getId())
		            	  {
		            		  found = true;
		            		  System.out.println("FOUND !!!!!");
		            	  }
		            	  else {
		            		  i++;
		            	  }
		              }
	        	   if (found) {
	        		   //connectedPlayer.set(i, recu);
	        		   System.out.println("IN FOUND !!!!!");
	        		   connectedPlayer.get(i).updatePAS(recu);
	        		   connectedPlayer.get(i).printPlayerServ();
	        		   j = i;
	        		   for (i = 0; i < connectedPlayer.size(); i++)
	 		          {
	        			   System.out.println("\n i = "+ i);
	        			   System.out.println("j = "+ j +"\n");
	        			   connectedPlayer.get(i).printPlayerServ();
	        			  if (i != j) {
	        				  System.out.println("sending ");
	        				  connectedPlayer.get(i).printPlayerServ();
	        				  ConnectionPlayer.get(j).sendUDP(connectedPlayer.get(i));
	        				  System.out.println("CONNECTION : " + ConnectionPlayer.get(j).toString());
	        			  }
	 		        	  
	 		          }
	        	   }
	        	   
		          
	              
	           }
	           addCounter();
	           if(getCounter() > 20000) {
	        	   System.exit(0);
	           }
	           
	        }
	        
		   
	     });
	    
	    
	}
	public static void addCounter() {
		counter++;
	}
	public static int getCounter() {
		return counter;
	}
    
     
	        }
	      
	    }); 
	}
	public void connected(Connection c){
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
			c.sendUDP(newPlayer);
			connectedPlayer.put(c.getID(), newConnected);
			System.out.println("Connection received 2.");
		}
		else {
			for(PlayerActionSer p : connectedPlayer.values()){
				AddPlayer newPlayer = new AddPlayer();
				newPlayer.id = p.c.getID();
				newPlayer.x = p.getPosXPlayer();
				newPlayer.y = p.getPosYPlayer();
				c.sendUDP(newPlayer);
				connectedPlayer.put(c.getID(), newConnected);
				System.out.println("Connection received 2.");
			}
		}
		
	}
	
	public void received(Connection c, Object o){
		if(o instanceof PlayerActionSer){
			PlayerActionSer packet = (PlayerActionSer) o;
			connectedPlayer.get(c.getID()).updatePAS(packet);
			server.sendToUDP(c.getID(), packet);
			System.out.println("received and sent an update to all packet");
		}
			
		
	}
	
	public void disconnected(Connection c){
		connectedPlayer.remove(c.getID());
		PacketRemovePlayer packet = new PacketRemovePlayer();
		packet.id = c.getID();
		server.sendToAllExceptTCP(c.getID(), packet);
		System.out.println("Connection dropped.");
	}*/

	  
}

