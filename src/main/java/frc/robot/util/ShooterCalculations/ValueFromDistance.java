package frc.robot.util.ShooterCalculations;
import java.util.TreeMap;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.Constants.ShooterConstants;

public class ValueFromDistance {

    public static TreeMap<Double, AngleSpeedTime> shootMap0 = new TreeMap<>();
    static {
        //put values here
        shootMap0.put(33.0, new AngleSpeedTime(5,3000,1));
        shootMap0.put(116.0, new AngleSpeedTime(20,3200,1));
        shootMap0.put(151.0, new AngleSpeedTime(20,3650,1));
        shootMap0.put(181.0, new AngleSpeedTime(23,3900,1));
        }

    public static TreeMap<Double, AngleSpeedTime> shootMap90 = new TreeMap<>();
    static {
        //put values here
        shootMap90.put(24.0, new AngleSpeedTime(4,2900,1));
        shootMap90.put(39.5, new AngleSpeedTime(7,3000,1));
        shootMap90.put(51.5, new AngleSpeedTime(10,3150,1));
        shootMap90.put(79.0, new AngleSpeedTime(10,3180,1));
        shootMap90.put(109.75, new AngleSpeedTime(12,3370,1));

    }
    
    public static TreeMap<Double, AngleSpeedTime> shootMap180 = new TreeMap<>();
    static{
        shootMap180.put(31.0, new AngleSpeedTime(8, 3100, 0));
        shootMap180.put(80.0, new AngleSpeedTime(13, 3000, 0));
        shootMap180.put(130.0, new AngleSpeedTime(24, 3600, 0));
    }

    public static TreeMap<Double, AngleSpeedTime> shootMap270 = new TreeMap<>();
    static{
        shootMap270.put(122.0, new AngleSpeedTime(18, 3800, 0));
        shootMap270.put(81.0, new AngleSpeedTime(14, 3400, 0));
        shootMap270.put(32.0, new AngleSpeedTime(5, 3200, 0));
        shootMap270.put(70.0, new AngleSpeedTime(10, 3300, 0));
        shootMap270.put(104.0, new AngleSpeedTime(16, 3400, 0));
    }




      public static TreeMap<Double, AngleSpeedTime> shootMap360 = new TreeMap<>();
    static{
        //put values here
        shootMap0.put(33.0, new AngleSpeedTime(5,3000,1));
        shootMap0.put(116.0, new AngleSpeedTime(20,3200,1));
        shootMap0.put(151.0, new AngleSpeedTime(20,3650,1));
        shootMap0.put(181.0, new AngleSpeedTime(23,3900,1));
    }
    

    /**
     * Calculates the speed and angle at which we should shoot based on how far we are
     * @author Kai Kwee-Hallem (with lots of help from Zach & the template of the 2024 code)
     * @param distance in meters
     * @return AngleSpeedTime
     */
    public static AngleSpeedTime getAngleSpeedTime(double distanceMeters, TreeMap<Double,AngleSpeedTime> shootMap) {
        double distance = 39.37 * distanceMeters;
        if(distance > shootMap.firstKey() && distance < shootMap.lastKey()) {
             if (distance<0){
            distance=0;
        }
            double floorDistance = shootMap.floorKey(distance);
            double ceilingDistance = shootMap.ceilingKey(distance);
            double remainderFrac = (distance - floorDistance) / (ceilingDistance - floorDistance);
            
            double speed = shootMap.get(floorDistance).getSpeed() + remainderFrac * (Math.abs(shootMap.get(ceilingDistance).getSpeed() - shootMap.get(floorDistance).getSpeed()));
            double angle = shootMap.get(floorDistance).getAngle() + remainderFrac * (Math.abs(shootMap.get(ceilingDistance).getAngle() - shootMap.get(floorDistance).getAngle()));
            double time = shootMap.get(floorDistance).getTime() + remainderFrac * (Math.abs(shootMap.get(ceilingDistance).getTime() - shootMap.get(floorDistance).getTime()));
            //System.out.println("WORKING!");
            return new AngleSpeedTime(angle, speed, time);
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
            /**Same thing for time. Apparently visions wants this, idk */
            double Timea = (distance - lastKey)/(lastKey - shootMap.lowerKey(lastKey));
            double Timeb = (shootMap.get(lastKey).getTime() - shootMap.get(shootMap.lowerKey(lastKey)).getTime());
            double Timec = (Timea*Timeb) + shootMap.get(lastKey).getTime();
            return new AngleSpeedTime(Anglec, Speedc, Timec);
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
            /**For time again */
            double Timea = (distance - firstKey)/(firstKey - shootMap.higherKey(firstKey));
            double Timeb = (shootMap.get(firstKey).getTime() - shootMap.get(shootMap.higherKey(firstKey)).getTime());
            double Timec = (Timea*Timeb) + shootMap.get(firstKey).getTime();
            return new AngleSpeedTime(Anglec, Speedc, Timec);
        }
        else {
            /**
             * backup case for when we're shooting farther than the limit value defined in constants (100 by default; don't ask 100 what, I don't know either.)
             * Shoots as far as it would if the distance was equal to the largest value in the treemap
             */
            return new AngleSpeedTime(shootMap.get(shootMap.lastKey()).getAngle(), shootMap.get(shootMap.lastKey()).getSpeed(), shootMap.get(shootMap.lastKey()).getTime());
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