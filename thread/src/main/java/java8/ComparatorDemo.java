package java8;

import java.util.Comparator;
import java.util.Optional;

public class ComparatorDemo {

    public static void main(String[] args) {
        Comparator<SupplierDemo.Person> comparator = (p1, p2) -> p1.firstName.compareTo(p2.firstName);
        SupplierDemo.Person p1 = new SupplierDemo.Person("John", "Doe");
        SupplierDemo.Person p2 = new SupplierDemo.Person("Alice", "Wonderland");
        comparator.compare(p1, p2);// >0
        comparator.reversed().compare(p1, p2);// <0


        // optional
        Optional<String> optional = Optional.of("bam");
        optional.isPresent(); //true
        optional.get();// get value
        optional.orElse("fallback");
        optional.ifPresent((s) -> System.out.println(s.charAt(0)));
        // output first

    }
}
