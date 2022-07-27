package dungeon;

import java.awt.Point;

import battles.BattleSystem;
import battles.GenerateMonster;
import battles.Monster;

/**
 * Generates a room that contains a Monster.
 * 
 * @author Colton Wickens
 * @version July 25, 2022
 */
public class MonsterRoom extends Room {
	//private boolean myIsVisited;
	private Monster myMonster;
	
	public MonsterRoom(final String theContents, final Point theCoords) {
		super(theContents, theCoords);
		//myIsVisited = false;
		myMonster = GenerateMonster.generate();
	}
	public MonsterRoom(final Room theOther) {
		super(theOther);
		//myNeighbors = theOther.myNeighbors.clone();
		myMonster = GenerateMonster.generate();
	}
	
	public Monster getMonster() {
		return myMonster;
	}
//	public boolean getVisited() {
//		return myIsVisited;
//	}
	public void visit(final Player p) {
		if (!super.getVisited()) {
			BattleSystem.doBattle(p.getChar(), myMonster);
			super.visit(p);
		}
	}

}
