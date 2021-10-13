import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.Window;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Level1 extends Level{
    private final int LEVEL1_POINT_THRESHOLD = 30;
    private final int STARTING_LIVES = 6;
    private final int LEVEL1_PIPE_TYPES = 2;
    private final Point SHOOT_INSTRUCT_POINT = new Point((SCREEN_WIDTH-INSTRUCTIONS_WIDTH)/2,
            (SCREEN_HEIGHT-FONT_HEIGHT)/2 + 68);

    private final int WEAPON_TYPES = 2;
    private final int WEAPON_SPAWN_SPEED_MIN = 80;
    private final int WEAPON_SPAWN_SPEED_MAX = 130;
    private int weaponType;
    private int weaponSpawnSpeed;
    private Integer equippedItemIndex;

    // Images of Bird
    private final bagel.Image BIRD_IMAGE_UP1 = new bagel.Image("res/level-1/birdWingUp.png");
    private final bagel.Image BIRD_IMAGE_DOWN1 = new Image("res/level-1/birdWingDown.png");

    // Weapons
    private ArrayList<Weapon> weapons = new ArrayList<Weapon>();
    private int leastRecentWeaponNum = 0;
    private int mostRecentWeaponNum = -1;
    private int weaponNum = 0;
    private Point weaponPoint;

    public Level1() {
        super();
        super.lifebar = new Lives(STARTING_LIVES);
        super.bird = new Bird(BIRD_SPAWN.x, BIRD_SPAWN.y, BIRD_IMAGE_UP1, BIRD_IMAGE_DOWN1);
        super.levelThreshold = LEVEL1_POINT_THRESHOLD;
        super.entityTimeScale.resetEntitySpeed();
    }

    protected void loseLife() {
        lifebar.loseLife();;
        bird = new Bird(BIRD_SPAWN.x, BIRD_SPAWN.y, BIRD_IMAGE_UP1, BIRD_IMAGE_DOWN1);
    }

    @Override
    public void updateStart(Input input) {
        super.updateStart(input);
        FONT.drawString("PRESS S TO SHOOT", SHOOT_INSTRUCT_POINT.x, SHOOT_INSTRUCT_POINT.y);
    }

    @Override
    public void updateRunning(Input input) {
        // Spawn Pipes
        if (frameCount % spawnSpeed == 0) {
            int rand_pipe_type = ThreadLocalRandom.current().nextInt(LEVEL1_PIPE_TYPES);
            if(rand_pipe_type == 0) {
                pipeSetArray.add(pipeSetNum, new PlasticPipe());
            } else {
                pipeSetArray.add(pipeSetNum, new SteelPipe());
            }
            pipeSetNum++;
            mostRecentPipeSetNum++;

            int rand_position = ThreadLocalRandom.current().nextInt(MAX_SPAWN, MIN_SPAWN+1);
            super.pipeSetArray.get(mostRecentPipeSetNum).spawnEntitySet(rand_position);
        }


        weaponSpawnSpeed = ThreadLocalRandom.current().nextInt(WEAPON_SPAWN_SPEED_MIN, WEAPON_SPAWN_SPEED_MAX+1);
        weaponType = ThreadLocalRandom.current().nextInt(WEAPON_TYPES);
        if (frameCount%weaponSpawnSpeed == 0 ) {
            int rand_position = ThreadLocalRandom.current().nextInt(MAX_SPAWN, MIN_SPAWN+1);
            if(weaponType == 0) {
                weapons.add(weaponNum, new Rock(rand_position));
            } else {
                weapons.add(weaponNum, new Bomb(rand_position));
            }
            mostRecentWeaponNum++;
            weaponNum++;
        }

        super.updateRunning(input);

        for (int j = leastRecentWeaponNum; j <= mostRecentWeaponNum; j++) {
            for (int i = leastRecentPipeSetNum; i <= mostRecentPipeSetNum; i++) {
                if (!weapons.get(j).checkDisabled() &&
                        (weapons.get(j).getWeaponHitBox().intersects(pipeSetArray.get(i).getUpHitBox()) ||
                                weapons.get(j).getWeaponHitBox().intersects(pipeSetArray.get(i).getDownHitBox()))) {
                    weapons.get(j).disableWeapon();
                    leastRecentWeaponNum++;
                }
            }
        }

        for (int j = leastRecentWeaponNum; j <= mostRecentWeaponNum; j++) {
            if (!weapons.get(j).checkDisabled() && !weapons.get(j).checkIfHeld()) {
                weapons.get(j).updateEntity();
            }
        }

        if(bird.isEquipped()) {
            if(bird.getIsUp()) {
                weapons.get(equippedItemIndex).updateHeldWeapon(birdHitBox1);
            } else {
                weapons.get(equippedItemIndex).updateHeldWeapon(birdHitBox2);
            }
        }

        if(input.wasPressed(Keys.S)) {
            weapons.get(equippedItemIndex).shootWeapon();
            bird.unequip();
        }

        if(equippedItemIndex != null && !weapons.get(equippedItemIndex).checkDisabled()
                && weapons.get(equippedItemIndex).isBeingShot()){
            weapons.get(equippedItemIndex).updateShootingWeapon();

            for (int i = leastRecentPipeSetNum; i <= mostRecentPipeSetNum; i++) {
                if (weapons.get(equippedItemIndex).getWeaponHitBox().intersects(pipeSetArray.get(i).getUpHitBox()) ||
                        weapons.get(equippedItemIndex).getWeaponHitBox().intersects(pipeSetArray.get(i).getDownHitBox())) {
                    System.out.println("HIT!");
                    if(weapons.get(equippedItemIndex) instanceof Bomb ||  pipeSetArray.get(i) instanceof PlasticPipe) {
                        System.out.println("BREAK!");
                        score ++;
                        pipeSetArray.get(i).breaks();
                        leastRecentPipeSetNum++;
                        weapons.get(equippedItemIndex).disableWeapon();
                    }
                }
            }
        }

        for (int j = leastRecentWeaponNum; j <= mostRecentWeaponNum; j++) {
            if (weapons.get(j).getWeaponHitBox().right() < 0) {
                weapons.get(j).isOffScreen();
                leastRecentWeaponNum++;
            }
        }

        if (!bird.isEquipped()) {
            for (int j = leastRecentWeaponNum; j <= mostRecentWeaponNum; j++) {
                if (birdHitBox1.intersects(weapons.get(j).getWeaponHitBox()) ||
                        birdHitBox1.intersects(weapons.get(j).getWeaponHitBox())) {
                    bird.equippingItem();
                    weapons.get(j).caughtWeapon();
                    equippedItemIndex = j;
                }
            }
        }




        frameCount++;
    }
}
