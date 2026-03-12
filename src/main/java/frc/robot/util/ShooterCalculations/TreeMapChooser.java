package frc.robot.util.ShooterCalculations;

import java.util.TreeMap;

public class TreeMapChooser{


public static TreeMap<Double, TreeMap<Double,AngleSpeedTime>> chooserMap = new TreeMap<>();
    static{
        //Put values here
        chooserMap.put(0.0, ValueFromDistance.shootMap0);
        chooserMap.put(90.0, ValueFromDistance.shootMap90);
        chooserMap.put(180.0, ValueFromDistance.shootMap180);
        chooserMap.put(270.0, ValueFromDistance.shootMap270);
        chooserMap.put(360.0, ValueFromDistance.shootMap360);
    }

public static AngleSpeedTime getChooserMap(double distance, double rAngle){
    if (rAngle < 0 ){
        rAngle = 360 + rAngle;
    }
    if((rAngle >= chooserMap.floorKey(rAngle)) && (rAngle <= chooserMap.ceilingKey(rAngle))) {
         //Might not need the next 3 lines 

        double floorAngle = chooserMap.floorKey(rAngle);
        double ceilingAngle = chooserMap.ceilingKey(rAngle);
        TreeMap<Double, AngleSpeedTime> floorMap = chooserMap.get(floorAngle);
        TreeMap<Double, AngleSpeedTime> ceilingMap = chooserMap.get(ceilingAngle);
        double remainderFrac= (rAngle - floorAngle)/(ceilingAngle- floorAngle);

        AngleSpeedTime floorValue = ValueFromDistance.getAngleSpeedTime(distance, floorMap);
        AngleSpeedTime ceilingValue = ValueFromDistance.getAngleSpeedTime(distance, ceilingMap);

        double angle = floorValue.getAngle() + remainderFrac * (Math.abs(ceilingValue.getAngle() - floorValue.getAngle()));
        double speed = floorValue.getSpeed() + remainderFrac * (Math.abs(ceilingValue.getSpeed() - floorValue.getSpeed()));
        double time =  floorValue.getTime() + remainderFrac * (Math.abs(ceilingValue.getTime() - floorValue.getTime()));
        
        return new AngleSpeedTime(angle,speed,time);
    }
    else{
        return new AngleSpeedTime(0,0,0);
    }
}








}


