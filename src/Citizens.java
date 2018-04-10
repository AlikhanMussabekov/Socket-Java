import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Citizens implements Serializable{
    private String name;
    private int age;
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private Date date = new Date();
    private String city = "";

    public Citizens(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getDate() {
        return dateFormat.format(date);
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity(){
        return city;
    }

    public void printInfo(){
        System.out.printf("%s, %d, %s, %d \n", name, age, city, date);
    }
}
