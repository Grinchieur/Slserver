package load;

import com.esotericsoftware.kryonet.Connection;



public class PlayerActionSer{
	private int id;
	private float posXPlayer = 300;
	private float posYPlayer = 300;
	private int direction = 2;
	private boolean onStair = false;
	private boolean attk = false;
	
	private boolean moving = false;
	Connection c;
	public PlayerActionSer(AddPlayer newPlayer){
		id = newPlayer.getId();
		posXPlayer = newPlayer.getX();
		posYPlayer = newPlayer.getY();
	}
	public PlayerActionSer(){
		
	}

	
	
	public float getPosXPlayer() {
		return posXPlayer;
	}
	public void setPosXPlayer(float posXPlayer) {
		this.posXPlayer = posXPlayer;
	}
	public float getPosYPlayer() {
		return posYPlayer;
	}
	public void setPosYPlayer(float posYPlayer) {
		this.posYPlayer = posYPlayer;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public boolean isOnStair() {
		return onStair;
	}
	public void setOnStair(boolean onStair) {
		this.onStair = onStair;
	}
	public boolean isMoving() {
		return moving;
	}
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isAttk() {
		return attk;
	}
	public void setAttk(boolean attk) {
		this.attk = attk;
	}
	public void printPlayerServ() {
		 System.out.println("id = " + this.getId() );
         System.out.println("x = " + this.getPosXPlayer() );
         System.out.println("y = " + this.getPosYPlayer() );
         System.out.println("Dir = " + this.getDirection() );
         System.out.println("isMoving = " + this.isMoving() );
	}
	public void updatePAS(PlayerActionSer temp) {
		System.out.println("temp print :\n");
		temp.printPlayerServ();
		System.out.println("\n");
		this.setPosXPlayer(temp.getPosXPlayer());
		this.setPosYPlayer(temp.getPosYPlayer());
		this.setDirection(temp.getDirection());
		this.setMoving(temp.isMoving());
		this.setAttk(temp.isAttk());
		
	}
}
