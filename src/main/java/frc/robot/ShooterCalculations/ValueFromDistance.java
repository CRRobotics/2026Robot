package frc.robot.ShooterCalculations;
import java.util.TreeMap;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.Constants.ShooterConstants;

public class ValueFromDistance {

    public static TreeMap<Double, AngleSpeed> shootMap = new TreeMap<>();
    static {
        //put values here
    }

    /**
     * Calculates the speed and angle at which we should shoot based on how far we are
     * @author Kai Kwee-Hallem (with lots of help from Zach & the template of the 2024 code)
     * @param distance
     * @return AngleSpeed
     */
    public static AngleSpeed getAngleSpeed(double distance) {
        if(distance > shootMap.firstKey() && distance < shootMap.lastKey()) {
            double floorDistance = shootMap.floorKey(distance);
            double ceilingDistance = shootMap.ceilingKey(distance);
            double remainderFrac = (distance - floorDistance) / (ceilingDistance - floorDistance);
        if (distance<0){
            distance=0;
        }
            double speed = shootMap.get(floorDistance).getSpeed() + remainderFrac * (Math.abs(shootMap.get(ceilingDistance).getSpeed() - shootMap.get(floorDistance).getSpeed()));
            double angle = shootMap.get(floorDistance).getAngle() + remainderFrac * (Math.abs(shootMap.get(ceilingDistance).getAngle() - shootMap.get(floorDistance).getAngle()));
            System.out.println("WORKING!");
            return new AngleSpeed(angle, speed);
        }
        else if (distance > shootMap.lastKey() && distance < ShooterConstants.ShooterSpeedTopLimit){
            /**
             * What this math does: exterpolates the speed and angle at which to fire if the distance we are shooting from is higher than the highest value in the treemap
             * a--Finds the porportion between the difference of the farthest entry in the treemap and the current distance, and the difference of the farthest entry in the treemap and the second farthest one
             * b--Then, finds the difference between the speed of the farthest entry in the treemap and the second farthest one
             * c--Finally, multiplies a by b and adds the speed of the farthest entry in the treemap
             */
            double lastKey = shootMap.lastKey();
            double Speeda = (distance - lastKey)/(lastKey - shootMap.lowerKey(lastKey));
            double Speedb = (shootMap.get(lastKey).getSpeed() - shootMap.get(shootMap.lowerKey(lastKey)).getSpeed());
            double Speedc = (Speeda*Speedb) + shootMap.get(lastKey).getSpeed();
            /**Same thing for the angle here */
            double Anglea = (distance - lastKey)/(lastKey - shootMap.lowerKey(lastKey));
            double Angleb = (shootMap.get(lastKey).getAngle() - shootMap.get(shootMap.lowerKey(lastKey)).getAngle());
            double Anglec = (Anglea*Angleb) + shootMap.get(lastKey).getAngle();
            return new AngleSpeed(Anglec, Speedc);
        }
        else if (distance < shootMap.firstKey()){
            /**
             * The same thing, but for the case where we're closer than the smallest value in the treemap.
             */
            double firstKey = shootMap.firstKey();
            double Speeda = (firstKey - distance)/(firstKey - shootMap.higherKey(firstKey));
            double Speedb = (shootMap.get(firstKey).getSpeed() - shootMap.get(shootMap.higherKey(firstKey)).getSpeed());
            double Speedc = (Speeda*Speedb) + shootMap.get(firstKey).getSpeed();
            /**Same thing for the angle here */
            double Anglea = (distance - firstKey)/(firstKey - shootMap.higherKey(firstKey));
            double Angleb = (shootMap.get(firstKey).getAngle() - shootMap.get(shootMap.higherKey(firstKey)).getAngle());
            double Anglec = (Anglea*Angleb) + shootMap.get(firstKey).getAngle();
            return new AngleSpeed(Anglec, Speedc);
        }
        else {
            /**
             * backup case for when we're shooting farther than the limit value defined in constants (100 by default; don't ask 100 what, I don't know either.)
             * Shoots as far as it would if the distance was equal to the largest value in the treemap
             */
            return new AngleSpeed(shootMap.get(shootMap.lastKey()).getAngle(), shootMap.get(shootMap.lastKey()).getSpeed());
        }
    }

    /**
     * Find distance from pose. I don't know if it works yet.
     * @param current
     * @param target
     * @return
     */
    public static double getDistanceToTarget(Pose2d current, Translation2d target) {
        return current.getTranslation().getDistance(target);
    }
}