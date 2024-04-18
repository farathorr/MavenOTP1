package mongoDB;

import org.bson.Document;
import org.bson.types.ObjectId;

public class Person {
    private ObjectId id;
    private String name;
    private int age;
    private String city;

    // Constructors, getters, and setters
    public Person() {}

    public Person(String name, int age, String city) {
        this.name = name;
        this.age = age;
        this.city = city;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", city='" + city + '\'' +
                '}';
    }

    public Document toDocument() {
        return new Document("name", name)
                .append("age", age)
                .append("city", city);
    }
}