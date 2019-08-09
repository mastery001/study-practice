package serializable.object;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;

/**
 * Created by zouziwen on 2018/1/13.
 */
public class PersonExternalizable implements Externalizable {
    private String firstname;
    private String lastname;
    private int age;
    private List<PersonExternalizable> friends;

    public PersonExternalizable() {
    }

    public PersonExternalizable(String firstname, String lastname, int age) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getFirstname());
        out.writeObject(getLastname());
        out.writeInt(getAge());
        out.writeObject(getFriends());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setFirstname((String) in.readObject());
        setLastname((String) in.readObject());
        setAge(in.readInt());
        setFriends((List<PersonExternalizable>) in.readObject());
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<PersonExternalizable> getFriends() {
        return friends;
    }

    public void setFriends(List<PersonExternalizable> friends) {
        this.friends = friends;
    }
}


