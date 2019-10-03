package thread.demo;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/***
 * @Author Alison
 * @Date 2019/4/29
 **/

public class AtomicLongFieldDemo {

    public static void main(String[] args) {
        Class clazz = Person.class;
        AtomicLongFieldUpdater a = AtomicLongFieldUpdater.newUpdater(clazz, "id");
        Person p = new Person(12345678L);
        a.compareAndSet(p, 12345678L, 1000);
        System.out.println("id==" + p.getId());
    }

    static class Person {
        volatile long id;

        public Person(long id) {
            this.id = id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }
    }
}
