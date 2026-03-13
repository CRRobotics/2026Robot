package frc.robot.util.ShooterCalculations;

import java.util.TreeMap;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

public static AngleSpeedTime getChooserMap(double distance, double inputAngle){
    double tAngle = inputAngle;
    if (tAngle < 0 ){
        tAngle = 360 + tAngle;
    }

    AngleSpeedTime ret = new AngleSpeedTime(0,0,0);

    SmartDashboard.putNumber("mapRAngle", tAngle);
    if((tAngle >= chooserMap.firstKey()) && (tAngle <= chooserMap.lastKey())){
         //Might not need the next 3 lines 
        double floorAngle = chooserMap.floorKey(tAngle);
        double ceilingAngle = chooserMap.ceilingKey(tAngle);
        TreeMap<Double, AngleSpeedTime> floorMap = chooserMap.get(floorAngle);
        TreeMap<Double, AngleSpeedTime> ceilingMap = chooserMap.get(ceilingAngle);
        AngleSpeedTime floorValue = ValueFromDistance.getAngleSpeedTime(distance, floorMap);
        AngleSpeedTime ceilingValue = ValueFromDistance.getAngleSpeedTime(distance, ceilingMap);

        if(floorAngle == ceilingAngle){
            ret = new AngleSpeedTime(floorValue.getAngle(), floorValue.getSpeed(), floorValue.getTime());
        } else {
            double remainderFrac= (tAngle - floorAngle)/(ceilingAngle- floorAngle);

            double angle = floorValue.getAngle() + remainderFrac * (Math.abs(ceilingValue.getAngle() - floorValue.getAngle()));
            double speed = floorValue.getSpeed() + remainderFrac * (Math.abs(ceilingValue.getSpeed() - floorValue.getSpeed()));
            double time =  floorValue.getTime() + remainderFrac * (Math.abs(ceilingValue.getTime() - floorValue.getTime()));

            ret = new AngleSpeedTime(angle,speed,time);
        }
    }

    Logger.recordOutput("AngleSpeedTime/Input/Distance", distance);
    Logger.recordOutput("AngleSpeedTime/Input/Angle", inputAngle);
    Logger.recordOutput("AngleSpeedTime/Input/ClampedAngle", tAngle);
    Logger.recordOutput("AngleSpeedTime/Output/Angle", ret.getAngle());
    Logger.recordOutput("AngleSpeedTime/Output/Speed", ret.getSpeed());
    Logger.recordOutput("AngleSpeedTime/Output/Time", ret.getTime());

    return ret;
}








}


