package Backend.Project.TaxiCompany.Support;

import Backend.Project.TaxiCompany.Model.Car;

public class CarUsage {
    public long carId;
    public String license;
    public int usageTime;

    public boolean addUsageTime(Car car)
    {

        //check if the component is null
        if(carId==0)
        {
            //no thing has been added
            this.carId=car.getId();
            this.license=car.getLicensePlate();
            usageTime=1;//has one use
            return true;
        }
        else{
            //compare car
            if(carId==car.getId())
            {
                //same car usage
                usageTime++;
                return true;
            }
            return false;//no thign has been added
        }
    }
    public String getLicense()
    {
        return license;
    }
    public int getUsageTime()
    {
        return usageTime;
    }
}
