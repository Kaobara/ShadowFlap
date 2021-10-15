import Entities.*;
import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Level1 extends Level{
    private final Image BACKGROUND_IMAGE1 = new Image("res/level-1/background.png");
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
    private Integer shotItemIndex;

    // Images of Bird
    private final bagel.Image BIRD_IMAGE_UP1 = new bagel.Image("res/level-1/birdWingUp.png");
    private final bagel.Image BIRD_IMAGE_DOWN1 = new Image("res/level-1/birdWingDown.png");

    // Weapons
    private ArrayList<Weapon> weapons = new ArrayList<Weapon>();
    private int leastRecentWeaponNum = 0;
    private int mostRecentWeaponNum = -1;
    private int weaponNum = 0;
    private Point weaponPoint;

    /**
     * This method is a constructor for Level 1, setting up most of the required attributes of the level
     */
    public Level1() {
        super.lifebar = new Lives(STARTING_LIVES);
        super.bird = new Bird(BIRD_SPAWN.x, BIRD_SPAWN.y, BIRD_IMAGE_UP1, BIRD_IMAGE_DOWN1);
        super.levelScoreThreshold = LEVEL1_POINT_THRESHOLD;
        super.entityTimeScale.resetEntitySpeed();
        super.backgroundImage = BACKGROUND_IMAGE1;
    }

    protected void loseLifeSpawnBird() {
        lifebar.loseLife();
        bird = new Bird(BIRD_SPAWN.x, BIRD_SPAWN.y, BIRD_IMAGE_UP1, BIRD_IMAGE_DOWN1);
    }

    /**
     * This is an override of the super's updateStart, which includes an additional instruction:
     * "Press S to Shoot"
     */
    @Override
    public void updateStart() {
        super.updateStart();
        FONT.drawString("PRESS S TO SHOOT", SHOOT_INSTRUCT_POINT.x, SHOOT_INSTRUCT_POINT.y);
    }

    /**
     * This method is the level1-specific version for updating the game whilst it is running.
     * It has special specifications for spawning pipes (specifically, spawns plastic and steel pipes,
     * in a random position between a min and max value, in addition to spawning weapon entities.
     * This method also takes care of the logic of weapons, specifically getting, holding, and shooting weapons
     * @param input this variable is the input given by the user
     */
    @Override
    public void updateRunning(Input input) {
        // Spawn either Plastic Pipes or Steel Pipes between y=100 and y=500 (height)
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
            super.pipeSetArray.get(mostRecentPipeSetNum).spawnPipeSet(rand_position);
        }

        super.updateRunning(input);

        // Spawn a random type of weapon, at a random interval in a random location between y = 100 adn 500
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

            // If a weapon spawns in the same space as a pipe, immediately disable it
            for (int i = leastRecentPipeSetNum; i <= mostRecentPipeSetNum; i++) {
                if (!weapons.get(mostRecentWeaponNum).checkDisabled() &&
                        (weapons.get(mostRecentWeaponNum).getHitBox().intersects(pipeSetArray.get(i).getUpHitBox()) ||
                                weapons.get(mostRecentWeaponNum).getHitBox().intersects(pipeSetArray.get(i).getDownHitBox()))) {
                    if(shotItemIndex == null || weapons.get(mostRecentWeaponNum) != weapons.get(shotItemIndex)) {
                        weapons.get(mostRecentWeaponNum).disableWeapon();
                        leastRecentWeaponNum++;
                    }
                }
            }
        }

        // Update all weapons that are not being held
        for (int j = leastRecentWeaponNum; j <= mostRecentWeaponNum; j++) {
            if (!weapons.get(j).checkDisabled() && !weapons.get(j).checkIfHeld()) {
                weapons.get(j).updateEntity();
            }
            if (weapons.get(j).getHitBox().right() < 0) {
                if(shotItemIndex == null || weapons.get(mostRecentWeaponNum) != weapons.get(shotItemIndex)) {
                    weapons.get(j).disableWeapon();
                    leastRecentWeaponNum++;
                }
            }
        }

        // Update the weapon that is being held
        if(bird.isEquipped()) {
            if(bird.getIsUp()) {
                weapons.get(equippedItemIndex).updateHeldWeapon(birdHitBox1);
            } else {
                weapons.get(equippedItemIndex).updateHeldWeapon(birdHitBox2);
            }
        }

        // Equipping a weapon
        if (!bird.isEquipped()) {
            for (int j = leastRecentWeaponNum; j <= mostRecentWeaponNum; j++) {
                if (birdHitBox1.intersects(weapons.get(j).getHitBox()) ||
                        birdHitBox1.intersects(weapons.get(j).getHitBox())) {
                    bird.equip();
                    weapons.get(j).caughtWeapon();
                    equippedItemIndex = j;
                }
            }
        }

        // Shooting
        if(input.wasPressed(Keys.S)) {
            if(equippedItemIndex != null) {
                weapons.get(equippedItemIndex).shootWeapon();
                shotItemIndex = equippedItemIndex;
                bird.unequip();
            }
        }

        // Updating shooting weapon
        if(shotItemIndex != null
                && !weapons.get(shotItemIndex).checkDisabled()
                && weapons.get(shotItemIndex).isBeingShot()){
            weapons.get(shotItemIndex).updateShootingWeapon();

            // Check if the weapon collides with a pipe
            for (int i = leastRecentPipeSetNum; i <= mostRecentPipeSetNum; i++) {
                if (!pipeSetArray.get(i).isBroken() && (weapons.get(shotItemIndex).getHitBox().intersects(pipeSetArray.get(i).getUpHitBox())
                        || weapons.get(shotItemIndex).getHitBox().intersects(pipeSetArray.get(i).getDownHitBox()))) {
                    // If a weapon collides with a pipe, check if it can destroy it or not.
                    if(weapons.get(shotItemIndex) instanceof Bomb ||  pipeSetArray.get(i) instanceof PlasticPipe) {
                        score ++;
                        pipeSetArray.get(i).breaks();
                    }
                    weapons.get(shotItemIndex).disableWeapon();
                    break;
                }
            }
        }

        frameCount++;
    }
}
