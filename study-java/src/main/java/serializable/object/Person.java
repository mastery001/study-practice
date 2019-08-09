package serializable.object;

import java.io.Serializable;
import java.util.List;

//@XmlRootElement
public class Person implements Serializable {

    /**
     * 2016年5月1日 下午1:48:15
     */
    private static final long serialVersionUID = -5938793522690145860L;

//    @XmlAttribute
    private String firstname;
//    @XmlAttribute
    private String lastname;
//    @XmlAttribute
    private int age;
    private List<Person> friends;

    public Person() {
    }

    public Person(String firstname, String lastname, int age) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public int getAge() {
        return age;
    }

    public List<Person> getFriends() {
        return friends;
    }

    public void setFriends(List<Person> friends) {
        this.friends = friends;
    }
}
