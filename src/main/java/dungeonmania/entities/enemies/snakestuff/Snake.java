package dungeonmania.entities.enemies.snakestuff;


import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public abstract class Snake extends Enemy {
    private double arrowBuff;
    private double treasureBuff;
    private double keyBuff;

    private Snake prevPart;
    private Snake nextPart;

    // next position to move to
    private Position nextMovePos;
    // next position to add to
    private Position nextAddPos = null;

    private boolean invis;
    private boolean invinc;

    private boolean firstPart;

    public Snake(
        Position position, double health, double attack, double arrowBuff, double treasureBuff,
        double keyBuff, Position nextMovePos, Snake nextPart, Snake prevPart, Boolean invis, Boolean invinc
    ) {
        super(position, health, attack);
        this.arrowBuff = arrowBuff;
        this.treasureBuff = treasureBuff;
        this.keyBuff = keyBuff;

        this.nextPart = nextPart;
        this.prevPart = prevPart;

        this.nextMovePos = nextMovePos;

        this.invinc = invinc;
        this.invis = invis;
    }

    public void addBody(GameMap map) {
        if (nextPart == null) {
            SnakeBody newBody;
            if (prevPart == null) {
                newBody = new SnakeBody(
                getNextAddPos(), getHealth(), getAttack(), arrowBuff, treasureBuff,
                keyBuff, getNextAddPos(), null, this, invis, invinc
            );
            } else {
                newBody = new SnakeBody(
                getNextAddPos(), getHealth(), getAttack(), arrowBuff, treasureBuff,
                keyBuff, getPosition(), null, this, invis, invinc
                );
            }
            nextPart = newBody;
            map.addEntity(newBody);
            firstPart = true;
        } else {
            nextPart.addBody(map);
        }
    }

    public boolean getFirstPart() {
        return firstPart;
    }

    public void setFirstPart(boolean help) {
        firstPart = help;
    }

    public Position getNextMovePos() {
        return nextMovePos;
    }

    public Position getNextAddPos() {
        // find the next add position of the last item
        if (nextPart == null) {
            return nextAddPos;
        } else {
            return nextPart.getNextAddPos();
        }
    }

    public Snake getNextPart() {
        return nextPart;
    }

    public void setNextMovePos(Position nextPos) {
        this.nextMovePos = nextPos;
    }

    public void setNextAddPos(Position nextPos) {
        this.nextAddPos = nextPos;
    }

    public void setNextPart(SnakeBody nextPart) {
        this.nextPart = nextPart;
    }

    public boolean getInvisStatus() {
        return invis;
    }

    public boolean sameSnake(Snake snake) {
        if (!this.equals(snake) && getNextPart() == null) {
            return false;
        } else if (this.equals(snake)) {
            return true;
        }
        return getNextPart().sameSnake(snake);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return entity instanceof Player || (entity instanceof Snake && invis && !((Snake) entity).sameSnake(this));
    }
    public void onDestroy(GameMap map) {
        Game g = map.getGame();
        g.unsubscribe(getId());

        if (prevPart == null) {
            // if at the head piece, remove invincibility so entire snake is destroyed
            invincChangeSnake(false);
        } else {
            prevPart.setNextPart(null);
        }

        if (nextPart != null && invinc) {
            // remove snakebody to be replaced with new head
            map.removeNode(nextPart);
            g.unsubscribe(nextPart.getId());
            // make new head, attach tail to it
            SnakeHead newHead = new SnakeHead(
                nextPart.getPosition(), getBattleStatistics().getHealth(), getBattleStatistics().getAttack(),
                arrowBuff, treasureBuff, keyBuff, nextPart.getPosition(), nextPart.getNextPart(), null, invis, invinc
            );
            map.addEntity(newHead);
            g.register(() -> newHead.move(map, map.getPlayer()), Game.AI_MOVEMENT, newHead.getId());
        } else if (nextPart != null) {
            // remove rest of snake
            map.destroyEntity(nextPart);
            if (prevPart != null) {
                prevPart.setNextPart(null);
            }
            nextPart = null;
        }
    }

    private void addArrowBuff(BattleStatistics battleStatistics) {
        battleStatistics.setAttack(battleStatistics.getAttack() + arrowBuff);
    }

    private void addTreasureBuff(BattleStatistics battleStatistics) {
        battleStatistics.setHealth(battleStatistics.getHealth() + treasureBuff);
    }

    private void addKeyBuff(BattleStatistics battleStatistics) {
        battleStatistics.setHealth(battleStatistics.getHealth() * keyBuff);
    }

    public void arrowBuffSnake() {
        addArrowBuff(getBattleStatistics());
        if (nextPart != null) {
            nextPart.arrowBuffSnake();
        }
    }

    public void treasureBuffSnake() {
        addTreasureBuff(getBattleStatistics());
        if (nextPart != null) {
            nextPart.treasureBuffSnake();
        }
    }

    public void keyBuffSnake() {
        addKeyBuff(getBattleStatistics());
        if (nextPart != null) {
            nextPart.keyBuffSnake();
        }
    }

    public void invisBuffSnake() {
        invis = true;
        if (nextPart != null) {
            nextPart.invisBuffSnake();
        }
    }

    public void invincChangeSnake(boolean newState) {
        invinc = newState;
        if (nextPart != null) {
            nextPart.invincChangeSnake(newState);
        }
    }

}
