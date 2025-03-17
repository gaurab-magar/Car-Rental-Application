import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car{
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePricePerDay , boolean isAvailable){
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = isAvailable;
    }
    public String getCarId(){
        return carId;
    }
    public String getModel(){
        return model;
    }
    public String getBrand(){
        return brand;
    }
    public double calculatePrice(int rentalDays){
        return basePricePerDay * rentalDays;
    }
    public boolean isAvailable(){
        return isAvailable;
    }
    public void rent(){
        isAvailable = false;
    }
    public void returnCar(){
        isAvailable = true;
    }
}
class Customer{
    private String customerId;
    private String name;

    public Customer(String customerId, String name){
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}
class Rental{
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days){
        this.car = car;
        this.customer = customer;
        this.days = days;
    }
    public Car getCar(){
        return car;
    }
    public Customer getCustomer(){
        return customer;
    }
    public int getDays(){
        return days;
    }
}
class CarRentalSystem{
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem(){
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }
    public void addCar(Car car){
        cars.add(car);
    }
    public void addCustomer(Customer customer){
        customers.add(customer);
    }
    public void rentalCar(Car car, Customer customer, int days){
        if(car.isAvailable()){
            car.rent();
            rentals.add(new Rental(car,customer,days));
        } else {
            System.out.println("car is not available for rent");
        }
    }
    public void renturnCar(Car car){
        car.returnCar();
        Rental rentalToRemove = null;
        for (Rental rental : rentals){
            if(rental.getCar()==car){
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null){
            rentals.remove(rentalToRemove);
            System.out.println("car returned successfully");
        }else{
            System.out.println("car was not rented");
        }
    }



}
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("== CAR RENTAL SYSTEM ==");
            System.out.println("1. Rent a car");
            System.out.println("2. Return a car");
            System.out.println("3. Exit");
            System.out.println("Enter Your Choice");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if(choice ==1){
                System.out.println("\n== Rent a car ==\n");
                System.out.println("Enter Your name: ");
                String customerName = scanner.nextLine();

                System.out.println("\nAvailable cars: ");
                for (Car car : cars){
                    if(car.isAvailable()){
                        System.out.println(car.getCarId() + " - "+ car.getBrand() + " - " + car.getModel());
                    }
                }
                System.out.println("\n Enter the car Id you want to rent");
                String carId = scanner.nextLine();
                System.out.println("\n Enter the num of days want to rental");
                String rentalDays = scanner.nextLine();
                scanner.nextLine();
                Customer newCustomer = new Customer("CUS"+(customers.size()+1), customerName);
                addCustomer(newCustomer);
                Car selectedCar = null;
                for (Car car : cars){
                    if(car.getCarId().equals(carId) && car.isAvailable()){
                        selectedCar = car;
                        break;
                    }
                }
                if(selectedCar != null){
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n== Rental Information ==");
                    System.out.println("Customer ID: "+ newCustomer.getCustomerId());
                    System.out.println("Customer Name:" + newCustomer.getName());
                    System.out.println("Car: "+ selectedCar.getBrand()+" "+ selectedCar.getModel());
                    System.out.println("Rental Days:" + rentalDays);
                    System.out.println("Total Price : " + totalPrice);

                    System.out.println("\n Confirm rental (Y/N)");
                    String confirm = scanner.nextLine();

                    if(confirm.equalsIgnoreCase("Y")){
                        rentCar(selectedCar,newCustomer,rentalDays);
                        System.out.println("\n car retned succesfully");
                    } else {
                        System.out.println("\n Invalid car selection or car is not available for rent");
                    }
                } else if(choice == 2){
                    System.out.println("\n == Return a Car == \n");
                    System.out.println("Enter the car ID you want to return: ");
                    String carId = scanner.nextLine();
                    Car carToReturn = null;
                    for(Car car: cars){
                        if(car.getCarId().equals(carId)&&!car.isAvailable()){
                            carToReturn = car;
                            break;
                        }
                    }
                    if(carToReturn != null){
                        Customer customer = null;
                        for (Rental rental : rentals){
                            if (rental.getCar() == carToReturn){
                                customer = rental.getCustomer();
                                break;
                            }
                        }
                        if(customer != null){
                            returnCar(carToReturn);
                            System.out.println("Car returned Successfully by"+ customer.getName());
                        }else {
                            System.out.println("Car was not returned or rental information is missing.");
                        }
                    }else {
                        System.out.println("Invalid carId or car is not rented.");
                    }
                }else if (choice == 3){
                    break;
                }else {
                    System.out.println("Invalid choice. please enter a  valid option.");
                }
            }
            System.out.println("\n Thank you for using the car rental system");
        }
    }
}

//main

//    CarRentalSystem rentalSystem = new CarRentalSystem();
//    car car1 = new Car("c002","Toyota","Camry", 60);
//    rentalSystem.addCar(car1);
//     rentalSystem.menu();
